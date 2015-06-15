package book;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

//회원 확인, s_ModalDialog 상속
class CheckIdModalDialog extends s_ModalDialog{

	JTextField id = new JTextField(10);
	JTextField password = new JTextField(10);

	public CheckIdModalDialog(JFrame frame, String title) {
		super(frame,title, true);	
		GridLayout grid = new GridLayout(3, 2);
		grid.setVgap(5);
		setLayout(grid);
		add(new JLabel(" ID"));
		add(id);
		add(new JLabel(" 비밀번호")); 
		add(password);
		add(ok);
		add(cancel);
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if( id.getText().length() == 0 || password.getText().length() == 0 ){
					JOptionPane.showMessageDialog(null, "제대로 입력해 주세요", "경고", JOptionPane.WARNING_MESSAGE);
				}
				else {
					i = 0;
					setVisible(false);
				}
			}
		});
		cancel.addActionListener(new cancelActionListener());
		setSize(300, 200);
		setVisible(true);
	}
	
	public String getId(){
		return id.getText();
	}
	
	public String getPassword(){
		return password.getText();
	}

}

public class BookGUI extends JFrame{
	
	ArrayList<Book> book = new ArrayList<Book>();
	ArrayList<BookRent> bookrent = new ArrayList<BookRent>();
	ArrayList<Client> client = new ArrayList<Client>();
	
	JComboBox l1;
	
	JTextField text = new JTextField(15);
	JPanel jp = new JPanel();
	JPanel tp = new JPanel();
	
	JButton jb = new JButton("검색");
	JButton jbrent = new JButton("대여");
	JButton jbreturn = new JButton("반납");
	JButton jbshow = new JButton("전체보기");

	JTable table;
	DefaultTableModel model;
	JScrollPane js;
	
	String n1[] = {"이름", "작가", "출판사","출판년도", "isbn","정보 검색"};
	String n2[] = {"이름","작가","출판사","출판년도","ISBN"};
	
	BookGUI(){
				
		BorderLayout border = new BorderLayout();
		FlowLayout flow = new FlowLayout();
		
		model = new DefaultTableModel(n2,0){
			public boolean isCellEditable(int rowindex,int Collenindex){
				return false;
			}
		};
		table = new JTable(model);
		table.getTableHeader().setReorderingAllowed(false);
		
		js = new JScrollPane(table);
		
		setTitle("Book Manager");
		setLayout(border);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createmenu();
		add(jp,"North");
		add(js,"Center");
		jp.setLayout(flow);
		
		l1 = new JComboBox(n1);
		
		jp.add(l1);
		jp.add(text);
		jp.add(jb);
		jp.add(jbrent);
		jp.add(jbreturn);
		jp.add(jbshow);
		
		jb.addActionListener(new jbActionListener());
		jbrent.addActionListener(new MyActionListener());
		jbreturn.addActionListener(new MyActionListener());
		jbshow.addActionListener(new MyActionListener());
		
		setSize(600,450);
		setVisible(true);
		
		start();
		
	}
	
	//테이블 행 추가
	void addrow(Book br){
		Vector<String> v = new Vector<String>();
		v.addElement(br.getTitle());
		v.addElement(br.getAuthor());
		v.addElement(br.getPublisher());
		v.addElement(Integer.toString(br.getBirth()));
		v.addElement(br.getIsbn());
		model.addRow(v);
	}
	
	//테이블 초기화
	void clearrow(){
		int row = model.getRowCount();
		for(int i = 0 ; i < row ; i++){
			model.removeRow(0);
		}
	}
	
