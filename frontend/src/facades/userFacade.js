import { URL } from "../utils/settings";
import { makeOptions, handleHttpErrors, fetcher } from "../utils/fetchUtils";


function userFacade() {
  const getUsersByIDs = (action, setError, usernames) => {
    const options = makeOptions("GET", true);
    return fetcher(URL + "/api/user/by/usernames/" + usernames, options, action, setError);
  };


  return { getUsersByIDs };
}
const facade = userFacade();
export default facade;
