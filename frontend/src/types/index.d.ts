import { string } from "zod"
import {ZodIssue} from "zod";


type ActionResponses<T> = 
  | {status: "succes", data: T} 
  | {status: "error", error: string | ZodIssue[]};
