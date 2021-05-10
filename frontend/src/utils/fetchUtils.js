import { getToken, getRecaptcha } from "./token";

export function handleHttpErrors(res) {
  if (!res.ok) {
    return Promise.reject({ status: res.status, fullError: res.json() });
  }
  return res.json();
}

export const makeOptions = (method, addToken, body) => {
  var opts = {
    method: method,
    headers: {
      "Content-type": "application/json",
      Accept: "application/json",
    },
  };
  if (addToken && getToken()) {
    opts.headers["x-access-token"] = getToken();
  }
  if(getRecaptcha()){
    opts.headers["g-recaptcha-response"] = getRecaptcha()
  }
  if (body) {
    opts.body = JSON.stringify(body);
  }
  return opts;
};

export const fetcher = (URL, options, action, setError, actionIfError) => {
  return fetch(URL, options)
    .then(handleHttpErrors)
    .then(action)
    .catch((err) => {
      if (actionIfError) actionIfError();
      else catcher(err, setError);
    });
};

const catcher = (err, setError) => {
  if (err.status) {
    err.fullError.then((e) => {
      setError(e.message);
    });
  } else {
    setError("Network error");
  }
};
