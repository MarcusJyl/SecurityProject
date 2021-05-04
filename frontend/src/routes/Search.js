import React, { useState, useEffect } from "react";
import Post from '../components/posts/post'
import AddPost from './AddPost'
import { Col, Row, Modal, Button } from 'react-bootstrap'
import facade from '../facades/searchFacade'

export default function Search({ setError, searchInput }) {

  const [searchResult, setSearchResult] = useState({})

  useEffect(() => {
      console.log(searchInput)
    facade.search((data)=>{
        setSearchResult({...data})
    }, setError, searchInput) 
  }, [])

  

  return (
    <>
      <Row>
          <Col md={1}></Col>
          <Col>
            {searchResult.usersDTO && searchResult.usersDTO.all.map(user=>{
                return(<p>{user.username}</p>)
            })}
          </Col>
          <Col>
          {searchResult.postsDTO && searchResult.postsDTO.all.map(post=>{
            return(<p>{post.content}</p>)
            })}
            </Col>
          <Col md={1}></Col>
      </Row>

    </>
  );


}
