import React, { useState, useEffect } from "react";
import Post from '../components/posts/post'
import AddPost from './AddPost'
import { Col, Row, Modal, Button } from 'react-bootstrap'
import postFacade from '../facades/postFacade'

export default function Home({ setError, user }) {

  const [title, setTitle] = useState("")
  const [posts, setPosts] = useState([])
  const [selectedTag, setSelectedTag] = useState("")

  useEffect(() => {
    postFacade.getAllPosts(({ all }) => {
      setPosts([...all.reverse()])
    }, setError)
  }, [])

  useEffect(() => {
    if(selectedTag === ""){
      postFacade.getAllPosts(({ all }) => { setPosts([...all.reverse()])}, setError)
    } else {
      postFacade.getAllPostsWithTags(({ all }) => setPosts([...all.reverse()]), selectedTag, setError)
    }
    setTitle(selectedTag)
  }, [selectedTag])

  return (
    <>

      <Row className="text-center p-4">
        <Col md={2}></Col>
        <Col className="justify-content-md-center">
          {title !== "" &&
            <>
              <h1>tags selected:</h1>
              <h2>{selectedTag}</h2>
              <Button className="btn-danger m-1" onClick={() => setSelectedTag("")}>Reset tags</Button><br/>
            </>}
          <AddPost setError={setError} user={user}/>
        </Col>
        <Col md={2}></Col>
      </Row>
      <Row>
        <Col md={2}></Col>
        <Col>
          {/* hent alle post og insæt dem her */}
          {posts.map((post) => {
            return (
              <Post post={post} setError={setError} setSelectedTag={setSelectedTag} />
            )
          })}
        </Col>
        <Col md={2}></Col>
      </Row>

    </>
  );


}
