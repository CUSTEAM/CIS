var convert = scheduler.date.date_to_str("%Y-%m-%d %H:%i");
var XD;
function createXMLHttpRequestDyna(){
	try{
		XD=new XMLHttpRequest();
	}catch(e){
		XD=false;
	}
	if (window.ActiveXObject){
		try{
			XD=new ActiveXObject("Msxml2.XMLHTTP");
		}catch (e){
			XD=new ActiveXObject("Microsoft.XMLHTTP");
		}
	}	
	if (window.XMLHttpRequest){
		XD=new XMLHttpRequest();
	}	
}

function proceDyna(){
		
	if(XD.readyState==4){
		//if(XD.status==200){
		if(XD.status==500){
			alert("資料同步不成功, 請重新整理畫面(F5)繼續");
		}
	}
}

addEvent = function (event_id, event_object) {//增加方法
	createXMLHttpRequestDyna();
	XD.open("GET",			
			"/CIS/editCalendar.do?start_date="+convert(event_object.start_date)+
			"&end_date="+convert(event_object.end_date)+
			"&details="+encodeURIComponent(event_object.details)+
			"&members="+encodeURIComponent(event_object.members)+
			"&rec_type="+encodeURIComponent(event_object.rec_type)+
			"&name="+encodeURIComponent(event_object.text)+			
			"&id="+encodeURIComponent(event_id)+"&method=addEvent"+
			"&event_length="+event_object.event_length
			,true);	
	XD.onreadystatechange=proceDyna;
	XD.send(null);
};

//编辑方法
changeEvent = function (event_id, event_object) {
	createXMLHttpRequestDyna();
	XD.open("GET",			
			"/CIS/editCalendar.do?start_date="+convert(event_object.start_date)+
			"&end_date="+convert(event_object.end_date)+
			"&details="+encodeURIComponent(event_object.details)+
			"&members="+encodeURIComponent(event_object.members)+
			"&rec_type="+encodeURIComponent(event_object.rec_type)+
			"&name="+encodeURIComponent(event_object.text)+			
			"&id="+encodeURIComponent(event_id)+"&method=changeEvent"+
			"&event_length="+event_object.event_length
			,true);	
	XD.onreadystatechange=proceDyna;
	XD.send(null);
};

//删除
deleteEvent = function (event_id, event_object) {
	createXMLHttpRequestDyna();
	XD.open("GET",			
			"/CIS/editCalendar.do?start_date="+convert(event_object.start_date)+
			"&end_date="+convert(event_object.start_date)+
			"&details="+encodeURIComponent(event_object.details)+
			"&members="+encodeURIComponent(event_object.members)+
			"&rec_type="+encodeURIComponent(event_object.rec_type)+
			"&name="+encodeURIComponent(event_object.text)+			
			"&id="+encodeURIComponent(event_id)+"&method=deleteEvent"+
			"&event_length="+event_object.event_length
			,true);	
	XD.onreadystatechange=proceDyna;
	XD.send(null);
};