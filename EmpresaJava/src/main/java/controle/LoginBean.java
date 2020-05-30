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

import dao.UsuarioDAO;
import dao.UsuarioDAOImpl;
import entidade.Usuario;



@ManagedBean(name = "LoginBean")
@SessionScoped
public class LoginBean {
	
	//Variáveis do login.xhtml
	private String txtEmail;
	private String txtSenha;
	
	private Boolean emailValido;

	private List<Usuario> listaUsuarios;
	private Usuario usuario;
	private String msgCadastroUsuario;
	
	private UsuarioDAO usuarioDAO;
	
	public LoginBean() {
		this.listaUsuarios = new ArrayList<Usuario>();
		this.usuario = new Usuario();
		this.usuarioDAO = new UsuarioDAOImpl();

	}

	/**
	 * Metodo responsavel por validar o usuario no login
	 */
	//public String entrar() {
	public void entrar() throws IOException {
		Usuario usuarioLogado = null;
		this.listaUsuarios = this.usuarioDAO.listarTodos();
		for (Usuario usuarioPesquisa : listaUsuarios) {
			if (usuarioPesquisa.getEmail().equals(this.txtEmail) && usuarioPesquisa.getSenha().equals(this.txtSenha)) {
				usuarioLogado = usuarioPesquisa;
			}
		}
		if (usuarioLogado != null) {
			HttpSession sessao =  (HttpSession)FacesContext.getCurrentInstance()
													.getExternalContext().getSession(true);
			sessao.setAttribute("usuarioLogado", usuarioLogado);
//			return "paginas/jogo/manterJogo.xhtml?faces-redirect=true&amp;includeViewParams=true";
			FacesContext.getCurrentInstance().getExternalContext().redirect("funcionarios.xhtml?faces-redirect=true&amp;includeViewParams=true");			
		} else {
//			return "index.xhtml";
			FacesContext.getCurrentInstance()
			.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção!", "Usuário não cadastrado ou senha inválida!!!"));
		}
	}

	public void criarUsuario() throws IOException {
		System.out.println("email "+this.txtEmail);
		System.out.println("email "+this.usuario.getEmail());
		System.out.println("senha "+this.txtSenha);
		System.out.println("senha "+this.usuario.getSenha());
		
		emailValido = validarEmail(this.usuario.getEmail());
		
		if (emailValido) {
			Usuario novo = new Usuario();
			novo.setEmail(this.usuario.getEmail());
			novo.setSenha(this.usuario.getSenha());
			
			boolean achou = false;
			this.listaUsuarios = this.usuarioDAO.listarTodos();
			for (Usuario usuarioPesquisa : listaUsuarios) {
				if (usuarioPesquisa.getEmail().equals(this.usuario.getEmail())) {
					achou = true;
				}
			}
			if(achou) {
				FacesContext.getCurrentInstance()
					.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção!", "E-mail já cadastrado, tente outro!!!"));
			}else {
				this.usuarioDAO.inserir(novo);
				this.usuario = new Usuario();
				FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
			}
		}else {
			FacesContext.getCurrentInstance()
			.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção!", "Digite um e-mail válido!!!"));
		}
		
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

	public String getTxtEmail() {
		return txtEmail;
	}

	public void setTxtEmail(String txtEmail) {
		this.txtEmail = txtEmail;
	}

	public String getTxtSenha() {
		return txtSenha;
	}

	public void setTxtSenha(String txtSenha) {
		this.txtSenha = txtSenha;
	}

	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getMsgCadastroUsuario() {
		return msgCadastroUsuario;
	}

	public void setMsgCadastroUsuario(String msgCadastroUsuario) {
		this.msgCadastroUsuario = msgCadastroUsuario;
	}
	
	public Boolean getEmailValido() {
		return emailValido;
	}

	public void setEmailValido(Boolean emailValido) {
		this.emailValido = emailValido;
	}
	
}
