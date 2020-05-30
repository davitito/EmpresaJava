package entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="TELEFONE")
public class Telefone {

	@Id
	@Column(name="ID", nullable = false)
	@GeneratedValue(generator = "S_TELEFONE")
	@SequenceGenerator(name = "S_TELEFONE", sequenceName = "S_TELEFONE", allocationSize = 1)
	private Long id;
	
	@Column(name="DDD", nullable = false)
	private Long ddd;
	
	@Column(name="NUMERO_TEL", nullable = false)
	private Long numero_tel;
	
	@ManyToOne
	@JoinColumn (name="ID", referencedColumnName = "TELEFONE_ID", nullable = false)
	private Funcionario funcionario;
	
	
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

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	
	
	
}
