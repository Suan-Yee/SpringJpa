<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../layouts/header.jsp" %>
<div class="login-page">
    <div class="card text-center" style="width: 300px;">
        <div class="card-header h5 text-white bg-success">Password Reset</div>
        <div class="card-body px-5">
            <p style="color: #328f8a;" class="card-text py-2">
                Enter your email address, and we'll send you an email with instructions to reset your password.
            </p>
            <c:if test="${not empty error}">
                <p>${error}</p>
            </c:if>

            <form:form action="/resets" modelAttribute="user" class="login-form" method="post">
                <div class="form-outline">
                    <form:input path="email" placeholder="Please enter your email" type="email" class="form-control my-3"/>
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