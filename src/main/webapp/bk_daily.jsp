<%@ page language="java" import="util.*,java.util.*,java.sql.*" pageEncoding="UTF-8" %>
<%@ page import="com.post.ibaties.secondary.xxx.send.entity.RSendData" %>
<%
    DataBean db = new DataBean();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="expires" content="0"/>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/style.css"/>
    <script type="text/javascript" src="<%=basePath%>js/jquery1.12.4.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $("tr:odd").css("background", "#ffffff");
            $("tr:even").css("background", "#ffe4e1");
        });
    </script>
    <style type="text/css">
        td {
            word-break: keep-all; /*支持IE，chrome，FF不支持*/
            word-wrap: normal; /*支持IE，chrome，FF*/
            white-space: nowrap;
            font-size: 20px;
        }
    </style>
</head>

<body style="overflow:auto;background:white;margin:10px;">
<%
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    String sql = "";

    conn = db.getConnection();
    sql = "select jg_name,jhrywl,jhrsr,ryjl,rsr from lfq_send_data,lfq_rjh where qj=jg_name";
    stmt = conn.prepareStatement(sql);
    List<RSendData> list = new ArrayList<>();
    rs = stmt.executeQuery();
    while (rs.next()) {
        String jg_name = rs.getString("jg_name");
        Integer jhryw = Integer.valueOf(rs.getString("jhrywl"));
        Integer jhrsr = Integer.valueOf(rs.getString("jhrsr"));
        Integer ryjl = Integer.valueOf(rs.getString("ryjl"));
        Double rsr = Double.valueOf(rs.getString("rsr"));
        RSendData data = new RSendData(jg_name, jhryw, jhrsr, ryjl, rsr);
        list.add(data);
    }
%>

<div id="div0"
     style="border:0px solid green;margin:0px;width:792px;height:983px;background:url(<%=basePath%>image/biaokuai_daily.png);background-size:792px 983px;background-repeat:no-repeat;">
    <div style="height:70px;width:100%;"></div>
    <div style="height:30px;line-height:30px;margin:0px 55px;font-size:36px;text-align:center;color:red;font-weight:bold;">
        2020年标快业务日报表
    </div>
    <div style="height:80px;line-height:35px;text-align:center;margin-left:-20px;margin-top:10px;color:red;font-size:26px;font-weight:bold;">
        　　(01月17日)
    </div>
    <div style="height:650px;margin:0px 55px;">
        <table id="dataTab" border="1" style="height:100%;width:100%;text-align:center;font-weight:bold;">
            <tr>
                <td rowspan="3">区分公司</td>
                <td colspan="2">日计划</td>
                <td colspan="4">日实绩</td>
            </tr>
            <tr>
                <td style="border-bottom: none">日均业务量</td>
                <td>日均业务收入</td>
                <td colspan="2" style="border-bottom: none">日业务量</td>
                <td colspan="2" style="border-bottom: none">日业务收入</td>
            </tr>
            <tr>
                <td style="border-top: none">（件）</td>
                <td style="border-top: none">（元）</td>
                <td style="border-top: none">实绩（件）</td>
                <td>完成比</td>
                <td style="border-top: none">实绩（元）</td>
                <td>完成比</td>
            </tr>

            <%
                for (int i = 0; i < list.size(); i++) {
            %>
            <tr>
                <td><%=list.get(i).getJgName()%></td>
                <td><%=list.get(i).getJhrywl()%></td>
                <td><%=list.get(i).getJhrsr()%></td>
                <td><%=list.get(i).getRyjl()%></td>
                <td><%=4.0%></td>
                <td><%=list.get(i).getRsr()%></td>
                <td><%=5.0%></td>
            </tr>
            <%
                }
            %>


        </table>
    </div>
    <div style="height:80px;line-height:30px;text-align:left;margin:10px 55px;color:red;font-weight:bold;font-size:20px;">
        备注：政务项目未剔除清分地市收入。数据源自新一代寄递平台，由程序自动提取生产、推送。
    </div>
</div>
<%
%>
</body>
</html>
