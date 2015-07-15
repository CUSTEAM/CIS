<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Teacher/Coansw4Tech" method="post" onsubmit="init('儲存中, 請稍後')">	
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/chart_line.gif"></div>
		<div nowrap style="float:left;"><font class="gray_15">教學評量</font></div>		
		</td>
	</tr>
	<tr>
		<td>
		<table class="hairLIneTable" width="99%">
			<tr>
				<td class="hairLIneTdF"><font class="gray_15">本學期教學評量</font></td>
			</tr>
		</table>
		</td>
	</tr>
	
<!-- 非遠距start -->
<c:if test="${countMyM>0}">	
	<tr>
		<td>
		
		<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" wmode="opaque" 
		 codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0"
		 width="100%" height="350" id="charts">
		<param name="wmode" value="opaque" />
		<param NAME=movie VALUE=course/export/charts.swf?library_path=/CIS/pages/course/export/charts_library/&xml_source=/CIS/CoanswForTech?<%=(int)(Math.random()*49)%>&ele=true">
		<param NAME=quality VALUE=high>
		<embed src="course/export/charts.swf?library_path=/CIS/pages/course/export/charts_library/&xml_source=/CIS/CoanswForTech?<%=(int)(Math.random()*49)%>"
		quality=high  WIDTH="100%" HEIGHT="350" NAME="charts" ALIGN=""
		type="application/x-shockwave-flash" PLUGINSPAGE="http://www.macromedia.com/go/getflashplayer"></EMBED>
		</object>
		
		
		</td>
	</tr>
	
<!-- 文字 start-->	
	<tr>
		<td width="100%">		
		
		<br>
		<table width="100%">
			<tr>
				<td>
				
				<c:forEach items="${myDtimeM}" var="m">
	
				<table width="99%" class="hairLineTable">
					<tr>
    					<td align="center" width="30%" rowspan="100" bgColor="ffffff">    					
    					<c:if test="${m.total<70}">
    					<font size="48" color="#bc4e32"><b>${m.total}</b></font>
    					<div><font color="#bc4e32"><a id="eSend${m.Oid}" style="cursor: pointer" onClick="callloadMsg(), 
    					showSend(this.id, '${m.Oid}', '${m.depart_class}', '${m.ClassName}', '${m.cscode}', '${m.chi_name}', '${m.techid}', '${m.total}')">
    					<c:if test="${m.total>0 && m.content_per==null}">線上回應</a> <b>|</b> 
    					<a href="/CIS/pages/downloads/course/feedback.doc" target="_blank">下載表格</a></font></div></c:if>
    					</c:if>
    					<c:if test="${m.total>=70}"><font size="48" color="999999"><b>${m.total}</b></font></c:if>
    					</td>
    					
    				</tr>
  					
					<tr>
						<td colspan="4" align="left">						
						&nbsp;&nbsp;${m.ClassName} - ${m.chi_name}
						</td>
					</tr>
					
					<c:forEach items="${m.score}" var="s">
					<tr>
						
						<td bgColor="ffffff" width="30%"  align="right" nowrap>
						${s.options}
						</td>
						<td width="30%" bgColor="ffffff">
						
						<!-- 長條圖 -->
						<table width="98%" bgColor="999999" cellspacing="0" cellpadding="0">
							<tr>
								<td>
								
								<table width="${s.score}%" bgColor="444444">
									<tr>
										<td>
										
										</td>
									</tr>
								</table>
								
								</td>
							</tr>
						</table>
						
						</td>
						<td align="center" bgColor="ffffff" width="10%">
						<c:if test="${s.score<70}"><font color="bc4e32"><b>${s.score}</b></font></c:if>
						<c:if test="${s.score>=70}"><font color="999999"><b>${s.score}</b></font></c:if>
						
						
						</td>
						
					</tr>
					</c:forEach>
					
				</table>
				<br>
	
				</c:forEach>
				
				</td>				
				
			</tr>
<!-- 文字 end-->
		</table>
	
		</td>
	</tr>
	
</c:if>	
<!-- 非遠距end -->

