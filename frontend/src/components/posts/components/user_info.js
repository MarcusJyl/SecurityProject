import React from "react";
import { Row } from "react-bootstrap";
import profile from '../../../utils/images/profile.png'

export default function UserInfo({name, profileImg}) {

  return (
    <>
    <Row className="justify-content-md-center">
    <img src={profileImg} width="50%" style={{borderRadius: 50}}/>
    </Row>
    <Row className="justify-content-md-center">
      <h5>{name}</h5>
    </Row>
    </>
  );
}