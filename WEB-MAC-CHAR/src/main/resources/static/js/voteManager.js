export default class voteManager {
    constructor() {
        this.slotBox = document.querySelector(".slot_box");
    }

    init() {
        this.slotBox.addEventListener("click", this.dayTimeVote.bind(this));
        Array.from(document.getElementsByClassName("player_status")).forEach(entry => {
            entry.innerHTML = "";
        });
    }

    dayTimeVote(e) {
        if (e.target.tagName === "DIV") {
            if (this.voted !== null) {
                this.voted.parentElement.getElementsByClassName("player_status")[0].innerHTML = "";
            }
            e.target.parentElement.getElementsByClassName("player_status")[0].innerHTML = "VOTED";
            this.voted = e.target;
        }
    }
}


