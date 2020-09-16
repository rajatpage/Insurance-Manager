package newLearning;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import jade.gui.TimeChooser;

/*
 * Implementation class for User GUI called by User Agent
 */
public class UserGuiImpl extends JFrame implements UserGUI{

	UserAgent myAgent;

	AdministratorAgent administratorAgent;

	private JTextField maxPriceTF, timePeriodTF, coverageTF;
	private JButton initiateContractB,  checkMyInsuaranceB, findOptionB, resetB;//, exitB;
	private JTextArea logTA;

	String[] insTypes = { "Health","Dental", "Health+Dental"};
	public UserGuiImpl() {
		super();

		addWindowListener(new   WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				myAgent.deleteAgent();
			}
		} );

		final HashSet groupNames = new HashSet<>();


		JPanel rootPanel = new JPanel();
		rootPanel.setLayout(new GridBagLayout());
		rootPanel.setMinimumSize(new Dimension(430, 175));
		rootPanel.setPreferredSize(new Dimension(530, 175));
		GridBagConstraints gridBagConstraints = new GridBagConstraints();


		
		JLabel l = new JLabel("Insuarance Type:");

		l.setHorizontalAlignment(SwingConstants.LEFT);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new Insets(5, 3, 0, 3);
		rootPanel.add(l, gridBagConstraints);

		final JComboBox<String> cb = new JComboBox<String>(insTypes);
		cb.setMinimumSize(new Dimension(246, 20));
		cb.setPreferredSize(new Dimension(246, 20));
		cb.setVisible(true);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new Insets(5, 3, 0, 3);
		rootPanel.add(cb, gridBagConstraints);
		
		getContentPane().add(rootPanel, BorderLayout.NORTH);

		// ********	Time Insurance *************************

		l = new JLabel("Max Price:");
		l.setHorizontalAlignment(SwingConstants.LEFT);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(5, 3, 0, 3);
		rootPanel.add(l, gridBagConstraints);

		maxPriceTF = new JTextField(64);
		maxPriceTF.setMinimumSize(new Dimension(246, 20));
		maxPriceTF.setPreferredSize(new Dimension(246, 20));
		maxPriceTF.setEnabled(true);
		maxPriceTF.setDisabledTextColor(Color.DARK_GRAY);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new Insets(5, 3, 0, 3);
		rootPanel.add(maxPriceTF, gridBagConstraints);
		
		getContentPane().add(rootPanel, BorderLayout.NORTH);

		l = new JLabel("Time Period:");
		l.setHorizontalAlignment(SwingConstants.LEFT);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(5, 3, 0, 3);
		rootPanel.add(l, gridBagConstraints);

		timePeriodTF = new JTextField(64);
		timePeriodTF.setMinimumSize(new Dimension(246, 20));
		timePeriodTF.setPreferredSize(new Dimension(246, 20));
		timePeriodTF.setEnabled(true);
		timePeriodTF.setDisabledTextColor(Color.DARK_GRAY);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new Insets(5, 3, 0, 3);
		rootPanel.add(timePeriodTF, gridBagConstraints);
	
		getContentPane().add(rootPanel, BorderLayout.NORTH);

		l = new JLabel("Coverage:");
		l.setHorizontalAlignment(SwingConstants.LEFT);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(5, 3, 0, 3);
		rootPanel.add(l, gridBagConstraints);

		coverageTF = new JTextField(64);
		coverageTF.setMinimumSize(new Dimension(246, 20));
		coverageTF.setPreferredSize(new Dimension(246, 20));
		coverageTF.setEnabled(true);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new Insets(5, 3, 0, 3);
		rootPanel.add(coverageTF, gridBagConstraints);
		
		// **********to appear on panel *************************
		rootPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		getContentPane().add(rootPanel, BorderLayout.NORTH);
		
		JPanel p1 = new JPanel(); //new GridLayout(1,2)
		// ********	Text Area *************************
		logTA = new JTextArea();
		logTA.setEnabled(false);
		logTA.setDisabledTextColor(Color.DARK_GRAY);
		logTA.setSize(250, 180);
		logTA.append("********************Logger********************");
		JScrollPane jsp = new JScrollPane(logTA);
		jsp.setMinimumSize(new Dimension(250, 180));
		jsp.setPreferredSize(new Dimension(250, 180));
		jsp.setMaximumSize(new Dimension(250, 180));

		p1.setBorder(new BevelBorder(BevelBorder.LOWERED));
		p1.add(jsp);
		getContentPane().add(p1, BorderLayout.CENTER);

		JPanel p2 = new JPanel();

		// ********	Propose Button *************************

		findOptionB = new JButton("Find Options");
		findOptionB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {

				if(coverageTF.getText().isEmpty() || maxPriceTF.getText().isEmpty() ||
						timePeriodTF.getText().isEmpty() ) { //locationTF.getText().isEmpty()
					JOptionPane.showMessageDialog(UserGuiImpl.this, "Fill all details!", "WARNING", JOptionPane.WARNING_MESSAGE);
				}else {
					String insType = (String)cb.getSelectedItem();
					System.out.println("selected value is "+insType);
					myAgent.findOptions(insType, Integer.parseInt(maxPriceTF.getText()), Integer.parseInt(timePeriodTF.getText()), Integer.parseInt(coverageTF.getText())); //locationTF.getText()
				}
			}
		} );

		// ********	Reset Button *************************
		resetB = new JButton("Reset");
		resetB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				//				locationTF.setText("");
				maxPriceTF.setText("");
				timePeriodTF.setText("");
				coverageTF.setText("");
				groupNames.clear();
				cb.setSelectedIndex(0);
			}
		} );
		//********************Check my insurances**************************88
		checkMyInsuaranceB = new JButton("My Insurances");
		checkMyInsuaranceB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				myAgent.showAvailableInsurances();
			}
		} );
		
		p2.add(findOptionB);
		p2.add(resetB);
		p2.add(checkMyInsuaranceB);
		p2.setBorder(new BevelBorder(BevelBorder.LOWERED));
		getContentPane().add(p2, BorderLayout.SOUTH);		
		pack();
		setResizable(false);
	}

	public void setAgent(UserAgent userInterfaceAgent) {
		myAgent = userInterfaceAgent;
		this.setTitle("Welcome "+myAgent.getLocalName());
	}

	public void notifyUser(String message) {
		logTA.append("\n"+message);
	}
	public void notifyUserWithDialogueBox(String message) {
		JOptionPane.showMessageDialog(UserGuiImpl.this, message, "WARNING", JOptionPane.WARNING_MESSAGE);
	}

	@Override
	public void updateLogger(Insuarance insuarance) {
		logTA.append("\n"+insuarance.getInsType()+" plan from "+insuarance.getInsCompanyName()+" has been added at price "+insuarance.getInsPrice());
	}

	@Override
	public void openInsuranceCB(HashSet<Insuarance> availableProviders) {
		System.out.println("opening Custom dialog");
		CustomDialog customDialog = new CustomDialog();
		
		
		int i = 0;
		for (Insuarance ins: availableProviders) {
//			System.out.println("inside for"+ i);
			JPanel p = new JPanel();
			JLabel label = new JLabel(ins.getInsCompanyName());
			label.setHorizontalAlignment(SwingConstants.LEFT);
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = i;
			gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
			gridBagConstraints.insets = new java.awt.Insets(5, 3, 0, 3);
			p.add(label, gridBagConstraints);
			JLabel midLabel = new JLabel(ins.getInsId()+ " Description: " +ins.getInsDescription() +" Price: "+ ins.getInsPrice());
			label.setHorizontalAlignment(SwingConstants.LEFT);
			gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 1;
			gridBagConstraints.gridy = i;
			gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
			gridBagConstraints.insets = new java.awt.Insets(5, 3, 0, 3);
			p.add(midLabel, gridBagConstraints);
			
			initiateContractB = new JButton("Initiate");
			initiateContractB.setName(ins.getInsId());
			initiateContractB.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					System.out.println("the property is "+ initiateContractB.getName());
					myAgent.initiateContract(initiateContractB.getName());
					
				}
			} );
			p.add(initiateContractB);	
			 
			customDialog.addComponent(p);
			i++;
		}
		customDialog.show();

	}
}
