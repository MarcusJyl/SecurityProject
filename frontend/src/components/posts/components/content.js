import React from "react";

export default function Content({text}) {

  return (
    <>
    <h3 style={{wordBreak: "break-all"}}>{text}</h3>
    </>
  );
}