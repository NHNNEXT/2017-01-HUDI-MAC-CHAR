import {printMessage} from "./room_util";

export default class voteSocket {
    connect(stompClient, vote_url, destinationUrl) {

        this.stompClient = stompClient;
        this.destinationUrl = destinationUrl;

        this.isFinished = false;

        this.stompClient.subscribe(vote_url, gameResult => {
            let {msg, finished, completeVote, roleString} = JSON.parse(gameResult.body);
            this.isFinished = finished;
            if (completeVote) {//모든 클라이언트가 투표를 했는지 검사하는 flag
                if (!finished) {//승패 여부를 판단하는 flag
                    if (msg === "") {
                        console.log(typeof msg);
                        printMessage(`아무도 죽지 않았습니다.`);
                    } else {
                        printMessage(`${msg}가 죽었습니다.`);
                        this.killVictim(msg);
                    }
                } else {
                    printMessage(msg);
                    for (let line of roleString.split("&")) {
                        let [name, role] = line.split(":");
                        printMessage(`${name} 님은 ${role} 였습니다.`);
                    }
                    // "마피아가 승리하였습니다." or "시민이 승리하였습니다."
                }
            }
        });
    }

    sendVoteResult(userName, theVoted, stage) {
        const data = {
            "userName": userName,
            "theVoted": theVoted,
            "stage": stage
        };
        this.stompClient.send(this.destinationUrl, JSON.stringify(data));
    }

    killVictim(theVoted) {
        if (theVoted === null) {
            console.log(`theVoted is null!!`);
            return;
        }

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
