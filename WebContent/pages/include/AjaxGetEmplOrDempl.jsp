<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<!--不同的方法會叫用不同的資料回來-->
<table class="ds_box" cellpadding="0" cellspacing="0" name="Acsname" id="Acsname" style="display: none;">
	<tr>
		<td>
		<div id="AcsnameInfo" onclick="document.getElementById('Acsname').style.display='none'">
		empty
		</div>
		</td>
	</tr>
</table>				
<script>
/**
 * 程式進入點
 */
function GgetAny(query, objId, tobjid, table, type) {
	if(query!=''){
		objectId=objId;
		targetObjectId=tobjid;
		tid=type;
		sendDyna('/CIS/AjaxGetEmplOrDempl?query='+encodeURIComponent(query)+'&table='+table+'&type='+type+'&'+Math.floor(Math.random()*999));
	}
}
</script>