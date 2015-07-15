<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<%@ page import="org.apache.struts.Globals" %>
<html:html>
<logic:present name="<%=Globals.MESSAGE_KEY%>">
	<table id="msg" style="display: inline">
		<tr>
			<td>
				<img src="../pages/images/allowed1.png">
			</td>
			<td>
				<div class="rtopMsg">
					<div class="r1f"></div>
					<div class="r2f"></div>
					<div class="r3f"></div>
					<div class="r4f"></div>
				</div>
				<table cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td align="left">
							<html:messages id="msg" message="true">		  			
			 			  			&nbsp;&nbsp;&nbsp;&nbsp;<b>${msg}</b>&nbsp;&nbsp;&nbsp;&nbsp;
			 			  		</html:messages>
						</td>
					</tr>
				</table>
				<div class="rtopMsg">
					<div class="r4f"></div>
					<div class="r3f"></div>
					<div class="r2f"></div>
					<div class="r1f"></div>
				</div>

			</td>
		</tr>
	</table>
</logic:present>
<logic:present name="<%=Globals.ERROR_KEY%>">
	<table id="err" style="display: inline">
		<tr>
			<td>
				<img src="../pages/images/blocked1.png">
			</td>
			<td>
				<div class="rtopEMsg">
					<div class="r1f"></div>
					<div class="r2f"></div>
					<div class="r3f"></div>
					<div class="r4f"></div>
				</div>

				<table cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td>
							<html:errors />
						</td>
					</tr>
				</table>
				<div class="rtopEMsg">
					<div class="r4f"></div>
					<div class="r3f"></div>
					<div class="r2f"></div>
					<div class="r1f"></div>
				</div>
			</td>
		</tr>
	</table>
</logic:present>
</html:html>