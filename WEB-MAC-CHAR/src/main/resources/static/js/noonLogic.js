import {textArea, syncScroll, printMessage} from "./room_util";
import timeCalculator from "./timeCalculator";
import voteManager from "./voteManager";

export function startNoon() {
    document.body.classList.remove("main");
    document.body.classList.add("noon");
    printMessage("마피아로 의심되는 사람에게 투표해주세요");
    let noonTimeCalculator = new timeCalculator();
    noonTimeCalculator.startTimer(60);
    let noonTimeVoteManager = new voteManager();
    noonTimeVoteManager.init();
}
