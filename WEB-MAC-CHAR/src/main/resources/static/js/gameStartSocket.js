export default class gameStartSocket {
    constructor(VoteSocket, dayTime, nightTime) {
        this.ENTER_KEY = 13;
        this.userName = document.getElementById("userName").textContent;
        this.readyBtn = document.getElementById("readyBtn");
        this.voteSocket = VoteSocket;
        this.dayTime = dayTime;
        this.nightTime = nightTime;
    }

    connect(stompClient, gameStart_url, destinationUrl) {
        console.log("DESTINATION URL: " + destinationUrl);
        this.stompClient = stompClient;
        this.destinationUrl = destinationUrl;
        stompClient.subscribe(gameStart_url, gameStart => {
            document.querySelector('.player_role_name').textContent = `은 ${gameStart.body}입`;
            Array.from(document.getElementsByClassName("player_status")).forEach(status => status.textContent = "");
            this.play(gameStart.body);
        });
    }

    play(role) {
        this.nightTime.setRole(role);
        this.dayTime.setNight(this.nightTime);
        this.nightTime.setDay(this.dayTime);

        this.dayTime.start();
    }
}

export function sendGameStart() {
    let msg = {
        "userName": this.userName,
    };
    this.readyBtn.style.display = "none";
    console.log(msg);
    this.stompClient.send(this.destinationUrl, JSON.stringify(msg));
}
