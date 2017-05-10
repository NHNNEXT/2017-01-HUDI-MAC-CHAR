import connect from "./js/connect";

import chatSocket from "./js/chatSocket";
import readySocket from "./js/readySocket";
import accessSocket from "./js/accessSocket";
import gameStartSocket from "./js/gameStartSocket";
//import socket from "./js/socket";

(function() {
	const GameStartSocket = new gameStartSocket();
	
    new connect(
//    		new socket()
        new chatSocket(),
        new readySocket(GameStartSocket),
        new accessSocket(),
        GameStartSocket
    ).init();
})();
