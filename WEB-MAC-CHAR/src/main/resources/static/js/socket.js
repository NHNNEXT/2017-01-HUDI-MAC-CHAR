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
    const TO_ACCESS_API = "/to/access/";
    const FROM_ACCESS_API = "/from/access/";
    
    let stompClient;

    function connect() {
        let socket = new SockJS(SERVER_SOCKET_API);
        let chat_url = FROM_CHAT_API + getRoomId();
        let ready_url = FROM_READY_API + getRoomId();
        let access_url = FROM_ACCESS_API + getRoomId();
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function () {
            stompClient.subscribe(chat_url, function (message) {
                printMessage(JSON.parse(message.body));
            });
            stompClient.subscribe(ready_url, function (ready) {
            	let readySignal = JSON.parse(ready.body);
            	printReady(readySignal);
            	checkTimerStart(readySignal.startTimer);
            });
            stompClient.subscribe(access_url, function (access) {
            	showUserList(JSON.parse(access.body));
            });
            sendAccess(userName, "enter");
        });
    }

    let inputElm = document.getElementById("chatInput");
    let readyBtn = document.getElementById("readyBtn");
    let userName = document.getElementById("userName").textContent;
    function chatKeyDownHandler(e) {
        if (e.which === ENTER_KEY && inputElm.value.trim() !== "") {
            sendMessage(userName, inputElm.value);
            clear(inputElm);
        }
    }
    
    function readyBtnClickHandler() {
        this.classList.toggle("ready_status")
    	sendReady(userName);
    }

    let textArea = document.getElementById("chatOutput");
    function printMessage(message) {
        syncScroll();
        let msg = `${message.userName}:\t${message.content}\n`;
        textArea.value += msg;
    }
    
    function printReady(ready) {
    	let playerNames = document.getElementsByClassName("player_name");
    	for (let playerName of playerNames) {
    		if (playerName.textContent === ready.userName) {
    			playerName.nextElementSibling.classList.toggle("player_not_ready");
    		}
    	}
    }
    
    function checkTimerStart(canStart) {
    	if(canStart) {
    		startTimer(5);
    		return;
    	}
    }
    
    function startTimer(time){
    	leftTime = time;
     	calcLeftTime = setInterval(timer, 1000); 
     	readyBtn.removeEventListener("click", readyBtnClickHandler);
     	readyBtn.classList.add("timer_started");
     }
    let leftTime;
    let calcLeftTime;
    
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
    
    
    function showUserList(access) {
    	const PLAYER_NOT_READY = "player_not_ready";
    	// sendAccess를 날린 클라이언트와 원래 방에 있던 사용자들을 구분해서 다르게 동작하게 하기
    	if (userName === access.userName) {
    		for (let user of access.users) {
    			let emptySlot = document.querySelector(".empty_slot");
    			emptySlot.querySelector(".player_name").textContent = user.nickname;
    			if (user.status === "READY") {
    				emptySlot.querySelector(".player_status").classList.remove(PLAYER_NOT_READY);
    			}
    			emptySlot.setAttribute("data-id", user.nickname);
    			emptySlot.classList.remove("empty_slot");
    		}
    		syncScroll();
    		let msg = `${access.userName} 님이 입장하셨습니다.\n`;
    		textArea.value += msg;
    	} else {
    		if (access.access === "enter") {
    			let emptySlot = document.querySelector(".empty_slot");
    			emptySlot.querySelector(".player_name").textContent = access.userName;
    			emptySlot.querySelector(".player_status").classList.remove(PLAYER_NOT_READY);
    			emptySlot.setAttribute("data-id", access.userName);
    			emptySlot.classList.remove("empty_slot");
    			syncScroll();
        		let msg = `${access.userName} 님이 입장하셨습니다.\n`;
        		textArea.value += msg;
    		} else { // access.access === "exit"
    			let userSlot = document.querySelector(`[data-id=${access.userName}]`);
    			userSlot.querySelector(".player_name").textContent = "";
    			userSlot.querySelector(".player_status").classList.add(PLAYER_NOT_READY);
    			userSlot.removeAttribute("data-id");
    			userSlot.classList.add("empty_slot");
    			syncScroll();
        		let msg = `${access.userName} 님이 퇴장하셨습니다.`;
        		textArea.value += msg;
    		}
    	}
    }

    function sendMessage(userName, text) {
        let destinationUrl = TO_CHAT_API + getRoomId();
        let msg = {
                    "content": text,
                    "userName": userName
                  };
        stompClient.send(destinationUrl, JSON.stringify(msg));
    }
    
    function sendReady(userName) {
    	let destinationUrl = TO_READY_API + getRoomId();
    	let msg = {
    			"userName": userName
    	};
    	console.log(msg);
    	stompClient.send(destinationUrl, JSON.stringify(msg));
    }
    
    function sendAccess(userName, access) {
    	let destinationUrl = TO_ACCESS_API + getRoomId();
    	let msg = {
    			"userName": userName,
    			"access": access
    	};
    	console.log(msg);
    	stompClient.send(destinationUrl, JSON.stringify(msg));
    }

    let exitBtn = document.getElementById("exit_button");
    exitBtn.addEventListener("click", disconnect);
    function disconnect() {
    	sendAccess(userName, "exit");
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
        readyBtn.addEventListener("click", readyBtnClickHandler);
        inputElm.addEventListener("keydown", chatKeyDownHandler);
    }

    return {
        init : init
    }
})();

export default 'socket';