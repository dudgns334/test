package book;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Client implements Serializable{

	String name;
	String id;
	String password;
	String date;
	ArrayList<BookRent> rentlist = new ArrayList<BookRent>();
	
	Client(String name, String id, String password){
		this.name = name;
		this.id = id;
		this.password = password;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
	    String today = df.format(date);
	    this.date = today;
	}
	
	public int rentbook(BookRent br){
		if(rentlist.size() > 6){
			return 1;
		}
		else{
			rentlist.add(br);
			return 0;
		}
	}
	
	public void returnbook(BookRent br){
		rentlist.remove(br);
	}
	
	public void seId(String id){
		this.id = id;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}

	public String getId(){
		return id;
	}
	
	public String getPassword(){
		return password;
	}
	
	public ArrayList<BookRent> getRentlist(){
		return rentlist;
	}
	
	public String toString(){
		String message = null;
		for(int i = 0 ; i < rentlist.size() ; i++){
			message += "\n\t"+(i+1)+". "+rentlist.get(i).getTitle();
		}
		return "이름 : "+name+"\nID : "+id+"\nPASSWORD : "+password+"\n가입 날짜 : "+date+"\n대출 목록"+message;
	}
	
}
