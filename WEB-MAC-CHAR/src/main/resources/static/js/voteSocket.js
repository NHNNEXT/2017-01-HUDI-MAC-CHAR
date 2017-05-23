import {printMessage} from "./room_util";

export default class voteSocket {
    connect(stompClient, vote_url, destinationUrl) {

        this.stompClient = stompClient;
        this.destinationUrl = destinationUrl;

        this.isFinished = false;

        stompClient.subscribe(vote_url, voteMessage => {
            let {msg, finished} = JSON.parse(voteMessage.body);
            this.isFinished = finished;
            if (!finished) {
                this.killVictim(msg);
            } else {
            	printMessage(msg); // 승패에 따른 경기 종료 메세지.
            }
        });
    }

    sendVoteResult(userName, theVoted) {
        const data = {
            "userName": userName,
            "theVoted": theVoted
        };
        this.stompClient.send(this.destinationUrl, JSON.stringify(data));
    }

    killVictim(theVoted) {
        Array.from(document.getElementsByClassName("slot_layout")).forEach(slot => {
            if (slot.getAttribute("data-id") === theVoted) {
                slot.classList.add("dead");
                slot.getElementsByClassName("player_status")[0].textContent = "";
            }
        });
    }

    gameIsFinished() {
        return this.isFinished;
    }
}
