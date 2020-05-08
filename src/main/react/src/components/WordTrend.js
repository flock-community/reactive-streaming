import React, {useEffect, useState} from "react";
import {RadialChart} from "react-vis";
import {createEventSource} from "../util"
import Typography from "@material-ui/core/Typography";

const initialData = {
    total: 0,
    distribution: [{
            angle: 0.1,
            label: 'Flock.',
            x: 'Flock.',
            y: 0.1
        }]
};

const WordTrend = ({}) => {
    const [trend, setTrend] = useState(initialData);
    const [eventSource, setEventSource] = useState(undefined);


    useEffect(() => {
        console.log("WordTrend is here");
        subscribeToWordDistributions();

        return () => {
            cancelWordDistributions();
        }
    },[]);


    const mapToRadialChartFormat = (distributionDTO) => {
        return Object.entries(distributionDTO.wordDistribution).map(([word, value]) => {
            return {angle: value, label: word, x:word, y:value}
        });
    };

    const subscribeToWordDistributions = () => {
        setEventSource(createEventSource("/wordclouds/word-distributions", handleNewDistribution));
    };

    const handleNewDistribution = (event) => {
        console.log("hello new distribution");
        console.log(event);

        let distributionDTO = JSON.parse(event.data);
        let mapToRadialChartFormat1 = mapToRadialChartFormat(distributionDTO);
        console.log(mapToRadialChartFormat1);
        setTrend({
            total: distributionDTO.wordTotal,
            distribution: mapToRadialChartFormat1
        });
    };

    const cancelWordDistributions = () => {
        eventSource.close()
    };

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