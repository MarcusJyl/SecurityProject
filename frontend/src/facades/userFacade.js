import { URL } from "../utils/settings";
import { makeOptions, handleHttpErrors, fetcher } from "../utils/fetchUtils";


function userFacade() {
  const getUsersByIDs = (action, setError, usernames) => {
    const options = makeOptions("GET", true);
    return fetcher(URL + "/api/user/by/usernames/" + usernames, options, action, setError);
  };

  const deleteUser = (action, setError, id) =>{
    const options = makeOptions("DELETE", true);
    return fetcher(URL + "/api/user/delete/"+ id, options, action, setError);
  }


  return { getUsersByIDs, deleteUser };
}
const facade = userFacade();
export default facade;
