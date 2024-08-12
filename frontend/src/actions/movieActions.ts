"use server"

import { ActionResponses } from "@/types";
import { Book, Movie } from "@/types/movie.type";

export async function movieFindAllAction(): Promise<ActionResponses<Movie[]>> {
  const urlbackend = process.env.NEXT_URL_BACKEND_JAVA as string;
  try {
    const res = await fetch(`${urlbackend}/api/movie`);
    const movie = await res.json();

    if(!movie) {
      throw new Error("movie can't not found");
    }

    return {status: "succes", data: movie}

  } catch (error: any) {
    return {status: "error", error: error.message};
  }
}
