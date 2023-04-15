import React from "react";
import {Grid, Typography} from "@material-ui/core";
import RSocketWordTrend from "./components/RSocketWordTrend";
import RSocketWord from "./components/RSocketWord";
import {makeStyles} from "@material-ui/core/styles";


const useStyles = makeStyles(theme => ({
        root: {
            justifyContent: "space-around"
        },
        card: {
            border: "5px solid black",
        }
    })
);

const RSocketApp = () => {
    const classes = useStyles();

    return (
        <Grid container spacing={5} className={classes.root}>
            <Grid container spacing={3} item xs={6} className={classes.card}>
                <Grid item xs={12}>
                    <Typography variant="h6">REST</Typography>
                </Grid>
                <Grid item>
                    <RSocketWordTrend/>
                </Grid>
                <Grid item>
                    <RSocketWord/>
                </Grid>
            </Grid>


            <Grid container spacing={3} item xs={6} className={classes.card}>
                <Grid item xs={12}>
                    <Typography variant="h6">Reactive</Typography>
                </Grid>
                <Grid item>
                    <RSocketWordTrend/>
                </Grid>
                <Grid item>
                    <RSocketWord/>
                </Grid>
            </Grid>
        </Grid>
)

};

export default RSocketApp