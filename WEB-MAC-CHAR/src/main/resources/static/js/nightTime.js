import timeCalculator from "./timeCalculator";

export default class nightTime {
    constructor(voteSocket) {
        this.timeCalculator = new timeCalculator();
        this.userName = document.getElementById("userName").textContent;
        this.slot_box = document.getElementsByClassName("slot_box")[0];
        this.voteSocket = voteSocket;

        this.voted = null;
        this.NIGHT_TIME = 4;
        this.nightTime = this; // to bind this to nightTime
    }

    setDay(dayTime) {
        this.dayTime = dayTime;
    }

    setRole(role) {
        this.role = role;
    }

    start() {
        this.slot_box.addEventListener("click", this.nightTimeVote.bind(this));
        this.nightTimerStart();
    }

    nightTimeVote({target}) {
        if (target.tagName === "DIV" && this.role === "Mafia") {
            this.clearBoard();
            target.parentElement.getElementsByClassName("player_status")[0].textContent = "VOTED";
            this.voted = target.parentElement;
        }
    }

    nightTimerStart() {
        this.timeCalculator.startTimer(this.NIGHT_TIME, this.sendNightTimeVoteResult.bind(this.nightTime));
    }

    sendNightTimeVoteResult() {
        let victim = this.voted === null ? "undefined" : this.voted.getElementsByClassName("player_name")[0].textContent;
        this.voteSocket.sendVoteResult(this.userName, victim);
        this.slot_box.removeEventListener("click", this.nightTimeVote.bind(this));
        if (!this.voteSocket.gameIsFinished()) {
            this.clearBoard();
            this.dayTime.start();
        }
    }

    clearBoard() {
        if (this.voted !== null) {
            this.voted.getElementsByClassName("player_status")[0].textContent = "";
        }
    }
}
