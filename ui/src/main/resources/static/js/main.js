$(function () {

    var shareNumber;

    function setCookie(name, token) {
        $.cookie(name, token, {expires: 1, path: '/', domain: '', secure: false, raw: false});
    }

    function getCookie(name) {
        return $.cookie(name);
    }

    function deleteCookie(name) {
        $.cookie(name, null,{expires: 1, path: '/', domain: '', secure: false, raw: false});
    }

    function header_display(){
        var token=getCookie("token");
        if(token=="null"){
            console.log("token is null");
            $("#name").text("");
            $("#isLogin").addClass("hidden");
            $("#notLogin").removeClass("hidden");
            return ;
        }
        $.ajax({
            url: '/user/getAuth',
            type: 'GET',
            data: {"token":token},
            success: function (data) {
                if (data.code == 0) {
                    setCookie("token", data.result.token);
                    shareNumber=data.result.sharesNumber;
                    $("#name").text(data.result.name);
                    $("#notLogin").addClass("hidden");
                    $("#isLogin").removeClass("hidden");
                    $("#grzy").attr('href','/userspance/u/'+data.result.username);
                    if(data.result.lv!=2){
                        $("#shareArticle").addClass("hidden");
                    }
                    if(data.result.lv==1){
                        $("#readApply").on("click",function () {
                            console.log("readApply");
                        });
                    }
                    else{
                        $("#readApply").addClass("hidden");
                    }
                } else {
                    deleteCookie("token");
                    $("#name").text("");
                    $("#isLogin").addClass("hidden");
                    $("#notLogin").removeClass("hidden");
                }
            },
            error: function (data) {
                $("#name").text("");
                $("#isLogin").addClass("hidden");
                $("#notLogin").removeClass("hidden");
            }
        });
    }

    header_display();

    function IsEmail(email) {
        var reg = /^\w+@[a-zA-Z0-9]{2,10}(?:\.[a-z]{2,4}){1,3}$/;
        return reg.test(email);
    }

    function isYZM(str) {
        var reg = /^[a-zA-Z\d]{6}$/;   /*定义验证表达式*/
        return reg.test(str);     /*进行验证*/
    }

    var wait = 60;

    function time() {
        if (wait == 0) {
            $("#getYZM").attr("disabled", false);
            $("#getYZM").text("获取验证码");
            $("#getYZM").removeClass("btn-light");
            $("#getYZM").addClass("btn-info");
            wait = 60;
        } else {
            $("#getYZM").attr("disabled", true);
            $("#getYZM").text(wait + "s后重新发送");
            $("#getYZM").removeClass("btn-info");
            $("#getYZM").addClass("btn-light");
            wait--;
            setTimeout(function () {
                time();
            }, 1000);
        }
    }

    $("#loginButton").click(function () {
        var username = $("#username").val().trim();
        var password = $("#password").val().trim();
        if (username.length < 3) {
            alert("账号名长度最小为3，你的长度不够，请重新输入！");
            return;
        }
        if (username.length > 20) {
            alert("账号名长度太长，请重新输入！");
        }
        if (password.length < 6) {
            alert("密码长度最小为6，请重新输入！");
            return;
        }
        password = hex_md5(password);
        $.ajax({
            url: '/user/login',
            type: 'POST',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({
                "username": username,
                "password": password
            }),
            success: function (data) {
                if (data.code == 0) {
                    alert("登录成功");
                    setCookie("token", data.result.token);
                    // 成功后，重定向
                    window.location.href = "/index";
                } else {
                    toastr.error("error!" + data.msg);
                }
            },
            error: function (data) {
                toastr.error(data.responseText);
            }
        });
    });

    $("#register").click(function () {
        var name = $("#registerForm #name").val().trim();
        var username = $("#username").val().trim();
        var password = $("#password").val().trim();
        var confirmPassword = $("#confirmPassword").val().trim();
        var email = $("#email").val().trim();
        var yzm = $("#yzm").val().trim();
        if (name.length < 2) {
            alert("用户名称长度不够！最小为2位");
            return;
        }
        if (name.length > 20) {
            alert("用户名称长度太长！最大为20位");
            return;
        }
        if (username.length < 3) {
            alert("账号名长度不够！最小为3位");
            return;
        }
        if (username.length > 20) {
            alert("账号名长度太长！最大为20位");
            return;
        }
        if (password.length < 6) {
            alert("密码长度太短");
            return;
        }
        if (password != confirmPassword) {
            alert("2次输入的密码不一样！");
            return;
        }
        if (IsEmail(email) == false) {
            alert("邮箱格式错误，请输入正确的格式");
            return;
        }
        if (isYZM(yzm) == false) {
            alert("验证码为6位字母加数字，请输入正确的格式!");
            return;
        }
        password = hex_md5(password);

        $.ajax({
            url: '/user/register',
            type: 'POST',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({
                "username": username,
                "password": password,
                "email": email,
                "name": name,
                "yzm": yzm
            }),
            success: function (data) {
                if (data.code == 0) {
                    alert("注册成功!");
                    // 成功后，重定向
                    window.location.href = "/login";
                } else {
                    toastr.error("error!" + data.msg);
                }
            },
            error: function (data) {
                toastr.error(data.responseText);
            }
        });
    });

    $("#confirmPassword").change(function () {
        var password = $("#password").val().trim();
        var confirmPassword = $("#confirmPassword").val().trim();
        if (password != confirmPassword)
            alert("2次输入的密码不一样");
    });

    $("#getYZM").click(function () {
        var email = $("#email").val().trim();

        if (IsEmail(email) == false) {
            alert("邮箱格式错误，请输入正确的格式");
            return;
        }
        $.ajax({
            url: '/user/getYZM',
            type: 'GET',
            contentType: "application/json; charset=utf-8",
            data: {
                "email": email,
                "zt": $("#getYZM").val()
            },
            success: function (data) {
                if (data.code == 0) {
                    alert("邮件发送成功！，请及时查收");
                    time();
                } else {
                    toastr.error(data.msg);
                }
            },
            error: function (data) {
                toastr.error(data.responseText);
            }
        });
    });

    $("#YZEmail").click(function () {
        var email = $("#email").val().trim();
        var yzm = $("#yzm").val().trim();
        if (IsEmail(email) == false) {
            alert("邮箱格式错误，请输入正确的格式");
            return;
        }
        if (isYZM(yzm) == false) {
            alert("验证码为6位字母加数字，请输入正确的格式!");
            return;
        }
        $.ajax({
            url: '/user/yzYZM',
            type: 'GET',
            contentType: "application/json; charset=utf-8",
            data: {
                "email": email,
                "yzm": yzm
            },
            success: function (data) {
                if (data.code == 0) {
                    alert("邮箱验证成功，请在5分钟之内输入新密码!,否则验证码将失效");
                    $("#userEmail").val(email);
                    $("#userYZM").val(yzm);
                    $("#emailYZ").addClass("hidden");
                    $("#editPWD").removeClass("hidden");
                } else {
                    toastr.error(data.msg);
                }
            },
            error: function (data) {
                toastr.error(data.responseText);
            }
        });
    });

    $("#retrievePWD").click(function () {
        var userEmail = $("#userEmail").val().trim();
        var userYZM = $("#userYZM").val().trim();
        var password = $("#password").val().trim();
        var confirmPassword = $("#confirmPassword").val().trim();
        if (password.length < 6) {
            alert("密码长度太短");
            return;
        }
        if (password != confirmPassword) {
            alert("2次输入的密码不一样！");
            return;
        }
        password = hex_md5(password);

        $.ajax({
            url: '/user/retrievePWD',
            type: 'POST',
            data: {
                "email": userEmail,
                "yzm": userYZM,
                "newPassword": password
            },
            success: function (data) {
                if (data.code == 0) {
                    alert("密码修改成功!!!");
                    window.location.href="/login";
                } else {
                    toastr.error(data.msg);
                }
            },
            error: function (data) {
                toastr.error(data.responseText);
            }
        });
    });

    $("#logout").click(function () {
        $.ajax({
            url: '/user/logout',
            type: 'GET',
            success: function () {
            },
            error: function (data) {
                toastr.error(data.responseText);
            }
        });
        deleteCookie("token");
        window.location.href="/login";
    });

    $("#editButton").click(function () {
        var email = $("#email").val().trim();
        var name = $("#editForm #name").val().trim();
        var introduction=$("#editForm #introduction").val().trim();
        if (IsEmail(email) == false) {
            alert("邮箱格式错误，请输入正确的格式");
            return;
        }
        $.ajax({
            url: '/user/userspance/profile',
            type: 'POST',
            data: {
                "email": email,
                "name": name,
                "introduction": introduction
            },
            success: function (data) {
                if (data.code == 0) {
                    alert("修改成功!");
                    // 成功后，重定向
                    window.location.href = "/userspance/profile";
                } else {
                    toastr.error("error!" + data.msg);
                }
            },
            error: function (data) {
                toastr.error(data.responseText);
            }
        });
    });

    $("#submitComment").click(function () {
        var content=$("#comment").val();
        var flowerId=$("#pl").attr("flowerId");
        $.ajax({
            url: "/cv/comment",
            type: 'POST',
            data:{"flowerId":flowerId,"content":content},
            success:function (data) {
                if(data.code==0){
                    toastr.success("评论成功");
                    $.ajax({
                        url: "/comment",
                        type: 'GET',
                        data:{"flowerId":flowerId},
                        success: function (data) {
                            $("#plFormContainer").html(data);
                        },
                        error: function () {
                            $("#plFormContainer").html("网络异常请稍后再试!");
                        }
                    });
                }
                else {
                   toastr.error(data.msg);
                }
            },
            error: function () {
                $("#plFormContainer").html("网络异常请稍后再试!");
            }
        });
    });

    function AddEvent() {
        $("[id= dianzan]").on("click",function () {
            var dianzan=$(this);
            var isVote=dianzan.attr("isVote");
            var flowerId = dianzan.attr("flowerId");
            if(isVote=="true"){
                $.ajax({
                    url: "/cv/vote",
                    type: 'DELETE',
                    data:{"flowerId":flowerId},
                    success: function (data) {
                        if(data.code==0){
                            toastr.success("取消点赞成功");
                            var voteSize=$("#vote"+flowerId).text();
                            $("#vote"+flowerId).text(voteSize-1);
                            dianzan.attr("src","/img/notVote.png");
                            dianzan.attr("isVote","false");
                            dianzan.attr("data-original-title","点赞");
                        }else{
                            toastr.error(data.msg);
                        }
                    },
                    error: function (data) {
                        toastr.error(data.responseText);
                    }
                });
            }
            else {
                $.ajax({
                    url: "/cv/vote",
                    type: 'GET',
                    data:{"flowerId":flowerId},
                    success: function (data) {
                        if(data.code==0){
                            toastr.success("点赞成功");
                            var voteSize=$("#vote"+flowerId).text();
                            $("#vote"+flowerId).text(voteSize-0+1);
                            dianzan.attr("src","/img/isVote.png");
                            dianzan.attr("isVote","true");
                            dianzan.attr("data-original-title","取消点赞");
                        }else{
                            toastr.error(data.msg);
                        }
                    },
                    error: function (data) {
                        toastr.error(data.responseText);
                    }
                });
            }
        });

        $("[id = plmodel]").on("click",function () {
            var pl=$(this);
            var flowerId=pl.attr("flowerId");
            var height=$(window).height();
            $("#pl .modal-body").height(height*0.55);
            $("#pl").attr("flowerId",flowerId);
            $("#plFormContainer").html("");
            $.ajax({
                url: "/comment",
                type: 'GET',
                data:{"flowerId":flowerId},
                success: function (data) {
                    $("#plFormContainer").html(data);
                },
                error: function () {
                    $("#plFormContainer").html("网络异常请稍后再试!");
                }
            });
        });

        $("[id = reportFlower]").on("click",function () {
            var report=$(this);
            var reportedId=report.attr("flowerId");
            var type=report.attr("type");
            $("#reportModal").attr("reportedId",reportedId);
            $("#reportModal").attr("type",type);
            $("#reason").val("");
        });
    }

    $("#submitEditPL").on("click",function () {
        $("#fbPL #fbPLFormContainer #comment").val("");
    });

    $("#jubao").on("click",function () {
        var reportedId=$("#reportModal").attr("reportedId");
        var type=$("#reportModal").attr("type");
        var reason=$("#reason").val();
        $.ajax({
            url:"/cv/report",
            type:"post",
            data:{"reportedId":reportedId, "type":type,"reason":reason},
            success:function (data) {
                if(data.code==0){
                    alert("举报成功!");
                }else{
                    toastr.error("error!!!\n" + data.msg);
                }
            },
            error:function (data) {
                toastr.error(data.responseText);
            }
        });
    });

    $("#readApply").on("click",function () {
        $.ajax({
            url:"/user/apply",
            type:"get",
            success:function (data) {
                if(data.code==0){
                    if(!data.result.result){
                        toastr.success("未同意");
                    }
                }else{
                    toastr.error(data.msg);
                }
            },
            error:function (data) {
                toastr.error(data.responseText);
            }
        });
    });

    AddEvent();
});