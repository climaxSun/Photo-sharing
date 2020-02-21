var APP_CODE = "da12d8e5e4b3402bac7aa9c8218b8464";
var BASE_URL = "http://plantgw.nongbangzhu.cn/";

function post(apiContextUrl, formData) {

    $.ajax({
        url: BASE_URL + apiContextUrl,
        type: "POST",
        headers: {'Authorization': 'APPCODE ' + APP_CODE},
        data: formData,
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        error: function (err) {
            $("#showFlower").html(err.responseJSON.Message);
        },
        success: function (data) {
            console.log(data);
            if (data.Status == 0) {
                var s = "";
                var array = data.Result;
                array.forEach(function (v) {
                    s = s + "<div class=\"card col-12 col-sm-6 col-md-4 col-lg-3 colxl-3\">\n" +
                        "                 <div class=\"card-header text-center example\">\n" +
                        "                     <img src=\"" + v.ImageUrl + " \" class=\"rounded img-thumbnail img-fluid\" >\n" +
                        "                 </div>\n" +
                        "                <div class=\"card-body\">\n" +
                        "                    <p><small class=\"text-left\">" + v.Family + "   " + v.Genus + "</small><span class=\"float-right\">相似度:<span class=\"text-danger\">" + v.Score + "%</span></span></p>\n" +
                        "                    <h4>" + v.Name + "<small>  ("+v.AliasName+") </small></h4>\n" +
                        "                    <small >" + v.LatinName + "</small>\n" +
                        "                </div>\n" +
                        "            </div>"
                });
                $("#showFlower").html(s);
                $('.example img').zoomify();
            } else if(data.Status==1001){
                $("#showFlower").html("提交请求内容中未包含img_base64参数，无法获取图片信息");
            }else if(data.Status==1002){
                $("#showFlower").html("Failed to recognize the request image.");
            }else if(data.Status==1003){
                $("#showFlower").html("植物图片识别失败");
            }
        },
        complete: function () {
            console.log("请求处理结束。");
        }
    });
}

$(function () {
    $("#show").click(function () {
        //先准备数据
        var apiContextUrl = 'plant/recognize2';
        // $(".link").text()开头是：data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEAYAB...，要注意先去掉/9j/之前的部分
        var img_base64 = $(".link").text().replace('data:image/jpeg;base64,', '');

        var formData = {
            img_base64: img_base64
        };
        post(apiContextUrl, formData);
        /*    $.ajax({
                url: "/ss",
                type: "get",
                success: function (data) {
                    if(data.code==0){
                        var s="";
                        var array=data.result;
                        array.forEach(function(v){
                            s=s+"<div class=\"card col-12 col-sm-6 col-md-4 col-lg-3 colxl-3\">\n" +
                                "                 <div class=\"card-header text-center example\">\n" +
                                "                     <img src=\""+v.imageUrl+" \" class=\"rounded img-thumbnail img-fluid\" >\n" +
                                "                 </div>\n" +
                                "                <div class=\"card-body\">\n" +
                                "                    <p><small class=\"text-left\">"+v.family+"   "+v.genus+"</small><span class=\"float-right\">相似度:<span class=\"text-danger\">"+v.score+"%</span></span></p>\n" +
                                "                    <h4>"+v.name+"<small>  (野菊,就菊花) </small></h4>\n" +
                                "                    <small >"+v.latinName+"</small>\n" +
                                "                </div>\n" +
                                "            </div>"
                        });
                        $("#showFlower").html(s);
                        $('.example img').zoomify();
                    }else{
                        $("#showFlower").html(data.msg);
                    }
                }
            });*/

    });

});