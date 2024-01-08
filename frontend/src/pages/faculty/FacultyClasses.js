import React from "react";
import SquareTile from "../../components/SquareTile";
import { Grid, ListItem } from "@mui/material";

const FacultyClasses = (props) => {
  return (
    <div>
      <Grid container spacing={3}>
        <Grid item>
          <SquareTile />
        </Grid>
        <Grid item>
          <SquareTile />
        </Grid>
        <Grid item>
          <SquareTile />
        </Grid>
        <Grid item>
          <SquareTile />
        </Grid>
      </Grid>
    </div>
  );
};

export default FacultyClasses;
