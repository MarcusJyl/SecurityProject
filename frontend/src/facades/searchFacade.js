import { URL } from "../utils/settings";
import { makeOptions, handleHttpErrors, fetcher } from "../utils/fetchUtils";


function searchFacade(){

    const search = (setter, setError, input) => {
        const options = makeOptions("GET", true);
        return fetcher(URL + "/api/search/"+ input, options, (data) => setter({...data}), setError);
      };

    return { search }
}

const facade = searchFacade();
export default facade;