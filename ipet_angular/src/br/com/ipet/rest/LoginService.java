package br.com.ipet.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.ipet.entidade.Usuario;

@Path("login")
public class LoginService {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Usuario logarUsuario(Usuario usuario) {
		if ("admin".equals(usuario.getUsuario())) {
			usuario.setLogado(true);
		} else {
			usuario.setLogado(false);
		}

		return usuario;
	}
}
