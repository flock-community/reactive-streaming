import React from "react";
import {RadialChart} from "react-vis";
import Typography from "@material-ui/core/Typography";
import {emptyDistribution} from "./Util";

const WordTrend = ({trend = emptyDistribution}) => {
    return (
        <>
        <Typography variant="h6" >out of {trend.total} words</Typography>
        <RadialChart
            data={trend.distribution}
            labelsStyle={{
                fontFamily: "Roboto",
                fontSize: 18
            }}
            showLabels
            width={300}
            height={300}
        />
        </>)
};

export default WordTrend