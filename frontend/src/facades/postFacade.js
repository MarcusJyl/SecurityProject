import { URL } from "../utils/settings";
import { makeOptions, handleHttpErrors, fetcher } from "../utils/fetchUtils";


function postFacade() {
  const getAllPosts = (action, setError) => {
    const options = makeOptions("GET", true);
    return fetcher(URL + "/api/post/all", options, action, setError);
  };

  const getAllCommentsForAPost = (action, setError, id) => {
    const options = makeOptions("GET", true);
    return fetcher(URL + "/api/comment/"+ id, options, action, setError);
  };

  const addComment = (action, setError, body) => {
    const options = makeOptions("POST", true, body);
    return fetcher(URL + "/api/comment", options, action, setError);
  }

  const addPost = (action, setError, body) => {
    const options = makeOptions("POST", true, body);
    return fetcher(URL + "/api/post", options, action, setError);
  }

  return { getAllPosts, addPost, getAllCommentsForAPost, addComment };
}
const facade = postFacade();
export default facade;
