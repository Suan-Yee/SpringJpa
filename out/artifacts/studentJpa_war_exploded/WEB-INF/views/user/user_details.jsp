<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.util.List" %>
<%@ page import="code.entity.User" %>


<%@ include file="../layouts/header.jsp" %>
<div class="container">
    <%@ include file="../layouts/sidenav.jsp" %>

    <% User log_user = (User)session.getAttribute("valid_user"); %>

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
                <%
                    List<User> users = (List<User>) request.getAttribute("users");
                    int count = 1;
                    if (users != null) {
                        for (User user_list : users) {
                %>
                <tr>
                    <td>
                        <% if (user_list.getEmail().equalsIgnoreCase(log_user.getEmail())) { %>
                        <i class="fa-solid fa-user"></i>
                        <% }else{ %>
                        <%=count++%>
                        <% } %>
                    </td>
                    <td><%=String.format("USR%03d",user_list.getId())%></td>
                    <td>
                        <%= user_list.getUsername() %>
                    </td>

                    <%--<td><%= user_list.getUserRoles() %></td>
                    <td><a href="updateUser?userId=<%=user_list.getId()%>">
                        <button type="button" class="btn btn-success mb-3 me-3"
                                <% if (log_user.getUserRoles().equalsIgnoreCase("ADMIN")) {
                                    if (user_list.getRole().equalsIgnoreCase("ADMIN") && !log_user.getEmail().equals(user_list.getEmail())) { %>
                                disabled
                                <% }
                                } else if (log_user.getUserRoles().equalsIgnoreCase("USER")) {
                                    if (!log_user.getEmail().equalsIgnoreCase(user_list.getEmail()) && (user_list.getRole().equalsIgnoreCase("ADMIN") || user_list.getRole().equalsIgnoreCase("USER"))) { %>
                                disabled
                                <% }
                                } %>>
                            Update
                        </button>
                    </a></td>
                    <td>--%>
                        <%--<a href="deleteUser?userId=<%=user_list.getId()%>">--%>
                        <%--<button type="button" class="btn btn-danger mb-3"
                                data-bs-toggle="modal" data-bs-target="#exampleModal" onclick="setUserId(<%=user_list.getId()%>)"
                                <% if (log_user.getRole().equalsIgnoreCase("ADMIN")) {
                                    if (user_list.getRole().equalsIgnoreCase("ADMIN") && !log_user.getEmail().equals(user_list.getEmail())) { %>
                                disabled
                                <% }
                                } else if (log_user.getRole().equalsIgnoreCase("USER")) {
                                    if (!log_user.getEmail().equalsIgnoreCase(user_list.getEmail()) && (user_list.getRole().equalsIgnoreCase("ADMIN") || user_list.getRole().equalsIgnoreCase("USER"))) { %>
                                disabled
                                <% }
                                } %>>
                            DELETE
                        </button>--%>
                    </td>
                </tr>
                <%
                        }
                    }
                %>
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
    let userIdToDelete;

    function setUserId(userId) {
        userIdToDelete = userId;
    }

    document.getElementById('confirmDeleteBtn').addEventListener('click', function () {
        if (userIdToDelete) {
            let deleteUrl = 'deleteUser?userId=' + userIdToDelete;
            window.location.href = deleteUrl;
        }
    });
</script>

<%@ include file="../layouts/footer.jsp" %>
