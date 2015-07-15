<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>

<script>
  history.go(1);
</script>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
  <html:form action="/Teacher/Rcconf" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
<!-- 標題列 start -->
    <tr>
      <td class="fullColorTable" width="100%">
        <table width="100%" cellpadding="0" cellspacing="0" border="0">
          <tr height="30">
            <td align="center" valign="middle"><b>研討會論文發表資料表&nbsp;</b></td>
          </tr>
        </table>
      </td>
    </tr>	
    <tr>
      <td>
        <table width="100%" cellpadding="0" cellspacing="0" onClick="showSpecs('Specs1_10')">
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
          <tr height="35" valign="middle" bgcolor="#E1D2A6" style="display:none" id="Specs1_10">
            <td>
              <div class="modulecontainer filled nomessages">
	        <div class="first">
	          <span class="first"></span>
	          <span class="last"></span>
	        </div>
	        <div>
	          <div> 
	            1. 本程式仿雲科大校務基本資料庫格式與介面，所有欄位不能空白，如果沒有資料請填<font color=red>「無」</font>。 <br>
	            2. 如果要新增，請依照畫面欄位填入相關資料，填寫完畢按下新增即可。 <br>
	            3. 如果要修改記錄，可以直接按下查詢或輸入年度後按下查詢，即可顯示已輸入資料清單，勾選要作業的資料按下檢視即可執行。  <br>
	                                欄位說明如下:<br>
	            &nbsp;&nbsp;1) 年度：請輸入民國年<font color=red>(非學年度)</font>，如98年度，即是98年1月1日至98年12月31日。<br>
	            &nbsp;&nbsp;2) 所屬計畫案：請輸入由哪一計畫案所得到的成果，若沒有請填寫「無」。 <br>
	            &nbsp;&nbsp;3) 論文名稱：輸入論文名稱。 <br>
	            &nbsp;&nbsp;4) 作者順序：依照發表之排名次序，請選擇「第一作者」、「第二作者」、「弟三作者」、「第四(以上)作者」。 <br>      
                &nbsp;&nbsp;5) 通訊作者：請選擇請選擇「是」、「否」。 <br>
                &nbsp;&nbsp;6) 研討會名稱：輸入舉行的研討會名稱。 <br>
                &nbsp;&nbsp;7) 舉行國家：研討會舉行的國家。 <br>
                &nbsp;&nbsp;8) 舉行程式：研討會舉行的城市。 <br>
                &nbsp;&nbsp;9) 開始日期：研討會開始日期，例如<font color=red>「2005/01/01」</font>。 <br>
	            &nbsp;&nbsp;10)結束日期：研討會結束日期，例如<font color=red>「2005/01/01」</font>。 <br>
	            &nbsp;&nbsp;11)發表年份：研討會論文發表年份，例如<font color=red>「2005」</font>。
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
                  <td width="15%" align="center" class="hairLineTdF">所屬計畫案</td>
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
                  <td width="15%" align="center" class="hairLineTdF">研討會名稱</td>
                  <td class="hairLineTd">
                    <input type="text" name="jname" value="" size="60"/>
                  </td>
                </tr>
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">舉行之國家</td>
                  <td class="hairLineTd">
                    <input type="text" name="nation" value="" size="60"/>
                  </td>
                </tr>   
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">舉行之城市</td>
                  <td class="hairLineTd">
                    <input type="text" name="city" value="" size="60"/>
                  </td>
                </tr>
              </table>
              <table width="99%" class="hairLineTable">
                <tr>
                  <td width="15%" align="center" class="hairLineTdF">開始日期(YYYY/MM/DD)</td>
                  <td class="hairLineTd">
                    <input type="text" name="bdate" value="" onclick="ds_sh(this), this.value='';" onfocus="ds_sh(this), this.value='';"
                           autocomplete="off" style="ime-mode:disabled" autocomplete="off" readonly="ture"/>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">結束日期(YYYY/MM/DD)</td>
                  <td class="hairLineTd">
                    <input type="text" name="edate" value="" onclick="ds_sh(this), this.value='';" onfocus="ds_sh(this), this.value='';"
                           autocomplete="off" style="ime-mode:disabled" autocomplete="off" readonly="ture"/>
                  </td>
                  <td width="15%" align="center" class="hairLineTdF">發表年份(YYYY)</td>
                  <td class="hairLineTd">
                    <input type="text" name="pyear" value=""/>
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
    <c:if test="${RcconfList != null}" >
	  <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
	  <tr>
	    <td>
	      <table width="100%" cellpadding="0" cellspacing="0">
		    <tr>
		      <td align="center">  
	            <display:table name="${RcconfList}" export="false" id="row" pagesize="20" sort="list" excludedParams="*" class="list">
	  		      <c:if test="${empty RcconfList}">
	     		    <%@ include file="../include/NoBanner.jsp" %>
	  		      </c:if>
	              <display:column title="<script>generateTriggerAll(${fn:length(RcconfList)}, 'rcconf');</script>" class="center" >
	                <script>generateCheckbox("${row.oid}", "rcconf");</script></display:column>
 	              <display:column title="年度"		property="school_year"	sortable="true" class="center" />
	              <display:column title="論文名稱"	property="title"		sortable="true" class="center" />	              
	              <display:column title="研討名稱" 	property="jname" 		sortable="true" class="center" />	              
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
  function showSpecs(ID) //是否開啟填表說明
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
  
  function myTest()  //檢查勾選數
  {
    var iCount;
    iCount = getCookie("rcconfCount");    
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
    Oid = getCookie("rcconf");
    //alert(Oid);
    return true;
  }
  
  function mySave()  //新增前檢查欄位填寫狀況
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
      alert("作者順序,此欄位不得為空白");
      return false;
    }
    else if (document.getElementById("COM_authorno").value == '') 
    {
      alert("通訊作者,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("jname").value == '') 
    {
      alert("研討名稱,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("nation").value == '') 
    {
      alert("舉行國家,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("city").value == '') 
    {
      alert("舉行城市,此欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("bdate").value == '') 
    {
      alert("開始日期,欄位不得為空白");
      return false;
    } 
    else if (document.getElementById("edate").value == '') 
    {
      alert("結束日期,此欄位不得為空白");
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