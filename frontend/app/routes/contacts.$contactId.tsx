import { Form, Link, useLoaderData } from "react-router";
import type { Route } from "./+types/contacts.$contactId";
import type { Contact } from "~/shared.types";
import Button from "~/components/button";
import AppLink from "~/components/AppLink";

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
  const data = (await response.json()) as Contact;

  return data;
}

export default function ContactDetails() {
  let data = useLoaderData();
  return (
    <div className="flex flex-col gap-4 p-4">
      <h2 className="text-3xl">{data.name}</h2>
      <div className="mt-4 text-lg">
        <p className="mb-4">{data.phone}</p>
        <p>{data.street}</p>
        <p>
          {data.zipCode} {data.city}
        </p>
      </div>
      <div className="flex justify-start items-center gap-4">
        <Form action={`/contacts/${data.id}/delete`} method="post">
          <Button className="bg-red-400" type="submit">
            Delete
          </Button>
        </Form>
        <AppLink
          to={`/contacts/${data.id}/edit`}
          state={{ contact: data }}
          variant="button"
        >
          Edit
        </AppLink>
      </div>
    </div>
  );
}
