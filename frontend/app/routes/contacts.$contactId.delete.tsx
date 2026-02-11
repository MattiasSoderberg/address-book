import { redirect } from "react-router";
import type { Route } from "./+types/contacts.$contactId.delete";

export async function action({ params }: Route.ActionArgs) {
  await fetch(`${process.env.BASE_API_URL}/contacts/${params.contactId}`, {
    method: "DELETE",
  });

  return redirect("/");
}
