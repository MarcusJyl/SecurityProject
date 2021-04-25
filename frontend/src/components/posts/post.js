import React, { useState } from "react";
import { Col, Container, Jumbotron, Row } from 'react-bootstrap'
import { Content, Header, UserInfo, Footer, Comments } from './components/components'

export default function Post({ post, setError }) {
 
  const { title, content, username, profileImg, id } = post
  const [showComments, setShowComments] = useState(false)

  return (
    <Jumbotron>
      <Row>
        <Col md={2} style={{ borderRight: 1 + 'px solid gray' }}><UserInfo name={username} profileImg={profileImg} /></Col>
        <Col >
          <Container fluid className="p-0 m-0">
            <Row><Header text={title} /></Row>
            <Row className="w-100"><Content text={content} /></Row>
          </Container>
          <Row className="pb-2"><Footer setShowComments={setShowComments} setError={setError} postID={id}/></Row>
        </Col>
      </Row>
      {showComments && 
      <Comments setError={setError} postID={id}/>
      }
    </Jumbotron>
  );
}
