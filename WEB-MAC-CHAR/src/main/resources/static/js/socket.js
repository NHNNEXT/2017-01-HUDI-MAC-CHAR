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
    const TO_READY_API = "/to/ready/";
    const FROM_READY_API = "/from/ready/";
    
    let stompClient;
    let stompClient2;

    function connect() {
        let socket = new SockJS(SERVER_SOCKET_API);
        let url = FROM_CHAT_API + getRoomId();
        let urlForReady = FROM_READY_API + getRoomId();
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function () {
            stompClient.subscribe(url, function (message) {
                printMessage(JSON.parse(message.body));
            });
            stompClient.subscribe(urlForReady, function (gameStartSignal) {
        		let canStartGame = JSON.parse(gameStartSignal.body).gameStart;
        		if (canStartGame) {
        			startTimer(5);
        		}
            });
        });
    }
        
    let leftTime;
    let calcLeftTime;
        
    function startTimer(time){
    	leftTime = time;
    	calcLeftTime = setInterval(timer, 1000);
    	}
		
    function timer() {
    	document.querySelector(".timer").innerText = timeFormat(leftTime--);
    	if(leftTime < 0) {
    		clearInterval(calcLeftTime);
    		}
    	}
		
    function timeFormat(leftTime) {
    	let leftMinutes = parseInt(leftTime / 60);
    	let leftSeconds = leftTime % 60;
    	return prependZero(leftMinutes, 2) + ":" + prependZero(leftSeconds, 2);
    	}
    
    function prependZero(num, len) {
    	while(num.toString().length < len) {
    		num = "0" + num;
    		}
    	return num;
    	}

    let inputElm = document.getElementById("chatInput");
    let userName = document.getElementById("userName").textContent;
    function chatKeyDownHandler(e) {
        if (e.which === ENTER_KEY && inputElm.value.trim() !== "") {
            sendMessage(userName, inputElm.value);
            clear(inputElm);
        }
    }

    let readyBtn = document.querySelector(".game_status");
    readyBtn.addEventListener("click", sendReady);
    
    function sendReady() {
        let destinationUrl = TO_READY_API + getRoomId();
    	let ready = {
    			"userName": userName
    	};
    	console.log("READY SEND" + ready);
        stompClient.send(destinationUrl, JSON.stringify(ready));
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