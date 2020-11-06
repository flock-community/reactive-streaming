import React, {useEffect, useState} from "react";
import {connectAndSubscribeToEndpoint, createRSocketClient} from "../RSocketUtil"
import WordTrend from "./WordTrend";
import {emptyDistribution, parseNewWordTrendDistribution} from "./Util";


const RSocketWordTrend = ({}) => {
    const [trend, setTrend] = useState(emptyDistribution);
    const [client, setClient] = useState(undefined)

    useEffect(() => {
        console.log("RSocketWordTrend is here");
        subscribeToWordDistributions();

        return () => {
            cancelWordDistributions();
        }
    },[]);

    const subscribeToWordDistributions = () => {
        const rSocketClient = createRSocketClient();
        setClient(rSocketClient)

        let onNext = payload => {
            // console.log(payload.data);
            let newDist = parseNewWordTrendDistribution(payload.data || {});
            setTrend(newDist)
        };

        let onSubscribe = subscription => {
            // Request for infinite distributions
            subscription.request(2147483647);
        };

        connectAndSubscribeToEndpoint(rSocketClient, "word-distributions", onNext, onSubscribe)
    };

    const cancelWordDistributions = () => {
        client.close()
    };

    return <WordTrend trend={trend}/>
};

export default RSocketWordTrend