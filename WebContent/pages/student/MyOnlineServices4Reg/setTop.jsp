<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<table width="400" align="center" class="hairLineTable" style="position:absolute; right:30px; top:40px; filter:Alpha(opacity: 0.8);filter:alpha(opacity=80);-moz-opacity:0.8; z-index:32768;">
	<tr height="50">
		<td class="hairLineTdF" id="showInfo">
		
		<table align="center">
			<tr>
				<td><img src="images/icon/email_star.gif"></td>
				<td>您有 <b>${fn:length(myServices.countServ)}</b> 項服務正在申辦中..</td>
			</tr>
			<tr>
				<td colspan="2">
				<c:forEach items="${myServices.countServ}" var="c">
				${c.get_method} - 
				<b><a href="../getOnlineServiceDoc?docNo=${c.doc_no}">${c.doc_no}</a></b> - 狀態:
				<c:choose>
					<c:when test="${c.status=='W'}">等待中</c:when>
					<c:when test="${c.status=='O'}">處理中</c:when>
					<c:when test="${c.status=='F'}">已完成</c:when>
					<c:when test="${c.status=='B'}">已暫停</c:when>
					<c:when test="${c.status=='M'}">已寄出</c:when>
					<c:when test="${c.status=='R'}">已拒絕</c:when>
					<c:when test="${c.status=='C'}">已結案</c:when>
					<c:otherwise>不明</c:otherwise>
				</c:choose>
				<br>
				</c:forEach>				
				</td>
			</tr>
		</table>
		<table width="100%">
			<tr>
				<td width="100%" align="right"><img src="images/icon/icon_info_exclamation.gif"/></td>
				<td nowrap>
				<font size="-2"><a style="cursor:pointer;" onClick="document.getElementById('loadMsg').style.display='none';">
				如要申請其他文件請點一下<b>這裡</b></a></font>
				</td>
			</tr>	
		</table>				
		</td>
	</tr>
</table>
<script>
document.getElementById('loadMsg').style.width=document.body.scrollWidth;
	//document.getElementById('loadMsg').style.height=document.body.clientHeight;
	if (navigator.appName.indexOf("Microsoft")!=-1) {
		//alert("IE");
		//document.getElementById('loadMsg').style.height=document.body.scrollHeight;// IE
		//document.getElementById('loadMsg').style.height=document.body.clientHeight;
		var scrollHeight=document.body.scrollHeight;
		var clientHeight=document.body.clientHeight;
		if (scrollHeight>clientHeight){
			document.getElementById('loadMsg').style.height=scrollHeight+200;// IE
		}else{
			document.getElementById('loadMsg').style.height=clientHeight+200;// IE
		}
		
	}else{
		//alert("not IE");		
		//document.getElementById('loadMsg').style.height=document.body.clientHeight;
		//document.getElementById('loadMsg').style.height=document.body.offsetHeight;
		document.getElementById('loadMsg').style.height=document.body.scrollHeight+200;// FF, ns
	}
	document.getElementById('loadMsg').style.display='inline';
    //document.getElementById('loadIco').style.display='inline';
</script>