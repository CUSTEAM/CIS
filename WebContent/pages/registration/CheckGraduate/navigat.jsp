<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table>
	<tr>
		<td>
		<img src="images/16-cube-blue.png">
		</td>
		<td width="100%">
		第 <b>${begin+1}</b> 至 <b>${end+1}</b> 位同學, 
		
		
		<%
		String index=request.getParameter("index");
		
		
		int z=0;
		try{
			z=Integer.parseInt(index);
		}catch(Exception e){
			z=0;
		}
		
		int x=0;
		int y=1;
		HttpSession s = request.getSession(false);
		List list=(List)s.getAttribute("graList");
		for(int i=0; i<list.size(); i++){		
			if(i%5==0){
				if((z+5)/5==y){%>				
				<b><a href="/CIS/Graduate4Next?index=<%=x%>"><font color="red" size="+2"><%=y%></font></a></b>				
				<%}else{%>
					<a href="/CIS/Graduate4Next?index=<%=x%>"><%=y%></a>			
				<%}					
				x=x+5;
				y=y+1;
			}		
		}%>		
		</td>
		
		
		<td align="right" nowrap>
		<table><tr>
		<td><table bgcolor="#3d7900" width="14" height="14" align="left"><tr><td></td></tr></table></td><td nowrap>正常</td>
		<td><table bgcolor="#bf0000" width="14" height="14" align="left"><tr><td></td></tr></table></td><td nowrap>不足</td>
		<td><table bgcolor="#666666" width="14" height="14" align="left"><tr><td></td></tr></table></td><td nowrap>無資料</td>
		
		</tr></table>
		</td>
	</tr>
</table>
