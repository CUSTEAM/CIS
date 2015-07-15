<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<c:set var="now" value="<%=new java.util.Date()%>" />
<script>
function addAbs(ddate, abs){	
	//alert(getCookie(ddate));
	if(getCookie(ddate)==null){
		setCookie(ddate, abs, true, null, null, null);
	}else{
		deleteCookie(ddate, null, null );
	}	
}

//取cookie
function getCookie(name) {
    var start = document.cookie.indexOf( name + "=" );
    var len = start + name.length + 1;
    if ( ( !start ) && ( name != document.cookie.substring( 0, name.length ) ) ) {
        return null;
    }
    if ( start == -1 ) return null;
    var end = document.cookie.indexOf( ';', len );
    if ( end == -1 ) end = document.cookie.length;
    return unescape( document.cookie.substring( len, end ) );
}

//存
function setCookie(name, value, expires, path, domain, secure) {
	//alert(name+"="+value);
    var today = new Date();
    today.setTime(today.getTime());
    if (expires){
        expires=expires*1000*60*60*1; //預設1小時吧
    }
    var expires_date = new Date( today.getTime() + (expires) );
    
    //採用escape 對ISO Latin對指定的字串編碼。所有的空格、標點符號、特殊文字以及其他非ASCII字串都將被轉化成%xx格式的字串編碼
    document.cookie = name+'='+escape( value ) +
    //document.cookie = name+'='+value+
        ((expires) ? ';expires='+expires_date.toUTCString() : '' ) + //expires.toGMTString()
        ((path) ? ';path=' + path : '' ) +
        ((domain) ? ';domain=' + domain : '' ) +
        ((secure) ? ';secure' : '' )+
        (';comment=shit')+
        (';version=1');
    
}
 
function deleteCookie( name, path, domain ) {
    if ( getCookie( name ) ) document.cookie = name + '=' +
            ( ( path ) ? ';path=' + path : '') +
            ( ( domain ) ? ';domain=' + domain : '' ) +
            ';expires=Thu, 01-Jan-1970 00:00:01 GMT';
}

function listCookie(){
	
	var strCookie=document.cookie; 
	var arrCookie=strCookie.split("; "); 
	var date, abs, dot;
	for(var i=0;i<arrCookie.length;i++){ 
		
		dot=arrCookie[i].indexOf(",")
		abs=arrCookie[i].substring(dot+1, dot+2);
		date=arrCookie[i].substring(0,dot);
		
		
		
		if(date.search("-")>0){
			document.getElementById("abshit").innerHTML=document.getElementById("abshit").innerHTML+
			"<input type='text' name='ddate' value='"+date+"'/>"+
			"<input type='text' name='abs' value='"+abs+"'/><br>";
		}
		
		//document.getElementById('loadMsg').style.width=document.body.scrollWidth+1024;
		document.getElementById('loadMsg').style.height=document.body.scrollHeight+8192;
		document.getElementById('loadMsg').style.display="inline";
		
	
	} 

	
}

</script>
<input type="button" onClick="listCookie()" valu="test" />

<div id="abshit">


</div>

