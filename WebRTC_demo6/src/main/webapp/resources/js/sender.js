//const webSocket = new WebSocket("wss://localhost:8080/kr/socket")
//const webSocket = new WebSocket("wss://192.168.0.184:8080/kr/socket")
const webSocket = new WebSocket("wss://192.168.43.124:8080/kr/socket")
webSocket.onmessage = (event) => {
	console.log(JSON.stringify(event))
	handleSignallingData(JSON.parse(event.data))
}

function handleSignallingData(data) {
	console.log(data)
	switch (data.type) {
	case "answer" : 
		peerConn.setRemoteDescription(data.answer)
		break
	case "candidate" :
		peerConn.addIceCandidate(data.candidate)
	}
}


let username
function sendUsername() {

    username = document.getElementById("username-input").value
    sendData({
        type: "store_user"
    })
}    

function sendData(data) {
    data.username = username
    console.log(data)
    webSocket.send(JSON.stringify(data))
}


let localStream
let peerConn
function startCall() {
    document.getElementById("video-call-div").style.display = "inline"

	var filter = "win16|win32|win64|mac|macintel";

	var constraints
	if( navigator.platform) { 
		if( filter.indexOf( navigator.platform.toLowerCase() ) < 0 ) {
			constraints = {
						        video: {
						            frameRate: 24,
						            width: {
						                min: 480, ideal: 720, max: 1280
						            },
						            aspectRatio: 1.33333,
						            facingMode: {
						            	exact: 'environment'
						            }
						        },
						        audio: true
						    }
		} else {
			constraints = {
						        video: {
						            frameRate: 24,
						            width: {
						                min: 480, ideal: 720, max: 1280
						            },
						            aspectRatio: 1.33333
						        },
						        audio: true
						    }
		}
	}


    navigator.mediaDevices.getUserMedia(constraints).then (function(stream){
    	
    	console.log(stream)
    
        localStream = stream
        document.getElementById("local-video").srcObject = localStream
        //console.log(localStream)
        let configuration = {
            iceServers: [
                {
                    "urls": ["stun:stun.l.google.com:19302", 
                    "stun:stun1.l.google.com:19302", 
                    "stun:stun2.l.google.com:19302"]
                }
            ]
        }
        

        peerConn = new RTCPeerConnection(configuration)
       
        peerConn.addStream(localStream)

        peerConn.onaddstream = (e) => {
            console.log('get peer stream')
            document.getElementById("remote-video").srcObject = e.stream
        }
        

        
        //stream.getTracks().forEach(track => peerConn.addTrack(track, stream))
        peerConn.onTrack = e => {
            console.log('get peer stream')
            document.getElementById("remote-video").srcObject = e.stream
        }

        peerConn.onicecandidate = ((e) => {
            if (e.candidate == null) return
            sendData({
                type: "candidate",
                candidate: e.candidate
            })
        })

        createAndSendOffer()
    }).then (function(error) {
        console.log(error)
    })
}

function createAndSendOffer() {
    peerConn.createOffer((offer) => {
        sendData({
            type: "offer",
            offer: offer
        })
        peerConn.setLocalDescription(offer)
    }, (error) => {
        console.log(error)
    })
}

let isAudio = true
function muteAudio() {
    isAudio = !isAudio
    localStream.getAudioTracks()[0].enabled = isAudio
}

let isVideo = true
function muteVideo() {
    isVideo = !isVideo
    localStream.getVideoTracks()[0].enabled = isVideo
}