package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entidade.Usuario;
import util.JdbcUtil;

public class UsuarioDAOImpl implements UsuarioDAO {

	public void inserir(Usuario usuario) {

		String sql = "insert into USUARIO (email, senha) values (?, ?)";

		Connection conexao;
		try {
			conexao = JdbcUtil.getConexao();
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setString(1, usuario.getEmail());
			ps.setString(2, usuario.getSenha());

			ps.execute();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}


	public void alterar(Usuario usuario) {
		
		String sql = "UPDATE USUARIO SET senha = ? where email = ?";

		Connection conexao;
		try {
			conexao = JdbcUtil.getConexao();
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setString(1, usuario.getSenha());
			ps.setString(2, usuario.getEmail());

			ps.execute();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void remover(Usuario usuario) {

		String sql = "DELETE FROM USUARIO WHERE email = ?";

		Connection conexao;
		try {
			conexao = JdbcUtil.getConexao();
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setString(1, usuario.getEmail());

			ps.execute();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public Usuario pesquisar(String email) {

		String sql = "select U.SENHA from USUARIO U where EMAIL = ?";
		
		Usuario usuario = null;
		
		Connection conexao;
		try {
			conexao = JdbcUtil.getConexao();
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setString(1, email);

			ResultSet res = ps.executeQuery();

			while (res.next()) {
				usuario = new Usuario();
				usuario.setEmail(res.getString("EMAIL"));
				usuario.setSenha(res.getString("SENHA"));
			 }
			
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return usuario;
	}

	public List<Usuario> listarTodos() {

		String sql = "select U.EMAIL, U.SENHA from USUARIO U";
		
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		
		Connection conexao;
		try {
			conexao = JdbcUtil.getConexao();
			
			PreparedStatement ps = conexao.prepareStatement(sql);

			ResultSet res = ps.executeQuery();

			while (res.next()) {
				
				Usuario usuario = new Usuario();
				usuario.setEmail(res.getString("EMAIL"));
				usuario.setSenha(res.getString("SENHA"));
				
				listaUsuarios.add(usuario);
			 }
			
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listaUsuarios;

	}

}
