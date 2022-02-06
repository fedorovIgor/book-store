<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <title>View Books</title>
        <link href="<c:url value="/css/common.css"/>" rel="stylesheet" type="text/css">
    </head>
    <body>

    ${roles}


            <select>
                <c:forEach var="role" items="${roles}">
                     <option> ${role} </optional>
                 </c:forEach>
             </select>

        <table>
            <thead>
                <tr>
                    <th>Username</th>
                    <th>Role</th>
                    <th>Change User</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${users}" var="user">
                    <tr>
                        <td>${user.getUsername()}</td>
                        <td id="${user.getUsername()}">

                            <span id="{user.getRoleName()}">${user.getRoleName()}<span>

                            <select>
                                <c:forEach var="role" items="${roles}">
                                     <option  value="${role}"> ${role} </optional>
                                 </c:forEach>
                             </select>
                        </td>
                        <td><input type="button" value="Change user role" name="${user.getUsername()}"
                        onclick="ChangeUserRole(this.name)"></td>

                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <script type="text/javascript">

            function ChangeUserRole(username) {
                let request = new XMLHttpRequest();
                request.onload = function() {
                    //location.reload();
                };

                chosenRole = document.querySelector('#'  + username).childNodes[1].value;

                console.log(document.getElementById(username));
                console.log(username);
                console.log(chosenRole);

                request.open("GET", "http://127.0.0.1:8086/users/" + username + "/role/" + chosenRole, false);
                request.send();
            }
        </script>
    </body>
</html>