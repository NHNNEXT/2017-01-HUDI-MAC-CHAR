import SockJS from 'sockjs-client';
import Stomp from 'webstomp-client';

export default class socket {
    constructor() {
        this.ENTER_KEY = 13;

        this.SERVER_SOCKET_API = "/websockethandler";
        this.FROM_CHAT_API = "/from/chat/";
        this.TO_CHAT_API = "/to/chat/";

        this.inputElm = document.getElementById("chatInput");
        this.userName = document.getElementById("userName").textContent;
        this.textArea = document.getElementById("chatOutput");

        this.stompClient;
    }

    init() {
        document.addEventListener("DOMContentLoaded", this.connect.bind(this));
        this.inputElm.addEventListener("keydown", this.chatKeyDownHandler.bind(this));
    }

    connect() {
        console.log(this);
        let socket = new SockJS(this.SERVER_SOCKET_API);
        let chat_url = this.FROM_CHAT_API + this.getRoomId();

        this.stompClient = Stomp.over(socket);
        console.log(this.stompClient);
        this.stompClient.connect({}, () => {
            this.stompClient.subscribe(chat_url, message => {
                this.printMessage(JSON.parse(message.body));
            });
        });
    }

    chatKeyDownHandler(e) {
        if (e.which === this.ENTER_KEY && this.inputElm.value.trim() !== "") {
            this.sendMessage(this.userName, this.inputElm.value);
            this.inputElm.value = "";
        }
    }
    sendMessage(userName, text) {
        let destinationUrl = this.TO_CHAT_API + this.getRoomId();
        let msg = {
            "content": text,
            "userName": userName
        };
        this.stompClient.send(destinationUrl, JSON.stringify(msg));
    }

    printMessage(message) {
        this.syncScroll();
        let msg = `${message.userName}:\t${message.content}\n`;
        this.textArea.value += msg;
    }

    getRoomId() {
        let roomId = document.getElementById("roomId").textContent;
        let parsed = roomId.split(".");
        return parsed[1];
    }

    syncScroll() {
        this.textArea.scrollTop = this.textArea.scrollHeight;
    }
}
