import React, { useEffect, useState } from "react";
import { useDropzone } from "react-dropzone";
import type { Contact } from "~/shared.types";
import { cn } from "~/utils";
import Cropper, { type Area, type Point } from "react-easy-crop";

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
    <div className="w-full relative">
      <label className="w-full flex flex-col">
        {label}
        <input
          type={type}
          name={name}
          defaultValue={defaultValue}
          className={cn(
            "p-1 border border-gray-300 rounded",
            errors && errors[name] ? "border-red-500" : "",
          )}
        />
      </label>
      {errors && errors[name] && (
        <p className="absolute -bottom-5 left-0 text-red-400 text-sm">
          {errors[name]}
        </p>
      )}
    </div>
  );
};

export const FileInput = () => {
  const [imageUrl, setImageUrl] = useState("");
  const [error, setError] = useState("");
  const [crop, setCrop] = useState<Point>({ x: 0, y: 0 });
  const [zoom, setZoom] = useState(1);

  const { getRootProps, getInputProps } = useDropzone({
    maxFiles: 1,
    accept: { "image/*": [] },
    onDropAccepted: (acceptedFiles) =>
      setImageUrl(URL.createObjectURL(acceptedFiles[0])),
    onDropRejected: (rejectedFiles) =>
      setError(rejectedFiles[0].errors[0].message),
  });

  const onCropComplete = (croppedArea: Area, croppedAreaPixels: Area) => {
    console.log(croppedArea, croppedAreaPixels);
  };

  useEffect(() => {
    return () => {
      if (imageUrl) {
        URL.revokeObjectURL(imageUrl);
      }
    };
  }, [imageUrl]);

  return (
    <section className="size-full flex flex-col gap-6">
      <label className="">
        Image
        <div className="w-full justify-between relative">
          <div
            {...getRootProps()}
            className="min-h-24 flex justify-center items-center bg-gray-100 border border-gray-300 rounded"
          >
            <input {...getInputProps()} />
            <p>Drop your image file here</p>
            {error && (
              <p className="text-sm text-red-400 absolute -bottom-5 left-0">
                {error}
              </p>
            )}
          </div>
        </div>
      </label>
      {imageUrl && (
        <div className="w-full h-full relative rounded overflow-hidden">
          <Cropper
            image={imageUrl}
            crop={crop}
            zoom={zoom}
            aspect={1 / 1}
            cropShape="round"
            showGrid={false}
            onCropChange={setCrop}
            onCropComplete={onCropComplete}
            onZoomChange={setZoom}
          />
        </div>
      )}
    </section>
  );
};

export const InputColumnWrapper = ({
  children,
  className,
}: {
  children: React.ReactNode;
  className?: string;
}) => {
  return (
    <div className={cn("w-full flex flex-col items-start gap-6", className)}>
      {children}
    </div>
  );
};
