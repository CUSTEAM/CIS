<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Course/CheckOut" method="post">
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/folder_bug.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">課務查核</font></div>		
		</td>
	</tr>
	<tr>
		<td>
		<%@ include file="CheckOut/search.jsp"%>		
		</td>
	</tr>
	
	
	<!-- 學生選課查核 -->
	<c:if test="${CheckOpt=='CheckThour'}">	
	<%@ include file="CheckOut/option/CheckThour.jsp"%>	
	</c:if>
	
	
	<!-- 學生選課查核 -->
	<c:if test="${CheckOpt=='CheckSeld'}">	
	<%@ include file="CheckOut/option/CheckSeld.jsp"%>	
	</c:if>


	<!-- 課程大綱查核 -->
	<c:if test="${CheckOpt=='CheckGist'}">
	<%@ include file="CheckOut/option/CheckGist.jsp"%>
	</c:if>

	<!-- 學分數查核 -->
	<c:if test="${CheckOpt=='CheckCredit'}">
	<%@ include file="CheckOut/option/CheckCredit.jsp"%>
	</c:if>
	
	<!-- 學程列表 -->	
	<c:if test="${CheckOpt=='CsGroupDoc'}">
	<%@ include file="CheckOut/option/CsGroupDoc.jsp"%>
	</c:if>
	
	<!-- 歷史重複修課 -->	
	<c:if test="${CheckOpt=='ReSelected'}">
	<%@ include file="CheckOut/option/ReSelected.jsp"%>
	</c:if>
	
	<!-- 本學期重複修課 -->	
	<c:if test="${CheckOpt=='ReSelectedNow'}">
	<%@ include file="CheckOut/option/ReSelectedNow.jsp"%>
	</c:if>
	
	<!-- 班級學生選課清單 -->	
	<c:if test="${CheckOpt=='ListSeld4Class'}">
	<%@ include file="CheckOut/option/ListSeld4Class.jsp"%>
	</c:if>

<!--人數上限查核-->

<c:if test="${CheckOpt=='CheckSelimit'}">
<tr>
	<td>
	<c:if test="${CheckSelimitList!=null }">
	<display:table name="${CheckSelimitList}" export="true" pagesize="10" sort="list" excludedParams="*" class="list">
			<display:column title="系所" property="name" sortable="true" class="left" />
			<display:column title="開課班級" property="ClassName" sortable="true" class="left" />
			<display:column title="班級代碼" property="ClassNo" sortable="true" class="center" />
			<display:column title="科目名稱" property="chi_name" sortable="true" class="left" />
			<display:column title="科目代碼" property="cscode" sortable="true" class="center" />
			<display:column title="教師姓名" property="cname" sortable="true" class="left" />
			<display:column title="學分數" property="credit" sortable="true" class="left" />
			<display:column title="人數上限" property="Select_Limit" sortable="true" class="center" />
			<display:column title="已選人數" property="count_now" sortable="true" class="center" />
		</display:table>
	<tr height="40">
		<td align="center">

		<table width="98%" class="hairLineTable">
  				<tr>
    				<td class="hairLineTdF">
    					<table>
    						<tr>
    							<td>
    							 選擇輸出格式:
    							</td>
    							<td>
    							<a href="course/export/list4CheckSel.jsp?type=excel">
    							<img src="images/ico_file_excel.png" border="0"> Excel
    							</a>
    							<a href="course/export/list4CheckSel.jsp?type=word">
    							<img src="images/ico_file_word.png" border="0"> Word
    							</a>
    							</td>
    						</tr>
    					</table>
    				</td>
  				</tr>
			</table>

		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable">
	
		</td>
	</tr>
</c:if>
</c:if>


















<!-- 不及格 -->	
<c:if test="${CheckOpt=='tripos'}">

<tr height="40">
		<td>
	<table width="98%" align="center" class="hairLineTable">
  				<tr>
    				<td class="hairLineTdF">
    					<a href="course/export/list4tripos.jsp?type=excel">輸出表格</a>
    				</td>
  				</tr>
			</table>
			</td>
		</tr>


