<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<c:if test="${editMode==true||createMode==true||deleteMode==true}">
	<%@ include file="/pages/include/Counter.js" %>
</c:if>
<script>
history.go(1);
</script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Course/OpenCourse" method="post" onsubmit="init('查詢進行中, 請稍後')">
<c:if test="${editMode==false}">
<c:if test="${createMode==false}">
<c:if test="${deleteMode==false}">
<!--welcomeMode Start-->
	<c:if test="${dtimeList==null }">		
		<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/book.gif"></div>
		<div nowrap style="float:left;"><font class="gray_15">課程管理</font></div>		
		</td>
	</tr>
		
		
	</c:if>
	
	<c:if test="${dtimeList!=null }">
		<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/24-search.png"></div>
		<div nowrap style="float:left;"><font class="gray_15">課程管理 - 查詢結果</font></div>		
		</td>
	</tr>
		
	</c:if>
	
	
<c:import url="/pages/course/OpenCourse/search.jsp"/>

<c:if test="${dtimeList!=null}" >
		<tr>
			<td>

		<c:import url="/pages/course/OpenCourse/list.jsp"/>
	
	
	       	<c:if test="${readonly==true}">
	       	<tr>
	       		<td align="center" class="fullColorTable">
	       		<INPUT type="submit" name="method" value="<bean:message key='View'/>" class="gCancel">
	       		</td>
	       	</tr>
	       	</c:if>
	       	
		</c:if>
		</td>
		
	</tr>
</c:if>
</c:if>
</c:if>




