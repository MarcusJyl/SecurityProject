import { URL } from "../utils/settings";
import { makeOptions, handleHttpErrors, fetcher } from "../utils/fetchUtils";


function postFacade() {
  const fetchData = (action, setError) => {
    const options = makeOptions("GET", true);
    return fetcher(URL + "/api/post/all", options, action, setError);
  };
  return { fetchData };
}
const facade = postFacade();
export default facade;
