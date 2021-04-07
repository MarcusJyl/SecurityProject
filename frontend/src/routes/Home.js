import React, { useState, useEffect } from "react";
import Post from '../components/posts/post'
import AddPost from './AddPost'
import { Col, Row, Modal, Button } from 'react-bootstrap'

export default function Home() {

  return (
    <>
    <Row>
      <Col md={2}></Col>
      <Col>
      {/* hent alle post og insæt dem her */}
      <Post />
      </Col>
      <Col md={2}></Col>
    </Row>¨
    <Row>
      <Col md={2}></Col>
      <Col>
      <AddPost/>
      </Col>
      <Col md={2}></Col>
    </Row>

    </>
  );
}
