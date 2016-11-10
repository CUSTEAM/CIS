<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table cellpadding="0" cellspacing="0" width="100%">
<c:if test="${empty students}">

	<tr>
		<td>
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" width="30" align="center" nowrap>
				<img src="images/icon/icon_info_exclamation.gif" />
				</td>
				<td class="hairLineTdF" width="100%">
				點選課程或班級即可取得學生列表
				</td>
			</tr>
		</table>		
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF">				
				<b>本學期授課班級</b>
				</td>
			</tr>
			<c:forEach items="${thisdtime}" var="c">
			<tr>
				<td class="hairLineTdF">				
				<a href="../Portfolio/ListMyStudents.do?depart_class=${c.Oid}">${c.ClassName}, ${c.chi_name}, ${c.credit}學分, ${c.thour}節課</a>
				</td>
				
			</tr>
			</c:forEach>
		</table>
		
		<c:if test="${!empty tutor}">
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF">				
				<b>本學期導師班級</b>
				</td>
			</tr>
			<c:forEach items="${tutor}" var="c">
			<tr>
				<td class="hairLineTdF">				
				<a href="../Portfolio/ListMyStudents.do?depart_class=${c.ClassNo}&tutor=1">${c.ClassName}</a>
				</td>
				
			</tr>
			</c:forEach>
		</table>
		</c:if>
		
		<c:forEach items="${alldtime}" var="a">
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF">
				
				<b>${a.school_year}學年授課班級</b>
				
				</td>
				
			</tr>
			
			<c:forEach items="${a.courses}" var="c">
			<tr>
				<td class="hairLineTdF">				
				<a href="../Portfolio/ListMyStudents.do?depart_class=${c.depart_class}&cscode=${c.cscode}&year=${a.school_year}&term=${c.school_term}">第${c.school_term}學期, ${c.ClassName}, ${c.chi_name}, ${c.credit}學分, ${c.thour}節課</a>
				</td>
				
			</tr>
			</c:forEach>
		</table>
		</c:forEach>
		
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" width="30" align="center" nowrap>
				<img src="images/icon/icon_info_exclamation.gif" />
				</td>
				<td class="hairLineTdF" width="100%">
				如果您有授課卻沒有在清單中顯示請洽教務單位， 如有導師班級卻沒有在清單中顯示請洽學務單位
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	</c:if>
	
	<c:if test="${!empty students}">
	<tr>
		<td>
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" style="font-size:18px;">
				點選學生查看學習歷程檔案, 或 <a href=javascript:history.back()><b>回到班級列表</b></a>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	
	<tr>
		<td>
		
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" nowrap width="100" style="font-size:18px;">學號</td>
				<td class="hairLineTdF" nowrap width="100" style="font-size:18px;">姓名</td>
				<td class="hairLineTdF" nowrap style="font-size:18px;">學習歷程</td>
				<td class="hairLineTdF" nowrap style="font-size:18px;">電子郵件</td>
				<td class="hairLineTdF" nowrap style="font-size:18px;">選課清單</td>
				<td class="hairLineTdF" nowrap style="font-size:18px;">歷年成績</td>
				<td class="hairLineTdF" nowrap style="font-size:18px;">核心能力</td>
				<td class="hairLineTdF" nowrap style="font-size:18px;">個人履歷</td>
				<td class="hairLineTdF" width="100%"></td>
			</tr>
			
			<c:forEach items="${students}" var="s">		
			<tr>				
				
				<td style="font-size:18px;" class="hairLineTdF" nowrap id="no${s.student_no}"
				onMouseOver="showHelpMessage('<img src=/CIS/ShowImage?studentNo=${s.student_no} width=134>', 'inline', 'no${s.student_no}')" 
				onMouseOut="showHelpMessage('', 'none', 'no${s.student_no}')">${s.student_no}</td>
				<td style="font-size:18px;" class="hairLineTdF" nowrap 
				onMouseOver="showHelpMessage('<img src=/CIS/ShowImage?studentNo=${s.student_no} width=134>', 'inline', 'no${s.student_no}')" 
				onMouseOut="showHelpMessage('', 'none', 'no${s.student_no}')">${s.student_name}</td>
				
				
				<td style="font-size:18px;" class="hairLineTdF"
				onMouseOver="showHelpMessage('<img src=/CIS/ShowImage?studentNo=${s.student_no} width=134>', 'inline', 'no${s.student_no}')" 
				onMouseOut="showHelpMessage('', 'none', 'no${s.student_no}')">
				&nbsp;&nbsp;
				
				<c:if test="${s.path!=''&&s.path!=null}">
				<a target="_blank" href="http://${server}/portfolio/myPortfolio?path=${s.path}"><img src="images/icon/house.gif" border="0"/></a>
				</c:if>
				
				<c:if test="${s.path==null||s.path==''}">
				<img src="images/icon/delete.gif" border="0"/>
				</c:if>
				</td>
				
				
				
				<td style="font-size:18px;" class="hairLineTdF" nowrap id="no${s.student_no}" align="center"
				onMouseOver="showHelpMessage('<img src=/CIS/ShowImage?studentNo=${s.student_no} width=134>', 'inline', 'no${s.student_no}')" 
				onMouseOut="showHelpMessage('', 'none', 'no${s.student_no}')">
				<c:if test="${s.Email!=''&&s.Email!=null}">
				<a href="mailto:${s.Email}"><img src="images/icon/email.gif" border="0"/></a>
				</c:if>
				
				<c:if test="${s.Email==''||s.Email==null}">
				<img src="images/icon/delete.gif" border="0"/>
				</c:if>
				</td>
				
				<!-- 選課清單 -->
				<td class="hairLineTdF" nowrap id="no${s.student_no}" align="center"
				onMouseOver="showHelpMessage('<img src=/CIS/ShowImage?studentNo=${s.student_no} width=134>', 'inline', 'no${s.student_no}')" 
				onMouseOut="showHelpMessage('', 'none', 'no${s.student_no}')">
				<a href="../Portfolio/ListMyStudents.do?type=select&student_no=${s.student_no}"><img src="images/icon/book.gif" border="0" /></a>
				</td>
				
				
				<!-- 歷年成績 -->
				<td class="hairLineTdF" nowrap id="no${s.student_no}" align="center"
				onMouseOver="showHelpMessage('<img src=/CIS/ShowImage?studentNo=${s.student_no} width=134>', 'inline', 'no${s.student_no}')" 
				onMouseOut="showHelpMessage('', 'none', 'no${s.student_no}')">
				<a href="../Portfolio/ListMyStudents.do?type=score&student_no=${s.student_no}"><img src="images/icon/icon_full_score.gif" border="0" /></a>
				</td>
				
				
				
				<!-- 個人履歷 -->
				<td class="hairLineTdF" nowrap id="no${s.student_no}" align="center"
				onMouseOver="showHelpMessage('<img src=/CIS/ShowImage?studentNo=${s.student_no} width=134>', 'inline', 'no${s.student_no}')" 
				onMouseOut="showHelpMessage('', 'none', 'no${s.student_no}')">
				<a href="../Portfolio/ListMyStudents.do?type=core&student_no=${s.student_no}"><img src="images/icon/chart_pie.gif" border="0" /></a>
				</td>
				
				<!-- 核心能力 -->
				<td class="hairLineTdF" nowrap id="no${s.student_no}" align="center"
				onMouseOver="showHelpMessage('<img src=/CIS/ShowImage?studentNo=${s.student_no} width=134>', 'inline', 'no${s.student_no}')" 
				onMouseOut="showHelpMessage('', 'none', 'no${s.student_no}')">
				
				<c:if test="${s.file_name!=null}">
				<a href="/eis/getFtpFile?path=resume&file=${s.file_name}"><img src="images/icon/script_edit.gif" border="0" /></a>
				</c:if>
				</td>
				
				<!-- 空白 -->
				<td class="hairLineTdF" nowrap id="no${s.student_no}" align="center"
				onMouseOver="showHelpMessage('<img src=/CIS/ShowImage?studentNo=${s.student_no} width=134>', 'inline', 'no${s.student_no}')" 
				onMouseOut="showHelpMessage('', 'none', 'no${s.student_no}')">
				
				</td>
				
			</tr>
			</c:forEach>
			
		</table>
		
		</td>
		
	</tr>
	</c:if>
</table>