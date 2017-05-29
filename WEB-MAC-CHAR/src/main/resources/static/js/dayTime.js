import timeCalculator from "./timeCalculator";
import {printMessage} from "./room_util";

export default class dayTime {
    constructor(voteSocket) {
        this.timeCalculator = new timeCalculator();
        this.userName = document.getElementById("userName").textContent;
        this.slot_box = document.getElementsByClassName("slot_box")[0];
        this.voteSocket = voteSocket;

        this.voted = null;
        this.DAY_TIME = 10;
        this.dayTime = this;
        this.voteFunction = this.dayTimeVote.bind(this);
    }

    setNight(nightTime) {
        this.nightTime = nightTime;
    }

    start() {
        document.querySelector('body').classList.remove('main');
        document.querySelector('body').classList.add('dayTime');
        printMessage("아침이 되었습니다 마피아로 의심되는 사람에게 투표해주세요");
        this.slot_box.addEventListener("click", this.voteFunction);
        this.dayTimerStart();
    }

    dayTimeVote({target}) {
        if (target.tagName === "DIV") {
            this.clearBoard();
            if (this.voted !== null) {
                this.voted.getElementsByClassName("player_status")[0].textContent = "";
            }

            target.parentElement.getElementsByClassName("player_status")[0].textContent = "VOTED";
            this.voted = target.parentElement;
        }

        Array.from(document.getElementsByClassName("dead")).forEach(slot => {
            if (slot.getAttribute("data-id") === this.userName) {
                this.voted.getElementsByClassName("player_status")[0].textContent = "";
                this.voted = null;
            }
        });
    }

    dayTimerStart() {
        this.timeCalculator.startTimer(this.DAY_TIME, this.sendDayTimeVoteResult.bind(this.dayTime));
    }

    sendDayTimeVoteResult() {
        let victim = this.voted === null ? "undefined" : this.voted.getElementsByClassName("player_name")[0].textContent;
        console.log("sendDayTimeVoteResult::this.voted:victim ", victim);
        this.voteSocket.sendVoteResult(this.userName, victim, "day");
        this.slot_box.removeEventListener("click", this.voteFunction);
        printMessage("결과를 처리 중입니다");
        this.voted = null;
        setTimeout(() => {
            if (!this.voteSocket.gameIsFinished()) {
                console.log(this.voteSocket.gameIsFinished());
                this.clearBoard();
                this.nightTime.start();
            }
        }, 5000);

    }

    clearBoard() {
        if (this.voted !== null) {
            this.voted.getElementsByClassName("player_status")[0].textContent = "";
        }
    }
}
