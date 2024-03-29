<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="code.entity.User" %>


<%@ include file="../layouts/header.jsp" %>
<div class="container">
    <%@ include file="../layouts/sidenav.jsp" %>

    <% User log_user = (User) session.getAttribute("valid_user");%>

    <% if(log_user == null){
        response.sendRedirect("/");
    }%>

    <div class="main_contents">
        <div id="sub_content">
            <form:form method="get" action="searchUser" class="row g-3 mt-3 ms-2">
                <div class="col-auto">
                    <label for="staticEmail2" class="visually-hidden">User Id</label>
                    <input type="text" class="form-control" id="staticEmail2" name="id" placeholder="User ID">
                </div>
                <div class="col-auto">
                    <label for="inputPassword2" class="visually-hidden">User Name</label>
                    <input type="text" class="form-control" id="inputPassword2" name="name" placeholder="User Name">
                </div>

                <div class="col-auto">
                    <button type="submit" class="btn btn-primary mb-3">Search</button>
                </div>

                <div class="col-auto">
                    <a href="userRegister" class="btn btn-secondary">Add</a>
                </div>

                <div class="col-auto">
                    <a href="userList" class="btn btn-danger">Reset</a>
                </div>

            </form:form>

            <table class="table table-success table-striped" id="stduentTable">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">User ID</th>
                    <th scope="col">User Name</th>
                    <th scope="col">Role</th>
                    <th scope="col">Update</th>
                    <th scope="col">Delete</th>
                </tr>
                </thead>

                <tbody>
                <c:choose>
                    <c:when test="${empty errors}">
                <%
                    List<User> users = (List<User>) request.getAttribute("users");
                    int count = 1;
                    if (users != null) {
                        for (User user_list : users) {
                %>
                <tr>
                    <% String error = (String) request.getAttribute("errors");%>
                    <% if(error != null ) { %>
                    <td><%=error%></td>
                    <%}%>

                    <td>
                        <% if ( log_user != null && user_list.getEmail().equalsIgnoreCase(log_user.getEmail())) { %>
                        <i class="fa-solid fa-user"></i>
                        <% }else{ %>
                        <%=count++%>
                        <% } %>
                    </td>
                    <td><%=String.format("USR%03d",user_list.getId())%></td>
                    <td>
                        <%= user_list.getUsername() %>
                    </td>

                    <td><%= user_list.getRole().getName() %></td>
                    <td><a href="updateUser?userId=<%=user_list.getId()%>">
                        <button type="button" class="btn btn-success mb-3 me-3"
                                <% if (log_user != null && log_user.getRole().getName().equalsIgnoreCase("ADMIN")) {
                                    if (user_list.getRole().getName().equalsIgnoreCase("ADMIN") && !log_user.getEmail().equals(user_list.getEmail())) { %>
                                disabled
                                <% }
                                } else if (log_user != null && log_user.getRole().getName().equalsIgnoreCase("USER")) {
                                    if (!log_user.getEmail().equalsIgnoreCase(user_list.getEmail()) && (user_list.getRole().getName().equalsIgnoreCase("ADMIN") || user_list.getRole().getName().equalsIgnoreCase("USER"))) { %>
                                disabled
                                <% }
                                } %>>
                            Update
                        </button>
                    </a></td>
                    <td>
                        <button type="button" class="btn btn-danger mb-3"
                                data-bs-toggle="modal" data-bs-target="#exampleModal" onclick="setStudentId(<%=user_list.getId()%>)"
                                <% if (log_user != null && log_user.getRole().getName().equalsIgnoreCase("ADMIN")) {
                                    if (user_list.getRole().getName().equalsIgnoreCase("ADMIN") && !log_user.getEmail().equals(user_list.getEmail())) { %>
                                disabled
                                <% }
                                } else if (log_user != null && log_user.getRole().getName().equalsIgnoreCase("USER")) {
                                    if (!log_user.getEmail().equalsIgnoreCase(user_list.getEmail()) && (user_list.getRole().getName().equalsIgnoreCase("ADMIN") || user_list.getRole().getName().equalsIgnoreCase("USER"))) { %>
                                disabled
                                <% }
                                } %>>
                            DELETE
                        </button>
                    </td>

                </tr>
                <%
                        }
                    }
                %>
    </c:when>
    <c:otherwise>
        <c:if test="${not empty errors}">
            <tr>
                <td colspan="7">${errors}</td>
            </tr>
        </c:if>

    </c:otherwise>
</c:choose>
                </tbody>
            </table>

            <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">User Deletion</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <h5 style="color: rgb(127, 209, 131);">Are you sure you want to delete this user?</h5>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-success" id="confirmDeleteBtn">Ok</button>
                        </div>
                    </div>
                </div>
            </div>


        </div>
    </div>
</div>
<script>
    let studentIdToDelete;

    function setStudentId(studentId) {
        studentIdToDelete = studentId;
    }
        document.getElementById('confirmDeleteBtn').addEventListener('click', function () {
            if (studentIdToDelete) {
                let deleteUrl = 'deleteUser?userId=' + studentIdToDelete;
                window.location.href = deleteUrl;
            }
        });
</script>

<%@ include file="../layouts/footer.jsp" %>
