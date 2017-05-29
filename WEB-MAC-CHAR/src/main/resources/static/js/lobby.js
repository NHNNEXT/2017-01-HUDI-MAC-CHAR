window.onload = () => {
    init();
}

function init() {
    let dimmer = document.querySelector("#dimmer");
    let eachRoom = document.querySelector(".eachRoom");
    let refreshRoom = document.querySelector("#btn_refresh");
    let logoutBtn = document.querySelector("#logout");
    const loginPage = "/login";

    logoutBtn.addEventListener("click", tryLogout);

    refreshRoom.addEventListener("click", function () {
        window.location.href = "/lobby";
    });

    document.querySelector("#make_room").addEventListener("click", () => {
        dimmer.style.display = "block";
        document.querySelector("#make_room_form").style.display = "block";
    });

    dimmer.addEventListener("click", function () {
        dimmer.style.display = "none";
        offModal();
    });

    document.querySelector(".rooms_list").addEventListener("click", forwardEachRoom);

    function forwardEachRoom(e) {
        let target = e.target;
        if (target.tagName.toLowerCase() === "td") {
            window.location.href = "/room/" + target.parentNode.id;
        }
    }

    function offModal() {
        let modals = document.querySelectorAll(".my_modal");
        for (modal of modals) {
            modal.style.display = "none";
        }
    }

    function tryLogout(e) {
        e.preventDefault();

        let url = logoutBtn.getAttribute("href");

        $.ajax({
            type: "get",
            url: url
        }).done(function (data, status) {
            location.href = loginPage;
        }).fail(function (jQueryXhr, status) {
            alert("failed to logout.");
        });
    }
}
