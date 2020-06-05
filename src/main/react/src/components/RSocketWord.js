import React, {useEffect, useState} from "react";
import Typography from "@material-ui/core/Typography";
import Grid from "@material-ui/core/Grid";
import {connectAndSubscribeToEndpoint, createRSocketClient} from "../util";


let client = undefined;
const RSocketWord = ({}) => {
    const [latestWords, setLatestWords] = useState([]);

    useEffect(() => {
        console.log("RSocketWord is here");
        subscribeToWords();

        return () => {
            cancelWords();
        }
    },[]);

    const subscribeToWords = () => {
        client =createRSocketClient();


        let onNext = payload => {
            console.log(payload.data);
            addWordToList(payload.data.word || '<no-word>')
        };

        let onSubscribe = subscription => {
                subscription.request(2147483647);
            };

        connectAndSubscribeToEndpoint(client, "words", onNext, onSubscribe)
    };

    function addWordToList(word) {
        latestWords.push(word);
        if (latestWords.length > 10) {
            latestWords.shift()
        }
        setLatestWords(new Array(...latestWords))
    }

    const cancelWords = () => {
        client.close()
        console.log("Trying to cancel words (NOT IMPLEMENTED YET")
    };

    const response = () =>
        latestWords.map((value, idx) =>
            (
                <Grid key={idx} item xs={12}>
                    <Typography variant="h6">{value}</Typography>
                </Grid>
            )
        );


    return (
        <>
            <Grid container>
            {response()}
            </Grid>
        </>
    )
};

export default RSocketWord