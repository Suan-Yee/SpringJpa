<%@ page import="code.entity.Course" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="../layouts/header.jsp" %>
<div class="container">
    <%@include file="../layouts/sidenav.jsp" %>

    <% User log_user = (User) session.getAttribute("valid_user");%>
    <div class="main_contents">
        <div id="sub_content">
            <form:form method="get" action="searchCourse" class="row g-3 mt-3 ms-2">
                <div class="col-auto">
                    <label for="staticEmail2"  class="visually-hidden">ID</label>
                    <input type="text" name="id" class="form-control" id="staticEmail2"  placeholder="Course ID">
                </div>
                <div class="col-auto">
                    <label  for="name" class="visually-hidden">Name</label>
                    <input name="name" type="text" class="form-control" id="name"  placeholder="Course Name">
                </div>

                <div class="col-auto">
                    <button type="submit" class="btn btn-primary mb-3 me-3">
                        Search
                    </button>
                </div>

                <div class="col-auto">
                    <a href="courseRegister" class="btn btn-secondary">Add</a>
                </div>

                <div class="col-auto">
                    <a href="courseDetails" class="btn btn-danger">Reset</a>
                </div>

            </form:form>

            <table class="table table-success table-striped" id="stduentTable">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col"> Id</th>
                    <th scope="col"> Name</th>
                    <th scope="col">Description</th>
                    <th scope="col">Instructor</th>
                  <%--  <% if(!log_user.getRole().equalsIgnoreCase("user")){ %>--%>
                    <th scope="col">Status</th>
                    <th scope="col">Delete</th>
                   <%-- <% } %>--%>
                </tr>
                </thead>

                <tbody>
                <%
                    List<Course> courses = (List<Course>) request.getAttribute("courses");
                    int count = 1;
                    if (courses != null) {
                        for (Course course : courses) {
                %>
                <tr>
                    <td><%=count++%></td>
                    <td><%=String.format("CUR%03d",course.getId())%></td>
                    <td><%= course.getName() %></td>
                    <td><%= course.getDescription()%></td>
                    <td>
                    <%if(course.getInstructor() != null){ %>
                    <%= course.getInstructor().getName()%>
                    <% } %></td>
                    <td>
                        <a href="courseStatus?courseId=<%=course.getId()%>">
                         <span class="<% if (course.getStatus() != null && course.getStatus().equalsIgnoreCase("pending")) { %>badge text-bg-warning<% } else { %>badge text-bg-info<% } %>">
                          <%= course.getStatus() != null ? course.getStatus() : "N/A" %>
                        </span>
                        </a>
                    </td>
                    <td>
                        <a href="courseDelete?courseId=<%=course.getId()%>">
                            <i class="fa-solid fa-trash"></i>
                        </a>
                    </td>
                </tr>
                <%
                        }
                    }
                %>
                </tbody>
            </table>
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

<%@include file="../layouts/footer.jsp" %>