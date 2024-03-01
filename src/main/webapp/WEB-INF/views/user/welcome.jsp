<%@include file="../layouts/header.jsp" %>
<%@include file="../layouts/sidenav.jsp" %>
<% User log_user = (User) session.getAttribute("valid_user");%>

<% if(log_user == null){
  response.sendRedirect("/");
}%>
<div class="main-contents">

  <div id="contents">

    <h3>Welcome Back...! <br><br>
      Enjoy this project and try your best in your own!</h3>
  </div>

</div>
<%@include file="../layouts/footer.jsp" %>
