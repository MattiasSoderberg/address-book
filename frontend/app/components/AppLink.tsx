import React from "react";
import { Link } from "react-router";
import { cn } from "~/utils";

type Variant = "default" | "button";

interface Props extends React.ComponentProps<"a"> {
  to: string;
  variant?: Variant;
  state?: any;
}

const AppLink = ({
  to,
  children,
  className,
  variant = "default",
  ...props
}: Props) => {
  const variantClasses = {
    default: "hover:underline hover:opacity-70",
    button:
      "bg-blue-400 text-white px-4 py-2 rounded-xl inline-block hover:opacity-70",
  };

  return (
    <Link to={to} className={cn(variantClasses[variant], className)} {...props}>
      {children}
    </Link>
  );
};

export default AppLink;
