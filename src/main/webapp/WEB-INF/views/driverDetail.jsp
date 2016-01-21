<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Bootstrap Admin</title>
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
        #table td{
            text-align: center;
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
            <div class="stats">
    <p class="stat"><span class="number">48</span>未提交审核</p>
    <p class="stat"><span class="number">4</span>待审核</p>
    <p class="stat"><span class="number">4</span>已审核</p>
</div>

            <h1 class="page-title">司机详情</h1>
        </div>
        
                <ul class="breadcrumb">
            <li>用户管理 <span class="divider">/</span></li>
            <li><a href="/tongbao/admin/driverManage">司机管理</a><span class="divider">/</span></li>
            <li class="active">司机详情</li>
        </ul>

        <div class="container-fluid">
            <div class="row-fluid">
                    

    <div class="block">
        <p class="block-heading">个人信息</p>
        <div class="block-body">
            
                <img src="http://i8.tietuku.com/c745ecd88b59126ct.jpg" class="img-polaroid" style="position:absolute;left:3%;">
                <table class="table" id="table" style="position:relative;left:30%;width:60%;">
                  <tbody>
                    <tr>
                        <td>id</td>
                        <td>${id}</td>
                    </tr>
                    <tr>
                        <td>昵称</td>
                        <td>${nickName}</td>
                    </tr>
                    <tr>
                        <td>金币</td>
                        <td>${money}</td>
                    </tr>
                    <tr>
                        <td>手机号</td>
                        <td>${phoneNum}</td>
                    </tr>
                    <tr>
                        <td>积分</td>
                        <td>${point}</td>
                    </tr>
                    <tr>
                        <td>注册时间</td>
                        <td>${registerTime}</td>
                    </tr>
                  </tbody>
                </table>
                    
        </div>
    </div>

    <div class="block">
        <div class="block-heading">
            <span class="block-icon pull-right">
                <a href="#" class="demo-cancel-click" rel="tooltip" title="点击刷新" id="refreshNum"><i class="icon-refresh"></i></a>
            </span>

            <a href="#page-stats" data-toggle="collapse">车辆信息</a>
        </div>
        <div id="page-stats" class="block-body collapse in" style="margin-top:1em;">
            <table id="example" class="display" cellspacing="0" width="100%">
                  <thead>
                    <tr>
                      <th>id</th>
                      <th>车牌号</th>
                      <th>车辆类型</th>
                      <th>车辆信息(载重/长/宽/高)</th>
                      <th>驾驶人姓名</th>
                      <th>驾驶证号码</th>
                      <th>随车电话</th>
                      <th>审核状态</th>
                      <th style="width: 40px;"></th>
                    </tr>
                  </thead>
                  <tfoot>
                    <tr>
                      <th>id</th>
                      <th>车牌号</th>
                      <th>车辆类型</th>
                      <th>车辆信息(载重/长/宽/高)</th>
                      <th>驾驶人姓名</th>
                      <th>驾驶证号码</th>
                      <th>随车电话</th>
                      <th>审核状态</th>
                      <th style="width: 40px;"></th>
                    </tr>
                  </tfoot>
                  <tbody>
                  </tbody>
            </table>


        </div>

    </div>


                    
           <%@include file="footer.jsp" %>
                    
            </div>
        </div>
    </div>
    


    <script src="../lib/bootstrap/js/bootstrap.js"></script>
    <script type="text/javascript">
        var t;
        $(document).ready(function() {
           t = $('#example').DataTable();
        } );
        $("[rel=tooltip]").tooltip();
        $(function() {
            $('.demo-cancel-click').click(function(){return false;});
        });
    </script>
    
  </body>
</html>


