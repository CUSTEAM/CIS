function GgetAny(query, objId, tobjid, table, type) {
		if(query!=''){
		objectId=objId;
		targetObjectId=tobjid;
		tid=type;
		sendDyna('/CIS/AjaxGetStmdOrGstmd?query='+encodeURIComponent(query)+'&table='+table+'&type='+type+'&'+Math.floor(Math.random()*999));
	}
}