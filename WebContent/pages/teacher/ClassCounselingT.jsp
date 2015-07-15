<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>"/>
<script>history.go(1);</script>

<script type="text/javascript" src="<%=basePath%>pages/include/json2.js"></script>

<style type="text/css">
<!--
.style1 {
	font-size: 24px;
	font-weight: bold;
}
.style2 {color: #FF0000}
.escalateBar {
	border-style: solid;
	border-width: 1px;
	border-color: #0000FF;
	background-color: lightcyan;
}
// -->
</style>
<!-- Begin Content Page Table Header -->

<form action="/CIS/Teacher/Tutor/CounselingT.do" method="post" name="csForm">
<table width="100%" cellpadding="0" cellspacing="0">

<script>generateTableBanner('<div class="gray_15"><B><bean:message key="CounselingRecordMaintain" bundle="TCH"/></B></div>');</script>	  
	<tr>
		<td>
<!-- End Content Page Table Header -->

		<table width="100%" cellspacing="5" class="empty-border">
			<tr>
				<td>
					說明：姓名後<font color="red">第一個數字</font>表示<font color="red">職涯輔導</font>次數，<font color="Green">第二個數字</font>表示<font color="Green">學習輔導</font>次數,<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;出現<font color="blue">第三個數字</font>表示該名學生<font color="blue">期中成績不及格達1/2或2/3個學分</font>,需進行<font color="blue">預警補救教學</font>。
				</td>
			</tr>
			<tr><td height="10">&nbsp;</td></tr>
		<c:set var="classcnt" value="0"></c:set>
		<input type="hidden" name="students" id="students" value="" />
		<c:forEach items="${ClassCounselingsT}" var="clazz">
			<c:set var="subject" value="${clazz.subject}"></c:set>
			<c:set var="students" value="${clazz.students}"></c:set>
			<tr>
				<td id="${subject.departClass}" class="escalateBar" onclick="onoffChild(this.id);chgEsclateImg('${subject.departClass}_img');">
				<c:if test="${classcnt=='0'}">
					<img src="<%=basePath%>pages/images/16-em-minus.png" id="${subject.departClass}_img">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</c:if>
				<c:if test="${classcnt!='0'}">
					<img src="<%=basePath%>pages/images/16-em-plus.png" id="${subject.departClass}_img">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</c:if>
					${subject.departClass}&nbsp;&nbsp;${subject.className}&nbsp;&nbsp;${subject.courseName}
				</td>
			</tr>
			<tr>
			<c:if test="${classcnt=='0'}">
				<td id="${subject.departClass}_content" style="display: inline;">
			</c:if>
			<c:if test="${classcnt!='0'}">
				<td id="${subject.departClass}_content" style="display: none;">
			</c:if>
				<table width="100%" class="bluebox">
					<c:set var="cnt" value="0"></c:set>
					<c:set var="total" value="${fn:length(students)}"></c:set>
					<c:set var="path" value="/CIS/Teacher/Tutor/Counseling.do"></c:set>
					<c:forEach items="${students}" var="student">
						<c:if test="${cnt==0}">
							<tr class="redbox">
						</c:if>
						<td width="10%" align="center">
						<input type="checkbox" name="studChkBox" id="${student.studentNo}" onClick="batchInput()"/>
						<c:if test="${student.fail == 'false'}">
						<a href="${path}?studentNo=${student.studentNo}&departClass=${subject.departClass}">${student.studentNo}<br>${student.studentName}[${student.countT}][${student.countU}]</a>
						</c:if>
						<c:if test="${student.fail == 'true'}">
						<a href="${path}?studentNo=${student.studentNo}&departClass=${subject.departClass}">${student.studentNo}<br>${student.studentName}[${student.countT}][${student.countU}][${student.failCount}]</a>
						</c:if>
						</td>
						<c:set var="cnt" value="${cnt+1}"/>
						<c:if test="${total<8 && total==cnt}">
							<c:forEach begin="0" end="${8-total-1}">
								<td width="10%">&nbsp;</td>
							</c:forEach>
						</c:if>
						<c:if test="${cnt==8}">
							</tr>
						<c:set var="cnt" value="0"></c:set>
						</c:if>
					</c:forEach>
				</table>
				</td>
			</tr>
		<c:set var="classcnt" value="1"></c:set>
		</c:forEach>
		</table>
		</td>
	</tr>
	<script>generateTableBanner('<Input type="submit" name="method" value="<bean:message key='BatchInsert'/>" >');</script>
	
</table>
</form>
<script language="javascript">
//<!--
var basepath = '<%=basePath%>';

function onoffChild(myid){
	var childId = myid + "_content";
	var child = document.getElementById(childId);
	if(child.style.display=='none'){
		child.style.display = 'inline';
	}else if(child.style.display=='inline'){
		child.style.display = 'none';
	}else{
		child.style.display = 'inline';
	}
}

function chgEsclateImg(imgId){
	var imgObj = document.getElementById(imgId);
	imgsrc = new String(imgObj.src);
	if(imgsrc.indexOf("plus")>0){
		// window.alert("plus");
		imgsrc.replace("plus", "minus");
		imgObj.src = basepath + "pages/images/16-em-minus.png";
	}else if(imgsrc.indexOf("minus")>0){
		// window.alert("minus");
		imgsrc.replace("minus", "plus");
		imgObj.src = basepath + "pages/images/16-em-plus.png";
	} 
}

function batchInput(){
	ids = new String();
	var cbObjs = document.getElementsByName("studChkBox");	//get students' checkbox
	var studs = document.getElementById("students");	//get hidden input that store the checked students
	for(i=0; i<cbObjs.length; i++){
		if(cbObjs[i].checked){
			ids = ids + cbObjs[i].id + "|";
		}
	}
	//window.alert("ids:" + ids);
	if(ids.length > 0){
		studs.value = ids.substring(0,ids.length-1);
	}else{
		studs.value = "";
	}
	//window.alert("students:" + studs.value);
}
//-->
</script>