<!--editMode start-->
<c:if test="${editMode==true}" >
<c:import url="/pages/course/OpenCourse/edit.jsp"/>
				
		<table>
		<c:forEach items="${opencsList}" var="Opcs" varStatus="opcs">
			<tr id=numInput3${opcs.index} style=display:inline>
				<td>
				<select name="cidno" <c:if test="${viewMode==true}"> disabled</c:if>>
					<option value="">清除校區</option>
					<option value="*" <c:if test="${Opcs.cidno=='*'}" > selected</c:if>>所有校區</option>
					<c:forEach items="${AllCampuses}" var="code5">
						<option value="${code5.idno}"
						<c:if test="${Opcs.cidno==code5.idno}"> selected</c:if>>${code5.name}</option>
					</c:forEach>
				</select>
				<select name="sidno" <c:if test="${viewMode==true}"> disabled</c:if>>
					<option value="">清除學制</option>
					<option value="*"<c:if test="${Opcs.sidno=='*'}"> selected</c:if>>所有學制</option>
					<c:forEach items="${AllSchools}" var="code5">
						<option value="${code5.idno}"
						<c:if test="${Opcs.sidno==code5.idno}"> selected</c:if>
						>${code5.name}</option>
					</c:forEach>
				</select>
				<select name="didno" <c:if test="${viewMode==true}"> disabled</c:if>>
					<option value="">清除科系</option>
					<option value="*" <c:if test="${Opcs.didno=='*'}"> selected</c:if>>所有科系</option>
					<c:forEach items="${AllDepts}" var="code5">
						<option value="${code5.idno}"
						<c:if test="${Opcs.didno==code5.idno}"> selected</c:if>
						>${code5.name}</option>
					</c:forEach>
				</select>
				<select name="grade" <c:if test="${viewMode==true}"> disabled</c:if>>
					<option value="">清除年級</option>
					<option value="*" <c:if test="${Opcs.grade=='*'}" > selected</c:if>>所有年級</option>
					<option value="1" <c:if test="${Opcs.grade=='1'}" > selected</c:if>>1年級</option>
					<option value="2" <c:if test="${Opcs.grade=='2'}" > selected</c:if>>2年級</option>
					<option value="3" <c:if test="${Opcs.grade=='3'}" > selected</c:if>>3年級</option>
					<option value="4" <c:if test="${Opcs.grade=='4'}" > selected</c:if>>4年級</option>
					<option value="5" <c:if test="${Opcs.grade=='5'}" > selected</c:if>>5年級</option>
				</select>
				<select name="departClass" <c:if test="${viewMode==true}"> disabled</c:if>>
					<option value="">清除班級</option>
					<option value="*" <c:if test="${Opcs.classNo=='*'}" > selected</c:if>>所有班級</option>
					<option value="1" <c:if test="${Opcs.classNo=='1'}" > selected</c:if>>甲</option>
					<option value="2" <c:if test="${Opcs.classNo=='2'}" > selected</c:if>>乙</option>
					<option value="3" <c:if test="${Opcs.classNo=='3'}" > selected</c:if>>丙</option>
					<option value="4" <c:if test="${Opcs.classNo=='4'}" > selected</c:if>>丁</option>
				</select>
				</td>
			</tr>

			</c:forEach>
			<%
			int star3=((Integer)session.getAttribute("opencsListSize")).intValue();
			for(int i=star3; i<99; i++){
			%>

			<tr id=numInput3<%=i%> style=display:none>
				<td>
				<select name="cidno">
					<option value="">選擇校區</option>
					<option value="*">所有校區</option>
					<c:forEach items="${AllCampuses}" var="code5">
						<option value="${code5.idno}">${code5.name}</option>
					</c:forEach>
				</select>
				<select name="sidno">
					<option value="">選擇校區</option>
					<option value="*">所有學制</option>
					<c:forEach items="${AllSchools}" var="code5">
						<option value="${code5.idno}">${code5.name}</option>
					</c:forEach>
				</select>
				<select name="didno">
					<option value="">選擇科系</option>
					<option value="*">所有科系</option>
					<c:forEach items="${AllDepts}" var="code5">
						<option value="${code5.idno}">${code5.name}</option>
					</c:forEach>
				</select>
				<select name="grade">
					<option value="">選擇年級</option>
					<option value="*">所有年級</option>
					<option value="1">1年級</option>
					<option value="2">2年級</option>
					<option value="3">3年級</option>
					<option value="4">4年級</option>
					<option value="5">5年級</option>
				</select>
				<select name="departClass">
					<option value="">選擇校區</option>
					<option value="*">所有班級</option>
					<option value="1">甲</option>
					<option value="2">乙</option>
					<option value="3">丙</option>
					<option value="4">丁</option>
				</select>
				</td>
			</tr>
			<%} %>
			<c:if test="${viewMode!=true}">
			<tr id=addMoreNum3 style=display:inline>
				<td align="left">
				<input value="&nbsp;&nbsp;&nbsp;&nbsp;增加跨選規則"
				type="button" onClick="javascript:AddNumInput3()" class="addTeacherButton"/>
				</td>
			</tr>
			<tr id=delMoreNum3 style=display:none>
				<td align="left">
				<table width="100%">
						<tr>
							<td>
								<div class="modulecontainer filled nomessages">
								<div class="first">
								<span class="first"></span>
								<span class="last"></span>
								</div>
								<div>
								<div>
									<table width="100%">
										<tr>
											<td>
											&nbsp;&nbsp;<img src="images/24-book-green-message.png">
											</td>
											<td align="left" width="100%">
											多餘的欄位請維持未選狀態
											</td>
										</tr>
									</table>
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
			</c:if>
		</table>
		</td>
	</tr>
	<c:if test="${viewMode!=true}">
		<c:if test="${newDtime!=null}">
			<tr>
				<td>
	       		<INPUT type="submit" disabled name="method" value="<bean:message key='ModifyRecord'/>" class="gSubmit">
	       		<INPUT type="submit" name="method" value="<bean:message key='Cancel'/>" class="gCencel">
	       		</td>
	       	</tr>
	       	
		</c:if>
		<c:if test="${newDtime==null}">
	       	<tr height="30">
	       		<td class="fullColorTable" align="center">	       		
	       			<INPUT type="submit" name="method" disabled value="<bean:message key='ModifyRecord'/>" class="gSubmit">	       			
	       			<INPUT type="submit" name="method" value="<bean:message key='Cancel'/>" class="gCancel">
	       		
	       		
	       		</td>
	       	</tr>
		</c:if>
	</c:if>
	
	
	<c:if test="${viewMode==true}">
	<tr>
		<td align="center" class="fullColorTable">
		<c:if test="${newDtime!=null}">
			
	       		<INPUT type="submit" name="method" disabled value="<bean:message key='Modify'/>" class="gSubmit" >
	       		<INPUT type="submit" name="method" disabled value="<bean:message key='Complete'/>" class="gGreen">
		</c:if>
		
		<c:if test="${newDtime==null}">
			<INPUT type="submit" name="method" value="<bean:message key='Cancel'/>" class="gCancel">
       		
       		<c:if test="${readonly==false}">
       			<INPUT type="submit" name="method" disabled value="<bean:message key='Modify'/>" class="gSubmit">
    		</c:if>
		</c:if>

		</c:if>
		</td>
	</tr>
	</c:if>

<!--建立課程 start-->
<c:if test="${createMode==true}">
<c:import url="/pages/course/OpenCourse/create.jsp"/>
</c:if>
		
<!--deleteMode start-->
<c:if test="${deleteMode==true}">
<c:import url="/pages/course/OpenCourse/delete.jsp"/>
</c:if>

