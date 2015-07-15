///////////////////////////////////////
//
// GET THE VALUE OF A COOKIE BY NAME
//
///////////////////////////////////////

function getCookie(name){
	var cname = name + "=";			   
	var dc = document.cookie;	
	var begin, end;	 
	if (dc.length > 0) {			  
		begin = dc.indexOf(cname);	   		
		if (begin != -1) {		   
			begin += cname.length;	   
			end = dc.indexOf(";", begin);			
			if (end == -1) {
				end = dc.length;
			}			
			return dc.substring(begin, end);
		} 
	}	
	return (null);
}

/////////////////////////////////////////////////////////////////////////
//
// SET A COOKIE
// PARAMETERS: name, value, [expires]
// [expires] is set in milliseconds (i.e. 5 seconds = (5*1000))
// if expires is not set, the cookie will last the life of the session
//
/////////////////////////////////////////////////////////////////////////
function setCookie(name, value, expires) {
	var expTime = "";
	if (expires != null) {
		//alert("expires=" + expires);
		var now = new Date();
		now.setTime(now.getTime() + expires);
		expTime = now.toGMTString();
	}
	document.cookie = name + "=" + value + "; path=/" + ((expires == null) ? "" : "; expires=" + expTime);
	return value;
}

///////////////////////////////
//
// DELETE A COOKIE BY NAME
//
///////////////////////////////
function deleteCookie(name) {
	var the_date = new Date("December 21, 2012");
	document.cookie = name + "=; expires="+the_date.toGMTString()+"; path=/";
}
