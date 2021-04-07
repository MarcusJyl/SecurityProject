import React from "react";
import {Jumbotron} from 'react-bootstrap'
import {Content, Header, UserInfo} from './post components/components'

export default function Post() {

  return (
    <Jumbotron>
        <Header/>
        <Content/>
        <UserInfo/>
    </Jumbotron>
  );
}