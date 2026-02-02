import type { Route } from "./+types/home";
import { SlBookOpen } from "react-icons/sl";
import AppLink from "~/components/AppLink";

export function meta({}: Route.MetaArgs) {
  return [
    { title: "Address Book" },
    { name: "description", content: "Your personal address book" },
  ];
}

export default function Home() {
  return (
    <div className="w-full h-full flex flex-col items-center gap-10 mt-36 overflow-hidden p-4">
      <h2 className="text-3xl">Your Personal Address Book</h2>
      <SlBookOpen className="size-40 text-gray-400" />
      <AppLink to="contacts/new" variant="button" className="text-xl px-10">
        Add a New Contact
      </AppLink>
    </div>
  );
}
