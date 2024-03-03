<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../layouts/header.jsp" %>
<div class="login-page">
    <div class="card text-center" style="width: 300px;">
        <div class="card-header h5 text-white bg-success">Password Reset</div>
        <div class="card-body px-5">
            <h1 style="color: #328f8a;" class="card-text py-2">
              Enter Your new Password.
            </h1>
            <form:form action="/resets/${token}" modelAttribute="user" class="login-form" method="post">
                <div class="form-outline">
                    <form:input path="email" value="${result.email}" disabled="true" placeholder="Please enter your email" type="email" class="form-control my-3"/>
                    <c:if test="${not empty error}">
                        <h2 style="color: red">${error}</h2>
                    </c:if>
                    <form:input path="password" placeholder="Please enter your Password" type="password" class="form-control my-3"/>

                    <input  placeholder="Confirm Password" type="password" name="confirmPass" class="form-control my-3"/>
                </div>

                <button type="submit" class="btn btn-success w-100">Reset password</button>
            </form:form>

            <div class="d-flex justify-content-between mt-4">
                <a href="/">Login</a>
                <a href="userRegister">Register</a>
            </div>
        </div>
    </div>
</div>


<%@ include file="../layouts/footer.jsp" %>