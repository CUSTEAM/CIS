<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/score/include/scoreinput.js" %>

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
<form action="/CIS/StudAffair/StudTimeOffInputBatch.do" method="post" name="inputForm">
<table width="100%" cellpadding="0" cellspacing="0">
<input type="hidden" name="mode" value="${StudTimeoffInitB.mode}">

<!-- Begin Content Page Table Header -->
	<c:if test="${StudTimeoffInitB.mode == 'ByClass'}">
    <script>generateTableBanner('<div class="gray_15"><B><bean:message key="stfTitle.StudTimeOffInputClass" bundle="SAF"/></B></div>');</script>
	</c:if>
	<c:if test="${StudTimeoffInitB.mode == 'BySubject'}">
    <script>generateTableBanner('<div class="gray_15"><B><bean:message key="stfTitle.StudTimeOffInputSubject" bundle="SAF"/></B></div>');</script>
	</c:if>
	
<!-- End Content Page Table Header -->
	<tr><td>

	<table width="100%" cellspacing="5" class="empty-border">
      <tr>
        <td  height="30" colspan="6" align="left" valign="middle">日期：
        <c:if test="${StudTimeoffInitB.tfYear != ''}">
        	<c:if test="${StudTimeoffInitB.mode == 'BySubject'}">
          	<input name="tfYear" type="text" size="3" maxlength="4" value="${StudTimeoffInitB.tfYear}" onChange="fillcourse();"/>年
          	</c:if>
			<c:if test="${StudTimeoffInitB.mode == 'ByClass'}">
          	<input name="tfYear" type="text" size="3" maxlength="4" value="${StudTimeoffInitB.tfYear}"/>年
          	</c:if>
        </c:if>
        <c:if test="${StudTimeoffInitB.tfYear == ''}">
        	<c:if test="${StudTimeoffInitB.mode == 'BySubject'}">
          	<input name="tfYear" type="text" size="3" maxlength="4" value="" onChange="fillcourse();"/>年
          	</c:if>
			<c:if test="${StudTimeoffInitB.mode == 'ByClass'}">
          	<input name="tfYear" type="text" size="3" maxlength="4" value=""/>年
        	</c:if>
         </c:if>
        <c:if test="${StudTimeoffInitB.tfMonth != ''}">
        	<c:if test="${StudTimeoffInitB.mode == 'BySubject'}">
          	<input name="tfMonth" type="text" size="2" maxlength="3" value="${StudTimeoffInitB.tfMonth}" onChange="fillcourse();"/>月
          	</c:if>
			<c:if test="${StudTimeoffInitB.mode == 'ByClass'}">
          	<input name="tfMonth" type="text" size="2" maxlength="3" value="${StudTimeoffInitB.tfMonth}"/>月
			</c:if>
        </c:if>
        <c:if test="${StudTimeoffInitB.tfMonth == ''}">
        	<c:if test="${StudTimeoffInitB.mode == 'BySubject'}">
          	<input name="tfMonth" type="text" size="2" maxlength="3" value="" onChange="fillcourse();"/>月
          	</c:if>
			<c:if test="${StudTimeoffInitB.mode == 'ByClass'}">
          	<input name="tfMonth" type="text" size="2" maxlength="3" value=""/>月
          	</c:if>
        </c:if>
        <c:if test="${StudTimeoffInitB.tfDay != ''}">
        	<c:if test="${StudTimeoffInitB.mode == 'BySubject'}">
          	<input name="tfDay" type="text" size="2" maxlength="3" value="${StudTimeoffInitB.tfDay}" onChange="fillcourse();"/>日
          	</c:if>
			<c:if test="${StudTimeoffInitB.mode == 'ByClass'}">
          	<input name="tfDay" type="text" size="2" maxlength="3" value="${StudTimeoffInitB.tfDay}" />日
			</c:if>
        </c:if>
        <c:if test="${StudTimeoffInitB.tfDay == ''}">
        	<c:if test="${StudTimeoffInitB.mode == 'BySubject'}">
          	<input name="tfDay" type="text" size="2" maxlength="3" value="" onChange="fillcourse();"/>日
          	</c:if>
			<c:if test="${StudTimeoffInitB.mode == 'ByClass'}">
          	<input name="tfDay" type="text" size="2" maxlength="3" value="" />日
        	</c:if>
        </c:if>
        <img src="spacer.gif" alt="spacer" width="20" height="6"/>
        </td>
      </tr>
      
      <tr>
        <td height="25" colspan="6" align="left" valign="bottom">
      	<font class="blue_13">請依序選擇校區、學制、科系、班級和科目</font>
      	</td>
      </tr>
      <tr><td>
      	班別：
	  		<c:set var="campusSel" value="${StudTimeoffInitB.campus}"/>
	  		<c:set var="schoolSel" value="${StudTimeoffInitB.school}"/>
	  		<c:set var="deptSel"   value="${StudTimeoffInitB.dept}"/>
	  		<c:set var="classSel"  value="${StudTimeoffInitB.clazz}"/>
			<c:if test="${StudTimeoffInitB.mode == 'ByClass'}">
				<%@ include file="/pages/studaffair/include/ClassSelectSAF.jsp" %>
			</c:if>
			<c:if test="${StudTimeoffInitB.mode == 'BySubject'}">
			<%@ include file="/pages/studaffair/include/ClassSelectSAF2.jsp" %>
			<img src="spacer.gif" alt="spacer" width="20" height="6"/>科目：
          	<select name="cscode">
          		<c:if test="${StudTimeoffInfoB != null }">
          		<c:forEach items="${StudTimeoffInfoB}" var="cscodes">
          			<c:if test="${StudTimeoffInitB.cscode==cscodes.cscode}">
          				<option name="cscode" value="${cscodes.cscode}" selected>${cscodes.chiName}</option>
          			</c:if>
          			<c:if test="${StudTimeoffInitB.cscode!=cscodes.cscode}">
          				<option name="cscode" value="${cscodes.cscode}">${cscodes.chiName}</option>
          			</c:if>
          		</c:forEach>
          		</c:if>
          		<c:if test="${StudTimeoffInfoB == null }">
          			<option value=""></option>
          		</c:if>
          	</select>
			</c:if>
        </td>
      </tr>
	</table>
		</td>		
	</tr>
	<script>generateTableBanner('<input type="submit" name="method" value="<bean:message key='StartInput' bundle='SAF'/>">&nbsp;&nbsp;'
							  + '<input type="reset" name="method" value="<bean:message key='Reset' bundle='SAF'/>">');</script>

	<!-- Test if have Query Result  -->
	<c:set var="tcnt" value="1"/>
	<c:if test="${(StudTimeOffInEditB != null  or StudTimeOffFormMapB != null) and ! empty StudTimeOffInEditB}" >
	<tr>
		<td>
			<table width="100%" border="1" cellpadding="2" cellspacing="1" bgcolor="#FFFFCC" id="tb1">
      		<!--DWLayoutTable-->
      			<tr>
      			<!-- 1:升旗 ,  -->
      			<td colspan="17">2:曠課 , 3:病假 , 4:事假 , 5:遲到早退 , 6:公假 , 7:喪假 , 8:婚假 , 9:產假 , 1:重大傷病住院<br>
      			狀態： w:退選, n:扣考, e:異常, x:禁止輸入
      			</td>
      			</tr>
      			<tr>
      			<td colspan="17">
      			<font class="blue_13">班級：
      				<c:out value="${StudTimeoffInitB.clazz}"/>&nbsp;&nbsp;
      				<c:out value="${StudTimeoffInitB.depClassName}"/>&nbsp;&nbsp;
      				<c:if test="${StudTimeoffInitB.mode == 'BySubject'}">
      					科目：
      					<c:out value="${StudTimeoffInitB.cscode}"/>&nbsp;&nbsp;
      					<c:out value="${StudTimeoffInitB.cscodeName}"/>&nbsp;&nbsp;
      				</c:if>
					<c:if test="${StudTimeOffFormMapB != null}">&nbsp;&nbsp;表格重填
					</c:if>
				</font>
      			</td>
      			</tr>
      			<tr>
      				<td>日期</td>
      				<td colspan="16">
      					<c:out value="${StudTimeoffInitB.tfYear} 年 ${StudTimeoffInitB.tfMonth} 月 ${StudTimeoffInitB.tfDay} 日"/>
						&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
				<tr>  
		  		<c:choose>
		  		<c:when test="${StudTimeoffInitB.daynite == '1'}">
		        	<td>節次</td>
	 	        	<td>升旗</td>
		        	<td>１</td>
		        	<td>２</td>
		        	<td>３</td>
		        	<td>４</td>
		        	<td>５</td>
		        	<td>６</td>
		        	<td>７</td>
		        	<td>８</td>
		        	<td>９</td>
		        	<td>10</td>
		        	<td>11</td>
		        	<td>12</td>
		        	<td>13</td>
		        	<td>14</td>
		        	<td>15</td>
		        </c:when>
		  		<c:when test="${StudTimeoffInitB.daynite == '2'}">
		        	<td>節次</td>
	 	        	<td>升旗</td>
		        	<td>１</td>
		        	<td>２</td>
		        	<td>３</td>
		        	<td>４</td>
		        	<td>５</td>
		        	<td>６</td>
		        	<td>７</td>
		        	<td>８</td>
		        	<td>９</td>
		        	<td>10</td>
		        	<td>N1</td>
		        	<td>N2</td>
		        	<td>N3</td>
		        	<td>N4</td>
		        	<td>N5</td>
		        </c:when>
		  		<c:when test="${StudTimeoffInitB.daynite == '3'}">
		        	<td>節次</td>
	 	        	<td>升旗</td>
		        	<td>１</td>
		        	<td>２</td>
		        	<td>３</td>
		        	<td>４</td>
		        	<td>５</td>
		        	<td>６</td>
		        	<td>７</td>
		        	<td>８</td>
		        	<td>９</td>
		        	<td>10</td>
		        	<td>11</td>
		        	<td>12</td>
		        	<td>13</td>
		        	<td>14</td>
		        	<td>15</td>
		        </c:when>
		        <c:otherwise>
		        </c:otherwise>
		        </c:choose>
	 	      
	 	      </tr>	      
		  <!-- <script language="JavaScript">generateScoreInput(scrinput);</script> -->
		  <c:set var="count" scope="page" value="0"/>
		  <c:set var="rcount" scope="page" value="0"/>
		  <c:if test="${StudTimeOffSubject != null}">
		  <tr>
		  	<td width="120">科目名稱</td>
		  	<td width="30">&nbsp;</td>
		  	
		  <c:set var="begin" value="1"/>
		  <c:forEach items="${StudTimeOffSubjInUse}" var="subject">
		  	<c:if test="${subject.begin != begin}">
		  		<c:forEach begin="${begin}" end="${subject.begin - 1}">
		  			<td width="30" align="left" valign="top">&nbsp;</td>
		  		</c:forEach>
		  	</c:if>
		  	<c:forEach begin="${subject.begin}" end="${subject.end}">
		  		<td width="30" align="left" valign="top">${subject.cscodeName}</td>
		  	</c:forEach>
		  	<c:set var="begin" value="${subject.end + 1}"/>
		  </c:forEach>
		  <c:if test="${begin < 16}">
		  	<c:forEach begin="${begin}" end="15">
		  		<td width="30" align="left" valign="top">&nbsp;</td>
		  	</c:forEach>
		  </c:if>
		  </tr>
		  </c:if>
	<c:choose>
	<c:when test="${StudTimeOffInEditB != null}">
	
          <c:forEach items="${StudTimeOffInEditB}" var="cbList">
          	<c:set var="courseName" scope="page" value="${cbList.cscodeName}"/>
          	<c:if test="${courseName != '' && ! empty cbList.classBook}">
          		<tr>
          			<td colspan="17" bgcolor="#7FFFD4" align="left">${courseName}</td>
          		</tr>
          	</c:if>
          	<c:set var="rcnt" value="0"/>
          	
          	<c:forEach items="${cbList.classBook}" var="classBook">
          		<c:set var="rcnt" value="${rcnt + 1}"/>
          		<c:if test="${rcnt==11}">
          		 <!-- 產生一行節次 -->
          			<c:set var="rcnt" value="1"/>
          			<tr bgcolor="#A9A9A9">
	          		<c:choose>
			  		<c:when test="${StudTimeoffInitB.daynite == '1'}">
			        	<td>節次</td>
		 	        	<td>升旗</td>
			        	<td>１</td>
			        	<td>２</td>
			        	<td>３</td>
			        	<td>４</td>
			        	<td>５</td>
			        	<td>６</td>
			        	<td>７</td>
			        	<td>８</td>
			        	<td>９</td>
			        	<td>10</td>
			        	<td>11</td>
			        	<td>12</td>
			        	<td>13</td>
			        	<td>14</td>
			        	<td>15</td>
			        </c:when>
			  		<c:when test="${StudTimeoffInitB.daynite == '2'}">
			        	<td>節次</td>
		 	        	<td>升旗</td>
			        	<td>１</td>
			        	<td>２</td>
			        	<td>３</td>
			        	<td>４</td>
			        	<td>５</td>
			        	<td>６</td>
			        	<td>７</td>
			        	<td>８</td>
			        	<td>９</td>
			        	<td>10</td>
			        	<td>N1</td>
			        	<td>N2</td>
			        	<td>N3</td>
			        	<td>N4</td>
			        	<td>N5</td>
			        </c:when>
			  		<c:when test="${StudTimeoffInitB.daynite == '3'}">
			        	<td>節次</td>
		 	        	<td>升旗</td>
			        	<td>１</td>
			        	<td>２</td>
			        	<td>３</td>
			        	<td>４</td>
			        	<td>５</td>
			        	<td>６</td>
			        	<td>７</td>
			        	<td>８</td>
			        	<td>９</td>
			        	<td>10</td>
			        	<td>11</td>
			        	<td>12</td>
			        	<td>13</td>
			        	<td>14</td>
			        	<td>15</td>
			        </c:when>
			        </c:choose>
	          	</tr>
	          	</c:if>
          		<tr>
          		<td>${classBook.studentNo}${classBook.studentName}</td>
	          		<c:choose>
	          			<c:when test="${classBook.status0 == 'x' || 
	          				classBook.status0 == 'e' || classBook.status0 == 'w' || classBook.status0 == 'n'}">
	          				<td align="center">
	          				<input type="hidden" name="st0" value="${classBook.status0}">
	          				${classBook.status0}
	          				</td>
	          			</c:when>
	          			<c:otherwise>
	          				<td align="center">
	          				<input type="text" name="st0" size="1" maxlength="1" value="${classBook.status0}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
          					<c:set var="tcnt" value="${tcnt + 1}"/>
	          				</td>
	          			</c:otherwise>
	          		</c:choose>
	          		<c:choose>
	          			<c:when test="${classBook.status1 == 'x' || 
	          				classBook.status1 == 'e' || classBook.status1 == 'w' || classBook.status1 == 'n'}">
	          				<td align="center"><input type="hidden" name="st1" value="${classBook.status1}">${classBook.status1}</td>
	          			</c:when>
	          			<c:otherwise>
	          				<td align="center">
	          				<input type="text" name="st1" size="1" maxlength="1" value="${classBook.status1}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
          					<c:set var="tcnt" value="${tcnt + 1}"/>
	          				</td>
	          			</c:otherwise>
	          		</c:choose>
	          		<c:choose>
	          			<c:when test="${classBook.status2 == 'x' || 
	          				classBook.status2 == 'e' || classBook.status2 == 'w' || classBook.status2 == 'n'}">
	          				<td align="center"><input type="hidden" name="st2" value="${classBook.status2}">${classBook.status2}</td>
	          			</c:when>
	          			<c:otherwise>
	          				<td align="center">
	          				<input type="text" name="st2" size="1" maxlength="1" value="${classBook.status2}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
          					<c:set var="tcnt" value="${tcnt + 1}"/>
	          				</td>
	          			</c:otherwise>
	          		</c:choose>
	          		<c:choose>
	          			<c:when test="${classBook.status3 == 'x' || 
	          				classBook.status3 == 'e' || classBook.status3 == 'w' || classBook.status3 == 'n'}">
	          				<td align="center"><input type="hidden" name="st3" value="${classBook.status3}">${classBook.status3}</td>
	          			</c:when>
	          			<c:otherwise>
	          				<td align="center">
	          				<input type="text" name="st3" size="1" maxlength="1" value="${classBook.status3}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
          					<c:set var="tcnt" value="${tcnt + 1}"/>
	          				</td>
	          			</c:otherwise>
	          		</c:choose>
	          		<c:choose>
	          			<c:when test="${classBook.status4 == 'x' || 
	          				classBook.status4 == 'e' || classBook.status4 == 'w' || classBook.status4 == 'n'}">
	          				<td align="center"><input type="hidden" name="st4" value="${classBook.status4}">${classBook.status4}</td>
	          			</c:when>
	          			<c:otherwise>
	          				<td align="center">
	          				<input type="text" name="st4" size="1" maxlength="1" value="${classBook.status4}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
          					<c:set var="tcnt" value="${tcnt + 1}"/>
	          				</td>
	          			</c:otherwise>
	          		</c:choose>
	          		<c:choose>
	          			<c:when test="${classBook.status5 == 'x' || 
	          				classBook.status5 == 'e' || classBook.status5 == 'w' || classBook.status5 == 'n'}">
	          				<td align="center"><input type="hidden" name="st5" value="${classBook.status5}">${classBook.status5}</td>
	          			</c:when>
	          			<c:otherwise>
	          				<td align="center">
	          				<input type="text" name="st5" size="1" maxlength="1" value="${classBook.status5}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
          					<c:set var="tcnt" value="${tcnt + 1}"/>
	          				</td>
	          			</c:otherwise>
	          		</c:choose>
	          		<c:choose>
	          			<c:when test="${classBook.status6 == 'x' || 
	          				classBook.status6 == 'e' || classBook.status6 == 'w' || classBook.status6 == 'n'}">
	          				<td align="center"><input type="hidden" name="st6" value="${classBook.status6}">${classBook.status6}</td>
	          			</c:when>
	          			<c:otherwise>
	          				<td align="center">
	          				<input type="text" name="st6" size="1" maxlength="1" value="${classBook.status6}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
          					<c:set var="tcnt" value="${tcnt + 1}"/>
	          				</td>
	          			</c:otherwise>
	          		</c:choose>
	          		<c:choose>
	          			<c:when test="${classBook.status7 == 'x' || 
	          				classBook.status7 == 'e' || classBook.status7 == 'w' || classBook.status7 == 'n'}">
	          				<td align="center"><input type="hidden" name="st7" value="${classBook.status7}">${classBook.status7}</td>
	          			</c:when>
	          			<c:otherwise>
	          				<td align="center">
	          				<input type="text" name="st7" size="1" maxlength="1" value="${classBook.status7}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
          					<c:set var="tcnt" value="${tcnt + 1}"/>
	          				</td>
	          			</c:otherwise>
	          		</c:choose>
	          		<c:choose>
	          			<c:when test="${classBook.status8 == 'x' || 
	          				classBook.status8 == 'e' || classBook.status8 == 'w' || classBook.status8 == 'n'}">
	          				<td align="center"><input type="hidden" name="st8" value="${classBook.status8}">${classBook.status8}</td>
	          			</c:when>
	          			<c:otherwise>
	          				<td align="center">
	          				<input type="text" name="st8" size="1" maxlength="1" value="${classBook.status8}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
          					<c:set var="tcnt" value="${tcnt + 1}"/>
	          				</td>
	          			</c:otherwise>
	          		</c:choose>
	          		<c:choose>
	          			<c:when test="${classBook.status9 == 'x' || 
	          				classBook.status9 == 'e' || classBook.status9 == 'w' || classBook.status9 == 'n'}">
	          				<td align="center"><input type="hidden" name="st9" value="${classBook.status9}">${classBook.status9}</td>
	          			</c:when>
	          			<c:otherwise>
	          				<td align="center">
	          				<input type="text" name="st9" size="1" maxlength="1" value="${classBook.status9}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
          					<c:set var="tcnt" value="${tcnt + 1}"/>
	          				</td>
	          			</c:otherwise>
	          		</c:choose>
	          		<c:choose>
	          			<c:when test="${classBook.status10 == 'x' || 
	          				classBook.status10 == 'e' || classBook.status10 == 'w' || classBook.status10 == 'n'}">
	          				<td align="center"><input type="hidden" name="st10" value="${classBook.status10}">${classBook.status10}</td>
	          			</c:when>
	          			<c:otherwise>
	          				<td align="center">
	          				<input type="text" name="st10" size="1" maxlength="1" value="${classBook.status10}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
          					<c:set var="tcnt" value="${tcnt + 1}"/>
	          				</td>
	          			</c:otherwise>
	          		</c:choose>
	          		<c:choose>
	          			<c:when test="${classBook.status11 == 'x' || 
	          				classBook.status11 == 'e' || classBook.status11 == 'w' || classBook.status11 == 'n'}">
	          				<td align="center"><input type="hidden" name="st11" value="${classBook.status11}">${classBook.status11}</td>
	          			</c:when>
	          			<c:otherwise>
	          				<td align="center">
	          				<input type="text" name="st11" size="1" maxlength="1" value="${classBook.status11}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
          					<c:set var="tcnt" value="${tcnt + 1}"/>
	          				</td>
	          			</c:otherwise>
	          		</c:choose>
	          		<c:choose>
	          			<c:when test="${classBook.status12 == 'x' || 
	          				classBook.status12 == 'e' || classBook.status12 == 'w' || classBook.status12 == 'n'}">
	          				<td align="center"><input type="hidden" name="st12" value="${classBook.status12}">${classBook.status12}</td>
	          			</c:when>
	          			<c:otherwise>
	          				<td align="center">
	          				<input type="text" name="st12" size="1" maxlength="1" value="${classBook.status12}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
          					<c:set var="tcnt" value="${tcnt + 1}"/>
	          				</td>
	          			</c:otherwise>
	          		</c:choose>
	          		<c:choose>
	          			<c:when test="${classBook.status13 == 'x' || 
	          				classBook.status13 == 'e' || classBook.status13 == 'w' || classBook.status13 == 'n'}">
	          				<td align="center"><input type="hidden" name="st13" value="${classBook.status13}">${classBook.status13}</td>
	          			</c:when>
	          			<c:otherwise>
	          				<td align="center">
	          				<input type="text" name="st13" size="1" maxlength="1" value="${classBook.status13}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
          					<c:set var="tcnt" value="${tcnt + 1}"/>
	          				</td>
	          			</c:otherwise>
	          		</c:choose>
	          		<c:choose>
	          			<c:when test="${classBook.status14 == 'x' || 
	          				classBook.status14 == 'e' || classBook.status14 == 'w' || classBook.status14 == 'n'}">
	          				<td align="center"><input type="hidden" name="st14" value="${classBook.status14}">${classBook.status14}</td>
	          			</c:when>
	          			<c:otherwise>
	          				<td align="center">
	          				<input type="text" name="st14" size="1" maxlength="1" value="${classBook.status14}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
          					<c:set var="tcnt" value="${tcnt + 1}"/>
	          				</td>
	          			</c:otherwise>
	          		</c:choose>
	          		<c:choose>
	          			<c:when test="${classBook.status15 == 'x' || 
	          				classBook.status15 == 'e' || classBook.status15 == 'w' || classBook.status15 == 'n'}">
	          				<td align="center"><input type="hidden" name="st15" value="${classBook.status15}">${classBook.status15}</td>
	          			</c:when>
	          			<c:otherwise>
	          				<td align="center">
	          				<input type="text" name="st15" size="1" maxlength="1" value="${classBook.status15}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
          					<c:set var="tcnt" value="${tcnt + 1}"/>
	          				</td>
	          			</c:otherwise>
	          		</c:choose>
          		
          		</tr>
          	</c:forEach>
          </c:forEach>
	</c:when>
	</c:choose>

          </table>
	   </td>
	</tr>
	
<script language="javascript">
//<!--
		var iplimit = ${tcnt-1};
		
		function nextfocus(ntab) {
		var code = event.keyCode;
		var iptx1s = document.getElementById("1");
		if(code==46 || code==8) {
			return;
		}
		if(code==37 || code==38) {
			if(ntab==2) return;
			ntab = ntab -2;
		} else if(code==40) {
		
		}
		if(ntab > iplimit) return;
		var nextElem = document.getElementById(ntab);
		if(code==37 || code==38) {
			nextElem.value="";
		}
		nextElem.focus();
	};
//-->
</script>

	<script>
	generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='OK'/>" >&nbsp;&nbsp;'+
	'<INPUT type="submit" name="method" value="<bean:message key='Cancel'/>" >');
	</script>
	</c:if>
</table>
</form>
