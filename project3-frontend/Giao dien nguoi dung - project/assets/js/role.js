$(document).ready(function () {
    // get RoleList to myFrom
    $.ajax({
        url: "http://localhost:8080/role/roleList",
        method: "GET",
        beforeSend: function (xhr) {
            var token = getCookie("token");
            xhr.setRequestHeader("Authorization", token);
        },
    }).done(function (data) {
        var str = "";
        for (var i = 1; i < data.roleList.length; i++) {
            str +=
                "<tr class='text-center'>" +
                "<td>" +
                data.roleList[i].id +
                "</td>" +
                "<td>" +
                data.roleList[i].name +
                "</td>" +
                "<td>" +
                "<button class='btn btn-outline-info btn-rounded'" +
                " onclick='openNavToEdit(this);'><i class='fas fa-pen'></i></button>" +
                " <button class='btn btn-outline-danger btn-rounded'" +
                "onclick='removeRow(this);'><i class='fas fa-trash'></i></button>" +
                "</td>";
            ("</tr>");
        }
        $("#dataTables-example").find("tbody").append(str);
        $("#dataTables-example").DataTable();
    });
});

function addRow() {
    if (!checkEmptyInput()) {
        var nameRole = $("#name").val();
        var pageActions = createpageActionList();
        $.ajax({
            type: "POST",
            url: "http://localhost:8080/role/createRole",
            contentType: "application/json",
            data: JSON.stringify({
                nameRole: nameRole,
                pageActions: pageActions,
            }),
            beforeSend: function (xhr) {
                var token = getCookie("token");
                xhr.setRequestHeader("Authorization", token);
            },
            success: function (data) {
                //alert(data.message);
                if (data.code == "100") {
                    $.notify(data.message, {
                        position: "top center",
                        className: "success",
                    });
                    // sửa ở phía client
                    var table = $("#dataTables-example").DataTable();
                    table.row
                        .add([
                            data.idRole,
                            nameRole,
                            "<button class='btn btn-outline-info btn-rounded'" +
                            " onclick='openNavToEdit(this);'><i class='fas fa-pen'></i></button>" +
                            " <button class='btn btn-outline-danger btn-rounded'" +
                            "onclick='removeRow(this);'><i class='fas fa-trash'></i></button>",
                        ])
                        .draw(false);
                    closeNav();
                } else {
                    $.notify(data.message, {
                        position: "top center",
                        className: "error",
                    });
                }
            },
        });
    }

}

function createpageActionList() {
    var formSwitch = document.getElementById("formSwitch");
    var pageActions = formSwitch.getElementsByClassName("pageAction");
    var pageActionList = new Array();
    for (var i = 0; i < pageActions.length; i++) {
        var actionList = new Array();

        var idPage = pageActions[i].getElementsByClassName("pageSwitch")[0].value;
        if (pageActions[i].getElementsByClassName("pageSwitch")[0].checked == true) {
            var page = {
                id: idPage,
                name: "",
                note: ""
            };
            var actionRead = {
                id: 2,
                name: ""
            };
            actionList.push(actionRead);
        }

        var actionSwitchs = pageActions[i].getElementsByClassName("actionSwitch");
        for (j = 0; j < actionSwitchs.length; j++) {
            var idAction = actionSwitchs[j].value;
            if (actionSwitchs[j].checked == true) {
                var action = {
                    id: idAction,
                    name: ""
                };
                actionList.push(action);
            }
        }

        var pageAction = {
            page: page,
            actions: actionList
        };
        pageActionList.push(pageAction);
    }
    return pageActionList;
}
var rowIndexEdit, idEdit;

