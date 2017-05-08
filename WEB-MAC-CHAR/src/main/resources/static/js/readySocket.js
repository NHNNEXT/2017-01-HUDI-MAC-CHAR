export default class readySocket {
    constructor() {
        this.userName = document.getElementById("userName").textContent;
        this.readyBtn = document.getElementById("readyBtn");

        this.leftTime;
        this.calcLeftTime;
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
            this.checkTimerStart(readySignal.startTimer);
        });
    }

    readyBtnClickHandler() {
        this.readyBtn.classList.toggle("ready_status")
        this.sendReady(this.userName);
    }

    sendReady(userName) {
        let msg = {
            "userName": userName
        };
        this.stompClient.send(this.destinationUrl, JSON.stringify(msg));
    }

    printReady(ready) {
        let playerNames = document.getElementsByClassName("player_name");
        for (let playerName of playerNames) {
            if (playerName.textContent === ready.userName) {
                playerName.nextElementSibling.classList.toggle("player_not_ready");
            }
        }
    }

    checkTimerStart(canStart) {
        console.log(this);
        if (canStart) {
            this.startTimer(5);
            return;
        }
    }

    startTimer(time) {
        this.leftTime = time;
        this.calcLeftTime = setInterval(timer, 1000);
        this.readyBtn.removeEventListener("click", this.readyBtnClickHandler);
        this.readyBtn.classList.add("timer_started");
    }
}
