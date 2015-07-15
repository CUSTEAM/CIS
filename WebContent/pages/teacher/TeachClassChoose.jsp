<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<style type="text/css">
<!--
.style1 {
	font-size: 24px;
	font-weight: bold;
}
.div1 {
	font-size: 13px;
	font-weight: normal;
	height: auto;
	width: auto;
	border: 1px solid #FF6600;
	margin: 3px;
}

.scoreprn {
	background-color: #cfe69f;
	border: 1px solid #cfe69f;
	position: absolute;
	margin-top: -25%;
	margin-left: -25%;
	margin: auto;
	z-index: 32767;
}

-->
</style>
<style type="text/css">
<!--
.style2 {color: #FF0000}
-->
</style>

<script type="text/javascript">
<!--
noMenu = false;
openMenu();
function resetTimeOut(){
	if(myTimeOut != null) {
		globalTimeOut = myTimeOut;
		myTimeOut = null;
	}
}

function setcscode(cscode, dtimeoid)
{
	var ips = document.getElementsByName("cscode");
	var ips2 = document.getElementsByName("dtimeoid");

  	for(var i=0; i<=ips.length-1;i++)
  	{
      	ips[i].value=cscode;
  	}
  	for(var i=0; i<=ips2.length-1;i++)
  	{
      	ips2[i].value=dtimeoid;
  	}
  	// alert(ips[0].value + " , " + cscode);
};

resetTimeOut;
//-->
</script>

<c:if test="${TchScoreUploadPrint != null && TchScoreUploadPrint.openMode == '101'}">
<c:set var="printURL" value="/CIS/pages/teacher/ScoreUploadPrint.jsp"/>
<c:set var="title" value="平時及期末成績上傳列印"/>
<script language="javaScript">
<!-- 
function subwin2print() {
	subwin = window.open("/CIS/pages/teacher/ScoreUploadPrint.jsp","平時及期末成績上傳列印",
	"width=800,height=600,scrollbars=yes,resizable=yes,left=0,top=0");
};
//window.onload=subwin2print;
// -->
</script>
</c:if>
<c:if test="${TchScoreUploadPrint != null && TchScoreUploadPrint.scoretype == '1'}">
<c:set var="printURL" value="/CIS/pages/teacher/ScoreMidTermPrint1.jsp"/>
<c:set var="title" value="期中成績上傳列印"/>
<script language="javaScript">
<!-- 
function subwin2print() {
	subwin = window.open("/CIS/pages/teacher/ScoreMidTermPrint1.jsp","期中成績上傳列印",
	"width=800,height=600,scrollbars=yes,resizable=yes,left=0,top=0");
};
//window.onload=subwin2print;
// -->
</script>
</c:if>
<c:if test="${TchScoreUploadPrint != null && TchScoreUploadPrint.scoretype == '3'}">
<c:set var="printURL" value="/CIS/pages/teacher/ScoreThisTermPrint.jsp"/>
<c:set var="title" value="學期成績上傳列印"/>
<script language="javaScript">
<!-- 
function subwin2print() {
	subwin = window.open("/CIS/pages/teacher/ScoreThisTermPrint.jsp","學期成績上傳列印",
	"width=800,height=600,scrollbars=yes,resizable=yes,left=0,top=0");
};
//window.onload=subwin2print;
// -->
</script>
</c:if>
<c:if test="${TchScoreBlankPrint != null && TchScoreBlankPrint.scoretype == '9'}">
<c:set var="printURL" value="/CIS/pages/teacher/ScoreBlankPrint.jsp"/>
<c:set var="title" value="空白成績冊列印"/>
<script language="javaScript">
<!-- 
function subwin2print() {
	subwin = window.open("/CIS/pages/teacher/ScoreBlankPrint.jsp","空白成績冊列印",
	"width=800,height=600,scrollbars=yes,resizable=yes,left=0,top=0");
};

//window.onload=subwin2print;
// -->
</script>
</c:if>

<c:if test="${TchScorePrint != null && TchScorePrint.scoretype == '8' && TchScorePrint.now == ''}">
<!-- 
<script language="javaScript">
alert("平時及期末成績上傳步驟尚未完成,\n無法列印成績!");
</script>
// -->
</c:if>
<c:if test="${TchScorePrint != null && TchScorePrint.scoretype == '8'}">
<c:set var="printURL" value="/CIS/pages/teacher/ScoreFinalPrint.jsp"/>
<c:set var="title" value="平時及期末成績冊列印"/>
<script language="javaScript">
<!-- 
function subwin2print() {
	subwin = window.open("/CIS/pages/teacher/ScoreFinalPrint.jsp","平時及期末成績冊列印",
	"width=800,height=600,scrollbars=yes,resizable=yes,left=0,top=0");
};

//window.onload=subwin2print;
// -->
</script>
</c:if>

<c:if test="${TchScorePrint != null && TchScorePrint.scoretype == '7' && TchScorePrint.now == ''}">
<!-- 
<script language="javaScript">
alert("成績上傳步驟尚未完成,\n無法列印成績!");
</script>
// -->
</c:if>
<c:if test="${TchScorePrint != null && TchScorePrint.scoretype == '7'}">
<c:set var="printURL" value="/CIS/pages/teacher/ScoreThisPrint.jsp"/>
<c:set var="title" value="學期成績冊列印"/>
<script language="javaScript">
<!-- 
function subwin2print() {
	subwin = window.open("/CIS/pages/teacher/ScoreThisPrint.jsp","學期成績冊列印",
	"width=800,height=600,scrollbars=yes,resizable=yes,left=0,top=0");
};

//window.onload=subwin2print;
// -->
</script>
</c:if>

<c:if test="${TchScorePrint != null && TchScorePrint.scoretype == '6' && TchScorePrint.now == ''}">
<script language="javaScript">
<!-- 
alert("成績上傳步驟尚未完成,\n無法列印成績!");
// -->
</script>
</c:if>
<c:if test="${TchScorePrint != null && TchScorePrint.scoretype == '6' && TchScorePrint.now != ''}">
<c:set var="printURL" value="/CIS/pages/teacher/ScoreMiddlePrint.jsp"/>
<c:set var="title" value="期中成績冊列印"/>
<script language="javaScript">
<!-- 
function subwin2print() {
	subwin = window.open("/CIS/pages/teacher/ScoreMiddlePrint.jsp","期中成績冊列印",
	"width=800,height=600,scrollbars=yes,resizable=yes,left=0,top=0");
};

//window.onload=subwin2print;
// -->
</script>
</c:if>

<!-- <html:form action="/Score/ScoreHist" method="post"> -->
<form action="/CIS/Teacher/ScoreUploadAll.do" method="post" name="inputForm">
<input name="scoretype" type="hidden" value="${TchScoreUploadInfo.scoretype}"/>
<input name="cscode" type="hidden" value=""/>
<input name="dtimeoid" type="hidden" value=""/>
<!-- Begin Content Page Table Header -->
<table width="100%" cellpadding="0" cellspacing="0">
<script>generateTableBanner('教師資訊系統');</script>	  
	<tr>
		<td width="100%" align="center" valign="top" class="decorate">
<!-- End Content Page Table Header -->

	<table width="100%" border="0" cellpadding="5" cellspacing="1">
      <!--DWLayoutTable-->
      <tr>
        <td height="33" colspan="6" align="left" valign="top" class="menuIndex">
        <c:out value="教師輸入成績系統"/> -&gt;<span class="style2">
        	<c:if test="${TchScoreUploadInfo.scoretype=='1'}"> 期中成績上傳</c:if>
        	<c:if test="${TchScoreUploadInfo.scoretype=='2'}"> 平時及期末成績上傳</c:if>
        </span></td>
  	    </tr>
      <tr>
        <td  height="35" colspan="6" align="left" valign="middle" bgcolor="#CCCCFF">
       	<font color="blue">${TchScoreUploadInfo.teacherName}&nbsp;老師</font>
       	<c:if test="${! empty TeachClassInChoose}">
       		<c:if test="${TchScoreUploadInfo.scoretype=='1'}">
       	 		:本程式為上傳期中考成績
       		</c:if>
       		<c:if test="${TchScoreUploadInfo.scoretype=='2'}">
       	 		:本程式為輸入學期成績<font color=red>(系統自動計算學期成績,需輸入平時及期末成績)</font>
       		</c:if>
       		<c:if test="${TchScoreUploadInfo.scoretype=='3'}">
       	 		:本程式為輸入學期成績<font color=red>(自行計算學期成績)</font>
       		</c:if>
       		<br/><br/>
       		<c:if test="${TchScoreUploadInfo.scoretype=='1' || TchScoreUploadInfo.scoretype=='2'|| TchScoreUploadInfo.scoretype=='3'}">
　　			請點選要輸入的班級：
			</c:if>
       		<c:if test="${TchScoreUploadInfo.scoretype=='9'}">
　　			請點選要列印的班級：<br><br>
			<div class="div1"><font color=red>如果沒有出現列印成績的視窗,
			表示您的瀏覽器有開啟阻擋彈跳視窗的功能,
			請解除或暫時允許彈跳視窗功能,謝謝!</font></div>
			<br>
			</c:if>
		</c:if>
        </td>
      	</tr>
      <tr>
      	<td>
		&nbsp;</td>
      </tr>
      <tr>
      	<td>
      	
		  <c:if test="${TeachClassInChoose != null}" >
      	   <c:forEach items="${TeachClassInChoose}" var="classes">
      			<input type="radio" name="selclass" value="${classes.departClass}" onclick='setcscode("${classes.cscode}","${classes.oid}");'/>
      			${classes.className2}(${classes.oid})&nbsp;&nbsp;${classes.chiName2}<br/>
          </c:forEach>
          </c:if>
		  <c:if test="${empty TeachClassInChoose}" >
		  	<font color=red>成績輸入尚未開放</font> 或者 <font color=gray>您這學期沒有授課班級</font>!!!
		  </c:if>
		  
     	</td>
      </tr>
	</table>
		</td>		
	</tr>
	<c:if test="${! empty TeachClassInChoose}">
	<script>
	generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='OK' bundle='SCR'/>" >&nbsp;&nbsp;'+
	'<INPUT type="submit" name="method" value="<bean:message key='Cancel' bundle='SCR'/>" >&nbsp;&nbsp;');
	</script>
	</c:if>
