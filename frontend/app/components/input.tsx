import React, { useCallback, useEffect, useState } from "react";
import Dropzone, { useDropzone, type FileRejection } from "react-dropzone";
import type { Contact } from "~/shared.types";
import { cn } from "~/utils";
import ContactImage from "./contactImage";

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
  const [imageUrl, setImageUrl] = useState("");
  const [error, setError] = useState("");
  const { getRootProps, getInputProps } = useDropzone({
    maxFiles: 1,
    accept: { "image/*": [] },
    onDropAccepted: (acceptedFiles) =>
      setImageUrl(URL.createObjectURL(acceptedFiles[0])),
    onDropRejected: (rejectedFiles) =>
      setError(rejectedFiles[0].errors[0].message),
  });

  useEffect(() => {
    return () => {
      if (imageUrl) {
        URL.revokeObjectURL(imageUrl);
      }
    };
  }, [imageUrl]);

  return (
    <section className="w-full flex flex-col gap-4 mt-10">
      <h3 className="text-lg">Image</h3>
      <div className="w-full max-w-4/5 flex items-center">
        <div className="w-full justify-between">
          <div
            {...getRootProps()}
            className="max-w-[400px] h-[70px] flex justify-center items-center bg-gray-100 border border-gray-300 rounded-xl"
          >
            <input {...getInputProps()} />
            <p>Drop your image file here</p>
          </div>
        </div>
        {imageUrl && <ContactImage imageUrl={imageUrl} />}
      </div>
      {error && <p className="text-sm text-red-500">{error}</p>}
    </section>
  );
};
