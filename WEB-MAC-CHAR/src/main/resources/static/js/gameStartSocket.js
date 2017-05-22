import {textArea, syncScroll, printMessage} from "./room_util";
import {startNoon} from "./noonLogic";

export default class gameStartSocket {
    constructor(gameLogic, VoteSocket) {
        this.ENTER_KEY = 13;
        this.userName = document.getElementById("userName").textContent;
        this.readyBtn = document.getElementById("readyBtn");
        this.gameLogic = gameLogic;
        this.voteSocket = VoteSocket;
    }

    connect(stompClient, gameStart_url, destinationUrl) {
        console.log("DESTINATION URL: " + destinationUrl);
        this.stompClient = stompClient;
        this.destinationUrl = destinationUrl;
        stompClient.subscribe(gameStart_url, gameStart => {
            document.querySelector('.player_role_name').textContent = `은 ${gameStart.body}입`;
            Array.from(document.getElementsByClassName("player_status")).forEach(s => {
    			s.innerHTML = "";
    		}); // Ready 지워주기
            this.gameLogic.start(gameStart.body);
        });
    }
}

export function sendGameStart() {
    let msg = {
        "userName": this.userName,
    };
    this.readyBtn.style.display = "none";
    console.log(msg);
    this.stompClient.send(this.destinationUrl, JSON.stringify(msg));
}
