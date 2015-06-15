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

//고객 추가, 수정 화면, s_ModelDialog 상속
class ClientModalDialog extends s_ModalDialog{
	
	JTextField name = new JTextField(10);
	JTextField id = new JTextField(10);
	JTextField password = new JTextField(10);
	
	public ClientModalDialog(JFrame frame, String title) {
		super(frame,title, true);
		GridLayout grid = new GridLayout(4, 2);
		grid.setVgap(5);
		setLayout(grid);
		add(new JLabel(" ID"));
		add(id);
		add(new JLabel(" 이름"));
		add(name);
		add(new JLabel(" 비밀번호")); 
		add(password);
		add(ok);
		add(cancel);
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(name.getText().length() == 0 || id.getText().length() == 0 || password.getText().length() == 0 ){
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
	
	public String getName(){
		return name.getText();
	}
	
	public String getPassword(){
		return password.getText();
	}
}
