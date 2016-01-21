<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>司机管理</title>
    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <link rel="stylesheet" type="text/css" href="../lib/bootstrap/css/bootstrap.css">

    <link rel="stylesheet" type="text/css" href="../stylesheets/theme.css">
    <link rel="stylesheet" href="../lib/font-awesome/css/font-awesome.css">

    <script src="../lib/jquery-1.11.3.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="../lib/jquery.dataTables.min.js"></script>
    <link rel="stylesheet" type="text/css" href="../stylesheets/jquery.dataTables.min.css">
    <!-- Demo page code -->

    <style type="text/css">
        #line-chart {
            height:300px;
            width:800px;
            margin: 0px auto;
            margin-top: 1em;
        }
        .brand { font-family: georgia, serif; }
        .brand .first {
            color: #ccc;
            font-style: italic;
        }
        .brand .second {
            color: #fff;
            font-weight: bold;
        }
        #example td{
          text-align: center;
        }
    </style>

    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <!-- Le fav and touch icons -->
    <link rel="shortcut icon" href="../assets/ico/favicon.ico">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="../assets/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="../assets/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="../assets/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="../assets/ico/apple-touch-icon-57-precomposed.png">
  </head>

  <!--[if lt IE 7 ]> <body class="ie ie6"> <![endif]-->
  <!--[if IE 7 ]> <body class="ie ie7 "> <![endif]-->
  <!--[if IE 8 ]> <body class="ie ie8 "> <![endif]-->
  <!--[if IE 9 ]> <body class="ie ie9 "> <![endif]-->
  <!--[if (gt IE 9)|!(IE)]><!-->
  <body class="">
  <!--<![endif]-->
  <%@include file="header.jsp" %>

    <div class="content">

        <div class="header">

            <h1 class="page-title">司机管理</h1>
        </div>

                <ul class="breadcrumb">
            <li><a href="#">用户管理</a> <span class="divider">/</span></li>
            <li class="active">司机管理</li>
        </ul>

        <div class="container-fluid">
            <div class="row-fluid">

<div class="btn-toolbar">
    <button class="btn btn-primary"><i class="icon-plus"></i> 添加司机</button>
</div>
<div class="well">
    <table id="example" class="display" cellspacing="0" width="100%">
      <thead>
        <tr>
          <th>id</th>
          <th>手机号</th>
          <th>昵称</th>
          <th>积分</th>
          <th>金币</th>
          <th>注册时间</th>
          <th style="width: 40px;"></th>
        </tr>
      </thead>
      <tfoot>
        <tr>
          <th>id</th>
          <th>手机号</th>
          <th>昵称</th>
          <th>积分</th>
          <th>金币</th>
          <th>注册时间</th>
          <th style="width: 40px;"></th>
        </tr>
      </tfoot>
      <tbody>

      </tbody>
    </table>
</div>

<div class="modal small hide fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3 id="myModalLabel">删除确认</h3>
    </div>
    <div class="modal-body">
        <p class="error-text"><i class="icon-warning-sign modal-icon"></i>你确定要删除id为<span id="user-id">10</span>用户吗?</p>
    </div>
    <div class="modal-footer">
        <button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
        <button class="btn btn-danger" data-dismiss="modal" id="delete">删除</button>
    </div>
</div>

<div class="modal small hide fade" id="yourModal" tabindex="-1" role="dialog" aria-labelledby="yourModalLabel" aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3 id="myModalLabel">重置确认</h3>
    </div>
    <div class="modal-body">
        <p class="error-text"><i class="icon-warning-sign modal-icon"></i>你确定要重置id为<span id="user-id-reset">10</span>用户的密码为12345吗?</p>
    </div>
    <div class="modal-footer">
        <button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
        <button class="btn btn-danger" data-dismiss="modal" id="reset">重置</button>
    </div>
