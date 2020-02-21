$(function () {

    $("#user").on("click",function () {
        $.get("/admin/users",{},function (data) {
            if(isJson(data)){
                toastr.error(data.msg);
            }else{
                $("#main-content").html(data);
            }
        });
    });

    $("#apply").on("click",function () {
        $.get("/admin/applys",{},function (data) {
            if(isJson(data)){
                toastr.error(data.msg);
            }else{
                $("#main-content").html(data);
            }
        });
    });

    $("#article").on("click",function () {
        $.get("/admin/articles",{},function (data) {
            if(isJson(data)){
                toastr.error(data.msg);
            }else{
                $("#main-content").html(data);
            }
        });
    });

    $("[id=logout]").on("click",function () {
        $.cookie("adminToken", null,{expires: 1, path: '/', domain: '', secure: false, raw: false});
        window.location.href="/admin"
    });

    $("[id=deal]").on("click",function () {
        var type=$(this).attr("type");
        $.get("/report/"+type, {}, function (data) {
            if(isJson(data)){
                toastr.error(data.msg);
            }else{
                $("#main-content").html(data);
            }
        });
    });

    function isJson(str) {
        if (typeof str== "object") {
            return true;
        }
        return false;
    }

    function IsEmail(email) {
        var reg = /^\w+@[a-zA-Z0-9]{2,10}(?:\.[a-z]{2,4}){1,3}$/;
        return reg.test(email);
    }

});