<!-- 遠距start -->
<c:if test="${countMyE>0}">	
	<tr>
		<td>
		
		<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" wmode="opaque" style="position:absolute; z-index:10;"
		codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0"
		width="100%" height="350" id="charts">
		<param name="wmode" value="opaque" />
		<param NAME=movie VALUE=course/export/charts.swf?library_path=/CIS/pages/course/export/charts_library/&xml_source=/CIS/CoanswForTechE?<%=(int)(Math.random()*49)%>&ele=true">
		<param NAME=quality VALUE=high>
		<param NAME=bgcolor VALUE=#f0fcd7>
		<embed src="course/export/charts.swf?library_path=/CIS/pages/course/export/charts_library/&xml_source=/CIS/CoanswForTechE?<%=(int)(Math.random()*49)%>"
		quality=high  WIDTH="100%" HEIGHT="350" NAME="charts" ALIGN=""
		TYPE="application/x-shockwave-flash" PLUGINSPAGE="http://www.macromedia.com/go/getflashplayer"></EMBED>
		</OBJECT>		
		
		</td>
	</tr>
	
<!-- 文字 start-->	
	<tr>
		<td width="100%">		
		
		<br>
		<table width="100%">
			<tr>
				<td>
				
				<c:forEach items="${myDtimeE}" var="m">
	
				<table width="99%" class="hairLineTable">
					<tr>
    					<td align="center" width="30%" rowspan="100" bgColor="ffffff">
    					<c:if test="${m.total<70}">
    					<font size="48" color="#bc4e32"><b>${m.total}</b></font>
    					<div><font color="#bc4e32"><a id="eSend${m.Oid}" style="cursor: pointer" onClick="callloadMsg(), 
    					showSend(this.id, '${m.Oid}', '${m.depart_class}', '${m.ClassName}', '${m.cscode}', '${m.chi_name}', '${m.techid}', '${m.total}')" 
    					style="CURSOR:pointer"><c:if test="${m.total>0 && m.content_per==null}">線上回應</a> <b>|</b> 
    					<a href="http://cap.cust.edu.tw/CIS/pages/downloads/feedback.doc" target="_blank">下載表格</a></font></div></c:if>
    					</c:if>
    					<c:if test="${m.total>=3}"><font size="48" color="999999"><b>${m.total}</b></font></c:if>
    					</td>
    					
    				</tr>
  					
					<tr>
						<td colspan="4" align="left">						
						&nbsp;&nbsp;${m.ClassName} - ${m.chi_name}
						</td>
					</tr>
					
					<c:forEach items="${m.score}" var="s">
					<tr>
						
						<td bgColor="ffffff" width="30%" align="right" nowrap>
						${s.options}
						</td>
						<td width="30%" bgColor="ffffff">
						
						<!-- 長條圖 -->
						<table width="98%" bgColor="999999" cellspacing="0" cellpadding="0">
							<tr>
								<td>
								
								<table width="${s.score*20}%" bgColor="444444">
									<tr>
										<td>
										
										</td>
									</tr>
								</table>
								
								</td>
							</tr>
						</table>
						
						</td>
						<td align="center" bgColor="ffffff" width="10%">
						<c:if test="${s.score<70}"><font color="bc4e32"><b>${s.score}</b></font></c:if>
						<c:if test="${s.score>=70}"><font color="999999"><b>${s.score}</b></font></c:if>
						</td>
						
					</tr>
					</c:forEach>
					
				</table>
				<br>
	
				</c:forEach>
				
				</td>				
				
			</tr>
<!-- 文字 end-->
		</table>
	
		</td>
	</tr>
	
</c:if>	
<!-- 遠距end -->





<!-- 歷年資料START -->
	<tr>
		<td>
		<table class="hairLIneTable" width="99%">
			<tr>
				<td class="hairLIneTdF">
				<table>
					<tr>
						<td width="1"><img src="images/icon/table.gif"></td>
						<td width="100%"><font class="gray_15">歷年教學評量</font></td>
						
					</tr>
				</table>
				</td>
			</tr>
		</table>
		
		
		<c:forEach items="${myConaswHist}" var="m">
		<c:if test="${!empty m.coansw}">
		<table class="hairLIneTable" width="99%">
			<tr>
				<td class="hairLIneTdF" nowrap><b>${m.school_year}學年</b></td>
				
				<td class="hairLIneTdF" width="150"nowrap>班級名稱</td>
				<td class="hairLIneTdF" width="100%" nowrap>課程名稱</td>
				<td class="hairLIneTdF" nowrap align="right">樣本數</td>
				<td class="hairLIneTdF" nowrap align="right">平均值</td>
			</tr>
			
			
			
			
			
			<c:forEach items="${m.coansw}" var="c">			
			<tr>
				<td class="hairLIneTdF" nowrap>第${c.school_term}學期</td>
				<td class="hairLIneTdF" width="150"nowrap>${c.ClassName}</td>
				<td class="hairLIneTdF" width="100%" nowrap>${c.chi_name}</td>
				<td class="hairLIneTdF" width="50" nowrap align="right">${c.samples}</td>
				<td class="hairLIneTdF" width="50" nowrap align="right">${c.avg}</td>
				
			</tr>
			</c:forEach>
			<tr>
				<td class="hairLIneTdF" align="center">-</td>
				<td class="hairLIneTdF" align="center">-</td>
				<td class="hairLIneTdF">${m.school_year}學年共 ${fn:length(m.coansw)}門課程</td>
				<td class="hairLIneTdF" align="right">${m.samples}</td>
				<td class="hairLIneTdF" align="right">${m.yearAvg}</td>
			</tr>	
		</table>
		</c:if>
		</c:forEach>


		</td>
	</tr>
	<tr>
		<td>
		<table class="hairLIneTable" width="99%">
			<tr>
				<td class="hairLIneTdF">
				<table>
					<tr>
						<td width="1"><img src="images/icon/icon_info.gif"></td>
						<td width="100%">系統自98學年第2學期開始儲存並顯示歷年統計資料，而所有統計資料仍以各部制課務單位每學期留存的為標準。</td>
						
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>
<!-- 歷年資料END -->
	<tr height="30">
		<td class="fullColorTable">
		
		</td>
	</tr>
