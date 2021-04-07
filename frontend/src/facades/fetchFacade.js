import { handleHttpErrors, makeOptions } from "../utils/fetchUtils";
import { URL } from "../utils/settings";

function jokeFetcher() {
  const fetchData = () => {
    const options = makeOptions("GET", true);
    return fetch(URL, options).then(handleHttpErrors);
  };
  return { fetchData };
}
const facade = jokeFetcher();
export default facade;
