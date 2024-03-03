<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../layouts/header.jsp" %>
<div class="login-page">
    <div class="form">
        <div class="login">
            <div class="login-header">
                <c:if test="${not empty error}">
                    <p style="color: red">${error}</p>
                </c:if>
                <form:form action="changeEmailAndPassword" modelAttribute="user" class="login-form" method="post">
                    <form:input  type="text" path="email" placeholder="Please enter your new email"/>
                    <form:input type="password" path="password" placeholder="Please enter your new Password"/>
                    <input name="confirmPass" type="hidden" placeholder="Confirm Password"/>

                    <button type="submit">Change</button>

                </form:form>
            </div>
        </div>
    </div>
</div>

<%@ include file="../layouts/footer.jsp" %>
