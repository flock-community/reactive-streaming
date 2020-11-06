import React, {useEffect, useState} from "react";
import {connectAndSubscribeToEndpoint, createRSocketClient} from "../RSocketUtil";
import Word from "./Word";
import {useAddWordToList} from "./Util";


const RSocketWord = ({alignRight}) => {
    const [words, addWord] = useAddWordToList();
    const [subscription, setSubscription] = useState(undefined);
    const [client, setClient] = useState(undefined)

    useEffect(() => {
        console.log("RSocketWord is here");
        subscribeToWords();

        return () => {
            cancelWords();
        }
    }, []);

    const subscribeToWords = () => {
        const rSocketClient = createRSocketClient();
        setClient(rSocketClient)

        let onNext = payload => {
            // console.log(payload.data);
            addWord(payload.data)
        };

        let onSubscribe = sub => {
            setSubscription(sub);
            // sub.request(0);
        };

        connectAndSubscribeToEndpoint(rSocketClient, "words", onNext, onSubscribe)
    };

    const cancelWords = () => {
        client.close();
    };

    const requestWords = (number) => {
        console.log(`Requesting ${number} words`)
        subscription.request(number);
    };

    return <Word alignRight={alignRight} words={words} onRequest={requestWords}/>
};

export default RSocketWord