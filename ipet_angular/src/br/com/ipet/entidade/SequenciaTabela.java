package br.com.ipet.entidade;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SequenciaTabela {

	private int id;
	private String tabela;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTabela() {
		return tabela;
	}

	public void setTabela(String tabela) {
		this.tabela = tabela;
	}

}
