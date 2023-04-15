import React from "react";
import {Grid, Typography} from "@material-ui/core";
import Word from "./components/Word";
import WordTrend from "./components/WordTrend";
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

const SSEApp = () => {
    const classes = useStyles();

    return (
        <Grid container spacing={5} className={classes.root}>
            <Grid container spacing={3} item xs={6} className={classes.card}>
                <Grid item xs={12}>
                    <Typography variant="h6">REST</Typography>
                </Grid>
                <Grid item xs={12}>
                    <WordTrend alignRight={true}/>
                </Grid>
                <Grid item xs={12}>
                    <Word alignRight={true}/>
                </Grid>
            </Grid>


            <Grid container spacing={3} item xs={6} className={classes.card}>
                <Grid item xs={12}>
                    <Typography variant="h6">Reactive</Typography>
                </Grid>
                <Grid item xs={12}>
                    <WordTrend/>
                </Grid>
                <Grid item xs={12}>
                    <Word/>
                </Grid>
            </Grid>
        </Grid>
)

};

export default SSEApp