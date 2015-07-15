<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<script>
history.go(1);
</script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<script>
	generateTableBanner('<table align="left">'+
									'<tr>'+
										'<td align="left">&nbsp;&nbsp;<img src="images/24-book-green-open.png"></td>'+
										'<td>暑修課程管理</td>'+
									'</tr>'+
								'</table>');
</script>
<html:form action="/Summer/sOpenCourse" method="post" onsubmit="init('查詢進行中, 請稍後')">
<!--主搜尋工具-->
<c:if test="${welcome}">
<c:import url="/pages/summer/CourseManager/search.jsp"/>
</c:if>



<!-- 查詢模式 Start -->
<c:if test="${mode=='list'}" >
<c:import url="/pages/summer/CourseManager/list.jsp"/>
</c:if>
<!-- 查詢模式 End-->


<!-- 編輯模式 Start-->
<c:if test="${mode=='edit'}" >
<c:import url="/pages/summer/CourseManager/edit.jsp"/>
</c:if>
<!-- 編輯模式 End -->

<!-- 新增模式 Start -->
<c:if test="${mode=='create'}" >
<c:import url="/pages/summer/CourseManager/create.jsp"/>
</c:if>
<!-- 新增模式 End -->


<!-- 檢視模式 Start -->
<c:if test="${mode=='view'}" >
<c:import url="/pages/summer/CourseManager/view.jsp"/>
</c:if>
<!-- 檢視模式  End -->



<!-- 刪除模式 Start -->
<c:if test="${mode=='delete'}" >
	<tr>
		<td>

		DELETE


		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center" width="100%">
		<INPUT type="submit"
						   name="method"
						   value="<bean:message key='DeleteConfirm'/>"
						   class="CourseButton"><INPUT type="submit"
						   						 name="method"
						   						 value="<bean:message key='Cancel'/>"
						  						 class="CourseButton">
		</td>
	</tr>
</c:if>
<!-- 刪除模式 End -->
</html:form>
</table>








<script>
	function setClass(id, values){
		//第10節為0, 第11節為a, 最大值為11(a)
		if(values=='10'){
			values='0';
		}
		if(values=='11'){
			values='a';
		}
		document.getElementById(id).value=document.getElementById(id).value+values;
	}
</script>



<c:import url="include/AjaxUniMod.jsp"/>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>