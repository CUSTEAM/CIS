<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<table class="hairLineTable" width="99%">
	<tr>		
		<td class="hairLineTdF" nowrap>原畢業學校資料</td>
		<td class="hairLineTdF" width="100%">新畢業學校資料</td>		
	</tr>


<c:forEach items="${schools}" var="s" varStatus="c">
	<tr>
		<td class="hairLineTdF" nowrap>
			<input type="text" size="2" value="${s.cnt}人" disabled/>
			<input type="text" name="schl_name" size="40" value="${s.schl_name}" />
			
		</td>
		<td class="hairLineTdF">
			<input type="text" name="schl_name_new" id="schl_name${c.index}" onkeyup="if(this.value.length>1)getAny(this.value, 'schl_name${c.index}', 'schl_code${c.index}', 'Recruitschool', 'name')" size="40"/>
			<input type="text" name="schl_code_new" id="schl_code${c.index}" onkeyup="if(this.value.length>1)getAny(this.value, 'schl_code${c.index}', 'schl_name${c.index}', 'Recruitschool', 'no')" size="6"
			style="ime-mode:disabled" autocomplete="off" autocomplete="off"/>
								 
								 
			
			
		</td>
	</tr>
</c:forEach>
</table>