import React, { useState } from "react";
import { Col, Container, Jumbotron, Row } from 'react-bootstrap'
import { Content, Header, UserInfo, Footer, Comments, Tags } from './components/components'

export default function Post({ post, setError, setSelectedTag, isOnProfilePage, setSelectedEditPost }) {

  const { title, content, username, profileImg, id, tags } = post
  const [showComments, setShowComments] = useState(false)

  const changePostToEdit = () => {
    setSelectedEditPost({...post})
  }

  return (
    <Jumbotron>

      <Row>
        <Col md={2} style={{ borderRight: 1 + 'px solid gray' }}><UserInfo name={username} profileImg={profileImg} /></Col>
        <Col >
          <Container fluid className="p-0 m-0">
            <Row><Header text={title} /></Row>
            <Row className="w-100"><Content text={content} /></Row>
            <Row className="w-100"><Tags tags={tags} setSelectedTag={setSelectedTag} /></Row>
          </Container>
          <Row className="pb-2"><Footer setShowComments={setShowComments} onEdit={changePostToEdit} setError={setError} postID={id} isOnProfilePage={isOnProfilePage} /></Row>
        </Col>
      </Row>
      {showComments &&
        <Comments setError={setError} postID={id} isOnProfilePage={isOnProfilePage} />
      }
    </Jumbotron>
  );
}