<table width="99%" class="hairLineTable">
	<tr>
		<td class="hairLineTdF"></td>
		<c:forEach items="${weekday}" var="w" begin="1" end="7">
		<td class="hairLineTdF" width="14%" align="center">${w}</td>
		</c:forEach>
	</tr>
	
	<tr>
		<td class="hairLineTdF"></td>	
		<td class="hairLineTdF" width="14%" align="center">星期一</td>
		<td class="hairLineTdF" width="14%" align="center">星期二</td>
		<td class="hairLineTdF" width="14%" align="center">星期三</td>
		<td class="hairLineTdF" width="14%" align="center">星期四</td>
		<td class="hairLineTdF" width="14%" align="center">星期五</td>
		<td class="hairLineTdF" width="14%" align="center">星期六</td>
		<td class="hairLineTdF" width="14%" align="center">星期日</td>
	</tr>
	
	<c:forEach begin="1" end="14" var="c">
	
	<c:if test="${c==11}">
	<tr>
	<td class="hairLineTdF"></td>
	<c:forEach items="${weekday}" var="w" begin="1" end="7">
	<td class="hairLineTdF" width="14%" align="center">${w}</td>
	</c:forEach>
	</tr>
	<tr>
		<td class="hairLineTdF">夜</td>	
		<td class="hairLineTdF" width="14%" align="center">星期一</td>
		<td class="hairLineTdF" width="14%" align="center">星期二</td>
		<td class="hairLineTdF" width="14%" align="center">星期三</td>
		<td class="hairLineTdF" width="14%" align="center">星期四</td>
		<td class="hairLineTdF" width="14%" align="center">星期五</td>
		<td class="hairLineTdF" width="14%" align="center">星期六</td>
		<td class="hairLineTdF" width="14%" align="center">星期日</td>
	</tr>
	</c:if>
	
	<tr height="75">
		<td class="hairLineTdF">${c}</td>		
		<c:forEach begin="1" end="7" var="w">		
		<td class="hairLineTdF" style="font-size:14px;" valign="middle">			
			<div style="cursor:pointer; text-align:center;" id="td${w}${c}"onClick="setCs(this.id, '${w}', '${c}')">
			
			</div>				
			<c:forEach items="${allClass}" var="a">				
			<c:if test="${a.week==w && (c>=a.begin && c<=a.end)}">
			<script>showObj("td${w}${c}");</script>
			<div id="a${w}${c}" style="cursor:pointer; width:100%;">
			${a.chi_name}${a.opt}<br>${a.cname}老師<br>
			
			<fmt:parseDate var="setNow" value="${weekday[w]}" type="DATE" pattern="yyyy-MM-dd"/> 
			
			
			<c:forEach items="${abs}" var="a" >
				<c:if test="${a.week_day==w}">							
					<c:if test="${a.bs[c]=='1'}">重大傷病</c:if>
					
					<c:if test="${a.bs[c]=='2'}">
					
					<font color="red">曠課</font>
					
					<c:if test="${(now.getTime()-setNow.getTime())<777600000}">
					
					<script>
					if(getCookie("${weekday[w]}"+","+"${c}")!=null){
						document.write("<input type=checkBox checked  onClick=\"addAbs('${weekday[w]},${c}', '${weekday[w]}');\" />");
						}else{
							document.write("<input type=checkBox onClick=\"addAbs('${weekday[w]},${c}', '${weekday[w]}');\" />");
					}
					</script>
					請假
					</c:if>
					</c:if>
					
					<c:if test="${a.bs[c]=='3'}"><font color="green">病假</font></c:if>
					<c:if test="${a.bs[c]=='4'}"><font color="green">事假</font></c:if>
					<c:if test="${a.bs[c]=='5'}"><font color="orange">遲到</font</c:if>
					<c:if test="${a.bs[c]=='6'}"><font color="green">公假</font></c:if>
					<c:if test="${a.bs[c]=='7'}"><font color="green">喪假</font></c:if>
					<c:if test="${a.bs[c]=='8'}"><font color="green">婚假</font></c:if>
					<c:if test="${a.bs[c]=='9'}"><font color="green">產假</font></c:if>
						
				</c:if>
			</c:forEach>
			
			<c:if test="${now<setNow}">	
			
			<script>
			if(getCookie("${weekday[w]}"+","+"${c}")!=null){
				document.write("<input type=checkBox checked  onClick=\"addAbs('${weekday[w]},${c}', '${weekday[w]}');\" />");
				}else{
					document.write("<input type=checkBox onClick=\"addAbs('${weekday[w]},${c}', '${weekday[w]}');\" />");
				}
			</script>		
			請假
			
			</c:if>
			
			
			</div>			
			</c:if>			
			</c:forEach>
		</td>		
		</c:forEach>
	</tr>
	</c:forEach>
</table>






<div class="ds_box" id="miniForm"  style="display:none; padding:5px;">
<table><tr><td><input type="button" class="gCancel" value="關閉" onClick="closeMiniForm();"/></td></tr></table>

<div id="dinfo">
<img src="images/indicator/loading2.gif"/>
</div>

<input type="hidden" name="DtimeOid" id="choseOid">
<input type="hidden" name="week" id="week">
<input type="hidden" name="begin" id="begin">
<input type="hidden" name="end" id="end">
<input type='submit' name='method' value=<bean:message key='AddCourse'/> id="AddCourse" style="display:none;"/>
</div>