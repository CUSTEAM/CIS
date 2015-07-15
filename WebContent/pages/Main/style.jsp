<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String home="home_blue";
String decorate="decorate_blue";

if(session.getAttribute("home")==null){

	Cookie cookies[]=request.getCookies();
	if(cookies!=null){
		for(int i=0; i<cookies.length; i++){
			if(cookies[i].getName().equals("home")){
				home=cookies[i].getValue();
			}		
			if(cookies[i].getName().equals("decorate")){
				decorate=cookies[i].getValue();
			}		
		}
	}	
}else{
	home=(String)session.getAttribute("home");
	decorate=(String)session.getAttribute("decorate");
}
%>