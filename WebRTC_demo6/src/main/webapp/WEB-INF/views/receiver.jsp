<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/> 
   
 
<!DOCTYPE html>
<html>
 <head>
    	<meta charset="EUC-KR">
		<title>Receiver Page</title>
    	<link href="<c:url value="/resources/css/style.css"/>" rel='stylesheet' />
    </head>
    <body>
        <div>
            <input placeholder="Enter username..."
                    type="text"
                    id="username-input" /><br>
            <button onclick="joinCall()">Join Call</button>
        </div>
        <div id="video-call-div">
            <video muted id="local-video" autoplay></video>
            <video id="remote-video" autoplay></video>
            <div class="call-action-div">
                <button onclick="muteVideo()">Mute Video</button>
                <button onclick="muteAudio()">Mute Audio</button>
            </div>
        </div>
        <script src='<c:url value="/resources/js/receiver.js"/>'></script>
    </body>
</html>