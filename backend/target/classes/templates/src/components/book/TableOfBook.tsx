"use client";

import React, { useCallback } from "react";
import {
   Table,
   TableHeader,
   TableColumn,
   TableBody,
   TableRow,
   TableCell,
   Input,
   Button,
   DropdownTrigger,
   Dropdown,
   DropdownMenu,
   DropdownItem,
   Pagination,
   useDisclosure,
} from "@nextui-org/react";
import { VerticalDotsIcon } from "./VerticalDotItems";
import ModalNewBook from "./ModalNewBook";

import { Book } from "@/types/movie.type";
import { capitalize } from "@/util/capitalize";
import { useHookBookTable, columns } from "@/hook/bookHook";
import ModalEditBook from "./ModalEditBook";

type Props = {
   book: Book[];
};

export default function TableOfBook({ book }: Props) {
   const {
      classNames,
      selectedKeys,
      sortDescriptor,
      setSelectedKeys,
      setSortDescriptor,
      sortedItems,
      onRowsPerPageChange,
      onSearchChange,
      pages,
      page,
      setPage,
      headerColumns,
      setVisibleColumns,
      hasSearchFilter,
      filterValue,
      setFilterValue,
      visibleColumns,
   } = useHookBookTable(book);
   const {
      isOpen: isOpenNew,
      onOpen: onOpenNew,
      onOpenChange: onOpenNewChange,
   } = useDisclosure();
   const {
      isOpen: isOpenEdit,
      onOpen: onOpenEdit,
      onOpenChange: onOpenEditChange,
   } = useDisclosure();

   const [selectedBook, setSelectedBook] = React.useState<Book | null>(null);

   const handleEditClick = useCallback(
      (book: Book) => {
         setSelectedBook(book);
         onOpenEdit();
      },
      [onOpenEdit]
   );

   const renderCell = React.useCallback(
      (book: Book, columnKey: React.Key) => {
         const cellValue = book[columnKey as keyof Book];
         const dataKey = `key-${book.id}-${columnKey}`;

         switch (columnKey) {
            case "title":
               return (
                  <div className="w-max">
                     <p className="text-bold capitalize line-clamp-1">
                        {book.title}
                     </p>
                  </div>
               );
            case "author":
               return (
                  <div className="bg-slate-200 w-max">
                     <p className="text-bold capitalize">{book.author}</p>
                  </div>
               );
            case "description":
               return (
                  <div className="w-full bg-yellow-400">
                     <p className="text-bold capitalize line-clamp-2">
                        {book.description}
                     </p>
                  </div>
               );
            case "actions":
               return (
                  <>
                     <div
                        className="relative flex justify-end items-center gap-2"
                        data-key={dataKey}>
                        <Dropdown className="bg-background border-1 border-default-200">
                           <DropdownTrigger>
                              <Button
                                 isIconOnly
                                 radius="full"
                                 size="sm"
                                 variant="light">
                                 <VerticalDotsIcon className="text-default-400" />
                              </Button>
                           </DropdownTrigger>
                           <DropdownMenu>
                              <DropdownItem>View</DropdownItem>
                              <DropdownItem
                                 onPress={() => handleEditClick(book)}>
                                 Edit
                              </DropdownItem>
                              <DropdownItem>Delete</DropdownItem>
                           </DropdownMenu>
                        </Dropdown>
                     </div>
                  </>
               );
            default:
               return cellValue;
         }
      },
      [handleEditClick]
   );

   const topContent = React.useMemo(() => {
      return (
         <>
            <ModalNewBook isOpen={isOpenNew} onOpenChange={onOpenNewChange} />
            {selectedBook && (
               <ModalEditBook
                  isOpen={isOpenEdit}
                  onOpenChange={onOpenEditChange}
                  book={selectedBook}
               />
            )}
            <div className="flex flex-col gap-4">
               <div className="flex justify-between gap-3 items-end">
                  <Input
                     isClearable
                     classNames={{
                        base: "w-[30%] sm:w-[45%]",
                        inputWrapper: "border-1",
                     }}
                     placeholder="Search by book title"
                     size="sm"
                     // startContent={<SearchIcon className="text-default-300" />}
                     value={filterValue}
                     variant="bordered"
                     onClear={() => setFilterValue("")}
                     onValueChange={onSearchChange}
                  />
                  <div className="flex gap-3">
                     <Dropdown>
                        <DropdownTrigger className="hidden sm:flex">
                           <Button
                              // endContent={<ChevronDownIcon className="text-small" />}
                              size="sm"
                              variant="flat">
                              Columns
                           </Button>
                        </DropdownTrigger>
                        <DropdownMenu
                           disallowEmptySelection
                           aria-label="Table Columns"
                           closeOnSelect={false}
                           selectedKeys={visibleColumns}
                           selectionMode="multiple"
                           onSelectionChange={setVisibleColumns}>
                           {columns.map((column) => (
                              <DropdownItem
                                 key={column.uid}
                                 className="capitalize">
                                 {capitalize(column.name)}
                              </DropdownItem>
                           ))}
                        </DropdownMenu>
                     </Dropdown>
                     <Button
                        onPress={onOpenNew}
                        className="bg-foreground text-background"
                        endContent={"+"}
                        size="sm">
                        Add New Book
                     </Button>
                  </div>
               </div>
               <div className="flex justify-between items-center">
                  <span className="text-default-400 text-small">
                     Total {book.length} users
                  </span>
                  <label className="flex items-center text-default-400 text-small">
                     Rows per page:
                     <select
                        className="bg-transparent outline-none text-default-400 text-small"
                        onChange={onRowsPerPageChange}>
                        <option value="5">5</option>
                        <option value="10">10</option>
                        <option value="15">15</option>
                     </select>
                  </label>
               </div>
            </div>
         </>
      );
   }, [
      isOpenNew,
      onOpenNewChange,
      selectedBook,
      isOpenEdit,
      onOpenEditChange,
      filterValue,
      onSearchChange,
      visibleColumns,
      setVisibleColumns,
      onOpenNew,
      book.length,
      onRowsPerPageChange,
      setFilterValue,
   ]);

   const bottomContent = React.useMemo(() => {
      return (
         <div className="py-2 px-2 flex justify-between items-center">
            <Pagination
               showControls
               classNames={{
                  cursor: "bg-foreground text-background",
               }}
               color="default"
               isDisabled={hasSearchFilter}
               page={page}
               total={pages}
               variant="light"
               onChange={setPage}
            />
            {/* <span className="text-small text-default-400">
               {selectedKeys === "all"
                  ? "All items selected"
                  : `${selectedKeys.size} of ${items.length} selected`}
            </span> */}
         </div>
      );
   }, [hasSearchFilter, page, pages, setPage]);

   return (
      <>
         <Table
            isCompact
            removeWrapper
            aria-label="Example table with custom cells, pagination and sorting"
            bottomContent={bottomContent}
            bottomContentPlacement="outside"
            checkboxesProps={{
               classNames: {
                  wrapper:
                     "after:bg-foreground after:text-background text-background",
               },
            }}
            classNames={classNames}
            selectedKeys={selectedKeys}
            selectionMode="single"
            sortDescriptor={sortDescriptor}
            topContent={topContent}
            topContentPlacement="outside"
            onSelectionChange={setSelectedKeys}
            onSortChange={setSortDescriptor}>
            <TableHeader columns={headerColumns}>
               {(column) => (
                  <TableColumn
                     key={column.uid}
                     align={column.uid === "actions" ? "end" : "start"}
                     allowsSorting={column.sortable}>
                     {column.name}
                  </TableColumn>
               )}
            </TableHeader>
            <TableBody emptyContent={"No users found"} items={sortedItems}>
               {(item) => (
                  <TableRow key={item.id}>
                     {(columnKey) => (
                        <TableCell>{renderCell(item, columnKey)}</TableCell>
                     )}
                  </TableRow>
               )}
            </TableBody>
         </Table>
      </>
   );
}
