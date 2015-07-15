<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>

<table class="hairLineTable" width="99%">
	<tr>
  		<td class="hairLineTdF">
  		
  		<table>
  			<tr>
  				<td>	    							
  							
 				<img src="images/printer.gif" border="0">
 				<select onchange="jumpMenu('parent',this,0)">
 					<option value="javascript:void(0)">證明書  ------------------------------------</option>
 					
 					<option value="/CIS/StudentCardForOne">學生證</option>
					<!--option value="/CIS/Diploma">學位證書(~2009)</option-->					
					<option value="/CIS/Print/registration/Diploma4Csgroup.do">學程證明書</option>
					<option value="/CIS/Print/registration/Transcript4Csgroup.do">學程歷年成績表</option>
					<option value="/CIS/Print/registration/Diploma12.do">副學士學位證書橫(二專)</option>
					<option value="/CIS/Print/registration/Diploma12Reissue.do">副學士學位證明書橫(二專)</option>
					
					<option value="/CIS/Print/registration/Diploma32.do">副學士學位證書橫(進專)</option>
					<option value="/CIS/Print/registration/Diploma32Reissue.do">副學士學位證明書橫(進專)</option>
					<option value="/CIS/Print/registration/Diploma72.do">學士學位證書橫(學院)</option>						
					<option value="/CIS/Print/registration/Diploma72Reissue.do">學士學位證明書橫(學院)</option>
					
					<option value="/CIS/Print/registration/Diploma14.do">學士學位證書橫(二技)</option>
					<option value="/CIS/Print/registration/Diploma14Reissue.do">學士學位證明書橫(二技)</option>
					
					<option value="/CIS/Print/registration/Diploma64.do">學士學位證書橫(四技)</option>					
					<option value="/CIS/Print/registration/Diploma64Reissue.do">學士學位證明書橫(四技)</option>	
									
					<option value="/CIS/Print/registration/Diploma1G.do">碩士學位證書橫</option>
					<option value="/CIS/Print/registration/Diploma8G.do">碩士學位證書橫(子產研究所)</option>
					
 				</select>  							
  				<select onchange="jumpMenu('parent',this,0)">
  					<option value="javascript:void(0)">名條名冊 --------------</option>  					
  					<option value="/CIS/List4Student">通用報表</option>  					
  					<option value="/CIS/List4NewStudents">新生名冊</option>
  					
  					<c:if test="${target=='s'}"><!-- 在校特定 -->
  					<option value="/CIS/Print/registration/StDetail.do">學籍簡表</option>
					<option value="/CIS/GraduateReportPoi?type=confirm">畢業生名冊</option>
					<option value="/CIS/GraduateReportPoi?type=ready">應屆畢業生名冊</option>
					<option value="/CIS/StudentList">各班級名條</option>
					<option value="/CIS/StudentList4Transfer">轉學生名冊</option>
					<option value="/CIS/StudentList4Retire?type=reb">復學生名冊</option>
					</c:if>
					<option value="/CIS/StudentList4Retire?type=rer">休學生名冊</option>
					<c:if test="${target=='g'}"><!-- 離校特定 -->
					<option value="/CIS/GraduateReportPoi?type=confirm">畢業生名冊</option>					
					<option value="/CIS/StudentList4Retire?type=rej">退學生名冊</option>
					<option value="/CIS/Print/registration/ListGra.do">本校後續就讀名冊</option>
					</c:if>					
  				</select>  							
  				<select onchange="jumpMenu('parent',this,0)">
  					<option value="javascript:void(0)">統計加總 ------------------</option>  					
  					<c:if test="${target=='s'}"><!-- 在校特定 -->
					<option value="/CIS/CountStudents4StudentManager">人數統計(原始格式)</option>
					<option value="/CIS/CountStudents4StudentManager1">人數統計(新增格式)</option>
					<option value="/CIS/CountStudentAge">年齡統計</option>					
					</c:if>					
					<c:if test="${target=='g'}"><!-- 離校特定 -->
					<option value="/CIS/LeaveHistList">修課期間統計</option>
					<option value="/CIS/LeaveCauseList">休退原因統計表</option>					
					<option value="/CIS/LeaveAvg">休退比例統計表</option>
					<option value="/CIS/LeaveHistList">休退復學歷程統計表</option>
					</c:if>					
  				</select>  							
  				<select onchange="jumpMenu('parent',this,0)">
  					<option value="javascript:void(0)">成績報表</option>
					<option value="/CIS/Score4Class">學期成績(總表)</option>
					<option value="/CIS/Score4Personal">學期成績(個人)</option>
  				</select>  				
  				</td>  				
  			</tr>
  		</table>
  		
  		</td>
	</tr>
</table>