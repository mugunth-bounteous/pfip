import React, { useContext } from "react";
import { useState } from "react";
import axios from "axios";
import { useSnackbar } from "notistack";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import "./LogIn.css";
import { Button } from "@mui/material";
import UserContext from "../context/userContext";
import backendUrl from "../constants/backendUrl";

const Login = () => {
  const navigate = useNavigate();
  const [details, setDetails] = useState({ username: 0, password: "" });
  const { enqueueSnackbar } = useSnackbar();
  const value = useContext(UserContext);
  const handleSubmit = (e) => {
    e.preventDefault();
    axios.post(`${backendUrl}/api/auth/login`, details).then((res) => {
      console.log(res);
      window.localStorage.setItem("dataLog",JSON.stringify({
        isLoggedIn: true,
        token: res.data.data.token,
        username: res.data.data.username,
        typeId: res.data.data.typeId,
        type: res.data.data.type,
      }))
      value.setUser({
        ...value.user,
        isLoggedIn: true,
        token: res.data.data.token,
        username: res.data.data.username,
        typeId: res.data.data.typeId,
        type: res.data.data.type,
      });
      navigate("/home");
    });
    // value.setUser({ ...value.user, isLoggedIn: true, type: "FACULTY" });
    // navigate("/home");
  };

  return (
    <>
      <div
        className="Auth-form-container"
        sx={{
          display: "flex",
          flexDirection: "column",
        }}
      >
        <form className="Auth-form">
          <div className="Auth-form-content">
            <h3 className="Auth-form-title">Sign In</h3>
            <div className="Auth-form-item">
              <label className="Form-item-label">Username</label>
              <input
                type="text"
                className="form-input"
                placeholder="Enter Username"
                onChange={(choice) => {
                  setDetails({ ...details, username: choice.target.value });
                }}
              />
            </div>
            <div className="Auth-form-item">
              <label className="Form-item-label">Password</label>
              <input
                type="text"
                className="form-input"
                placeholder="Enter password"
                onChange={(choice) => {
                  setDetails({ ...details, password: choice.target.value });
                }}
              />
            </div>
            <div className="Auth-form-item">
              <Button
                onClick={handleSubmit}
                variant="contained"
                color="primary"
              >
                Submit
              </Button>
            </div>
            <br />
          </div>
        </form>
      </div>
    </>
  );
};

export default Login;
