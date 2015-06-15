package book;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BookRent implements Serializable{
	
	private String title;
	private Client person;
	private String start_d;
	private String end_d;
	
	
	BookRent(String title, Client person){
		this.title = title;
		this.person = person;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
	    String today = df.format(date);
	    start_d = today;
		
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getId(){
		return person.getId();
	}
	
	public void setEnd_d(String date){
		this.end_d = date;
	}
	
	//대출  상황 보여주기1
	public String show1(){
		return "책 이름 : "+title+"\n대출자 : "+person.getName()+"\n대출일 : "+start_d+"\n반납일 : "+end_d;
	}
	
	//대출상황 보여주기2
	public String show2(){
		return "대출자 : "+person.getName()+"\n대출일 : "+start_d+"\n반납일 : "+end_d;
	}
}
