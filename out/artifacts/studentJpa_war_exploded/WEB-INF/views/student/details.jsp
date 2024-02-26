<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 2/26/2024
  Time: 9:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="../layouts/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${not empty course}">
<div class="padding">
  <div class="row container d-flex justify-content-center">
    <div class="col-xl-6 col-md-12">
      <div class="card user-card-full">
        <div class="row m-l-0 m-r-0">
          <div class="col-sm-4 bg-c-lite-green green-bg user-profile">
            <div class="card-block text-center text-white">
              <div class="m-b-25">
                <img src="https://img.icons8.com/bubbles/100/000000/user.png" class="img-radius" alt="User-Profile-Image">
              </div>

              <h6 class="f-w-600">${student.name}</h6>
              <p>${student.education}</p>
              <i class=" mdi mdi-square-edit-outline feather icon-edit m-t-10 f-16  fa-solid fa-pen-to-square"></i>
            </div>

          </div>
          <div class="col-sm-8">
            <div class="card-block">
              <h6 class="m-b-20 p-b-5 b-b-default f-w-600">Information</h6>
              <div class="row">
                <div class="col-sm-6">
                  <p class="m-b-10 f-w-600">Address</p>
                  <h6 class="text-muted f-w-400">YGN</h6>
                </div>
                <div class="col-sm-6">
                  <p class="m-b-10 f-w-600">Phone</p>
                  <h6 class="text-muted f-w-400">${student.phone}</h6>
                </div>
              </div>
              <h6 class="m-b-20 p-b-5 b-b-default f-w-600"></h6>
              <div class="row">
                <div class="col-sm-6">
                  <p class="m-b-10 f-w-600">DOB</p>
                  <h6 class="text-muted f-w-400">${student.dob}</h6>
                </div>
                <div class="col-sm-6">
                  <p class="m-b-10 f-w-600">Gender</p>
                  <h6 class="text-muted f-w-400">${student.gender}</h6>
                </div>
              </div>
              <h6 class="m-b-20 p-b-5 b-b-default f-w-600"></h6>

              <ul class="social-link list-unstyled m-t-40 m-b-10">
                <c:if test="${not empty course}">
                  <p class="m-b-10 f-w-600">Courses:</p>
                  <ol>
                    <c:set var="courseIndex" value="0" />
                    <c:forEach items="${course}" var="courseName">
                      <li style="color: black;"><span style="color: blue;">${courseIndex + 1}.</span> ${courseName}</li>
                      <c:set var="courseIndex" value="${courseIndex + 1}" />
                    </c:forEach>
                  </ol>
                </c:if>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
</div>
</c:if>
<%@include file="../layouts/footer.jsp" %>