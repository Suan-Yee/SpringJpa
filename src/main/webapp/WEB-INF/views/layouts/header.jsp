<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="test.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">

    <%@ page import="java.util.Date"%>
    <%@ page import="code.entity.User" %>
    <%@ page import="code.entity.Role" %>
    <%@ page import="code.entity.UserRole" %>
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet"
          href="../../resources/test.css"  <%--${pageContext.request.contextPath}--%>
          type="text/css" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>

    <title>Course Registration</title>


</head>
<body>
<% User user  = (User) session.getAttribute("valid_user");%>
<%if(user != null) {%>
<div id="testheader">
    <div class="container">
        <div class="row">
            <div class="col-md-5">
                <a href="welcome"><h3>Student Registration</h3></a>
            </div>
            <div class="col-md-6">
                <span style="color: #E53935; display: inline-block;"> User: </span>
                <p style="color: #5D4037; display: inline-block; margin: 0;"> <%=String.format("USR%03d",user.getId()) %> <%=user.getUsername()%>
                   </p>
                <p>
                    <span style="color: #E53935;">Current Date:</span>
                    <span style="color: #5D4037;"><%= new java.text.SimpleDateFormat("yy/MM/dd").format(new Date()) %></span>
                </p>
            </div>

            <div class="col-md-1">
                <a href="logout"><input type="button" class="btn-basic btn btn-primary" id="logout-button" value="Log Out"></a>
            </div>

            <script>
                function logout() {
                    document.getElementById("logout-form").submit();
                }
            </script>
        </div>
    </div>
</div>
<% } %>
</body>

</html>
