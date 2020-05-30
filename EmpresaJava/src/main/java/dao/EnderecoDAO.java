package dao;

import java.util.List;
import entidade.Endereco;

public interface EnderecoDAO {
	
	public void inserir(Endereco endereco);
	
	public void alterar(Endereco endereco);

	public void remover(Endereco endereco);

	public Endereco pesquisar(Long id);

	public List<Endereco> listarTodos();

}
