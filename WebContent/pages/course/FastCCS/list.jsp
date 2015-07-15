<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td>
		<table class="hairLineTable" width="99%" cellpadding="0" cellspacing="0">
			<tr>
				<td class="hairLineTdF">退選</td>
				<td class="hairLineTdF">開課班級</td>
				<td class="hairLineTdF" nowrap>課程代碼</td>
				<td class="hairLineTdF">課程名稱</td>
				<td class="hairLineTdF">選別</td>				
				<td class="hairLineTdF" nowrap>學分</td>
				<td class="hairLineTdF" nowrap>時數</td>
				<td class="hairLineTdF">人數</td>
				<td class="hairLineTdF" nowrap>時間地點</td>
			</tr>
			
			<c:set var="thours" value="0"/>   
			<c:set var="credits" value="0"/>   
			<c:forEach items="${myCs}" var="s">
			<c:set var="thours" value="${thours+s.thour}"/>   
			<c:set var="credits" value="${credits+s.credit}"/>
			<tr>
				<td class="hairLineTdF">
				<input type="checkbox" onClick="checkOid('${s.sOid}')"/>
				<input type="hidden" id="did${s.sOid}" name="dDtime_oid"/>
				</td>
				<td class="hairLineTdF" nowrap>${s.ClassName}</td>
				<td class="hairLineTdF" nowrap>${s.cscode}</td>
				<td class="hairLineTdF">${s.chi_name}</td>
				<td class="hairLineTdF" nowrap>${s.opt}</td>
				<td class="hairLineTdF">${s.credit}</td>
				<td class="hairLineTdF">${s.thour}</td>
				<td class="hairLineTdF">${s.stdSelect}/${s.Select_Limit}</td>
				<td class="hairLineTdF"><font size="-2">
				${s.buildName}
				<c:forEach items="${s.dtimeClass}" var="p">
				星期${p.week}, 第${p.begin}至${p.end}節, ${p.place}教室
				</c:forEach>
				</font>
				</td>
				
			</tr>
			</c:forEach>
			<tr>
				<td class="hairLineTdF"></td>
				<td class="hairLineTdF"></td>
				<td class="hairLineTdF" nowrap></td>
				<td class="hairLineTdF"></td>
				<td class="hairLineTdF"></td>				
				<td class="hairLineTdF" nowrap>${credits}</td>
				<td class="hairLineTdF" nowrap>${thours}</td>
				<td class="hairLineTdF"></td>
				<td class="hairLineTdF" nowrap></td>
			</tr>
		</table>
		</td>
	</tr>





	<tr height="30">
		<td class="fullColorTable" align="center">
		<input type="submit"
			   name="method" id="RemoveCourse"
			   value="<bean:message
			   key='RemoveCourse'/>"
			   class="gCancel" 
			   onMouseOver="showHelpMessage('立即退選', 'inline', this.id)" 
			   onMouseOut="showHelpMessage('', 'none', this.id)" />
			   
		
		</td>
	</tr>
</table>
<script>
function checkOid(Oid){
	if(document.getElementById("did"+Oid).value==""){
		document.getElementById("did"+Oid).value=Oid;
	}else{
		document.getElementById("did"+Oid).value="";
	}

}
</script>