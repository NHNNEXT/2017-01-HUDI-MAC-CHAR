import {sendGameStart} from './gameStartSocket';

export default class readySocket {
    constructor(gameStartSocket) {
        this.userName = document.getElementById("userName").textContent;
        this.readyBtn = document.getElementById("readyBtn");
        this.ONE_MINUTE = 60;
        this.TIME_OUT = 5;
        this.ONE_SECOND = 1000;

        this.leftTime;
        this.calcLeftTime;
        
        this.gameStartSocket = gameStartSocket;
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
            this.startTimer(this.TIME_OUT);
            cb();
        }
        return;
    }

    startTimer(time) {
        this.leftTime = time;
        this.calcLeftTime = setInterval(this.timer.bind(this), this.ONE_SECOND);
        //Once ready, player can't cancel ready
        this.readyBtn.removeEventListener("click", this.readyBtnClickHandler);
        this.readyBtn.classList.add("timer_started");
    }

    timer() {
        document.querySelector(".timer").innerText = this.timeFormat(this.leftTime--);
        if (this.leftTime < 0) {
            clearInterval(this.calcLeftTime);
        }
    }

    timeFormat(leftTime) {
        const leftMinutes = parseInt(leftTime / this.ONE_MINUTE);
        const leftSeconds = leftTime % this.ONE_MINUTE;
        return `${this.buildTimeFormat(leftMinutes)}:${this.buildTimeFormat(leftSeconds)}`;
    }

    buildTimeFormat(num) {
        if (num.toString().length < 2) {
            return `0${num}`;
        }
        return num;
    }
}
