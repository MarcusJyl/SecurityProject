import React from "react";

export default function Tags({tags, setSelectedTag}) {

  return (
    <>
    {tags.map(tag => {
        return(
            <a href="#" className="m-1" onClick={() => setSelectedTag(tag)}> #{tag} </a>
        )
    })}
    </>
  );
}