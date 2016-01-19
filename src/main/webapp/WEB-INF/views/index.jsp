<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Admin</title>
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

    <script src="../lib/jquery-1.7.2.min.js" type="text/javascript"></script>

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
            <div class="stats">
    <p class="stat"><span class="number"><c:out value="${examinedDriverNum}" /></span>已审核</p>
    <p class="stat"><span class="number"><c:out value="${waitingExamineDriverNum}" /></span>待审核</p>
    <p class="stat"><span class="number"><c:out value="${unSubmittedDriverNum}" /></span>未提交审核</p>
</div>

            <h1 class="page-title">首页</h1>
        </div>

                <ul class="breadcrumb">
            <li><a href="/tongbao/admin/index">首页</a> <span class="divider">/</span></li>
            <li class="active">管理</li>
        </ul>

        <div class="container-fluid">
            <div class="row-fluid">


<div class="row-fluid">
    <c:choose>
      <c:when test="${waitingExamineDriverNum != 0}">
        <div class="alert alert-info">
            <button type="button" class="close" data-dismiss="alert">×</button>
            <strong>注意:</strong> 有新的需要审核的司机!
        </div>
      </c:when>
    </c:choose>
    
    

    <div class="block">
        <div class="block-heading">
            <span class="block-icon pull-right">
                <a href="#" class="demo-cancel-click" rel="tooltip" title="点击刷新" id="refreshNum"><i class="icon-refresh"></i></a>
            </span>

            <a href="#page-stats" data-toggle="collapse">最新动态</a>
        </div>
        <div id="page-stats" class="block-body collapse in">

            <div class="stat-widget-container">
                <div class="stat-widget">
                    <div class="stat-button">
                        <p class="title" id="shipperNum">${shipperNum}</p>
                        <p class="detail">货主</p>
                    </div>
                </div>

                <div class="stat-widget">
                    <div class="stat-button">
                        <p class="title" id="driverNum">${driverNum}</p>
                        <p class="detail">司机</p>
                    </div>
                </div>

                <div class="stat-widget">
                    <div class="stat-button">
                        <p class="title" id="orderNum">${orderNum}</p>
                        <p class="detail">订单</p>
                    </div>
                </div>

                <div class="stat-widget">
                    <div class="stat-button">
                        <p class="title" id="accountNum">${accountNum}</p>
                        <p class="detail">账单</p>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>

<div class="row-fluid">
    <div class="block span6">
        <div class="block-heading">
            <span class="block-icon pull-right">
                <a href="#" class="demo-cancel-click" rel="tooltip" title="点击刷新" id="refreshShipper"><i class="icon-refresh"></i></a>
            </span>

            <a href="#tablewidget1" data-toggle="collapse">货主</a>
        </div>
        <div id="tablewidget1" class="block-body collapse in">
            <table class="table" id="shipperTable">
              <thead>
                <tr>
                  <th>id</th>
                  <th>手机号</th>
                  <th>昵称</th>
                  <th>注册时间</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>1</td>
                  <td>Mark</td>
                  <td>Tompson</td>
                  <td>the_mark7</td>
                </tr>
                <tr>
                  <td>1</td>
                  <td>Ashley</td>
                  <td>Jacobs</td>
                  <td>ash11927</td>
                </tr>
                <tr>
                  <td>1</td>
                  <td>Audrey</td>
                  <td>Ann</td>
                  <td>audann84</td>
                </tr>
                <tr>
                  <td>1</td>
                  <td>John</td>
                  <td>Robinson</td>
                  <td>jr5527</td>
                </tr>
                <tr>
                  <td>1</td>
                  <td>Aaron</td>
                  <td>Butler</td>
                  <td>aaron_butler</td>
                </tr>
                <tr>
                  <td>1</td>
                  <td>Chris</td>
                  <td>Albert</td>
                  <td>cab79</td>
                </tr>
              </tbody>
            </table>
            <p><a href="/tongbao/admin/shipperManage">更多...</a></p>
        </div>
    </div>
    <div class="block span6">
        <div class="block-heading">
            <span class="block-icon pull-right">
                <a href="#" class="demo-cancel-click" rel="tooltip" title="点击刷新" id="refreshDriver"><i class="icon-refresh"></i></a>
            </span>

            <a href="#tablewidget2" data-toggle="collapse">司机</a>
        </div>
        <div id="tablewidget2" class="block-body collapse in">
            <table class="table" id="driverTable">
              <thead>
                <tr>
                  <th>id</th>
                  <th>手机号</th>
                  <th>昵称</th>
                  <th>注册时间</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>1</td>
                  <td>Mark</td>
                  <td>Tompson</td>
                  <td>the_mark7</td>
                </tr>
                <tr>
                  <td>1</td>
                  <td>Ashley</td>
                  <td>Jacobs</td>
                  <td>ash11927</td>
                </tr>
                <tr>
                  <td>1</td>
                  <td>Audrey</td>
                  <td>Ann</td>
                  <td>audann84</td>
                </tr>
                <tr>
                  <td>1</td>
                  <td>John</td>
                  <td>Robinson</td>
                  <td>jr5527</td>
                </tr>
                <tr>
                  <td>1</td>
                  <td>Aaron</td>
                  <td>Butler</td>
                  <td>aaron_butler</td>
                </tr>
                <tr>
                  <td>1</td>
                  <td>Chris</td>
                  <td>Albert</td>
                  <td>cab79</td>
                </tr>
              </tbody>
            </table>
            <p><a href="users.html">更多...</a></p>
        </div>
    </div>
