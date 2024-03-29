<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <link rel="stylesheet" href="../../resources/test.css">
  <title> Student Registration Login </title>
  <style>
    .error {
      color: #ff0000;
    }

    .alert{
      width:50%;
      text-align:center;
      margin-left:70px;

    }
  </style>
</head>
<body class="login-page-body">

<div class="login-page">
  <div class="form">
    <div class="login">
      <div class="login-header">
        <h1>Enter OTP Code!</h1>
      </div>
    </div>
    <c:if test="${not empty error}">
    <p class="error alert">${error}</p>
    </c:if>
    <c:if test="${not empty gmail}">
      <p style="color: #328f8a" class="alert">${gmail}</p>
    </c:if>
    <form:form action="/confirm" modelAttribute="otp" class="login-form" method="post">
      <form:input  type="text" path="otpCode" placeholder="Please enter the OTP code send to your email"/>
      <button type="submit">Enter</button>

    </form:form>
  </div>
</div>
</body>
<style>
  .tito{
    display:flex;
    justify-content: space-between;
    margin-bottom: 5px;
    font-size: 12px;
  }
  a {
    text-decoration: none;
    color: #424242;
    transition: color 0.3s ease;
  }
  a:hover {
    color: #E53935;
  }
</style>

</html>
