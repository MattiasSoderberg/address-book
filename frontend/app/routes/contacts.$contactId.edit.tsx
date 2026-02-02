import { redirect, useLoaderData, useLocation } from "react-router";
import type { Route } from "./+types/contacts.$contactId.edit";
import FormComponent from "~/components/form";

export async function action({ params, request }: Route.ActionArgs) {
  const formData = await request.formData();
  const updatedContact = {
    id: params.contactId,
    name: formData.get("name"),
    phone: formData.get("phone"),
    street: formData.get("street"),
    zipCode: formData.get("zipCode"),
    city: formData.get("city"),
  };

  await fetch(`${process.env.BASE_API_URL}/addresses/${params.contactId}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(updatedContact),
  });

  return redirect(`/contacts/${updatedContact.name}`);
}

export default function EditContact() {
  const location = useLocation();
  return (
    <div className="p-4">
      <h2 className="text-2xl">Edit Contact</h2>
      <FormComponent
        action={`/contacts/${location.state?.contact.id}/edit`}
        contact={location.state?.contact}
      />
    </div>
  );
}
