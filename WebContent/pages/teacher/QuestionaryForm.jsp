<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<script src="/CIS/pages/include/decorate.js"></script>

<table width="90%" id="coansForm" align="center" style="z-index:32768; position:absolute; left:62px; top:30px;" class="hairLineTable">
	<html:form action="/Teacher/AQ/LoginQform" method="post" onsubmit="init('處理中, 請稍後')">
	<tr height="30">
		<td class="fullColorTable" style="font-size:18px;">
		中華科技大學「行政與教學單位服務品質之滿意度調查」
		</td>
	</tr>
	<tr>
		<td class="hairLineTdF">
		

		<table width="99%" class="hairLineTable">
			<tr>
				<td class="hairLineTdF">
				親愛的老師，您好：此份問卷是調查本校「行政及教學單位服務品質的滿意度」，
				目的在瞭解學校提供教師們的服務是否滿意。您寶貴的意見將對本校提供教師服務有很大的幫助，
				調查結果將作為未來提升服務品質之參考。您所回答的內容將僅作為統計分析之用途，不對外公開，
				謝謝您的合作與支持。中華科技大學秘書室   啟
				</td>
			</tr>
			<tr>
				<td class="hairLineTdF" align="right">
				<input type="submit" name="method" value="<bean:message key='Skip'/>" class="gCancel">
				</td>
			</tr>
		</table>
		
		
		
		
		
		<c:if test="${empty qform}">
		<%@ include file="Qform/LoginDirect.jsp" %>
		</c:if>	
	
		<c:if test="${!empty qform}">
		<%@ include file="Qform/LoginQform.jsp" %>
		</c:if>

		</td>
	</tr>
	</html:form>
</table>

<script>
	document.getElementById("loadMsg").style.display="inline";
	history.go(1);
</script>

<script>
	function openView(){
		document.getElementById("loadMsg").style.display="none";
		document.getElementById("coansForm").style.display="none";
	}
</script>

<script>
	function showLoginButton(){
		document.getElementById("loginIco").src="/CIS/pages/images/catch_button_over_white.gif";
		document.getElementById("loginLab").color="#f7941d";
	}

	function hideLoginButton(){

		document.getElementById("loginIco").src="/CIS/pages/images/catch_button_white.gif";
		document.getElementById("loginLab").color="#000000";

	}
</script>

<script>
	function showQust(id){
		if(document.getElementById(id).style.display=='none'){
			document.getElementById(id).style.display='inline';
		}else{
			document.getElementById(id).style.display='none';
		}
		document.getElementById('loadMsg').style.height=document.body.scrollHeight;
		document.getElementById('loadMsg').style.width=document.body.scrollWidth;
	}
</script>

<script>
var scrollHeight=document.body.scrollHeight;
var clientHeight=document.body.clientHeight;
document.getElementById('loadMsg').style.width=document.body.scrollWidth*1.1;
document.getElementById('loadMsg').style.height=scrollHeight+50;// IE
document.getElementById('loadMsg').style.height=clientHeight+50;// IE
document.getElementById('loadMsg').style.height=document.body.scrollHeight+50;// FF, ns
</script>