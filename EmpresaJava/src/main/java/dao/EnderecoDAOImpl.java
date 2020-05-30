package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entidade.Endereco;
import util.JdbcUtil;

public class EnderecoDAOImpl implements EnderecoDAO {
	
	private Long recuperaId() {
		String sql = "SELECT S_ENDERECO.NEXTVAL FROM DUAL";
		Long idRetorno = null;
		Connection conexao;
		try {
			conexao = JdbcUtil.getConexao();
			PreparedStatement ps = conexao.prepareStatement(sql);
			ResultSet res = ps.executeQuery();
			while (res.next()) {
					idRetorno = res.getLong(1);
			 }
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idRetorno;
	}
	public void inserir(Endereco endereco) {

		String sql = "INSERT INTO ENDERECO (ID, RUA, NUMERO_END, BAIRRO, CIDADE, ESTADO, CEP) VALUES (?, ?, ?, ?, ?, ?, ?)";

		Connection conexao;
		try {
			conexao = JdbcUtil.getConexao();
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			Long id = this.recuperaId();
			
			ps.setLong(1, id);
			ps.setString(2, endereco.getRua());
			ps.setString(3, endereco.getNumero_end());
			ps.setString(4,endereco.getBairro());
			ps.setString(5, endereco.getCidade());
			ps.setString(6, endereco.getEstado());
			ps.setString(7, endereco.getCep());

			ps.execute();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}


	public void alterar(Endereco endereco) {
		
		String sql = "UPDATE ENDERECO SET RUA = ? , NUMERO_END = ? , BAIRRO = ? , CIDADE = ? , ESTADO = ? , CEP = ? ) VALUES (?, ?, ?, ?, ?, ?)";

		Connection conexao;
		try {
			conexao = JdbcUtil.getConexao();
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setString(1, endereco.getRua());
			ps.setString(2, endereco.getNumero_end());
			ps.setString(3, endereco.getBairro());
			ps.setString(4, endereco.getCidade());
			ps.setString(5, endereco.getEstado());
			ps.setString(6, endereco.getCep());
			

			ps.execute();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void remover(Endereco endereco) {

		String sql = "DELETE FROM ENDERECO WHERE ID = ?";

		Connection conexao;
		try {
			conexao = JdbcUtil.getConexao();
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setLong(1, endereco.getId());

			ps.execute();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public Endereco pesquisar(Long id) {

		String sql = "SELECT E.RUA, E.NUMERO_END, E.BAIRRO, E.CIDADE, E.ESTADO, E.CEP FROM TELEFONE T WHERE ID = ?";
		
		Endereco endereco = null;
		
		Connection conexao;
		try {
			conexao = JdbcUtil.getConexao();
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setLong(1, id);

			ResultSet res = ps.executeQuery();

			while (res.next()) {
				endereco = new Endereco();
				endereco.setRua(res.getString("RUA"));
				endereco.setNumero_end(res.getString("NUMERO_END"));
				endereco.setBairro(res.getString("BAIRRO"));
				endereco.setCidade(res.getString("CIDADE"));
				endereco.setEstado(res.getString("ESTADO"));
				endereco.setCep(res.getString("CEP"));
				endereco.setId(res.getLong("ID"));			
			 }
			
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return endereco;
	}

	public List<Endereco> listarTodos() {

		String sql = "SELECT E.ID, E.RUA, E.NUMERO_END, E.BAIRRO, E.CIDADE, E.ESTADO, E.CEP, FROM ENDERECO E";
		
		List<Endereco> listaEnderecos = new ArrayList<Endereco>();
		
		Connection conexao;
		try {
			conexao = JdbcUtil.getConexao();
			
			PreparedStatement ps = conexao.prepareStatement(sql);

			ResultSet res = ps.executeQuery();

			while (res.next()) {
				
				Endereco endereco = new Endereco();
				
				endereco.setId(res.getLong("ID"));
				endereco.setRua(res.getString("RUA"));
				endereco.setNumero_end(res.getString("NUMERO_END"));
				endereco.setBairro(res.getString("BAIRRO"));
				endereco.setCidade(res.getString("CIDADE"));
				endereco.setEstado(res.getString("ESTADO"));
				endereco.setCep(res.getString("CEP"));
			
				
				listaEnderecos.add(endereco);
			 }
			
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listaEnderecos;

	}

}
