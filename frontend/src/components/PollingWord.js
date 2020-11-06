import React, {useEffect} from "react";
import Word from "./Word";
import {useAddWordToList} from "./Util";


const PollingWord = ({version="v1", alignRight}) => {
    const [words, addWord] = useAddWordToList();

    useEffect(() => {
        console.log("PollingWord is here");
    }, []);

    const requestWords = (number) => {
        console.log("Polling most recent endpoint once more (ignoring #number argument)")
        fetch(`http://localhost:3000/wordclouds/${version}/words/most-recent`)
            .then(it => {
                console.log("Requested words", it)
                return it.json()
            })
            .then(data => {
                console.log(data)
                addWord(data)
            });
    }

    return <Word
        alignRight={alignRight}
        onRequest={requestWords}
        words={words}
    />
};

export default PollingWord