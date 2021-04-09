import { makeOptions, handleHttpErrors } from "../utils/fetchUtils";
import { URL } from "../utils/settings";

function apiFacade() {
  const login = (user, password) => {
    const options = makeOptions("POST", true, {
      username: user,
      password: password,
    });
    return fetch(URL + "/api/login", options)
      .then(handleHttpErrors)
  };

  const signup = (username, password) => {
    const options = makeOptions("POST", true, {
      username,
      password,
    });
    return fetch(
      URL + "/api/signup",
      options
    ).then(handleHttpErrors);
  };

  const fetchUserRole = (user) => {
    const options = makeOptions("GET", true);
    return fetch(URL + "/api/" + user, options).then(handleHttpErrors);
  };

  return {
    login,
    fetchUserRole,
    signup,
  };
}
const facade = apiFacade();
export default facade;