</html:form>
</table>
<c:if test="${editMode==true||createMode==true||deleteMode==true}">
	<c:if test="${deleteMode!=true}">
	<%@ include file="/pages/include/Counter.js" %>
	<script>
		function getCheckBox(id){
			this.Id=new String(id);
			var boxValue="";
			if(this.value!=""){
				boxValue="1";
			}
			if(document.getElementById(Id).value=="1"){
				boxValue="0";
			}
			document.getElementById(Id).value = boxValue;
		}
	</script>
	<script>

				var nextHiddenIndex=${dtimeClasSize};
				function AddNumInput(){
	  				var idxStart = nextHiddenIndex;
	  				var idxEnd   = (nextHiddenIndex+1);
	  				var idx;
	  				for  (idx=idxStart; idx<idxEnd; idx++){
	    				document.all["numInput" + idx].style.display = "inline";
	  				}
	  				//document.all["numInput" + idxStart].style.display = "inline";
	  				nextHiddenIndex++;
	  				if (nextHiddenIndex >=9){
	    				document.all.addMoreNum.style.display = "none";
	  				}
  					if (nextHiddenIndex >=${dtimeClasSize}+1){
	    				document.all.delMoreNum.style.display = "inline";
	  				}
				};

				var nextHiddenIndex2=${dtimeTeacherSize};
				function AddNumInput2(){
	  				var idxStart2 = nextHiddenIndex2;
	  				var idxEnd2   = (nextHiddenIndex2+1);
	  				var idx2;
	  				for  (idx2=idxStart2; idx2<idxEnd2; idx2++){
	    				document.all["numInput2" + idx2].style.display = "inline";
	  				}
	  				//document.all["numInput" + idxStart].style.display = "inline";
	  				nextHiddenIndex2++;
	  				if (nextHiddenIndex2 >=9){
	    				document.all.addMoreNum2.style.display = "none";
	  				}
  					if (nextHiddenIndex2 >=${dtimeTeacherSize}+1){
	    				document.all.delMoreNum2.style.display = "inline";
	  				}
				};

				var nextHiddenIndex3=${opencsListSize};
				function AddNumInput3(){
	  				var idxStart3 = nextHiddenIndex3;
	  				var idxEnd3   = (nextHiddenIndex3+1);
	  				var idx3;
	  				for  (idx3=idxStart3; idx3<idxEnd3; idx3++){
	    				document.all["numInput3" + idx3].style.display = "inline";
	  				}
	  				//document.all["numInput" + idxStart].style.display = "inline";
	  				nextHiddenIndex3++;
	  				if (nextHiddenIndex3 >=9){
	    				document.all.addMoreNum3.style.display = "none";
	  				}
  					if (nextHiddenIndex3 >=${opencsListSize}+1){
	    				document.all.delMoreNum3.style.display = "inline";
	  				}
				};

				var nextHiddenIndex4=${dtimeExamListSize};
				function AddNumInput4(){
	  				var idxStart4 = nextHiddenIndex4;
	  				var idxEnd4   = (nextHiddenIndex4+1);
	  				var idx4;
	  				for  (idx4=idxStart4; idx4<idxEnd4; idx4++){
	    				document.all["numInput4" + idx4].style.display = "inline";
	  				}
	  				//document.all["numInput" + idxStart].style.display = "inline";
	  				nextHiddenIndex4++;
	  				if (nextHiddenIndex4 >=9){
	    				document.all.addMoreNum4.style.display = "none";
	  				}
  					if (nextHiddenIndex4 >=${dtimeExamListSize}+1){
	    				document.all.delMoreNum4.style.display = "inline";
	  				}
				};

	</script>
	<script>
		function getFullClass(classNo, className) {

			this.claNo=new String(classNo);
			this.claName=new String(className);

			var classname = "";

			<c:forEach items="${classes}" var="cc">
			if (document.getElementById(claNo).value == "${cc.classNo}"){
			classname="${cc.className}" }
			</c:forEach>

			document.getElementById(claName).value = classname;
		};
  </script>
  <script>
	function mouseCounter(ti){
	var n=parseFloat(0.5);
	var b=parseFloat(document.getElementById(ti).value);
		if(event.button==1){
			document.getElementById(ti).value=n+b;
	}

		if(event.button==2){
			document.getElementById(ti).value=parseFloat(document.getElementById(ti).value)-n;
			if(parseFloat(document.getElementById(ti).value)<1){
			document.getElementById(ti).value=0
			}
		}
	}
  </script>
  <script>
		function getCheckBox(id){
			this.Id=new String(id);
			var boxValue="";
			if(this.value!=""){
				boxValue="1";
			}
			if(document.getElementById(Id).value=="1"){
				boxValue="0";
			}
			document.getElementById(Id).value = boxValue;
		}
  </script>


	</c:if>
</c:if>
<c:import url="include/AjaxUniMod.jsp"/>
<%@ include file="/pages/include/ajaxGetMate.jsp" %> 