<!-- Begin Content Page Table Footer -->
	<c:if test="${TchScorePrint != null && TchScorePrint.now != ''}">
    <tr>
    	<td><br>選擇輸出格式:
    		<a href="teacher/TeachPrintScore.jsp?type=excel">
    		<img src="images/ico_file_excel.png" border="0"> Excel
    		</a>
    		<a href="teacher/TeachPrintScore.jsp?type=word">
    		<img src="images/ico_file_word.png" border="0"> Word
    		</a>
    	</td>
    </tr>
	</c:if>
	<c:if test="${TchScoreUploadPrint != null && (TchScoreUploadPrint.openMode == '101' ||
	TchScoreUploadPrint.scoretype == '1'||TchScoreUploadPrint.scoretype == '3')}">
    <tr>
    	<td><br>選擇輸出格式:
    		<a href="teacher/TeachUploadPrintScore.jsp?type=excel">
    		<img src="images/ico_file_excel.png" border="0"> Excel
    		</a>
    		<a href="teacher/TeachUploadPrintScore.jsp?type=word">
    		<img src="images/ico_file_word.png" border="0"> Word
    		</a>
    	</td>
    </tr>
	</c:if>
				
</table>
<!-- End Content Page Table Footer -->
</form>


<!-- </html:form> -->
<script>
history.go(1);
</script>
