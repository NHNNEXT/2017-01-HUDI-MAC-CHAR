import SockJS from 'sockjs-client';
import Stomp from 'webstomp-client';

export default class connectSocket {
    constructor(chatSocket, readySocket) {
        this.chatSocket = chatSocket;
        this.readySocket = readySocket;
        this.chatSocket.init();
        this.readySocket.init();

        this.ENTER_KEY = 13;

        this.SERVER_SOCKET_API = "/websockethandler";
        this.FROM_CHAT_API = "/from/chat/";
        this.TO_CHAT_API = "/to/chat/";
        this.TO_READY_API = "/to/ready/";
        this.FROM_READY_API = "/from/ready/";

        this.stompClient;
    }

    init() {
        document.addEventListener("DOMContentLoaded", this.connect.bind(this));
    }

    connect() {
        const socket = new SockJS(this.SERVER_SOCKET_API);
        const chat_url = this.FROM_CHAT_API + this.getRoomId();
        const ready_url = this.FROM_READY_API + this.getRoomId();

        this.stompClient = Stomp.over(socket);

        this.stompClient.connect({}, () => {
            this.chatSocket.connect(this.stompClient, chat_url, this.TO_CHAT_API + this.getRoomId());
            this.readySocket.connect(this.stompClient, ready_url, this.TO_READY_API + this.getRoomId());
        });
    }

    getRoomId() {
        const roomId = document.getElementById("roomId").textContent;
        const parsed = roomId.split(".");
        return parsed[1];
    }
}
