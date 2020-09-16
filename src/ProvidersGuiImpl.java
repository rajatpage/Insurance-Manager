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

import jade.core.Agent;
import jade.gui.TimeChooser;

/*
 * Implementation class for Provider GUI called by Tutor Agent
 */
public class ProvidersGuiImpl extends JFrame implements ProviderGUI{

	ProvidersAgent myAgent;

	AdministratorAgent administratorAgent;

	private JTextField maxPriceTF, timePeriodTF, minPriceTF, descriptionTF, coverageTF;
	private JButton findOptionB, resetB;//, exitB;
	private JTextArea logTA;

	String[] insTypes = { "Health","Dental", "Health+Dental"};
	public ProvidersGuiImpl(Agent agent) {
		super();
		myAgent = (ProvidersAgent) agent;
		addWindowListener(new   WindowAdapter() {
			public void windowClosing(WindowEvent e) {
//				myAgent.deleteAgent();
			}
		} );

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

		l = new JLabel("Max Price:");
		l.setHorizontalAlignment(SwingConstants.LEFT);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(5, 3, 0, 3);
		rootPanel.add(l, gridBagConstraints);

		maxPriceTF = new JTextField(64);
		maxPriceTF.setMinimumSize(new Dimension(60, 20));
		maxPriceTF.setPreferredSize(new Dimension(60, 20));
		maxPriceTF.setEnabled(true);
//		maxPriceTF.setDisabledTextColor(Color.DARK_GRAY);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new Insets(5, 3, 0, 3);
		rootPanel.add(maxPriceTF, gridBagConstraints);
		
		l = new JLabel("Min Price:");
		l.setHorizontalAlignment(SwingConstants.LEFT);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(5, 3, 0, 3);
		rootPanel.add(l, gridBagConstraints);

		minPriceTF = new JTextField(64);
		minPriceTF.setMinimumSize(new Dimension(60, 20));
		minPriceTF.setPreferredSize(new Dimension(60, 20));
		minPriceTF.setEnabled(true);
//		maxPriceTF.setDisabledTextColor(Color.DARK_GRAY);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new Insets(5, 3, 0, 3);
		rootPanel.add(minPriceTF, gridBagConstraints);
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
//		timePeriodTF.setDisabledTextColor(Color.DARK_GRAY);
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
		
		l = new JLabel("Description:");
		l.setHorizontalAlignment(SwingConstants.LEFT);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(5, 3, 0, 3);
		rootPanel.add(l, gridBagConstraints);

		descriptionTF = new JTextField(164);
		descriptionTF.setMinimumSize(new Dimension(246, 40));
		descriptionTF.setPreferredSize(new Dimension(246, 40));
		descriptionTF.setEnabled(true);
		descriptionTF.setHorizontalAlignment(0);
	
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new Insets(5, 3, 0, 3);
		rootPanel.add(descriptionTF, gridBagConstraints);
		
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

		findOptionB = new JButton("Add");
		findOptionB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {

				if(coverageTF.getText().isEmpty() || maxPriceTF.getText().isEmpty() ||
						timePeriodTF.getText().isEmpty() ) { //locationTF.getText().isEmpty()
					JOptionPane.showMessageDialog(ProvidersGuiImpl.this, "Fill all details!", "WARNING", JOptionPane.WARNING_MESSAGE);
				}else {
					String insType = (String)cb.getSelectedItem();
					System.out.println("selected value is "+insType);
					myAgent.updateavailableInsurances(insType, Integer.parseInt(maxPriceTF.getText()), Integer.parseInt(minPriceTF.getText()), Integer.parseInt(coverageTF.getText()), Integer.parseInt(timePeriodTF.getText()), descriptionTF.getText()); //locationTF.getText()
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
				cb.setSelectedIndex(0);
			}
		} );
		p2.add(findOptionB);
		p2.add(resetB);
		p2.setBorder(new BevelBorder(BevelBorder.LOWERED));
		getContentPane().add(p2, BorderLayout.SOUTH);		
		pack();
		setResizable(false);
	}

	public void setAgent(ProvidersAgent providersAgent) {
		myAgent = providersAgent;
		this.setTitle("Welcome "+myAgent.getLocalName());
	}

	public void notifyUser(String message) {
		logTA.append("\n"+message);
	}
	public void notifyUserWithDialogueBox(String message) {
		JOptionPane.showMessageDialog(ProvidersGuiImpl.this, message, "WARNING", JOptionPane.WARNING_MESSAGE);
	}

	@Override
	public void openInsuranceBox(HashSet<Insuarance> availableProviders) {
		

	}
}
