document.addEventListener("DOMContentLoaded", function() {
    WebSocket.init();
});

let WebSocket = (function() {
    const SERVER_SOCKET_API = "/websockethandler";
    const ENTER_KEY = 13;
    let stompClient;

    function connect() {
        let socket = new SockJS(SERVER_SOCKET_API);
        console.log("socket: ", socket);
        stompClient = Stomp.over(socket);
        console.log("stompClient: ", stompClient);
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/roomId', function (greeting) {
                console.log(greeting);//ack, header, destination, messageId, body
                console.log(JSON.parse(greeting.body).content);//{"content":"gi"}.content
                printMessage(JSON.parse(greeting.body).content);
            });
        });
    }

    let textArea = document.getElementById("chatOutput");
    function printMessage(message) {
        textArea.value += message + "\n";
    }

    let inputElm = document.getElementById("chatInput");
    function chatKeyDownHandler(e) {
        if (e.which === ENTER_KEY && inputElm.value.trim() !== "") {
            sendMessage(inputElm.value);
            clear(inputElm);
        }
    }

    function clear(input) {
        input.value = "";
    }

    function sendMessage(text) {
        stompClient.send("/app/hello", {}, JSON.stringify({'content': text}));
    }

    function init() {
        connect();
        inputElm.addEventListener("keydown", chatKeyDownHandler);
    }
    return {
        init : init
    }
})();
