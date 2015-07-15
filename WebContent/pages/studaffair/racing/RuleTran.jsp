<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<style type="text/css">
<!--
.input {
	width:20px;
}
.style1 {
	font-size: 24px;
	font-weight: bold;
}
.style2 {color: #FF0000}
-->
</style>
<script type="text/javascript">
<!--
function lockInput(tagId)
{
	var obj=document.getElementById(tagId);
	obj.readOnly=true;
}
// -->
</script>


<form action="/CIS/StudAffair/Racing/RuleTransmit.do" method="post" name="tfForm">

<c:set var="tcnt" value="1"/>

<table width="100%" cellpadding="0" cellspacing="0" border="0">

<!-- Begin Content Page Table Header -->
<script>generateTableBanner('<div class="gray_15"><B><bean:message key="SAFTitle.RuleTranMaintain" bundle="SAF"/></B></div>');</script>	  
	<tr>
		<td align="left">
<!-- End Content Page Table Header -->

	<table cellspacing="5" class="empty-border">
	  <tr>
	  <td colspan="10"><font color="red">班級幹部會議未到扣分填寫時,分數前請加負號!!!</font></td>
	  </tr>
      <tr>
        	<td>&nbsp;</td>
        	<td width="35" align="center" valign="middle">班級</td>
        	<td width="120" align="center" valign="middle">班級名稱</td>
        	<td width="35" align="center" valign="middle">規定傳達分數</td>
        	<td width="35" align="center" valign="middle">會議未到扣分</td>
        	<td width="35" align="center" valign="middle">班級</td>
        	<td width="120" align="center" valign="middle">班級名稱</td>
        	<td width="35" align="center" valign="middle">規定傳達分數</td>
         	<td width="35" align="center" valign="middle">會議未到扣分</td>
         	<td>&nbsp;</td>
      </tr>
      <c:choose>
        <c:when test="${RuleTranInEdit != null}">
        	<c:set var="classes" value="${RuleTranInEdit.depart_class}"/>
        	<c:set var="clazzNames" value="${RuleTranInEdit.departClassName}"/>
        	<c:set var="scores" value="${RuleTranInEdit.score}"/>
        	<c:set var="mscores" value="${RuleTranInEdit.mscore}"/>
        	<c:set var="rcnt" value="0"/>
			<c:forEach begin="0" end="${fn:length(RuleTranInEdit)}">
				<input type="hidden" name="depart_class" value="${classes[rcnt]}"/>
				<input type="hidden" name="departClassName" value="${clazzNames[rcnt]}"/>
				
				<c:if test="${rcnt%2 == 0}">
		      	<tr>
        			<td>&nbsp;</td>
		        	<td width="35" align="left" valign="middle" bgcolor="#ffa07a">
		        		<c:out value="${classes[rcnt]}" />
		        	</td>
		        	<td width="120" align="left" valign="middle" bgcolor="#ffa07a">
		        		<c:out value="${clazzNames[rcnt]}" />
		        	</td>
		        	<td width="35" align="left" valign="middle" bgcolor="#f5deb3">
		           	<input name="score" id="${rcnt}" value="${scores[rcnt]}" 
		           	type="text" size="5" maxlength="5" 
		          		onblur="if(this.value!='') checknum(this);">
		        	</td>
		        	<td width="35" align="left" valign="middle" bgcolor="#f5deb3">
		           	<input name="mscore" id="${rcnt}" value="${mscores[rcnt]}" 
		           	type="text" size="5" maxlength="5" 
		          		onblur="if(this.value!='') checknum(this);">
		        	</td>
				</c:if>
				<c:if test="${rcnt%2 != 0}">
		        	<td width="35" align="left" valign="middle" bgcolor="#add8e6">
		        		<c:out value="${classes[rcnt]}" />
		        	</td>
		        	<td width="120" align="left" valign="middle" bgcolor="#add8e6">
		        		<c:out value="${clazzNames[rcnt]}" />
		        	</td>
		        	<td width="35" align="left" valign="middle" bgcolor="#f5deb3">
		           	<input name="score" id="${rcnt}" value="${scores[rcnt]}" 
		           	type="text" size="5" maxlength="5" 
		          		onblur="if(this.value!='') checknum(this);">
		        	</td>
		        	<td width="35" align="left" valign="middle" bgcolor="#f5deb3">
		           	<input name="mscore" id="${rcnt}" value="${mscores[rcnt]}" 
		           	type="text" size="5" maxlength="5" 
		          		onblur="if(this.value!='') checknum(this);">
		        	</td>
        			<td>&nbsp;</td>
        		</tr>
				</c:if>
        		<c:set var="rcnt" value="${rcnt+1}"/>
			</c:forEach>
         </c:when>
         <c:when test="${! empty RuleTranList}">
        	<c:set var="rcnt" value="0"/>
			<c:forEach items="${RuleTranList}" var="ruleTran">
				<input type="hidden" name="depart_class" value="${ruleTran.departClass}"/>
				<input type="hidden" name="departClassName" value="${ruleTran.departClassName}"/>
				
				<c:if test="${rcnt%2 == 0}">
		      	<tr>
        			<td>&nbsp;</td>
		        	<td width="35" align="left" valign="middle" bgcolor="#ffa07a">
		        		<c:out value="${ruleTran.departClass}" />
		        	</td>
		        	<td width="120" align="left" valign="middle" bgcolor="#ffa07a">
		        		<c:out value="${ruleTran.departClassName}" />
		        	</td>
		        	<td width="35" align="left" valign="middle" bgcolor="#f5deb3">
		           	<input name="score" id="${rcnt}" value="${ruleTran.score}" 
		           	type="text" size="5" maxlength="5" 
		          		onblur="if(this.value!='') checknum(this);">
		        	</td>
		        	<td width="35" align="left" valign="middle" bgcolor="#f5deb3">
		           	<input name="mscore" id="${rcnt}" value="${ruleTran.mscore}" 
		           	type="text" size="5" maxlength="5" 
		          		onblur="if(this.value!='') checknum(this);">
		        	</td>
				</c:if>
				<c:if test="${rcnt%2 != 0}">
		        	<td width="35" align="left" valign="middle" bgcolor="#add8e6">
		        		<c:out value="${ruleTran.departClass}" />
		        	</td>
		        	<td width="120" align="left" valign="middle" bgcolor="#add8e6">
		        		<c:out value="${ruleTran.departClassName}" />
		        	</td>
		        	<td width="15" align="left" valign="middle" bgcolor="#f5deb3">
		           	<input name="score" id="${rcnt}" value="${ruleTran.score}" 
		           	type="text" size="5" maxlength="5" 
		          		onblur="if(this.value!='') checknum(this);">
		        	</td>
		        	<td width="35" align="left" valign="middle" bgcolor="#f5deb3">
		           	<input name="mscore" id="${rcnt}" value="${ruleTran.mscore}" 
		           	type="text" size="5" maxlength="5" 
		          		onblur="if(this.value!='') checknum(this);">
		        	</td>
        			<td>&nbsp;</td>
        		</tr>
				</c:if>
        		<c:set var="rcnt" value="${rcnt+1}"/>
			</c:forEach>
         </c:when>
        </c:choose>
        </table>
       </td>
      </tr>
    
	<script>
	generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='OK'/>" >&nbsp;&nbsp;'+
						'<INPUT type="submit" name="method" value="<bean:message key='Cancel'/>" >');
	</script>
</table>
</form>
<script>history.go(1);</script>
<script language="javascript">
//<!--
function checknum(obj) {
	data = obj.value.match(/^\d{1}|^\d{2}|^\d{1}\.\d{1}|^\d{2}\.\d{1}|^100[\.0]/);
	if(isNaN(obj.value)) {
		alert("欄位資料須為數字!");
		obj.value = "0.0";
		obj.focus();
		return false;
	} else if(obj.value > 100) {
		alert("分數不可大於100分!");
		obj.value = "";
		obj.focus();
		return false;
	}
	return true;
};
//-->
</script>
