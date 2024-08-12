export type Movie = {
   movieId: number;
   movieTitle: string;
   movieDescription: string;
   movieRate: number;
   isAdultMovie: boolean;
   status: "COMPLETED" | "ONGOING";
};

export type Book = {
   id: number;
   title: string;
   author: string;
   description: string;
};
