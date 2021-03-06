package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entidade.Funcionario;


import util.JdbcUtil;

public class FuncionarioDAOImpl implements FuncionarioDAO {

	public void inserir(Funcionario funcionario) {

		String sql = "INSERT INTO FUNCIONARIO (CPF, EMAIL, NOME, SENHA) VALUES (?, ?, ?, ?)";
	
		Connection conexao;
		try {
			conexao = JdbcUtil.getConexao();
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setLong(1, funcionario.getCpf());
			ps.setString(2, funcionario.getEmail());
			ps.setString(3, funcionario.getNome());
			ps.setString(4, funcionario.getSenha());

			ps.execute();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}


	public void alterar(Funcionario funcionario) {		
		String sql = "UPDATE FUNCIONARIO SET EMAIL = ?, NOME = ? where CPF = ?";

		Connection conexao;
		try {
			conexao = JdbcUtil.getConexao();
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setString(1, funcionario.getEmail());
			ps.setString(2, funcionario.getNome());
			ps.setLong(3, funcionario.getCpf());
			
			ps.execute();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void remover(Funcionario funcionario) {

		String sql = "DELETE FROM FUNCIONARIO WHERE CPF = ?";

		Connection conexao;
		try {
			conexao = JdbcUtil.getConexao();
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setLong(1, funcionario.getCpf());

			ps.execute();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public Funcionario pesquisar(Long cpf) {
		String sql = "SELECT F.NOME, F.EMAIL, F.SENHA FROM FUNCIONARIO F WHERE CPF = ?";
		
		Funcionario funcionario = null;
		
		Connection conexao;
		try {
			conexao = JdbcUtil.getConexao();
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setLong(1, cpf);

			ResultSet res = ps.executeQuery();

			while (res.next()) {
				funcionario = new Funcionario();
				funcionario.setNome(res.getString("NOME"));
				funcionario.setEmail(res.getString("EMAIL"));
				funcionario.setSenha(res.getString("SENHA"));
			 }
			
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return funcionario;
	}

	public List<Funcionario> listarTodos() {

		String sql = "SELECT F.CPF, F.NOME, F.EMAIL, F.SENHA FROM FUNCIONARIO F";
		
		List<Funcionario> listaFuncionarios = new ArrayList<Funcionario>();
		
		Connection conexao;
		try {
			conexao = JdbcUtil.getConexao();
			
			PreparedStatement ps = conexao.prepareStatement(sql);

			ResultSet res = ps.executeQuery();

			while (res.next()) {
				
				Funcionario funcionario = new Funcionario();
				funcionario.setCpf(res.getLong("CPF"));
				funcionario.setNome(res.getString("NOME"));
				funcionario.setEmail(res.getString("EMAIL"));
				funcionario.setSenha(res.getString("SENHA"));
				
				listaFuncionarios.add(funcionario);
			 }
			
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listaFuncionarios;

	}

}