</div>

<div class="row-fluid">
    <div class="block span6">
        <div class="block-heading">
            <span class="block-icon pull-right">
                <a href="#" class="demo-cancel-click" rel="tooltip" title="点击刷新" id="refreshOrder"><i class="icon-refresh"></i></a>
            </span>

            <a href="#tablewidget3" data-toggle="collapse">订单</a>
        </div>
        <div id="tablewidget3" class="block-body collapse in">
            <table class="table list" id="orderTable">
              <thead>
                <tr>
                  <th>id</th>
                  <th>支付金额</th>
                  <th>发布时间</th>
                  <th>当前状态</th>
                  <th>起点</th>
                  <th>终点</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>1</td>
                  <td>Mark</td>
                  <td>Tompson</td>
                  <td>the_mark7</td>
                  <td>the_mark7</td>
                  <td>the_mark7</td>
                </tr>
                <tr>
                  <td>1</td>
                  <td>Ashley</td>
                  <td>Jacobs</td>
                  <td>ash11927</td>
                  <td>the_mark7</td>
                  <td>the_mark7</td>
                </tr>
                <tr>
                  <td>1</td>
                  <td>Audrey</td>
                  <td>Ann</td>
                  <td>audann84</td>
                  <td>the_mark7</td>
                  <td>the_mark7</td>
                </tr>
                <tr>
                  <td>1</td>
                  <td>Mark</td>
                  <td>Tompson</td>
                  <td>the_mark7</td>
                  <td>the_mark7</td>
                  <td>the_mark7</td>
                </tr>
                <tr>
                  <td>1</td>
                  <td>Ashley</td>
                  <td>Jacobs</td>
                  <td>ash11927</td>
                  <td>the_mark7</td>
                  <td>the_mark7</td>
                </tr>
                <tr>
                  <td>1</td>
                  <td>Audrey</td>
                  <td>Ann</td>
                  <td>audann84</td>
                  <td>the_mark7</td>
                  <td>the_mark7</td>
                </tr>
                
              </tbody>
            </table>
            <p><a href="users.html">更多...</a></p>
        </div>
    </div>
    <div class="block span6">
        <div class="block-heading">
            <span class="block-icon pull-right">
                <a href="#" class="demo-cancel-click" rel="tooltip" title="点击刷新" id="refreshAccount"><i class="icon-refresh"></i></a>
            </span>

            <a href="#tablewidget4" data-toggle="collapse">账单</a>
        </div>
        <div id="tablewidget4" class="block-body collapse in">
            <table class="table list" id="accountTable">
              <thead>
                <tr>
                  <th>id</th>
                  <th>交易时间</th>
                  <th>交易金额</th>
                  <th>类型</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>1</td>
                  <td>Mark</td>
                  <td>Tompson</td>
                  <td>the_mark7</td>
                </tr>
                <tr>
                  <td>1</td>
                  <td>Mark</td>
                  <td>Tompson</td>
                  <td>the_mark7</td>
                </tr>
                <tr>
                  <td>1</td>
                  <td>Mark</td>
                  <td>Tompson</td>
                  <td>the_mark7</td>
                </tr>
                <tr>
                  <td>1</td>
                  <td>Mark</td>
                  <td>Tompson</td>
                  <td>the_mark7</td>
                </tr>
                <tr>
                  <td>1</td>
                  <td>Mark</td>
                  <td>Tompson</td>
                  <td>the_mark7</td>
                </tr>
                <tr>
                  <td>1</td>
                  <td>Mark</td>
                  <td>Tompson</td>
                  <td>the_mark7</td>
                </tr>
              </tbody>
            </table>
            <p><a href="users.html">更多...</a></p>
        </div>
    </div>
