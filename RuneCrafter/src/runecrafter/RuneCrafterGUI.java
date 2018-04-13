package runecrafter;

import javax.swing.JOptionPane;

public class RuneCrafterGUI extends javax.swing.JFrame {

	RuneCrafter runeCrafter;

	public RuneCrafterGUI(RuneCrafter runeCrafter) {
		this.setTitle("RuneCrafter");
		this.runeCrafter = runeCrafter;
		initComponents();
	}

	private void initComponents() {

		tab = new javax.swing.JTabbedPane();
		mainPanel = new javax.swing.JPanel();
		altarLabel1 = new javax.swing.JLabel();
		altarCombo = new javax.swing.JComboBox();
		essenceLabel = new javax.swing.JLabel();
		essenceCombo = new javax.swing.JComboBox();
		startButton = new javax.swing.JButton();
		delayPanel = new javax.swing.JPanel();
		minLoopLabel = new javax.swing.JLabel();
		minLoopDelayField = new javax.swing.JTextField();
		minEventLabel = new javax.swing.JLabel();
		minEventDelayField = new javax.swing.JTextField();
		maxLoopLabel = new javax.swing.JLabel();
		maxLoopDelayField = new javax.swing.JTextField();
		maxEventLabel = new javax.swing.JLabel();
		maxEventDelayField = new javax.swing.JTextField();
		accessoryLabel = new javax.swing.JLabel();
        accessoryCombo = new javax.swing.JComboBox();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		tab.setName("Altar");

		altarLabel1.setText("Altar");

		altarCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { runeCrafter.AIR_ALTAR, runeCrafter.EARTH_ALTAR }));
		altarCombo.setName("");

		essenceLabel.setText("Essence");

		essenceCombo.setModel(new javax.swing.DefaultComboBoxModel(
				new String[] { runeCrafter.RUNE_ESSENCE, runeCrafter.PURE_ESSENCE }));

		startButton.setText("Start RuneCrafter");
		startButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				startButtonActionPerformed(evt);
			}
		});
		
		accessoryLabel.setText("Accessory");

        accessoryCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { runeCrafter.TALISMAN, runeCrafter.TIARA }));

		setInitFieldValues();

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(altarLabel1, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(accessoryLabel)
                        .addGap(18, 18, 18)
                        .addComponent(accessoryCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(essenceLabel)
                        .addGap(28, 28, 28)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(altarCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(essenceCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(altarCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(altarLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(essenceLabel)
                    .addComponent(essenceCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(accessoryLabel)
                    .addComponent(accessoryCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(startButton)
                .addContainerGap())
        );

		tab.addTab("Altar", mainPanel);

		minLoopLabel.setText("Minimum loop delay");

		minEventLabel.setText("Minimum event delay");

		maxLoopLabel.setText("Maximum loop delay");

		maxEventLabel.setText("Maximum event delay");

		javax.swing.GroupLayout delayPanelLayout = new javax.swing.GroupLayout(delayPanel);
		delayPanel.setLayout(delayPanelLayout);
		delayPanelLayout.setHorizontalGroup(delayPanelLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(delayPanelLayout.createSequentialGroup().addContainerGap().addGroup(delayPanelLayout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(delayPanelLayout.createSequentialGroup().addComponent(minLoopLabel).addGap(18, 18, 18)
								.addComponent(minLoopDelayField, javax.swing.GroupLayout.PREFERRED_SIZE, 55,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(maxLoopLabel)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(maxLoopDelayField, javax.swing.GroupLayout.PREFERRED_SIZE, 55,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGroup(delayPanelLayout.createSequentialGroup().addComponent(minEventLabel)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(minEventDelayField, javax.swing.GroupLayout.PREFERRED_SIZE, 55,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(maxEventLabel)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(maxEventDelayField, javax.swing.GroupLayout.PREFERRED_SIZE, 55,
										javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addContainerGap()));
		delayPanelLayout.setVerticalGroup(delayPanelLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(delayPanelLayout.createSequentialGroup().addContainerGap()
						.addGroup(delayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(minLoopDelayField, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(maxLoopLabel)
								.addComponent(maxLoopDelayField, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(minLoopLabel))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(delayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(minEventLabel).addComponent(minEventDelayField,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(maxEventLabel).addComponent(maxEventDelayField,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		tab.addTab("Delay", delayPanel);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				tab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.PREFERRED_SIZE));
		layout.setVerticalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addComponent(tab)
	        );

		tab.getAccessibleContext().setAccessibleName("Altar");

		pack();
	}

	private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {
		
		if (setCrafterParams()) {
			runeCrafter.setIsStarted(true);
			this.dispose();
		}
		
	}

	public boolean setCrafterParams() {

		boolean validationSuccess = true;

		if (minLoopDelayField.getText() == "") {
			validationSuccess = false;
			JOptionPane.showMessageDialog(null, "Minimum loop delay cannot be null!");
		}
		if (validationSuccess) {
			try {
				runeCrafter.setBETWEEN_LOOPS_LOW(Integer.parseInt(minLoopDelayField.getText()));
			} catch (Exception e) {
				validationSuccess = false;
				JOptionPane.showMessageDialog(null, "Minimum loop delay must be an integer!");
			}
		}

		if (validationSuccess) {
			if (maxLoopDelayField.getText() == "") {
				validationSuccess = false;
				JOptionPane.showMessageDialog(null, "Maximum loop delay cannot be null!");
			}
		}
		if (validationSuccess) {
			try {
				runeCrafter.setBETWEEN_LOOPS_HIGH(Integer.parseInt(maxLoopDelayField.getText()));
			} catch (Exception e) {
				validationSuccess = false;
				JOptionPane.showMessageDialog(null, "Maximum loop delay must be an integer!");
			}
		}

		if (validationSuccess) {
			if (minEventDelayField.getText() == "") {
				validationSuccess = false;
				JOptionPane.showMessageDialog(null, "Minimum event delay cannot be null!");
			}
		}
		if (validationSuccess) {
			try {
				runeCrafter.setBETWEEN_LOOPS_LOW(Integer.parseInt(minEventDelayField.getText()));
			} catch (Exception e) {
				validationSuccess = false;
				JOptionPane.showMessageDialog(null, "Minimum event delay must be an integer!");
			}
		}

		if (validationSuccess) {
			if (maxEventDelayField.getText() == "") {
				validationSuccess = false;
				JOptionPane.showMessageDialog(null, "Maximum event delay cannot be null!");
			}
		}
		if (validationSuccess) {
			try {
				runeCrafter.setBETWEEN_LOOPS_LOW(Integer.parseInt(maxEventDelayField.getText()));
			} catch (Exception e) {
				validationSuccess = false;
				JOptionPane.showMessageDialog(null, "Maximum event delay must be an integer!");
			}
		}

		if (validationSuccess) {
			runeCrafter.setESSENCE(essenceCombo.getSelectedItem().toString());
			runeCrafter.setCraftingParams(altarCombo.getSelectedItem().toString(), accessoryCombo.getSelectedItem().toString());
		}

		return validationSuccess;
	}

	public void setInitFieldValues() {
		minLoopDelayField.setText("1200");
		maxLoopDelayField.setText("2000");
		minEventDelayField.setText("1200");
		maxEventDelayField.setText("1600");
	}

	private javax.swing.JTextField maxEventDelayField;
	private javax.swing.JLabel maxEventLabel;
	private javax.swing.JTextField maxLoopDelayField;
	private javax.swing.JLabel maxLoopLabel;
	private javax.swing.JComboBox altarCombo;
	private javax.swing.JLabel altarLabel1;
	private javax.swing.JPanel delayPanel;
	private javax.swing.JComboBox essenceCombo;
	private javax.swing.JLabel essenceLabel;
	private javax.swing.JPanel mainPanel;
	private javax.swing.JTextField minEventDelayField;
	private javax.swing.JLabel minEventLabel;
	private javax.swing.JTextField minLoopDelayField;
	private javax.swing.JLabel minLoopLabel;
	private javax.swing.JButton startButton;
	private javax.swing.JTabbedPane tab;
	private javax.swing.JComboBox accessoryCombo;
    private javax.swing.JLabel accessoryLabel;
}
