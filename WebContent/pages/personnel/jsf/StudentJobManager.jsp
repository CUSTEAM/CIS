<%@taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<html>
	<body>
		<f:view>
			<h:form>
				<h3>
					請輸入您的名稱
				</h3>
            名稱: <h:inputText value="#{StudentJobManager.studentNo}" />
				<p>
					<h:commandButton value="送出" action="StudentJobManager" />
			</h:form>
			
			
			<h:outputText value="#{StudentJobManager.studentNo}" /> 您好！
        <h3>
				歡迎使用 JavaServer Faces！
			</h3>
		</f:view>
	</body>
</html>