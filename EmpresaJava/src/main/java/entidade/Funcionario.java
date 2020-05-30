package entidade;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="FUNCIONARIO")
public class Funcionario {

	@Id
	@Column(name="CPF", nullable = false)
	private Long cpf;
	
	@Column(name="EMAIL", nullable = false)
	private String email;
	
	@Column(name="NOME", nullable = false)
	private String nome;
	
	@Column(name="TELEFONE_ID", nullable = false)
	private Long telefone_id;
	
	@Column(name="ENDERECO_ID", nullable = false)
	private Long endereco_id;
	
	@Column(name="SENHA", nullable = false)
	private String senha;
	
	@OneToMany
	@JoinColumn(name = "ID", referencedColumnName = "ID", nullable = false)
	private List <Telefone> telefones;
	
	@OneToOne
	@JoinColumn(name = "ID", referencedColumnName = "ID", nullable = false)
	private Endereco endereco;
	
	public Long getCpf() {
		return cpf;
	}

	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getTelefone_id() {
		return telefone_id;
	}

	public void setTelefone_id(Long telefone_id) {
		this.telefone_id = telefone_id;
	}

	public Long getEndereco_id() {
		return endereco_id;
	}

	public void setEndereco_id(Long endereco_id) {
		this.endereco_id = endereco_id;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	
	
}
