import SockJS from 'sockjs-client';
import Stomp from 'webstomp-client';

export default class connectSocket {
    constructor(chatSocket, readySocket, accessSocket, gameStartSocket) {
        this.chatSocket = chatSocket;
        this.readySocket = readySocket;
        this.accessSocket = accessSocket;
        this.gameStartSocket = gameStartSocket;

        this.chatSocket.init();
        this.readySocket.init();
        this.accessSocket.init();

        this.ENTER_KEY = 13;

        this.SERVER_SOCKET_API = "/websockethandler";

        this.TO_CHAT_API = "/to/chat/";
        this.TO_READY_API = "/to/ready/";
        this.TO_ACCESS_API = "/to/access/";
        this.TO_GAME_START_API = "/to/gameStart/";

        this.FROM_CHAT_API = "/from/chat/";
        this.FROM_READY_API = "/from/ready/";
        this.FROM_ACCESS_API = "/from/access/";
        this.FROM_GAME_START_API = "/from/gameStart/";

        this.stompClient;
    }

    init() {
        document.addEventListener("DOMContentLoaded", this.connect.bind(this));
        this.userName = document.getElementById("userName").textContent;
    }

    connect() {
        const socket = new SockJS(this.SERVER_SOCKET_API);
        const chat_url = this.FROM_CHAT_API + this.getRoomId();
        const ready_url = this.FROM_READY_API + this.getRoomId();
        const access_url = this.FROM_ACCESS_API + this.getRoomId();
        const game_start_url = this.FROM_GAME_START_API + this.getRoomId() + "/" + this.userName;

        this.stompClient = Stomp.over(socket);

        this.stompClient.connect({}, () => {
            this.chatSocket.connect(this.stompClient, chat_url, this.TO_CHAT_API + this.getRoomId());
            this.readySocket.connect(this.stompClient, ready_url, this.TO_READY_API + this.getRoomId());
            this.gameStartSocket.connect(this.stompClient, game_start_url, this.TO_GAME_START_API + this.getRoomId() + "/" + this.userName);
            this.accessSocket.connect(this.stompClient, access_url, this.TO_ACCESS_API + this.getRoomId());
        });
    }

    getRoomId() {
        const roomId = document.getElementById("roomId").textContent;
        const parsed = roomId.split(".");
        return parsed[1];
    }
}
