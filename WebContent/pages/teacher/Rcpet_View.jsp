<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>


<table width="100%" cellpadding="0" cellspacing="0" border="0">
  <html:form action="/Teacher/Rcpet_View" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
  <body  onload="myLoad('${depute_v}')">
<!-- 標題列 start -->
    <tr>
      <td class="fullColorTable" width="100%">
        <table width="100%" cellpadding="0" cellspacing="0" border="0">
          <tr height="30">
            <td align="center" valign="middle"><b>專利資料表 &nbsp;</b></td>
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
                  <td width="15%" align="center" class="hairLineTdF">專利名稱</td>
                  <td class="hairLineTd">
                    <input type="text" name="title" value="${title }" size="45"/>
                  </td>
                </tr>
              </table>
              <table width="99%" class="hairLineTable">
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">區域</td>
                  <td class="hairLineTd">
                    <select name=area>
					  <option value=""></option>
					  <c:forEach items="${area}" var="c">
					    <option <c:if test="${area_v==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">類型</td>
                  <td class="hairLineTd">
                    <select name=petType>
					  <option value=""></option>
					  <c:forEach items="${petType}" var="c">
					    <option <c:if test="${petType_v==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                </tr>
              </table>
              <table width="99%" class="hairLineTable">
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">報告分數</td>
                  <td class="hairLineTd">
                    <select name=score>					  
					  <c:forEach items="${score}" var="c">
					    <option <c:if test="${score_v==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">進度狀況</td>
                  <td class="hairLineTd">
                    <select name=schedule>
					  <option value=""></option>
					  <c:forEach items="${schedule}" var="c">
					    <option <c:if test="${schedule_v==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">作者順序</td>
                  <td class="hairLineTd">
                    <select name=authorno>
					  <option value=""></option>
					  <c:forEach items="${authorno}" var="c">
					    <option <c:if test="${authorno_v==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>                  
                </tr>
              </table>
              <table width="99%" class="hairLineTable">
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">申請人/權利人</td>
                  <td class="hairLineTd">
                    <input type="text" name="proposer" value="${proposer }" size="60"/>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">申請人類型</td>
                  <td class="hairLineTd">
                    <select name=proposerType>					  
					  <c:forEach items="${proposerType}" var="c">
					    <option <c:if test="${proposerType_v==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
				  </td>
                </tr>
              </table>              
              <table width="99%" class="hairLineTable">
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">申請日期(YYYY/MM/DD)</td>
                  <td class="hairLineTd">
                    <input type="text" name="bdate" id="bdate" value="${bdate }"                           
                           onfocus="ds_sh(this), this.value='';" 
                           onclick="ds_sh(this), this.value='';" 
                           autocomplete="off" 
                           style="ime-mode:disabled" 
                           autocomplete="off" 
                           readonly="ture"/>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">終止日期(YYYY/MM/DD)</td>
                  <td class="hairLineTd">
                    <input type="text" name="edate" value="${edate }" 
                           onclick="ds_sh(this), this.value='';" 
                           onfocus="ds_sh(this), this.value='';"
                           autocomplete="off" 
                           style="ime-mode:disabled" 
                           autocomplete="off" 
                           readonly="ture"/>
                  </td>
                </tr>
              </table>
              <table width="99%" class="hairLineTable">
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">發照機關</td>
                  <td class="hairLineTd">
                    <input type="text" name="inst" value="${inst }" size="70"/>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">證書字號</td>
                  <td width="35%" class="hairLineTd">
                    <input type="text" name="certno" value="${certno }"/>
                  </td>
                </tr>
              </table>
              <table width="99%" class="hairLineTable">
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">技術轉移或授權</td>
                  <td  class="hairLineTd">
                    <select name="depute" onChange="showDepute()">
					  <option value=""></option>
					  <c:forEach items="${depute}" var="c">
					    <option <c:if test="${depute_v==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>                    
                  </td>
                </tr>
              </ttable>
              <table id="deputeInfo" style="display:none">
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">轉移/授權廠商名稱</td>
                  <td class="hairLineTd" colspan="5">
                    <input type="text" name="deputeBusiness" value="${deputeBusiness }" size="60"/>
                  </td>
                </tr>                
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">技術轉移/授權金額</td>
                  <td class="hairLineTd" >
                    <input type="text" name="deputeMoney" value="${deputeMoney }"/>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">技術轉移/授權起始日</td>
                  <td class="hairLineTd" >
                    <input type="text" name="deputeSdate" id="deputeSdate"  value="${deputeSdate }"                           
                           onfocus="ds_sh(this), this.value='';" 
                           onclick="ds_sh(this), this.value='';" 
                           autocomplete="off" 
                           style="ime-mode:disabled" 
                           autocomplete="off" 
                           readonly="ture"/>
                  </td><td width="15%" align="center" class="hairLineTdF">技術轉移/授權終止日</td>
                  <td class="hairLineTd" >
                    <input type="text" name="deputeEdate" id="deputeEdate" value="${deputeEdate }"                           
                           onfocus="ds_sh(this), this.value='';" 
                           onclick="ds_sh(this), this.value='';" 
                           autocomplete="off" 
                           style="ime-mode:disabled" 
                           autocomplete="off" 
                           readonly="ture"/>
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
              <table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none;">
		        <tr>
				  <td id="ds_calclass"></td>
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
                     onClick= "window.open('/CIS/pages/teacher/Rcpet_Out.jsp','專刊資料表', 'width=800 scrollbars=yes,resizable=no,left=0,top=0');"/>
              <hr class="myHr"/>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </html:form>
</table>

<script>
  function myLoad(depute_v)
  {
    var In = depute_v;
    
    
    if(In=='42' || In=='44')
    {
      document.getElementById("deputeInfo").style.display = 'inline';
    }
    else
    {
      document.getElementById("deputeInfo").style.display = 'none';
    }    
  }
  
  function showDepute() 
  {
    
    if(document.getElementById("depute").value == '42' || document.getElementById("depute").value == '44') 
    {
      document.getElementById("deputeInfo").style.display = 'inline';      
    } 
    else
    {
      document.getElementById("deputeInfo").style.display = 'none'; 
      document.getElementById("deputeBusiness").value = '';
      document.getElementById("deputeMoney").value = '';
      document.getElementById("deputeSdate").value = '';
      document.getElementById("deputeEdate").value = '';				
    }    
  }
  
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