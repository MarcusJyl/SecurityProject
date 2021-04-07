import React from "react";


export default function Header({ text }) {

  return (
    <div className="text-center w-100">
      <h1 >{text}</h1>
    </div>
  );
}