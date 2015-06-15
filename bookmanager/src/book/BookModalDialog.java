package book;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

//책 추가, 수정 화면
class BookModalDialog extends s_ModalDialog{
	JTextField title = new JTextField(10);
	JTextField author = new JTextField(10);
	JTextField publisher = new JTextField(10);
	JTextField price = new JTextField(10);
	JTextField isbn = new JTextField(10);
	JTextField birth = new JTextField(10);
	JTextField num = new JTextField(10);
	
	public BookModalDialog(JFrame frame, String name) {
		super(frame,name, true);
		GridLayout grid = new GridLayout(8, 2);
		grid.setVgap(5);
		setLayout(grid);
		add(new JLabel(" 책이름"));
		add(title);
		add(new JLabel(" 작가"));
		add(author);
		add(new JLabel(" 출판사")); 
		add(publisher);
		add(new JLabel(" 가격"));
		add(price);
		add(new JLabel(" isbn"));
		add(isbn);
		add(new JLabel(" 출판년도(YYYY)"));
		add(birth);
		add(new JLabel(" 수량"));
		add(num);
		add(ok);
		add(cancel);
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(title.getText().length() == 0 || author.getText().length() == 0 || publisher.getText().length() == 0 || price.getText().length() == 0 || isbn.getText().length() == 0 || birth.getText().length() == 0 || num.getText().length() == 0){
					JOptionPane.showMessageDialog(null, "제대로 입력해 주세요", "경고", JOptionPane.WARNING_MESSAGE);
				}
				else {
					i = 0;
					setVisible(false);
				}
			}
		});
		cancel.addActionListener(new cancelActionListener());
		setSize(480, 320);
		setVisible(true);
	}
	
	public String getAuthor() {
		return author.getText();
	}

	public String getIsbn() {
		return isbn.getText();
	}

	public int getPrice() {
		return Integer.parseInt(price.getText());
	}

	public String getTitle() {
		return title.getText();
	}
	
	public int getBirth(){
		return Integer.parseInt(birth.getText());
	}
	
	public String getPublisher(){
		return publisher.getText();
	}
	
	public int getNum(){
		return Integer.parseInt(num.getText());
	}
	
}
