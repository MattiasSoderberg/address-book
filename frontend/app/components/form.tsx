import React from "react";
import { Form } from "react-router";
import { TextInput, FileInput, type Errors, InputColumnWrapper } from "./input";
import Button from "./button";
import type { Contact } from "~/shared.types";

type Props = {
  action: "/contacts/new" | `/contacts/${string}/edit`;
  contact?: Contact;
  buttonText?: string;
  errors?: Errors;
};

const FormComponent = ({
  action,
  contact,
  buttonText = "Submit",
  errors,
}: Props) => {
  return (
    <Form
      className="flex justify-between gap-4 overflow-y-auto"
      action={action}
      method="post"
    >
      <InputColumnWrapper>
        <TextInput
          label="Name"
          type="text"
          name="name"
          defaultValue={contact?.name}
          errors={errors}
        />
        <TextInput
          label="Phone"
          type="text"
          name="phone"
          defaultValue={contact?.phone}
          errors={errors}
        />
        <TextInput
          label="Street"
          type="text"
          name="street"
          defaultValue={contact?.street}
          errors={errors}
        />
        <TextInput
          label="Zip Code"
          type="text"
          name="zipCode"
          defaultValue={contact?.zipCode}
          errors={errors}
        />
        <TextInput
          label="City"
          type="text"
          name="city"
          defaultValue={contact?.city}
          errors={errors}
        />
        <Button type="submit" className="mt-6 px-8">
          {buttonText}
        </Button>
      </InputColumnWrapper>
      <FileInput />
    </Form>
  );
};

export default FormComponent;
