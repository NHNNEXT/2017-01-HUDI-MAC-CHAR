import {textArea, syncScroll} from './room_util';

export default class accessSocket {
    constructor() {
        this.PLAYER_NOT_READY = "player_not_ready";

        this.userName = document.getElementById("userName").textContent;
        this.exitBtn = document.getElementById("exit_button");
    }

    init() {
        this.exitBtn.addEventListener("click", this.disconnect.bind(this));
    }

    connect(stompClient, access_url, destinationUrl) {
        this.stompClient = stompClient;
        this.destinationUrl = destinationUrl;
        stompClient.subscribe(access_url, access => {
            this.showUserList(JSON.parse(access.body));
        });
    }

    sendAccess(userName, access) {
        let msg = {
            "userName": userName,
            "access": access
        };
        this.stompClient.send(this.destinationUrl, JSON.stringify(msg));
    }

    showUserList(access) {
        if (access.access === "enter") {
            let slots = document.querySelectorAll(".slot_layout");
            for (let slot of slots) {
                slot.querySelector(".player_name").textContent = "";
                slot.querySelector(".player_status").classList.add(this.PLAYER_NOT_READY);
                slot.removeAttribute("data-id");
                slot.classList.add("empty_slot");
            }
            for (let user of access.users) {
                let emptySlot = document.querySelector(".empty_slot");
                emptySlot.querySelector(".player_name").textContent = user.nickname;
                if (user.status === "READY") {
                    emptySlot.querySelector(".player_status").classList.remove(this.PLAYER_NOT_READY);
                }
                emptySlot.setAttribute("data-id", user.nickname);
                emptySlot.classList.remove("empty_slot");
            }
            syncScroll();
            let msg = `${access.userName} 님이 입장하셨습니다.\n`;
            textArea.value += msg;
        } else {
            let userSlot = document.querySelector(`[data-id=${access.userName}]`);
            userSlot.querySelector(".player_name").textContent = "";
            userSlot.querySelector(".player_status").classList.add(this.PLAYER_NOT_READY);
            userSlot.removeAttribute("data-id");
            userSlot.classList.add("empty_slot");
            syncScroll();
            let msg = `${access.userName} 님이 퇴장하셨습니다.\n`;
            textArea.value += msg;
        }
    }

    disconnect() {
        this.sendAccess(this.userName, "exit");
        if (stompClient != null) {
            stompClient.disconnect();
        }
        console.log("Disconnected");
    }
}
