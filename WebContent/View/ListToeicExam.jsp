<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib prefix = "c" uri = "http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Toeic Test</title>

<link href="Template/Frontend/css/bootstrap.min.css" rel="stylesheet">
	<link href="Template/Frontend/css/bootstrap-responsive.min.css" rel="stylesheet">
	<link href="Template/Frontend/css/style.css" rel="stylesheet">


  <link rel="apple-touch-icon-precomposed" sizes="144x144" href="Template/Frontend/img/apple-touch-icon-144-precomposed.png">
  <link rel="apple-touch-icon-precomposed" sizes="114x114" href="Template/Frontend/img/apple-touch-icon-114-precomposed.png">
  <link rel="apple-touch-icon-precomposed" sizes="72x72" href="Template/Frontend/img/apple-touch-icon-72-precomposed.png">
  <link rel="apple-touch-icon-precomposed" href="Template/Frontend/img/apple-touch-icon-57-precomposed.png">
  <link rel="shortcut icon" href="Template/Frontend/img/favicon.png">
  


    <!-- SCRIPT 
    ============================================================-->  
    <script src="http://code.jquery.com/jquery.js"></script>
    <script src="js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="Header.jsp"/>
<center> <p style="color: red; padding-top:40px"><%=request.getAttribute("msgexam") !=null? request.getAttribute("msgexam"):"" %></p></center>
	<div class="row" style="padding-top: 90px; padding-left: 50px">
	<c:forEach items = "${listexam}" var = "list">
		<div class="span6">
		<div class="media">
				 <a href="#" class="pull-left"><img src="ImageUpload/${list.examinationimage}" class="media-object" alt='' height="120" width="120"/></a>
				<div class="media-body">
					<h4 class="media-heading">
						${list.examinationname}
					</h4> 
					<c:if test="${sessionid != null}">
					<a href="DetailExamForward?examinationid=${list.examinationid}&memberid=<%=session.getAttribute("sessionid")%>" class="btn" type="button">Read more</a>
					</c:if>
					<c:if test="${sessionid == null}">
					<a href="DetailExamForward1" class="btn" type="button">Read more</a>
					</c:if>
				</div>
			</div>
		</div>			
</c:forEach>

	
	</div>

  <!-- /HEADER ROW -->
  <center>
 
<div class="pagination" style ="padding-top: 70px">

				<ul>
		 <c:if test ="${numberpage == 1}">
		  <c:if test ="${numberpage == maxpageid}">
	   <li class = "disabled"><a href = "#">Prev</a></li>

	   <li class = "disabled"><a href = "#">Next</a></li>
	   
</c:if>
		 	  <c:if test ="${numberpage != maxpageid}">
	   <li class = "disabled"><a href = "#">Prev</a></li>
	 
	   <li><a href = "DisplayExamForward?pageid=${numberpage+1}">Next</a></li>
	   </c:if>
</c:if>
	 <c:if test ="${numberpage != 1}">
 <c:if test ="${numberpage == maxpageid}">
	   <li><a href = "DisplayExamForward?pageid=${numberpage-1}">Prev</a></li>

	   <li class = "disabled"><a href = "#">Next</a></li>
</c:if>
 <c:if test ="${numberpage != maxpageid}">
  <li class = "disabled"><a href = "#">Prev</a></li>

	   <li class = "disabled"><a href = "#">Next</a></li>
 </c:if>
</c:if>
<c:if test ="${numberpage >1 && numberpage <maxpageid}">
	   <li><a href = "DisplayExamForward?pageid=${numberpage-1}">Prev</a></li>
	
	   <li><a href = "DisplayExamForward?pageid=${numberpage+1}">Next</a></li>
</c:if>
				</ul>
			</div>
 </center>	
<jsp:include page="Footer.jsp"/>
</body>
</html>