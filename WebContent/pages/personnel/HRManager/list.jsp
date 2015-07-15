<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td>
		<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td>
				<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
				<display:table name="${empls}" pagesize="10" id="row" sort="list" class="list">
				<display:column title="<script>generateTriggerAll(${fn:length(empls)}, 'emps'); </script>" class="center" >
	          		<script>generateCheckbox("${row.idno}", "emps")</script>
	          	</display:column>
	          	<display:column title="姓名" property="cname" sortable="true" class="center" />
	          	<display:column title="身分證字號" property="idno" sortable="true" class="center" />
	          	<!-- display:column title="性別" property="sex" sortable="true" class="center" /-->
				<display:column title="單位" property="unitName" sortable="true" class="left" />
				<display:column title="職級" property="pcodeName" sortable="true" class="center" />
				<display:column title="員工分類" property="categoryName" sortable="true" class="center" />
				<display:column title="狀態" property="statusName" sortable="true" class="center" />
				</display:table>
				
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
<!-- 列表 end-->
	<tr>
		<td>
		<br>
		<table width="100%" cellpadding="0" cellspacing="0">
  			<tr>
  				<td width="10" align="left" nowrap>
      			<hr noshade class="myHr"/>
    			</td>
    			<td width="24" align="center" nowrap>
    			<img src="images/16-cube-blue.png" />
    			</td>
    			<td nowrap>
    			報表列印&nbsp;
    			</td>
    			<td width="100%" align="left">
      			<hr noshade class="myHr"/>
    			</td>
  			</tr>
		</table>
			
		</td>
	</tr>	
	<tr>
		<td align="left">

		<table class="hairLineTable" width="99%">
  				<tr>
    				<td class="hairLineTdF">
    					<table>
    						<tr>
    							<td>	    							
    							
    							<img src="images/printer.gif" border="0">
    							    							
    							<select onchange="jumpMenu('parent',this,0)">
    								<option value="javascript:void(0)">統計報表</option>
    								<option value="/CIS/List4EmplGeneral">通用報表</option>
    							</select>
    							
    							<select onchange="jumpMenu('parent',this,0)">
    								<option value="javascript:void(0)">個人證明書</option>
    								<option value="/CIS/EmplCard">正式識別證</option>
    								<option value="/CIS/EmplCard4PartTime">臨時識別證</option>
    							</select>
    							
    							
    							<select onchange="jumpMenu('parent',this,0)">
    								<option value="javascript:void(0)">報部表格</option>
    								<option value="/CIS/List4Emp11">表1-1教師基本資料表</option>
    								<option value="/CIS/List4Emp121">表1-2-1教師實務經驗資料表</option>
    								<option value="/CIS/List4Emp122">表1-2-2教師相關證照資料表</option>
    								<option value="/CIS/List4Emp1d">表1-14職員資料表</option>
    							</select>
    							</td>
    						</tr>
    					</table>
    				</td>
  				</tr>
			</table>

		</td>

	</tr>
	
	<tr>
		<td class="fullColorTable">
		<table align="center">
			<tr>
				<td>
					<INPUT type="submit"
						   name="method"
						   value="<bean:message key='Modify'/>"
						   class="CourseButton"><INPUT type="submit"
													   name="method"
													   value="<bean:message
													   key='Cancel'/>"
													   class="CourseButton" />
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>