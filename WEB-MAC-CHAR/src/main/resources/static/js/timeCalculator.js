export default class timeCalculator {
    constructor() {
        this.ENTER_KEY = 13;
        this.SECOND_BY_MILLISECOND = 1000;
        this.MINUITE_BY_SECOND = 60;
    }

    startTimer(time, nextAction) {
        this.leftTime = time;
        this.nextAction = nextAction;
        this.runningTimer = setInterval(this.timer.bind(this), this.SECOND_BY_MILLISECOND);
    }

    timer() {
        document.querySelector('.timer').innerText = this.timeFormat(this.leftTime--);
        if (this.leftTime < 0) {
            clearInterval(this.runningTimer);
            this.nextAction();
        }
    }

    timeFormat(leftTime) {
        let leftMinutes = parseInt(leftTime / this.MINUITE_BY_SECOND);
        let leftSeconds = leftTime % this.MINUITE_BY_SECOND;
        return `${this.buildTimeFormat(leftMinutes)}:${this.buildTimeFormat(leftSeconds)}`;
    }

    buildTimeFormat(num) {
        if (num.toString().length < 2) {
            return `0${num}`;
        }
        return num;
    }
}
