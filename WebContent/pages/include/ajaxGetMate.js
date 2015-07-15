function getAny(query, objId, tobjid, table, type) {
	if(query!=''){
		objectId=objId;
		targetObjectId=tobjid;
		tid=type;
		sendDyna('/CIS/AjaxGetNameOrNumber?query='+encodeURIComponent(query)+'&table='+table+'&type='+type+'&'+Math.floor(Math.random()*999));
	}	
}