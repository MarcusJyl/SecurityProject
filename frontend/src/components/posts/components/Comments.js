import React, { useEffect, useState } from "react";
import facade from '../../../facades/postFacade'

export default function Comments({ setError, postID }) {

    const [comments, setComments] = useState([])

    useEffect(async() => {
        let data = await facade.getAllCommentsForAPost(data => data, setError, postID)
        setComments([...data.all])
    }, [])

    return (
        <>
        {comments.map(c => {
            return(
                <p>{c.text}</p>
            )
        })}
        </>
    )
}