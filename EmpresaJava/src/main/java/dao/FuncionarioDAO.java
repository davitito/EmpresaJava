package dao;

import java.util.List;
import entidade.Funcionario;

public interface FuncionarioDAO {
	
	public void inserir(Funcionario funcionario);
	
	public void alterar(Funcionario funcionario);

	public void remover(Funcionario funcionario);

	public Funcionario pesquisar(Long cpf);

	public List<Funcionario> listarTodos();

}
