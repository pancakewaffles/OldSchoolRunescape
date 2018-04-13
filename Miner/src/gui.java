import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JCheckBox;
import java.awt.Insets;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class gui extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gui frame = new gui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public gui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblSelectTheOres = new JLabel("Select the ores, and then check whether you want Antiban and Banking.");
		GridBagConstraints gbc_lblSelectTheOres = new GridBagConstraints();
		gbc_lblSelectTheOres.insets = new Insets(0, 0, 5, 0);
		gbc_lblSelectTheOres.gridx = 0;
		gbc_lblSelectTheOres.gridy = 1;
		contentPane.add(lblSelectTheOres, gbc_lblSelectTheOres);
		
		JCheckBox chckbxAntiban = new JCheckBox("Antiban");
		GridBagConstraints gbc_chckbxAntiban = new GridBagConstraints();
		gbc_chckbxAntiban.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxAntiban.gridx = 0;
		gbc_chckbxAntiban.gridy = 3;
		contentPane.add(chckbxAntiban, gbc_chckbxAntiban);
		
		JCheckBox chckbxBanking = new JCheckBox("Banking");
		GridBagConstraints gbc_chckbxBanking = new GridBagConstraints();
		gbc_chckbxBanking.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxBanking.gridx = 0;
		gbc_chckbxBanking.gridy = 4;
		contentPane.add(chckbxBanking, gbc_chckbxBanking);
		
		Button button = new Button("Start");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.anchor = GridBagConstraints.NORTH;
		gbc_button.gridx = 0;
		gbc_button.gridy = 6;
		contentPane.add(button, gbc_button);
	}

}
