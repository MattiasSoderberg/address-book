import { type RouteConfig, index, route } from "@react-router/dev/routes";

export default [
  index("routes/home.tsx"),
  route("contacts/new", "routes/contacts.add.tsx"),
  route("contacts/:contactName", "routes/contacts.$contactName.tsx"),
  route("contacts/:contactId/edit", "routes/contacts.$contactId.edit.tsx"),
  route("contacts/:contactId/delete", "routes/contacts.$contactId.delete.tsx"),
] satisfies RouteConfig;
