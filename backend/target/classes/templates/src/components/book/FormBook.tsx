"use client";
import React, { useEffect } from "react";
import { BookSchema, bookSchema } from "@/lib/bookSchema";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import {
   Button,
   Input,
   ModalBody,
   ModalFooter,
   Textarea,
} from "@nextui-org/react";
import { bookSaveAction, bookUpdateAction } from "@/actions/bookActions";
import { useRouter } from "next/navigation";
import { Book } from "@/types/book.type";

type Props = {
   onClose: () => void;
   choose: "create" | "update";
   book?: Book;
};

export default function FormBook({ onClose, choose, book }: Props) {
   const router = useRouter();
   const {
      handleSubmit,
      register,
      formState: { isSubmitting, isValid, errors },

      setError,
      reset,
   } = useForm<BookSchema>({
      mode: "onTouched",
      resolver: zodResolver(bookSchema), // validation client
   });

   useEffect(() => {
      if (book) {
         reset({
            title: book.title,
            description: book.description,
            author: book.author,
         });
      }
   }, [book, reset]);

   const submitBookHandler = async (data: BookSchema) => {
      await new Promise((resolve) =>
         setTimeout(() => resolve("success"), 2000)
      );

      if (choose === "create") {
         const result = await bookSaveAction(data);
         if (result.status === "error") {
            if (Array.isArray(result.error)) {
               result.error.forEach((e) => {
                  const fieldsname = e.path.join("") as
                     | "title"
                     | "author"
                     | "description";
                  setError(fieldsname, { message: e.message });
               });
            }
         }
      }

      if (choose === "update") {
         const result = await bookUpdateAction(data, book!.id);
         console.log(result);
      }
      onClose();
      router.refresh();
   };

   return (
      <form
         onSubmit={handleSubmit(submitBookHandler)}
         className="flex flex-col gap-3">
         <ModalBody>
            <Input
               type="text"
               label="Book Title"
               placeholder={!book ? "title book is here input..." : ""}
               defaultValue={book ? book.title : ""}
               variant="bordered"
               isInvalid={!!errors.title}
               errorMessage={errors.title?.message}
               className="border-black"
               {...register("title")}
            />
            <Input
               type="text"
               label="Author Name"
               placeholder={!book ? "author name is here input..." : ""}
               defaultValue={book ? book.author : ""}
               variant={choose === "update" ? "flat" : "bordered"}
               isInvalid={!!errors.author}
               errorMessage={errors.author?.message}
               isReadOnly={choose === "update"}
               color={choose === "update" ? "primary" : "default"}
               {...register("author")}
            />

            <Textarea
               label="Description"
               placeholder={!book ? "description book is here input..." : ""}
               defaultValue={book ? book.description : ""}
               variant="bordered"
               isInvalid={!!errors.description}
               errorMessage={errors.description?.message}
               {...register("description")}
            />
         </ModalBody>

         <ModalFooter>
            <Button
               type="button"
               color="danger"
               variant="light"
               onPress={onClose}>
               Close
            </Button>
            <Button
               type="submit"
               color={isValid ? "success" : "default"}
               variant="solid"
               isDisabled={!isValid}
               isLoading={isSubmitting}
               className="text-white">
               {choose === "create" ? (
                  <>{isSubmitting ? "send..." : "submit book"}</>
               ) : (
                  <>{isSubmitting ? "send..." : "update book"}</>
               )}
            </Button>
         </ModalFooter>
      </form>
   );
}