</div>


                    <%@include file="footer.jsp"%>

            </div>
        </div>
    </div>



    <script src="../lib/bootstrap/js/bootstrap.js"></script>
    <script src="../lib/auth.js"></script>
    <script type="text/javascript">
        $("[rel=tooltip]").tooltip();
        $(function() {
            $('.demo-cancel-click').click(function(){return false;});
            getRecentUsers(0);
            getRecentUsers(1);
        });
        $('#refreshNum').click(function(){
          $.ajax({
                type:"POST",
                url:"/tongbao/admin/getLatestNumInfo",
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
                        humane.log("刷新成功!");
                        $('#shipperNum').text(data.data.shipperNum);
                        $('#driverNum').text(data.data.driverNum);
                        $('#orderNum').text(data.data.orderNum);
                        $('#accountNum').text(data.data.accountNum);
                    }else{
                        alert("error!");
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
                    location.href = "/tongbao/admin/login";
                    alert("err/or!");
                } 
          });
        });
        function getRecentUsers(type){
          $.ajax({
            type: "POST",
            url: "/tongbao/admin/getRecentRegisterUsers",
            data: {type: type, num: 6},
            datatype: "json",
            success: function(data){
              if(data.result == 1){
                addUsersToTable(type,data.data);
              }else{
                alert("error!");
              }
            },
            error: function(){
              // location.href = "/tongbao/admin/login"
              alert('error');
            }
          });
        }
        function addUsersToTable(type,data){
          var trs = "";
          for (var i = 0; i <= data.length - 1; i++) {
            trs += "<tr><td>"+data[i].id+"</td><td>"+data[i].phoneNum+"</td><td>"+data[i].nickName+"</td><td>"+data[i].registerTime+"</td></tr>"
          };
          if(type == 0){
            $('#shipperTable tr:last').after(trs);
          }else{
            $('#driverTable tr:last').after(trs);
          }
        }
        function getRecentOrders(){
          $.ajax({
            type: "POST",
            url: "/tongbao/admin/getRecentOrders",
            data: {num: 6},
            datatype: "json",
            success: function(data){
              if(data.result == 1){
                addOrdersToTable(data.data);
              }else{
                alert("error");
              }
            },
            error: function(){
              alert("error!");
            }
          });
        }
        function addOrdersToTable(data){
          var trs = "";
          for (var i = 0; i <= data.length - 1; i++) {
            trs += "<tr><td>"+data[i].id+"</td><td>"+data[i].money+"</td><td>"+data[i].time+"</td><td>"+data[i].stateStr
            +"</td><td>"+data[i].addressFrom+"</td><td>"+data[i].addressTo+"</td></tr>"
          };
          $('#orderTable tr:last').after(trs);
        }
        function getRecentAccounts(){
          $.ajax({
            type: "POST",
            url: "/tongbao/admin/getRecentAccounts",
            data: {num: 6},
            datatype: "json",
            success: function(data){
              if(data.result == 1){
                addAccountsToTable(data.data);
              }else{
                alert("error");
              }
            },
            error: function(){
              alert("error!");
            }
          });
        }
        function addAccountsToTable(data){

        }
    </script>

  </body>
</html>


