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
				FacesContext.getCurrentInstance().getExternalContext().redirect("funcionalidades.xhtml");
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
	//PESQUISAR
	public void mostrarDados() {
		Funcionario func = new Funcionario();
		Telefone tele = new Telefone();
		Endereco ende = new Endereco();
		boolean achou = false;

		this.listaFuncionarios = this.funcionarioDAO.listarTodos();
		this.listaTelefones = this.telefoneDAO.listarTodos();
		this.listaEnderecos = this.enderecoDAO.listarTodos();
		
		for (Funcionario funcionarioPesquisa : listaFuncionarios) {
			if (funcionarioPesquisa.getCpf().equals(this.txtCpf)) {
				func = funcionarioPesquisa;
				achou = true;
			}
		}
		
		if(achou) {
			
			func = this.funcionarioDAO.pesquisar(this.txtCpf);
			tele = this.telefoneDAO.pesquisar(this.txtCpf);
			ende = this.enderecoDAO.pesquisar(this.txtCpf);
	
			this.txtEmail = func.getEmail();
			this.txtNome = func.getNome();
			
			this.txtTelDDD = tele.getDdd();
			this.txtTelNum = tele.getNumero_tel();
			
			this.txtEndRua = ende.getRua();
			this.txtEndNum = ende.getNumero_end();	
			this.txtEndBairro = ende.getBairro();
			this.txtEndCidade = ende.getCidade();
			this.txtEndEstado = ende.getEstado();
			this.txtEndCEP = ende.getCep();
			
		}else {
			FacesContext.getCurrentInstance()
			.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção!", "Funcionário não cadastrado!!!"));
		}
	}
	//Volta para as páginas dos botões - funcionalidades	
	public void voltar() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect("funcionalidades.xhtml");
	}
	//alterar -- o cpf já é o correto, verificado pelo botão buscar--função mostrarDados.
	public void alterarFuncionario() throws IOException {

		Funcionario fun = new Funcionario();
		Telefone tel = new Telefone();
		Endereco end = new Endereco();
		
		this.listaFuncionarios = this.funcionarioDAO.listarTodos();
		this.listaTelefones = this.telefoneDAO.listarTodos();
		this.listaEnderecos = this.enderecoDAO.listarTodos();
		
		end.setCpf_end(this.txtCpf);
		
		end.setRua(this.txtEndRua);
		end.setNumero_end(this.txtEndNum);
		end.setBairro(this.txtEndBairro);
		end.setCidade(this.txtEndCidade);
		end.setEstado(this.txtEndEstado);
		end.setCep(this.txtEndCEP);
				
		tel.setCpf_tel(this.txtCpf);
		
		tel.setDdd(this.txtTelDDD);
		tel.setNumero_tel(this.txtTelNum);
		
		fun.setCpf(this.txtCpf);
		
		fun.setEmail(this.txtEmail);
		fun.setNome(this.txtNome);
		
		
		this.enderecoDAO.alterar(end);
		this.telefoneDAO.alterar(tel);
		this.funcionarioDAO.alterar(fun);
		
		this.endereco = new Endereco();
		this.telefone = new Telefone();
		this.funcionario = new Funcionario();
	}
	
	//remover
	public void removerFuncionario() throws IOException {
		Funcionario f = new Funcionario();
		Telefone t = new Telefone();
		Endereco e = new Endereco();
		boolean achou = true;

		e.setCpf_end(this.txtCpf);
		f.setCpf(this.txtCpf);
		t.setCpf_tel(this.txtCpf);
		
		this.listaFuncionarios = this.funcionarioDAO.listarTodos();
		for (Funcionario funcionarioPesquisa : listaFuncionarios) {
			if (funcionarioPesquisa.getCpf().equals(this.txtCpf)) {
				achou = true;
			}
		}
		if(achou) {
			this.enderecoDAO.remover(e);
			this.endereco = new Endereco();
			
			this.telefoneDAO.remover(t);
			this.telefone = new Telefone();
			
			this.funcionarioDAO.remover(f);
			this.funcionario = new Funcionario();
			FacesContext.getCurrentInstance().getExternalContext().redirect("funcionalidades.xhtml");
			
		}else {
			FacesContext.getCurrentInstance()
			.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção!", "Funcionário não encontrado!!!"));
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
