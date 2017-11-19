package br.ucsal.geu.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.ucsal.geu.model.Tipo;
import br.ucsal.util.Conexao;

public class TipoDAO {

	private Conexao conexao;

	
	public TipoDAO() {
		this.conexao = Conexao.getConexao();
	}

	public List<Tipo> listar() {
		Statement stmt;
		List<Tipo> tipos = new ArrayList<>();
		try {
			stmt = conexao.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery("select id,nome,descricao from tipos;");
			while(rs.next()) {
				Tipo t = new Tipo();
				t.setId(rs.getInt("id"));
				t.setNome(rs.getString("nome"));
				t.setDescricao(rs.getString("descricao"));
				tipos.add(t);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		return tipos;
	}

	public void inserir(Tipo tipo) {
		try {
			
			PreparedStatement ps = conexao.getConnection()
					.prepareStatement("insert into tipos (nome,descricao) values (?,?);");
			ps.setString(1, tipo.getNome());
			ps.setString(2, tipo.getDescricao());
			ps.execute();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public Tipo getByID(int id) {
		Tipo t = null;
		try {
			PreparedStatement ps = conexao.getConnection().prepareStatement("select id,nome,descricao from tipos where id=?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				t = new Tipo();
				t.setId(rs.getInt("id"));
				t.setNome(rs.getString("nome"));
				t.setDescricao(rs.getString("descricao"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return t;
	}
	
	public void close() {
		conexao.closeConnection();
	}
	
	
	


}
