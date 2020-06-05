import React, {useEffect, useState} from "react";
import Typography from "@material-ui/core/Typography";
import Grid from "@material-ui/core/Grid";

const {
    RSocketClient,
    JsonSerializer,
    IdentitySerializer
} = require('rsocket-core');

const RSocketWebSocketClient = require('rsocket-websocket-client').default;

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
        // Create an instance of a client
        client = new RSocketClient({
            serializers: {
                data: JsonSerializer,
                metadata: IdentitySerializer
            },
            setup: {
                // ms btw sending keepalive to server
                keepAlive: 60000,
                // ms timeout if no keepalive response
                lifetime: 180000,
                // format of `data`
                dataMimeType: 'application/json',
                // format of `metadata`
                metadataMimeType: 'message/x.rsocket.routing.v0',
            },
            transport: new RSocketWebSocketClient({
                url: `ws://localhost:8080/ws`
            }),
        });
        // Open the connection
        let subscriber = client.connect().subscribe({
            onComplete: socket => {
                // socket provides the rsocket interactions fire/forget, request/response,
                // request/stream, etc as well as methods to close the socket.
                socket.requestStream({
                    data: null,
                    metadata: String.fromCharCode('words'.length) + 'words',
                }).subscribe({
                    onComplete: () => console.log('complete'),
                    onError: error => {
                        console.log(error);
                    },
                    onNext: payload => {
                        console.log(payload.data);
                        addWordToList(payload.data.word || '<no-word>')
                    },
                    onSubscribe: subscription => {
                        subscription.request(2147483647);
                    },
                });
            },
            onError: error => {
                console.log(error);
            },
            onSubscribe: cancel => {
                console.log("subscribed for words")
            }
        });
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