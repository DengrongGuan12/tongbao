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
            
                <img src="${url}" class="img-polaroid" style="position:absolute;left:3%;width:25%;height:27%;">
                <table class="table" id="table" style="position:relative;left:30%;width:60%;">
                  <tbody>
                    <tr>
                        <td>id</td>
                        <td id="userId">${id}</td>
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
                <a href="#" class="demo-cancel-click" rel="tooltip" title="点击刷新" id="refresh"><i class="icon-refresh"></i></a>
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
                    <tr>
                      <td>1</td>
                      <td>Mark</td>
                      <td>Tompson</td>
                      <td>the_mark7</td>
                      <td>Tompson</td>
                      <td>the_mark7</td>
                      <td>Tompson</td>
                      <td>the_mark7</td>
                      <td>
                          <a href="javascript:void(0)" title="审核" role="button" onclick="resetPassword(12);"><i class="icon-edit"></i></a>
                          <a href="javascript:void(0)" role="button" title="删除" onclick="showModal(12);"><i class="icon-remove"></i></a>
                      </td>
                    </tr>
                  </tbody>
            </table>


        </div>

    </div>
    <div class="modal small hide fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h3 id="myModalLabel">删除确认</h3>
        </div>
        <div class="modal-body">
            <p class="error-text"><i class="icon-warning-sign modal-icon"></i>你确定要删除id为<span id="truck-id">10</span>的车辆信息吗?</p>
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
        var refresh = false;
        $(document).ready(function() {
           t = $('#example').DataTable();
           getTruckInfoList();
        } );
        $("[rel=tooltip]").tooltip();
        $(function() {
            $('.demo-cancel-click').click(function(){return false;});

        });
        $('#refresh').click(function(){
          refresh = true;
          getTruckInfoList();
        });
        $('#delete').click(function(){
          var child = '#'+deleteId;
          $.ajax({
            type:"POST",
            url:"/tongbao/admin/deleteTruck",
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
        function getTruckInfoList(){
          var userId = $('#userId').text();
          $.ajax({
            type:"POST",
            url:"/tongbao/admin/getAllTruckInfo",
            //提交的数据
            data:{userId:userId},
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
                  if(refresh){
                    humane.log("刷新成功!");
                  }
                  addTrucksToTable(data.data);
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
        function showModal(truckId){
          $('#truck-id').text(truckId);
          deleteId = truckId;
          $('#myModal').modal();
        }
        function addTrucksToTable(data){
          t.clear().draw();
          if(data != null){
            for(var i = 0;i<data.length;i++){
              var operation = "";
              var state = "";
              switch(data[i].authState){
                case 1:
                  state = "正在审核";
                  operation += "<a href='/tongbao/admin/truckDetail/"+data[i].id+"' title='审核' role='button' ><i class='icon-edit'></i></a>"
                  break;
                case 0:
                  state = "未提交审核";
                  break;
                case 2:
                  state = "审核通过";
                  break;
                case 3:
                  state = "审核不通过"
                  break;
              }
              operation += "  <a href='javascript:void(0)' role='button' title='删除' onclick='showModal("+data[i].id+");' id='"+data[i].id+"'><i class='icon-remove'></i></a>";
              var truckInfo = data[i].capacity+"吨/"+data[i].length+"米/"+data[i].width+"米/"+data[i].height+"米";
              t.row.add([
                data[i].id,
                data[i].truckNum,
                data[i].typeName,
                truckInfo,
                data[i].realName,
                data[i].licenseNum,
                data[i].phoneNum,
                state,
                operation
              ]).draw(false);
            }
          }
        }
    </script>
    
  </body>
</html>


