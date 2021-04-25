import React from "react";


export default function Header({ text }) {

  return (
    <div className="text-center w-100">
      <h1 >{text}</h1>
      <hr style={{backgroundColor: 'black', width: 95+'%'}}></hr>
    </div>
  );
}