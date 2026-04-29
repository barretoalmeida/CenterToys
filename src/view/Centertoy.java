package view;

import java.awt.Color;
import java.awt.Desktop;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
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
import javax.swing.filechooser.FileNameExtensionFilter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


import model.Categoria;
import model.DAO;
import utils.Validador;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

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
	private JList <String> listNomes;
	private JButton btnAdicionar;
	private JButton btnLimpar;
	private JButton btnExcluir;
	private JButton btnEditar;
	private JButton btnCadastrar;
	private JButton btnInfo;
	private JButton btnPdf;

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
		contentPane.setBackground(new Color(255, 226, 168));
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPaneListar = new JScrollPane();
		scrollPaneListar.setBorder(null);
		scrollPaneListar.setVisible(false);
		scrollPaneListar.setBounds(90, 198, 332, 95);
		contentPane.add(scrollPaneListar);
		
		listNomes = new JList <String>();
		scrollPaneListar.setViewportView(listNomes);
		listNomes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarProdutos();
			}
		});
		listNomes.setBorder(null);

		JLabel lblNewLabel = new JLabel("Cadastrar produtos");
		lblNewLabel.setForeground(new Color(51, 153, 204));
		lblNewLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 47));
		lblNewLabel.setBounds(352, 32, 473, 67);
		contentPane.add(lblNewLabel);

		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon(Centertoy.class.getResource("/img/logo (1).png")));
		lblLogo.setBounds(109, 0, 169, 169);
		contentPane.add(lblLogo);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 17));
		lblNome.setBounds(26, 181, 65, 19);
		contentPane.add(lblNome);

		txtNome = new JTextField();
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarProdutos();
				
			}
			
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()== KeyEvent.VK_ENTER) {
					scrollPaneListar.setVisible(false);
					int confirma = JOptionPane.showConfirmDialog(null, "Produto não cadastrado \n Deseja cadastra um novo produto? ", "Aviso", JOptionPane.YES_OPTION);
					if (confirma == JOptionPane.YES_OPTION) {
						txtNome.requestFocus();
						btnCadastrar.setEnabled(true);
					}else {
						reset();
					}
				}
			}
		});
		txtNome.setBounds(90, 180, 332, 20);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		txtNome.setDocument(new Validador(30));

		txtDataF = new JTextField();
		txtDataF.setHorizontalAlignment(SwingConstants.LEFT);
		txtDataF.setToolTipText("dd-mm-yyyy");
		//String fabricacao = txtDataF.getText();
		txtDataF.setBounds(191, 211, 231, 22);
		contentPane.add(txtDataF);
		txtDataF.setColumns(10);
		//DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		//LocalDate data = LocalDate.parse(fabricacao, formato);

		JLabel lblCate = new JLabel("Categoria:");
		lblCate.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 17));
		lblCate.setBounds(26, 242, 112, 26);
		contentPane.add(lblCate);

		
		//JComboBox<Categoria> comboBoxCategoria;
		comboBoxCategoria = new JComboBox<>(Categoria.values());
		comboBoxCategoria.setBounds(121, 244, 301, 22);
		contentPane.add(comboBoxCategoria);

		JLabel lblNewLabel_1 = new JLabel("Faixa Etária:");
		lblNewLabel_1.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(26, 284, 107, 19);
		contentPane.add(lblNewLabel_1);

		txtFaixa = new JTextField();
		txtFaixa.setBounds(134, 285, 288, 20);
		contentPane.add(txtFaixa);
		txtFaixa.setColumns(10);
		txtFaixa.setDocument(new Validador(30));

		JLabel lblNewLabel_2 = new JLabel(" Preço:");
		lblNewLabel_2.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		lblNewLabel_2.setBounds(26, 324, 65, 19);
		contentPane.add(lblNewLabel_2);

		txtPreco = new JTextField();
		txtPreco.setToolTipText("R$00.00");
		txtPreco.setText("");
		txtPreco.setBounds(90, 325, 332, 20);
		contentPane.add(txtPreco);
		txtPreco.setColumns(10);
		

		lblFoto = new JLabel("");
		lblFoto.setForeground(new Color(255, 255, 255));
		lblFoto.setIcon(new ImageIcon(Centertoy.class.getResource("/img/Iconcamera.png")));
		lblFoto.setBackground(new Color(255, 255, 255));
		lblFoto.setBounds(586, 163, 266, 212);
		contentPane.add(lblFoto);

		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setBorder(null);
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadastra();

			}
		});
		btnCadastrar.setForeground(new Color(255, 255, 255));
		btnCadastrar.setBackground(new Color(51, 153, 204));
		btnCadastrar.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		btnCadastrar.setBounds(172, 422, 144, 54);
		contentPane.add(btnCadastrar);

		btnAdicionar = new JButton("Adicionar imagem");
		btnAdicionar.setBorder(null);
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarFoto();

			}
		});
		btnAdicionar.setForeground(new Color(255, 255, 255));
		btnAdicionar.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
		btnAdicionar.setBackground(new Color(251, 85, 55));
		btnAdicionar.setBounds(617, 422, 160, 54);
		contentPane.add(btnAdicionar);

		btnLimpar = new JButton("");
		btnLimpar.setBorder(null);
		btnLimpar.setIcon(new ImageIcon(Centertoy.class.getResource("/img/replay.png")));
		btnLimpar.setToolTipText("Limpar Campo");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
			}
			
		});
		btnLimpar.setForeground(new Color(255, 255, 0));
		btnLimpar.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		btnLimpar.setBackground(new Color(255, 226, 168));
		btnLimpar.setBounds(320, 434, 38, 42);
		contentPane.add(btnLimpar);
		
		btnExcluir = new JButton("");
		btnExcluir.setBorder(null);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluir();
				
			}
		});
		btnExcluir.setToolTipText("Excluir");
		btnExcluir.setIcon(new ImageIcon(Centertoy.class.getResource("/img/trash.png")));
		btnExcluir.setForeground(Color.YELLOW);
		btnExcluir.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		btnExcluir.setBackground(new Color(51, 153, 204));
		btnExcluir.setBounds(787, 438, 38, 38);
		contentPane.add(btnExcluir);
		
		btnEditar = new JButton("");
		btnEditar.setBorder(null);
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarProduto();
			}
		});
		btnEditar.setToolTipText("Editar");
		btnEditar.setIcon(new ImageIcon(Centertoy.class.getResource("/img/editar1.png")));
		btnEditar.setForeground(Color.YELLOW);
		btnEditar.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		btnEditar.setBackground(new Color(252, 224, 31));
		btnEditar.setBounds(835, 438, 38, 38);
		contentPane.add(btnEditar);
		
		JLabel lblPesquisar = new JLabel("");
		lblPesquisar.setIcon(new ImageIcon(Centertoy.class.getResource("/img/search.png")));
		lblPesquisar.setToolTipText("Pesquisar");
		lblPesquisar.setBounds(423, 177, 24, 26);
		contentPane.add(lblPesquisar);
		
		btnInfo = new JButton("");
		btnInfo.setBorder(null);
		btnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sobre sobre = new Sobre();
				sobre.setVisible(true);
			}
		});
		btnInfo.setIcon(new ImageIcon(Centertoy.class.getResource("/img/info.png")));
		btnInfo.setBounds(914, 517, 38, 37);
		contentPane.add(btnInfo);
		
		btnPdf = new JButton("");
		btnPdf.setBorderPainted(false);
		btnPdf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gerarPdf();
			}
		});
		btnPdf.setBorder(null);
		btnPdf.setToolTipText("Gerar PDF dos produtos");
		btnPdf.setBackground(new Color(252, 226, 167));
		btnPdf.setIcon(new ImageIcon(Centertoy.class.getResource("/img/pdf.png")));
		btnPdf.setBounds(444, 163, 44, 54);
		contentPane.add(btnPdf);
		
				JLabel lblDataF = new JLabel("Data da fabricação:");
				lblDataF.setBounds(26, 211, 332, 20);
				contentPane.add(lblDataF);
				lblDataF.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
				
				this.setLocationRelativeTo(null);

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
				//pst.setString(2, txtDataF.getText());
				DateTimeFormatter formatoBR = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				LocalDate data = LocalDate.parse(txtDataF.getText(), formatoBR);

				pst.setDate(2, java.sql.Date.valueOf(data));
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
					//txtDataF.setText(rs.getString(3));
					LocalDate data = rs.getDate(3).toLocalDate();
					DateTimeFormatter formatoBR = DateTimeFormatter.ofPattern("dd/MM/yyyy");

					txtDataF.setText(data.format(formatoBR));
					
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
					btnPdf.setEnabled(false);
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
					//pst.setString(1, txtDataF.getText());
					 System.out.println("Data digitada: " + txtDataF.getText());

					DateTimeFormatter formatoBR = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				    LocalDate data = LocalDate.parse(txtDataF.getText(), formatoBR);

				    pst.setDate(1, java.sql.Date.valueOf(data));
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
					//System.out.println(e);
					 JOptionPane.showMessageDialog(null, "Data inválida! Use dd/MM/yyyy");
					    return;
				}
			}else {
				Categoria categoriaEnum = (Categoria) comboBoxCategoria.getSelectedItem();
				String update = "update brinquedos set fabricacao=?, categoria=?, faixa_etaria=?, preco=? where nome=? ";
				try {
					con = dao.conectar();
					pst = con.prepareStatement(update);
					//pst.setString(1, txtDataF.getText());
					DateTimeFormatter formatoBR = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					LocalDate data = LocalDate.parse(txtDataF.getText(), formatoBR);

					pst.setDate(1, java.sql.Date.valueOf(data));
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
	
	public boolean isFotoCarregada() {
		return fotoCarregada;
	}

	public void setFotoCarregada(boolean fotoCarregada) {
		this.fotoCarregada = fotoCarregada;
	}
	
	private void gerarPdf() {
		Document document = new Document();
		// gerar o documento em pdf
		try {
			PdfWriter.getInstance(document, new FileOutputStream("center_toy.pdf"));
			document.open();
			Date date = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			//System.out.println(getClass().getResource("/img/logo (1).png")); //teste
			com.itextpdf.text.Image img = com.itextpdf.text.Image.getInstance(
				    getClass().getResource("/img/logo (1).png")
				);

				img.scaleToFit(150, 150); // redimensiona
				img.setAlignment(com.itextpdf.text.Image.ALIGN_CENTER);

				document.add(img);
			document.add(new Paragraph(formatador.format(date)));
			document.add(new Paragraph (" "));
			//gerando a tabela
			PdfPTable tabela = new PdfPTable(6);
			BaseColor azul = new BaseColor(0, 121, 182);
			
			com.itextpdf.text.Font fonteCabecalho = new com.itextpdf.text.Font();
			fonteCabecalho.setSize(12);
			fonteCabecalho.setStyle(com.itextpdf.text.Font.BOLD);
			fonteCabecalho.setColor(com.itextpdf.text.BaseColor.WHITE);
			
			PdfPCell col1 = new PdfPCell(new Paragraph("Nomes",fonteCabecalho));
			col1.setBackgroundColor(azul);
			tabela.addCell(col1);
			PdfPCell col2 = new PdfPCell(new Paragraph("Data de fabricação",fonteCabecalho));
			col2.setBackgroundColor(azul);
			tabela.addCell(col2);
			PdfPCell col3 = new PdfPCell(new Paragraph("Categoria", fonteCabecalho));
			col3.setBackgroundColor(azul);
			tabela.addCell(col3);
			PdfPCell col4 = new PdfPCell(new Paragraph("Faxia Etaria", fonteCabecalho));
			col4.setBackgroundColor(azul);
			tabela.addCell(col4);
			PdfPCell col5 = new PdfPCell(new Paragraph("Preço", fonteCabecalho));
			col5.setBackgroundColor(azul);
			tabela.addCell(col5);
			PdfPCell col6 = new PdfPCell(new Paragraph ("Imagem", fonteCabecalho));
			col6.setBackgroundColor(azul);
			tabela.addCell(col6);
			tabela.setWidthPercentage(100);
			
			String readLista =  "select * from brinquedos order by nome";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readLista);
				rs= pst.executeQuery();
				while (rs.next()) {
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
					tabela.addCell(rs.getString(4));
					tabela.addCell(rs.getString(5));
					tabela.addCell(rs.getString(6));
					Blob blob  = (Blob) rs.getBlob(7);
					byte[] imga = blob.getBytes(1, (int) blob.length());
					com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(imga);
					tabela.addCell(image);
				}
				con.close();
			} catch (Exception ex) {
				System.out.println(ex);
			}
			document.add(tabela);
		}catch (Exception e) {
			System.out.println(e);
		}finally {
			document.close();
		}
		try {
			Desktop.getDesktop().open(new File("center_toy.pdf"));
		}catch (Exception e2) {
			System.out.println(e2);
		}
	}
	
	private void reset() {
		txtNome.setText(null);
		lblFoto.setIcon(new ImageIcon(Centertoy.class.getResource("/img/Iconcamera.png")));
		txtDataF.setText(null);
		txtFaixa.setText(null);
		txtPreco.setText(null);
		comboBoxCategoria.setSelectedItem(null);
		setFotoCarregada(false);
		tamanho = 0;
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		btnPdf.setEnabled(true);
		
	}


}
