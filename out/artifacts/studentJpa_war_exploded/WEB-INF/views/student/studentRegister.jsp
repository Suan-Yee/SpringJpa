<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="../layouts/header.jsp" %>

<!-- <div id="testsidebar">Hello World </div> -->
<div class="container">
    <%@include file="../layouts/sidenav.jsp" %>
    <div class="main_contents">
        <div id="sub_content">
            <form:form method="post" modelAttribute="register" action="addStudent" id="af-form">
                <h2 class="col-md-6 offset-md-2 mb-5 mt-4">Student Registration</h2>

                <div class="row mb-4">
                    <div class="col-md-2"></div>
                    <form:label for="name" path="student.name"  class="col-md-2 col-form-label">Name</form:label>
                    <div class="col-md-5">
                        <form:input type="text" path="student.name" class="form-control"
                                    placeholder="Please Enter your name"></form:input>
                    </div>
                </div>
                <div class="row mb-4">
                    <div class="col-md-2"></div>
                    <form:label for="dob" path="student.dob" class="col-md-2 col-form-label">DOB</form:label>
                    <div class="col-md-5">
                        <form:input type="date" path="student.dob"  name="dob" class="form-control" id="dob"
                                    placeholder="YYYY-MM-DD"></form:input>
                    </div>
                </div>

                <fieldset class="row mb-4">
                    <div class="col-md-2"></div>
                    <legend class="col-form-label col-md-2 pt-0">Gender</legend>
                    <div class="col-md-5">
                        <div class="form-check-inline">
                            <form:label path="student.gender">Gender</form:label></td> <td>
                                <form:radiobutton path="student.gender" value="MALE" label="Male" />
                                <form:radiobutton path="student.gender" value="FEMALE" label="Female" />
                        </div>
                    </div>
                </fieldset>

                <div class="row mb-4">
                    <div class="col-md-2"></div>

                    <form:label path="student.phone" for="phone" class="col-md-2 col-form-label">Phone</form:label>
                    <div class="col-md-5">
                        <div id="error" style="color: red;"></div>
                        <form:input path="student.phone" type="text"  class="form-control" id="phone"
                                    placeholder="Please enter your phone number" maxlength="11"></form:input>
                    </div>
                </div>

                <div class="row mb-4">
                    <div class="col-md-2"></div>
                    <form:label path="student.education" for="education" class="col-form-label">Education</form:label>
                    <div class="col-md-5">
                        <form:select path="student.education" class="form-select" aria-label="Education" id="education">
                            <form:option value="Bachelor of Information Technology">Bachelor of Information Technology</form:option>
                            <form:option value="Diploma in IT">Diploma in IT</form:option>
                            <form:option value="Bachelor of Computer Science">Bachelor of Computer Science</form:option>
                        </form:select>
                    </div>
                </div>
                <fieldset class="row mb-5">
                    <div class="col-md-2"></div>
                    <legend class="col-form-label col-md-2 pt-0">Attend</legend>

                    <div class="col-md-5">
                        <div class="form-check-inline col-md-5">
                            <div class="col-md-4"></div>
                            <form:checkboxes items="${courses}" path="courses" itemValue="id" itemLabel="name"/>
                        </div>
                    </div>
                </fieldset>

                <div class="row mb-4">
                    <div class="col-md-2"></div>
                    <label for="name" class="col-md-2 col-form-label">Photo</label>
                    <div class="col-md-4">
                        <input type="file" path="" class="form-control" id="name"
                               accept="image/*" multiple="true">
                    </div>
                </div>

                <div class="row mb-4">
                    <div class="col-md-4"></div>

                    <div class="col-md-4">
                        <button type="button" class="btn btn-danger ">Reset</button>
                        <button type="submit" class="btn btn-secondary col-md-2"
                                data-bs-toggle="modal" data-bs-target="#exampleModal">
                            Add</button>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
    <%--<script>
document.addEventListener('DOMContentLoaded', function(){
    let form = document.getElementById("af-form");
    let errorMessaage = document.getElementById("alert-Message");

    form.addEventListener('submit', function(event){
        if(!validateForm()){
            event.preventDefault();
        }
    });

    function validateForm(){
        let name = document.getElementById("name").value.trim();
        let gender = document.querySelector("input[name='gender']:checked");
        let phone = document.getElementById("phone").value;
        let education  = document.getElementById("education").value;
        let dob = document.getElementById("dob").value;
        let attend = document.querySelectorAll("input[name='attend']:checked");

        if(name === '' || gender === null || phone === '' || education === '' || dob === '' || attend.length === 0 ){
            errorMessaage.innerHTML = "Please fill in all required fields";
            return false;
        } else {
            errorMessaage.innerHTML = "";
            return true;
        }
    }
});

function validatePhone(input){

    input.value = input.value.replace(/[^0-9]/g, '');
    if(input.value.length < 7){
        document.getElementById("error").innerText = "Phone number should be at least 7 digits";
        return false;
    }else{
        document.getElementById("error").innerText ="";
        return true;
    }


}
function showModal() {

    let id = document.getElementById("exampleModal");
    modal.style.display = 'none';

}


</script>--%>

</div>

<%@include file="../layouts/footer.jsp" %>