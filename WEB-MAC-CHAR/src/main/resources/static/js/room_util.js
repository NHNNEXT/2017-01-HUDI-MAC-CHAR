export const textArea = document.getElementById("chatOutput");

export function syncScroll() {
    textArea.scrollTop = textArea.scrollHeight;
}

export function printMessage(message) {
    syncScroll();
    let msg = '\n';
    const howRepeat = 40 - message.length;
    for (let i = 0; i < howRepeat; i++) {
        msg += '=';
    }
    msg += message;
    for (let i = 0; i < howRepeat; i++) {
        msg += '=';
    }
    message + '\n';
    textArea.value += msg;
}
