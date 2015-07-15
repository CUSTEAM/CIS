<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script>
var exSwitch=false;
var exSwitchid='';
</script>
<%@ include file="navigat.jsp"%>

<c:forEach items="${graList}" var="g" begin="${begin}" end="${end}">
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF">
		<%@ include file="info.jsp"%>
		<%@ include file="blance.jsp"%>
		<%@ include file="scorehist.jsp"%>
		</td>
	</tr>
</table>		
		
<script>
function selCou${g.student_no}(id, note, sdtimeOid){	
	<c:forEach items="${g.myCs}" var="m">
	if(document.getElementById('check${m.Oid}').value!=''){
		var str=document.getElementById("off"+id).innerHTML;
		document.getElementById("off"+id).innerHTML='';		
		document.getElementById('check${m.Oid}').value="";
		document.getElementById('simNote${m.Oid}').value=note;
		document.getElementById("SavedtimeOid"+${m.Oid}).value=sdtimeOid;
		
		document.getElementById("off"+id).innerHTML=str+'<br>'+document.getElementById('my${m.Oid}').innerHTML;
		document.getElementById('my${m.Oid}').style.display="none";		
		document.getElementById("simSHOid"+${m.Oid}).value="";
	}
	</c:forEach>

if(document.getElementById("tr"+id).className=="fullColorTrF"){
		document.getElementById("tr"+id).className="fullColorTrLite";
	}else{
		document.getElementById("tr"+id).className="fullColorTrF";
	}	
}
</script>
		
</c:forEach>























<script>
function deleteNote(id){
	if(document.getElementById(id).value!=""){
		document.getElementById(id).value="";
	}
}
</script>

<script>
function clearNote(id){
	alert(id);
	document.getElementById(id).value="DEL";
}
</script>



<script>
function openEx(id){

	if(exSwitch==false){
		exSwitchid="exNote"+id;
		if(document.getElementById("extr"+id).style.display=='none'){
		document.getElementById("extr"+id).style.display='inline';
		}else{
			document.getElementById("extr"+id).style.display='none';
		}
		exSwitch=true;	
	}
	
}
</script>


<script>
function inpuText(inText){
	if(exSwitch==true)
	document.getElementById(exSwitchid).value=document.getElementById(exSwitchid).value+inText;
}
</script>






<script>
function closeEx(id){
	exSwitch=false;
	exSwitchid='';
	document.getElementById("extr"+id).style.display='none'
}
</script>

<script>
function discount(id, studentNo, dtime){
	if(document.getElementById("simDiscount"+id).value==""){
		document.getElementById("simDiscount"+id).value=studentNo;
		document.getElementById("simDisDtime"+id).value=dtime;
	}else{
		document.getElementById("simDiscount"+id).value="";
		document.getElementById("simDisDtime"+id).value=dtime;
	}
}
</script>