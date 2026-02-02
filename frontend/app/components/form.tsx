import React from "react";
import { Form } from "react-router";
import Input, { type Errors } from "./input";
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
    <Form className="" action={action} method="post">
      <Input
        label="Name"
        type="text"
        name="name"
        defaultValue={contact?.name}
        errors={errors}
      />
      <Input
        label="Phone"
        type="text"
        name="phone"
        defaultValue={contact?.phone}
        errors={errors}
      />
      <Input
        label="Street"
        type="text"
        name="street"
        defaultValue={contact?.street}
        errors={errors}
      />
      <Input
        label="Zip Code"
        type="text"
        name="zipCode"
        defaultValue={contact?.zipCode}
        errors={errors}
      />
      <Input
        label="City"
        type="text"
        name="city"
        defaultValue={contact?.city}
        errors={errors}
      />
      <Button type="submit" className="mt-6 px-8">
        {buttonText}
      </Button>
    </Form>
  );
};

export default FormComponent;