function editRow() {
    if (!checkEmptyInput()) {
        var nameRole = $("#name").val();
        var pageActions = createpageActionList();
        $.ajax({
            type: "POST",
            url: "http://localhost:8080/role/editRole",
            contentType: "application/json",
            data: JSON.stringify({
                idRole: idEdit,
                nameRole: nameRole,
                pageActions: pageActions,
            }),
            beforeSend: function (xhr) {
                var token = getCookie("token");
                xhr.setRequestHeader("Authorization", token);
            },
            success: function (data) {
                //alert(data.message);
                if (data.code == "100") {
                    $.notify(data.message, {
                        position: "top center",
                        className: "success",
                    });
                    // sửa ở phía client
                    var table = $("#dataTables-example").DataTable();
                    table
                        .row(rowIndexEdit)
                        .data([
                            data.idRole,
                            nameRole,
                            "<button class='btn btn-outline-info btn-rounded'" +
                            " onclick='openNavToEdit(this);'><i class='fas fa-pen'></i></button>" +
                            " <button class='btn btn-outline-danger btn-rounded'" +
                            "onclick='removeRow(this);'><i class='fas fa-trash'></i></button>",
                        ])
                        .draw();
                    closeNav();
                } else {
                    $.notify(data.message, {
                        position: "top center",
                        className: "error",
                    });
                }
            },
        });
    }
}

// xóa Role khi nhấn button xóa (onclick)

function removeRow(button) {
    var table = $("#dataTables-example").DataTable();
    //lấy ra Role

    var rowDelete = table.row($(button).parents("td").parents("tr")).index();
    // lấy giá trị trong cột 0:id
    idDelete = table.row(rowDelete).data()[0];
    console.log(idDelete);

    if (
        confirm(
            "CẢNH BÁO: Những người dùng có quyền này sẽ không thể truy cập vào bất cứ trang nào ! \nBạn có chắc muốn xóa quyền này không ?"
        )
    ) {
        // xóa luôn quyền ở phía server !
        $.ajax({
            type: "POST",
            url: "http://localhost:8080/role/deleteRole/" + idDelete,
            beforeSend: function (xhr) {
                var token = getCookie("token");
                xhr.setRequestHeader("Authorization", "Bearer " + token);
            },
            success: function (data) {
                // xóa luôn Quyền ở phía client

                if (data.code == "100") {
                    $.notify(data.message, {
                        position: "top center",
                        className: "success",
                    });
                    table.row($(button).parents("td").parents("tr")).remove().draw();
                } else {
                    $.notify(data.message, {
                        position: "top center",
                        className: "error",
                    });
                }
            },
        });
    }
}

// ---------------------------------------------------------------------------------------------------------------------
// các hàm phục vụ cho việc check dữ liệu, clear dữ liệu khi bật FormData,
// đóng mở form ,dán dữ liệu từ hàng vào form,................ ở đây nhé !
// ---------------------------------------------------------------------------------------------------------


// hàm mở form
function openNav() {
    //hàm xóa sạch thông tin khi mở form thêm mới !
    removedRowToInput();
    clearSwitch();

    document.getElementById("myForm").style.height = "80%";
    document.getElementById("overlay2").style.display = "block";
    // vô hiệu hóa nút sửa vì ta đang cần thêm !
    document.getElementById("editRow").style.display = "none";
    document.getElementById("addRow").style.display = "inline-block";

    document.getElementById("nhanvien").classList.remove("show");
    document.getElementById("khachhang").classList.remove("show");
    document.getElementById("sanpham").classList.remove("show");
    document.getElementById("giaodich").classList.remove("show");
    document.getElementById("chamsoc").classList.remove("show");
}

// hàm đóng form(dùng cho các nút X, thoát)
function closeNav() {
    document.getElementById("myForm").style.height = "0%";
    document.getElementById("overlay2").style.display = "none";
}


// clear data form khi bật nút add user !
function removedRowToInput() {
    document.getElementById("name").value = "";
}
// clear các switch khi mở form infor
function clearSwitch() {
    var formSwitch = document.getElementById("formSwitch");
    var pageAction = formSwitch.getElementsByClassName("pageAction");
    for (var i = 0; i < pageAction.length; i++) {
        pageAction[i].getElementsByClassName("pageSwitch")[0].checked = false;
        var actionSwitch = pageAction[i].getElementsByClassName("actionSwitch");
        for (j = 0; j < actionSwitch.length; j++) {
            actionSwitch[j].checked = false;
        }
    }
}


