<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>订单管理</title>
    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel='stylesheet' href='../lib/humane/themes/bigbox.css'/>
    <link rel='stylesheet' href='../lib/humane/themes/boldlight.css'/>
    <link rel='stylesheet' href='../lib/humane/themes/jackedup.css'/>
    <link rel='stylesheet' href='../lib/humane/themes/libnotify.css'/>
    <link rel='stylesheet' href='../lib/humane/themes/original.css'/>
    <link rel='stylesheet' href='../lib/humane/themes/flatty.css'/>

    <script src='../lib/humane/humane.js'></script>
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

            <h1 class="page-title">账单管理</h1>
        </div>

                <ul class="breadcrumb">
            <li><a href="/tongbao/admin/index">首页</a> <span class="divider">/</span></li>
            <li class="active">账单管理</li>
        </ul>

        <div class="container-fluid">
            <div class="row-fluid">

<div class="well">
    <table id="example" class="display" cellspacing="0" width="100%">
      <thead>
        <tr>
          <th>id</th>
          <th>用户id</th>
          <th>用户联系方式</th>
          <th>账单生成时间</th>
          <th>账单类型</th>
          <th>金额</th>
          <th>相关订单id</th>
          <th style="width: 40px;"></th>
        </tr>
      </thead>
      <tfoot>
        <tr>
            <th>id</th>
            <th>用户id</th>
            <th>用户联系方式</th>
            <th>账单生成时间</th>
            <th>账单类型</th>
            <th>金额</th>
            <th>相关订单id</th>
            <th style="width: 40px;"></th>
        </tr>
      </tfoot>
      <tbody>

      </tbody>
    </table>
</div>

<div class="modal small hide fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="deleteModalLabel" aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3 id="myModalLabel">删除确认</h3>
    </div>
    <div class="modal-body">
        <p class="error-text"><i class="icon-warning-sign modal-icon"></i>你确定要删除id为<span id="account-id-delete">10</span>的订单吗?</p>
    </div>
    <div class="modal-footer">
        <button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
        <button class="btn btn-danger" data-dismiss="modal" id="delete">删除</button>
    </div>
</div>




                    <%@include file="footer.jsp" %>

            </div>
        </div>
    </div>



    <script src="../lib/bootstrap/js/bootstrap.js"></script>
    <script type="text/javascript">
        var t;
        var deleteId;
        $(document).ready(function() {
            t = $('#example').DataTable();
            getAccountsList();
        } );
        $("[rel=tooltip]").tooltip();
        $(function() {
            $('.demo-cancel-click').click(function(){return false;});
        });
        function deleteAccount(id){
          $('#account-id-delete').text(id);
          $('#deleteModal').modal();
          deleteId = id;
        }
        $('#delete').click(function(){
          var child = '#'+deleteId;
          $.ajax({
            type:"POST",
            url:"/tongbao/admin/deleteAccount",
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
        function getAccountsList(){
          $.ajax({
            type:"POST",
            url:"/tongbao/admin/getAllAccounts",
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
                    addAccountsToTable(data.data);
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
        function addAccountsToTable(data){
          t.clear().draw();
          if(data != null){
            for(var i = 0;i<data.length;i++){
            	var operation = "<a href='javascript:void(0)' title='删除' role='button' onclick='deleteAccount("+data[i].id+");' id='"+data[i].id+"'><i class='icon-remove'></i></a>"
              t.row.add([
                data[i].id,
                data[i].userId,
                data[i].userPhoneNum,
                data[i].time,
                data[i].typeStr,
                data[i].money,
                data[i].orderId,
                operation
              ]).draw(false);
            }

          }
        }
    </script>

  </body>
</html>


