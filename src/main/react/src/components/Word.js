import React, {useEffect, useState} from "react";
import {createEventSource} from "../util"
import Typography from "@material-ui/core/Typography";
import Grid from "@material-ui/core/Grid";


const Word = ({}) => {
    const [latestWords, setLatestWords] = useState([]);
    const [eventSource, setEventSource] = useState(undefined);


    useEffect(() => {
        console.log("Word is here");
        subscribeToWords();

        return () => {
            cancelWords();
        }
    },[]);

    const subscribeToWords = () => {
        setEventSource(createEventSource("/wordclouds/words", handleNewWord));
    };

    const handleNewWord = (event) => {
        console.log("hello new word");
        console.log(event);

        let wordDTO = JSON.parse(event.data);
        latestWords.push(wordDTO.word);
        if(latestWords.length > 10){
            latestWords.shift()
        }
        setLatestWords(new Array(...latestWords))
    };

    const cancelWords = () => {
        eventSource.close()
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

export default Word