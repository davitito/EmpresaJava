package dao;

import java.util.List;
import entidade.Telefone;

public interface TelefoneDAO {
	
	public void inserir(Telefone telefone);
	
	public void alterar(Telefone telefone);

	public void remover(Telefone telefone);

	public Telefone pesquisar(Long id);

	public List<Telefone> listarTodos();

}
