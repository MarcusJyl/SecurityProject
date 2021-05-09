import React, { useEffect, useState } from "react";
import { Col, Row } from "react-bootstrap";
import facade from '../../../facades/postFacade'
import userFacade from '../../../facades/userFacade'


export default function Comments({ setError, postID }) {

    const [comments, setComments] = useState([])

    useEffect(async () => {
        let commentsData = await facade.getAllCommentsForAPost(data => data, setError, postID)
        if (commentsData) {
            let usernames = []
            commentsData.all.forEach(d => { if (!usernames.includes(d.username)) usernames.push(d.username) })
            let users = await userFacade.getUsersByIDs(data => data, setError, usernames)
            console.log(users)
            commentsData = commentsData.all.map(comment => { return { ...comment, user: users.all.find(e => e.username === comment.username) } })

            setComments([...commentsData])
        }
    }, [])

    return (
        <Row style={{ borderTop: 1 + 'px solid black' }}>

            {comments.map(c => {
                return (
                    <Row className="w-100 mt-2" style={{ borderBottom: 1 + 'px solid black' }}>
                        <Col md={2}>
                            <Row>
                                <Col><img src={c.user.profilePicture} width="95%"></img></Col>
                                <Col><p>{c.user.username}</p></Col>
                            </Row>
                        </Col>
                        <Col>
                            <p >{c.text}</p>

                        </Col>
                    </Row>
                )
            })}
        </Row>
    )
}