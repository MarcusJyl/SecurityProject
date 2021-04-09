import { Col, Row, Modal, Button, Form } from 'react-bootstrap'
import React, { useState, useEffect } from "react";
import postFacade from '../facades/postFacade'


export default function AddPost({setError}) {
  const init = {title: "", content: ""}

  const [show, setShow] = useState(false);
  const [post, setPost] = useState({...init})

  const handleShow = () => setShow(true);
  const handleClose = () => {
    setPost({...init})
    setShow(false);
  }

  const send = () => {
    postFacade.addPost((data) => console.log(data), setError, post)
    console.log(post)
    handleClose()
  }

  const onchange = (evt) => {
    setPost({...post, [evt.target.id]: evt.target.value})
    console.log(evt.target.value)
  }

  return (
    <>
      <Button variant="primary" onClick={handleShow}>
        Make New POST IDK XDDD FUN HAHAHHAHAHAHA
      </Button>

      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Your POST</Modal.Title>
        </Modal.Header>
        <Modal.Body>

          <Form onChange={onchange}>
            <Form.Group controlId="title">
              <Form.Label>Title</Form.Label>
              <Form.Control type="text" placeholder="Enter title" minLength="3" maxLength="40"/>
              <Form.Text className="text-muted">
                Ja du er Gay
              </Form.Text>
            </Form.Group>

            <Form.Group controlId="content">
              <Form.Label>Content</Form.Label>
              <Form.Control as="textarea" rows={3} placeholder="Enter you posts text here" maxLength="255"/>
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
