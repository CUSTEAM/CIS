<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF" width="99%">
		<input type="button" class="gCancel" value="列印" onClick="preview();">
		</td>
	</tr>
</table>
<table class="hairLineTable" width="99%" id="table1">
	<tr>
		<td class="hairLineTdF">編號</td>
		<td class="hairLineTdF">班級名稱</td>
		<td class="hairLineTdF">課程名稱</td>
		<td class="hairLineTdF">教師</td>
		<td class="hairLineTdF" nowrap>連絡</td>
		<td class="hairLineTdF" nowrap>人數</td>
		<td class="hairLineTdF" nowrap><font size="-1">不及格</font></td>
		<td class="hairLineTdF" nowrap><font size="-1">無成績</font></td>
		<td class="hairLineTdF"></td>
		<td class="hairLineTdF"></td>
	</tr>
<c:forEach items="${cslist}" var="c">
	<tr>
		<td class="hairLineTdF">${c.Oid}</td>
		<td class="hairLineTdF" nowrap><font size="-1">${c.ClassName}</font></td>
		<td class="hairLineTdF" nowrap><font size="-1">${c.chi_name}</font></td>
		<td class="hairLineTdF" nowrap>${c.cname}</td>
		<td class="hairLineTdF" nowrap><font size="-2">${c.CellPhone}</font></td>
		<td class="hairLineTdF" nowrap>${c.total}</td>
		<td class="hairLineTdF" nowrap>${c.nopa}</td>
		<td class="hairLineTdF" nowrap>${c.less}</td>
		<td class="hairLineTdF" nowrap><input type="button" class="gGreenSmall" value="編輯" 
		onClick="location.href='/CIS/Score/ScoreNotUpload.do?Oid=${c.Oid}'">
		</td>
		<td class="hairLineTdF" nowrap>
		<select id="type${c.Oid}" class="upInput">
			<option <c:if test="${m.type==''}">selected</c:if> value="">一般格式</option>
			<option <c:if test="${m.type=='e'}">selected</c:if> value="e">語言中心格式</option>
			<option <c:if test="${m.type=='s'}">selected</c:if> value="s">體育室格式</option>
		</select>
		<input type="button" class="gCancelSmall" value="期中列印" onClick="chosRt('m', document.getElementById('type${c.Oid}').value, '${c.Oid}');" />
		<input type="button" class="gCancelSmall" value="期末列印" onClick="chosRt('f', document.getElementById('type${c.Oid}').value, '${c.Oid}');" />
		</td>
	</tr>
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