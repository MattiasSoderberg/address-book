import React from "react";
import type { Contact } from "~/shared.types";
import { cn } from "~/utils";

export interface Errors extends Omit<Contact, "id"> {
  [key: string]: string | undefined;
}

type Props = {
  label: string;
  type: string;
  name: string;
  defaultValue?: string;
  errors?: Errors;
};

const Input = ({
  label,
  type = "text",
  name,
  defaultValue = "",
  errors,
}: Props) => {
  return (
    <label className="max-w-4/5 grid grid-cols-7 items-center text-lg gap-4 mb-4">
      {label}:{" "}
      <input
        type={type}
        name={name}
        defaultValue={defaultValue}
        className={cn(
          "col-span-3 p-1 border border-gray-300 rounded",
          errors && errors[name] ? "border-red-500" : "",
        )}
      />
      {errors && (
        <p className="col-span-3 text-red-500 text-sm">{errors[name]}</p>
      )}
    </label>
  );
};

export default Input;
