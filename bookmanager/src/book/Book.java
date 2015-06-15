package book;
import java.io.Serializable;
import java.util.ArrayList;

public class Book implements Serializable {// �ø���(����)<-->pararell(����)��ų �� �ִ� �������̽��� �ִٴ� �ǹ�.
	private String isbn;//���� ������ ��򰡿� ���۽�ų �� �ʿ��ϴ�. ������ �׳� �Ϸķ� �� ���� �� �ִٴ� �ǹ�.
	private String title;
	private String author;
	private int price;
	private int birth;
	private String publisher;
	private int num;
	private int rent_o;
	private int rent_x;
	private ArrayList<BookRent> br = new ArrayList<BookRent>();

	public Book(String title, String author, String publisher, int price, String isbn, int birth, int num){//������Ŭ������ ���� ���� �׳� ������
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
	
	//������ ���� ��
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
	
	//å������
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
	//��Ʈ��� ���
	public String printbr(){
		String s = null;
		int size = br.size();
		if (size == 0 ){
			s = "���� ������ �����ϴ�";
		}
		else{
			for (int i = 0 ; i < size ; i++){
				s = br.get(i).show2();
			}
		}
		return s;
	}
	
	//å ���� ���
	public String toString(){// ������Ʈ�� �޼ҵ� toString�̹Ƿ� �������̵� ����
		return "�̸� : "+title+"\n���� : "+author+"\n���ǻ� : "+publisher+"\n���ǳ⵵ : "+birth+"\nISBN : "+isbn+"\n���� : "+price+"\n���� : "+num+"\n���Ⱑ�� : "+rent_o;
	}

}
