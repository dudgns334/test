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
	
	//����  ��Ȳ �����ֱ�1
	public String show1(){
		return "å �̸� : "+title+"\n������ : "+person.getName()+"\n������ : "+start_d+"\n�ݳ��� : "+end_d;
	}
	
	//�����Ȳ �����ֱ�2
	public String show2(){
		return "������ : "+person.getName()+"\n������ : "+start_d+"\n�ݳ��� : "+end_d;
	}
}
