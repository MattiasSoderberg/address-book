import React from "react";

type Props = {
  imageUrl: string;
};

const ContactImage = ({ imageUrl }: Props) => {
  return (
    <div className="w-[200px] h-[200px] border border-gray-600 rounded-full overflow-hidden">
      <img src={imageUrl} alt="" />
    </div>
  );
};

export default ContactImage;
