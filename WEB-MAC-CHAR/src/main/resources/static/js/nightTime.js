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

    init(role) {
        this.slot_box.addEventListener("click", this.nightTimeVote.bind(this));
        this.role = role
        this.nightTimerStart();
    }

    nightTimeVote({target}) {
        console.log(this.role);
        if (target.tagName === "DIV" && this.role === "Mafia") {
            if (this.voted !== null) {
                this.voted.getElementsByClassName("player_status")[0].textContent = "";
            }
            target.parentElement.getElementsByClassName("player_status")[0].textContent = "VOTED";
            this.voted = target.parentElement;
        }
    }

    nightTimerStart() {
        this.timeCalculator.startTimer(this.NIGHT_TIME, this.sendNightTimeVoteResult.bind(this.nightTime));
    }

    sendNightTimeVoteResult() {
        let victim = this.voted.getElementsByClassName("player_name")[0].textContent;
        console.log("victim :: " + victim);
    	this.voteSocket.sendVoteResult(this.userName, victim);
        
        this.slot_box.removeEventListener("click", this.nightTimeVote.bind(this));
    }
}
