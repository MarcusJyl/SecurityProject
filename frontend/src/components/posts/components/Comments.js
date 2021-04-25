import React, { useEffect, useState } from "react";
import { Col, Row } from "react-bootstrap";
import facade from '../../../facades/postFacade'

export default function Comments({ setError, postID }) {

    const [comments, setComments] = useState([])

    useEffect(async () => {
        let data = await facade.getAllCommentsForAPost(data => data, setError, postID)
        setComments([...data.all])
    }, [])

    return (
        <Row style={{ borderTop: 1 + 'px solid black' }}>

            {comments.map(c => {
                return (
                    <Row className="w-100 mt-2">
                        <Col md={2}></Col>
                        <Col>
                            <p style={{ border: 1 + 'px solid black' }}>{c.text}</p>

                        </Col>
                    </Row>
                )
            })}
        </Row>
    )
}