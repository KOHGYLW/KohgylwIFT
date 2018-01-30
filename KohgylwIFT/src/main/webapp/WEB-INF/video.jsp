<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!doctype html>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>KPlayer</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/overrall.min.css">
<link rel="stylesheet" href="css/video-js.min.css">
<link rel="icon" type="image/x-icon" href="css/player.png" />
<script type="text/javascript" src="js/videojs-ie8.min.js"></script>
</head>

<body>
	<%-- 中央布局 --%>
	<div class="container">

		<%-- 头部 --%>
		<div class="row">
			<div class="col-md-12">
				<div class="titlebox">
					<span class="titletext"><em> 青阳网络视频播放器 <small><span
								class="graytext">KIFT-Player</span></small></em></span>
					<button class="btn btn-link rightbtn" onclick="reMainPage()">
						返回 <span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span>
					</button>
				</div>
				<hr />
			</div>
		</div>
		<%-- end 头部 --%>

		<%-- 主体 --%>
		<div class="row">
			<div class="col-md-12">
				<p class="subtitle">视频名称：${video.fileName }</p>
				<p class="subtitle">${video.fileCreator }/${video.fileCreationDate }/${video.fileSize } MB</p>
				<br />
				<!-- 播放窗口组件位置 -->
				<video id="kiftplayer" class="video-js col-md-12" controls
					preload="auto" height="500">
					<source src="fileblocks/${video.filePath }" type="video/mp4">
					<source src="fileblocks/${video.filePath }" type="video/webm">
				</video>
			</div>
		</div>
		<%-- end 主体 --%>

	</div>
	<%-- end 中央布局 --%>

</body>
<script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/video.js"></script>
<script type="text/javascript">
	var player = videojs('kiftplayer');
	player.ready(function() {
		this.play();
	});
	function reMainPage(){
		window.location.href="home.jsp";
	}
</script>
</html>