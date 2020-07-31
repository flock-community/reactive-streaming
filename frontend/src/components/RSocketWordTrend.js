import React, {useEffect, useState} from "react";
import {RadialChart} from "react-vis";
import {connectAndSubscribeToEndpoint, createRSocketClient} from "../util"
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

let client = undefined;

const RSocketWordTrend = ({}) => {
    const [trend, setTrend] = useState(initialData);


    useEffect(() => {
        console.log("RSocketWordTrend is here");
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

    const handleNewDist = distributionDTO => {
        let mapToRadialChartFormat1 = mapToRadialChartFormat(distributionDTO);
        console.log(mapToRadialChartFormat1);
        setTrend({
            total: distributionDTO.wordTotal,
            distribution: mapToRadialChartFormat1
        });
    };


    const subscribeToWordDistributions = () => {
        client = createRSocketClient();

        let onNext = payload => {
            console.log(payload.data);
            handleNewDist(payload.data || {})
        };

        let onSubscribe = subscription => {
            subscription.request(2147483647);
        };

        connectAndSubscribeToEndpoint(client, "word-distributions", onNext, onSubscribe)
    };

    const cancelWordDistributions = () => {
        client.close()
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

export default RSocketWordTrend