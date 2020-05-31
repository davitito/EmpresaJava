package entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="ENDERECO")
public class Endereco {

	@Id
	@Column(name="ID", nullable = false)
	private Long id;
	
	@Column(name="RUA", nullable = false)
	private String rua;
	
	@Column(name="NUMERO_END", nullable = false)
	private String numero_end;
	
	@Column(name="BAIRRO", nullable = false)
	private String bairro;
	
	@Column(name="CIDADE", nullable = false)
	private String cidade;
	
	@Column(name="ESTADO", nullable = false)
	private String estado;
	
	@Column(name="CEP", nullable = false)
	private String cep;
	
	@OneToOne
	@Column(name="CPF_END", nullable = false)
	private Long cpf_end;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getNumero_end() {
		return numero_end;
	}

	public void setNumero_end(String numero_end) {
		this.numero_end = numero_end;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Long getCpf_end() {
		return cpf_end;
	}

	public void setCpf_end(Long cpf_end) {
		this.cpf_end = cpf_end;
	}

		
	
}
