<%@ page import="code.entity.User" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../layouts/header.jsp"/>

<!-- <div id="testsidebar">Hello World </div> -->
<div class="container">
    <jsp:include page="../layouts/sidenav.jsp"/>
            <% User log_user = (User) session.getAttribute("valid_user");%>

            <% if(log_user == null){
                response.sendRedirect("/");
            }%>
    <div class="main_contents">
        <div id="sub_content">
            <form:form method="post" modelAttribute="register" action="addStudent" id="form-ad" enctype="multipart/form-data">
                <h2 class="col-md-6 offset-md-2 mb-5 mt-4">Student Registration</h2>
                <div class="row mb-4">
                    <div class="col-md-2"></div>
                    <form:label for="username" path="student.name"  class="col-md-2 col-form-label">Name</form:label>
                    <div class="col-md-5" class="input-control">
                        <form:input type="text" path="student.name" class="form-control"
                                    placeholder="Please Enter your name" id="username"></form:input>
                        <div class="error" >

                        </div>
                    </div>
                </div>
                <div class="row mb-4">
                    <div class="col-md-2"></div>
                    <form:label for="dob" path="student.dob" class="col-md-2 col-form-label">DOB</form:label>
                    <div class="col-md-5" class="input-control">
                        <form:input type="date" path="student.dob"  name="dob" class="form-control" id="dob"
                                    placeholder="YYYY-MM-DD"></form:input>
                        <div class="error"></div>
                    </div>
                </div>

                <fieldset class="row mb-4">
                    <div class="col-md-2"></div>
                    <legend class="col-form-label col-md-2 pt-0">Gender</legend>
                    <div class="col-md-5">
                        <div class="form-check-inline" class="input-control">
                            <form:label for="gender" path="student.gender">Gender</form:label></td> <td>
                                <form:radiobutton id="gender" path="student.gender" value="MALE" label="Male" />
                                <form:radiobutton id="gender" path="student.gender" value="FEMALE" label="Female" />
                                    <div class="error"></div>
                        </div>
                    </div>
                </fieldset>

                <div class="row mb-4">
                    <div class="col-md-2"></div>

                    <form:label path="student.phone" for="phone" class="col-md-2 col-form-label">Phone</form:label>
                    <div class="col-md-5" class="input-control">
                        <form:input path="student.phone" type="text"  class="form-control" id="phone"
                                    placeholder="Please enter your phone number" maxlength="11"></form:input>
                        <div class="error"></div>
                    </div>
                </div>

                <div class="row mb-4">
                    <div class="col-md-2"></div>
                    <form:label path="student.education"  for="education" class="col-md-2 col-form-label">Education</form:label>
                    <div class="col-md-5" class="input-control">
                        <form:select path="student.education" class="form-select" aria-label="Education" id="education">
                            <form:option id="education" value="Bachelor of Information Technology">Bachelor of Information Technology</form:option>
                            <form:option id="education" value="Diploma in IT">Diploma in IT</form:option>
                            <form:option id="education" value="Bachelor of Computer Science">Bachelor of Computer Science</form:option>
                        </form:select>
                        <div class="error"></div>
                    </div>
                </div>
                <fieldset class="row mb-5">
                    <div class="col-md-2"></div>
                    <legend class="col-form-label col-md-2 pt-0">Attend</legend>
                    <div class="col-md-5">
                        <c:forEach items="${courses}" var="course">
                        <div class="form-check-inline col-md-2">
                            <input class="form-check-label" type="checkbox" name="courses" value="${course.id}" />
                            <label class="form-check-label" for="gridRadios${course.id}">
                                    ${course.name}
                            </label>
                        </div>
                            </c:forEach>
                            <div class="error"></div>
                    </div>
                </fieldset>


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
                        <button type="button" class="btn btn-danger ">Reset</button>
                        <button type="submit" class="btn btn-secondary col-md-2"
                                >
                            Add</button>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
        <script>
            document.addEventListener('DOMContentLoaded', function() {
                const form = document.getElementById('form-ad');
                const username = document.getElementById('username');
                const phone = document.getElementById('phone');
                const dob = document.getElementById('dob');

                // form.addEventListener('submit', e => {
                //     e.preventDefault();
                //     const isValid = validateInputs();
                //     if (isValid) {
                //         console.log('Validation passed');
                //         form.submit();
                //     }
                // });

                form.addEventListener('submit', function (event) {
                    if (!validateInputs()) {
                        event.preventDefault();
                    }
                });

                const setError = (element, message) => {
                    const inputControl = element.parentElement;
                    const errorDisplay = inputControl.querySelector('.error');

                    errorDisplay.innerText = message;
                    inputControl.classList.add('error');
                    inputControl.classList.remove('success');
                    errorDisplay.classList.add('alert', 'alert-danger');
                    errorDisplay.classList.add('show');
                };

                const setSuccess = element => {
                    const inputControl = element.parentElement;
                    const errorDisplay = inputControl.querySelector('.error');

                    errorDisplay.innerText = '';
                    inputControl.classList.add('success');
                    inputControl.classList.remove('error');
                    errorDisplay.classList.remove('show');
                    errorDisplay.classList.remove('alert', 'alert-danger');
                };

                const validateInputs = () => {
                    const usernameValue = username.value.trim();
                    const phoneValue = phone.value.trim();
                    const dobValue = new Date(dob.value);
                    const genderValue = document.querySelectorAll('input[name="student.gender"]:checked').length > 0 ? document.querySelectorAll('input[name="student.gender"]:checked')[0].value : '';

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
                    if (!genderValue) {
                        setError(document.querySelector('input[name="student.gender"]'), 'Gender is required');
                    } else {
                        setSuccess(document.querySelector('input[name="student.gender"]'));
                    }

                    const now = new Date();
                    if (dobValue > now) {
                        setError(dob, 'Date of birth cannot be in the future');
                    } else {
                        setSuccess(dob);
                    }
                    return !document.querySelectorAll('.error.show').length;
                }
            });
        </script>

<jsp:include page="../layouts/footer.jsp"/>