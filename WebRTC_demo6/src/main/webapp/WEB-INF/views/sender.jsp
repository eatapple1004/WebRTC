<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="EUC-KR">
		<title>Sender Page</title>
		<link href="<c:url value="/resources/css/style.css"/>" rel='stylesheet' />
	</head>
	<body>
		<div>
			<input placeholer="Enter username..."
				type="text"
				id="username-input"/><br>
			<button onclick="sendUsername()">Send</button>
			<button onclick="startCall()">Start</button>
		</div>
		<div id="video-call-div">
			<video mute id ="local-video" autoplay></video>
			<video id = "remote-video" autoplay></video>
			<div class="call-action-div">
				<button onclick="muteVideo()">Mute Video</button>
				<button onclick="muteAudio()">Mute Audio</button>
			</div>
		</div>
		<script src="${path}/resources/js/sender.js"/></script>
	</body>
</html>