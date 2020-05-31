package entidade;


public class Endereco {

	private Long id;
	private String rua;
	private String numero_end;
	private String bairro;
	private String cidade;
	private String estado;
	private String cep;
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
