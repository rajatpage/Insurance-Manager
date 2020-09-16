package newLearning;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

public class AdministratorGuiImpl extends JFrame implements AdministratorGui{
	
	private AdministratorAgent myAgent;
	private JTextField userIdTF,userNameTF,passwordTF;
	private JButton addUserB, removeUserB, resetB;//, exitB;
	private JTextArea logTA;
	private int counterTF;

	public AdministratorGuiImpl() {
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

		// ********	User Name ***********************
		l = new JLabel("User Name:");

		l.setHorizontalAlignment(SwingConstants.LEFT);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new Insets(5, 3, 0, 3);
		rootPanel.add(l, gridBagConstraints);		

		userNameTF = new JTextField(64);
		userNameTF.setMinimumSize(new Dimension(222, 20));
		userNameTF.setPreferredSize(new Dimension(222, 20));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new Insets(5, 3, 0, 3);
		rootPanel.add(userNameTF, gridBagConstraints);


		// ********	Password *************************

		l = new JLabel("Password:");
		l.setHorizontalAlignment(SwingConstants.LEFT);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new Insets(5, 3, 0, 3);
		rootPanel.add(l, gridBagConstraints);

		passwordTF = new JTextField(64);
		passwordTF.setMinimumSize(new Dimension(222, 20));
		passwordTF.setPreferredSize(new Dimension(222, 20));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new Insets(5, 3, 0, 3);
		rootPanel.add(passwordTF, gridBagConstraints);

		//		****************type of user**************************

		l = new JLabel("What are you?");
		l.setHorizontalAlignment(SwingConstants.LEFT);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new Insets(5, 3, 0, 3);
		rootPanel.add(l, gridBagConstraints);

		final JRadioButton optionUser = new JRadioButton("User", true);
		final JRadioButton optionProviders = new JRadioButton("Providers");
		ButtonGroup group = new ButtonGroup();
		group.add(optionUser);
		group.add(optionProviders);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridx = 1;
		rootPanel.add(optionUser,gridBagConstraints);
		gridBagConstraints.gridx = 2;
		rootPanel.add(optionProviders,gridBagConstraints);
			

		// **********to appear on panel *************************
		rootPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		getContentPane().add(rootPanel, BorderLayout.NORTH);

		// ********	Text Area *************************
		logTA = new JTextArea();
		logTA.setEnabled(false);
		logTA.setDisabledTextColor(Color.RED);
		JScrollPane jsp = new JScrollPane(logTA);
		jsp.setMinimumSize(new Dimension(300, 180));
		jsp.setPreferredSize(new Dimension(300, 180));
		JPanel p = new JPanel();
		p.setBorder(new BevelBorder(BevelBorder.LOWERED));
		p.add(jsp);
		getContentPane().add(p, BorderLayout.CENTER);

		

		// ********	add Button *************************
		p = new JPanel();
		addUserB = new JButton("ADD");
		addUserB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				String userName = userNameTF.getText();
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
				
				try {
					if(myAgent.addUers(userId, userName, userPassword, userType)) {
						notifyUser(userType + " with Id: "+userId+", name: "+userName+" is added.            : "+userPassword);
					}
					else {
						JOptionPane.showMessageDialog(AdministratorGuiImpl.this, "User ID already exists", "WARNING", JOptionPane.WARNING_MESSAGE);
					}
				}
				catch(Exception ex) {
					JOptionPane.showMessageDialog(AdministratorGuiImpl.this, "Errors", "WARNING", JOptionPane.WARNING_MESSAGE);
				}
			}
		} );

		// ********	Remove Button *************************

		removeUserB = new JButton("Remove");
		removeUserB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String userId = userIdTF.getText();
				String userName = userNameTF.getText();
				String userPassword = passwordTF.getText();

				myAgent.removeUers(userId, userName, userPassword);
				notifyUser("Removed User with: "+userId+", name: "+userName+" is also removed from group: "+userPassword);

			}
		} );

		// ********	Reset Button *************************
		resetB = new JButton("Reset");
		resetB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				userIdTF.setText("");
				userNameTF.setText("");
				passwordTF.setText("");
				optionUser.setSelected(true);
//				myAgent.showNames();
			}
		} );
		p.add(addUserB);	
		p.add(removeUserB);
		p.add(resetB);
		p.setBorder(new BevelBorder(BevelBorder.LOWERED));
		getContentPane().add(p, BorderLayout.SOUTH);
		pack();
		setResizable(false);
	}

	public void setAgent(AdministratorAgent administratorAgent) {
		myAgent = administratorAgent;
		this.setTitle("Admin logged in as: "+myAgent.getLocalName());
	}

	public void notifyUser(String message) {
		logTA.append(message+"\n");
		counterTF++;
	}
	public void notifyUserWithDialogueBox(String message) {
		JOptionPane.showMessageDialog(AdministratorGuiImpl.this, message, "WARNING", JOptionPane.WARNING_MESSAGE);
	}

	@Override
	public void reset() {
	}

	@Override
	public void removeLastEntry() {
		logTA.remove(counterTF);
		counterTF--;
	}

}
