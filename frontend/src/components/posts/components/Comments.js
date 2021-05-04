import React, { useEffect, useState } from "react";
import { Col, Row } from "react-bootstrap";
import facade from '../../../facades/postFacade'
import userFacade from '../../../facades/userFacade'


export default function Comments({ setError, postID }) {

    const [comments, setComments] = useState([])

    useEffect(async () => {
        const data = await facade.getAllCommentsForAPost(data => data, setError, postID)
        if (data) {
            let usernames = []
            data.all.forEach(d => {if(!usernames.includes(d.username)) usernames.push(d.username)})
            const commenters = await userFacade.getUsersByIDs(data => data, setError, usernames)
            setComments([...data.all])
        }
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