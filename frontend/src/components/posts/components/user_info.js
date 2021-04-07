import React from "react";
import { Row } from "react-bootstrap";
import profile from '../../../utils/images/profile.png'

export default function UserInfo({name}) {

  return (
    <>
    <Row className="justify-content-md-center">
    <img src={profile} width="50%"/>
    </Row>
    <Row className="justify-content-md-center">
      <h5>{name}</h5>
    </Row>
    </>
  );
}