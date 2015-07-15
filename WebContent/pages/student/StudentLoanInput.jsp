<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>

<script>
history.go(1);
</script>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Student/StudentLoanInput" method="post" focus="wifeIdno" onsubmit="init('系統處理中...')">
	<script>generateTableBanner('<div class="gray_15"><B>學 生 就 學 貸 款</B></div>');</script>
    <tr>
      	<td>      
        	<table width="100%" cellpadding="0" cellspacing="0">
          		<tr>
            		<td>            
              			<table width="100%" class="hairLineTable">
                			<tr>
                  				<td width="15%" class="hairLineTdF" align="center">配偶身分證字號</td>
                  				<td class="hairLineTd">
                  					<html:text property="wifeIdno" size="10" maxlength="10" />
                  				</td>
                  				<td width="15%" align="center" class="hairLineTdF">配偶姓名</td>
                  				<td class="hairLineTd">
                  					<html:text property="wifeName" size="10" maxlength="10" />
                  				</td>
                			</tr>
                			<tr>
                  				<td width="15%" align="center" class="hairLineTdF">父親身分證字號</td>
                  				<td class="hairLineTd">
                  					<html:text property="fatherIdno" size="10" maxlength="10" />
                  				</td>
                  				<td width="15%" align="center" class="hairLineTdF">父親姓名</td>
                  				<td class="hairLineTd">
                  					<html:text property="fatherName" size="10" maxlength="10" />
                  				</td>
                			</tr>
               	 			<tr>
                  				<td width="15%" align="center" class="hairLineTdF">母親身分證字號</td>
                  				<td class="hairLineTd">
                  					<html:text property="motherIdno" size="10" maxlength="10" />
                  				</td>
                  				<td width="15%" align="center" class="hairLineTdF">母親姓名</td>
                  				<td class="hairLineTd">
                  					<html:text property="motherName" size="10" maxlength="10" />
                  				</td>
                			</tr>
                			<tr>
                  				<td width="15%" align="center" class="hairLineTdF">貸款金額</td>
                  				<td class="hairLineTd">
                  					<html:text property="amount" size="10" maxlength="100" />
                  				</td>
                  				<td width="15%" align="center" class="hairLineTdF">畢業年月</td>
                  				<td class="hairLineTd">
                  					<html:select property="gradeYear" size="1">
                  						<html:options property="gradeYears" labelProperty="gradeYearsName" />
                  					</html:select>
                  					<html:select property="gradeMonth">
                  						<html:option value="06">六月</html:option>
                  					</html:select>
                  				</td>
                			</tr>
                			<tr>
                  				<td width="15%" align="center" class="hairLineTdF">通訊電話</td>
                  				<td class="hairLineTd" colspan="3">
                  					<html:text property="phone" size="15" maxlength="20" />
                  				</td>
                			</tr>
                			<tr>
                  				<td width="15%" align="center" class="hairLineTdF">戶籍地址</td>
                  				<td class="hairLineTd" colspan="3">
                  					<html:text property="address" size="50" maxlength="100" />
                  				</td>
                			</tr>
                			<tr>
                				<td colspan="4" class="hairLineTdF">保證人一</td>
                			</tr>
                			<tr>
                  				<td width="15%" align="center" class="hairLineTdF">身分證字號</td>
                  				<td class="hairLineTd">
                  					<html:text property="sponsorIdno1" size="10" maxlength="10" />
                  				</td>
                  				<td width="15%" align="center" class="hairLineTdF">姓名</td>
                  				<td class="hairLineTd">
                  					<html:text property="sponsorName1" size="10" maxlength="10" />
                  				</td>
                			</tr>
                			<tr>
                  				<td width="15%" align="center" class="hairLineTdF">關係</td>
                  				<td class="hairLineTd">
                  					<html:text property="sponsorRelation1" size="10" maxlength="10" />
                  				</td>
                  				<td width="15%" align="center" class="hairLineTdF">連絡電話</td>
                  				<td class="hairLineTd">
                  					<html:text property="sponsorPhone1" size="10" maxlength="10" />
                  				</td>
                			</tr>
                			<tr>
                				<td colspan="4" class="hairLineTdF">保證人二</td>
                			</tr>
                			<tr>
                  				<td width="15%" align="center" class="hairLineTdF">身分證字號</td>
                  				<td class="hairLineTd">
                  					<html:text property="sponsorIdno2" size="10" maxlength="10" />
                  				</td>
                  				<td width="15%" align="center" class="hairLineTdF">姓名</td>
                  				<td class="hairLineTd">
                  					<html:text property="sponsorName2" size="10" maxlength="10" />
                  				</td>
                			</tr>
                			<tr>
                  				<td width="15%" align="center" class="hairLineTdF">關係</td>
                  				<td class="hairLineTd">
                  					<html:text property="sponsorRelation2" size="10" maxlength="10" />
                  				</td>
                  				<td width="15%" align="center" class="hairLineTdF">連絡電話</td>
                  				<td class="hairLineTd">
                  					<html:text property="sponsorPhone2" size="10" maxlength="10" />
                  				</td>
                			</tr>
              			</table>
            		</td>
          		</tr>
        	</table>		
      	</td>
	</tr>
	<script>generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Save'/>" class="CourseButton">&nbsp;'
     						  + '<INPUT type="submit" name="method" value="<bean:message key='Cancel'/>" class="CourseButton">');</script>
</html:form>
</table>