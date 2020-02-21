$(function() {

    $('.form-control-tag').tagEditor({
        initialTags: [],
        maxTags: 5,
        delimiter: ',',
        forceLowercase: false,
        animateDelete: 0,
        placeholder: '请输入标签'
    });

    $("#file-1").fileinput({
        theme: 'fas',
        language: 'zh',
        uploadUrl: '/image/upload/flowers', // you must set a valid URL here else you will get an error
        allowedFileExtensions: ['jpg', 'png', 'gif'],
        overwriteInitial: false,
        showRemove: true,
        showCaption: false,
        dropZoneEnabled: false,
        maxFileSize: 1000,
        maxFilesNum: 10,
        fileActionSettings: {
            showRemove: false,
            showUpload: false
        },
        //allowedFileTypes: ['image', 'video', 'flash'],
        slugCallback: function (filename) {
            return filename.replace('(', '_').replace(']', '_');
        }
    }).on("fileuploaded", function (event, data, id, index) {
        var url = data.response[0];
        photoUrl[photoUrl.length] = url;
    }).on("filecleared", function (event, data, msg) {
        if (photoUrl.length == 0)
            return;
        $.ajax({
            url: "/image/delete/flowers",
            type: 'POST',
            dataType: "json",
            data: {"photoName": photoUrl}
        });
        photoUrl = [];
        console.log(photoUrl);
    });

    $("#flower_sumbit").click(function () {
        if(photoUrl.length==0){
            alert("请先上传图片！");
            return;
        }
        var tags = $("#tags").val();
        var content=$("#comment").val();
        if(content==""){
            alert("请输入正文内容！");
            return;
        }
        $.ajax({
            url: "/flower/flower",
            type: 'POST',
            dataType: "json",
            data: {"photoUrl": photoUrl,"tag":tags,"content":content},
            success: function (data) {
                if(data.code==0){
                    alert("分享成功!");
                    window.location.href="/userspance/u";
                }else{
                    toastr.error(data.msg);
                    window.location.href="/login";
                }
            },
            error: function (data) {
                toastr.error(data.responseText);
            }
        });
    });

});