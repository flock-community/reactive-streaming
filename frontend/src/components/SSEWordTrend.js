import React, {useEffect, useState} from "react";
import {createEventSource} from "../EventSourceUtil"
import WordTrend from "./WordTrend";
import {emptyDistribution, parseNewWordTrendDistribution} from "./Util";


const SSEWordTrend = () => {
    const [trend, setTrend] = useState(emptyDistribution);
    const [source, setSource] = useState(undefined);

    useEffect(() => {
        console.log("WordTrend is here");
        subscribeToWordDistributions();

        return () => {
            cancelWordDistributions();
        }
    },[]);

    const subscribeToWordDistributions = () => {
        // const source = createEventSource("/wordclouds/v1/word-distributions", handleNewDistribution)
        const source = createEventSource("/wordclouds/v2/word-distributions", handleNewDistribution)
        setSource(source)
    };

    const handleNewDistribution = (event) => {
        console.debug("hello new distribution", event);

        const distributionDTO = JSON.parse(event.data);
        let newDist = parseNewWordTrendDistribution(distributionDTO);
        setTrend(newDist)
    };

    const cancelWordDistributions = () => {
        source.close()
    };

    return <WordTrend trend={trend}/>

};

export default SSEWordTrend