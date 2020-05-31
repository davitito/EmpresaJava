package controle;

import java.io.IOException;
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
	private String txtSenha;	
	
	private Long txtTelDDD;
	private Long txtTelNum;
	
	private String txtEndRua;
	private String txtEndNum;	
	private String txtEndBairro;
	private String txtEndCidade;
	private String txtEndEstado;
	private String txtEndCEP;
	
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
	
	private List<Telefone> listaTelefonesAdicionais;
	
	
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
		
		this.listaTelefonesAdicionais = new ArrayList<Telefone>();
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
	
	public void criarFuncionario() throws IOException {

		emailValido = validarEmail(this.txtEmail);
		
		if (emailValido) {
			
			Funcionario novo = new Funcionario();
						
			novo.setCpf(this.txtCpf);
			novo.setNome(this.txtNome);
			novo.setEmail(this.txtEmail);
			novo.setSenha(this.txtSenha);
			
			boolean achou = false;
			this.listaFuncionarios = this.funcionarioDAO.listarTodos();
			for (Funcionario funcionarioPesquisa : listaFuncionarios) {
				if (funcionarioPesquisa.getCpf().equals(this.funcionario.getCpf())) {
					achou = true;
				}
			}

			if(achou) {
				FacesContext.getCurrentInstance()
					.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção!", "Funcionário já cadastrado, tente outro!!!"));
			}else {
				this.funcionarioDAO.inserir(novo);
				cadastrarTelefone();
				cadastrarEndereco();
				this.funcionario = new Funcionario();
				
			}
		}else {
			FacesContext.getCurrentInstance()
			.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção!", "Digite um e-mail válido!!!"));
		}
		
	}
	public void cadastrarTelefone() {
		Telefone novoTel = new Telefone();
		novoTel.setDdd(this.txtTelDDD);
		novoTel.setNumero_tel(this.txtTelNum);
		novoTel.setCpf_tel(this.txtCpf);

		listaTelefonesAdicionais.add(novoTel);
		for (Telefone adicionaTel : listaTelefonesAdicionais) { 
			System.out.println(adicionaTel);
		    this.telefoneDAO.inserir(adicionaTel);
	    }
		this.telefone = new Telefone();
	}
	
	public void adicionarTelefone() {
		Telefone addTel = new Telefone();
		addTel.setDdd(this.txtTelDDD);
		addTel.setNumero_tel(this.txtTelNum);
		addTel.setCpf_tel(this.txtCpf);
		listaTelefonesAdicionais.add(addTel);
		this.txtTelDDD = null;
		this.txtTelNum = null;
	}
	
	public void cadastrarEndereco() {
		Endereco novoEnd = new Endereco();

		novoEnd.setRua(this.txtEndRua);
		novoEnd.setNumero_end(this.txtEndNum);
		novoEnd.setBairro(this.txtEndBairro);
		novoEnd.setCidade(this.txtEndCidade);
		novoEnd.setEstado(this.txtEndEstado);
		novoEnd.setCep(this.txtEndCEP);
		novoEnd.setCpf_end(this.txtCpf);
		
		if (this.endereco.getId() == null) {
			this.enderecoDAO.inserir(novoEnd);
			this.endereco = new Endereco();
		}
	}
	public void pesquisarFuncionario() throws IOException {
		boolean achou = false;
		this.listaFuncionarios = this.funcionarioDAO.listarTodos();
		this.listaTelefones = this.telefoneDAO.listarTodos();
		this.listaEnderecos = this.enderecoDAO.listarTodos();
		
		System.out.println("listaFuncionarios "+listaFuncionarios);
		System.out.println("listaTelefones "+listaTelefones);
		System.out.println("listaEnderecos "+listaEnderecos);
		
		
		for (Funcionario funcionarioPesquisa : listaFuncionarios) {
			
			if (funcionarioPesquisa.getCpf().equals(this.txtCpf)) {
				achou = true;
				System.out.println("funcionarioPesquisa.getCpf() "+funcionarioPesquisa.getCpf());
				System.out.println("funcionarioPesquisa.getEmail() "+funcionarioPesquisa.getEmail());
				System.out.println("funcionarioPesquisa.getNome() "+funcionarioPesquisa.getNome());
				System.out.println("funcionarioPesquisa.getSenha() "+funcionarioPesquisa.getSenha());
				//System.out.println("funcionarioPesquisa.getTelefone().getCpf_tel() "+funcionarioPesquisa.getTelefone().getCpf_tel());
				//System.out.println("funcionarioPesquisa.getTelefone().getDdd() "+funcionarioPesquisa.getTelefone().getDdd());
				//System.out.println("funcionarioPesquisa.getTelefone().getNumero_tel() "+funcionarioPesquisa.getTelefone().getNumero_tel());
			}
		}
		System.out.println("achou "+achou);
		if(achou) {
			for (Endereco enderecoPesquisa : listaEnderecos) {
				//if (enderecoPesquisa.getCpf_end().equals(this.txtCpf)) {
					System.out.println("funcionarioPesquisa.getEndereco().getCpf_end() "+enderecoPesquisa.getCpf_end());
					System.out.println("funcionarioPesquisa.getEndereco().getRua() "+enderecoPesquisa.getRua());
					System.out.println("funcionarioPesquisa.getEndereco().getNumero_end() "+enderecoPesquisa.getNumero_end());
					System.out.println("funcionarioPesquisa.getEndereco().getBairro() "+enderecoPesquisa.getBairro());
					System.out.println("funcionarioPesquisa.getEndereco().getCidade() "+enderecoPesquisa.getCidade());
					System.out.println("funcionarioPesquisa.getEndereco().getEstado() "+enderecoPesquisa.getEstado());
					System.out.println("funcionarioPesquisa.getEndereco().getCep() "+enderecoPesquisa.getCep());
				//}
			}
			//this.funcionarioDAO.pesquisar();
			System.out.println("mostrar funcionario");
		}else {
			FacesContext.getCurrentInstance()
			.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção!", "Funcionário não cadastrado!!!"));
		}
	}
	
	
	
	public void alterarFuncionario() throws IOException {
		boolean achou = false;
		this.listaFuncionarios = this.funcionarioDAO.listarTodos();
		for (Funcionario funcionarioPesquisa : listaFuncionarios) {
			if (funcionarioPesquisa.getCpf().equals(this.funcionario.getCpf())) {
				achou = true;
			}
		}
		if(achou) {
			System.out.println("alterar funcionario");
		}else {
			FacesContext.getCurrentInstance()
			.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção!", "Funcionário não cadastrado!!!"));
		}
	}
	public void removerFuncionario() throws IOException {
		boolean achou = false;
		this.listaFuncionarios = this.funcionarioDAO.listarTodos();
		for (Funcionario funcionarioPesquisa : listaFuncionarios) {
			if (funcionarioPesquisa.getCpf().equals(this.funcionario.getCpf())) {
				achou = true;
			}
		}
		if(achou) {
			System.out.println("remover funcionario");
		}else {
			FacesContext.getCurrentInstance()
			.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção!", "Funcionário não cadastrado!!!"));
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

	public List<Telefone> getListaTelefonesAdicionais() {
		return listaTelefonesAdicionais;
	}

	public void setListaTelefonesAdicionais(List<Telefone> listaTelefonesAdicionais) {
		this.listaTelefonesAdicionais = listaTelefonesAdicionais;
	}

	
}
