
$(document).ready(function () {
    logOut();
});

function logOut() {
    $("#btnLogout").click(function () {
        $.ajax({
            url: "http://localhost:8080/user/logout",
            method: "POST",
            beforeSend: function (xhr) {
                var token = getCookie("token");
                xhr.setRequestHeader("Authorization", token);
            },
            success: function (data) {
                console.log(data);
                $.notify(data.message, {
                    position: "top center",
                    className: "success",
                });
                document.cookie = "token=; expires=Thu, 01 Jan 1970 00:00:00 UTC";
                document.cookie = "pageActions=; expires=Thu, 01 Jan 1970 00:00:00 UTC";
                location.replace("login.html");
            },
            error: function (e) {
                document.cookie = "token=; expires=Thu, 01 Jan 1970 00:00:00 UTC";
                document.cookie = "pageActions=; expires=Thu, 01 Jan 1970 00:00:00 UTC";
                console.log(e);
                location.replace("login.html");
            },
        });
    });
}