// check the empty input
function checkEmptyInput() {
    var isEmptyName = true, isEmptyRole = true;
    name = document.getElementById("name").value;

    if (name === "") {
        $.notify("Chưa nhập tên !", {
            position: "top center",
            className: "warn",
        });
        isEmptyName = true;
        return isEmptyRole;
    }
    var formSwitch = document.getElementById("formSwitch");
    var pageAction = formSwitch.getElementsByClassName("pageAction");
    var count = 0;
    for (var i = 0; i < pageAction.length; i++) {
        if (pageAction[i].getElementsByClassName("pageSwitch")[0].checked == true) {
            isEmptyRole = false;
            count++;
            break;
        }
    }
    if (count == 0) {
        $.notify("Chưa chọn quyền !", {
            position: "top center",
            className: "warn",
        });
    }
    return isEmptyRole;
}


// load thông tin từ hàng vào form khi nhấn icon sửa (hình bút !)
function selectedRowToInput() {
    var table = document.getElementById("dataTables-example");
    for (var i = 1; i < table.rows.length; i++) {
        table.rows[i].onclick = function () {
            // get the seected row index
            document.getElementById("role_id").value = this.cells[0].innerHTML;
            document.getElementById("name").value = this.cells[1].innerHTML;
        };
    }
}


// mở form để edit
function openNavToEdit(button) {
    document.getElementById("myForm").style.height = "80%";
    document.getElementById("overlay2").style.display = "block";
    // vô hiệu hóa nút thêm vì ta đang cần sửa !
    document.getElementById("editRow").style.display = "inline-block";
    document.getElementById("addRow").style.display = "none";
    //hàm đưa thông tin từ hàng vào form !
    selectedRowToInput();

    var table = $("#dataTables-example").DataTable();
    //lấy ra hàng
    rowIndexEdit = table.row($(button).parents("td").parents("tr")).index();
    // lấy chỉ mục dòng
    //var rowNumber = row.rowIndex;
    // lấy giá trị trong cột 0:id
    idEdit = table.row(rowIndexEdit).data()[0];
    console.log(rowIndexEdit);
    console.log(idEdit);
    $.ajax({
        url: "http://localhost:8080/role/getRolePageAction/" + idEdit,
        method: "GET",
        beforeSend: function (xhr) {
            var token = getCookie("token");
            xhr.setRequestHeader("Authorization", token);
        },
    }).done(function (data) {
        if (data.code == 100) {
            clearSwitch();
            var formSwitch = document.getElementById("formSwitch");
            var pageAction = formSwitch.getElementsByClassName("pageAction");
            if (data.rolePageActionList != null) {
                for (var i = 0; i < data.rolePageActionList.length; i++) {
                    for (var j = 0; j < pageAction.length; j++) {
                        if (
                            data.rolePageActionList[i].idPage ==
                            pageAction[j].getElementsByClassName("pageSwitch")[0].value
                        ) {
                            pageAction[j].getElementsByClassName(
                                "pageSwitch"
                            )[0].checked = true;
                            var actionSwitch = pageAction[j].getElementsByClassName(
                                "actionSwitch"
                            );
                            for (k = 0; k < actionSwitch.length; k++) {
                                if (
                                    data.rolePageActionList[i].idAction == actionSwitch[k].value
                                ) {
                                    actionSwitch[k].checked = true;
                                }
                            }
                        }
                    }
                }
            }
            if (document.getElementById("switchUser").checked == true) {
                document.getElementById("nhanvien").classList.add("show");
            } else {
                document.getElementById("nhanvien").classList.remove("show");
            }
            if (document.getElementById("switchCustomer").checked == true) {
                document.getElementById("khachhang").classList.add("show");
            } else {
                document.getElementById("khachhang").classList.remove("show");
            }
            if (document.getElementById("switchProduct").checked == true) {
                document.getElementById("sanpham").classList.add("show");
            } else {
                document.getElementById("sanpham").classList.remove("show");
            }
            if (document.getElementById("switchTransaction").checked == true) {
                document.getElementById("giaodich").classList.add("show");
            } else {
                document.getElementById("giaodich").classList.remove("show");
            }
            if (document.getElementById("switchHistoryCare").checked == true) {
                document.getElementById("chamsoc").classList.add("show");
            } else {
                document.getElementById("chamsoc").classList.remove("show");

            }
        } else {
            $.notify(data.message, {
                position: "top center",
                className: "error",
            });
            closeNav();
            table.row($(button).parents("td").parents("tr")).remove().draw();
        }
    });
}
