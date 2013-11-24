var socket;
if (window.WebSocket) {
    socket = new WebSocket("ws://localhost:8080/websocket");
    socket.onmessage = function(event) {
        $('#output').append('<p>Received: '+event.data+'</p>');
    };
    socket.onopen = function(event) {
        $('#output').append('<p>Web Socket opened!</p>');
    };
    socket.onclose = function(event) {
        $('#output').append('<p>Web Socket closed!</p>');
    };
} else {
    alert("Your browser does not support Websockets. (Use Chrome)");
}

function send(message) {
    if (!window.WebSocket) {
        return;
    }
    if (socket.readyState == WebSocket.OPEN) {
        socket.send(message);
    } else {
        alert("The socket is not open.");
    }
}