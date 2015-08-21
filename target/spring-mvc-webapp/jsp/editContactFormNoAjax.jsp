<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Company Contacts</title>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet"> <!-- Bootstrap core CSS -->
        <link rel="shortcut icon" href="http://www.thesoftwareguild.com/favicons/favicon-32x32.png"> <!-- SWC Icon -->
    </head>
    <body>
        <div class="container">
            <h1>Company Contacts</h1>
            <hr/>
            <div class="container">
                <h1>Edit Contact Form</h1>
                <a href="displayContactListNoAjax">Contact List</a><br/>
                <hr/>
                <sf:form class="form-horizontal" role="form" modelAttribute="thisDvd3" action="editContactNoAjax" method="post">
                    <div class="form-group">
                        <label for="add-first-name" class="col-md-4 control-label">First Name: </label>
                        <div class="col-md-8">
                            <sf:input type="text" class="form-control" id="add-first-name" path="firstName" placeholder="First Name" />
                            <sf:errors path="firstName" cssclass="error"></sf:errors>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="add-last-name" class="col-md-4 control-label">Last Name: </label>
                        <div class="col-md-8">
                            <sf:input type="text" class="form-control" id="add-last-name" path="lastName" placeholder="Last Name" />
                            <sf:errors path="lastName" cssclass="error"></sf:errors>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="add-company" class="col-md-4 control-label">Company: </label>
                        <div class="col-md-8">
                            <sf:input type="text" class="form-control" id="add-company" path="company" placeholder="Company" />
                            <sf:errors path="company" cssclass="error"></sf:errors>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="add-phone" class="col-md-4 control-label">Phone: </label>
                        <div class="col-md-8">
                            <sf:input type="text" class="form-control" id="add-phone" path="phone" placeholder="Phone" />
                            <sf:errors path="phone" cssclass="error"></sf:errors>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="add-email" class="col-md-4 control-label">Email: </label>
                        <div class="col-md-8">
                            <sf:input type="text" class="form-control" id="add-email" path="email" placeholder="Email" />
                            <sf:errors path="email" cssclass="error"></sf:errors>
                            <sf:hidden path="contactId" /> <!-- the sf:hidden field is used to retain the contact id number for the contact when it goes back to the controller -->
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-md-offset-4 col-md-8">
                            <button type="submit" id="add-button" class="btn btn-default">Edit Contact</button>
                        </div>
                    </div>
                </sf:form>
            </div>
        </div>
        <!--  -->
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-1.11.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>


