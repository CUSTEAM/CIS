<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<c:forEach items="${CsGroup}" var="bg">
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF">
		
		<table>
			<tr>
				<td>
				
				<table>
					<tr>
						<td><img src="images/book_go.gif"/></td>
						<td><b>${bg.cname}學程</b></td>
						<td><font size="1">
						核心必修: <b>${bg.major}</b>學分, 核心選修: <b>${bg.minor}</b>學分
						外系課程至少: <b>${bg.outdept}</b>學分</font>
						</td>
					</tr>
				</table>
				
				<table>
					<tr>
						<td width="1"><img src="images/folder_page_zero.gif"/></td>
						<td nowrap width="100%" align="left">
						我已得 核心必修 <font <c:if test="${bg.opt1>0}">color="red"</c:if> size="2"><b>${bg.opt1}</b></font>學分, 
						核心選修 <font <c:if test="${bg.opt2>0}">color="red"</c:if> size="2"><b>${bg.opt2}</b></font>學分, 
						外系課程 <font <c:if test="${bg.optOut>0}">color="red"</c:if> size="2"><b>${bg.optOut}</b></font>學分</td>
					</tr>
				</table>	
				
				
				</td>
			</tr>
			<tr>
			
				<td>						
				<table align="left">
					<tr>
						<td> <img src="images/folder_magnify.gif" /> </td>
						<td><font size="1"><a onClick="showObj('major${bg.Oid}')" style="cursor:pointer;">查看核心必修課程</a></font></td>
					</tr>
				</table>
				<table align="left">
					<tr>
						<td> <img src="images/folder_magnify.gif" /> </td>
						<td><font size="1"><a onClick="showObj('minor${bg.Oid}')" style="cursor:pointer;">查看核心選修課程</a></font></td>
					</tr>
				</table>
									
				</td>
				
			</tr>
		</table>
		
		<table width="100%">
			<tr>
				<td width="50%" valign="top">
				
				<table class="hairLineTable" width="99%" id="major${bg.Oid}" style="display:none;">
					<tr>
						<td colspan="5" class="hairLineTd">
						核心必修課程				
						</td>
					</tr>
					<tr>
						<td class="hairLineTdF" nowrap><font size="1">開課系所</font></td>
						<td class="hairLineTdF" nowrap><font size="1">課程代碼</font></td>
						<td class="hairLineTdF"><font size="1">課程名稱</font></td>
						<td class="hairLineTdF" nowrap><font size="1">選別</font></td>
						<td class="hairLineTdF" nowrap><font size="1">學分</font></td>
					</tr>
				<c:if test="${!empty bg.smallGroupMajor}" >
				<c:forEach items="${bg.smallGroupMajor}" var="sg">
					<tr>
						<td class="hairLineTdF" nowrap><font size="1">${sg.deptName}</font></td>
						<td class="hairLineTdF" nowrap>
						<c:if test="${sg.igot}">
						<font size="1" color="red">${sg.cscode}</font><img src="images/icon/8-em-check.png"/>
						</c:if>
						<c:if test="${!sg.igot}">
						<font size="1">${sg.cscode}</font>
						</c:if>
						</td>
						<td class="hairLineTdF"><font size="1">${sg.chi_name}</font></td>
						<td class="hairLineTdF" nowrap><font size="1">${sg.opt}</font></td>
						<td class="hairLineTdF" nowrap><font size="1">${sg.credit}</font></td>
					</tr>
				</c:forEach>
				</c:if>
				</table>
				
				</td>
				<td width="50%" valign="top">
				
				<table	class="hairLineTable" id="minor${bg.Oid}" width="99%" style="display:none;">
					<tr>
						<td colspan="5" class="hairLineTd">
						核心選修課程				
						</td>
					</tr>
					<tr>
						<td class="hairLineTdF" nowrap><font size="1">開課系所</font></td>
						<td class="hairLineTdF" nowrap><font size="1">課程代碼</font></td>
						<td class="hairLineTdF"><font size="1">課程名稱</font></td>
						<td class="hairLineTdF" nowrap><font size="1">選別</font></td>
						<td class="hairLineTdF" nowrap><font size="1">學分</font></td>
					</tr>
				<c:if test="${!empty bg.smallGroupMinor}" >
				<c:forEach items="${bg.smallGroupMinor}" var="sg">
					<tr>
						<td class="hairLineTdF" nowrap><font size="1">${sg.deptName}</font></td>
						<td class="hairLineTdF" nowrap>
						<c:if test="${sg.igot}">
						<font size="1" color="red">${sg.cscode}</font><img src="images/icon/8-em-check.png"/>
						</c:if>
						<c:if test="${!sg.igot}">
						<font size="1">${sg.cscode}</font>
						</c:if>								
						</td>
						<td class="hairLineTdF"><font size="1">${sg.chi_name}</font></td>
						<td class="hairLineTdF" nowrap><font size="1">${sg.opt}</font></td>
						<td class="hairLineTdF" nowrap><font size="1">${sg.credit}</font></td>
					</tr>
				</c:forEach>
				</c:if>
				</table>
				
				</td>
			</tr>
		</table>
		
		
		
		
		</td>
	</tr>
	
	

	

	
	
	
</table>


</c:forEach>