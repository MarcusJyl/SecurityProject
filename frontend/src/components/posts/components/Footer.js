import React from "react";


export default function Footer({ text }) {

    return (
        // <div className="h-100 w-100" style={{ background: "red" }}>
            <div className="w-100">
                <a href="#" className="ml-2" style={{ float: "left" }}>Comments</a>
                <a href="#" className="mr-2" style={{ float: 'right' }}>Like</a>
            </div>
        // </div>
    );
}

