<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="code.entity.Student" %>
<%@include file="../layouts/header.jsp" %>
<div class="container">

    <%@include file="../layouts/sidenav.jsp" %>
        <% Student student  = (Student) request.getAttribute("student"); %>

    <div class="main_contents">
        <div id="sub_content">
            <form:form method="post" action="updateStudentAction" modelAttribute="register" id="form-ad" enctype="multipart/form-data">

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
                <div class="col-md-5" class="input-control">
                    <form:input path="student.name" type="text" class="form-control"  id="name"
                                value="<%=student.getName()%>"></form:input>
                    <div class="error"></div>
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
                    <div class="col-md-5" class="input-control">
                        <div id="error" style="color: red;"></div>
                        <form:input path="student.phone" type="text"  class="form-control" id="phone"
                                    placldehoer="Please enter your phone number" value="<%=student.getPhone()%>" maxlength="11"></form:input>
                        <div class="error"></div>
                    </div>
                </div>

                <div class="row mb-4">
                    <div class="col-md-2"></div>
                    <label for="file" class="col-md-2 col-form-label">Photo</label>
                    <div class="col-md-4">
                        <form:input type="file" path="student.file" class="form-control" name="file" id="file"
                                    accept="image/*" multiple="true"></form:input>
                    </div>
                </div>

                <div class="row mb-4">
                    <div class="col-md-4"></div>

                    <div class="col-md-4">
                        <button type="submit" id="submit-btn" class="btn btn-success">
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
        <script>

            document.addEventListener('DOMContentLoaded', function() {
                const form = document.getElementById('form-ad');
                const username = document.getElementById('name');
                const phone = document.getElementById('phone');

                form.addEventListener('submit', function(event){
                    if(!validateInputs()){
                        event.preventDefault();
                    }
                });

                const setError = function (element, message) {
                    const inputControl = element.parentElement;
                    const errorDisplay = inputControl.querySelector('.error');

                    errorDisplay.innerText = message;
                    inputControl.classList.add('error');
                    errorDisplay.classList.add('alert', 'alert-danger');
                    errorDisplay.classList.add('show');
                };

                const setSuccess = function (element) {
                    const inputControl = element.parentElement;
                    const errorDisplay = inputControl.querySelector('.error');

                    errorDisplay.innerText = '';
                    inputControl.classList.remove('error');
                    errorDisplay.classList.remove('alert', 'alert-danger');
                    errorDisplay.classList.remove('show');
                };

                const validateInputs = function () {
                    const usernameValue = username.value.trim();
                    const phoneValue = phone.value.trim();

                    if (usernameValue === '') {
                        setError(username, 'Username is required');
                    } else {
                        setSuccess(username);
                    }

                    if (phoneValue === '') {
                        setError(phone, 'Phone is required');
                    } else if (!/^\d{11}$/.test(phoneValue)) {
                        setError(phone, 'Phone must be 11 digits long');
                    } else {
                        setSuccess(phone);
                    }

                    return !document.querySelectorAll('.error.show').length;
                };
            });
        </script>
<%@include file="../layouts/footer.jsp" %>