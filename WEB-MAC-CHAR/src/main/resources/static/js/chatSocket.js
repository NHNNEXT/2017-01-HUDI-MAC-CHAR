export default class chatSocket {
    constructor() {
        this.ENTER_KEY = 13;

        this.inputElm = document.getElementById("chatInput");
        this.userName = document.getElementById("userName").textContent;
        this.textArea = document.getElementById("chatOutput");
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
        this.syncScroll();
        const msg = `${message.userName}:\t${message.content}\n`;
        this.textArea.value += msg;
    }

    syncScroll() {
        this.textArea.scrollTop = this.textArea.scrollHeight;
    }
}
