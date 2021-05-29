import { Col, Row, Modal, Button, Form } from 'react-bootstrap'
import React, { useState, useEffect } from "react";
import postFacade from '../facades/postFacade'


export default function AddPost({ setError, user }) {
  const init = { title: "", content: "", tag: [] }

  const [show, setShow] = useState(false);
  const [post, setPost] = useState({ ...init })
  const [tag, setTag] = useState("")

  const handleShow = () => setShow(true);
  const handleClose = () => {
    setPost({ ...init })
    setShow(false);
  }

  const send = () => {
    postFacade.addPost((data) => console.log(data), setError, post)
    console.log(post)
    handleClose()
  }

  const onchange = (evt) => {
    if (evt.target.id === "tag") {
      setTag(evt.target.value)
    }
    else {
      setPost({ ...post, [evt.target.id]: evt.target.value })
    }
  }

  const onAddTag = () => {
    setPost({ ...post, tag: [...post.tag, tag] })
    setTag("")
  }

  return (
    <>
      {user && 
      <Button variant="primary" onClick={handleShow}>
        Add new post
      </Button>}


      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Your POST</Modal.Title>
        </Modal.Header>
        <Modal.Body>

          <Form onChange={onchange}>
            <Form.Group controlId="title">
              <Form.Label>Title</Form.Label>
              <Form.Control type="text" placeholder="Enter title" minLength="3" maxLength="40" />
            </Form.Group>

            <Form.Group controlId="content">
              <Form.Label>Content</Form.Label>
              <Form.Control as="textarea" rows={3} placeholder="Enter you posts text here" maxLength="255" />
            </Form.Group>

            <Form.Group controlId="tag">
              <Form.Label>Tag</Form.Label>
              <p>{post.tag.map(t => <>#{t} </>)}</p>
              <Form.Control type="text" placeholder="Enter tag" minLength="1" maxLength="80" value={tag} />
              <Button onClick={onAddTag}>add</Button>
            </Form.Group>
            {/* <Form.Group controlId="formBasicCheckbox">
              <Form.Check type="checkbox" label="Check me out" />
            </Form.Group> */}
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Close
          </Button>
          <Button variant="primary" onClick={send}>
            Send
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
}
