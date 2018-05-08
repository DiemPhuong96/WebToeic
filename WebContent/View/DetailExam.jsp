<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Do Exam</title>

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
    <link rel="stylesheet" type="text/css" href="JqueryPhanTrang/css/style.css" media="screen"/>
    <script src="countdown/js/jquery.timeout.interval.idle.js" type="text/javascript"></script> 
	<script src="countdown/js/jquery.countdown.counter.js" type="text/javascript"></script> 
    
    <style>
           
            .demo{
               
                padding:10px;
                margin:10px auto;
                border: 1px solid #fff;
                background-color:#f7f7f7;
            }
       
			.pagedemo{
			
				
				margin:2px;
                padding:50px 10px;
         
				background-color:white;	
			}
        </style>
        <script type="text/javascript">
        function auto_submit(){
        	document.countdown.submit();
        }
        
        function auto_submit2(){
        	setTimeout("auto_submit()", 100000)
        }
        
        </script>
<script src="countdown/countdown.js"></script>
</head>
<body onload="auto_submit2();">
<jsp:include page="Header.jsp"/>
 	<div class="row" style="padding-left: 50px">
 	
 	<br>
 	<br>

 	<script>

						function doneHandler(result) 
						{
							
							
							var year = result.getFullYear();
							var month = result.getMonth() + 1; // bump by 1 for humans
							var day = result.getDate();
							var h = result.getHours();
							var m = result.getMinutes();
							var s = result.getSeconds();
							
							var UTC = result.toString();
							
							var output = UTC + "\n";
							output += "year: " + year + "\n";
							output += "month: " + month + "\n";
							output += "day: " + day + "\n";
							output += "h: " + h + "\n";
							output += "m: " + m + "\n";
							output += "s: " + s + "\n";
							
							
						}				
						var myCountdownTest = new Countdown({
														 	time: 100,
															width	: 300, 
															height	: 50,
															onComplete : doneHandler
															});					
					</script>

					<br>
				
	<div class="span8">
 	<div id="paginationdemo" class= "thumbnail" style =" height: 500px;">

 	<div class = "reading_decription" style ="overflow: auto;">
 	<div id="p1" class=" _current">
 	<center><h1 style = "padding-top: 100px">Next Page to show the exam</h1></center>
 	</div>
 	<c:forEach items="${listexamquesion}" var ="list">
					<div id="p${list.num+1}" class="pagedemo" style="display:none;">
					
				
					
						<c:if test="${(list.imagequestion != '')}">
						<img alt="" src="ImageAudioExam/${list.imagequestion}" style = "height: 300px; width: 300px">
						
						
						</c:if>	
						<c:if test="${(list.audiomp3 != ' ')&&(list.audiogg != ' ')}">
						<audio controls>
						<source src="ImageAudioExam/${list.audiogg}" type = "audio/ogg">
						<source src="ImageAudioExam/${list.audiomp3}" type = "audio/mp3">
						</audio>
						</c:if>
					<c:if test="${(list.paragraph != ' ')}">
					<p>${list.paragraph}</p>
					</c:if>
					<br>
						<p><b>Question: ${list.num}</b></p>
							<p><b>${list.question}</b></p>
					<c:if test="${(list.option1 != ' ')}">
					
						<p>${list.option1}</p>
						
					</c:if>	
						<c:if test="${(list.option2 != ' ')}">
					
						
						<p>${list.option2}</p>
					
					</c:if>	
					<c:if test="${(list.option3 != ' ')}">
					
						
						<p>${list.option3}</p>
					
					</c:if>	
					<c:if test="${(list.option4 != ' ')}">
					
						
						<p>${list.option4}</p>
					
					</c:if>	
						
					</div>
				</c:forEach>
</div>
 		
 	</div>
 	<div id="demo5">
 		</div>
 	</div>
 	<form name="countdown" action="DetailExamForward?examinationid=${examinationid}&memberid=${memberid}" method="post">
 	<div class="span4">
 	<div class= "thumbnail">
 	<div class = "reading_decription" style ="overflow: auto; height: 500px;">
 	<center></center><c:forEach items = "${listexamquesion}" var = "list">

 	<div class="span1">
 	${list.num}
 	</div>
	A.<input type ="radio" name ="ans[${list.num}]" value = "A">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	B.<input type = "radio" name = "ans[${list.num}]" value ="B">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	C.<input type = "radio" name = "ans[${list.num}]" value = "C">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	D.<input type = "radio" name = "ans[${list.num}]" value = "D">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<br>
	<br>
</c:forEach>
 	</div>
 
 	</div>
 	<br>
 	<input type="submit" class = "btn btn-primary" value = "Complete"/>
 	</div>
 	
 </form>	
 	</div>
<jsp:include page="Footer.jsp"/>

       
		<script type="text/javascript" src="JqueryPhanTrang/jquery-1.3.2.js"></script>
		<script src="JqueryPhanTrang/jquery.paginate.js" type="text/javascript"></script>
		<script type="text/javascript">
		
			$(function() 
			{
				$("#demo5").paginate({
					count 		: 10,
					start 		: 1,
					display     : 5,
					border					: true,
					border_color			: '#fff',
					text_color  			: '#fff',
					background_color    	: 'black',	
					border_hover_color		: '#ccc',
					text_hover_color  		: '#000',
					background_hover_color	: '#fff', 
					images					: false,
					mouse					: 'press',
					onChange     			: function(page){
												$('._current','#paginationdemo').removeClass('_current').hide();
												$('#p'+page).addClass('_current').show();
											  }
				});
			});
		</script>
</body>
</html>