</div>



                    <%@include file="footer.jsp" %>

            </div>
        </div>
    </div>



    <script src="../lib/bootstrap/js/bootstrap.js"></script>
    <script type="text/javascript">
        var t;
        var resetId;
        var deleteId;
        $(document).ready(function() {
            t = $('#example').DataTable();
            getDriversList();
        } );
        $("[rel=tooltip]").tooltip();
        $(function() {
            $('.demo-cancel-click').click(function(){return false;});
        });
        function showModal(id){
          $('#user-id').text(id);
          $('#myModal').modal();
          deleteId = id;
        }
        function resetPassword(id){
          $('#user-id-reset').text(id);
          $('#yourModal').modal();
          resetId = id;
        }
        $('#delete').click(function(){
          var child = '#'+deleteId;
          $.ajax({
            type:"POST",
            url:"/tongbao/admin/deleteUser",
            //提交的数据
            data:{id:deleteId},
            //返回数据的格式
            datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
            //在请求之前调用的函数
            beforeSend:function(){
                // alert("beforeSend");
            },
            //成功返回之后调用的函数
            success:function(data){
                // alert(data);
                if(data.result == 1){
                    t
                    .row( $(child).parents('tr') )
                    .remove()
                    .draw();
                }else{
                    alert(data.errorMsg);
                }
            },
            //调用执行后调用的函数
            complete: function(XMLHttpRequest, textStatus){
               // alert(XMLHttpRequest.responseText);
               // alert(textStatus);
                //HideLoading();
            },
            //调用出错执行的函数
            error: function(){
                //请求出错处理
                alert("error!");
            }
          });
        });
        $('#reset').click(function(){
          $.ajax({
            type:"POST",
            url:"/tongbao/admin/resetUserPassword",
            //提交的数据
            data:{id:resetId,password:12345},
            //返回数据的格式
            datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
            //在请求之前调用的函数
            beforeSend:function(){
                // alert("beforeSend");
            },
            //成功返回之后调用的函数
            success:function(data){
                // alert(data);
                if(data.result == 1){
                    humane.log("设置成功!");
                }else{
                    alert(data.errorMsg);
                }
            },
            //调用执行后调用的函数
            complete: function(XMLHttpRequest, textStatus){
               // alert(XMLHttpRequest.responseText);
               // alert(textStatus);
                //HideLoading();
            },
            //调用出错执行的函数
            error: function(){
                //请求出错处理
                alert("error!");
            }
          });
        });
        function getDriversList(){
          $.ajax({
            type:"POST",
            url:"/tongbao/admin/getAllUsersByType",
            //提交的数据
            data:{type:1},
            //返回数据的格式
            datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
            //在请求之前调用的函数
            beforeSend:function(){
                // alert("beforeSend");
            },
            //成功返回之后调用的函数
            success:function(data){
                // alert(data);
                if(data.result == 1){
                    addUsersToTable(data.data);
                }else{
                    alert(data.errorMsg);
                }
            },
            //调用执行后调用的函数
            complete: function(XMLHttpRequest, textStatus){
               // alert(XMLHttpRequest.responseText);
               // alert(textStatus);
                //HideLoading();
            },
            //调用出错执行的函数
            error: function(){
                //请求出错处理
                alert("error!");
            }
          });
        }
        function addUsersToTable(data){
          t.clear().draw();
          if(data != null){
            
            for(var i = 0;i<data.length;i++){
              var operation = "<a href='javascript:void(0)' role='button' title='详情'><i class='icon-edit'></i></a>  <a href='javascript:void(0)' title='重置密码' role='button' onclick='resetPassword("+data[i].id+");' id='"+data[i].id+"'><i class='icon-exclamation-sign'></i></a>  <a href='javascript:void(0)' role='button' title='删除' onclick='showModal("+data[i].id+");'><i class='icon-remove'></i></a>";
              t.row.add([
                data[i].id,
                data[i].phoneNum,
                data[i].nickName,
                data[i].point,
                data[i].money,
                data[i].registerTime,
                operation
              ]).draw(false);
            }

          }
        }
    </script>

  </body>
</html>


