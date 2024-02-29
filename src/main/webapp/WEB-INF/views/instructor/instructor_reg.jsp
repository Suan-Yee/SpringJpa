<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="../layouts/header.jsp" %>
<div class="container">
  <%@include file="../layouts/sidenav.jsp" %>
  <div class="main_contents">
    <div id="sub_content">
      <form:form method="post" modelAttribute="instructor" action="addInstructor" id="form-ad">

        <h2 class="col-md-6 offset-md-2 mb-5 mt-4">Instructor Registration</h2>

        <div class="row mb-4">
          <div class="col-md-2"></div>
          <form:label for="email" path="email" class="col-md-2 col-form-label">Email</form:label>
          <div class="col-md-4" class="input-control">
            <form:input type="text" class="form-control" path="email" id="email" placeholder="Enter Instructor Email"></form:input>
            <div class="error"></div>
          </div>
        </div>

        <div class="row mb-4">
          <div class="col-md-2"></div>
          <form:label for="name" path="name" class="col-md-2 col-form-label">Name</form:label>
          <div class="col-md-4" class="input-control">
            <form:input type="text" class="form-control" path="name" id="name" placeholder="Enter Instructor Name"></form:input>
            <div class="error"></div>
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

<%--<script>
    document.getElementById('openModalButton').addEventListener('click', function () {
        document.getElementById('exampleModal').classList.add('show');
        document.getElementById('exampleModal').style.display = 'block';
    });

    document.getElementById('okButton').addEventListener('click', function () {
        document.getElementById('courseForm').submit();
    });
</script>--%>

<script>
  document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('form-ad');
    const name = document.getElementById('name');
    const email = document.getElementById('email');

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

    const isValidEmail = email => {
      const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      return re.test(String(email).toLowerCase());
    }

    const validateInputs = function () {
      const nameValue = name.value.trim();
      const emailValue = email.value.trim();

      if (nameValue === '') {
        setError(name, 'Username is required');
      } else {
        setSuccess(name);
      }

      if(emailValue === '') {
        setError(email, 'Email is required');
      } else if (!isValidEmail(emailValue)) {
        setError(email, 'Provide a valid email address');
      } else {
        setSuccess(email);
      }

      return !document.querySelectorAll('.error.show').length;
    };
  });
</script>

<%@include file="../layouts/footer.jsp" %>