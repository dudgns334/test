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

//å �߰�, ���� ȭ��
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
		add(new JLabel(" å�̸�"));
		add(title);
		add(new JLabel(" �۰�"));
		add(author);
		add(new JLabel(" ���ǻ�")); 
		add(publisher);
		add(new JLabel(" ����"));
		add(price);
		add(new JLabel(" isbn"));
		add(isbn);
		add(new JLabel(" ���ǳ⵵(YYYY)"));
		add(birth);
		add(new JLabel(" ����"));
		add(num);
		add(ok);
		add(cancel);
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(title.getText().length() == 0 || author.getText().length() == 0 || publisher.getText().length() == 0 || price.getText().length() == 0 || isbn.getText().length() == 0 || birth.getText().length() == 0 || num.getText().length() == 0){
					JOptionPane.showMessageDialog(null, "����� �Է��� �ּ���", "���", JOptionPane.WARNING_MESSAGE);
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
