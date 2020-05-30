package controle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import dao.FuncionarioDAO;
import dao.FuncionarioDAOImpl;
import entidade.Funcionario;

import entidade.Usuario;

import util.JdbcUtil;
import dao.TelefoneDAO;
import dao.TelefoneDAOImpl;
import entidade.Telefone;

import dao.EnderecoDAO;
import dao.EnderecoDAOImpl;
import entidade.Endereco;

@ManagedBean(name = "FuncionarioBean")
@SessionScoped
public class FuncionarioBean {
	
	//Variáveis do funcionarios.xhtml
	private Long txtCpf;
	private String txtNome;	
	private String txtEmail;
	
	private Long txtTelDDD;
	private Long txtTelNum;
	
	private String txtEndRua;
	private String txtEndNum;	
	private String txtEndBairro;
	private String txtEndCidade;
	private String txtEndEstado;
	private String txtEndCEP;
	
	private String txtSenha;	
	
	private Boolean emailValido;
	
	private Usuario usuarioLogado;

	private List<Funcionario> listaFuncionarios;
	private Funcionario funcionario;
	private FuncionarioDAO funcionarioDAO;

	private List<Telefone> listaTelefones;
	private Telefone telefone;
	private TelefoneDAO telefoneDAO;
	
	private List<Endereco> listaEnderecos;
	private Endereco endereco;
	private EnderecoDAO enderecoDAO;
	
	public FuncionarioBean() {
		this.listaFuncionarios = new ArrayList<Funcionario>();
		this.funcionario = new Funcionario();
		this.funcionarioDAO = new FuncionarioDAOImpl();
		
		this.listaTelefones = new ArrayList<Telefone>();
		this.telefone = new Telefone();
		this.telefoneDAO = new TelefoneDAOImpl();
		
		this.listaEnderecos = new ArrayList<Endereco>();
		this.endereco = new Endereco();
		this.enderecoDAO = new EnderecoDAOImpl();
		
		this.atualizarUsuarioLogado();

	}
	
	public void atualizarUsuarioLogado() {
		HttpSession sessao =  (HttpSession)FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		this.usuarioLogado = (Usuario)sessao.getAttribute("usuarioLogado");
	}

	public static boolean validarEmail(String email) {
		boolean emailValido = false;
			if (email != null && email.length() > 0) {
		        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		        Matcher matcher = pattern.matcher(email);
		    	if (matcher.matches()) {
		    		emailValido = true;
		    	}
			}
		return emailValido;
	}
	
