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
            url: "/index",
            type:"get",
            contentType : 'application/json',
            data:{
                "async":true,
                "pageIndex":pageIndex,
                "pageSize":pageSize,
                "keyword":$("#indexKeyword").val()
            },
            success: function(data){
                $("#mainContainer").html(data);
                AddEvent();
                isVote();
            },
            error : function() {
                toastr.error("error!");
            }
        });
    }

    $("#indexSearch").click(function() {
        getFlowers(0, _pageSize);
    });

// 最新\最热切换事件
    $(".nav-item .nav-link").click(function() {

        var url = $(this).attr("url");

        // 先移除其他的点击样式，再添加当前的点击样式
        $(".nav-item .nav-link").removeClass("active");
        $(this).addClass("active");

        // 加载其他模块的页面到右侧工作区
        $.ajax({
            url: url+'&async=true',
            success: function(data){
                $("#mainContainer").html(data);
                isVote();
            },
            error : function() {
                toastr.error("error!");
            }
        });

        // 清空搜索框内容
        $("#indexKeyword").val('');
    });

    // 分页
    $.tbpage("#mainContainer", function (pageIndex, pageSize) {
        getFlowers(pageIndex, pageSize);
        _pageSize = pageSize;
    });

    function isVote() {
        var token=$.cookie("token");
        if(token=="null"){
            return
        }
        $.ajax({
            url: '/user/getAuth',
            type: 'GET',
            data: {"token":token},
            success: function (data) {
                if (data.code == 0) {
                    var userId=data.result.id;
                    $("[id=dianzan]").each(function () {
                        var dz=$(this);
                        var flowerId=dz.attr("flowerId");
                        $.ajax({
                            url: '/cv/vote/isVote',
                            type: 'GET',
                            data: {"userId":userId,flowerId:flowerId},
                            success:function (data) {
                                if(data.result){
                                    dz.attr("src","/img/isVote.png");
                                    dz.attr("isVote","true");
                                    dz.attr("data-original-title","取消点赞");
                                }
                            }
                        });
                    });
                }
            }
        });
    }
    isVote();
});