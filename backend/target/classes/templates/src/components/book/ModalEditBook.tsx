import { Modal, ModalContent, ModalHeader } from "@nextui-org/react";
import React from "react";
import FormBook from "./FormBook";
import { Book } from "@/types/movie.type";

type Props = {
   book: Book;
   isOpen: boolean;
   onOpenChange: ((isOpen: boolean) => void) | undefined;
};

export default function ModalEditBook({ isOpen, onOpenChange, book }: Props) {
   return (
      <Modal size="lg" isOpen={isOpen} onOpenChange={onOpenChange}>
         <ModalContent>
            {(onClose) => (
               <>
                  <ModalHeader className="flex flex-col gap-1">
                     Edit New Book
                  </ModalHeader>
                  <FormBook onClose={onClose} choose="update" book={book} />
               </>
            )}
         </ModalContent>
      </Modal>
   );
}
