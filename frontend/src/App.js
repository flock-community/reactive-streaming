import React from "react";
import {Grid, Typography} from "@material-ui/core";
import SSEWord from "./components/SSEWord";
import {makeStyles} from "@material-ui/core/styles";
import SSEWordTrend from "./components/SSEWordTrend";
import RSocketWord from "./components/RSocketWord";
import RSocketWordTrend from "./components/RSocketWordTrend";
import PollingWord from "./components/PollingWord";


const useStyles = makeStyles(theme => ({
        root: {
            justifyContent: "space-around",
            alignItems: "flex-start"
        },
        card: {
            // border: "5px solid black",
        }
    })
);

const App = () => {
    const classes = useStyles();

    return (
        <Grid container spacing={5} className={classes.root}>
            <Grid container spacing={3} item xs={6} className={classes.card}>
                <Grid item xs={12}>
                    <Typography variant="h6">REST</Typography>
                    <PollingWord version="v1" alignRight={true}/>
                </Grid>

                {/**/}
                {/*<Grid item xs={12}>*/}
                {/*    <Typography variant="h6">SSE</Typography>*/}
                {/*    <SSEWordTrend/>*/}
                {/*    <SSEWord version={"v1"} alignRight={true}/>*/}
                {/*</Grid>*/}

                {/*<Grid item xs={12}>*/}
                {/*    <Typography variant="h6">RSocket</Typography>*/}
                {/*    <RSocketWordTrend/>*/}
                {/*    <RSocketWord alignRight={true}/>*/}
                {/*</Grid>*/}
            </Grid>


            <Grid container spacing={3} item xs={6} className={classes.card}>
                <Grid item xs={12}>
                    <Typography variant="h6">REST</Typography>
                    <PollingWord version={"v1"} alignRight={false}/>
                </Grid>

                {/*<Grid item xs={12}>*/}
                {/*    <Typography variant="h6">SSE</Typography>*/}
                {/*    <SSEWordTrend version={"v1"}/>*/}
                {/*    <SSEWord version={"v2"} alignRight={false}/>*/}
                {/*</Grid>*/}
                {/**/}
                {/*<Grid item xs={12}>*/}
                {/*    <Typography variant="h6">RSocket</Typography>*/}
                {/*    <RSocketWordTrend/>*/}
                    {/*<RSocketWord alignRight={false}/>*/}
                {/*</Grid>*/}

            </Grid>
        </Grid>
    )

};

export default App