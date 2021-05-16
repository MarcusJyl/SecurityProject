import React, { useEffect, useState } from "react";
import { Col, Jumbotron, Row, Button, Form } from "react-bootstrap";
import { storage } from "../utils/firebase";
import { fetcher, makeOptions } from '../utils/fetchUtils'
import { URL as baseURL } from '../utils/settings'
import Post from '../components/posts/post'
import EditPostModal from '../components/posts/components/EditPostModal'

export default function Profile({ setError }) {
  const [file, setFile] = useState(null);
  const [url, setURL] = useState("");
  const [user, setUser] = useState({})
  const [posts, setPosts] = useState([])
  const [selectedEditPost, setSelectedEditPost] = useState({})


  useEffect(async () => {
    const options = makeOptions('POST', true, { url })
    console.log(baseURL + '/api/user/picture')
    if (url != "") {
      const data = await fetcher(baseURL + '/api/user/picture', options, () => { }, setError)
    }
    const user = await fetcher(baseURL + '/api/user', makeOptions('GET', true), (data) => data, setError)
    if (user) {
      const posts = await fetcher(baseURL + '/api/post/' + user.username, makeOptions('GET', true), (data) => data, setError)
      setUser({ ...user })
      setPosts([...posts.all])
    }

  }, [url])

  function handleChange(e) {
    setFile(e.target.files[0]);
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
    <>
      <EditPostModal selectedEditPost={selectedEditPost} setError={setError}></EditPostModal>
      <Row className="mt-2">
        <Col md={2} className="m-2">
          <Jumbotron className="text-center">
            {(user) &&
              <div>
                <h1>{user.username}</h1>
                {(user.profilePicture) && <img src={user.profilePicture} style={{ width: 80 + '%' }} />}
              </div>
            }
            <Form onSubmit={handleUpload}>
              <Form.Group>
                <Form.File id="exampleFormControlFile1" label="Example file input" onChange={handleChange} />
              </Form.Group>
              <Button variant="primary" type="submit" disabled={!file}>
                Update your info
            </Button>
            </Form>
            {(file != null) && <>
              <img src={URL.createObjectURL(file)} alt="" />
            </>
            }
          </Jumbotron>
        </Col>
        <Col className="m-2">
          <Jumbotron className="text-center">
            {posts.map((post) => {
              return (
                <Post post={post} setError={setError} isOnProfilePage={true} setSelectedEditPost={setSelectedEditPost}/>
              )
            })}
          </Jumbotron>
        </Col>
        <Col md={2} className="m-2"></Col>
      </Row>
    </>
  );
}