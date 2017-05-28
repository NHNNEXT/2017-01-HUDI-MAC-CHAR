import {printMessage} from "./room_util";

export default class voteSocket {
    connect(stompClient, vote_url, destinationUrl) {

        this.stompClient = stompClient;
        this.destinationUrl = destinationUrl;

        this.isFinished = false;

        stompClient.subscribe(vote_url, gameResult => {
            let {msg, finished, completeVote} = JSON.parse(gameResult.body);
            this.isFinished = finished;
            if (completeVote) {
                console.log(`투표가 완료되었습니다.`);
                if (!finished) {
                    this.killVictim(msg);
                    if (msg !== "") {
                        printMessage(`${msg}가 죽었습니다.`);
                    }
                } else {
                    printMessage(msg);
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
        console.log(`   voteSocket::theVoted: ${theVoted}`);
        if (theVoted === null) {
            return;
        }

        if (theVoted === "") {
            printMessage(`아무도 죽지 않았습니다.`);
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
