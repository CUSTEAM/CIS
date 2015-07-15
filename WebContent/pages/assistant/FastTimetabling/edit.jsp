<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<table cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td>
		
		
		<table width="99%" class="hairLineTable">
			<tr>
				<td class="hairLineTdF">課程名稱</td>
				<td class="hairLineTdF">任課教師</td>
				<td class="hairLineTdF">星期</td>
				<td class="hairLineTdF">開始</td>
				<td class="hairLineTdF">結束</td>
				<td class="hairLineTdF">地點</td>
			</tr>
			<tr>
				<td class="hairLineTdF" width="100%">
				<input type="hidden" name="Oid" />
				<select id="Dtime_reserve_oid" name="Dtime_reserve_oid" style="font-size:18px; width:100%;">
					<option value="">選擇已建立課程</option>
					<c:forEach items="${allCs}" var="ac">
					<option value="${ac.Oid}">${ac.Oid} - ${ac.chi_name} 
					
					
					<c:forEach items="${ac.opencs}" var="s">
					${s.Cidno}${s.Sidno}${s.Didno}${s.Grade}${s.ClassNo},
					</c:forEach>
					
					</option>
					</c:forEach>
				</select>
				</td>
				<td class="hairLineTdF">
				<input type="text" style="font-size:18px;"
						onkeyup="getAny(this.value, 'cname', 'techid', 'empl', 'name')" name="cname" id="cname"/>				
						<input type="hidden" name="techid" id="techid"/>
				</td>
				<td class="hairLineTdF">				
				<select style="font-size:18px;" name="week">
					<c:forEach begin="1" end="7" var="w">
					<option value="${w}">${w}</option>
					</c:forEach>
				</select>
				</td>
				<td class="hairLineTdF">
				
				<select style="font-size:18px;" name="begin">
					<c:forEach begin="1" end="14" var="w">
					<option value="${w}">${w}</option>
					</c:forEach>
				</select>
				</td>
				<td class="hairLineTdF">
				<select style="font-size:18px;" name="end">
					<c:forEach begin="1" end="14" var="w">
					<option value="${w}">${w}</option>
					</c:forEach>
				</select>
				</td>
				<td class="hairLineTdF"><input type="text" style="font-size:18px;" size="4" name="place" value="" 
				onkeyup="getAny(this.value, 'placehiden', 'place', 'Nabbr', 'id')" name="cname" id="cname${ad.dcOid}"/>
				
				<input type="hidden" id="placehiden">
				
				</td>
			</tr>
		</table>
		
		
		
		</td>
	</tr>
	<tr height="40">
		<td class="fullColorTable" align="center">
		<input type="submit" name="method" value="<bean:message key='Add'/>" 
		id="Save" class="gGreen">
		
		</td>
	</tr>
	<tr>
		<td>
		
		<table width="99%" class="hairLineTable">
			<tr>
				<td class="hairLineTdF">課程名稱</td>
				<td class="hairLineTdF">任課教師</td>
				<td class="hairLineTdF">地點</td>
				<td class="hairLineTdF">星期</td>
				<td class="hairLineTdF">開始</td>
				<td class="hairLineTdF">結束</td>
				
			</tr>
			<c:forEach items="${allClass}" var="ad">
			<tr>
				<td class="hairLineTdF" width="100%">
				<input type="hidden" name="Oid" value="${ad.dcOid}"/>
				
				<select id="Dtime_reserve_oid" name="Dtime_reserve_oid" style="font-size:18px; width:100%;">
					<option value="">刪除排課</option>
					<c:forEach items="${allCs}" var="ac">
					<option <c:if test="${ad.dtOid==ac.Oid}">selected</c:if> value="${ac.Oid}">${ac.chi_name}</option>
					</c:forEach>
				</select>
				</td>
				<td class="hairLineTdF">
				<input type="text" style="font-size:18px;"  value="${ad.cname }"
						onkeyup="getAny(this.value, 'cname${ad.dcOid}', 'techid${ad.dcOid}', 'empl', 'name')" name="cname" id="cname${ad.dcOid}"/>				
				
				<input type="hidden" name="techid" id="techid${ad.dcOid}" value="${ad.techid}"/>
				</td>
				
				<td class="hairLineTdF">				
				<input type="text" style="font-size:18px;" size="4" value="${ad.place}" name="place" id="place${ad.dcOid}" value="${ad.place}" 
				onkeyup="getAny(this.value, 'placehiden${ad.dcOid}', 'place${ad.dcOid}', 'Nabbr', 'id')" name="cname" id="cname${ad.dcOid}"/>				
				<input type="hidden" id="placehiden${ad.dcOid}">				
				</td>
				
				<td class="hairLineTdF">				
				<select style="font-size:18px;" name="week">
					<c:forEach begin="1" end="7" var="w">
					<option <c:if test="${ad.week==w}">selected</c:if> value="${w}">${w}</option>
					</c:forEach>
				</select>
				</td>
				<td class="hairLineTdF">
				<select style="font-size:18px;" name="begin">
					<c:forEach begin="1" end="14" var="b">
					<option <c:if test="${ad.begin==b}">selected</c:if> value="${b}">${b}</option>
					</c:forEach>
				</select>
				</td>
				<td class="hairLineTdF">
				<select style="font-size:18px;" name="end">
					<c:forEach begin="1" end="14" var="b">
					<option <c:if test="${ad.end==b}">selected</c:if> value="${b}">${b}</option>
					</c:forEach>
				</select>
				</td>
				
			</tr>
			</c:forEach>
			
			
		</table>
		
		
		
		</td>
	</tr>
	<tr height="40">
		<td class="fullColorTable" align="center">
		<input type="submit" name="method" value="<bean:message key='Save'/>" 
		id="Save" class="gSubmit">
		
		</td>
	</tr>
</table>



	
	
	

<%@ include file="/pages/include/AjaxUniMod.jsp" %>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>
<%@ include file="/pages/include/AjaxGetEmplOrDempl.jsp" %>