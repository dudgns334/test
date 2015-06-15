package book;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

public class s_ModalDialog extends JDialog{
	int i = 1;

	JButton ok = new JButton("확인");
	JButton cancel = new JButton("취소");

	class cancelActionListener  implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			setVisible(false);
		}
	}
	
	public s_ModalDialog(JFrame frame, String title, boolean b) {
		// TODO Auto-generated constructor stub
		super(frame,title, true);
	}

	public int OK(){
		return i;
	}

}
