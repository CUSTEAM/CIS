<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>

<script>
  history.go(1);
</script>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
  <html:form action="/Teacher/Article_Statistics" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...請耐心等待...')">
<!-- 標題列 start -->
    <tr>
      <td class="fullColorTable" width="100%">
        <table width="100%" cellpadding="0" cellspacing="0" border="0">
          <tr height="30">
            <td align="center" valign="middle"><b>研究成果資料統計&nbsp;</b></td>
          </tr>
        </table>
      </td>
    </tr>	    
<!-- 標題列 end -->		
<!-- 查詢條件列 start -->  
    <tr>
      <td>      
        <table width="100%" cellpadding="0" cellspacing="0">
          <tr>
            <td>            
              <table>
                <tr>
                                    
                </tr>
              </table>   
			  <table width="99%" class="hairLineTable">
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">年度</td>
                  <td class="hairLineTd">
                    <select name=schoolYear>
					  <c:forEach items="${myYear}" var="c">
					    <option <c:if test="${school_Year==c.school_year}">selected</c:if> value="${c.school_year}">${c.school_year}</option>
					  </c:forEach>
					</select>                    
                  </td>                
                  <td width="15%" align="center" class="hairLineTdF">系所</td>
                  <td class="hairLineTd">
                    <select name=Tch_Unit>
					  <option value=""></option>
					  <c:forEach items="${Tch_Unit}" var="c">
					    <option <c:if test="${UserUnit==c.name}">selected</c:if> value="${c.idno}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                </tr>
                <tr>       
                  <td width="15%" class="hairLineTdF" align="center">教師姓名</td>
                  <td class="hairLineTd">
                    <input type="text" name="TCH_Cname" id="TCH_Cname" value="" size="20"
                           onMouseOver="showHelpMessage('輸入關鍵字即可搜尋', 'inline', this.id)" 
                           onMouseOut="showHelpMessage('', 'none', this.id)" />
                  </td>
                  <td width="15%" class="hairLineTdF">審查狀態</td>
                  <td class="hairLineTd">
                    <select name=approve>
					  <option value="">不分狀態</option>
					  <c:forEach items="${approve}" var="c">
					    <option <c:if test="${aEmpl.joinid==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>                   
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td class="hairLineTd" align="center">              
              <input type="submit" name="method" value="查詢" Key="Query" class="CourseButton" />
            </td>
          </tr>
        </table>		
      </td>
    </tr>
<!-- 查詢條件列 End -->
<!-- 結果清單列 start -->  
    <c:if test="${ArticleList != null}" >
	  <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
	  <tr>
	    <td bgcolor="#CCCCFF">	  
	    <font color="blue"><b>查詢年度 : ${Year }</b></font>    
	      <table width="100%" cellpadding="0" cellspacing="0">
		    <tr>
		      <td align="center">  
	            <display:table name="${ArticleList}" export="false" id="row" pagesize="20" sort="list" excludedParams="*" class="list">
	  		      <c:if test="${empty ArticleList}">
	     		    <%@ include file="../include/NoBanner.jsp" %>
	  		      </c:if>	               	              
 	              
	              <display:column title="系所"	      property="Tch_Unit"		sortable="true" class="center" />
	              <display:column title="教師名稱"	  property="TCH_Cname"		sortable="true" class="center" />	              
	              <display:column title="學術活動" 	  property="Rcact"		    sortable="true" class="center" />
	              <display:column title="計畫與產學" 	  property="Rcporj"		    sortable="true" class="center" />
	              <display:column title="期刊論文" 	  property="Rcjour"		    sortable="true" class="center" />
	              <display:column title="研討會論文" 	  property="Rcconf"		    sortable="true" class="center" />
	              <display:column title="專書(篇章)" 	  property="Rcbook"		    sortable="true" class="center" />
	              <display:column title="專利/新品種"   property="Rcpet"		    sortable="true" class="center" />
	              <display:column title="獲獎與榮譽" 	  property="Rchono"		    sortable="true" class="center" />	              	              
 	            </display:table>
 	            <display:table name="${total}" export="false" id="row" pagesize="20" sort="list" excludedParams="*" class="list">	  		      
	              <display:column title="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                                     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                                     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;合  計"	  
	                                                  property=""	sortable="true" class="center" />	              
	              <display:column title="學術活動" 	  property="Ra"		    sortable="true" class="center" />
	              <display:column title="計畫與產學" 	  property="Rp"		    sortable="true" class="center" />
	              <display:column title="期刊論文" 	  property="Rj"		    sortable="true" class="center" />
	              <display:column title="研討會論文" 	  property="Rc"		    sortable="true" class="center" />
	              <display:column title="專書(篇章)" 	  property="Rb"		    sortable="true" class="center" />
	              <display:column title="專利/新品種"   property="Re"		    sortable="true" class="center" />
	              <display:column title="獲獎與榮譽" 	  property="Rh"		    sortable="true" class="center" />	              	              
 	            </display:table>
 	          </td> 	           	          
 	        </tr> 	              
	      </table>
	    </td>
	  </tr>
	  <tr>        
        <td class="hairLineTd" align="center">             
          <input name="Submit04" type="submit" value="匯出Excel" class="CourseButton"
                 onclick="MM_goToURL('parent','/CIS/pages/teacher/Article/Article_StatisticsSel.jsp');return document.MM_returnValue"/>
        </td>
      </tr>
    </c:if>
<!-- 結果清單列 End -->
  </html:form>
</table>

<script>
  function MM_goToURL() { //v3.0 匯出Excel
    var i, args=MM_goToURL.arguments; document.MM_returnValue = false;
    for (i=0; i<(args.length-1); i+=2) eval(args[i]+".location='"+args[i+1]+"'");
  }  
</script>

<%@ include file="/pages/include/AjaxUniMod.jsp" %>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>
<%@ include file="/pages/include/AjaxGetEmplOrDempl.jsp" %>
<%@ include file="/pages/include/leo_Calendar.jsp" %>