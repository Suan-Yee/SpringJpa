<%@ page import="code.entity.User" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../layouts/header.jsp" %>
<div class="container">

    <% User log_user = (User) session.getAttribute("valid_user");%>

    <% if(log_user != null){%>
    <%@ include file="../layouts/sidenav.jsp"%>
    <%}%>

    <div class="main_contents">
        <div id="sub_content">
            <form:form method="post" modelAttribute="user" action="addUser">

                <h2 class="col-md-6 offset-md-2 mb-5 mt-4">User Registration</h2>
                <h1 class="col-md-6 offset-md-2 mb-5 mt-4" style="color:red"></h1>
                <div class="row mb-4">
                    <div class="col-md-2"></div>
                    <form:label for="name" path="username" class="col-md-2 col-form-label" >Name</form:label>
                    <div class="col-md-4">
                        <form:input type="text" path="username" class="form-control" id="name" placeholder="Please enter your name"></form:input>
                    </div>
                </div>
                <div class="row mb-4">
                    <div class="col-md-2"></div>
                    <form:label path="email" for="email" class="col-md-2 col-form-label">Email</form:label>
                    <div class="col-md-4">
                        <form:input type="email" path="email" class="form-control" id="email" placeholder="Please enter your email"></form:input>
                    </div>
                </div>
                <div class="row mb-4">
                    <div class="col-md-2"></div>
                    <form:label path="password" for="password" class="col-md-2 col-form-label">Password</form:label>
                    <div class="col-md-4">
                        <form:input path="password" type="password" name="password" class="form-control" id="password" placeholder="Please enter your password"></form:input>
                    </div>
                </div>
                <div class="row mb-4">
                    <div class="col-md-2"></div>
                    <label  for="confirmPassword"  class="col-md-2 col-form-label">Confirm Password</label>
                    <div class="col-md-4">
                        <input  type="password" name="confirmPass" class="form-control" id="confirmPassword" placeholder="Please enter your confirmPassword">
                    </div>
                </div>
                <!-- <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="userRole" class="col-md-2 col-form-label">User Role</label>
                <div class="col-md-4">
                <select class="form-select" name="role" aria-label="User Role" id="userRole">
                <option selected value="Admin">Admin</option>
                <option value="User">User</option>
                </select>
                </div>
                </div> -->
                <div class="row mb-4">
                    <div class="col-md-4"></div>
                    <div class="col-md-6">
                        <button type="submit" class="btn btn-secondary col-md-2" data-bs-toggle="modal" data-bs-target="#exampleModal">Create</button>
                    </div>
                </div>
                <%if(log_user != null){ %>
                <div class="row mb-4">
                    <div class="col-md-4"></div>
                    <div class="col-md-6">
                        <a href="userList" class="btn btn-secondary col-md-2">Back</a>
                    </div>
                </div>
                <%}%>

            </form:form>
        </div>
    </div>
    <dialog id="errorDialog">
        <div>
            <p>Error: ${error}</p>
            <button id="closeDialogBtn">Close</button>
        </div>
    </dialog>
</div>

<script>

    if ('${error}' !== null && '${error}' !== '') {
        const errorDialog = document.getElementById('errorDialog');
        errorDialog.showModal();
        const closeDialogBtn = document.getElementById('closeDialogBtn');
        closeDialogBtn.addEventListener('click', () => {
            errorDialog.close();
        });
    }
    function validateForm() {
        let name = document.getElementById('name').value;
        let email = document.getElementById('email').value;
        let password = document.getElementById('password').value;
        let confirmPassword = document.getElementById('confirmPassword').value;

        if (name === '' || email === '' || password === '' || confirmPassword === '') {
            alert('Please fill in all fields');
            return false;
        }

        return true;
    }
</script>
<%@ include file="../layouts/footer.jsp" %>
