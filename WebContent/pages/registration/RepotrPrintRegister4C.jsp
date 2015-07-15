<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<style type="text/css">
<!--
.style0 {
	font-size: 16px;
	fong-weight:500; background: #FCDFFF; border: 1px solid #000000;
}.style1 {
	font-size: 15px;
	fong-weight:300; background: #ddffff; border: 1px solid #000000;
}
.style2 {font-size:15px; fong-weight:300; background: #DDDDDD; border: 1px solid #000000;}
.style3 {font-size:12px; font-alignment:right; border: 1px solid #0000ff;}

// -->
</style>
<!-- Begin Content Page Table Header -->

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<center>
<font size="+3">中華科技大學 ${RegsReport.schoolYear} 學年度  第 ${RegsReport.schoolTerm} 學期 註冊資料統計表 </font>
</center>
<table width="100%" cellpadding="3" cellspacing="3" border="1">
	<tr><td class="style2">部制</td><td  class="style2">應註冊人數</td><td class="style2">已啟動註冊人數</td><td class="style2">尚未註冊人數</td><td class="style2">尚未註冊比例</td><td class="style2">新生人數</td><td class="style2">已啟動註冊新生數</td><td class="style2">尚未註冊新生數</td><td class="style2">新生尚未註冊比例</td></tr>
	<tr><td class="style1">台北日間</td><td class="style3">${RegsReport.total1D}</td><td class="style3">${RegsReport.reg1D}</td><td class="style3">${RegsReport.noReg1D}</td><td class="style3">${RegsReport.noRegPcn1D}</td><td class="style3">${RegsReport.new1D}</td><td class="style3">${RegsReport.newReg1D}</td><td class="style3">${RegsReport.newno1D}</td><td class="style3">${RegsReport.newNoRegPcn1D}</td></tr>
	<tr><td class="style1">台北進修</td><td class="style3">${RegsReport.total1N}</td><td class="style3">${RegsReport.reg1N}</td><td class="style3">${RegsReport.noReg1N}</td><td class="style3">${RegsReport.noRegPcn1N}</td><td class="style3">${RegsReport.new1N}</td><td class="style3">${RegsReport.newReg1N}</td><td class="style3">${RegsReport.newno1N}</td><td class="style3">${RegsReport.newNoRegPcn1N}</td></tr>
	<tr><td class="style1">台北學院</td><td class="style3">${RegsReport.total1H}</td><td class="style3">${RegsReport.reg1H}</td><td class="style3">${RegsReport.noReg1H}</td><td class="style3">${RegsReport.noRegPcn1H}</td><td class="style3">${RegsReport.new1H}</td><td class="style3">${RegsReport.newReg1H}</td><td class="style3">${RegsReport.newno1H}</td><td class="style3">${RegsReport.newNoRegPcn1H}</td></tr>
	<tr><td class="style1">新竹分部</td><td class="style3">${RegsReport.total2A}</td><td class="style3">${RegsReport.reg2A}</td><td class="style3">${RegsReport.noReg2A}</td><td class="style3">${RegsReport.noRegPcn2A}</td><td class="style3">${RegsReport.new2A}</td><td class="style3">${RegsReport.newReg2A}</td><td class="style3">${RegsReport.newno2A}</td><td class="style3">${RegsReport.newNoRegPcn2A}</td></tr>
	<tr><td class="style0">總計</td><td class="style0">${RegsReport.totalAll}</td><td class="style0">${RegsReport.regAll}</td><td class="style0">${RegsReport.noRegAll}</td><td class="style0">${RegsReport.noRegPcnAll}</td><td class="style0">${RegsReport.newAll}</td><td class="style0">${RegsReport.newRegAll}</td><td class="style0">${RegsReport.newnoAll}</td><td class="style0">${RegsReport.newNoRegPcnAll}</td></tr>
	<c:forEach items="${DeptRegs}" var="dep">
	<tr><td class="style1">${dep.name}</td><td class="style3">${dep.totalAll}</td><td class="style3">${dep.regAll}</td><td class="style3">${dep.noRegAll}</td><td class="style3">${dep.noRegPcnAll}</td><td class="style3">&nbsp;</td><td class="style3">&nbsp;</td><td class="style3">&nbsp;</td><td class="style3">&nbsp;</td></tr>
	</c:forEach>
</table>
