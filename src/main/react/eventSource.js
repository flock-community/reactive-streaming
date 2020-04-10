function createEventSource(url, elementId) {
    const wordDistributions = new EventSource(url);
    wordDistributions.onmessage = function (event) {
        const newElement = document.createElement("li");
        const eventList = document.getElementById(elementId);

        newElement.innerHTML = "message: " + event.data;
        eventList.appendChild(newElement);
    };

    wordDistributions.onerror = function (event) {
        console.log("Received error");
        console.error(event);
        wordDistributions.close()
    };
}

createEventSource("http://localhost:3000/wordclouds/word-distributions", "words");
createEventSource("http://localhost:3000/wordclouds/words", "distributions");