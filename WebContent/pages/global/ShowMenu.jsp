<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
	<tr height="30">
		<td class="fullColorTable" colspan="2">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/user_tick.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">我的功能清單</font></div>		
		</td>
	</tr>
	<tr>
		<td width="50%" valign="top">
		<c:forEach items="${Menu.items}" var="i"  begin="0" end="${fn:length(Menu.items)/2}">	
	
		<table class="hairLineTable" width="99%">
			<tr>
				<td style="padding:1px; background-color:${i.module.color};"></td>
				<td class="hairLineTdF" width="99%" <c:if test="${!empty i.subMenu.items}">style="cursor:pointer;"</c:if> onClick="showObj('f${i.module.oid}');">
				<c:if test="${!empty i.subMenu.items}"><b>${i.module.label}</b><br></c:if>
				<c:if test="${empty i.subMenu.items}"><html:link page="${i.module.action}" style="font-size:18px;"><b>${i.module.label}</b><br></html:link></c:if>
				
				<div <c:if test="${fn:length(Menu.items)>10}">style="display:none;"</c:if> id="f${i.module.oid}">
				<c:forEach items="${i.subMenu.items}" var="s">
					<c:if test="${s.module.icon!=null}"><img border=0 src="images/${s.module.icon}"/></c:if>
					<html:link page="${s.module.action}">${s.module.label}</html:link><br>
				</c:forEach>
				</div>
				</td>
			</tr>
		</table>
		</c:forEach>
		</td>
		<td valign="top">
		
		
	
		<c:forEach items="${Menu.items}" var="i" begin="${(fn:length(Menu.items)/2)+1}">	
	
		<table class="hairLineTable" width="99%">
			<tr>
				<td style="padding:1px; background-color:${i.module.color};"></td>
				<td class="hairLineTdF" width="99%" <c:if test="${!empty i.subMenu.items}">style="cursor:pointer;"</c:if> onClick="showObj('f${i.module.oid}');">
				<c:if test="${!empty i.subMenu.items}"><b>${i.module.label}</b><br></c:if>
				<c:if test="${empty i.subMenu.items}"><html:link page="${i.module.action}" style="font-size:18px;"><b>${i.module.label}</b><br></html:link></c:if>
				
				<div <c:if test="${fn:length(Menu.items)>10}">style="display:none;"</c:if> id="f${i.module.oid}">
				<c:forEach items="${i.subMenu.items}" var="s">
					<c:if test="${s.module.icon!=null}">
					<img border=0 src="images/${s.module.icon}"/>
					</c:if>
					<html:link page="${s.module.action}">${s.module.label}</html:link><br>
				</c:forEach>
				</div>
				</td>
			</tr>
		</table>
		</c:forEach>
		
		
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" colspan="2">		
		
		</td>
	</tr>
</table>