import React, { useState, useEffect } from "react";
import Post from '../components/posts/post'

export default function Home() {

  return (
    <div className="text-center w-100">
        <h1>HOME</h1>
        <Post/>
    </div>
  );
}