	//대출, 반납, 보여주기 버튼 리스너
	class MyActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == jbshow){
				int row = model.getRowCount();
				int size = book.size();
				for(int i = 0 ; i < row ; i++){
					model.removeRow(0);
				}
				for(int i = 0 ; i < size ; i++){
					addrow(book.get(i));
				}
			}
			else if(e.getSource() == jbrent){
				checkid_rent();
			}
			else if(e.getSource() == jbreturn){
				checkid_return();
			}
		}
	}
	void checkid_rent(){
		CheckIdModalDialog modal = new CheckIdModalDialog(this, "회원 확인");
		if(modal.OK() == 0){
			String id = modal.getId();
			String password = modal.getPassword();
			int j = findclient(id);
			if(j == -1){
				JOptionPane.showMessageDialog(null, "일치하는 ID가 없습니다.", "경고", JOptionPane.WARNING_MESSAGE);
			}
			else if(!client.get(j).getPassword().equals(password)){
				JOptionPane.showMessageDialog(null, "비밀번호가 잘못 되었습니다.", "경고", JOptionPane.WARNING_MESSAGE);
			}
			else{
				String title = JOptionPane.showInputDialog("빌릴 책 이름을 입력하세요.");
				int n = findbook(title,"이름");
				if( n == -1 ){
					JOptionPane.showMessageDialog(null, "일치하는 책이 없습니다.", "경고", JOptionPane.WARNING_MESSAGE);
				}
				else{
					if(book.get(n).getRent_o() == 0){
						JOptionPane.showMessageDialog(null, "빌릴 수 있는 책이 없습니다.", "경고", JOptionPane.WARNING_MESSAGE);
					}
					else{
						BookRent br = new BookRent(title,client.get(j));
						if(client.get(j).rentbook(br) == 0){
							book.get(n).bookrent(br);
							bookrent.add(br);
						    JOptionPane.showMessageDialog(null, "대출완료", "알림", JOptionPane.PLAIN_MESSAGE);
						}
						else{
							JOptionPane.showMessageDialog(null, "이미 다섯권을 빌렸습니다.", "경고", JOptionPane.WARNING_MESSAGE);							
						}
					}
				}
			}
		}
	}
	
	void checkid_return(){
		CheckIdModalDialog modal = new CheckIdModalDialog(this, "회원 확인");
		if(modal.OK() == 0){
			String id = modal.getId();
			String password = modal.getPassword();
			int j = findclient(id);
			if(j == -1){
				JOptionPane.showMessageDialog(null, "일치하는 ID가 없습니다.", "경고", JOptionPane.WARNING_MESSAGE);
			}
			else if(!client.get(j).getPassword().equals(password)){
				JOptionPane.showMessageDialog(null, "비밀번호가 잘못 되었습니다.", "경고", JOptionPane.WARNING_MESSAGE);
			}
			else{
				String title = JOptionPane.showInputDialog("반납할 책 이름을 입력하세요.");
				int n = findbook(title,"이름");
				if( n == -1 ){
					JOptionPane.showMessageDialog(null, "일치하는 책이 없습니다.", "경고", JOptionPane.WARNING_MESSAGE);
				}
				else{
					int k = -1;
					for(int a = 0 ; a < bookrent.size() ; a++){
						if(bookrent.get(a).getId().equals(id) && bookrent.get(a).getTitle().equals(title)){
							k = a;
							break;
						}
					}
					if( k == -1 ){
						JOptionPane.showMessageDialog(null, "일치하는 책이 없습니다.", "경고", JOptionPane.WARNING_MESSAGE);
					}
					else{
						client.get(j).returnbook(bookrent.get(k));
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
						Date date = new Date();
					    String today = df.format(date);
					    bookrent.get(k).setEnd_d(today);
					    book.get(n).bookreturn(bookrent.get(k), today);
					    JOptionPane.showMessageDialog(null, "반납완료", "알림", JOptionPane.PLAIN_MESSAGE);
					}
				}
			}
		}
	}

	
	//시작 데이터 추가
	void start(){
		bookload();
		bookrentload();
		clientload();
	}
	
	//책 불러오기
	void bookload(){
		FileInputStream fin = null;
		ObjectInputStream ois = null;
		
		ArrayList list = new ArrayList();

		try{
			fin = new FileInputStream("booklist.dat");
			ois = new ObjectInputStream(fin);

			list = (ArrayList)ois.readObject();
			
			int size = list.size();

			for(int i = 0 ; i < size ; i++){
				book.add((Book)list.get(i));
				addrow(book.get(i));
			}
		}catch(Exception ex){
		}finally{
			try{
				ois.close();
				fin.close();
			}catch(IOException ioe){}
		}
	}

	//대출 목록 불러오기
	void bookrentload(){
		
		FileInputStream finr = null;
		ObjectInputStream oisr = null;
		
		ArrayList listr = new ArrayList();
		
		try{
			finr = new FileInputStream("rentlist.dat");
			oisr = new ObjectInputStream(finr);
			
			listr = (ArrayList)oisr.readObject();

			int sizer = listr.size();
			
			for(int i = 0 ; i < sizer ; i++){
				bookrent.add((BookRent)listr.get(i));
			}
		}catch(Exception ex){
		}finally{
			try{
				oisr.close();
				finr.close();
			}catch(IOException ioe){}
		} // finally
	}
	
	void clientload(){
		FileInputStream finc = null;
		ObjectInputStream oisc = null;
		
		ArrayList listc = new ArrayList();
		
		try{
			finc = new FileInputStream("clientlist.dat");
			oisc = new ObjectInputStream(finc);
			
			listc = (ArrayList)oisc.readObject();

			int sizec = listc.size();
			
			for(int i = 0 ; i < sizec ; i++){
				client.add((Client)listc.get(i));
			}
		}catch(Exception ex){
		}finally{
			try{
				oisc.close();
				finc.close();
			}catch(IOException ioe){}
		} // finally

	}
	//메뉴생성
	void createmenu(){
		JMenuBar mb = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		JMenu bookmenu = new JMenu("책관리");
		JMenu clientmenu = new JMenu("회원관리");

		JMenuItem sv = new JMenuItem("Save");
		JMenuItem bookload = new JMenuItem("책파일추가");
		JMenuItem clientload = new JMenuItem("회원파일추가");
		
		JMenuItem bookadd = new JMenuItem("책추가");
		JMenuItem bookset = new JMenuItem("책수정");
		JMenuItem bookremove = new JMenuItem("책삭제");
		JMenuItem booksearch = new JMenuItem("책정보");
		
		JMenuItem clientadd = new JMenuItem("회원추가");
		JMenuItem clientset = new JMenuItem("회원수정");
		JMenuItem clientremove = new JMenuItem("회원삭제");
		JMenuItem clientsearch = new JMenuItem("회원검색");
		
		fileMenu.add(sv);
		fileMenu.add(bookload);
		fileMenu.add(clientload);
		sv.addActionListener(new svActionListener());
		bookload.addActionListener(new blActionListener());
		clientload.addActionListener(new clActionListener());
		
		bookmenu.add(bookadd);
		bookmenu.add(bookset);
		bookmenu.add(bookremove);
		bookmenu.add(booksearch);
		bookadd.addActionListener(new baActionListener());
		bookset.addActionListener(new bsActionListener());
		bookremove.addActionListener(new brActionListener());
		booksearch.addActionListener(new bsrActionListener());
		
		clientmenu.add(clientadd);
		clientmenu.add(clientset);
		clientmenu.add(clientremove);
		clientmenu.add(clientsearch);
		clientadd.addActionListener(new caActionListener());
		clientset.addActionListener(new csActionListener());
		clientremove.addActionListener(new crActionListener());
		clientsearch.addActionListener(new csrActionListener());
		
		mb.add(fileMenu);
		mb.add(bookmenu);
		mb.add(clientmenu);
		setJMenuBar(mb);
	}
	
	//책 파일 읽기 버튼
	class blActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			File f;
			JFileChooser fc = new JFileChooser();
			int answer = fc.showOpenDialog(null);
			if(answer == JFileChooser.APPROVE_OPTION){
				f = fc.getSelectedFile();
				breaddata(f);
			}
		}
	}
	
	//책 파일 읽기, 추가
	void breaddata(File f){
		FileInputStream fin = null;
		ObjectInputStream ois = null;
		ArrayList list = new ArrayList();
		try{
			fin = new FileInputStream(f);
			ois = new ObjectInputStream(fin);
			
			list = (ArrayList)ois.readObject();
			for(int i = 0 ; i < list.size() ; i++){
				book.add((Book)list.get(i));
			}
		}catch(Exception ex){
		}finally{
			try{
				ois.close();
				fin.close();
			}catch(IOException ioe){}
		} // finally
	}
	
	//고객 파일 읽기 버튼
	class clActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			File f;
			JFileChooser fc = new JFileChooser();
			int answer = fc.showOpenDialog(null);
			if(answer == JFileChooser.APPROVE_OPTION){
				f = fc.getSelectedFile();
				creaddata(f);
			}
		}
	}
	
	//고객 파일 읽기, 추가
	void creaddata(File f){
		FileInputStream fin = null;
		ObjectInputStream ois = null;
		ArrayList list = new ArrayList();
		try{
			fin = new FileInputStream(f);
			ois = new ObjectInputStream(fin);
			
			list = (ArrayList)ois.readObject();
			for(int i = 0 ; i < list.size() ; i++){
				client.add((Client)list.get(i));
			}
		}catch(Exception ex){
		}finally{
			try{
				ois.close();
				fin.close();
			}catch(IOException ioe){}
		} // finally
	}	
	
	//저장 버튼
	class svActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			savedata();
		}
	}
	
	//저장
	void savedata(){
		FileOutputStream fout_b = null;
		ObjectOutputStream oos_b = null;
		FileOutputStream fout_r = null;
		ObjectOutputStream oos_r = null;
		FileOutputStream fout_c = null;
		ObjectOutputStream oos_c = null;
		ArrayList list_b = new ArrayList();
		ArrayList list_r = new ArrayList();
		ArrayList list_c = new ArrayList();

		try{
			fout_b = new FileOutputStream("booklist.dat");
			oos_b = new ObjectOutputStream(fout_b);
			fout_r = new FileOutputStream("rentlist.dat");
			oos_r = new ObjectOutputStream(fout_r);
			fout_c = new FileOutputStream("clientlist.dat");
			oos_c = new ObjectOutputStream(fout_c);
			int size_b = book.size();
			int size_r = bookrent.size();
			int size_c = client.size();
			for(int i = 0 ; i < size_b ; i++ ){
				list_b.add(book.get(i));
			}
			for(int i = 0 ; i < size_r ; i++ ){
				list_r.add(bookrent.get(i));
			}
			for(int i = 0 ; i < size_c ; i++ ){
				list_c.add(client.get(i));
			}
			for(int i = 0 ; i < 2 ; i++){
					oos_b.writeObject(list_b);//파일 입력의 법칙에 따라 두번 한 것임.
					oos_b.reset();
					oos_r.writeObject(list_r);
					oos_r.reset();
					oos_c.writeObject(list_c);
					oos_c.reset();
			}
			
			System.out.println("저장되었습니다.");
			
		}catch(Exception ex){
		}finally{
			try{
				oos_b.close();
				fout_b.close();
				oos_r.close();
				fout_r.close();
				oos_c.close();
				fout_c.close();
				JOptionPane.showMessageDialog(null, "저장완료", "알림", JOptionPane.PLAIN_MESSAGE);
			}catch(IOException ioe){}
		} // finally
	}

	class jbActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String s = text.getText();
			String t = (String)l1.getSelectedItem();
			searchbook(s,t);
		}
	}
	
	//책 추가
	void bookmenu(){
		BookModalDialog dialog = new BookModalDialog(this,"책");
		if( dialog.OK() == 0){
			if(dialog.getTitle() != null){
				int i = findbook(dialog.getTitle(),"이름");
				int j = findbook(dialog.getIsbn(),"isbn");
				if(i == -1 && j == -1){
					String isbn = dialog.getIsbn();
					String title = dialog.getTitle();
					String author = dialog.getAuthor();
					int price = dialog.getPrice();
					int birth = dialog.getBirth();
					int num = dialog.getNum();
					String publisher = dialog.getPublisher();
					book.add(new Book(title,author,publisher,price,isbn,birth,num));
					addrow(book.get(book.size()-1));
				}
				else if( j != -1){
					JOptionPane.showMessageDialog(null, "isbn이 같은 책이 이미 있습니다.", "경고", JOptionPane.WARNING_MESSAGE);
				}
				else{
					JOptionPane.showMessageDialog(null, "이름이 같은 책이 이미 있습니다.", "경고", JOptionPane.WARNING_MESSAGE);
				}
			}
		}
	}
	
	//책 수정
	void bookmenu(int n){
		BookModalDialog dialog = new BookModalDialog(this,"책");
		if(dialog.OK() == 0 ){
			if(dialog.getTitle() != null){
				int i = findbook(dialog.getTitle(),"이름");
				if(i == -1 || i == n){
					String isbn = dialog.getIsbn();
					String title = dialog.getTitle();
					String author = dialog.getAuthor();
					int price = dialog.getPrice();
					int birth = dialog.getBirth();
					int num = dialog.getNum();
					book.get(n).setAuthor(author);
					book.get(n).setBirth(birth);
					book.get(n).setIsbn(isbn);
					book.get(n).setNum(num);
					book.get(n).setPrice(price);
					book.get(n).setTitle(title);
					String s_price = Integer.toString(price);
					int row = model.getRowCount();
					clearrow();
					for(int j = 0 ; j < book.size() ; j++){
						addrow(book.get(j));
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "책이 이미 있습니다.", "경고", JOptionPane.WARNING_MESSAGE);
				}
			}
		}
	}
	
	//책 추가 버튼
	class baActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			bookmenu();
		}
	}
	
	//책 수정 버튼
	class bsActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String title = JOptionPane.showInputDialog("책 이름을 입력하세요.");
			if(title != null ){
				int i = findbook(title,"이름");
				if (i == -1){
					JOptionPane.showMessageDialog(null, "책이 없습니다.", "경고", JOptionPane.WARNING_MESSAGE);
				}
				else{
					bookmenu(i);
				}
			}
		}
	}
	
	//책 제거 버튼
	class brActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String title = JOptionPane.showInputDialog("책 이름을 입력하세요.");
			int i = -1;
			if(title != null ){
				i = findbook(title,"이름");
				if(i == -1){
					JOptionPane.showMessageDialog(null, "책이 없습니다.", "경고", JOptionPane.WARNING_MESSAGE);
				}
				else{
					book.remove(i);
				}
			}
			clearrow();
			int size = book.size();
			for(int j = 0 ; j < size ; j++){
				addrow(book.get(j));
			}
		}
	}
	
	//책 정보보여주기
	class bsrActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String title = JOptionPane.showInputDialog("책 이름을 입력하세요.");
			if(title != null ){
				int i = findbook(title,"이름");
				if (i == -1){
					JOptionPane.showMessageDialog(null, "책이 없습니다.", "경고", JOptionPane.WARNING_MESSAGE);
				}
				else{
					String message = book.get(i).toString();
					JOptionPane.showMessageDialog(null, message, "책정보", JOptionPane.PLAIN_MESSAGE);
				}
			}
		}
	}
	//고객 추가
	void clientmenu(){
		ClientModalDialog dialog = new ClientModalDialog(this,"고객");
		if(dialog.OK() == 0){
			int i = findclient(dialog.getId());
			if(i == -1 ){
				String id = dialog.getId();
				String name = dialog.getName();
				String password = dialog.getPassword();
				client.add(new Client(name , id ,password));
			}
			else{
				JOptionPane.showMessageDialog(null, "ID가 이미 있습니다.", "경고", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	//고객 수정
	void clientmenu(int n){
		ClientModalDialog dialog = new ClientModalDialog(this,"고객");
		if(dialog.OK() == 0){
			int i = findclient(dialog.getId());
			if(i == -1 ){
				String id = dialog.getId();
				String name = dialog.getName();
				String password = dialog.getPassword();
				client.set(n ,new Client(name , id ,password));
			}
			else{
				JOptionPane.showMessageDialog(null, "ID가 이미 있습니다.", "경고", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	//고객 추가 버튼
	class caActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			clientmenu();
		}
	}
	
	//고객 수정 버튼
	class csActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String name = JOptionPane.showInputDialog("ID를 입력하세요.");
			int i = -1;
			if(name != null ){
				i = findclient(name);
				if(i == -1){
					JOptionPane.showMessageDialog(null, "고객이 없습니다.", "경고", JOptionPane.WARNING_MESSAGE);
				}
				else{
					clientmenu(i);					
				}
			}
		}
	}
	
	//고객 삭제 버튼
	class crActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String id = JOptionPane.showInputDialog("ID를 입력하세요.");
			int i = -1;
			if(id != null ){
				i = findclient(id);
				if(i == -1){
					JOptionPane.showMessageDialog(null, "고객이 없습니다.", "경고", JOptionPane.WARNING_MESSAGE);
				}
				else{
					client.remove(i);
				}
			}
		}
	}
	
	class csrActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			String title = JOptionPane.showInputDialog("고객 ID를 입력하세요.");
			if(title != null ){
				int i = findclient(title);
				if (i == -1){
					JOptionPane.showMessageDialog(null, "고객이 없습니다.", "경고", JOptionPane.WARNING_MESSAGE);
				}
				else{
					String message = client.get(i).toString();
					JOptionPane.showMessageDialog(null, message, "고객정보", JOptionPane.PLAIN_MESSAGE);
				}
			}
		}
	}

	//id로 고객 확인
	int findclient(String id){
		int j = -1;
		int size = client.size();
		for(int i = 0 ; i < size ; i++){
			if(id.equals(client.get(i).getId())){
				j = i;
				break;
			}
		}
		return j;
	}
	//책 존재 확인
	int findbook(String s, String t){
		int n = book.size();
		int i;
		int j = -1;
		if(t.equals("이름")){
			for( i = 0 ; i < n ; i++){
				if(s.equals(book.get(i).getTitle())){
					j = i;
					break;
				}
			}
		}
		else if(t.equals("작가")){
			for( i = 0 ; i < n ; i++){
				if(s.equals(book.get(i).getAuthor())){
					j = i;
					break;
				}
			}
		}
		else if(t.equals("츨판사")){
			for( i = 0 ; i < n ; i++){
				if(s.equals(book.get(i).getPublisher())){
					j = i;
					break;
				}
			}
		}
		else if (t.equals("출판년도")){
			for( i = 0 ; i < n ; i++){
				if(s.equals(book.get(i).getBirth())){
					j = i;
					break;
				}
			}
		}
		else{
			for( i = 0 ; i < n ; i++){
				if(s.equals(book.get(i).getIsbn())){
					j = i;
					break;
				}
			}
		}
		return j;
	}
	
	//책검색
	void searchbook(String s , String t){
		if(t.equals("정보 검색")){
			String title = text.getText();
			if(title != null ){
				int i = findbook(title,"이름");
				if( i != -1){
					String message = book.get(i).toString();
					JOptionPane.showMessageDialog(null, message, "책정보", JOptionPane.PLAIN_MESSAGE);
					return;
				}
			}
		}
		int row = model.getRowCount();
		int size = book.size();
		clearrow();
		if(t.equals("이름")){
			for(int z = 0 ; z < size ; z++ ){
				if(book.get(z).getTitle().indexOf(s) > -1){
					addrow(book.get(z));
				}
			}
		}
		else if(t.equals("작가")){
			for(int z = 0 ; z < size ; z++ ){
				if(s.indexOf(book.get(z).getAuthor()) > -1){
					addrow(book.get(z));
				}
			}
		}
		else if(t.equals("출판사")){
			for(int z = 0 ; z < size ; z++ ){
				if(s.indexOf(book.get(z).getPublisher()) > -1){
					addrow(book.get(z));
				}
			}
		}
		else if(t.equals("ISBN")){
			for(int z = 0 ; z < size ; z++ ){
				if(s.indexOf(book.get(z).getIsbn()) > -1){
					addrow(book.get(z));
				}
			}
		}
		else {
			for(int z = 0 ; z < size ; z++ ){
				if(s.indexOf(book.get(z).getBirth()) > -1){
					addrow(book.get(z));
				}
			}
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BookGUI bg = new BookGUI();
	}

}
