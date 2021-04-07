import React from "react";
import { Col, Container, Jumbotron, Row } from 'react-bootstrap'
import { Content, Header, UserInfo } from './components/components'

export default function Post({post}) {

  const {title, content, username} = post

  return (
    <Jumbotron>
      <Row>
        <Col md={2} style={{ borderRight: 5 + 'px solid red' }}><UserInfo name={username} /></Col>
        <Col>
          <Container fluid className="p-0 m-0">
            <Row style={{ borderBottom: 5 + 'px solid red' }}><Header text={title} /></Row>
            <Row><Content text={content} /></Row>
          </Container>
        </Col>
      </Row>
    </Jumbotron>
  ); 
}
