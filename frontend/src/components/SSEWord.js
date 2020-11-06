import React, {useEffect, useState} from "react";
import {createEventSource} from "../EventSourceUtil"
import Word from "./Word";
import {useAddWordToList} from "./Util";


const SSEWord = ({version="v2", alignRight=false}) => {
    const [words, addWord] = useAddWordToList();
    const [source, setSource] = useState(undefined);

    useEffect(() => {
        console.log("Word is here");
        subscribeToWords();

        return () => {
            cancelWords();
        }
    }, []);

    const subscribeToWords = () => {
        const source = createEventSource(`/wordclouds/${version}/words`, handleNewWord)
        setSource(source);
    };

    const handleNewWord = (event) => {
        console.debug("hello new word", event);
        const word =  JSON.parse(event.data);
        addWord(word)
    };


    const cancelWords = () => {
        source.close()
    };

    return <Word
        alignRight={alignRight}
        onRequest={() => {}}
        words={words}
        />
};

export default SSEWord