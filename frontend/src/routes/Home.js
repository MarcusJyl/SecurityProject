import React, { useState, useEffect } from "react";
import Post from '../components/posts/post'
import AddPost from './AddPost'
import { Col, Row, Modal, Button } from 'react-bootstrap'
import postFacade from '../facades/postFacade'

export default function Home({ setError }) {

  const [posts, setPosts] = useState([])

  useEffect(() => {
    console.log("dasdasas")

    postFacade.fetchData(({ all }) => {
      setPosts([...all.reverse()])
    }, setError)
  }, [])

  return (
    <>
      <Row>
        <Col md={2}></Col>
        <Col>
          {/* hent alle post og insæt dem her */}
          {posts.map((post) => {
            return (
              <Post post={post}/>
            )
          })}
        </Col>
        <Col md={2}></Col>
      </Row>¨
      <Row>
        <Col md={2}></Col>
        <Col>
          <AddPost />
        </Col>
        <Col md={2}></Col>
      </Row>
    </>
  );
}
