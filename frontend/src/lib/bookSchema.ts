import { z } from "zod";

export const bookSchema = z.object({
   title: z.string().min(1, "Title is required"),
   author: z.string().min(1, "Author name is required"),
   description: z
      .string()
      .min(1, "Description is required")
      .min(6, "Description book must be at least 6 characters long"),
});

export type BookSchema = z.infer<typeof bookSchema>;
