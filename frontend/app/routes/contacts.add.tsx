import { redirect } from "react-router";
import type { Route } from "./+types/contacts.add";
import FormComponent from "~/components/form";

export function meta({}: Route.MetaArgs) {
  return [
    { title: "New React Router App" },
    { name: "description", content: "Welcome to React Router!" },
  ];
}

export async function action({ request }: Route.ActionArgs) {
  const formData = await request.formData();
  const newContact = {
    id: crypto.randomUUID(),
    name: formData.get("name"),
    phone: formData.get("phone"),
    street: formData.get("street"),
    zipCode: formData.get("zipCode"),
    city: formData.get("city"),
  };

  const response = await fetch(`${process.env.BASE_API_URL}/addresses`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(newContact),
  });

  if (!response.ok) {
    const errors = await response.json();
    return errors;
  }
  return redirect(`/contacts/${newContact.name}`);
}

export default function AddContact({ actionData }: Route.ComponentProps) {
  return (
    <div className="size-full flex flex-col gap-10 p-4">
      <h2 className="text-2xl">Add Contact</h2>
      <FormComponent
        action="/contacts/new"
        buttonText="Add"
        errors={actionData}
      />
    </div>
  );
}
