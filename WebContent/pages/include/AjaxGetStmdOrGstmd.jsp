<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<!--不同的方法會叫用不同的資料回來-->
<script>
function GgetAny(query, objId, tobjid, table, type) {
		if(query!=''){
		objectId=objId;
		targetObjectId=tobjid;
		tid=type;
		sendDyna('/CIS/AjaxGetStmdOrGstmd?query='+encodeURIComponent(query)+'&table='+table+'&type='+type+'&'+Math.floor(Math.random()*999));
	}
}
</script>