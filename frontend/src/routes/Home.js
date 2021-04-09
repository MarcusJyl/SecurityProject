import React, { useState, useEffect } from "react";
import Post from '../components/posts/post'
import AddPost from './AddPost'
import { Col, Row, Modal, Button } from 'react-bootstrap'
import postFacade from '../facades/postFacade'

export default function Home({ setError }) {

  const [posts, setPosts] = useState([])

  useEffect(() => {
    console.log("dasdasas")

    postFacade.getAllPosts(({ all }) => {
      setPosts([...all.reverse()])
    }, setError)
  }, [])

  const fileSelectedHandler = evt => {
    console.log(evt.target.files[0].name)
  }

  return (
    <>
      <Row className="text-center p-4">
        <Col md={2}></Col>
        <Col className="justify-content-md-center">
          <AddPost setError={setError} />
          <br></br>
          <input type="file" onChange={fileSelectedHandler} />
        </Col>
        <Col md={2}></Col>
      </Row>
      <Row>
        <Col md={2}></Col>
        <Col>
          {/* hent alle post og insæt dem her */}
          {posts.map((post) => {
            return (
              <Post post={post} />
            )
          })}
        </Col>
        <Col md={2}></Col>
      </Row>¨

    </>
  );


}
