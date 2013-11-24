var ws = $.gracefulWebSocket("ws://127.0.0.1:8080/websocket");
ws.onmessage = function(event) {
    var messageFromServer = event.data;
    $('#output').append('<p>Received: '+messageFromServer+'</p>');
}

function send(message) {
    ws.send(message);
}