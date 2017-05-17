export default class gameStartSocket {
    constructor(dayTime, nightTime, VoteSocket) {
        this.ENTER_KEY = 13;
        this.userName = document.getElementById("userName").textContent;
        this.readyBtn = document.getElementById("readyBtn");
        this.dayTime = dayTime;
        this.nightTime = nightTime;
        this.voteSocket = VoteSocket;
    }

    connect(stompClient, gameStart_url, destinationUrl) {
        console.log('DESTINATION URL: ' + destinationUrl);
        this.stompClient = stompClient;
        this.destinationUrl = destinationUrl;
        stompClient.subscribe(gameStart_url, gameStart => {
            document.querySelector('.player_role_name').textContent = `은 ${gameStart.body}입`;
            Array.from(document.getElementsByClassName("player_status")).forEach(s => {
    			s.innerHTML = "";
    		}); // Ready 지워주기
//            this.dayTime.init();
//            this.dayTime.dayTimerStart();
            // 밤 시간대부터 시작한다고 가정
            this.nightTime.init(gameStart.body);
            this.nightTime.nightTimerStart();
        });
    }
}

export function sendGameStart() {
    let msg = {
        "userName": this.userName,
    };
    this.readyBtn.style.display = 'none';
    console.log(msg);
    this.stompClient.send(this.destinationUrl, JSON.stringify(msg));
}
