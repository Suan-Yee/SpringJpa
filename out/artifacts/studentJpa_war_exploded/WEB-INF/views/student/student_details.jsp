<%@ page import="code.entity.Student" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="../layouts/header.jsp" %>

<div class="container">
    <%@include file="../layouts/sidenav.jsp" %>

    <div class="main_contents">
        <div id="sub_content">
            <form:form method="get" action="searchStudent" class="row g-3 mt-3 ms-2">
                <div class="col-auto">
                    <label for="staticEmail2" class="visually-hidden">studentID</label>
                    <input type="text" name="id" class="form-control" id="staticEmail2"
                           placeholder="Student ID">
                </div>
                <div class="col-auto">
                    <label for="name" class="visually-hidden">studentName</label>
                    <input type="text" name="name" class="form-control"
                           id="name" placeholder="Student Name">
                </div>
                <div class="col-auto">
                    <label for="inputPassword2" class="visually-hidden">Course</label>
                    <input type="text" name="course" class="form-control"
                           id="inputPassword2" placeholder="Course">
                </div>
                <div class="col-auto">
                    <button type="submit" class="btn btn-success mb-3">Search</button>
                </div>
                <div class="col-auto">
                    <a href="studentList" class="btn btn-danger">Reset</a>
                </div>
            </form:form>

            <div>
                <table class="table table-success table-striped" id="stduentTable">
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Student ID</th>
                        <th scope="col">Name</th>
                        <th scope="col">Attend</th>
                        <th scope="col">Update</th>
                        <th scope="col">Delete</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        List<Student> studentList = (List<Student>)  request.getAttribute("students");
                        int count = 1;
                        if(studentList != null){
                            for(Student student : studentList){
                    %>
                    <tr>
                        <td><%=count++%></td>
                        <td><%= String.format("STR%03d", student.getId()) %></td>
                        <td><%=student.getName()%></td>
                        <td></td>
                        <td><a href="updateStudent?studentId=<%=student.getId()%>">
                            <button type="button" class="btn btn-success mb-3 me-3">Update</button>
                        </a></td>
                        <td>
                            <%--<a href="deleteStudent?studentId=<%=student.getId()%>">--%>
                            <button type="button" class="btn btn-danger mb-3"
                                    data-bs-toggle="modal" data-bs-target="#exampleModal" onclick="setStudentId(<%=student.getId()%>)"
                            >Delete</button>
                            <%--</a>--%> </td>
                    </tr>
                    <%
                            }
                        }
                    %>

                    </tbody>
                </table>

                <div class="modal fade" id="exampleModal" tabindex="-1"
                     aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLabel">Student
                                    Deletion</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                        aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <h5 style="color: rgb(127, 209, 131);">Are you sure you
                                    want to delete this student?</h5>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-primary"
                                        id="confirmDeleteBtn">Sure</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    let studentIdToDelete;

    function setStudentId(studentId){
        studentIdToDelete = studentId;
    }
    document.getElementById('confirmDeleteBtn').addEventListener('click', function () {
        if (studentIdToDelete) {
            let deleteUrl = 'deleteStudent?studentId=' + studentIdToDelete;
            window.location.href = deleteUrl;
        }
    });
</script>
<%@include file="../layouts/footer.jsp" %>