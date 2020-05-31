package entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="TELEFONE")
public class Telefone {

	@Id
	@Column(name="ID", nullable = false)
	private Long id;
	
	@Column(name="DDD", nullable = false)
	private Long ddd;
	
	@Column(name="NUMERO_TEL", nullable = false)
	private Long numero_tel;
	
	@ManyToOne
	@Column(name="CPF_TEL", nullable = false)
	private Long cpf_tel;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDdd() {
		return ddd;
	}

	public void setDdd(Long ddd) {
		this.ddd = ddd;
	}

	public Long getNumero_tel() {
		return numero_tel;
	}

	public void setNumero_tel(Long numero_tel) {
		this.numero_tel = numero_tel;
	}

	public Long getCpf_tel() {
		return cpf_tel;
	}

	public void setCpf_tel(Long cpf_tel) {
		this.cpf_tel = cpf_tel;
	}
	
	
	
}
