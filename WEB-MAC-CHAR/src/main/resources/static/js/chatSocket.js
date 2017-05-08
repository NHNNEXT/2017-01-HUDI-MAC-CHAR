import { textArea, syncScroll } from './room_util';

export default class chatSocket {
    constructor() {
        this.ENTER_KEY = 13;

        this.inputElm = document.getElementById("chatInput");
        this.userName = document.getElementById("userName").textContent;
    }

    init() {
        this.inputElm.addEventListener("keydown", this.chatKeyDownHandler.bind(this));
    }

    connect(stompClient, chat_url, destinationUrl) {
        this.stompClient = stompClient;
        this.destinationUrl = destinationUrl;
        stompClient.subscribe(chat_url, message => {
            this.printMessage(JSON.parse(message.body));
        });
    }

    chatKeyDownHandler(e) {
        if (e.which === this.ENTER_KEY && this.inputElm.value.trim() !== "") {
            this.sendMessage(this.userName, this.inputElm.value);
            this.inputElm.value = "";
        }
    }
    
    sendMessage(userName, text) {
        const msg = {
            "content": text,
            "userName": userName
        };
        this.stompClient.send(this.destinationUrl, JSON.stringify(msg));
    }

    printMessage(message) {
        syncScroll();
        const msg = `${message.userName}:\t${message.content}\n`;
        textArea.value += msg;
    }
}