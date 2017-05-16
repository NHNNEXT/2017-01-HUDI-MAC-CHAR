import timeCalculator from "./timeCalculator";
import voteSocket from "./voteSocket";

export default class nightTime {
    constructor(role) {
        this.timeCalculator = new timeCalculator();
        this.role = role;
        this.slot_box = document.getElementsByClassName("slot_box")[0];
        this.voteSocket = new voteSocket();
        this.voted = null;
        this.NIGHT_TIME = 4;
        this.nightTime = this; // to bind this to nightTime
    }

    init() {
        this.slot_box.addEventListener("click", this.nightTimeVote.bind(this));
    }

    nightTimeVote({target}) {
        console.log(this.role);
        if (target.tagName === "DIV" && this.role === "Mafia") {
            if (this.voted !== null) {
                this.voted.getElementsByClassName("player_status")[0].innerHTML = "";
            }
            target.parentElement.getElementsByClassName("player_status")[0].innerHTML = "VOTED";
            this.voted = target.parentElement;
        }
    }

    nightTimerStart() {
        this.timeCalculator.startTimer(this.NIGHT_TIME, this.sendNightTimeVoteResult.bind(this.nightTime));
    }

    sendNightTimeVoteResult() {
        let victim = this.voted.getElementsByClassName("player_name")[0].textContent;
        this.voteSocket.killVictim(victim);
        this.slot_box.removeEventListener("click", this.nightTimeVote);
    }
}
