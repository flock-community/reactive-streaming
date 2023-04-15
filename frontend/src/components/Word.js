import React, {useEffect, useState} from "react";
import {createEventSource} from "../util"
import Typography from "@material-ui/core/Typography";
import Grid from "@material-ui/core/Grid";
import {makeStyles} from "@material-ui/core/styles";


const useStyles = makeStyles({
        align: {
            textAlign: "right",
            display: "flex",
            flexDirection: "row-reverse"
        },
        card: {
            border: "5px solid black",
        }
    });


const Word = ({alignRight}) => {
    const [latestWords, setLatestWords] = useState([]);
    const [eventSource, setEventSource] = useState(undefined);

    const classes = useStyles();


    useEffect(() => {
        console.log("Word is here");
        subscribeToWords();

        return () => {
            cancelWords();
        }
    }, []);

    const subscribeToWords = () => {
        setEventSource(createEventSource("/wordclouds/words", handleNewWord));
    };

    const handleNewWord = (event) => {
        console.log("hello new word");
        console.log(event);

        let wordDTO = JSON.parse(event.data);
        addWordToList(wordDTO.word)
    };

    const addWordToList = (word) => {
        setLatestWords(prevState => {
            const newArray = [word].concat(prevState)
            if (newArray.length > 15) {
                newArray.pop()
            }

            return newArray
        })

    }

    const cancelWords = () => {
        eventSource.close()
    };

    const response = () =>
        latestWords.map((value, idx) =>
            (
                <Grid key={idx} item xs={12}>
                    { !!alignRight ? (

                    <Typography variant="h6" className={classes.align}>
                        <span>{idx + 1}</span><span> - </span><span>{value}</span>
                    </Typography>
                        ) :
                        (

                    <Typography variant="h6" >
                        <span>{idx + 1}</span><span> - </span><span>{value}</span>
                    </Typography>
                        )
                    }
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