import { bookFindAllAction } from "@/actions/bookActions";
import TableOfBook from "@/components/book/TableOfBook";

export default async function Home() {
   const book = await bookFindAllAction();

   return (
      <div className="grid grid-cols-12 container mx-auto m-12 items-center">
         <h1 className="m-12 container mx-auto col-span-3">
            NEXT JS WITH SPRING BOT
         </h1>
         <div className=" col-span-9">
            <TableOfBook book={book} />
         </div>
      </div>
   );
}
