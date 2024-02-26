<%@ page import="java.util.List" %>
<%@ page import="code.entity.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
                        <th scope="col">Gender</th>
                        <th scope="col">Phone</th>
                        <th scope="col">Update</th>
                        <th scope="col">Delete</th>
                        <th scope="col">Details</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:if test="${not empty students}">
                        <c:forEach items="${students}" var="student" varStatus="loopStatus">
                            <tr>
                                <td>${loopStatus.index + 1}</td>
                                <fmt:formatNumber type="number" pattern="000"
                                                  value="${student.id}" var="formattedId" />
                                <td>STR${formattedId}</td>
                                <td><c:out value="${student.name}" /></td>

                                <td>
                                <c:choose>
                                <c:when test="${student.gender eq 'MALE'}">
                                    <i class="fa-solid fa-mars"></i>
                                    <c:out value="${student.gender}" />
                                </c:when>
                                <c:otherwise>
                                    <i class="fa-solid fa-venus"></i>
                                    <c:out value="${student.gender}" />
                                </c:otherwise>
                                </c:choose>
                                </td>

                                <td><c:out value="${student.phone}" /></td>
                                <td>
                                    <a href="updateStudent?studentId=${student.id}">
                                        <button type="button" class="btn btn-success mb-3 me-3">
                                            <i class="fa-solid fa-pen-to-square"></i>
                                            Update</button>
                                    </a>
                                </td>
                                <td>
                                    <button type="button" class="btn btn-danger mb-3"
                                            data-bs-toggle="modal" data-bs-target="#exampleModal" onclick="setStudentId(${student.id})">
                                        <i class="fa-solid fa-trash"></i>
                                        Delete
                                    </button>
                                </td>
                                <td>
                                    <a href="studentDetails?studentId=${student.id}">
                                        <i class="fa-solid fa-bars" style="font-size: 24px; color: black;"></i>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>

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