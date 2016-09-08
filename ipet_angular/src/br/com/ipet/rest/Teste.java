package br.com.ipet.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("teste")
public class Teste {

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String aloMundo() {
		return "<strong>Alo Mundo GET</strong>";
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("/param")
	public String getParam(@QueryParam("nome") String nome) {
		System.out.println(nome);
		return "<strong>Alo Mundo " + nome + "</strong>";
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("param3/{nome}")
	public String getParam3(@PathParam("nome") String nome) {
		System.out.println(nome);
		return "<strong>Alo Mundo " + nome + "</strong>";
	}

	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/param2")
	public String getParam2(@FormParam("nome") String nome) {
		System.out.println(nome);
		return "<strong>Alo Mundo " + nome + "</strong>";
	}

	@GET
	@Produces(MediaType.TEXT_XML)
	@Path("/json")
	public Pessoa aloMundoJSON() {
		Pessoa pessoa = new Pessoa();
		pessoa.setId(1);
		pessoa.setNome("Diogo");
		return pessoa;
	}

}