</table>

<table id="sendTable" class="non_transparent_top" style="display:none" align="center" width="100%">
	<tr>
		<td width="100%">
		
		<table align="center" width="100%" bgcolor="cfe69f" border="0" cellspacing="1" cellpadding="1">
			<tr>
				<td bgColor="ffffff" width="100%">
				
				
				<table align="center" width="100%">
					<tr height="30">
						<td class="fullColorTable">&nbsp;<img src="images/overlays.png" />
						<input type="text" id="ClassNameValue" disabled size="16"/><input type="text" id="chi_nameValue" disabled/>
						<input type="hidden" id="cscode" name="cscode"/>
						<input type="hidden" id="depart_class" name="depart_class"/>
						<input type="hidden" name="chi_name" id="chi_name"/>
						<input type="hidden" name="ClassName" id="ClassName"/>
						<input type="hidden" id="dtimeOid" name="dtimeOid"/>
						<input type="hidden" id="techid" name="techid"/>
						<input type="hidden" id="techid" name="score"/>
						</td>
					</tr>
					<tr>
						<td width="100%">						
						<textarea name="content_per" rows="10" style="width:100%;"></textarea>
						</td>
					</tr>
					<tr height="30">
						<td class="fullColorTable" align="center">
						<INPUT type="submit"
						   name="method" id="send"
						   onMouseOver="showHelpMessage('送出', 'inline', this.id)" 
					 	   onMouseOut="showHelpMessage('', 'none', this.id)"
						   value="<bean:message key='Send'/>"
						   class="CourseButton" /><input 
							value="取消" type="button" onClick="cancel()" class="CourseButton"/>
						</td>
					</tr>
				
				</table>
				
				</td>
			</tr>
		</table>
		
		
		</td>
	</tr>
</html:form>
</table>

<script>
	function getLeft(ed) {
					var tmp = ed.offsetLeft;
					ed = ed.offsetParent
				while(ed) {
					tmp += ed.offsetLeft;
					ed = ed.offsetParent;
					}
				return tmp;
				}
	
	function getTop(ed) {
					var tmp = ed.offsetTop;
					ed = ed.offsetParent
				while(ed) {
					tmp += ed.offsetTop;
					ed = ed.offsetParent;
					}
				return tmp+24;
				}
	function showSend(id, dtimeOid, depart_class, ClassName, cscode, chi_name, techid, score){
		
		document.getElementById('dtimeOid').value=dtimeOid;
		document.getElementById('depart_class').value=depart_class;
		document.getElementById('ClassName').value=ClassName;
		document.getElementById('cscode').value=cscode;
		document.getElementById('chi_name').value=chi_name;
		document.getElementById('techid').value=techid;
		document.getElementById('score').value=score;
		
		document.getElementById('ClassNameValue').value=ClassName;
		document.getElementById('chi_nameValue').value=chi_name;
		
		//document.getElementById('content_per').value='fuck'
		
		document.getElementById('sendTable').style.left=getLeft(document.getElementById(id))-210;	//y座標
 		document.getElementById('sendTable').style.top=getTop(document.getElementById(id))-20;	//x座標
		
		document.getElementById('sendTable').style.display='inline';
	
	
	}
</script>

<script>
	function cancel(){
		document.getElementById('loadMsg').style.display='none'
		document.getElementById('sendTable').style.display='none'
	}
</script>