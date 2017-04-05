import SockJS from 'sockjs-client';
import Stomp from 'webstomp-client';

document.addEventListener("DOMContentLoaded", function() {
    WebSocket.init();
});

let WebSocket = (function() {
    const SERVER_SOCKET_API = "/websockethandler";
    const ENTER_KEY = 13;
    let stompClient;

    function connect() {
        let roomId = getRoomId();
        console.log("roomId: ", roomId);
        let socket = new SockJS(SERVER_SOCKET_API);
        console.log("socket_server: ", socket._server);
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function () {
            stompClient.subscribe("/topic/roomId", function (message) {
                printMessage(JSON.parse(message.body));
            });
        });
    }

    function getRoomId() {
        let roomId = document.getElementById("roomId").textContent;
        let parsed = roomId.split(".");
        return parsed[1];
    }

    let inputElm = document.getElementById("chatInput");
    let userName = document.getElementById("userName").textContent;
    function chatKeyDownHandler(e) {
        if (e.which === ENTER_KEY && inputElm.value.trim() !== "") {
            sendMessage(userName, inputElm.value);
            clear(inputElm);
        }
    }

    function sendMessage(userName, text) {
        stompClient.send("/app/hello", JSON.stringify({"content": text, "userName": userName}));
    }

    let textArea = document.getElementById("chatOutput");
    function printMessage(message) {
        syncScroll();
        let formattedMsg = message.userName + " : " + message.content;
        textArea.value += formattedMsg + "\n";
    }

    let exitBtn = document.getElementById("exit_button");
    exitBtn.addEventListener("click", disconnect);

    function disconnect() {
        if (stompClient != null) {
            stompClient.disconnect();
        }
        console.log("Disconnected");
    }

    function clear(input) {
        input.value = "";
    }

    function syncScroll() {
        textArea.scrollTop = textArea.scrollHeight;
    }

    function init() {
        connect();
        inputElm.addEventListener("keydown", chatKeyDownHandler);
    }

    return {
        init : init
    }
})();

export default 'socket';