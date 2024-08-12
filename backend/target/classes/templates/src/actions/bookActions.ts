"use server";

import { BookSchema } from "@/lib/bookSchema";
import { ActionResponses } from "@/types";
import { Book } from "@/types/movie.type";
import { ZodIssue } from "zod";

export async function bookFindAllAction(): Promise<Book[]> {
   const urlbackend = process.env.NEXT_URL_BACKEND_JAVA as string;
   try {
      const res = await fetch(`${urlbackend}/api/book`, { cache: "no-cache" });
      const book: any = await res.json();

      if (book.statusCode === 404) {
         throw new Error("book can't not found");
      }

      if (!Array.isArray(book.data)) {
         throw new Error("Unexpected response format");
      }

      return book.data.map((b: any) => ({
         id: b.book_id,
         title: b.book_title,
         author: b.author_name,
         description: b.book_description,
      }));
   } catch (error: any) {
      console.log(error.message);
      throw error;
   }
}

export async function bookSaveAction(
   data: BookSchema
): Promise<ActionResponses<string>> {
   const urlbackend = process.env.NEXT_URL_BACKEND_JAVA as string;
   const bookObject = {
      author_name: data.author,
      book_title: data.title,
      book_description: data.description,
   };

   try {
      const res = await fetch(`${urlbackend}/api/book`, {
         method: "POST",
         cache: "no-store",
         headers: {
            "Content-Type": "application/json",
         },
         body: JSON.stringify(bookObject),
      });

      const { data, statusCode } = await res.json();

      if (statusCode === 400) {
         const zodIssues: ZodIssue[] = data.map((error: any) => {
            let pathZod: string[] = [];

            switch (error.fields) {
               case "bookTitle":
                  pathZod = ["title"];
                  break;
               case "authorName":
                  pathZod = ["author"];
                  break;
               case "bookDescription":
                  pathZod = ["description"];
                  break;
               default:
                  pathZod = [];
            }

            return {
               path: pathZod,
               message: error.defaultMessage,
               code: "invalid_type",
            };
         });
         return { status: "error", error: zodIssues };
      }

      return { status: "succes", data };
   } catch (error) {
      return { status: "error", error: "something went wrong" };
   }
}

export async function bookUpdateAction(
   book: BookSchema,
   bookid: number
): Promise<string | undefined> {
   const urlbackend = process.env.NEXT_URL_BACKEND_JAVA as string;
   try {
      const bookObjectUpdate = {
         book_title: book.title,
         book_description: book.description,
      };

      const response = await fetch(`${urlbackend}/api/book/${bookid}`, {
         method: "PATCH",
         cache: "no-store",
         headers: {
            "Content-Type": "application/json",
         },
         body: JSON.stringify(bookObjectUpdate),
      });

      const { statusCode, data } = await response.json();

      if (statusCode === 404) {
         return data;
      }

      return data;
   } catch (error) {
      console.log(error);
   }
}
