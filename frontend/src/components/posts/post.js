import React from "react";
import { Col, Container, Jumbotron, Row } from 'react-bootstrap'
import { Content, Header, UserInfo, Footer } from './components/components'

export default function Post({ post }) {

  const { title, content, username, profileImg } = post

  return (
    <Jumbotron>
      <Row>
        <Col md={2} style={{ borderRight: 5 + 'px solid red' }}><UserInfo name={username} profileImg={profileImg} /></Col>
        <Col >
          <Container fluid className="p-0 m-0">
            <Row style={{ borderBottom: 5 + 'px solid red' }}><Header text={title} /></Row>
            <Row className="w-100"><Content text={content} /></Row>
          </Container>
          <Row className="pb-2"><Footer /></Row>
        </Col>
      </Row>
    </Jumbotron>
  );
}
