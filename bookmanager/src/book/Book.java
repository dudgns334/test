package book;
import java.io.Serializable;
import java.util.ArrayList;

public class Book implements Serializable {// 시리얼(직렬)<-->pararell(병렬)시킬 수 있는 인터페이스가 있다는 의미.
	private String isbn;//직렬 병렬은 어딘가에 전송시킬 때 필요하다. 직렬은 그냥 일렬로 줄 세울 수 있다는 의미.
	private String title;
	private String author;
	private int price;
	private int birth;
	private String publisher;
	private int num;
	private int rent_o;
	private int rent_x;
	private ArrayList<BookRent> br = new ArrayList<BookRent>();

	public Book(String title, String author, String publisher, int price, String isbn, int birth, int num){//데이터클래스라 메인 없이 그냥 생성자
		this.publisher = publisher;
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.price = price;
		this.birth = birth;
		this.num = num;
		rent_o = num;
		rent_x = 0;
	}

	public String getAuthor() {
		return author;
	}

	public String getIsbn() {
		return isbn;
	}

	public int getPrice() {
		return price;
	}

	public String getTitle() {
		return title;
	}
	
	public int getBirth(){
		return birth;
	}
	
	public int getNum(){
		return num;
	}
	
	public String getPublisher(){
		return publisher;
	}
	
	//빌리기 가능 수
	public int getRent_o(){
		return rent_o;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setBirth(int birth) {
		this.birth = birth;
	}
	
	public void setNum(int num){
		this.num = num;
	}
	
	public void setPublisher(String publisher){
		this.publisher = publisher;
	}
	
	public void setRent_o(int rent_o){
		this.rent_o = rent_o;
		rent_x = num - rent_o;
	}
	
	public void setRent_x(int rent_x){
		this.rent_x = rent_x;
		rent_o = num - rent_x;
	}
	
	//책빌리기
	public void bookrent(BookRent a){
		rent_x++;
		rent_o--;
		br.add(a);
	}
	
	public void bookreturn(BookRent br , String date){
		int i = 0 ;
		for (int  n = 0 ; n <  this.br.size() ; n++){
			if(this.br.equals(br)){
				i = n;
				break;
			}
		}
		this.br.set(i, br);
	}
	//렌트목록 출력
	public String printbr(){
		String s = null;
		int size = br.size();
		if (size == 0 ){
			s = "대출 내역이 없습니다";
		}
		else{
			for (int i = 0 ; i < size ; i++){
				s = br.get(i).show2();
			}
		}
		return s;
	}
	
	//책 정보 출력
	public String toString(){// 오브젝트의 메소드 toString이므로 오버라이드 가능
		return "이름 : "+title+"\n저자 : "+author+"\n출판사 : "+publisher+"\n출판년도 : "+birth+"\nISBN : "+isbn+"\n가격 : "+price+"\n수량 : "+num+"\n대출가능 : "+rent_o;
	}

}
