package br.com.ipet.rest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.com.ipet.config.DatabaseConfig;
import br.com.ipet.entidade.Produto;

@Path("produto")
public class ProdutoService {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/new")
	public void inserirNovo(Produto produto) {
		Connection con = DatabaseConfig.getConnection();

		try {
			StringBuilder str = new StringBuilder();
			str.append("INSERT INTO TB_PRODUTO");
			str.append(" (DESCRICAO,VALOR,ESTOQUE,TIPO)");
			str.append(" VALUES(?,?,?,?)");

			PreparedStatement stm = con.prepareStatement(str.toString());
			stm.setString(1, produto.getDescricao());
			stm.setDouble(2, produto.getValor());
			stm.setInt(3, produto.getEstoque());
			stm.setInt(4, produto.getTipo());

			stm.execute();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/delete")
	public void deletarProduto(Produto produto) {
		Connection con = DatabaseConfig.getConnection();

		try {
			StringBuilder str = new StringBuilder();
			str.append("DELETE FROM TB_PRODUTO WHERE TB_PRODUTO.ID = ?");

			PreparedStatement stm = con.prepareStatement(str.toString());
			stm.setInt(1, produto.getId());

			stm.execute();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/listar")
	public List<Produto> listarProdutos(@QueryParam("id") String id) {
		List<Produto> produtos = new ArrayList<Produto>();

		Connection con = DatabaseConfig.getConnection();

		try {
			StringBuilder str = new StringBuilder();
			str.append(" SELECT TB_PRODUTO.ID, TB_PRODUTO.DESCRICAO, TB_PRODUTO.VALOR, TB_PRODUTO.ESTOQUE, TB_PRODUTO.TIPO FROM TB_PRODUTO ");

			if (id != null)
				try {
					Integer.parseInt(id);
					str.append(" WHERE TB_PRODUTO.ID = ").append(id);
				} catch (Exception e) {
				}
				
			PreparedStatement stm = con.prepareStatement(str.toString());
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				Produto produto = new Produto();
				produto.setId(rs.getInt("id"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setValor(rs.getDouble("valor"));
				produto.setEstoque(rs.getInt("estoque"));
				produto.setTipo(rs.getInt("tipo"));
				produtos.add(produto);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return produtos;
	}
}
