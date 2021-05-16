import React, { useEffect, useState } from "react";
import { Col, Row, Button } from "react-bootstrap";
import facade from '../../../facades/postFacade'
import userFacade from '../../../facades/userFacade'
import { fetcher, makeOptions } from '../../../utils/fetchUtils'
import { URL as baseURL } from '../../../utils/settings'

export default function Comments({ setError, postID, isOnProfilePage }) {

    const [comments, setComments] = useState([])

    useEffect(async () => {
        let commentsData = await facade.getAllCommentsForAPost(data => data, setError, postID)
        if (commentsData) {
            let usernames = []
            commentsData.all.forEach(d => { if (!usernames.includes(d.username)) usernames.push(d.username) })
            if (usernames.length > 0) {
                let users = await userFacade.getUsersByIDs(data => data, setError, usernames)
                console.log(usernames)
                commentsData = commentsData.all.map(comment => { return { ...comment, user: users.all.find(e => e.username === comment.username) } })

                setComments([...commentsData])
            }
        }
    }, [])

    const deleteComment = async (commentID) => {
        const comment = await fetcher(baseURL + '/api/comment/' + commentID, makeOptions('DELETE', true), (data) => data, setError)
    }

    return (
        <Row style={{ borderTop: 1 + 'px solid black' }}>

            {comments.map(c => {
                return (
                    <Row className="w-100 text-center" style={{ ...centerAlign, borderBottom: 1 + 'px solid black' }}>
                        <Col md={2}>
                            <Row style={centerAlign}>
                                <Col><img src={c.user.profilePicture} width="95%" style={{ maxHeight: 100 + '%' }}></img></Col>
                                <Col><p>{c.user.username}</p></Col>
                            </Row>
                        </Col>
                        <Col>
                            <p >{c.text}</p>
                        </Col>
                        {(isOnProfilePage) &&
                            <>
                                <Button className="btn-warning mr-1" onClick={() => deleteComment(c.id)}>Block</Button>
                            </>
                        }
                    </Row>
                )
            })}

        </Row>
    )
}

const centerAlign = {
    display: 'flex',
    alignItems: 'center'
}