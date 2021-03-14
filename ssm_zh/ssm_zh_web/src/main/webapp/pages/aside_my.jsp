<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020-03-18
  Time: 22:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
        <!-- Sidebar user panel -->
        <div class="user-panel">
            <div class="pull-left image">
                <img src="${pageContext.request.contextPath}/img/user2-160x160.jpg" class="img-circle" >
            </div>
            <div class="pull-left info">
                <p>张猿猿</p>
                <a href="#"><i class="fa fa-circle text-success"></i> 在线</a>
            </div>
        </div>
        <!-- search form -->
        <!--<form action="#" method="get" class="sidebar-form">
    <div class="input-group">
        <input type="text" name="q" class="form-control" placeholder="搜索...">
        <span class="input-group-btn">
        <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i>
        </button>
      </span>
    </div>
</form>-->
        <!-- /.search form -->


        <!-- sidebar menu: : style can be found in sidebar.less -->
        <ul class="sidebar-menu">
            <li class="header">菜单</li>

            <li id="admin-index">
                <a href="${pageContext.request.contextPath}/pages/main.jsp">
                    <i class="fa fa-dashboard"></i>
                    <span>首页</span></a>
            </li>



            <!-- 菜单 -->

            <li class="treeview" id="treeview">
                <a href="#">
                    <i class="fa fa-cube"></i> <span>数据管理</span>
                    <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                    </span>
                </a>
                <ul class="treeview-menu" id="treeview-menu">

                    <li id="product-manage">
                        <a href="${pageContext.request.contextPath}/product/all">
                            <i class="fa fa-circle-o"></i> 产品管理
                        </a>
                    </li>

                    <li id="order-manage">
                        <a href="${pageContext.request.contextPath}/orders/all">
                            <i class="fa fa-circle-o"></i> 订单管理
                        </a>
                    </li>

                    <li id="user-manage">
                        <a href="${pageContext.request.contextPath}/user/all">
                            <i class="fa fa-circle-o"></i> 用户管理
                        </a>
                    </li>

                    <li id="role_manager">
                        <a href="${pageContext.request.contextPath}/role/all">
                            <i class="fa fa-circle-o"></i> 角色管理
                        </a>
                    </li>

                    <li id="permission_manager">
                        <a href="${pageContext.request.contextPath}/permission/all">
                            <i class="fa fa-circle-o"></i> 资源权限管理
                        </a>
                    </li>

                    <li id="log_manager">
                        <a href="${pageContext.request.contextPath}/syslog/all">
                            <i class="fa fa-circle-o"></i> 用户日志
                        </a>
                    </li>

                </ul>
            </li>

            <li class="treeview">
                <a href="#">
                    <i class="fa fa-cogs"></i> <span>系统管理</span>
                    <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                    </span>
                </a>
                <ul class="treeview-menu">

                    <li id="system-setting">
                        <a href="all-system-setting-edit.html">
                            <i class="fa fa-circle-o"></i> 系统设置
                        </a>
                    </li>

                </ul>
            </li>


        </ul>
    </section>
    <!-- /.sidebar -->
    <script>

    </script>
</aside>
