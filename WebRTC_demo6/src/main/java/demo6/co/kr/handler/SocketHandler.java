package demo6.co.kr.handler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
/*
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.RemoteEndpoint.Basic;
*/
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import demo6.co.kr.vo.UserVO;

@EnableWebMvc
@Component
public class SocketHandler extends TextWebSocketHandler {
	
	List<WebSocketSession>sessions = new CopyOnWriteArrayList<>(); 
	List<UserVO> users = new CopyOnWriteArrayList<>();
	UserVO user = new UserVO();
	
	
	private static final String JOINCALL = "join_call";
	private static final String USER = "store_user";
	private static final String CANDIDATE = "candidate";
	private static final String OFFER = "offer";
	private static final String ANSWER = "answer";
	
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws InterruptedException, IOException, ParseException {
		
		
		System.out.println("====================== new handle Message ========================");
		System.out.println("getpaylodae :: "+message.getPayload());
		
		JSONParser jsonParse = new JSONParser();
		Object obj = jsonParse.parse(message.getPayload());
		JSONObject jsonObj = (JSONObject) obj;
		
		
		String datatype = "" + jsonObj.get("type");
		int currentUser = findCurrentUser(session);
		UserVO oppositeUser = findUser(session);
			
		switch(datatype) {
		
        case USER:
            System.out.println("store user work :: " + jsonObj.get("username"));
            
            String newUsername = "" + jsonObj.get("username");
            UserVO newUser = new UserVO();
            newUser.setconn(session);
            newUser.setusername(newUsername);
            System.out.println("new user :: " + newUser);
            users.add(newUser);
            System.out.println("size of users :: " + users.size());
            break;
        case OFFER:
        	System.out.println("json object :: "+jsonObj);
        	System.out.println("store offer work from :: " + jsonObj.get("username"));
        	users.get(currentUser).setoffer(message);
        	if (oppositeUser == null) return;	
            
            send(oppositeUser.getconn(), message);
            
            System.out.println(users.get(currentUser).getoffer().getPayload());
            break;
        
        case CANDIDATE:
        	System.out.println("get new candidate from ::" + users.get(currentUser).getusername());
        	users.get(currentUser).addcandidates(message);
        	if(users.get(currentUser).getcandidates() != null) {
        		System.out.println("user has candidates");
        		System.out.println("size of candidate :: " + users.get(currentUser).getcandidates().size());
        	}
        	
        	if (oppositeUser == null) return;
            send(oppositeUser.getconn(), message);
            break;	
        
        case ANSWER:               
            if (oppositeUser == null) return;

            send(oppositeUser.getconn(), message);
            break;
        case JOINCALL: 
        	if(oppositeUser == null) return;
            System.out.println("oppositeUser is " + oppositeUser.getusername());
            System.out.println("offer data :: " + oppositeUser.getoffer().getPayload());
            
            send(session, oppositeUser.getoffer());
           
            for(TextMessage candidate : oppositeUser.getcandidates()) {
            	System.out.println("candidate data :: " + candidate);
            	send(session, candidate);
            }
           
            break;
       
		}
	}
	
	
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
	    sessions.add(session);
	    System.err.println("session connected +=" + session);
	}
	
	public void send(WebSocketSession session, TextMessage textMessage) throws IOException {
		session.sendMessage(textMessage);
	}
	
	
	
	public UserVO findUser(WebSocketSession session) {
	    System.out.println("findUser Works");
		for (int i = 0;i < users.size();i++) {
	        if(session != users.get(i).getconn()) {
	        	return users.get(i);
	        }
	    }
		return null;
	}
	
	public int findCurrentUser(WebSocketSession session) {
		for(int i =0; i < users.size(); i++) {
			if(session == users.get(i).getconn()) {
				return i;
			}
		}
		return 0;
	}
	 
}
