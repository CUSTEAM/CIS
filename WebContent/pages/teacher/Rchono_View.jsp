<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>


<table width="100%" cellpadding="0" cellspacing="0" border="0">
  <html:form action="/Teacher/Rchono_View" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
<!-- 標題列 start -->
    <tr>
      <td class="fullColorTable" width="100%">
        <table width="100%" cellpadding="0" cellspacing="0" border="0">
          <tr height="30">
            <td align="center" valign="middle"><b>頒獎與榮譽資料表  &nbsp;</b></td>
          </tr>
        </table>
      </td>
    </tr>	
<!-- 標題列 end -->
    <tr>
      <td>      
        <table width="100%" cellpadding="0" cellspacing="0">
          <tr height="35" align="center" valign="middle" bgcolor="#CCCCFF">
            <font color="blue">${TeacherUnit}&nbsp;&nbsp;${TeacherName}&nbsp;老師</font>
          </tr>
        </table>        
      </td>
    </tr>
    <tr>
      <td>      
        <table width="100%" cellpadding="0" cellspacing="0">
          <tr>
            <td>            
              <table width="99%" class="hairLineTable">
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">年度</td>
                  <td class="hairLineTd">
                    <input type="text" name="schoolYear" value="${school_year }"/>
                    <input type=hidden name="oid_s" value="${oid_s }"/>
                  </td>                   
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>
              <table width="99%" class="hairLineTable">
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">所屬計畫案號</td>
                  <td class="hairLineTd">
                    <input type="text" name="projno" value="${projno }"/>
                  </td>
                </tr>
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">獲獎名稱</td>
                  <td class="hairLineTd">
                    <input type="text" name="title" value="${title }" size="60"/>
                  </td>
                </tr>
              </table>
              <table width="99%" class="hairLineTable">
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">作者順序</td>
                  <td class="hairLineTd">
                    <select name=authorno>
					  <option value=""></option>
					  <c:forEach items="${authorno}" var="c">
					    <option <c:if test="${authorno_v==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">頒獎國別</td>
                  <td class="hairLineTd">
                    <input type="text" name="nation" value="${nation }"/>
                  </td>
                </tr>
              </table>
              <table width="99%" class="hairLineTable">                
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">頒獎機構</td>
                  <td class="hairLineTd">
                    <input type="text" name="inst" value="${inst }" size="60"/>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">頒獎日期(YYYY/MM/DD)</td>
                  <td class="hairLineTd">
                    <input type="text" name="bdate" value="${bdate }"/>
                  </td>
                </tr>
              </table>
              <table width="99%" class="hairLineTable">
                <tr>
                  <td>
                  	綱要&簡述<br><font color=red>輸入字數不得低於350字</font>
                  </td>                  
                </tr>
                <tr>
				  <td>
					<textarea name="intor" rows="10" cols="90">${intor }</textarea>
                  </td>
				</tr>
              </table>
              <input type=hidden name="approve" value="${approve }"/>
              <c:if test="${approve == '98'}" >
              <table width="99%" class="hairLineTable">
                <tr>
                  <td>
                  	不核可原因
                  </td>                  
				  <td align="center">
					<textarea name="approveTemp" rows="5" cols="81" readonly="ture">${approveTemp }</textarea>					
                  </td>
				</tr>
              </table>
              </c:if>
            </td>
          </tr>
          <tr>
            <td class="hairLineTd" align="center">
              <c:if test="${approve != '97'}" >
                <input type="submit" name="method" value="更新" Key="Update" class="CourseButton"/>
              </c:if>
              <!-- <input type="submit" name="method" value="刪除" Key="Delete" class="CourseButton"/>  -->
              <input type="submit" name="method" value="返回" Key="Back" class="CourseButton"/>
              <input type="submit" name="method" value="匯出" Key="PrintOut" class="CourseButton" 
                     onClick= "window.open('/CIS/pages/teacher/Rchono_Out.jsp','專刊資料表', 'width=800 scrollbars=yes,resizable=no,left=0,top=0');"/>
              <hr class="myHr"/>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </html:form>
</table>

<script>
  function showSpecs(ID) 
  {
    var id = ID;
    if(document.getElementById(id).style.display == 'none') 
    {
      document.getElementById(id).style.display = 'inline';      
    } 
    else 
    {
      document.getElementById(id).style.display = 'none';				
    }
  }
</script>	

<%@ include file="/pages/include/AjaxUniMod.jsp" %>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>
<%@ include file="/pages/include/AjaxGetEmplOrDempl.jsp" %>
<%@ include file="/pages/include/leo_Calendar.jsp" %>	