<tr>
	<td>
	
	
	
	<display:table name="${triposList}" export="true" sort="list" excludedParams="*" class="list">
			<display:column title="開課班級" property="ClassName" sortable="true" class="left" />
			<display:column title="科目代碼" property="cscode" sortable="true" class="left" />
			<display:column title="科目名稱" property="chi_name" sortable="true" class="left" />
			<display:column title="教師姓名" property="cname" sortable="true" class="center" />
			
			<display:column title="選課人數" property="stuSelect" sortable="true" class="right" />
			<display:column title="期中未評" property="midNon" sortable="true" class="right" />
			<display:column title="不及格" property="midCount" sortable="true" class="right" />
			<display:column title="比例" property="midavg" sortable="true" class="right" />
			<display:column title="期末未評" property="endNon" sortable="true" class="right" />
			<display:column title="不及格" property="endCount" sortable="true" class="right" />
			<display:column title="比例" property="endavg" sortable="true" class="right" />
			<display:column title="學期未評" property="scoreNon" sortable="true" class="right" />
			<display:column title="不及格" property="scoreCount" sortable="true" class="right" />
			<display:column title="比例" property="scoreavg" sortable="true" class="right" />
		</display:table>
	
	<td>
</tr>
<tr height="40">
	<td>
	<table width="98%" align="center" class="hairLineTable">
  				<tr>
    				<td class="hairLineTdF">
    					<table>
    						<tr>
    							<td>
    							 選擇輸出格式:
    							</td>
    							<td>
    							<a href="course/export/list4tripos.jsp?type=excel">
    							<img src="images/ico_file_excel.png" border="0"> Excel
    							</a>
    							<a href="course/export/list4tripos.jsp?type=word">
    							<img src="images/ico_file_word.png" border="0"> Word
    							</a>
    							</td>
    						</tr>
    					</table>
    				</td>
  				</tr>
			</table>
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable">
	
		</td>
	</tr>
</c:if>	








<!-- 低修高 -->	
<c:if test="${CheckOpt=='CheckHeight'}">

<tr>
		
	<td>
	
	<display:table name="${CheckHeight}" export="true" pagesize="10" sort="list" excludedParams="*" class="list">
			<display:column title="開課班級" property="csClass" sortable="true" class="left" />
			<display:column title="程程代碼" property="cscode" sortable="true" class="center" />
			<display:column title="科程代碼" property="chi_name" sortable="true" class="left" />
			<display:column title="學生班級" property="stClass" sortable="true" class="center" />
			<display:column title="學號" property="student_no" sortable="true" class="left" />
			<display:column title="姓名" property="student_name" sortable="true" class="left" />
		</display:table>
	
	<td>
</tr>
<tr height="40">
	<td>
	<table width="98%" align="center" class="hairLineTable">
  				<tr>
    				<td class="hairLineTdF">
    					<table>
    						<tr>
    							<td>
    							 選擇輸出格式:
    							</td>
    							<td>
    							<a href="course/export/list4tripos.jsp?type=excel">
    							<img src="images/ico_file_excel.png" border="0"> Excel
    							</a>
    							<a href="course/export/list4tripos.jsp?type=word">
    							<img src="images/ico_file_word.png" border="0"> Word
    							</a>
    							</td>
    						</tr>
    					</table>
    				</td>
  				</tr>
			</table>
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable">
	
		</td>
	</tr>
</c:if>







<!-- 學分費 -->	
<c:if test="${CheckOpt=='CheckPay'}">

<tr>
		
	<td>
	<display:table name="${allSelds}" export="true" pagesize="10" sort="list" excludedParams="*" class="list">
		<display:column title="學號" property="student_no" sortable="true" class="left" />
		<display:column title="姓名" property="student_name" sortable="true" class="left" />
		<display:column title="班級代碼" property="ClassNo" sortable="true" class="left" />
		<display:column title="班級名稱" property="ClassName" sortable="true" class="left" />
		<display:column title="時數" property="sumThour" sortable="true" class="left" />
		<display:column title="學分數" property="sumCredit" sortable="true" class="left" />
		<display:column title="電腦實習" property="Extrapay_Kind" sortable="true" class="left" />
	</display:table>
	<td>
