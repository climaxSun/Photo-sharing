$(function() {
    // 获取编辑用户头像的界面
    $(".user-edit-avatar").on("click", function () {
        $.ajax({
            url: "/userspance/avatar",
            type: 'GET',
            success: function(data){
                $("#avatarFormContainer").html(data);
            },
            error : function() {
                toastr.error("error!");
            }
        });
    });

    /**
     * 将以base64的图片url数据转换为Blob
     * @param urlData
     *            用url方式表示的base64图片数据
     */
    function convertBase64UrlToBlob(urlData){

        var bytes=window.atob(urlData.split(',')[1]);        //去掉url的头，并转换为byte

        //处理异常,将ascii码小于0的转换为大于0
        var ab = new ArrayBuffer(bytes.length);
        var ia = new Uint8Array(ab);
        for (var i = 0; i < bytes.length; i++) {
            ia[i] = bytes.charCodeAt(i);
        }

        return new Blob( [ab] , {type : 'image/png'});
    }

    // 提交用户头像的图片数据
    $("#submitEditAvatar").on("click", function () {
        var form = $('#avatarformid')[0];
        var formData = new FormData(form);   //这里连带form里的其他参数也一起提交了,如果不需要提交其他参数可以直接FormData无参数的构造函数
        var base64Codes = $(".cropImg > img").attr("src");
        formData.append("file",convertBase64UrlToBlob(base64Codes));  //append函数的第一个参数是后台获取数据的参数名,和html标签的input的name属性功能相同

        $.ajax({
            url: '/image/upload/userAvatar',
            type: 'POST',
            cache: false,
            data: formData,
            processData: false,
            contentType: false,
            success: function(data){
                var avatarUrl = data;
                $.ajax({
                    url: "/user/userspance/avatar",
                    type: 'POST',
                    data: {"avatarUrl":avatarUrl},
                    success: function(data){
                        if (data.code==0) {
                            // 成功后，置换头像图片
                            $(".user-avatar").attr("src", "/image/userAvatar/"+avatarUrl);
                        } else{
                            toastr.error("error!"+data.msg);
                            window.location.href="/login";
                        }
                    },
                    error : function(data) {
                        toastr.error(data.responseText);
                    }
                });
            },
            error : function(data) {
                toastr.error(data.responseText);
            }
        })
    });

    $("#submitEditPWD").on("click",function () {
        var newPWD=$("#newPWD").val();
        if(newPWD.length<6){
            alert("新密码长度太短!!!");
            return ;
        }
        newPWD=hex_md5(newPWD);
        $.ajax({
            url: "/user/userspance/pwd",
            type: 'POST',
            data:{"pwd":newPWD},
            success: function (data) {
                if(data.code==0){
                    alert("密码修改成功");
                }else{
                    toastr.error(data.msg);
                }
            },
            error: function (data) {
                toastr.error(data.responseText);
            }
        });
    });


});