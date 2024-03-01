<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../layouts/header.jsp" %>
<div class="login-page">
  <div class="form">
    <div class="login">
      <div class="login-header">
        <c:if test="${ not empty error}">
          <p>${error}</p>
        </c:if>
        <form:form action="changeEmailAction" modelAttribute="user" class="login-form" method="post">

          <form:input  type="text" path="email" placeholder="Please enter your email"/>
          <form:input type="password" path="password" placeholder="Please enter your Password"/>

          <button type="submit">Submit</button>

        </form:form>
      </div>
    </div>
  </div>
</div>

<%@ include file="../layouts/footer.jsp" %>