</tr>
<tr height="40">
	<td>
	<table width="98%" align="center" class="hairLineTable">
  				<tr>
    				<td class="hairLineTdF">
    					<table>
    						<tr>
    							<!--
    							<td width="1">
    							<a href="/CIS/List4Adcd">
    							<img src="images/ico_file_excel.png" border="0">
    							</a>
    							</td>
    							<td>
    							<a href="/CIS/List4Adcd">加退選總表</a>
    							</td>    							
    							
    							<td width="1">
    							<a href="/CIS/ReceiptList4Student">
    							<img src="images/ico_file_word.png" border="0">
    							</a>
    							</td>
    							<td>
    							<a href="/CIS/ReceiptList4Student">補繳單</a>
    							</td>
    							-->
    							<td width="1">
    							<a href="/CIS/Receipt4Student">
    							<img src="images/ico_file_word.png" border="0">
    							</a>
    							</td>
    							<td>
    							<a href="/CIS/Receipt4Student">補繳收據</a>
    							</td>
    							
    							<td width="1">
    							<a href="/CIS/PayBack4Student?type=">
    							<img src="images/ico_file_excel.png" border="0">
    							</a>
    							</td>
    							<td>
    							<a href="/CIS/PayBack4Student?type=">收費明細</a>
    							</td>
    							
    							<td width="1">
    							<a href="/CIS/PayBack4Student?type=back">
    							<img src="images/ico_file_excel.png" border="0">
    							</a>
    							</td>
    							<td>
    							<a href="/CIS/PayBack4Student?type=back">退費明細</a>
    							</td>
    							
    						</tr>
    					</table>
    				</td>
  				</tr>
			</table>
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable">
	
		</td>
	</tr>
</c:if>	




<!-- 期中考不及格者輔導紀錄-->	
<c:if test="${CheckOpt=='ListCounseling'}">

<tr>
		
	<td>
	
	<display:table name="${Counseling}" export="true" pagesize="10" sort="list" excludedParams="*" class="list">
		<display:column title="班級" property="ClassName" sortable="true" class="left" />
		<display:column title="學號" property="student_no" sortable="true" class="left" />
		<display:column title="姓名" property="student_name" sortable="true" class="left" />
		<display:column title="科目名稱" property="chi_name" sortable="true" class="left" />
		<display:column title="期中考成績" property="score2" sortable="true" class="left" />
		<display:column title="輔導任課老師" property="cname" sortable="true" class="left" />
		<display:column title="期未考成績" property="score3" sortable="true" class="left" />
		<display:column title="輔導次數" property="sum" sortable="true" class="left" />
		<display:column title="輔導紀錄" property="content" sortable="true" class="left" />
	</display:table>
	
	<td>
</tr>
<tr height="40">
	<td>
	<table width="98%" class="hairLineTable">
  				<tr>
    				<td class="hairLineTdF">
    					<table>
    						<tr>
    							<td>
    							 選擇輸出格式:
    							</td>
    							<td>
    							<a href="course/export/list4Counseling.jsp?type=excel">
    							<img src="images/ico_file_excel.png" border="0"> Excel
    							</a>
    							<a href="course/export/list4Counseling.jsp?type=word">
    							<img src="images/ico_file_word.png" border="0"> Word
    							</a>
    							</td>
    						</tr>
    					</table>
    				</td>
  				</tr>
			</table>
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable">
	
		</td>
	</tr>
</c:if>	



<!-- 班級開課衝堂 -->	
<c:if test="${CheckOpt=='CheckClass'}">

<tr>
		
	<td>
	
	<display:table name="${CheckClass}" export="true" pagesize="10" sort="list" excludedParams="*" class="list">
		<display:column title="開課班級" property="ClassName" sortable="true" class="left" />
		<display:column title="課程名稱" property="chi_name" sortable="true" class="left" />
		<display:column title="星期" property="week" sortable="true" class="left" />
		<display:column title="開始節次" property="begin" sortable="true" class="left" />
		<display:column title="結束節次" property="end" sortable="true" class="left" />
	</display:table>
	
	<td>
</tr>
<tr height="40">
	<td>
	<table width="98%" class="hairLineTable">
  				<tr>
    				<td class="hairLineTdF">
    					<table>
    						<tr>
    							<td>
    							 選擇輸出格式:
    							</td>
    							<td>
    							<a href="course/export/list4CheckClass.jsp?type=excel">
    							<img src="images/ico_file_excel.png" border="0"> Excel
    							</a>
    							<a href="course/export/list4CheckClass.jsp?type=word">
    							<img src="images/ico_file_word.png" border="0"> Word
    							</a>
    							</td>
    						</tr>
    					</table>
    				</td>
  				</tr>
			</table>
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable">
	
		</td>
	</tr>
</c:if>	

<!-- 教室開課衝堂 -->	
<c:if test="${CheckOpt=='CheckRoom'}">

