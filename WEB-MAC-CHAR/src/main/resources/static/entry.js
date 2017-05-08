import connect from "./js/connect";

import chatSocket from "./js/chatSocket";
import readySocket from "./js/readySocket";

(function() {
    new connect(
        new chatSocket(),
        new readySocket()
    ).init();
})();
