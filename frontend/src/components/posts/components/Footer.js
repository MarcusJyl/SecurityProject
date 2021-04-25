import React, { useState } from "react";
import { Row, InputGroup, Button, FormControl } from "react-bootstrap";
import facade from '../../../facades/postFacade'


export default function Footer({ setShowComments, setError, postID }) {

    const [comment, setComment] = useState("")
    const [showAddComment, setShowAddComment] = useState(false)

    const addComment = async(evt) => {
        evt.preventDefault()
        const body = {
            post: postID,
            text: comment
        }
        await facade.addComment(data => data, setError, body )
    }

    const onChangeHandler = (evt) => {
        const value = evt.target.value
        setComment(value)
    }

    return (
        <>
            <div className="w-100 text-center">
                <a href="#" onClick={(evt) => { evt.preventDefault(); setShowComments(true) }} className="ml-2" style={{ float: "left" }}>Show comments</a>
                <a href="#" className="mr-2" onClick={(evt) => { evt.preventDefault(); setShowAddComment(true) }}>Comment</a>
                <a href="#" className="mr-2" style={{ float: 'right' }}>Like</a>
            </div>
            {showAddComment &&
                <Row className="w-100 p-1">
                    <InputGroup className="mb-3">
                        <FormControl aria-describedby="basic-addon1" value={comment} onChange={onChangeHandler}/>

                        <InputGroup.Prepend>
                            <Button variant="outline-primary" onClick={addComment}>Send</Button>
                        </InputGroup.Prepend>
                    </InputGroup>
                </Row>
            }

        </>
    );
}

