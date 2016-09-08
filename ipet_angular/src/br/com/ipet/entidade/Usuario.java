package br.com.ipet.entidade;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Usuario {

	private String usuario;
	private String senha;

	private boolean logado;
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean isLogado() {
		return logado;
	}

	public void setLogado(boolean logado) {
		this.logado = logado;
	}

}
