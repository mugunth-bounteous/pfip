import { Avatar, Typography } from "@mui/material";
import { blue } from "@mui/material/colors";
import React from "react";

const DrawerProfile = ({ name, type }) => {
  return (
    <div className="DrawerProfileContainer">
      <div className="AvatarContainer">
        <Avatar
          className="ProfileAvatar"
          sx={{
            fontSize: "2rem",
            width: 100, 
            height: 100,
            margin: 6, 
            marginBottom: 2,
            marginTop: 2,
          }}
        ></Avatar>
      </div>
      <Typography sx={{
        marginLeft: 8,
      }}>Hi {name}!</Typography>
      <Typography
        sx={{
          fontWeight: "100",
          fontSize: "10px",
          fontStyle: "italic",
          marginLeft: 8.5,
        }}
      >
        {type}
      </Typography>
    </div>
  );
};

export default DrawerProfile;
