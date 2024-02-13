import { BackHand, Square } from "@mui/icons-material";
import React, { useEffect } from "react";
import SquareTile from "../../components/SquareTile";
import axios from "axios";
import backendUrl from "../../constants/backendUrl";
import UserContext from "../../context/userContext";
import { useContext, useState } from "react";
import { Grid } from "@mui/material";

const FacultyHome = (props) => {
  let contextdata = useContext(UserContext);
  const [courseData, setCourseData] = useState([]);
  useEffect(() => {
    console.log("contextdata.dataLog : ", contextdata.dataLog);
    if (contextdata.dataLog.token) {
      axios
        .get(`${backendUrl}/api/course/getCourseByFaculty`, {
          headers: { Authorization: `Bearer ${contextdata.dataLog.token}` },
        })
        .then((res) => {
          setCourseData(res.data.data);
        })
        .catch((err) => {
          console.log(err);
        });
    }
  }, [contextdata]);
  return (
    <div>
      <Grid container spacing={2}>
        {courseData.map((val) => {
          return (
            <Grid xs={2} md={2}>
              <SquareTile courseId={val.id} name={val.courseName} />
            </Grid>
          );
        })}
      </Grid>
    </div>
  );
};

export default FacultyHome;
