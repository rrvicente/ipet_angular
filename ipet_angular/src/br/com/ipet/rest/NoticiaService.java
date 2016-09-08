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
import javax.ws.rs.core.MediaType;

import br.com.ipet.config.DatabaseConfig;
import br.com.ipet.entidade.Noticia;
import br.com.ipet.entidade.Status;

@Path("noticia")
public class NoticiaService {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/new")
	public void logarUsuario(Noticia noticia) {
		Connection con = DatabaseConfig.getConnection();

		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		try {
			PreparedStatement stm = con.prepareStatement("INSERT INTO TB_NOTICIA(TITULO, DESCRICAO, TEXTO, DATA, STATUS) VALUES(?, ?, ?, ?, ?)");
			stm.setString(1, noticia.getTitulo());
			stm.setString(2, noticia.getDescricao());
			stm.setString(3, noticia.getTexto());

			Date data = new Date(format.parse(noticia.getData()).getTime());

			stm.setDate(4, data);
			stm.setInt(5, Status.ATIVA.ordinal());

			stm.execute();
			con.close();
		} catch (SQLException | ParseException e) {
			e.printStackTrace();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/listar")
	public List<Noticia> listarNoticias() {
		List<Noticia> noticias = new ArrayList<Noticia>();

		Connection con = DatabaseConfig.getConnection();

		try {
			PreparedStatement stm = con.prepareStatement("SELECT * FROM TB_NOTICIA");
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				Noticia noticia = new Noticia();
				noticia.setId(rs.getInt("id"));
				noticia.setTitulo(rs.getString("titulo"));
				noticia.setDescricao(rs.getString("descricao"));
				noticia.setTexto(rs.getString("texto"));
				noticia.setData(rs.getDate("data").toString());
				
				noticias.add(noticia);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return noticias;
	}
}
