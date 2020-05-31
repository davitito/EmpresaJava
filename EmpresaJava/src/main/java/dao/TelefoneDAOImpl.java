package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entidade.Telefone;
import util.JdbcUtil;

public class TelefoneDAOImpl implements TelefoneDAO {
	
	public Long recuperaId() {
		String sql = "SELECT S_TELEFONE.NEXTVAL FROM DUAL";
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
	
	public void inserir(Telefone telefone) {

		String sql = "INSERT INTO TELEFONE (ID, DDD, NUMERO_TEL, CPF_TEL) VALUES (?, ?, ?, ?)";

		Connection conexao;
		try {
			conexao = JdbcUtil.getConexao();
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			Long id = this.recuperaId();
			
			ps.setLong(1, id);
			ps.setLong(2, telefone.getDdd());
			ps.setLong(3,telefone.getNumero_tel());
			ps.setLong(4, telefone.getCpf_tel());
			
			ps.execute();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}


	public void alterar(Telefone telefone) {
		
		String sql = "UPDATE TELEFONE SET DDD = ? , NUMERO_TEL = ? WHERE ID = ?";

		Connection conexao;
		try {
			conexao = JdbcUtil.getConexao();
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setLong(1, telefone.getDdd());
			ps.setLong(2, telefone.getNumero_tel());
			ps.setLong(3, telefone.getId());

			ps.execute();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void remover(Telefone telefone) {

		String sql = "DELETE FROM TELEFONE WHERE ID = ?";

		Connection conexao;
		try {
			conexao = JdbcUtil.getConexao();
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setLong(1, telefone.getId());

			ps.execute();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public Telefone pesquisar(Long id) {

		String sql = "SELECT T.DDD, T.NUMERO_TEL, T.CPF_TEL FROM TELEFONE T WHERE ID = ?";
		
		Telefone telefone = null;
		
		Connection conexao;
		try {
			conexao = JdbcUtil.getConexao();
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setLong(1, id);

			ResultSet res = ps.executeQuery();

			while (res.next()) {
				telefone = new Telefone();
				telefone.setDdd(res.getLong("DDD"));
				telefone.setNumero_tel(res.getLong("NUMERO_TEL"));
				telefone.setCpf_tel(res.getLong("CPF_TEL"));
				telefone.setId(res.getLong("ID"));					
			 }
			
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return telefone;
	}

	public List<Telefone> listarTodos() {

		String sql = "SELECT T.ID, T.DDD, T.NUMERO_TEL, T.CPF_TEL from TELEFONE T";
		
		List<Telefone> listaTelefones = new ArrayList<Telefone>();
		
		Connection conexao;
		try {
			conexao = JdbcUtil.getConexao();
			
			PreparedStatement ps = conexao.prepareStatement(sql);

			ResultSet res = ps.executeQuery();

			while (res.next()) {
				
				Telefone telefone = new Telefone();
				telefone.setId(res.getLong("ID"));
				telefone.setDdd(res.getLong("DDD"));
				telefone.setNumero_tel(res.getLong("NUMERO_TEL"));
				telefone.setCpf_tel(res.getLong("CPF_TEL"));
				
				listaTelefones.add(telefone);
			 }
			
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listaTelefones;

	}

}