<tr>
		
	<td>
	
	<display:table name="${CheckRoom}" export="true" pagesize="10" sort="list" excludedParams="*" class="list">
		<display:column title="班級名稱" property="className" sortable="true" class="left" />
		<display:column title="班級代碼" property="depart_class" sortable="true" class="left" />
		<display:column title="教室代碼" property="place" sortable="true" class="left" />
		<display:column title="課程名稱" property="chi_name" sortable="true" class="left" />
		<display:column title="星期" property="week" sortable="true" class="left" />
		<display:column title="開始節次" property="begin" sortable="true" class="left" />
		<display:column title="結束結次" property="end" sortable="true" class="left" />
	</display:table>
	
	<td>
</tr>
<tr height="40">
	<td>
	<table width="98%" align="center" class="hairLineTable">
  				<tr>
    				<td class="hairLineTdF">
    					<table>
    						<tr>
    							<td>
    							 選擇輸出格式:
    							</td>
    							<td>
    							<a href="course/export/list4CheckRoom.jsp?type=excel">
    							<img src="images/ico_file_excel.png" border="0"> Excel
    							</a>
    							<a href="course/export/list4CheckRoom.jsp?type=word">
    							<img src="images/ico_file_word.png" border="0"> Word
    							</a>
    							</td>
    						</tr>
    					</table>
    				</td>
  				</tr>
			</table>
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable">
	
		</td>
	</tr>
</c:if>	




<!-- 教師開課衝堂 -->	
<c:if test="${CheckOpt=='CheckTech'}">

<tr>
		
	<td>
	
	<display:table name="${CheckTech}" export="true" pagesize="10" sort="list" excludedParams="*" class="list">
		<display:column title="授課教師" property="cname" sortable="true" class="left" />
		<display:column title="開課班級" property="depart_class" sortable="true" class="left" />
		<display:column title="開課班級" property="ClassName" sortable="true" class="left" />
		<display:column title="課程名稱" property="chi_name" sortable="true" class="left" />
		<display:column title="星期" property="week" sortable="true" class="left" />
		<display:column title="開始節次" property="begin" sortable="true" class="left" />
		<display:column title="結束節次" property="end" sortable="true" class="left" />
	</display:table>
	
	<td>
</tr>
<tr height="40">
	<td>
	<table width="98%" class="hairLineTable">
  				<tr>
    				<td class="hairLineTdF">
    					<table>
    						<tr>
    							<td>
    							 選擇輸出格式:
    							</td>
    							<td>
    							<a href="course/export/list4CheckTech.jsp?type=excel">
    							<img src="images/ico_file_excel.png" border="0"> Excel
    							</a>
    							<a href="course/export/list4CheckTech.jsp?type=word">
    							<img src="images/ico_file_word.png" border="0"> Word
    							</a>
    							</td>
    						</tr>
    					</table>
    				</td>
  				</tr>
			</table>
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable">
	
		</td>
	</tr>
</c:if>

<!-- 通識未選 -->	
<c:if test="${CheckOpt=='CheckGeneral'}">

<tr>
		
	<td>
	
	<display:table name="${noGeneral}" sort="list" excludedParams="*" class="list">	
		<display:column title="班級" property="ClassName" sortable="true" class="left" />
		<display:column title="學號" property="student_no" sortable="true" class="left" />
		<display:column title="姓名" property="student_name" sortable="true" class="left" />
		<display:column title="原因" property="cause" sortable="true" class="left" />
	</display:table>
	
	<td>
</tr>
<tr height="40">
	<td>
	<table width="98%" class="hairLineTable">
  				<tr>
    				<td class="hairLineTdF">
    					<table>
    						<tr>
    							<td>
    							<img src="images/icon/printer_cancel.gif"/>&nbsp;&nbsp;
    							</td>
    						</tr>
    					</table>
    				</td>
  				</tr>
			</table>
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable">
	
		</td>
	</tr>
</c:if>	

<!-- 體育未選 -->	
<c:if test="${CheckOpt=='CheckSport'}">

<tr>
		
	<td>
	
	<display:table name="${noSport}" sort="list" excludedParams="*" class="list">	
		<display:column title="班級" property="ClassName" sortable="true" class="left" />
		<display:column title="學號" property="student_no" sortable="true" class="left" />
		<display:column title="姓名" property="student_name" sortable="true" class="left" />
		<display:column title="原因" property="cause" sortable="true" class="left" />
	</display:table>
	
	<td>
