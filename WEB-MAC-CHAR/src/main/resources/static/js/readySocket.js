import {sendGameStart} from './gameStartSocket';
import timeCalculator from "./timeCalculator";

export default class readySocket {
    constructor(gameStartSocket) {
        this.userName = document.getElementById("userName").textContent;
        this.readyBtn = document.getElementById("readyBtn");
        this.TIME_OUT = 1;

        this.leftTime;
        this.calcLeftTime;

        this.gameStartSocket = gameStartSocket;
        this.timeCalculator = new timeCalculator();
    }

    init() {
        this.readyBtn.addEventListener("click", this.readyBtnClickHandler.bind(this));
    }

    connect(stompClient, ready_url, destinationUrl) {
        this.stompClient = stompClient;
        this.destinationUrl = destinationUrl;
        stompClient.subscribe(ready_url, ready => {
            let readySignal = JSON.parse(ready.body);
            this.printReady(readySignal);
            this.checkTimerStart(readySignal.startTimer, sendGameStart.bind(this.gameStartSocket));
        });
    }

    readyBtnClickHandler() {
        this.readyBtn.classList.toggle("ready_status");
        this.sendReady(this.userName);
    }

    sendReady(userName) {
        let msg = {
            "userName": userName
        };
        this.stompClient.send(this.destinationUrl, JSON.stringify(msg));
    }

    printReady({userName}) {
        let playerNames = document.getElementsByClassName("player_name");
        for (let playerName of playerNames) {
            if (playerName.textContent === userName) {
                playerName.nextElementSibling.classList.toggle("player_not_ready");
            }
        }
    }

    checkTimerStart(isStartTimerTrue, cb = () => console.log('undefined')) {
        if (isStartTimerTrue) {
            this.timeCalculator.startTimer(this.TIME_OUT, cb);
        }
        return;
    }
}
