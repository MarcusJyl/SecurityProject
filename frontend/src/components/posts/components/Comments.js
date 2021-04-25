import React from "react";


export default function Footer({ setShowComments }) {

    return (
        // <div className="h-100 w-100" style={{ background: "red" }}>
            <div className="w-100">
                <a href="#" onClick={() => setShowComments(true)} className="ml-2" style={{ float: "left" }}>Comment</a>
                <a href="#" className="mr-2" style={{ float: 'right' }}>Like</a>
            </div>
        // </div>
    )
}