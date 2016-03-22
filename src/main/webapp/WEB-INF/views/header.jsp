<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="navbar">
        <div class="navbar-inner">
                <ul class="nav pull-right">

                    <!-- <li><a href="#" class="hidden-phone visible-tablet visible-desktop" role="button">Settings</a></li> -->
                    <li id="fat-menu" class="dropdown">
                        <a href="#" role="button" class="dropdown-toggle" data-toggle="dropdown">
                            <i class="icon-user"></i> <c:out value="${name}" />
                            <i class="icon-caret-down"></i>
                        </a>

                        <ul class="dropdown-menu">
                            <li><a tabindex="-1" href="/tongbao/admin/resetPassword">重设密码</a></li>
                            <li class="divider"></li>
                            <li><a tabindex="-1" class="visible-phone" href="#">Settings</a></li>
                            <li class="divider visible-phone"></li>
                            <li><a tabindex="-1" href="/tongbao/admin/logout">退出登录</a></li>
                        </ul>
                    </li>

                </ul>
                <a class="brand" href="index.html"><span class="first">通宝</span> <span class="second">后台管理</span></a>
        </div>
    </div>

    <div class="sidebar-nav">
        <form class="search form-inline">
            <input type="text" placeholder="搜索...">
        </form>
        <a href="#dashboard-menu" class="nav-header" data-toggle="collapse"><i class="icon-dashboard"></i>控制面板</a>
        <ul id="dashboard-menu" class="nav nav-list collapse in">
            <li><a href="/tongbao/admin/index">首页</a></li>
            <li ><a href="/tongbao/admin/orderManage">订单管理</a></li>
            <li ><a href="/tongbao/admin/accountManage">账单管理</a></li>

        </ul>

        <a href="#usermanager-menu" class="nav-header" data-toggle="collapse"><i class="icon-user"></i>用户管理</a>
        <ul id="usermanager-menu" class="nav nav-list collapse">
            <li ><a href="/tongbao/admin/shipperManage">货主管理</a></li>
            <li ><a href="/tongbao/admin/driverManage">司机管理</a></li>

        </ul>
        <a href="#accounts-menu" class="nav-header" data-toggle="collapse"><i class="icon-briefcase"></i>我的账户</a>
        <ul id="accounts-menu" class="nav nav-list collapse">
            <li ><a href="/tongbao/admin/logout">退出登录</a></li>
            <li ><a href="/tongbao/admin/resetPassword">重设密码</a></li>
        </ul>

    </div>