$(document).ready(function () {
    showandhide();
});

function showandhide() {
    var pageActions = JSON.parse(getCookie("pageActions"));
    var str =
        "<li> <a href='dashboard.html'><i class='fas fa-home'></i> Trang chủ</a> </li>";
    $("#sidebar ul").append(str);
    for (var i = 0; i < pageActions.length; i++) {
        if (pageActions[i].page.name == "role") {
            pageName = "Quản lý vai trò";
            var str =
                "<li> <a href='role.html'><i class='fas fa-user'></i> " +
                pageName +
                "</a> </li>";
            $("#sidebar ul").append(str);
        } else if (pageActions[i].page.name == "reset_password") {
            pageName = "Reset mật khẩu";
            var str =
                "<li> <a href='forgot-password.html'><i class='fas fa-user-friends'></i> " +
                pageName +
                "</a> </li>";
            $("#sidebar ul").append(str);
        }
    }
}
