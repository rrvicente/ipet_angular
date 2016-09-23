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
import br.com.ipet.entidade.Animal;

@Path("animal")
public class AnimalService {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/new")
	public void inserirNovo(Animal animal) {
		Connection con = DatabaseConfig.getConnection();

		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		try {
			StringBuilder str = new StringBuilder();
			str.append("INSERT INTO TB_ANIMAL");
			str.append(" (ID_CLIENTE,ESPECIE,RACA,NOME_PET,DATA_NASCIMENTO_PET)");
			str.append(" VALUES(?,?,?,?,?)");

			PreparedStatement stm = con.prepareStatement(str.toString());
			stm.setInt(1, animal.getId_cliente());
			stm.setString(2, animal.getEspecie());
			stm.setString(3, animal.getRaca());
			stm.setString(4, animal.getNome_pet());

			Date data = new Date(format.parse(animal.getData_nascimento_pet())
					.getTime());
			stm.setDate(5, data);

			stm.execute();
			con.close();
		} catch (SQLException | ParseException e) {
			e.printStackTrace();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/delete")
	public void deletarAnimal(Animal animal) {
		Connection con = DatabaseConfig.getConnection();

		try {
			StringBuilder str = new StringBuilder();
			str.append("DELETE FROM TB_ANIMAL WHERE TB_ANIMAL.ID = ?");

			PreparedStatement stm = con.prepareStatement(str.toString());
			stm.setInt(1, animal.getId());

			stm.execute();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/listar")
	public List<Animal> listarAnimais(@QueryParam("id") String id) {
		List<Animal> animais = new ArrayList<Animal>();

		Connection con = DatabaseConfig.getConnection();

		try {
			StringBuilder str = new StringBuilder();
			str.append(" SELECT TB_ANIMAL.ID, TB_ANIMAL.ID_CLIENTE, TB_ANIMAL.ESPECIE, TB_ANIMAL.RACA, TB_ANIMAL.NOME_PET, TB_ANIMAL.DATA_NASCIMENTO_PET FROM TB_ANIMAL ");

			if (id != null)
				try {
					Integer.parseInt(id);
					str.append(" WHERE TB_ANIMAL.ID_CLIENTE = ").append(id);
				} catch (Exception e) {
				}

			PreparedStatement stm = con.prepareStatement(str.toString());
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				Animal animal = new Animal();
				animal.setId(rs.getInt("id"));
				animal.setId_cliente(rs.getInt("id_cliente"));
				animal.setEspecie(rs.getString("especie"));
				animal.setRaca(rs.getString("raca"));
				animal.setNome_pet(rs.getString("nome_pet"));
				animal.setData_nascimento_pet(rs.getDate("data_nascimento_pet")
						.toString());
				animais.add(animal);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return animais;
	}
}
