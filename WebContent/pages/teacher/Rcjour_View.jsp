<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>


<table width="100%" cellpadding="0" cellspacing="0" border="0">
  <html:form action="/Teacher/Rcjour_View" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
<!-- 標題列 start -->
    <tr>
      <td class="fullColorTable" width="100%">
        <table width="100%" cellpadding="0" cellspacing="0" border="0">
          <tr height="30">
            <td align="center" valign="middle"><b>期刊論文發表資料表&nbsp;</b></td>
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
                    <input type="text" name="schoolYear" id="schoolYear" size="1"
                           onMouseOver="showHelpMessage('民國年', 'inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)" value="${school_year }"/>
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
                  <td width="15%" align="center" class="hairLineTdF">論文名稱</td>
                  <td class="hairLineTd">
                    <input type="text" name="title" value="${title }" size="60"/>
                  </td>
                </tr>
              </table>
              <table width="99%" class="hairLineTable">
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">收錄分類</td>
                  <td class="hairLineTd">
                    <select name=kindid>
					  <option value=""></option>
					  <c:forEach items="${kindid}" var="c">
					    <option <c:if test="${kindid_v==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">作者順序</td>
                  <td width="35%" class="hairLineTd">
                    <select name=authorno>
					  <option value=""></option>
					  <c:forEach items="${authorno}" var="c">
					    <option <c:if test="${authorno_v==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">通訊作者</td>
                  <td class="hairLineTd">
                    <select name=COM_authorno>
                      <option value=""></option>
                      <option <c:if test="${COMauthorno=='Y'}">selected</c:if> value="Y">是</option>
                      <option <c:if test="${COMauthorno=='N'}">selected</c:if> value="N">否</option>
                    </select>
                  </td>
                </tr>
              </table>
              <table width="99%" class="hairLineTable">
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">刊物名稱</td>
                  <td class="hairLineTd">
                    <input type="text" name="jname" value="${jname }" size="60"/>
                  </td>                  
                </tr>
              </table>
              <table width="99%" class="hairLineTable">
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">發表形式</td>
                  <td width="35%" class="hairLineTd">
                    <select name=type>
					  <option value=""></option>
					  <c:forEach items="${type}" var="c">
					    <option <c:if test="${type_v==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">出刊地點</td>
                  <td class="hairLineTd">
                    <select name=place>
					  <c:forEach items="${place}" var="c">
					    <option <c:if test="${place_v==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                </tr>
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">發表卷數</td>
                  <td class="hairLineTd">
                    <input type="text" name="volume" value="${volume }"/>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">發表期數</td>
                  <td class="hairLineTd">
                    <input type="text" name="period" value="${period }"/>
                  </td>
                </tr>
                <tr>                  
                  <td width="15%" align="center" class="hairLineTdF">發表年份(YYYY)</td>
                  <td class="hairLineTd">
                    <input type="text" name="pyear" id="pyear" onMouseOver="showHelpMessage('請填入數字', 'inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)" value="${pyear }"/>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">發表月份(1-12)</td>
                  <td class="hairLineTd">
                    <input type="text" name="pmonth" id="pmonth" onMouseOver="showHelpMessage('請填入數字', 'inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)" value="${pmonth }"/>
                  </td>
                </tr>
              </table>
              <table width="99%" class="hairLineTable">
                <tr>
                  <td>
                  	摘要/簡述<font color=red>(輸入字數不得低於350字)</font><br>
                    <font color=red>請在此欄位中填寫內容摘要並說明登載之刊物名稱及頁碼</font>
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
                     onClick= "window.open('/CIS/pages/teacher/Rcjour_Out.jsp','期刊論文發表資料表列印', 'width=800 scrollbars=yes,resizable=no,left=0,top=0');"/>
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
  
  function myTest() 
  {
    var iCount;
    iCount = getCookie("rcjoirCount");    
    if (iCount == 0) 
    {
      alert("請勾選至少一項資料進行檢視!!");
      return false;
    } 
    else if(iCount > 1) 
    {
      alert("請僅勾選一項資料進行檢視!!");
      return false;
    }
    Oid = getCookie("rcjour");
    //alert(Oid);
    return true;
  }
</script>		
<%@ include file="/pages/include/AjaxUniMod.jsp" %>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>
<%@ include file="/pages/include/AjaxGetEmplOrDempl.jsp" %>
<%@ include file="/pages/include/leo_Calendar.jsp" %>