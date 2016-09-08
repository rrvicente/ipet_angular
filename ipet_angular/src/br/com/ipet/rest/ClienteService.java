package br.com.ipet.rest;

import java.sql.Connection;
import java.sql.Date;
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
import javax.ws.rs.core.MediaType;

import br.com.ipet.config.DatabaseConfig;
import br.com.ipet.entidade.Cliente;

@Path("cliente")
public class ClienteService {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/new")
	public void logarUsuario(Cliente cliente) {
		Connection con = DatabaseConfig.getConnection();

		try {
			StringBuilder str = new StringBuilder();
			str.append("INSERT INTO TB_CLIENTE");
			str.append(" (NOME,CPF,TELEFONE,ENDERECO,OBSERVACAO,DATA_CADASTRO)");
			str.append(" VALUES(?,?,?,?,?,?)");

			PreparedStatement stm = con.prepareStatement(str.toString());
			stm.setString(1, cliente.getNome());
			stm.setString(2, cliente.getCpf());
			stm.setString(3, cliente.getTelefone());
			stm.setString(4, cliente.getEndereco());
			stm.setString(5, cliente.getObservacao());
			stm.setDate(6, new Date(new java.util.Date().getTime()));

			stm.execute();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/listar")
	public List<Cliente> listarClientes() {
		List<Cliente> clientes = new ArrayList<Cliente>();

		Connection con = DatabaseConfig.getConnection();

		try {
			StringBuilder str = new StringBuilder();
			str.append(" SELECT ID,NOME,CPF,TELEFONE,ENDERECO,OBSERVACAO,DATA_CADASTRO ");
			str.append(" FROM TB_CLIENTE ");

			PreparedStatement stm = con.prepareStatement(str.toString());
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				Cliente cliente = new Cliente();
				cliente.setId(rs.getInt("id"));

//				Animal animal = new Animal();
//				animal.setId(rs.getInt("id_animal"));
//				animal.setId_cliente(rs.getInt("id"));
//				animal.setEspecie(rs.getString("especie"));
//				animal.setRaca(rs.getString("raca"));
//				animal.setNome_pet(rs.getString("nome_pet"));
//				animal.setData_nascimento_pet(rs.getDate("data_nascimento_pet").toString());
//				cliente.getPets().add(animal);

				cliente.setNome(rs.getString("nome"));
				cliente.setCpf(rs.getString("cpf"));
				cliente.setTelefone(rs.getString("telefone"));
				cliente.setEndereco(rs.getString("endereco"));
				cliente.setObservacao(rs.getString("observacao"));
				cliente.setData_cadastro(rs.getDate("data_cadastro").toString());

				clientes.add(cliente);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clientes;
	}
}
