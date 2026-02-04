import React from "react";
import Dropzone from "react-dropzone";
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

export const TextInput = ({
  label,
  type = "text",
  name,
  defaultValue = "",
  errors,
}: Props) => {
  return (
    <label className="max-w-4/5 grid grid-cols-7 items-end text-lg gap-4 mb-4">
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

export const FileInput = () => {
  return (
    <Dropzone onDrop={(acceptedFiles) => console.log(acceptedFiles)}>
      {({ getRootProps, getInputProps }) => (
        <section className="w-full flex flex-col gap-4 mt-10">
          <h3 className="text-lg">Image</h3>
          <div className="max-w-4/5 grid grid-cols-7">
            <div
              {...getRootProps()}
              className="col-span-4 h-[70px] flex justify-center items-center bg-gray-100 border border-gray-300 rounded-xl"
            >
              <input {...getInputProps()} />
              <p>Drop your image file here</p>
            </div>
          </div>
        </section>
      )}
    </Dropzone>
  );
};
