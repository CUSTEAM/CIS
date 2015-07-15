<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
  <html:form action="/Teacher/Rcact_View" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
<!-- 標題列 start -->
    <tr>
      <td class="fullColorTable" width="100%">
        <table width="100%" cellpadding="0" cellspacing="0" border="0">
          <tr height="30">
            <td align="center" valign="middle"><b>教師/職員參加學術活動資料表&nbsp;</b></td>
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
                  <td width="13%" align="center" class="hairLineTdF">年度</td>
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
                  <td width="15%" class="hairLineTdF" align="center">活動名稱</td>
                  <td class="hairLineTd">
                    <input type="text" name="actname" id="actname" value="${actname }" size="45"/>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">主辦單位(進修機構)</td>
                  <td class="hairLineTd">
                    <input type="text" name="sponoff" value="${sponoff }" size="45"/>
                  </td>
                </tr>
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">活動種類</td>
                  <td class="hairLineTd">
                    <select name=kindid>
					  <option value=""></option>
					  <c:forEach items="${kindid}" var="c">
					    <option <c:if test="${kindid_v==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">活動類型</td>
                  <td class="hairLineTd">
                    <select name=typeid>
					  <option value=""></option>
					  <c:forEach items="${typeid}" var="c">
					    <option <c:if test="${typeid_v==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                </tr>
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">活動地點</td>
                  <td class="hairLineTd">
                    <select name=placeid>
					  <option value=""></option>
					  <c:forEach items="${placeid}" var="c">
					    <option <c:if test="${placeid_v==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">參與情形</td>
                  <td class="hairLineTd">
                    <select name=joinid>
					  <option value=""></option>
					  <c:forEach items="${joinid}" var="c">
					    <option <c:if test="${joinid_v==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                </tr>
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">開始日期</td>
                  <td class="hairLineTd">
                    <input type="text" name="bdate" value="${bdate }"/>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">結束日期</td>
                  <td class="hairLineTd">
                    <input type="text" name="edate" value="${edate }"/>
                  </td>
                </tr>
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">時數</td>
                  <td class="hairLineTd">
                    <input type="text" name="hour" value="${hour }"/>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">學校補助情形</td>
                  <td class="hairLineTd">
                    <input type="text" name="schspon" value="${schspon }" size="45"/>
                  </td>
                </tr>
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">是否有研習證明</td>
                  <td class="hairLineTd">
                    <select name=certyn>
                      <option value=""></option>
                      <option <c:if test="${certyn=='Y'}">selected</c:if> value="Y">有</option>
                      <option <c:if test="${certyn=='N'}">selected</c:if> value="N">無</option>
                    </select>                    
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">證書字號</td>
                  <td class="hairLineTd">
                    <input type="text" name="certno" value="${certno }"/>
                  </td>
                </tr>                
              </table>
              <table width="99%" class="hairLineTable">
                <tr>
                  <td>
                  	研習心得<font color=red>輸入字數不得低於350字</font>
                  </td>                  
                </tr>
                <tr>
				  <td align="center">
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
                     onClick= "window.open('/CIS/pages/teacher/Rcact_Out.jsp','學術活動資料表列印', 'width=800 scrollbars=yes,resizable=no,left=0,top=0');"/>
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
    iCount = getCookie("rcactCount");    
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
    Oid = getCookie("rcact");
    alert(Oid);
    return true;
  }
</script>

<%@ include file="/pages/include/AjaxUniMod.jsp" %>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>
<%@ include file="/pages/include/AjaxGetEmplOrDempl.jsp" %>
<%@ include file="/pages/include/leo_Calendar.jsp" %>