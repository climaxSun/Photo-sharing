"use strict";
$(function() {

    var _pageSize; // 存储用于搜索
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

        $('[data-toggle="tooltip"]').tooltip();
    }

    function getFlowers(pageIndex, pageSize) {
        $.ajax({
            url: "/userspance/u/"+  username,
            contentType : 'application/json',
            data:{
                "async":true,
                "pageIndex":pageIndex,
                "pageSize":pageSize,
                "keyword":keyword
            },
            success: function(data){
                $("#mainContainer").html(data);
                AddEvent();
            },
            error : function() {
                toastr.error("error!");
            }
        });
    }

    // 分页
    $.tbpage("#mainContainer", function (pageIndex, pageSize) {
        getFlowers(pageIndex, pageSize);
        _pageSize = pageSize;
    });

    $("#jubaoUser").on("click",function () {
        var report=$(this);
        var reportedId=report.attr("userId");
        var type=report.attr("type");
        $("#reportModal").attr("reportedId",reportedId);
        $("#reportModal").attr("type",type);
        $("#reason").val("");
    });

    $("#lvUp").on("click",function () {
        $.ajax({
            url:"/user/apply",
            type:"postreportFlower",
            success:function (data) {
                if(data.code==0){
                    toastr.success("已经开始申请");
                }else{
                    toastr.error(data.msg);
                }
            },
            error:function (data) {
                toastr.error(data);
            }
        });
    });


});