import {
  isRouteErrorResponse,
  Links,
  Meta,
  Outlet,
  Scripts,
  ScrollRestoration,
  useLoaderData,
} from "react-router";

import type { Route } from "./+types/root";
import "./app.css";
import type { Contact } from "./shared.types";
import { IoAddOutline } from "react-icons/io5";
import AppLink from "./components/AppLink";

export async function loader() {
  const response = await fetch(`${process.env.BASE_API_URL}/contacts`);
  const data = (await response.json()) as Contact[];

  return data;
}

export function Layout({ children }: { children: React.ReactNode }) {
  const data = useLoaderData<typeof loader>();
  return (
    <html lang="en">
      <head>
        <meta charSet="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <Meta />
        <Links />
      </head>
      <body>
        <div className="w-screen h-screen overflow-hidden relative">
          <header className="w-full h-20 p-4 bg-gray-600 flex items-center text-white absolute top-0 inset-x-0">
            <AppLink to="/">
              <h1 className="w-full text-3xl font-bold">Address Book</h1>
            </AppLink>
          </header>
          <div className="w-full h-full flex pt-20">
            <aside className="min-w-[300px] h-full bg-gray-100 border-r border-gray-300 relative">
              <div className="flex justify-between items-center border-b border-gray-300 p-4">
                <h2 className="text-2xl">Contacts</h2>
                <AppLink to="contacts/new" variant="button" className="p-1">
                  <IoAddOutline className="size-6" />
                </AppLink>
              </div>
              <ul className="w-full h-full flex flex-col items-start gap-2 overflow-y-auto mt-4 p-4">
                {data?.length > 0 ? (
                  data.map((contact) => (
                    <li key={contact.id}>
                      <AppLink
                        to={`contacts/${contact.id}`}
                        className="text-lg hover:opacity-70 hover:underline"
                      >
                        {contact.name}
                      </AppLink>
                    </li>
                  ))
                ) : (
                  <p>No contacts yet</p>
                )}
              </ul>
            </aside>
            <main className="size-full">{children}</main>
          </div>
        </div>
        <ScrollRestoration />
        <Scripts />
      </body>
    </html>
  );
}

export default function App() {
  return <Outlet />;
}

export function ErrorBoundary({ error }: Route.ErrorBoundaryProps) {
  let message = "Oops!";
  let details = "An unexpected error occurred.";
  let stack: string | undefined;

  if (isRouteErrorResponse(error)) {
    message = error.status === 404 ? "404" : "Error";
    details =
      error.status === 404
        ? "The requested page could not be found."
        : error.statusText || details;
  } else if (import.meta.env.DEV && error && error instanceof Error) {
    details = error.message;
    stack = error.stack;
  }

  return (
    <main className="pt-16 p-4 container mx-auto">
      <h1>{message}</h1>
      <p>{details}</p>
      {stack && (
        <pre className="w-full p-4 overflow-x-auto">
          <code>{stack}</code>
        </pre>
      )}
    </main>
  );
}

export function HydrateFallback() {
  return (
    <div id="loading-splash">
      <div id="loading-splash-spinner" />
      <p>Loading, please wait...</p>
    </div>
  );
}
