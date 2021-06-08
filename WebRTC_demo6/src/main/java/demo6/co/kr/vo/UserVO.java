package demo6.co.kr.vo;

import java.util.ArrayList;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;



public class UserVO {
	String username;
	TextMessage offer;
	ArrayList<TextMessage> candidates = new ArrayList<TextMessage>();
	WebSocketSession conn;
	 
	public String getusername() {
		return username;
	}
	
	public TextMessage getoffer() {
		return offer;
	}
	
	public ArrayList<TextMessage> getcandidates() {
		return candidates;
	}
	
	public WebSocketSession getconn() {
		return conn;
	}
	
	public void setusername(String username) {
		this.username = username;
	}
	
	
	public void setoffer(TextMessage offer) {
		this.offer = offer;
	}
	
	public void addcandidates(TextMessage candidates) {
		this.candidates.add(candidates);
	}
	
	public void setcandidates() {
		this.candidates.clear();
	}

	public void setconn(WebSocketSession conn) {
		this.conn = conn;
	}
	
	public String toString() {
		return "UserVO [usename=" + username + "]";
	}
}
