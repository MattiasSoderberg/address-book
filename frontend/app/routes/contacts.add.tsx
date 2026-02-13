import { redirect } from "react-router";
import type { Route } from "./+types/contacts.add";
import FormComponent from "~/components/form";
import { parseFormData, type FileUpload } from "@remix-run/form-data-parser";

export function meta({}: Route.MetaArgs) {
  return [
    { title: "Address Book" },
    { name: "description", content: "New contact" },
  ];
}

export async function action({ request }: Route.ActionArgs) {
  const uploadHandler = (fileUpload: FileUpload) => {
    return fileUpload.size > 0 ? fileUpload : null;
  };

  const formData = await parseFormData(request, uploadHandler);

  const body = new FormData();
  body.append(
    "contact",
    new Blob(
      [
        JSON.stringify(
          {
            name: formData.get("name"),
            phone: formData.get("phone"),
            street: formData.get("street"),
            zipCode: formData.get("zipCode"),
            city: formData.get("city"),
          },
          null,
          2,
        ),
      ],
      { type: "application/json" },
    ),
  );

  if (formData.get("image")) {
    body.append("image", formData.get("image") as Blob);
    body.append("cropSize", formData.get("cropSize") as string);
    body.append("cropX", formData.get("cropX") as string);
    body.append("cropY", formData.get("cropY") as string);
  }

  const response = await fetch(`${process.env.BASE_API_URL}/contacts`, {
    method: "POST",
    headers: {
      // "Content-Type": "multipart/form-data",
    },
    body,
  });

  if (!response.ok) {
    const errors = await response.json();
    console.error("Error creating user ");
    return errors;
  }

  const data = await response.json();

  return redirect(`/contacts/${data.id}`);
}

export default function AddContact({ actionData }: Route.ComponentProps) {
  return (
    <div className="size-full flex flex-col gap-10 p-4 pr-10">
      <h2 className="text-2xl">Add Contact</h2>
      <FormComponent
        action="/contacts/new"
        buttonText="Add"
        errors={actionData}
      />
    </div>
  );
}
