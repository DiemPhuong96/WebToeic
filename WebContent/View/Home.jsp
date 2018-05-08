<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Trang chủ</title>


    <link href="Template/Frontend/css/bootstrap.css" rel="stylesheet">
    <link href="Template/Frontend/css/bootstrap-responsive.css" rel="stylesheet">
    <link href="Template/Frontend/css/style.css" rel="stylesheet"> 

    <link href='Template/Frontend/font/font.css' rel='stylesheet'>
  
 
    <script src="Template/Frontend/js/jquery-1.js"></script>
    <script src="Template/Frontend/js/bootstrap.min.js"></script>
    
    <script type="text/javascript">
				
		
				
				function Search()
				{
					var xhttp;
					var grammarname = document.myform.grammarname.value;
					
					if (grammarname != "")
					{
						var url = "SearchController?grammarname="+grammarname;
						
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
								document.getElementById("searchresult").innerHTML = data;
							}			
							
						}	
						xhttp.open("POST",url,true);
						xhttp.send();	
					}
					else 
					{
						document.getElementById("searchresult").innerHTML = "";
					}
								
						
				}
				
				
		
		</script>
    <style type=".search-form .form-group {
  float: right !important;
  transition: all 0.35s, border-radius 0s;
  width: 32px;
  height: 32px;
  background-color: #fff;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset;
  border-radius: 25px;
  border: 1px solid #ccc;
}
.search-form .form-group input.form-control {
  padding-right: 20px;
  border: 0 none;
  background: transparent;
  box-shadow: none;
  display:block;
}
.search-form .form-group input.form-control::-webkit-input-placeholder {
  display: none;
}
.search-form .form-group input.form-control:-moz-placeholder {
  /* Firefox 18- */
  display: none;
}
.search-form .form-group input.form-control::-moz-placeholder {
  /* Firefox 19+ */
  display: none;
}
.search-form .form-group input.form-control:-ms-input-placeholder {
  display: none;
}
.search-form .form-group:hover,
.search-form .form-group.hover {
  width: 100%;
  border-radius: 4px 25px 25px 4px;
}
.search-form .form-group span.form-control-feedback {
  position: absolute;
  top: -1px;
  right: -2px;
  z-index: 2;
  display: block;
  width: 34px;
  height: 34px;
  line-height: 34px;
  text-align: center;
  color: #3596e0;
  left: initial;
  font-size: 14px;
}"></style>
</head>
<body>

<!--HEADER ROW-->
 <jsp:include page="Header.jsp"/>
 
  <!-- /HEADER ROW -->

  
  <div class="container">
  
      <div class="row" style="padding-top: 20px">
        <div class="col-md-4 col-md-offset-3">
            <form  class="search-form" name = "myform">
                <div class="form-group has-feedback">
            		
            		<input type="text" class="form-control" name="grammarname" id="search" placeholder="search"  onkeyup="Search()">
              		<span class="glyphicon glyphicon-search form-control-feedback"></span>
            	</div>
            </form>
        </div>
    </div>
  </div>


  <div class="container" id="searchresult">
 


  <!--Carousel
  ==================================================-->

  <div id="myCarousel" class="carousel slide">
    <div class="carousel-inner">

      <div class="active item">
        <div class="container">
          <div class="row">
            
              <div class="span6">

                <div class="carousel-caption">
                      <h1>Huong dan phan nghe, doc toeic</h1>
                      <p class="lead">Chung toi cung cap cho ban nhung kien thuc tot nhat</p>
                      <a class="btn btn-large btn-primary" href="#">Hay tham gia ngay</a>
                </div>

              </div>

                <div class="span6"> <img src="Template/Frontend/img/slide/slide1.jpg" height = "530px" width = "320px"></div>

          </div>
        </div>
       



      </div>

<c:forEach items = "${listbanner}" var = "list">



      <div class="item">
       
        <div class="container">
          <div class="row">
            
              <div class="span6">

                <div class="carousel-caption">
                      <h1>${list.slidename}</h1>
                      <p class="lead">${list.slidecontent}</p>
                      <a class="btn btn-large btn-primary" href="#">Hay tham gia</a>
                </div>

              </div>

                <div class="span6"> <img src="Template/Frontend/img/slide/${list.slideimage}" height = "670px" width = "380px" ></div>
                

          </div>
        </div>

      </div>


</c:forEach>


    </div>
       <!-- Carousel nav -->
      <a class="carousel-control left " href="#myCarousel" data-slide="prev"><i class="icon-chevron-left"></i></a>
      <a class="carousel-control right" href="#myCarousel" data-slide="next"><i class="icon-chevron-right"></i></a>
        <!-- /.Carousel nav -->

  </div>
    <!-- /Carousel -->



<!-- Feature 
  ==============================================-->


  <div class="row feature-box">
      <div class="span12 cnt-title">
       <h1>Ms Phuong toeic cung cap cho ban mot moi truong hoc tieng anh than thien, tiet kiem va hieu qua</h1> 
        <span>Hoc --- Lam bai tap ----- Thi Thu</span>        
      </div>

      <div class="span4">
        <img src="Template/Frontend/image/director.jpg" height="150" width="150">
        <h2>Tu vung va ngu phap</h2>
        <p>
           Chung toi cam doan cung cap den ban cac bai hoc sat voi de thi
        </p>

        <a href="#" data-toggle="modal" data-target="#myModal">Read More &rarr;</a>

      </div>

      <div class="span4">
        <img src="Template/Frontend/image/study.jpg"height="150" width="150">
        <h2>Bai tap phan nghe, doc</h2>
        <p>
            Lam bai tap cung co nhung kien thuc vua hoc
          
        </p>   
          <a href="#">Read More &rarr;</a>    
      </div>

      <div class="span4">
        <img src="Template/Frontend/image/test.png"height="150" width="150">
        <h2>De thi thu</h2>
        <p>
           Cac dang de sat voi cac de toeic nhat
        </p>
          <a href="DisplayExamForward?pageid=1">Read More &rarr;</a>
      </div>
  </div>


<!-- /.Feature -->

  <div class="hr-divider"></div>

<!-- Row View -->


  
    
</div>


<!-- /.Row View -->



<jsp:include page="Footer.jsp"/>

<div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
  <!-- Modal content-->

      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">LOAI BAI HUONG DAN</h4>
        </div>
        
       <div class ="modal-body">
       <div class ="media">
       <a class ="pull-left"><img src = "Template/Frontend/image/listen.png" class= "media-object" alt =''height="75" width="75"/></a>
       <div class = "media-body">
       <h4>
       <a href= "VocabularyFrontForward?pageid=1">Huong dan phan tu vung</a>
       </h4>
       </div>
       </div>
        <div class ="media">
       <a class ="pull-left"><img src = "Template/Frontend/image/grammar.jpg" class= "media-object" alt ='' height="75" width="75"/></a>
       <div class = "media-body">
       <h4>
       <a href= "ListGrammarGuildeForward?pageid=1">Huong dan phan ngu phap</a>
       </h4>
       </div>
       </div>
       </div>
      
        <div class="modal-footer">
         <button class="btn btn-info" type="submit">
												<i class="ace-icon fa fa-check bigger-110"></i>
												Cannel
											</button>
        </div>
      </div>
      
      </div>
      </div>

</body>
</html>