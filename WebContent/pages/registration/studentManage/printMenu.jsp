<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<table width="99%" class="hairLineTable" cellspacing="0" cellpadding="0">
	<tr>
		<td class="hairLineTdF">		
		
			<table>
				<tr>
					<td>
							
					<img src="images/printer.gif" border="0">
					<select name="reportType" onchange="jumpMenu('parent',this,0)" id="inSchool"
			   		 onMouseOver="showHelpMessage('各項個人證明', 'inline', this.id)"
			   		 onMouseOut="showHelpMessage('', 'none', this.id)">
						<option value="javascript:void(0)">個人證明書</option>
						
					<option value="/CIS/Print/registration/Diploma4Csgroup.do">學程證明書</option>
					<option value="/CIS/Print/registration/Transcript4Csgroup.do">學程歷年成績表</option>
						
						<!--option value="/CIS/Diploma">中文學位證書(~2009)</option-->						
						<!--option value="/CIS/DiplomaReissue">中文學位證明書補發(~2009)</option-->
						<option value="/CIS/Print/registration/DropOutCht.do">中文修業證書</option>						
						<option value="/CIS/StudentCardForOne">學生證重印(非補發)</option>						
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
						
						<option value="javascript:void(0)">-------------------------</option>						
						<option value="/CIS/DiplomaEng">英文畢業證書</option>
						<option value="/CIS/Diploma4NoGraduate?type=eng">英文修業證書</option>
						<option value="javascript:void(0)">-------------------------</option>
						<option value="/CIS/OldStudentInfo?studentNo=${StudentManagerForm.map.studentNo}">學籍卡影像檔</option>
						<option value="/CIS/OldStudentInfo?studentNo=${StudentManagerForm.map.studentNo}&type=scr">歷年成績影像檔</option>
						<option value="javascript:void(0)">-------------------------</option>
						
					</select>
					</td>
  				
  					<td>
						<html:link page="/Course/StudentScoreHistory.do" paramId="no" paramName="NO" onclick="return confirm('成績必須在所有成績均轉入歷年成績檔後才能產生正確資料')">
						<img src="images/vcard.png" border="0"> 歷年成績表
						</html:link>
						<html:link page="/Course/StudentScoreHistoryEnglish.do">
						<img src="images/vcard.png" border="0"> 英文歷年成績表
						</html:link>
						<html:link page="/Course/ForeignExchangeStudentScoreHistory.do">
						<img src="images/vcard.png" border="0"> 交換學生歷年成績表
						</html:link>
						<html:link page="/Course/StudentDiploma.do" target="_blank">
						<img src="images/vcard.png" border="0"> 學位證明書補發
						</html:link>
						<html:link page="/Course/StudentScoreHistory4CreditClass.do" target="_blank">
						<img src="images/vcard.png" border="0"> 歷年成績表(學院專用)
						</html:link>
					</td>
					
				</tr>
			</table>
		</td>
	</tr>
</table>