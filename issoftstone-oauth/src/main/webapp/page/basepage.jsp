<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.hy.include" prefix="hy" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="x-ua-compatible" content="IE=Edge">
    <link rel="icon" href="${ctx}/favicon.ico">
    <link rel="stylesheet" href="${ctx}/static/lib/layui/dist/css/layui.css">
    <link rel="stylesheet" href="${ctx}/static/lib/fontawesome/css/font-awesome.min.css">
    <script src="${ctx}/static/lib/jquery/dist/jquery.min.js"></script>
    <script src="${ctx}/static/lib/layui/dist/layui.js"></script>
    <script src="${ctx}/static/lib/layui/layer-util.js"></script>
    <script src="${ctx}/static/lib/layui/page.js"></script>
    <title>
        <hy:block name="title"></hy:block>
    </title>
    <hy:block name="javascript"></hy:block>
    <style type="text/css">
        .layui-nav * {
            font-size: initial;
        }

        * {
            font-size: 12px;
            /* font-family: Monaco, "Source Code Pro", serif; */
        }

        .layui-nav-tree {
            height: 100%;
            overflow-y: auto;
        }

        .layui-nav * {
            font-size: 12px;
        }

        @media screen and (min-width: 1600px) {
            * {
                font-size: 18px;
            }

            .layui-nav * {
                font-size: 18px !important;
            }
        }

        .layui-nav-tree::-webkit-scrollbar,
        .layui-table-box::-webkit-scrollbar,
        .layui-table-body::-webkit-scrollbar {
            /*滚动条整体样式*/
            width: 5px; /*高宽分别对应横竖滚动条的尺寸*/
            height: 5px;
        }

        .layui-nav-tree::-webkit-scrollbar-thumb,
        .layui-table-box::-webkit-scrollbar-thumb,
        .layui-table-body::-webkit-scrollbar-thumb {
            /*滚动条里面小方块*/
            border-radius: 10px;
            -webkit-box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
            background: #3d86a8;
        }

        .layui-nav-tree::-webkit-scrollbar-track,
        .layui-table-box::-webkit-scrollbar-track,
        .layui-table-body::-webkit-scrollbar-track {
            /*滚动条里面轨道*/
            -webkit-box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
            border-radius: 10px;
            background: #ededed;
        }

        .layui-nav-child a {
            padding-left: 40px !important;
        }

        .layui-nav-item i {
            margin: 10px 10px 10px 0px;
        }

        .layui-layout-left .layui-nav-child a {
            padding-left: 10px !important;
        }

        .layui-table, .layui-table-view {
            margin: 10px 10px;
        }
        .layui-table-page {
            text-align: right;
        }
        iframe {
            overflow-y: auto;
        }
      /*  body {
            overflow-y: auto !important;
        }*/
    </style>

    <hy:block name="css"></hy:block>
</head>
<body class="layui-layout-body">
    <hy:block name="body"></hy:block>
</body>
</html>