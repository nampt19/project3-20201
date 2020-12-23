$(document).ready(function () {
    checkAccount();
});

function checkAccount() {
    $("#btnLogIn").click(function () {
        var account = {
            email: $("#email").val(),
            password: $("#password").val(),
        };
        console.table(account);
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "http://localhost:8080/user/login",
            dataType: "json",
            data: JSON.stringify(account),
            success: function (data) {
                console.log(data);
                if (data.code == "100") {
                    createCookie("pageActions", JSON.stringify(data.pageActions), 30);
                    createCookie("token", data.token, 30);
                    location.assign("dashboard.html");
                } else if (data.code == "102") {
                    $.notify(data.message, {
                        position: "top center",
                        className: "error"
                    });
                } else if (data.code == "103") {
                    $.notify(data.message, {
                        position: "top center",
                        className: "error"
                    });
                } else {
                    $.notify(data.message, {
                        position: "top center",
                        className: "error"
                    });
                }
            },
            error: function (e) {
                console.log(e);
            },
        });
    });
}