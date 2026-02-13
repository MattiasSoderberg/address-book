import React from "react";

type Props = {
  imageUrl: string;
};

const ContactImage = ({ imageUrl }: Props) => {
  return (
    <div className="flex min-w-25 max-w-50 min-h-25 max-h-50 border border-gray-600 rounded-full overflow-hidden">
      <img src={imageUrl} alt="" />
    </div>
  );
};

export default ContactImage;
