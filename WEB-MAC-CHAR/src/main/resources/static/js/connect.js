import SockJS from 'sockjs-client';
import Stomp from 'webstomp-client';

export default class connectSocket {
    constructor(chatSocket, readySocket, accessSocket, gameStartSocket, voteSocket, investSocket) {
        this.chatSocket = chatSocket;
        this.readySocket = readySocket;
        this.accessSocket = accessSocket;
        this.gameStartSocket = gameStartSocket;
        this.voteSocket = voteSocket;
        this.investSocket = investSocket;

        this.chatSocket.init();
        this.readySocket.init();
        this.accessSocket.init();

        this.ENTER_KEY = 13;

        this.SERVER_SOCKET_API = "/websockethandler";

        this.TO_CHAT_API = "/to/chat/";
        this.TO_READY_API = "/to/ready/";
        this.TO_ACCESS_API = "/to/access/";
        this.TO_GAME_START_API = "/to/gameStart/";
        this.TO_VOTE_API = "/to/vote/";
        this.TO_INVEST_API = "/to/invest/";

        this.FROM_CHAT_API = "/from/chat/";
        this.FROM_READY_API = "/from/ready/";
        this.FROM_ACCESS_API = "/from/access/";
        this.FROM_GAME_START_API = "/from/gameStart/";
        this.FROM_VOTE_API = "/from/vote/";
        this.FROM_INVEST_API = "/from/invest/";

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
        const vote_url = this.FROM_VOTE_API + this.getRoomId();
        const invest_url = this.FROM_INVEST_API + this.getRoomId() + "/" + this.userName;

        this.stompClient = Stomp.over(socket);

        this.stompClient.connect({}, () => {
            this.chatSocket.connect(this.stompClient, chat_url, this.TO_CHAT_API + this.getRoomId());
            this.readySocket.connect(this.stompClient, ready_url, this.TO_READY_API + this.getRoomId());
            this.gameStartSocket.connect(this.stompClient, game_start_url, this.TO_GAME_START_API + this.getRoomId() + "/" + this.userName);
            this.accessSocket.connect(this.stompClient, access_url, this.TO_ACCESS_API + this.getRoomId());
            this.voteSocket.connect(this.stompClient, vote_url, this.TO_VOTE_API + this.getRoomId());
            this.investSocket.connect(this.stompClient, invest_url, this.TO_INVEST_API + this.getRoomId() + "/" + this.userName);
        });
    }

    getRoomId() {
        const roomId = document.getElementById("roomId").textContent;
        const parsed = roomId.split(".");
        return parsed[1];
    }
}
