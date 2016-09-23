package br.com.ipet.rest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.com.ipet.config.DatabaseConfig;
import br.com.ipet.entidade.SequenciaTabela;

@Path("sequencia_tabela")
public class SequenciaTabelaService {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/get_sequencia")
	public SequenciaTabela getCodigoSequenciaTabela(@QueryParam("tabela") String tabela) {
		Connection con = DatabaseConfig.getConnection();
		SequenciaTabela sequencia = new SequenciaTabela();
		
		try {
			StringBuilder str = new StringBuilder();
			str.append(" SELECT ID + 1 AS ID FROM TB_CONTROLA_SEQUENCIA WHERE TABELA = '"+ tabela +"' ");

			PreparedStatement stm = con.prepareStatement(str.toString());
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				sequencia.setId(rs.getInt("id"));
				sequencia.setTabela(tabela);
			}
				
			str.setLength(0);
			str.append(" UPDATE TB_CONTROLA_SEQUENCIA SET ID = "+ sequencia.getId() +" WHERE TABELA = '"+ tabela +"' ");
			stm = con.prepareStatement(str.toString());
			stm.execute();
				
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sequencia;
	}
}
