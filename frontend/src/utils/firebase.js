import firebase from 'firebase/app'
import 'firebase/storage'

const firebaseConfig = {
  apiKey: "AIzaSyBUk8AJmkVdQjZusQnvuU3UNwQvLDdEKwk",
  authDomain: "banden-dde2c.firebaseapp.com",
  projectId: "banden-dde2c",
  storageBucket: "banden-dde2c.appspot.com",
  messagingSenderId: "484649639439",
  appId: "1:484649639439:web:3fe4493e056517021dcd7a",
  measurementId: "G-XNBTLLYTCN"
};
  // Initialize Firebase
  firebase.initializeApp(firebaseConfig);

  const storage = firebase.storage()


//analytics is optional for this tutoral 

//   firebase.analytics();1

  
export  {
    storage, firebase as default
  }
 
 