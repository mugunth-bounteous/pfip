import logo from "./logo.svg";
import "./App.css";
import LogIn from "./pages/LogIn";
import HomeTemplate from "./components/HomeTemplate";
import FacultyClasses from "./pages/faculty/FacultyClasses";
import { useRoutes } from "react-router-dom";
import { createContext, useState } from "react";
import UserContext from "./context/userContext";
import { useEffect } from "react";
import FacultyHomeTemplate from "./pages/faculty/FacultyHomeTemplate";

function App() {
  const [user, setUser] = useState({
    token: "",
    username: "",
    typeId: 0,
    type: "",
    isLoggedIn: false,
  });

  useEffect(() => {
    console.log(user);
  }, [user]);
  const getRoutes = (user) => {
    let dataLog = window.localStorage.getItem("dataLog");
    if (dataLog) {
      let user = JSON.parse(dataLog);
      switch (user.type) {
        case "PARENT":
          return [
            { path: "/", element: <LogIn /> },
            { path: "login", element: <LogIn /> },
          ];
        case "ADMIN":
          return [
            { path: "/", element: <LogIn /> },
            { path: "login", element: <LogIn /> },
          ];

        case "FACULTY":
          return [
            // { path: "/", element: <LogIn /> },
            // { path: "login", element: <LogIn /> },
            {
              element: <FacultyHomeTemplate />,
              children: [
                { path: "home", element: <FacultyClasses /> },
                { path: "/", element: <FacultyClasses /> },
                { path: "/login", element: <FacultyClasses /> },
              ],
            },
          ];

        default:
          return [
            { path: "/", element: <LogIn /> },
            { path: "login", element: <LogIn /> },
          ];
          break;
      }
    } else {
      return [
        { path: "/", element: <LogIn /> },
        { path: "login", element: <LogIn /> },
      ];
    }
  };

  let routes = getRoutes(user);
  let ret = useRoutes(routes);
  return (
    <UserContext.Provider value={{ user, setUser }}>{ret}</UserContext.Provider>
  );
}

export default App;
