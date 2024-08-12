export type Book = {
   id: number;
   title: string;
   author: string;
   description: string;
};

export type BookUpdateType = Pick<Book, "title" | "description">;
