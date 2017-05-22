import timeCalculator from "./timeCalculator";

export default class dayTime {
    constructor(voteSocket) {
        this.timeCalculator = new timeCalculator();
        this.userName = document.getElementById("userName").textContent;
        this.slot_box = document.getElementsByClassName("slot_box")[0];
        this.voteSocket = voteSocket;
        
        this.voted = null;
        this.DAY_TIME = 5;
        this.dayTime = this; // to bind this to nightTime
    }

    init() {
        this.slot_box.addEventListener("click", this.dayTimeVote.bind(this));
        this.dayTimerStart();	
    }

    dayTimeVote({target}) {
        if (target.tagName === "DIV") {
            if (this.voted !== null) {
                this.voted.getElementsByClassName("player_status")[0].textContent = "";
            }
            target.parentElement.getElementsByClassName("player_status")[0].textContent = "VOTED";
            this.voted = target.parentElement;
        }
    }

    dayTimerStart() {
        this.timeCalculator.startTimer(this.DAY_TIME, this.sendDayTimeVoteResult.bind(this.dayTime));
    }

    sendDayTimeVoteResult() {
        let victim = this.voted === null ? "undefined" : this.voted.getElementsByClassName("player_name")[0].textContent;
    	this.voteSocket.sendVoteResult(this.userName, victim);
        this.slot_box.removeEventListener("click", this.dayTimeVote.bind(this));
    }
}
