import React, { useState } from "react";
import { Col, Jumbotron, Row, Button, Form } from "react-bootstrap";
import { storage } from "../utils/firebase";

export default function Profile() {
  const [options, setOptions] = useState({})
  const [file, setFile] = useState(null);
  const [url, setURL] = useState("");

  function handleChange(e) {
    setFile(e.target.files[0]);
  }

  function handleChangeRange(e, input) {
    let value = e.target.value | input
    setOptions({ range: value })
    // setFile(e.target);
  }

  function handleUpload(e) {
    e.preventDefault();
    const uploadTask = storage.ref(`/images/${file.name}`).put(file);
    uploadTask.on("state_changed", console.log, console.error, () => {
      storage
        .ref("images")
        .child(file.name)
        .getDownloadURL()
        .then((url) => {
          setFile(null);
          setURL(url);
        });
    });
  }

  return (
    <Row className="mt-2">
      <Col md={2}></Col>
      <Col>
        <Jumbotron className="text-center">
          <Form onSubmit={handleUpload}>
            <Form.Group controlId="formBasicEmail">
              <Form.Label>Email address</Form.Label>
              <Form.Control type="email" placeholder="Enter email" />
              <Form.Text className="text-muted">
                We'll never share your email with anyone else.
              </Form.Text>
            </Form.Group>
            <Form.Group controlId="firstName">
              <Form.Label>First name</Form.Label>
              <Form.Control type="text" placeholder="Name" />
            </Form.Group>
            <Form.Group controlId="lastName">
              <Form.Label>Last name</Form.Label>
              <Form.Control type="text" placeholder="Last name" />
            </Form.Group>
            <Form.Group controlId="Postnummer">
              <Form.Label>Last name</Form.Label>
              <Form.Control type="number" placeholder="2880" />
            </Form.Group>
            <Form.Group>
              <Form.File id="exampleFormControlFile1" label="Example file input" onChange={handleChange} />
            </Form.Group>
            <Button variant="primary" type="submit" disabled={!file}>
              Update your info
            </Button>
          </Form>

          {(file != null) && <>
            <img src={URL.createObjectURL(file)} alt="" />
            <input type="range" onChange={handleChangeRange} value={options.range} max={1000} />
            <input type="number" onChange={handleChangeRange} value={options.range} max={1000} />
          </>
          }




          {/* 
          <form onSubmit={handleUpload}>
            <input type="file" onChange={handleChange} />
            <Button disabled={!file}>upload to firebase</Button>
          </form>
          <img src={url} alt="" /> */}
        </Jumbotron>
      </Col>
      <Col md={2}></Col>
    </Row>
  );
}