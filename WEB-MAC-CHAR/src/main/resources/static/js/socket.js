import SockJS from 'sockjs-client';
import Stomp from 'webstomp-client';

document.addEventListener("DOMContentLoaded", function() {
    WebSocket.init();
});

let WebSocket = (function() {
    const SERVER_SOCKET_API = "/websockethandler";
    const ENTER_KEY = 13;
    const TO_CHAT_API = "/to/chat/";
    const FROM_CHAT_API = "/from/chat/";
    let stompClient;

    function connect() {
        let socket = new SockJS(SERVER_SOCKET_API);
        let url = FROM_CHAT_API + getRoomId();
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function () {
            stompClient.subscribe(url, function (message) {
                printMessage(JSON.parse(message.body));
            });
        });
    }

    let inputElm = document.getElementById("chatInput");
    let userName = document.getElementById("userName").textContent;
    function chatKeyDownHandler(e) {
        if (e.which === ENTER_KEY && inputElm.value.trim() !== "") {
            sendMessage(userName, inputElm.value);
            clear(inputElm);
        }
    }

    let textArea = document.getElementById("chatOutput");
    function printMessage(message) {
        syncScroll();
        let msg = `${message.userName}:\t${message.content}\n`;
        textArea.value += msg;
    }

    function sendMessage(userName, text) {
        let destinationUrl = TO_CHAT_API + getRoomId();
        let msg = {
                    "content": text,
                    "userName": userName
                  };
        stompClient.send(destinationUrl, JSON.stringify(msg));
    }

    let exitBtn = document.getElementById("exit_button");
    exitBtn.addEventListener("click", disconnect);
    function disconnect() {
        if (stompClient != null) {
            stompClient.disconnect();
        }
        console.log("Disconnected");
    }

    function getRoomId() {
        let roomId = document.getElementById("roomId").textContent;
        let parsed = roomId.split(".");
        return parsed[1];
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