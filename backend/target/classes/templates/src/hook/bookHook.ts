"use client";

import { Book } from "@/types/movie.type";
import { Selection, SortDescriptor } from "@nextui-org/react";
import React from "react";

const INITIAL_VISIBLE_COLUMNS = ["title", "author", "description", "actions"];

export const columns = [
   { name: "ID", uid: "id", sortable: true },
   { name: "TITLE", uid: "title", sortable: true },
   { name: "AUTHOR", uid: "author", sortable: true },
   { name: "DESCRIPTION", uid: "description", sortable: true },
   { name: "ACTIONS", uid: "actions" },
];

export function useHookBookTable(book: Book[]) {
   const [filterValue, setFilterValue] = React.useState("");

   const [selectedKeys, setSelectedKeys] = React.useState<Selection>(
      new Set([])
   );

   const [visibleColumns, setVisibleColumns] = React.useState<Selection>(
      new Set(INITIAL_VISIBLE_COLUMNS)
   );

   const [rowsPerPage, setRowsPerPage] = React.useState(5);

   const [sortDescriptor, setSortDescriptor] = React.useState<SortDescriptor>({
      column: "age",
      direction: "ascending",
   });

   const [page, setPage] = React.useState(1);

   const pages = Math.ceil(book.length / rowsPerPage);

   const hasSearchFilter = Boolean(filterValue);

   const headerColumns = React.useMemo(() => {
      if (visibleColumns === "all") return columns;

      return columns.filter((column) =>
         Array.from(visibleColumns).includes(column.uid)
      );
   }, [visibleColumns]);

   const filteredItems = React.useMemo(() => {
      let filteredUsers = [...book];

      if (hasSearchFilter) {
         filteredUsers = filteredUsers.filter((user) =>
            user.title.toLowerCase().includes(filterValue.toLowerCase())
         );
      }

      return filteredUsers;
   }, [book, filterValue, hasSearchFilter]);

   const items = React.useMemo(() => {
      const start = (page - 1) * rowsPerPage;
      const end = start + rowsPerPage;

      return filteredItems.slice(start, end);
   }, [page, filteredItems, rowsPerPage]);

   const sortedItems = React.useMemo(() => {
      return [...items].sort((a: Book, b: Book) => {
         const first = a[sortDescriptor.column as keyof Book] as number;
         const second = b[sortDescriptor.column as keyof Book] as number;
         const cmp = first < second ? -1 : first > second ? 1 : 0;

         return sortDescriptor.direction === "descending" ? -cmp : cmp;
      });
   }, [sortDescriptor, items]);

   const onRowsPerPageChange = React.useCallback(
      (e: React.ChangeEvent<HTMLSelectElement>) => {
         setRowsPerPage(Number(e.target.value));
         setPage(1);
      },
      []
   );

   const onSearchChange = React.useCallback((value?: string) => {
      if (value) {
         setFilterValue(value);
         setPage(1);
      } else {
         setFilterValue("");
      }
   }, []);

   const classNames = React.useMemo(
      () => ({
         wrapper: ["max-h-[382px]", "max-w-3xl"],
         th: [
            "bg-transparent",
            "text-default-500",
            "border-b",
            "border-divider",
         ],
         td: [
            // changing the rows border radius
            // first
            "group-data-[first=true]:first:before:rounded-none",
            "group-data-[first=true]:last:before:rounded-none",
            // middle
            "group-data-[middle=true]:before:rounded-none",
            // last
            "group-data-[last=true]:first:before:rounded-none",
            "group-data-[last=true]:last:before:rounded-none",
         ],
      }),
      []
   );

   return {
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
      items,
   };
}
