export const textArea = document.getElementById("chatOutput");

export function syncScroll() {
    textArea.scrollTop = textArea.scrollHeight;
}
