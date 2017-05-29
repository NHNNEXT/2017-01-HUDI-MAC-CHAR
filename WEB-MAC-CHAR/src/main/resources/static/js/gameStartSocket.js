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
        this.stompClient = stompClient;
        this.destinationUrl = destinationUrl;
        stompClient.subscribe(gameStart_url, gameStart => {
            document.querySelector('.player_role_name').textContent = `은 ${gameStart.body}입`;
            this.showMission(gameStart.body);
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

    showMission(role) {
        let mission;
        let skill;
        switch(role) {
            case "Citizen" :
                mission = "당신은 도시에 살고있는 평범한 시민입니다. 다른 시민들과 힘을 합쳐 도시의 모든 마피아를 제거해야 합니다.";
                skill = "없음";
                break;
            case "Mafia" :
                mission = "당신은 도시를 지배하려는 마피아입니다. 전체 플레이어 중 마피아가 반 이상이 되도록 다른 플레이어들을 제거해야 합니다."
                skill = "매일 밤 마피아끼리 투표를 통해 한 명의 플레이어를 죽일 수 있습니다";
                break;
            case "Police" :
                mission = "당신은 도시를 지키는 경찰입니다. 다른 시민들과 힘을 합쳐 도시의 모든 마피아를 제거해야 합니다.";
                skill = "매일 밤 마피아로 의심되는 플레이어를 용의자로 지목하여 수사를 통해 그의 직업을 알아낼 수 있습니다.";
                break;
            case "Doctor" :
                mission = "당신은 도시의 부상자들을 치료하는 의사입니다. 다른 시민들과 힘을 합쳐 도시의 모든 마피아를 제거해야 합니다.";
                skill = "매일 밤 마피아에 의해 살해될 것 같은 사람을 지목해 치료합니다.";
                break;

        }
        const introRole = `<p class="player_mission_title">Mission</p> <p class="player_mission_description">${mission}</p> 
                          <p class="player_mission_title">Skill</p> <p class="player_mission_description">${skill}</p>`;

        document.querySelector('#profile_table').innerHTML = introRole;
    }
}

export function sendGameStart() {
    let msg = {
        "userName": this.userName,
    };
    this.stompClient.send(this.destinationUrl, JSON.stringify(msg));
}
