import { Modal, ModalContent, ModalHeader } from "@nextui-org/react";
import React from "react";
import FormBook from "./FormBook";

type Props = {
   isOpen: boolean;
   onOpenChange: ((isOpen: boolean) => void) | undefined;
};

export default function ModalNewBook({ isOpen, onOpenChange }: Props) {
   return (
      <Modal size="lg" isOpen={isOpen} onOpenChange={onOpenChange}>
         <ModalContent>
            {(onClose) => (
               <>
                  <ModalHeader className="flex flex-col gap-1">
                     Add New Book
                  </ModalHeader>
                  <FormBook onClose={onClose} choose="create" />
               </>
            )}
         </ModalContent>
      </Modal>
   );
}
