<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>

<script>
  history.go(1);
</script>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
  <html:form action="/Teacher/Rcjour" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
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
    <tr>
      <td>
        <table width="100%" cellpadding="0" cellspacing="0" onClick="showSpecs('Specs1_9')">
          <tr style="cursor:pointer;">
            <td width="10" align="left" nowrap>
              <hr class="myHr"/>
            </td>
            <td width="24" align="center" nowrap>
              <img src="images/folder_explore.gif" id="searchEx">
            </td>
            <td nowrap><font color=red><b>填表說明&nbsp;</b></font></td>
            <td width="100%" align="left">
              <hr class="myHr"/>
            </td>
          </tr>
        </table>
        <table width="100%" cellpadding="0" cellspacing="0">
          <tr height="35" valign="middle" bgcolor="#E1D2A6" style="display:none" id="Specs1_9">
            <td>
              <div class="modulecontainer filled nomessages">
	        <div class="first">
	          <span class="first"></span>
	          <span class="last"></span>
	        </div>
	        <div>
	          <div> 
	            1. 本程式仿雲科大校務基本資料庫格式與介面，所有欄位不能空白，<font color=red>如果沒有資料請填「無」</font>。 <br>
	            2. 如果要新增，請依照畫面欄位填入相關資料，填寫完畢按下新增即可。 <br>
	            3. 如果要修改記錄，可以直接按下查詢或輸入年度後按下查詢，即可顯示已輸入資料清單，勾選要作業的資料按下檢視即可執行。  <br>
	                                欄位說明如下:<br>
	            &nbsp;&nbsp;1) 年度：請輸入民國年(非學年度)<br>
	            &nbsp;&nbsp;2) 所屬計畫案號：請輸入由哪一計畫案所得到的成果，若沒有研究案請填「無」。 <br>
	            &nbsp;&nbsp;3) 論文名稱：請填寫論文名稱。 <br>
	            &nbsp;&nbsp;4) 論文收錄分類：請選擇SCI、SSCI、AHCI、TSSCI、EI、其他(O)。 <br>                
                &nbsp;&nbsp;5) 作者順序：依照發表時之排名次序，選擇作者順序。 <br>
                &nbsp;&nbsp;6) 通訊作者：教師是否為通訊作者，請選擇「是」或「否」。 <br>
                &nbsp;&nbsp;7) 刊物名稱：指發表刊物名稱，例如：JournalofFluidMechanics。 <br>
                &nbsp;&nbsp;8) 發表卷數、發表期數、發表月份、發表年份：<font color=red>均為數字欄位</font>，期數欄位可填「無」。<br>
                &nbsp;&nbsp;9) 發表形式 ：請選擇該期刊論文為「紙本」、「電子期刊」或「兩者皆有」。
	          </div>
	        </div>
	        <div class="last">
	          <span class="first"></span>
	          <span class="last"></span>
	        </div>
	      </div>
            </td>               
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
                  <td width="15%" align="center" class="hairLineTdF">民國年度</td>
                  <td class="hairLineTd">
                    <input type="text" name="schoolYear" id="schoolYear" size="1"
                           onMouseOver="showHelpMessage('請輸入民國年,非學年度', 'inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)" value=""/>
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
                    <input type="text" name="projno" value=""/>
                  </td>
                </tr>
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">論文名稱</td>
                  <td class="hairLineTd">
                    <input type="text" name="title" value="" size="60"/>
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
					    <option <c:if test="${aEmpl.kindid==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">作者順序</td>
                  <td width="35%" class="hairLineTd">
                    <select name=authorno>
					  <option value=""></option>
					  <c:forEach items="${authorno}" var="c">
					    <option <c:if test="${aEmpl.authorno==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">通訊作者</td>
                  <td class="hairLineTd">
                    <select name=COM_authorno>                      
                      <option value="Y">是</option>
                      <option selected value="N">否</option>
                    </select>
                  </td>
                </tr>
              </table>
              <table width="99%" class="hairLineTable">
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">刊物名稱</td>
                  <td class="hairLineTd">
                    <input type="text" name="jname" value="" size="60"/>
                  </td>                  
                </tr>
              </table>
              <table width="99%" class="hairLineTable">
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">發表形式</td>
                  <td class="hairLineTd">
                    <select name=type>
					  <c:forEach items="${type}" var="c">
					    <option <c:if test="${60==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">出刊地點</td>
                  <td class="hairLineTd">
                    <select name=place>
					  <c:forEach items="${place}" var="c">
					    <option <c:if test="${94==c.Oid}">selected</c:if> value="${c.Oid}">${c.name}</option>
					  </c:forEach>
					</select>
                  </td>
                </tr>
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">發表卷數</td>
                  <td class="hairLineTd">
                    <input type="text" name="volume" value=""/>
                  </td>                
                  <td width="15%" align="center" class="hairLineTdF">發表期數</td>
                  <td class="hairLineTd">
                    <input type="text" name="period" value=""/>
                  </td>
                </tr>
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">發表年份(YYYY)</td>
                  <td class="hairLineTd">
                    <input type="text" name="pyear" id="pyear" onMouseOver="showHelpMessage('請填入數字', 'inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)" value=""/>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">發表月份(1-12)</td>
                  <td class="hairLineTd">
                    <input type="text" name="pmonth" id="pmonth" onMouseOver="showHelpMessage('請填入數字', 'inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)" value=""/>
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
					<textarea name="intor" rows="10" cols="90"></textarea>
                  </td>
				</tr>             
              </table>
              <table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none;">
		        <tr>
				  <td id="ds_calclass"></td>
			    </tr>
			  </table>
            </td>
          </tr>
          <tr>
            <td class="hairLineTd" align="center">
              <input type="submit" name="method" value="<bean:message key='Create'/>" onClick="return mySave()" class="CourseButton"/>
              <input type="submit" name="method" value="查詢" Key="Query" class="CourseButton"/>
              <hr class="myHr"/>
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <c:if test="${RcjourList != null}" >
	  <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
	  <tr>
	    <td>
	      <table width="100%" cellpadding="0" cellspacing="0">
		    <tr>
		      <td align="center">  
	            <display:table name="${RcjourList}" export="false" id="row" pagesize="20" sort="list" excludedParams="*" class="list">
	  		      <c:if test="${empty RcjourList}">
	     		    <%@ include file="../include/NoBanner.jsp" %>
	  		      </c:if>
	              <display:column title="<script>generateTriggerAll(${fn:length(RcjourList)}, 'rcjour');</script>" class="center" >
	                <script>generateCheckbox("${row.oid}", "rcjour");</script></display:column>
 	              <display:column title="年度"		property="school_year"	sortable="true" class="center" />
	              <display:column title="論文名稱"	property="title"		sortable="true" class="center" />
	              <display:column title="刊物名稱" 	property="jname" 		sortable="true" class="center" />
	              <display:column title="審核狀態" 	property="name"		sortable="true" class="center" />              
 	            </display:table>
 	          </td>
 	        </tr>	      
	      </table>
	    </td>
	  </tr>
	  <tr>
        <td class="hairLineTd" align="center">
          <input type="submit" name="method" value="檢視" key="View" onClick="return myTest()" class="CourseButton"/>          
        </td>
      </tr>
    </c:if>    
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
    iCount = getCookie("rcjourCount");    
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
  
  function mySave() 
  {        
    
    if (document.getElementById("schoolYear").value == '') 
    {
      alert("年度,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("projno").value == '') 
    {
      alert("計畫案號,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("title").value == '') 
    {
      alert("論文名稱,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("authorno").value == '') 
    {
      alert("作者分類,此欄位不得為空白");
      return false;
    }
    else if (document.getElementById("COM_authorno").value == '') 
    {
      alert("通訊作者,此欄位不得為空白");
      return false;
    }  
    else if (document.getElementById("kindid").value == '') 
    {
      alert("收錄分類,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("jname").value == '') 
    {
      alert("刊物名稱,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("volume").value == '') 
    {
      alert("發表卷數,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("period").value == '') 
    {
      alert("發表期數此,欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("pmonth").value == '') 
    {
      alert("發表月份,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("pyear").value == '') 
    {
      alert("發表年份,此欄位不得為空白");
      return false;
    } 
    else 
    
    return true;
  }
</script>

<%@ include file="/pages/include/AjaxUniMod.jsp" %>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>
<%@ include file="/pages/include/AjaxGetEmplOrDempl.jsp" %>
<%@ include file="/pages/include/leo_Calendar.jsp" %>		