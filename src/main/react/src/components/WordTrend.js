import React, {useEffect, useState} from "react";
import {RadialChart} from "react-vis";
import {createEventSource} from "../util"

const radialData = [
    {
        angle: 0.1,
        label: 'Flock.',
        x: 'Flock.',
        y: 0.1
    }
];
const WordTrend = ({}) => {
    const [distribution, setDistribution] = useState(radialData);
    const [eventSource, setEventSource] = useState(undefined);


    useEffect(() => {
        console.log("WordTrend is here");
        subscribeToWordDistributions();

        return () => {
            cancelWordDistributions();
        }
    },[]);


    const mapToRadialChartFormat = (distributionDTO) => {
        return Object.entries(distributionDTO.wordCounter).map(([word, value]) => {
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
        setDistribution(mapToRadialChartFormat1);
    };

    const cancelWordDistributions = () => {
        eventSource.close()
    };

    return (
        <RadialChart
            data={distribution}
            labelsStyle={{
                fontFamily: "Roboto",
                fontSize: 18
            }}
            showLabels
            width={300}
            height={300}
        />)
};


// createEventSource("/wordclouds/words", "words");
// createEventSource("//wordclouds/word-distributions", "distributions");

export default WordTrend