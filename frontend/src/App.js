import React, { useEffect, useState } from "react";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import { Container } from "react-bootstrap";
import {Navbar} from './components/components'
import { Profile, Signup, Login, Home, Search } from "./routes";
import { getUserByJwt, setToken } from "./utils/token";
import {loginMethod, logoutMethode} from './utils/loginUtils'

function App() {
  const init = { username: "", roles: [] };
  const [error, setError] = useState("");
  const [user, setUser] = useState({...init});
  const login = (user, pass) => loginMethod(user, pass, setUser)
  const logout = () => logoutMethode(setUser, init)
  const [searchInput, setSearchInput] = useState("") 




  useEffect(() => {
    if(getUserByJwt()){
      setUser(getUserByJwt())
    }
  },[]);

  return (
    <>
      <Router>
        <Navbar user={user} logout={logout} setSearchInput={setSearchInput}/>
        <Switch>
          <Container fluid>
            <Route path="/" exact>
              <Home setError={setError}/>
            </Route>
            <Route path="/profile">
              <Profile setError={setError}/>
            </Route>
            <Route path="/products" />
            <Route path="/signin">
              <Login login={login} user={user} logout={logout} />
            </Route>
            <Route path="/search">
              <Search searchInput={searchInput} setError={setError}/>
            </Route>
            <Route path="/signup">
              <Signup />
            </Route>
          </Container>
        </Switch>
      </Router>
    </>
  );
}

export default App;
