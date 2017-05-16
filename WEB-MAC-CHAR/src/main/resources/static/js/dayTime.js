import timeCalculator from "./timeCalculator";
import gameStartSocket from "./gameStartSocket";

export default class dayTime {
    constructor() {
        this.timeCalculator = new timeCalculator();
        this.slot_box = document.getElementsByClassName("slot_box")[0];
        this.userName = document.getElementById("userName").textContent;
        this.voted = null;
        this.DAY_TIME = 3;
    }

    init() {
        this.slot_box.addEventListener("click", this.dayTimeVote);
    }

    dayTimeVote({target}) {
        if (target.tagName === "DIV") {
            if (this.voted !== null) {
                this.voted.getElementsByClassName("player_status")[0].innerHTML = "";
            }
            target.parentElement.getElementsByClassName("player_status")[0].innerHTML = "VOTED";
            this.voted = target.parentElement;
        }
    }

    dayTimerStart() {
        this.timeCalculator.startTimer(this.DAY_TIME, this.sendDayTimeVoteResult.bind(this));
    }

    sendDayTimeVoteResult() {
        this.slot_box.removeEventListener("click", this.dayTimeVote);
    }
}
