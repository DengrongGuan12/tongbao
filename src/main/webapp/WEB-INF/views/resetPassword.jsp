<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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

    <div class="navbar">
        <div class="navbar-inner">
                <ul class="nav pull-right">

                </ul>
                <a class="brand" href="index.html"><span class="first">通宝</span> <span class="second">后台管理</span></a>
        </div>
    </div>






        <div class="row-fluid">
    <div class="dialog">
        <div class="block">
            <p class="block-heading">重设密码</p>
            <div class="block-body">
                <form onsubmit="return resetPwd();">
                    <label>原密码</label>
                    <input type="password" class="span12" id="oldPassword" required>
                    <label>新密码</label>
                    <input type="password" class="span12" id="newPassword1" required>
                    <label>再次输入</label>
                    <input type="password" class="span12" id="newPassword2" required>
                    <button type="submit" class="btn btn-primary pull-right">提交</button>
                    <div class="clearfix"></div>
                </form>
            </div>
        </div>
    </div>
</div>





    <script src="../lib/bootstrap/js/bootstrap.js"></script>
    <script type="text/javascript">
        $("[rel=tooltip]").tooltip();
        $(function() {
            $('.demo-cancel-click').click(function(){return false;});
        });
        function resetPwd(){
            var oldPassword = $("#oldPassword").val();
            var newPassword1 = $("#newPassword1").val();
            var newPassword2 = $("#newPassword2").val();
            if(newPassword2 === newPassword1){
                $.ajax({
                    type: "POST",
                    url: "/tongbao/admin/modifyPassword",
                    data: {oldPassword: oldPassword, newPassword: newPassword1},
                    datatype: "JSON",
                    beforeSend:function(){
                    // alert("beforeSend");
                    },
                    //成功返回之后调用的函数             
                    success:function(data){
                        // alert(data); 
                        if(data.result == 1){
                            alert("修改成功!");
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
                        alert("error");
                    } 
                });
            }else{
                alert("两次输入的密码不一致!");
            }
            return false;
        }
    </script>

  </body>
</html>