	public Long recuperaIdTel() {
		String sql = "SELECT S_TELEFONE.NEXTVAL FROM DUAL";
		Long idRetorno = null;
		Connection conexao;
		try {
			conexao = JdbcUtil.getConexao();
			PreparedStatement ps = conexao.prepareStatement(sql);
			ResultSet res = ps.executeQuery();
			while (res.next()) {
				idRetorno = res.getLong(1);
			 }
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idRetorno;
	}
	
	public Long recuperaIdEnd() {
		String sql = "SELECT S_ENDERECO.NEXTVAL FROM DUAL";
		Long idRetorno = null;
		Connection conexao;
		try {
			conexao = JdbcUtil.getConexao();
			PreparedStatement ps = conexao.prepareStatement(sql);
			ResultSet res = ps.executeQuery();
			while (res.next()) {
				idRetorno = res.getLong(1);
			 }
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idRetorno;
	}

	public void criarFuncionario() throws IOException {

		emailValido = validarEmail(this.txtEmail);
		
		if (emailValido) {
			
			Telefone novoTel = new Telefone();

			Long idTel = recuperaIdTel();
			novoTel.setId(idTel);
			novoTel.setDdd(this.txtTelDDD);
			novoTel.setNumero_tel(this.txtTelNum);
			
			if (this.telefone.getId() == null) {
				this.telefoneDAO.inserir(novoTel);
			}
			
			Endereco novoEnd = new Endereco();

			Long idEnd = recuperaIdEnd();
			novoEnd.setId(idEnd);
			novoEnd.setRua(this.txtEndRua);
			novoEnd.setNumero_end(this.txtEndNum);
			novoEnd.setBairro(this.txtEndBairro);
			novoEnd.setCidade(this.txtEndCidade);
			novoEnd.setEstado(this.txtEndEstado);
			novoEnd.setCep(this.txtEndCEP);
			
			
			if (this.endereco.getId() == null) {
				this.enderecoDAO.inserir(novoEnd);
			}
			
			Funcionario novo = new Funcionario();
			
			//List<Telefone> telefones = novo.getTelefones();
			//telefones = this.telefoneDAO.listarTodos();
						
			novo.setCpf(this.txtCpf);
			novo.setNome(this.txtNome);
			novo.setEmail(this.txtEmail);
			novo.setTelefone_id(idTel);
			//novo.setEndereco_id(this.funcionario.getEndereco_id());
			novo.setEndereco_id(idEnd);
			//novo.setEndereco_id(this.endereco.getId());
			novo.setSenha(this.txtSenha);
				
			
			boolean achou = false;
			this.listaFuncionarios = this.funcionarioDAO.listarTodos();
			for (Funcionario funcionarioPesquisa : listaFuncionarios) {
				if (funcionarioPesquisa.getCpf().equals(this.funcionario.getCpf())) {
					achou = true;
				}
			}
			System.out.println("nova entidade "+novo);
			
			System.out.println("novo cpf "+novo.getCpf());
			System.out.println("novo email"+novo.getEmail());
			System.out.println("novo nome "+novo.getNome());
			System.out.println("novo tel id "+novo.getTelefone_id());
			System.out.println("novo end id "+novo.getEndereco_id());
			System.out.println("novo senha "+novo.getSenha());
			
			System.out.println("this cpf "+this.txtCpf);
			System.out.println("this email "+this.txtEmail);
			System.out.println("this nome "+this.txtNome);
			System.out.println("this tel id "+idTel);
			System.out.println("this end id "+idEnd);
			System.out.println("this senha "+this.txtSenha);
			
			if(achou) {
				FacesContext.getCurrentInstance()
					.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção!", "Funcionário já cadastrado, tente outro!!!"));
			}else {
				this.funcionarioDAO.inserir(novo);
				this.funcionario = new Funcionario();
				
			}
		}else {
			FacesContext.getCurrentInstance()
			.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção!", "Digite um e-mail válido!!!"));
		}
		
	}

	public Long getTxtCpf() {
		return txtCpf;
	}

	public void setTxtCpf(Long txtCpf) {
		this.txtCpf = txtCpf;
	}

	public String getTxtEmail() {
		return txtEmail;
	}

	public void setTxtEmail(String txtEmail) {
		this.txtEmail = txtEmail;
	}

	public String getTxtNome() {
		return txtNome;
	}

	public void setTxtNome(String txtNome) {
		this.txtNome = txtNome;
	}

	public String getTxtSenha() {
		return txtSenha;
	}

	public void setTxtSenha(String txtSenha) {
		this.txtSenha = txtSenha;
	}

	public Long getTxtTelDDD() {
		return txtTelDDD;
	}

	public void setTxtTelDDD(Long txtTelDDD) {
		this.txtTelDDD = txtTelDDD;
	}

	public Long getTxtTelNum() {
		return txtTelNum;
	}

	public void setTxtTelNum(Long txtTelNum) {
		this.txtTelNum = txtTelNum;
	}

	public String getTxtEndRua() {
		return txtEndRua;
	}

	public void setTxtEndRua(String txtEndRua) {
		this.txtEndRua = txtEndRua;
	}

	public String getTxtEndBairro() {
		return txtEndBairro;
	}

	public void setTxtEndBairro(String txtEndBairro) {
		this.txtEndBairro = txtEndBairro;
	}

	public String getTxtEndCidade() {
		return txtEndCidade;
	}

	public void setTxtEndCidade(String txtEndCidade) {
		this.txtEndCidade = txtEndCidade;
	}

	public String getTxtEndEstado() {
		return txtEndEstado;
	}

	public void setTxtEndEstado(String txtEndEstado) {
		this.txtEndEstado = txtEndEstado;
	}

	public String getTxtEndNum() {
		return txtEndNum;
	}

	public void setTxtEndNum(String txtEndNum) {
		this.txtEndNum = txtEndNum;
	}

	public String getTxtEndCEP() {
		return txtEndCEP;
	}

	public void setTxtEndCEP(String txtEndCEP) {
		this.txtEndCEP = txtEndCEP;
	}

	public Boolean getEmailValido() {
		return emailValido;
	}

	public void setEmailValido(Boolean emailValido) {
		this.emailValido = emailValido;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public List<Funcionario> getListaFuncionarios() {
		return listaFuncionarios;
	}

	public void setListaFuncionarios(List<Funcionario> listaFuncionarios) {
		this.listaFuncionarios = listaFuncionarios;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public List<Telefone> getListaTelefones() {
		return listaTelefones;
	}

	public void setListaTelefones(List<Telefone> listaTelefones) {
		this.listaTelefones = listaTelefones;
	}

	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

	public List<Endereco> getListaEnderecos() {
		return listaEnderecos;
	}

	public void setListaEnderecos(List<Endereco> listaEnderecos) {
		this.listaEnderecos = listaEnderecos;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}


}
