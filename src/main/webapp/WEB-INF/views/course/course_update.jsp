<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../layouts/header.jsp" %>
<div class="container">
    <%@include file="../layouts/sidenav.jsp" %>
    <% User log_user = (User) session.getAttribute("valid_user");%>
    <% if(log_user == null){
        response.sendRedirect("/");
    }%>
    <div class="main_contents">
        <div id="sub_content">
            <form:form method="post" modelAttribute="course" action="courseUpdate" id="form-ad" >

                <h2 class="col-md-6 offset-md-2 mb-5 mt-4">Course Update</h2>

                <div class="row mb-4">
                    <div class="col-md-2"></div>
                    <form:label for="name" path="course.name" class="col-md-2 col-form-label">Name</form:label>
                    <div class="col-md-4 input-control">
                        <form:input value="${update.name}" type="text" class="form-control" path="course.name" id="name" placeholder="Enter Course Name"></form:input>
                        <input type="hidden" name="hiddenId" value="${update.id}"/>
                        <div class="error"></div>
                    </div>
                </div>

                <div class="row mb-4">
                    <div class="col-md-2"></div>
                    <form:label for="description" path="course.description" class="col-md-2 col-form-label">Description</form:label>
                    <div class="col-md-4 input-control">
                        <form:textarea value="${update.description}" type="text" class="form-control" path="course.description" id="description" placeholder="Description"></form:textarea>
                        <div class="error"></div>
                    </div>
                </div>

                <div class="row mb-4">
                    <div class="col-md-2"></div>
                    <form:label for="instructor" path="instructor"  class="col-md-2 col-form-label">Instructor</form:label>
                    <div class="col-md-4 input-control">
                        <form:input value="${update.instructor.name}" type="text" class="form-control" path="instructor" id="instructor" placeholder="Instructor"></form:input>
                        <c:if test="${not empty error}">
                            <div class="error alert alert-danger"> ${error}</div>
                        </c:if>

                    </div>
                </div>

                <div class="row mb-4">
                    <div class="col-md-6 offset-md-2">
                        <button type="submit" class="btn btn-secondary col-md-2" id="openModalButton">Add</button>
                    </div>
                </div>

            </form:form>
        </div>
    </div>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        const form = document.getElementById('form-ad');
        const name = document.getElementById('name');
        const description = document.getElementById('description');
        const instructor = document.getElementById('instructor');

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
            const nameValue = name.value.trim();
            const descriptionValue = description.value.trim();

            if (nameValue === '') {
                setError(name, 'CourseName is required');
            } else {
                setSuccess(name);
            }

            if (descriptionValue === ''){
                setError(description,'Description is required');
            }else{
                setSuccess(description);
            }

            return !document.querySelectorAll('.error.show').length;
        };
    });
</script>
<%@include file="../layouts/footer.jsp" %>