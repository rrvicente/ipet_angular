package br.com.ipet.rest;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import br.com.ipet.entidade.Atendimento;
import br.com.ipet.entidade.AtendimentoItem;

@Path("atendimento")
public class AtendimentoService {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/new")
	public void inserirAtendimento(Atendimento atendimento) {
		Connection con = DatabaseConfig.getConnection();

		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		try {
			StringBuilder str = new StringBuilder();
			str.append("INSERT INTO TB_ATENDIMENTO");
			str.append(" (ID,ID_ANIMAL,VALOR,DATA_ATENDIMENTO)");
			str.append(" VALUES(?,?,?,?)");

			PreparedStatement stm = con.prepareStatement(str.toString());
			stm.setInt(1, atendimento.getId());
			stm.setInt(2, atendimento.getId_animal());
			stm.setDouble(3, atendimento.getValor());

			Date data = new Date(format
					.parse(atendimento.getData_atendimento()).getTime());
			stm.setDate(4, data);

			stm.execute();
			con.close();
		} catch (SQLException | ParseException e) {
			e.printStackTrace();
		}
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/new_item")
	public void inserirItemAtendimento(@QueryParam("idAtendimento") String idAtendimento,
			@QueryParam("idProduto") String idProduto,
			@QueryParam("quantidade") String quantidade, @QueryParam("valor") String valor) {
		Connection con = DatabaseConfig.getConnection();

		try {
			StringBuilder str = new StringBuilder();
			str.append("INSERT INTO TB_ATENDIMENTO_ITEM");
			str.append(" (ID_ATENDIMENTO,ID_PRODUTO,VALOR,QUANTIDADE)");
			str.append(" VALUES(?,?,?,?)");

			PreparedStatement stm = con.prepareStatement(str.toString());
			stm.setInt(1, Integer.parseInt(idAtendimento));
			stm.setInt(2, Integer.parseInt(idProduto));
			stm.setDouble(3, Double.parseDouble(valor));
			stm.setDouble(4, Double.parseDouble(quantidade));

			stm.execute();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/delete")
	public void deletarAtendimento(Atendimento atendimento) {
		Connection con = DatabaseConfig.getConnection();

		try {
			StringBuilder str = new StringBuilder();
			str.append("DELETE FROM TB_ATENDIMENTO WHERE TB_ATENDIMENTO.ID = ?");

			PreparedStatement stm = con.prepareStatement(str.toString());
			stm.setInt(1, atendimento.getId());

			stm.execute();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/listar")
	public List<Atendimento> listarAtendimentos(@QueryParam("id") String id) {
		List<Atendimento> atendimentos = new ArrayList<Atendimento>();

		Connection con = DatabaseConfig.getConnection();

		try {
			StringBuilder str = new StringBuilder();
			str.append(" SELECT TB_ATENDIMENTO.ID, TB_ATENDIMENTO.ID_ANIMAL, TB_ATENDIMENTO.VALOR, TB_ATENDIMENTO.DATA_ATENDIMENTO FROM TB_ATENDIMENTO ");

			if (id != null)
				try {
					Integer.parseInt(id);
					str.append(" WHERE TB_ATENDIMENTO.ID_ANIMAL = ").append(id);
				} catch (Exception e) {
				}

			PreparedStatement stm = con.prepareStatement(str.toString());
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				Atendimento atendimento = new Atendimento();
				atendimento.setId(rs.getInt("id"));
				atendimento.setId_animal(rs.getInt("id_animal"));
				atendimento.setValor(rs.getDouble("valor"));
				atendimento.setData_atendimento(rs.getDate("data_atendimento")
						.toString());
				atendimentos.add(atendimento);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return atendimentos;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/listar_itens")
	public List<AtendimentoItem> listarAnimais(@QueryParam("id") String id) {
		List<AtendimentoItem> itensAtendimento = new ArrayList<AtendimentoItem>();

		Connection con = DatabaseConfig.getConnection();

		try {
			StringBuilder str = new StringBuilder();
			str.append(" SELECT TB_ATENDIMENTO_ITEM.ID, TB_ATENDIMENTO_ITEM.ID_ATENDIMENTO, TB_ATENDIMENTO_ITEM.ID_PRODUTO, TB_ATENDIMENTO_ITEM.VALOR, TB_ATENDIMENTO_ITEM.QUANTIDADE, TB_PRODUTO.DESCRICAO FROM TB_ATENDIMENTO_ITEM, TB_PRODUTO ");
			str.append(" WHERE TB_ATENDIMENTO_ITEM.ID_PRODUTO = TB_PRODUTO.ID ");
			
			if (id != null)
				try {
					Integer.parseInt(id);
					str.append(" AND TB_ATENDIMENTO_ITEM.ID_ATENDIMENTO = ")
							.append(id);
				} catch (Exception e) {
				}

			PreparedStatement stm = con.prepareStatement(str.toString());
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				AtendimentoItem item = new AtendimentoItem();
				item.setId(rs.getInt("id"));
				item.setIdAtendimento(rs.getInt("id_atendimento"));
				item.setIdProduto(rs.getInt("id_produto"));
				item.setValor(rs.getDouble("valor"));
				item.setQuantidade(rs.getDouble("quantidade"));
				item.setDescricao(rs.getString("descricao"));

				itensAtendimento.add(item);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return itensAtendimento;
	}
}
