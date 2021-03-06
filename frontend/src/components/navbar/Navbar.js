import React, { useState } from "react";
import { Link } from "react-router-dom";
import { Navbar, Nav, Form, FormGroup, Button } from "react-bootstrap";



function NavbarShow({ user, logout, setSearchInput, search }) {

 

  function onChange(event) {
    setSearchInput(event.target.value)
  }


  return (
    <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark">
      <Link to="/" className="navbar-brand">
        BANDEN <i className="fab fa-typo3" />
      </Link>
      <Navbar.Toggle aria-controls="responsive-navbar-nav" />
      <Navbar.Collapse id="responsive-navbar-nav">
        <Nav className="mr-auto">
          {user.username && user.username !== "" &&
            <Link to="/profile" className="nav-link">
              Profile
            </Link>
          }
          {user.roles && user.roles.includes("admin") &&
            <Link to="/admin" className="nav-link">
              Admin
            </Link>
          }

        </Nav>
        
        <input id="searchInput" type="text" placeholder="search" onChange={onChange} onKeyDown={evt => { if (evt.which == 13 || evt.keyCode == 13) search() }}></input>
        <Link to="/search">
          <button onClick={search} className="btn btn-primary mr-1 ml-1" id="searchButton">Search</button>
        </Link>
        <Nav>
          {user.username !== "" ? (
            <>
              <h5 className="mr-2 text-light">{user.username}</h5>
              <button className="btn btn-danger" onClick={logout}>
                Logout
              </button>
            </>
          ) : (
            <Link to="/signin">
              <button className="btn btn-primary">Sign In</button>
            </Link>
          )}
        </Nav>
      </Navbar.Collapse>
    </Navbar>
  );
}
export default NavbarShow;
