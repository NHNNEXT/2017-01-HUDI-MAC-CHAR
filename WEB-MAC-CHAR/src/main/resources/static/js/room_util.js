export const textArea = document.getElementById("chatOutput");

export function syncScroll() {
    textArea.scrollTop = textArea.scrollHeight;
}

export function printMessage(message) {
    textArea.value += `[ [ [    ${message}    ] ] ]
`;
    syncScroll();
}
