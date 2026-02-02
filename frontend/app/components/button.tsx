import React from "react";
import { cn } from "~/utils";

interface Props extends React.ComponentProps<"button"> {
  children: React.ReactNode;
  type?: "button" | "submit" | "reset";
  onClick?: () => void;
}

const Button = ({ children, type = "button", onClick, className }: Props) => {
  return (
    <button
      className={cn(
        "bg-blue-500 text-white px-4 py-2 rounded-xl cursor-pointer hover:opacity-70",
        className,
      )}
      onClick={onClick}
      type={type}
    >
      {children}
    </button>
  );
};

export default Button;
