// import { post } from "jquery";
import { useState } from "react";
import { Form, Button } from "react-bootstrap";
import postFacade from "../facades/postFacade"
import userFacade from "../facades/userFacade"
import { handleHttpErrors } from "../utils/fetchUtils";

export default function Admin({setError}){
    
    const[userID ,setUserID] = useState("")
    const[postID, setPostID] = useState("")

    const onChangePost = (evt) =>{
        setPostID(evt.target.value)
    }

    const onChangeUser = (evt) =>{
        setUserID(evt.target.value)
    }

    const deletePost = () =>{
        postFacade.deletePost((data) => {}, setError, postID)
    }

    const deleteUser = () =>{
        userFacade.deleteUser((data) =>{}, setError, userID)
    }


    return(
        <div>
            <Form.Group onChange={onChangePost}>
                <Form.Label for="PostID">Post to be deleted</Form.Label>
                <Form.Control type="text" id="PostID" ></Form.Control>
                <Button type="button" onClick={deletePost}>Delete Post</Button>
            </Form.Group>
            <Form.Group onChange={onChangeUser}>
                <Form.Label for="UserID">User to be deleted</Form.Label>
                <Form.Control type="text" id="UserID" ></Form.Control>
                <Button type="button" onClick={deleteUser}>Delete User</Button>
            </Form.Group>
        
        
        </div>
    )
}