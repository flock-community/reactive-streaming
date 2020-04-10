const evtSource = new EventSource("http://localhost:3000/wordclouds");
evtSource.onmessage = function(event) {
    const newElement = document.createElement("li");
    const eventList = document.getElementById("list");

    newElement.innerHTML = "message: " + event.data;
    eventList.appendChild(newElement);
};

evtSource.onerror = function(event){
    console.log("Received error");
    console.error(event);
    evtSource.close()
};