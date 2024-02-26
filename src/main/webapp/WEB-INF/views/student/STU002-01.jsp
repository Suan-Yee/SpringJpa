<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="code.entity.Student" %>
<%@ page import="code.entity.Course" %>
<%@ page import="java.util.List" %>
<%@include file="../layouts/header.jsp" %>
<!-- <div id="testsidebar">Hello World </div> -->
<div class="container">
    <%@include file="../layouts/sidenav.jsp" %>
        <% Student student  = (Student) request.getAttribute("student"); %>

        <%List<Course> courses = (List<Course>) request.getAttribute("courses"); %>
        <% ArrayList<String> n_course = (ArrayList<String>) request.getAttribute("c_name"); %>

    <div class="main_contents">
        <div id="sub_content">
            <%--@elvariable id="register" type=""--%>
            <form:form method="post" action="updateStudentAction" modelAttribute="register">

            <h2 class="col-md-6 offset-md-2 mb-5 mt-4">Student Update</h2>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label path="id" for="studentID" class="col-md-2 col-form-label">Student
                    ID</label>
                <div class="col-md-5">
                    <input type="text" class="form-control"
                           value="STR00<%=student.getId()%>" id="studentID" disabled/>

                </div>
                <input type="hidden" name="hide" value="<%=student.getId()%>"/>
                <input type="hidden" name="id" value="" />
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <form:label path="student.name" for="name" class="col-md-2 col-form-label">Name</form:label>
                <div class="col-md-5">
                    <form:input path="student.name" type="text" class="form-control"  id="names"
                                value="<%=student.getName()%>"></form:input>
                </div>
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <form:label path="student.dob" for="dob" class="col-md-2 col-form-label">DOB</form:label>
                <div class="col-md-5">
                    <form:input path="student.dob" type="date" name="dob" class="form-control" id="dob"
                                value="<%=student.getDob()%>"></form:input>
                </div>
            </div>

            <fieldset class="row mb-4">
                <div class="col-md-2"></div>
                <legend class="col-form-label col-md-2 pt-0">Gender</legend>
                <div class="col-md-5">
                    <div class="form-check-inline">
                        <input class="form-check-input" type="radio" name="gender"
                               id="gridRadios1" value="Male"
                                <%if (student.getGender() != null && student.getGender().equalsIgnoreCase("Male")) {%>
                               checked <%}%>> <label class="form-check-label"
                                                     for="gridRadios1"> Male </label>
                    </div>
                    <div class="form-check-inline">
                        <input class="form-check-input" type="radio" name="gender"
                               id="gridRadios2" value="Female"
                                <%if (student.getGender() != null && student.getGender().equalsIgnoreCase("Female")) {%>
                               checked <%}%>> <label class="form-check-label"
                                                     for="gridRadios2"> Female </label>
                    </div>
                </div>
            </fieldset>

            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="education" class="col-md-2 col-form-label">Education</label>
                <div class="col-md-5">
                    <select class="form-select" aria-label="Education" id="education"
                            name="education">
                        <option value="Bachelor of Information Technology"
                                <%if (student.getEducation() != null && student.getEducation().equals("Bachelor of Information Technology")) {%>
                                selected <%}%>>Bachelor of Information Technology</option>
                        <option value="Diploma in IT"
                                <%if (student.getEducation() != null && student.getEducation().equals("Diploma in IT")) {%>
                                selected <%}%>>Diploma in IT</option>
                        <option value="Bachelor of Computer Science"
                                <%if (student.getEducation() != null && student.getEducation().equals("Bachelor of Computer Science")) {%>
                                selected <%}%>>Bachelor of Computer Science</option>
                    </select>
                </div>

                <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

                <fieldset class="row mb-5">
                    <div class="col-md-2"></div>
                    <legend class="col-form-label col-md-2 pt-0">Attend</legend>
                    <div class="col-md-5">
                        <c:forEach var="course" items="${courses}">
                            <div class="form-check-inline col-md-2">
                                <input class="form-check-input" type="checkbox" name="course"
                                       id="gridRadios${course.id}" value="${course.name}"
                                       <c:if test="${c_name.contains(course.name)}">checked</c:if>>
                                <label class="form-check-label" for="gridRadios${course.id}">
                                        ${course.name}
                                </label>
                                </div>
                        </c:forEach>
                    </div>
                </fieldset>

                <div class="row mb-4">
                    <div class="col-md-2"></div>

                    <form:label path="student.phone" for="phone" class="col-md-2 col-form-label">Phone</form:label>
                    <div class="col-md-5">
                        <div id="error" style="color: red;"></div>
                        <form:input path="student.phone" type="text"  class="form-control" id="phone"
                                    placeholder="Please enter your phone number" value="<%=student.getPhone()%>" maxlength="11"></form:input>
                    </div>
                </div>

                <div class="row mb-4">
                    <div class="col-md-4"></div>

                    <div class="col-md-4">
                        <button type="submit" class="btn btn-success"
                                data-bs-toggle="modal" data-bs-target="#exampleModal">
                            Update</button>

                        <button type="button" class="btn btn-danger  "
                                onclick="location.href = 'student_details.jsp';">Cancel
                        </button>

                    </div>

                </div>


                <!--Modal-->

                </form:form>
            </div>
        </div>
    </div>
<%@include file="../layouts/footer.jsp" %>