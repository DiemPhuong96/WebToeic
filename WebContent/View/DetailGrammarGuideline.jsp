<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib prefix = "c" uri = "http://java.sun.com/jstl/core_rt" %>
       <%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
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
    <script src="Template/Frontend/js/bootstrap.min.js"></script>
    <script type="text/javascript">
    function Comment()
			{
				var xhttp;
				var memberid = <%=session.getAttribute("sessionid")%>;
				var content = document.formcomment.cmtgrammarcontent.value;
				var grammarguidelineid = <%=request.getAttribute("grammarguidelineid")%>;
				var url = "CmtGrammarController?memberid="+memberid+"&content="+content+"&grammarguidelineid="+grammarguidelineid;
				
				if (window.XMLHttpRequest) 
				{          
				           xhttp = new XMLHttpRequest();
				} 
				else
				{        
					xhttp = new ActiveXObject("Microsoft.XMLHTTP");
				}
				
				xhttp.onreadystatechange = function()
				{
					if(xhttp.readyState == 4)
					{
						var data = xhttp.responseText;
						document.getElementById("listgrammarcmt").innerHTML = data;
					}			
					
				}

				
				xhttp.open("POST",url,true);
				xhttp.send();
			}
    </script>
</head>
<body>
 <jsp:include page="Header.jsp"/>
<div class="container">
	  <!--PAGE TITLE-->

	<div class="row">
		<div class="span12">
		<div class="page-header">
				<h1 style="color:black">
				<a href="ListGrammarGuildeForward?pageid=1">Guideline</a>
			</h1>
		</div>
		</div>
	</div>

  <!-- /. PAGE TITLE-->



	<div class="row">

	
		<div class="span9">
			<!--Blog Post-->
			<div class="blog-post">
				<!-- h2>There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour</h2-->

				<div class="postmetadata">
					<ul>
							

							<li>
								 <i class="icon-calendar"></i><%=new java.util.Date() %>
							</li>

							<li>
								<i class="icon-comment"></i> <a href="#">${countrowcmt} Comments</a>
							</li>
							
					</ul>
				</div>


			<!-- thich thi bo anh o day -->
					<c:set var = "kq1" value="${fn:replace(content,databasesenter,htmlenter)}"/>
					
					<c:out value="${kq1}" escapeXml= "false"/>
					
			</div>



			<% 
		if(session.getAttribute("sessionuser") == null){
			%>

<div class="comments">

			
				<!--Post Comments-->

				<h2>Leave a Message</h2>

				<form>
				
					 <textarea class="span8" rows="10" placeholder="Message" disabled style = "color: red">Login to comment </textarea>
					

					<button type="submit" class="btn btn-large btn-primary"disabled>Submit</button>

			</form>

			</div>
	

<%
}
else{
%>


			<div class="comments">

			
				<!--Post Comments-->

				<h2>Leave a Message</h2>

				<form name ="formcomment">
				
					 <textarea class="span8" rows="8" placeholder="Message" name = "cmtgrammarcontent"></textarea>
					

					<button type="button" class="btn btn-large btn-primary"onclick="Comment()">Submit</button>

			</form>

			</div>
<%
}%>

			<div id = "listgrammarcmt">
	<c:forEach items = "${listcmt}" var = "list">



			
				<!--Post Comments-->

				<h4>${list.fullname}</h4>

				<form>
				
					 <textarea class="span8" style = "height: 60px; width: 150px" placeholder="Message" disabled style = "color: red">${list.cmtgrammercontent}</textarea>
					

					

			</form>

</c:forEach>
			
			
			</div>
		
</div>
</div>
			
		</div>	

 
<jsp:include page="Footer.jsp"/>
</body>
</html>