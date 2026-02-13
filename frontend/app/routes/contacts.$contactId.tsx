import { Form, useLoaderData } from "react-router";
import type { Route } from "./+types/contacts.$contactId";
import type { Contact } from "~/shared.types";
import Button from "~/components/button";
import AppLink from "~/components/AppLink";
import ContactImage from "~/components/contactImage";

export function meta({ params }: Route.MetaArgs) {
  return [
    { title: params.contactId },
    {
      name: "description",
      content: `Contact details for ${params.contactId}`,
    },
  ];
}

export async function loader({ params }: Route.LoaderArgs) {
  const response = await fetch(
    `${process.env.BASE_API_URL}/contacts/${params.contactId}`,
  );
  const contact = (await response.json()) as Contact;

  const imageResponse = await fetch(
    `${process.env.BASE_API_URL}/contacts/${params.contactId}/image`,
  );

  return { contact, imageUrl: imageResponse.url };
}

export default function ContactDetails() {
  const { contact, imageUrl }: { contact: Contact; imageUrl: string } =
    useLoaderData();

  return (
    <div className="w-full flex gap-10">
      <div className="flex flex-col gap-4 p-4">
        <h2 className="text-3xl">{contact.name}</h2>
        <div className="mt-4 text-lg">
          <p className="mb-4">{contact.phone}</p>
          <p>{contact.street}</p>
          <p>
            {contact.zipCode} {contact.city}
          </p>
        </div>
        <div className="flex justify-start items-center gap-4">
          <Form action={`/contacts/${contact.id}/delete`} method="post">
            <Button className="bg-red-400" type="submit">
              Delete
            </Button>
          </Form>
          <AppLink
            to={`/contacts/${contact.id}/edit`}
            state={{ contact }}
            variant="button"
          >
            Edit
          </AppLink>
        </div>
      </div>
      {imageUrl && <ContactImage imageUrl={imageUrl} />}
    </div>
  );
}