</tr>
<tr height="40">
	<td>
	<table width="98%" class="hairLineTable">
  				<tr>
    				<td class="hairLineTdF">
    					<table>
    						<tr>
    							<td>
    							<img src="images/icon/printer_cancel.gif"/>
    							</td>
    						</tr>
    					</table>
    				</td>
  				</tr>
			</table>
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable">
	
		</td>
	</tr>
</c:if>	

<!-- 無名選課 -->	
<c:if test="${CheckOpt=='CheckNoname'}">

<tr>
		
	<td>
	
	<display:table name="${noName}" sort="list" excludedParams="*" class="list">	
		<display:column title="學號" property="student_no" sortable="true" class="left" />
		<display:column title="課程代碼" property="cscode" sortable="true" class="left" />
		<display:column title="課程名稱" property="chi_name" sortable="true" class="left" />
	</display:table>
	
	<td>
</tr>
<tr height="40">
	<td>
	<table width="98%" class="hairLineTable">
  				<tr>
    				<td class="hairLineTdF">
    					<table>
    						<tr>
    							<td>
    							<img src="images/icon/printer_cancel.gif"/>&nbsp;&nbsp;
    							</td>
    						</tr>
    					</table>
    				</td>
  				</tr>
			</table>
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable">
	
		</td>
	</tr>
</c:if>	

<!-- 暑修開課建議 -->	
<c:if test="${CheckOpt=='SummerOpen'}">

<tr>
		
	<td>
	
	<display:table name="${fail}" export="true" sort="list" excludedParams="*" class="list">
	
		<display:column title="課程代碼" property="cscode" sortable="true" class="left" />
		<display:column title="課程名稱" property="chi_name" sortable="true" class="left" />
		<display:column title="科系" property="DeptNo" sortable="true" class="left" />
		
		<display:column title="選別" property="opt" sortable="true" class="left" />
		<display:column title="時數" property="thour" sortable="true" class="left" />
		<display:column title="學分" property="credit" sortable="true" class="left" />
		<display:column title="被當人數" property="sumSt" sortable="true" class="center" />
	</display:table>
	
	<td>
</tr>

<!-- tr>
		
	<td>
	
	<display:table name="${retry}" export="true" pagesize="10" sort="list" excludedParams="*" class="list">
		<display:column title="課程代碼" property="cscode" sortable="true" class="left" />
		<display:column title="科系" property="ClassName" sortable="true" class="left" />
		<display:column title="課程名稱" property="chi_name" sortable="true" class="left" />
		<display:column title="學分" property="credit" sortable="true" class="left" />
		<display:column title="暑修, 隨班, 及格人數" property="sumSt" sortable="true" class="left" />
	</display:table>
	
	<td>
</tr-->
<tr height="40">
	<td>
	<table width="98%" class="hairLineTable">
  				<tr>
    				<td class="hairLineTdF">
    					<table>
    						<tr>
    							<td>
    							 人數統計:
    							</td>
    							<td>
    							<a href="course/export/list4Idiot.jsp">
    							<img src="images/ico_file_excel.png" border="0">
    							</a>
    							</td>
    							<td>
    							 &nbsp;&nbsp;&nbsp;&nbsp;人數詳細:
    							</td>
    							<td>
    							<a href="/CIS/List4IdiotSummer?ClassLess=${CheckOutForm.map.classLess}">
    							<img src="images/ico_file_excel.png" border="0">
    							</a>
    							</td>
    						</tr>
    					</table>
    				</td>
  				</tr>
			</table>
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable">
	
		</td>
	</tr>
</c:if>	


<!-- 同學程重複開課 start -->
<c:if test="${CheckOpt=='ReOpenHist'}">

<tr>
	<td>	
	
	
	<display:table name="${ReOpenHist}" export="true" pagesize="10" sort="list" excludedParams="*" class="list">
		<display:column title="學年" property="school_year" sortable="true" class="left" />
		<display:column title="學期" property="school_term" sortable="true" class="left" />
		<display:column title="班級" property="depart_class" sortable="true" class="left" />
		<display:column title="課程代碼" property="cscode" sortable="true" class="left" />
		
		
	</display:table>
	
	<td>
</tr>

<tr height="30">
	<td class="fullColorTable">

	</td>
</tr>
</c:if>	


<!-- 同學程重複開課 end -->

</html:form>
</table>


<%@ include file="/pages/include/AjaxGetStmdOrGstmd.jsp" %>
<%@ include file="/pages/include/AjaxUniMod.jsp" %>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>