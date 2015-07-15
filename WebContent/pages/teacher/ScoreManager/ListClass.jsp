<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>






<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF" nowrap>開課班級</td>
		<td class="hairLineTdF">課程名稱</td>
		<td class="hairLineTdF">編輯與列印
		<input type="hidden" name="Dtime_oid" id="Dtime_oid" />
		<input type="hidden" name="type" id="type" />
		</td>		
	</tr>
	<c:forEach items="${myClass}" var="m">
	<c:if test="${m.cscode!='50000'}">
	<tr>
		<td class="hairLineTdF" width="220" nowrap>${m.ClassName}<br><font size="-1">${m.dtimeClass}</font></td>
		<td class="hairLineTdF">${m.chi_name}</td>
		<td class="hairLineTdF" nowrap>		
		<select id="type${m.Oid}" class="upInput">
			<option <c:if test="${m.type==''}">selected</c:if> value="">一般格式</option>
			<option <c:if test="${m.type=='e'}">selected</c:if> value="e">語言中心格式</option>
			<option <c:if test="${m.type=='s'}">selected</c:if> value="s">體育室格式</option>
		</select>
		<input type="submit" name="method" onmouseover="cho('${m.Oid}', document.getElementById('type${m.Oid}').value);" value="<bean:message key='EditScore'/>" class="gGreen"  />
		<input type="button" class="gCancel" value="期中列印" onClick="chosRt('m', document.getElementById('type${m.Oid}').value, '${m.Oid}');" />
		<input type="button" class="gCancel" value="期末列印" onClick="chosRt('f', document.getElementById('type${m.Oid}').value, '${m.Oid}');" />
		</td>
	</tr>
	</c:if>
	</c:forEach>
</table>

<script>
function chosRt(level, type, Oid){
	if(level=="m"){
		//期中
		if(type==""){location.href="/CIS/Print/teacher/NorRat.do?level=m&dtimeOid="+Oid;}
		if(type=="e"){location.href="/CIS/Print/teacher/EngRat.do?level=m&dtimeOid="+Oid;}
		if(type=="s"){location.href="/CIS/Print/teacher/SpoRat.do?level=m&dtimeOid="+Oid;}
	}else{
		if(type==""){location.href="/CIS/Print/teacher/NorRat.do?level=f&dtimeOid="+Oid;}
		if(type=="e"){location.href="/CIS/Print/teacher/EngRat.do?level=f&dtimeOid="+Oid;}
		if(type=="s"){location.href="/CIS/Print/teacher/SpoRat.do?level=f&dtimeOid="+Oid;}
	}
	

}
</script>

<script>
function cho(Oid, type){
	document.getElementById("Dtime_oid").value=Oid;
	document.getElementById("type").value=type;
}
</script>
