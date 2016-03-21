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
        #table td{
            text-align: center;
        }
        #table th{
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
            <h1 class="page-title">司机详情</h1>
        </div>
        
                <ul class="breadcrumb">
            <li>用户管理 <span class="divider">/</span></li>
            <li><a href="/tongbao/admin/driverManage">司机管理</a><span class="divider">/</span></li>
            <li><a href="/tongbao/admin/driverDetail/${userId}">司机详情</a><span class="divider">/</span></li>
            <li class="active">货车详情</li>
        </ul>

        <div class="container-fluid">
            <div class="row-fluid">
                    

    <div class="block">
        <p class="block-heading">信息概要</p>
        <div class="block-body">
              <table class="table" id="table">
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
                    </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>${id}</td>
                    <td>${truckNum}</td>
                    <td>${typeName}</td>
                    <td>${capacity}吨/${length}米/${width}米/${height}米</td>
                    <td>${realName}</td>
                    <td>${licenseNum}</td>
                    <td>${phoneNum}</td>
                    <td id="state"></td>
                  </tr>
                </tbody>
              </table>
                    
        </div>
    </div>
    <div class="block">
        <div class="block-heading">

            <a href="#driving-info" data-toggle="collapse">照片信息</a>
        </div>
        <div id="driving-info" class="block-body collapse in" style="margin-top:1em;">
          <table class="table" id="table">
            <tbody>
              <tr>
                  <td style="width: 25%;">
                    <div>
                      <img src="${drivingLicense}" class="img-polaroid" style="position:relative;left:3%;margin-bottom: 15px;width:100%;height:100%;">
                      <div>行驶证照片</div>
                    </div>
                  </td>
                  <td style="width: 25%;">
                    <div>
                      <img src="${truckLicense}" class="img-polaroid" style="position:relative;left:3%;margin-bottom: 15px;width:100%;height:100%;">
                      <div>驾驶证照片</div>
                    </div>
                  </td>
                  <td style="width: 25%;">
                    <div>
                      <img src="${headPicture}" class="img-polaroid" style="position:relative;left:3%;margin-bottom: 15px;width:100%;height:100%;">
                      <div>司机头像</div>
                    </div>
                  </td>
                  <td style="width: 25%;">
                    <div>
                      <img src="${truckPicture}" class="img-polaroid" style="position:relative;left:3%;margin-bottom: 15px;width:100%;height:100%;">
                      <div>车头照</div>
                    </div>
                  </td>
              </tr>
            </tbody>
          </table>
          
        </div>
    </div>
    <c:choose>
      <c:when test="${authState == 1}">
        <div style="width:100%;text-align:center;">
          <button id="pass">通过</button>
          <button id="reject">不通过</button>
        </div>
      </c:when>
    </c:choose>
    
    <div class="modal small hide fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h3 id="myModalLabel">审核确认</h3>
        </div>
        <div class="modal-body">
            <p class="error-text"><i class="icon-warning-sign modal-icon"></i>你确定要审核状态设为<span id="truck-id">通过</span>吗?</p>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
            <button class="btn btn-danger" data-dismiss="modal" id="sure-pass">通过</button>
        </div>
    </div>
    <div class="modal small hide fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h3 id="myModalLabel">审核确认</h3>
        </div>
        <div class="modal-body">
            <p class="error-text"><i class="icon-warning-sign modal-icon"></i>你确定要审核状态设为<span id="truck-id">不通过</span>吗?</p>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
            <button class="btn btn-danger" data-dismiss="modal" id="sure-reject">不通过</button>
        </div>
    </div>

                    
           <%@include file="footer.jsp" %>
                    
            </div>
        </div>
    </div>
    


    <script src="../lib/bootstrap/js/bootstrap.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
           var authState = ${authState};
           var state = "";
           switch(authState){
            case 0:
              state = "未提交审核";
              break;
            case 1:
              state = "正在审核";
              break;
            case 2:
              state = "审核通过";
              break;
            case 3:
              state = "审核不通过";
              break;

           }
           $('#state').text(state);
        } );
        $("[rel=tooltip]").tooltip();
        $(function() {
            $('.demo-cancel-click').click(function(){return false;});

        });
        $('#pass').click(function(){
          $('#myModal1').modal();
        });
        $('#reject').click(function(){
          $('#myModal2').modal();
        });
        $('#sure-pass').click(function(){
          auth(2);
        });
        $('#sure-reject').click(function(){
          auth(3);
        });
        function auth(state){
          $.ajax({
                type:"POST",
                url:"/tongbao/admin/setAuthState",
                data:{id:${id},state:state},
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
                        window.location.reload();
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
                    // location.href = "/tongbao/admin/login";
                    alert("error!");
                } 
          });
        }
    </script>
    
  </body>
</html>


