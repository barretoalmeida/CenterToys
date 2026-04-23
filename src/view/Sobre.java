package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Sobre extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Sobre dialog = new Sobre();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Sobre() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Sobre.class.getResource("/img/logo_personagem.png")));
		setTitle("Sobre Center Toys");
		setBounds(100, 100, 620, 408);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(255, 239, 174));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Sobre Center Toys");
			lblNewLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
			lblNewLabel.setBounds(30, 25, 144, 14);
			contentPanel.add(lblNewLabel);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("@Author Gabriely Barreto Almeida");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblNewLabel_1.setBounds(30, 105, 193, 14);
			contentPanel.add(lblNewLabel_1);
		}
		{
			JLabel lblNewLabel_2 = new JLabel("@Author Vitória Cristina Correa dos Santos");
			lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblNewLabel_2.setBounds(28, 146, 242, 14);
			contentPanel.add(lblNewLabel_2);
		}
		{
			JLabel lblNewLabel_2 = new JLabel("@Author Anna Luiza F.C Santos");
			lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblNewLabel_2.setBounds(30, 189, 177, 14);
			contentPanel.add(lblNewLabel_2);
		}
		{
			JLabel lblNewLabel_2 = new JLabel("@Author Pedro Henrique Reis");
			lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblNewLabel_2.setBounds(30, 230, 177, 14);
			contentPanel.add(lblNewLabel_2);
		}
		{
			JButton btnGitHubG = new JButton("");
			btnGitHubG.setIcon(new ImageIcon(Sobre.class.getResource("/img/github.png")));
			btnGitHubG.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					link("https://github.com/barretoalmeida");
				}
				private void link(String url) {
					Desktop desktop = Desktop.getDesktop(); 
					try {
						URI uri = new URI (url);
						desktop.browse(uri);
					} catch (Exception e) {
						System.out.println(e);
					}
				}
			});
			btnGitHubG.setBounds(227, 87, 32, 32);
			contentPanel.add(btnGitHubG);
		}
		{
			JButton btnGitHubV = new JButton("");
			btnGitHubV.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					link("https://github.com/vcorreasantos");
				}
				private void link(String url) {
					Desktop desktop = Desktop.getDesktop(); 
					try {
						URI uri = new URI (url);
						desktop.browse(uri);
					} catch (Exception e) {
						System.out.println(e);
					}
				}
			});
			btnGitHubV.setIcon(new ImageIcon(Sobre.class.getResource("/img/github.png")));
			btnGitHubV.setBounds(269, 128, 32, 32);
			contentPanel.add(btnGitHubV);
		}
		{
			JButton btnGitHubA = new JButton("");
			btnGitHubA.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					link("https://github.com/AnnaLuSant");
				}
				private void link(String url) {
					Desktop desktop = Desktop.getDesktop(); 
					try {
						URI uri = new URI (url);
						desktop.browse(uri);
					} catch (Exception e) {
						System.out.println(e);
					}
				}
			});
			btnGitHubA.setIcon(new ImageIcon(Sobre.class.getResource("/img/github.png")));
			btnGitHubA.setBounds(217, 171, 32, 32);
			contentPanel.add(btnGitHubA);
		}
		{
			JButton btnGitHubP = new JButton("");
			btnGitHubP.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					link("https://github.com/pedrohreismarques");
				}
				private void link(String url) {
					Desktop desktop = Desktop.getDesktop(); 
					try {
						URI uri = new URI (url);
						desktop.browse(uri);
					} catch (Exception e) {
						System.out.println(e);
					}
				}
			});
			btnGitHubP.setIcon(new ImageIcon(Sobre.class.getResource("/img/github.png")));
			btnGitHubP.setBounds(205, 212, 32, 32);
			contentPanel.add(btnGitHubP);
		}
		{
			JButton btnok = new JButton("OK");
			btnok.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btnok.setBounds(255, 296, 89, 23);
			contentPanel.add(btnok);
		}
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(Sobre.class.getResource("/img/logo (1).png")));
		lblNewLabel_3.setBounds(399, 25, 144, 126);
		contentPanel.add(lblNewLabel_3);
	}
}
