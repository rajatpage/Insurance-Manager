package newLearning;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

public class LoginGuiImpl extends JFrame implements LoginGui{

	private LoginAgent myAgent;
	private JTextField userIdTF,passwordTF;
	private JButton loginB;//, exitB;
	private JTextArea logTA;
	private int counterTF;

	public LoginGuiImpl() {
		super();

		addWindowListener(new   WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				myAgent.doDelete();
			}

		} );

		JPanel rootPanel = new JPanel();
		rootPanel.setLayout(new GridBagLayout());
		rootPanel.setMinimumSize(new Dimension(330, 125));
		rootPanel.setPreferredSize(new Dimension(330, 125));
		JLabel l = new JLabel("User ID:");

		l.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new Insets(5, 3, 0, 3);
		rootPanel.add(l, gridBagConstraints);

		userIdTF = new JTextField(64);
		userIdTF.setMinimumSize(new Dimension(222, 20));
		userIdTF.setPreferredSize(new Dimension(222, 20));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new Insets(5, 3, 0, 3);
		rootPanel.add(userIdTF, gridBagConstraints);
		// ********	Password *************************



		l = new JLabel("Password:");
		l.setHorizontalAlignment(SwingConstants.LEFT);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new Insets(5, 3, 0, 3);
		rootPanel.add(l, gridBagConstraints);

		passwordTF = new JTextField(64);
		passwordTF.setMinimumSize(new Dimension(222, 20));
		passwordTF.setPreferredSize(new Dimension(222, 20));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new Insets(5, 3, 0, 3);
		rootPanel.add(passwordTF, gridBagConstraints);

		l = new JLabel("What are you?");
		l.setHorizontalAlignment(SwingConstants.LEFT);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new Insets(5, 3, 0, 3);
		rootPanel.add(l, gridBagConstraints);

		final JRadioButton optionUser = new JRadioButton("User", true);
		final JRadioButton optionProviders = new JRadioButton("Providers");
		ButtonGroup group = new ButtonGroup();
		group.add(optionUser);
		group.add(optionProviders);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridx = 1;
		rootPanel.add(optionUser,gridBagConstraints);
		gridBagConstraints.gridx = 2;
		rootPanel.add(optionProviders,gridBagConstraints);

		// **********to appear on panel *************************
		rootPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		getContentPane().add(rootPanel, BorderLayout.NORTH);

		//************Login Button *****************************
		JPanel p = new JPanel();
		loginB = new JButton("Login");
		loginB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {

				String userPassword = passwordTF.getText();
				String userType = "";
				if (optionProviders.isSelected()) 
				{
					userType = "provider";
				}
				else
				{
					userType = "user";
				}
				String userId = userType.toUpperCase().charAt(0)+"-"+userIdTF.getText();
				if(userPassword.isEmpty() || userId.isEmpty() || userId==null ||userPassword==null) {
					JOptionPane.showMessageDialog(LoginGuiImpl.this, "Fill all details", "WARNING", JOptionPane.WARNING_MESSAGE);
				} else {
					myAgent.ValidateUser(userId, userPassword);
				}
			}
		} );
		p.add(loginB);	
		p.setBorder(new BevelBorder(BevelBorder.LOWERED));
		getContentPane().add(p, BorderLayout.SOUTH);
		pack();
		setResizable(false);
	}
	@Override
	public void setAgent(LoginAgent loginAgent) {
		// TODO Auto-generated method stub
		myAgent = loginAgent;
		this.setTitle("Login Agent");
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	public void notifyUserWithDialogueBox(String message) {
		JOptionPane.showMessageDialog(LoginGuiImpl.this, message, "WARNING", JOptionPane.WARNING_MESSAGE);
	}
}
