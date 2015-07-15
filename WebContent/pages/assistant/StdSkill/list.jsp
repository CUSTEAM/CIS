<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<display:table name="${skilist}" export="false" id="row" pagesize="100" sort="list" excludedParams="*" class="list" >
  	<display:column title="動作" property="act" class="center"/>
  	<display:column title="學期" property="termyear" sortable="true" class="center"/>
  	<display:column title="學號" property="student_no" sortable="true" class="left"/>
  	<display:column title="姓名" property="student_name" sortable="true" class="left" />
  	<!-- display:column title="證照代碼" property="Code" sortable="true" class="left" /-->
  	<!--display:column title="代碼/名稱" property="name" sortable="true" class="left" href="StdSkill.do" paramId="editOid" paramProperty="Oid"/-->
  	<display:column title="代碼/名稱" property="name" sortable="true" class="left"/>
  	<!--display:column title="級別" property="Level" sortable="true" class="left" /-->
  	<display:column title="類別" property="Type" sortable="true" class="left" />
  	<!--display:column title="生效日期" property="LicenseValidDate" sortable="true" class="left" /-->
  	<display:column title="類別" property="AmountType" class="left" sortable="true" />
  	<display:column title="補助金額" property="Amount" class="right" sortable="true" />
  	<display:column title="補助日期" property="AmountDate" class="left" sortable="true" />
  	
</display:table>