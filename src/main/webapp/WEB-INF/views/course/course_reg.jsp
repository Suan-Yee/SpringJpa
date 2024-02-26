<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="../layouts/header.jsp" %>
<div class="container">
    <%@include file="../layouts/sidenav.jsp" %>
    <div class="main_contents">
        <div id="sub_content">
            <form:form method="post" modelAttribute="course" action="addCourse" id="courseForm" >

                <h2 class="col-md-6 offset-md-2 mb-5 mt-4">Course Registration</h2>

                <div class="row mb-4">
                    <div class="col-md-2"></div>
                    <form:label for="name" path="name" class="col-md-2 col-form-label">Name</form:label>
                    <div class="col-md-4">
                        <form:input type="text" class="form-control" path="name" id="name" placeholder="Enter Course Name"></form:input>
                    </div>
                </div>

                <div class="row mb-4">
                    <div class="col-md-2"></div>
                    <form:label for="description" path="description" class="col-md-2 col-form-label">Description</form:label>
                    <div class="col-md-4">
                        <form:textarea type="text" class="form-control" path="description" id="description" placeholder="Description"></form:textarea>
                    </div>
                </div>

                <div class="row mb-4">
                    <div class="col-md-2"></div>
                    <label for="instructor"  class="col-md-2 col-form-label">Instructor</label>
                    <div class="col-md-4">
                        <input type="text" class="form-control" name="instructor" id="instructor" placeholder="Instructor">
                    </div>
                </div>

                <div class="row mb-4">
                    <div class="col-md-6 offset-md-2">
                        <button type="submit" class="btn btn-secondary col-md-2" id="openModalButton">Add</button>
                    </div>
                </div>

            </form:form>

            <%--<div class="modal" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">Course Registration</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <h5 style="color: rgb(127, 209, 131);">Registered Successfully!</h5>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-success col-md-2" data-bs-dismiss="modal" id="okButton">Ok</button>
                        </div>
                    </div>
                </div>
            </div>--%>
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

<%@include file="../layouts/footer.jsp" %>