import React, { useEffect, useState } from "react";
import { Modal, Button, Form, Row, Col } from 'react-bootstrap'
import { fetcher, makeOptions } from '../../../utils/fetchUtils'
import { URL as baseURL } from '../../../utils/settings'

export default function Content ({ selectedEditPost, setError }) {

    const [show, setShow] = useState(false);
    const [post, setPost] = useState({})
    const [tag, setTag] = useState("")

    useEffect(() => {
        if (Object.keys(selectedEditPost).length === 0) handleClose()
        else handleShow()
        setPost({ ...selectedEditPost })
    }, [selectedEditPost])

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const removeTag = (tagToRemove) => {
        const tags = post.tags.filter(t => tagToRemove !== t)
        setPost({ ...post, tags })
    }

    const onAddTag = () => {
        setPost({ ...post, tags: [...post.tags, tag] })
        setTag("")
    }

    const onchange = (evt) => {
        if (evt.target.id === "tag") setTag(evt.target.value)
        else setPost({ ...post, [evt.target.id]: evt.target.value })
    }

    const editPost = async(newPost) => {
        console.log("asdasdasdas")
        const test = {...newPost}
        console.log(test)
        const post = await fetcher(baseURL + '/api/post/' , makeOptions('PUT', true, {...newPost}), (data) => data, setError)
        handleClose()
    }

    return (
        <>
            <Modal show={show} onHide={handleClose}>
                <Form onChange={onchange} className="text-center p-1">
                    <Form.Group controlId="title">
                        <Form.Label>Title</Form.Label>
                        <Form.Control type="text" minLength="3" maxLength="40" value={post.title} />
                    </Form.Group>

                    <Form.Group controlId="content">
                        <Form.Label>Content</Form.Label>
                        <Form.Control as="textarea" rows={3} placeholder="Enter you posts text here" maxLength="255" value={post.content} />
                    </Form.Group>

                    <Form.Group controlId="tag">

                        <Form.Label>Tag</Form.Label>
                        {post.tags && <p>{post.tags.map(t => <a href="#" onClick={() => removeTag(t)}>#{t} </a>)}</p>}
                        <Row>
                            <Col><Form.Control type="text" placeholder="Enter tag" minLength="1" maxLength="80" value={tag} /></Col>
                            <Col md={2}><Button className="" onClick={onAddTag}>add</Button></Col>
                        </Row>
                    </Form.Group>

                    <Button className="w-100 btn-secondary" onClick={() => editPost({...post})}>Edit Post</Button>
                </Form>
            </Modal>
        </>
    );
}