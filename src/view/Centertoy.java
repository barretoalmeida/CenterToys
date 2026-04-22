package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import javax.swing.Icon;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.Categoria;
import model.DAO;
import utils.Validador;

public class Centertoy extends JFrame {
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private JComboBox<Categoria> comboBoxCategoria;
	private ResultSet rs;


	// Instanciar objeto para fluxo de bytes
	private FileInputStream fis;

	private int tamanho;
	
	private boolean fotoCarregada = false;

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNome;
	private JTextField txtDataF;
	private JTextField txtFaixa;
	private JTextField txtPreco;
	private JLabel lblFoto;
	private JScrollPane scrollPaneListar;
	private JList listNomes;
	private JButton btnAdicionar;
	private JButton btnLimpar;
	private JButton btnExcluir;
	private JButton btnEditar;
	private JButton btnCadastrar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Centertoy frame = new Centertoy();
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
	public Centertoy() {
		setResizable(false);
		setBackground(new Color(233, 234, 213));
		setIconImage(Toolkit.getDefaultToolkit().getImage(Centertoy.class.getResource("/img/logo_personagem.png")));
		setTitle("Center Toys");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				status();
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 994, 604);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(252, 226, 167));
		contentPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPaneListar = new JScrollPane();
		scrollPaneListar.setBorder(null);
		scrollPaneListar.setVisible(false);
		scrollPaneListar.setBounds(92, 248, 332, 95);
		contentPane.add(scrollPaneListar);
		
		listNomes = new JList();
		scrollPaneListar.setViewportView(listNomes);
		listNomes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarProdutos();
			}
		});
		listNomes.setBorder(null);

		JLabel lblNewLabel = new JLabel("Cadastrar produtos");
		lblNewLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 45));
		lblNewLabel.setBounds(418, 62, 430, 67);
		contentPane.add(lblNewLabel);

		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon(Centertoy.class.getResource("/img/logo (1).png")));
		lblLogo.setBounds(151, 11, 169, 169);
		contentPane.add(lblLogo);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 17));
		lblNome.setBounds(32, 224, 65, 19);
		contentPane.add(lblNome);

		txtNome = new JTextField();
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarProdutos();
			}
		});
		txtNome.setBounds(92, 226, 332, 20);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		txtNome.setDocument(new Validador(30));

		JLabel lblDataF = new JLabel("Data da fabricação:");
		lblDataF.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		lblDataF.setBounds(26, 248, 157, 37);
		contentPane.add(lblDataF);

		txtDataF = new JTextField();
		txtDataF.setHorizontalAlignment(SwingConstants.LEFT);
		txtDataF.setToolTipText("yyyy-mm-dd");
		//String fabricacao = txtDataF.getText();
		txtDataF.setBounds(193, 257, 231, 22);
		contentPane.add(txtDataF);
		txtDataF.setColumns(10);
		//DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		//LocalDate data = LocalDate.parse(fabricacao, formato);

		JLabel lblCate = new JLabel("Categoria:");
		lblCate.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 17));
		lblCate.setBounds(32, 285, 112, 26);
		contentPane.add(lblCate);

		
		//JComboBox<Categoria> comboBoxCategoria;
		comboBoxCategoria = new JComboBox<>(Categoria.values());
		comboBoxCategoria.setBounds(123, 290, 301, 22);
		contentPane.add(comboBoxCategoria);

		JLabel lblNewLabel_1 = new JLabel("Faixa Etária:");
		lblNewLabel_1.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(32, 324, 107, 19);
		contentPane.add(lblNewLabel_1);

		txtFaixa = new JTextField();
		txtFaixa.setBounds(136, 324, 288, 20);
		contentPane.add(txtFaixa);
		txtFaixa.setColumns(10);
		txtFaixa.setDocument(new Validador(30));

		JLabel lblNewLabel_2 = new JLabel(" Preço:");
		lblNewLabel_2.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		lblNewLabel_2.setBounds(32, 354, 65, 19);
		contentPane.add(lblNewLabel_2);

		txtPreco = new JTextField();
		txtPreco.setToolTipText("R$00.00");
		txtPreco.setText("");
		txtPreco.setBounds(92, 355, 332, 20);
		contentPane.add(txtPreco);
		txtPreco.setColumns(10);
		

		lblFoto = new JLabel("");
		lblFoto.setIcon(new ImageIcon(Centertoy.class.getResource("/img/camera.png")));
		lblFoto.setBackground(new Color(255, 255, 255));
		lblFoto.setBounds(647, 216, 144, 157);
		contentPane.add(lblFoto);

		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadastra();

			}
		});
		btnCadastrar.setForeground(new Color(255, 255, 255));
		btnCadastrar.setBackground(new Color(63, 121, 218));
		btnCadastrar.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		btnCadastrar.setBounds(176, 427, 144, 54);
		contentPane.add(btnCadastrar);

		btnAdicionar = new JButton("Adicionar imagem");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarFoto();

			}
		});
		btnAdicionar.setForeground(new Color(0, 128, 255));
		btnAdicionar.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		btnAdicionar.setBackground(new Color(255, 221, 26));
		btnAdicionar.setBounds(543, 428, 160, 54);
		contentPane.add(btnAdicionar);

		btnLimpar = new JButton("");
		btnLimpar.setIcon(new ImageIcon(Centertoy.class.getResource("/img/replay.png")));
		btnLimpar.setToolTipText("Limpar Campo");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
			}
			
		});
		btnLimpar.setForeground(new Color(255, 255, 0));
		btnLimpar.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		btnLimpar.setBackground(new Color(250, 61, 33));
		btnLimpar.setBounds(710, 427, 50, 50);
		contentPane.add(btnLimpar);
		
		btnExcluir = new JButton("");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluir();
				
			}
		});
		btnExcluir.setToolTipText("Excluir");
		btnExcluir.setIcon(new ImageIcon(Centertoy.class.getResource("/img/trash.png")));
		btnExcluir.setForeground(Color.YELLOW);
		btnExcluir.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		btnExcluir.setBackground(new Color(250, 234, 37));
		btnExcluir.setBounds(770, 427, 50, 50);
		contentPane.add(btnExcluir);
		
		btnEditar = new JButton("");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarProduto();
			}
		});
		btnEditar.setToolTipText("Editar");
		btnEditar.setIcon(new ImageIcon(Centertoy.class.getResource("/img/edit.png")));
		btnEditar.setForeground(Color.YELLOW);
		btnEditar.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		btnEditar.setBackground(new Color(46, 79, 233));
		btnEditar.setBounds(830, 427, 50, 50);
		contentPane.add(btnEditar);
		
		JLabel lblPesquisar = new JLabel("");
		lblPesquisar.setIcon(new ImageIcon(Centertoy.class.getResource("/img/search.png")));
		lblPesquisar.setToolTipText("Pesquisar");
		lblPesquisar.setBounds(429, 224, 24, 26);
		contentPane.add(lblPesquisar);

	}

	private void status() {
		try {
			con = dao.conectar();
			if (con == null) {
				System.out.println("Erro de conexão!");
			} else {
				System.out.println("Banco de dados conectado");
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void cadastra() {
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(txtNome, "Preencha o nome");
			txtNome.requestFocus();
		} else if (txtDataF.getText().isEmpty()) {
			JOptionPane.showMessageDialog(txtDataF, "Preencha a data de fabricação");
			txtDataF.requestFocus();
		} else if (txtFaixa.getText().isEmpty()) {
			JOptionPane.showMessageDialog(txtFaixa, "Preencha a Faixa");
			txtFaixa.requestFocus();
		} else if (txtPreco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(txtPreco, "Preencha com o valor");
			txtPreco.requestFocus();
		} else if (tamanho == 0){
			JOptionPane.showMessageDialog(null, "Selecionar uma foto");
			
		}else {

			Categoria categoriaEnum = (Categoria) comboBoxCategoria.getSelectedItem();
			String insert = "insert into brinquedos (nome, fabricacao, categoria, faixa_etaria, preco, foto) values (?,?,?,?,?,?)";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(insert);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtDataF.getText());
				pst.setString(3, categoriaEnum.getDescricao());
				pst.setString(4, txtFaixa.getText());
				BigDecimal preco = new BigDecimal(txtPreco.getText());
				pst.setBigDecimal(5, preco);
				pst.setBlob(6, fis, tamanho);
				int confrimar = pst.executeUpdate();
				if (confrimar == 1) {
					JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
					reset();
				}else {
					JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto");
				}

				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}

		}

	}
	
	private void listarProdutos() {
		//scrollPaneListar.setVisible(true);
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listNomes.setModel(modelo);
		
		String readLista = "select * from brinquedos where nome like '" + txtNome.getText() + "%'" + "order by nome";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readLista);
			rs = pst.executeQuery();
			while (rs.next()) {
				scrollPaneListar.setVisible(true);
				modelo.addElement(rs.getString(2));
				if (txtNome.getText().isEmpty()) {
					scrollPaneListar.setVisible(false);
				}
			}
			con.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	private void buscarProdutos() {
		//System.out.println("teste");
		int linha = listNomes.getSelectedIndex();
		if (linha >= 0) {
			String readProduto = "select * from brinquedos where nome like '" +txtNome.getText() + "%'" + "order by nome limit " + (linha) + ",1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readProduto);
				rs =pst.executeQuery();
				while (rs.next()){
					scrollPaneListar.setVisible(false);
					txtNome.setText(rs.getString(2));
					txtDataF.setText(rs.getString(3));
					//categoriaEnum.setDescricao(rs.getString(3));
					String categoriaBanco = rs.getString(4);
					for (Categoria c : Categoria.values()) {
					    if (c.getDescricao().equals(categoriaBanco)) {
					        comboBoxCategoria.setSelectedItem(c);
					        break;
					    }
					}
					txtFaixa.setText(rs.getString(5));
					txtPreco.setText(rs.getString(6));
					Blob blob = (Blob) rs.getBlob(7);
					byte[] img = blob.getBytes(1, (int) blob.length());
					BufferedImage imagem = null;
					try {
						imagem = ImageIO.read(new ByteArrayInputStream(img));
					} catch (Exception e) {
						System.out.println(e);
					}
					ImageIcon icone = new ImageIcon(imagem);
					Icon foto = new ImageIcon(icone.getImage().getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), Image.SCALE_SMOOTH));
					lblFoto.setIcon(foto);
					lblFoto.setIcon(foto);
					btnAdicionar.setEnabled(true);
					btnCadastrar.setEnabled(true);
					btnEditar.setEnabled(true);
					btnExcluir.setEnabled(true);
				}
			} catch (Exception e) {
				
			}
		}
	}

	private void adicionarFoto() {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Selecionar Arquivo");
		jfc.setFileFilter(new FileNameExtensionFilter("Arquivo de imagens(*.PNG, *JPEG, *.JPG)", "png", "jpeg", "jpg"));
		int resultado = jfc.showOpenDialog(this);
		if (resultado == JFileChooser.APPROVE_OPTION) {
			try {
				fis = new FileInputStream(jfc.getSelectedFile());
				tamanho = (int) jfc.getSelectedFile().length();
				Image foto = ImageIO.read(jfc.getSelectedFile()).getScaledInstance(lblFoto.getWidth(),
						lblFoto.getHeight(), Image.SCALE_SMOOTH);
				lblFoto.setIcon(new ImageIcon(foto));
				lblFoto.updateUI();
				setFotoCarregada(true);
			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}
	
	private void editarProduto() {
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showConfirmDialog(null, "Preencha o nome");
			txtNome.requestFocus();
		}else {
			if (fotoCarregada == true) {
				Categoria categoriaEnum = (Categoria) comboBoxCategoria.getSelectedItem();
				String update = "update brinquedos set fabricacao=?, categoria=?, faixa_etaria=?, preco=?, foto=? where nome=? ";
				try {
					con = dao.conectar();
					pst = con.prepareStatement(update);
					pst.setString(1, txtDataF.getText());
					pst.setString(2, categoriaEnum.getDescricao());
					pst.setString(3, txtFaixa.getText());
					BigDecimal preco = new BigDecimal(txtPreco.getText());
					pst.setBigDecimal(4, preco);
					pst.setBlob(5, fis, tamanho);
					pst.setString(6, txtNome.getText());
					int confirma = pst.executeUpdate();
					if (confirma == 1) {
						JOptionPane.showConfirmDialog(null, "Dados do produtos alterado com sucesso");
						reset();
					}else {
						JOptionPane.showMessageDialog(null, "Erro ao alterar os dados");
					}
					con.close();
				} catch (Exception e) {
					System.out.println(e);
				}
			}else {
				Categoria categoriaEnum = (Categoria) comboBoxCategoria.getSelectedItem();
				String update = "update brinquedos set fabricacao=?, categoria=?, faixa_etaria=?, preco=? where nome=? ";
				try {
					con = dao.conectar();
					pst = con.prepareStatement(update);
					pst.setString(1, txtDataF.getText());
					pst.setString(2, categoriaEnum.getDescricao());
					pst.setString(3, txtFaixa.getText());
					BigDecimal preco = new BigDecimal(txtPreco.getText());
					pst.setBigDecimal(4, preco);
					pst.setString(5, txtNome.getText());
					int confirma = pst.executeUpdate();
					if (confirma == 1) {
						JOptionPane.showConfirmDialog(null, "Dados do produtos alterado com sucesso");
						reset();
					}else {
						JOptionPane.showMessageDialog(null, "Erro ao alterar os dados");
					}
					con.close();
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}
	}
	
	private void excluir() {
		int confirmarExcluir =  JOptionPane.showConfirmDialog(null, "Confirma a exclusão de Produto?", "Atenção", JOptionPane.YES_NO_OPTION);
		if(confirmarExcluir == JOptionPane.YES_OPTION) {
		String deletar = "delete from brinquedos where nome=?";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(deletar);
			pst.setString(1, txtNome.getText());
			int confirmar = pst.executeUpdate();
			if (confirmar == 1 ) {
				reset();
				JOptionPane.showMessageDialog(null, "Produto excluido com sucesso");
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		}
	}
	
	private void reset() {
		txtNome.setText(null);
		lblFoto.setIcon(new ImageIcon(Centertoy.class.getResource("/img/camera.png")));
		txtDataF.setText(null);
		txtFaixa.setText(null);
		txtPreco.setText(null);
		comboBoxCategoria.setSelectedItem(null);
		setFotoCarregada(false);
		tamanho = 0;
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		
	}

	public boolean isFotoCarregada() {
		return fotoCarregada;
	}

	public void setFotoCarregada(boolean fotoCarregada) {
		this.fotoCarregada = fotoCarregada;
	}
}
