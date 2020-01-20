<%@ page language="java" import="util.*,java.util.*,java.sql.*" pageEncoding="UTF-8" %>
<%@ page import="com.post.ibaties.secondary.xxx.send.entity.RSendData" %>
<%@ page import="java.text.DecimalFormat" %>
<%
    DataBean db = new DataBean();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
    Calendar cal = Calendar.getInstance();
    int day = cal.get(Calendar.DATE) - 1;
    int month = cal.get(Calendar.MONTH) + 1;
    int a = 0, b = 0, c = 0, d = 0;
    int e = 0, f = 0, g = 0, h = 0;
    int s1 = 0, s2 = 0,rmb=0,rmb2=0;


%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="expires" content="0"/>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/style.css"/>
    <script type="text/javascript" src="js/jquery-3.4.1.min.js"></script>
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
    Connection conn2 = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    String sql = "";
    List<RSendData> list = new ArrayList<>();
    DecimalFormat df = new DecimalFormat("0");
    List<RSendData> list2 = new ArrayList<>();

    try {
        conn = db.getConnection();
        conn2 = db.getConnection2();
        sql = "select count(*) sl from lfq_send_data";
        stmt = conn.prepareStatement(sql);
        rs = stmt.executeQuery();
        if (rs.next() & rs.getInt("sl") <17) {
            out.print("<div id='errmsg' style='text-align:center;color:red;font-size:20px;'>没有时段数据</div></body></html>");
            return;
        }
        rs.close();
        //分公司小计
        sql = "select jg_name,rmbywl,jhrywl,jhrsr,ryjl,rsr from lfq_send_data,lfq_rjh where qj=jg_name and jg_name not in('同城项目','菜鸟项目','政务项目','华为项目')";
        rs = conn.prepareStatement(sql).executeQuery();
        while (rs.next()) {
            Integer rmbywl = Integer.valueOf(rs.getString("rmbywl"));
            Integer jhryw = Integer.valueOf(rs.getString("jhrywl"));
            Integer jhrsr = Integer.valueOf(rs.getString("jhrsr"));
            Integer ryjl = Integer.valueOf(rs.getString("ryjl"));
            Double rsr = Double.valueOf(rs.getString("rsr"));
            rmb += rmbywl;
            e += jhryw;
            f += jhrsr;
            g += ryjl;
            h += rsr;
        }
        // System.out.println(e+","+f+","+g+","+h);
        rs.close();
        //邮政板块
        stmt = conn2.prepareStatement("select count(*)sl from tb_report_02 where product_id='2' and xsbz='1' and riqi=to_char(sysdate-1,'yyyy-MM-dd')  and qjmc not in('全市','同城') order by xh");
        rs = stmt.executeQuery();
        if (rs.next() & rs.getInt("sl") < 13) {
            out.print("<div id='errmsg' style='text-align:center;color:red;font-size:20px;'>没有时段数据</div></body></html>");
            return;
        }
        rs.close();
        sql = "select qjmc name,yz_sl ryjl,yz_je rsr from tb_report_02 where product_id='2' and xsbz='1' and riqi=to_char(sysdate-1,'yyyy-MM-dd')  and qjmc not in('全市','同城') order by xh";
        rs = conn2.prepareStatement(sql).executeQuery();
        while (rs.next()) {
            int ryjl = rs.getInt("ryjl");
            double rsr = rs.getDouble("rsr");
            s1 += ryjl;
            s2 += rsr;
            RSendData data = new RSendData();
            data.setJgName(rs.getString(1));
            data.setRyjl(ryjl);
            data.setRsr(rsr);
            list2.add(data);
        }
        g += s1;
        h += s2;
        //System.out.println(e+","+f+","+g+","+h);
        rs.close();


        sql = "select jg_name,rmbywl,jhrywl,jhrsr,ryjl,rsr from lfq_send_data,lfq_rjh where qj=jg_name";
        stmt = conn.prepareStatement(sql);

        rs = stmt.executeQuery();
        while (rs.next()) {
            String jg_name = rs.getString("jg_name");
            int rmbywl = rs.getInt("rmbywl");
            int jhryw = rs.getInt("jhrywl");
            int jhrsr =  rs.getInt("jhrsr");
            int ryjl =  rs.getInt("ryjl");
            double rsr =  rs.getDouble("rsr");

            for(RSendData rSendData:list2){
                if(rSendData.getJgName().equals(jg_name)){
                    ryjl+=rSendData.getRyjl();
                    rsr+=rSendData.getRsr();
                    //System.out.println(ryjl+","+rSendData.getRyjl());
                }
            }
            //合计
            rmb2 += rmbywl;
            a += jhryw;
            b += jhrsr;
            c += ryjl;
            d += rsr;
            RSendData data = new RSendData(jg_name, jhryw, jhrsr, ryjl, rsr);
            data.setRmbywl(rmbywl);
            list.add(data);
        }
        rs.close();

%>

<div id="div0"
     style="border:0px solid green;margin:0px;width:792px;height:1150px;background:url(dise.png);background-size:792px 1150px;background-repeat:no-repeat;">
    <div style="height:70px;width:100%;"></div>
    <div style="font-family: 'microsoft yahei'height:30px;line-height:30px;text-align:right;margin:0px 55px;font-size:36px;text-align:center;color:#fff;font-weight:bold;">
        2020年全市标快业务日报表
    </div>
    <div style="height:80px;line-height:35px;text-align:center;margin-left:-20px;margin-top:20px;color:white;font-size:26px;font-weight:bold;">
        　　(<%=month < 10 ? "" + 0 + month : month%>月<%=day%>日)
    </div>
    <div style="height:650px;margin:0px 55px;">
        <table id="dataTab" border="1" style="height:100%;width:100%;text-align:center;font-weight:bold;margin-left: -33px;line-height: 33px">
            <tr>
                <td rowspan="3">区分公司</td>
                <td rowspan="3" style="text-align: center">日目标<br>业务量<br>（件）</td>
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
                    if (i == 13) {
            %>
            <tr>
                <td>分公司小计</td>
                <td><%=rmb%>
                </td>
                <td><%=e%>
                </td>
                <td><%=f%>
                </td>
                <td><%=g%>
                </td>
                <td><%=df.format((float) g / e * 100)%>%</td>
                <td><%=h%>
                </td>
                <td><%=df.format((float) h / f * 100)%>%</td>
            </tr>
            <%
                }
            %>
            <tr>
                <td><%=list.get(i).getJgName()%>
                </td>
                <td><%=list.get(i).getRmbywl()%>
                </td>
                <td><%=list.get(i).getJhrywl()%>
                </td>
                <td><%=list.get(i).getJhrsr()%>
                </td>
                <td><%=list.get(i).getRyjl()%>
                </td>
                <td><%=df.format((float) list.get(i).getRyjl() / list.get(i).getJhrywl() * 100)%>%</td>
                <td><%=df.format(list.get(i).getRsr())%>
                </td>
                <td><%=Math.round(list.get(i).getRsr() / list.get(i).getJhrsr() * 100)%>%</td>
            </tr>
            <%
                }
            %>
            <tr>
                <td>合计</td>
                <td><%=rmb2%>
                </td>
                <td><%=a%>
                </td>
                <td><%=b%>
                </td>
                <td><%=c%>
                </td>
                <td><%=df.format((float) c / a * 100)%>%</td>
                <td><%=d%>
                </td>
                <td><%=df.format((float) d / b * 100)%>%</td>
            </tr>
        </table>
    </div>
    <div style="height:80px;line-height:30px;text-align:left;margin:230px 25px;color:white;font-weight:bold;font-size:20px;">
        备注：政务项目未剔除清分地市收入，同城项目不含同城平台收入。数据源自新一代寄递平台，由程序自动提取生产、推送。
    </div>
</div>
<%
    } catch (Exception ex) {
        ex.printStackTrace();
    } finally {
        db.closeResource(conn, stmt, rs);
        db.closeResource(conn2, stmt, rs);
    }
%>
</body>
</html>
