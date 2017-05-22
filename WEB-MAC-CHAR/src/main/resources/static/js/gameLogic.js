import timeCalculator from "./timeCalculator";

export default class gameLogic {
    constructor(dayTime, nightTime) {
	this.dayTime = dayTime;
	this.nightTime = nightTime;
	this.timeCalculator = new timeCalculator();
    }
    
    start(role) {
	this.dayTime.init();
//	setTimeout(() => {
//	    this.nightTime.init(role);    
//	}, 15000);
    }
}