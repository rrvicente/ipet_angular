package br.com.ipet.entidade;

import javax.xml.bind.annotation.XmlRootElement;

import org.json.simple.JSONObject;

@XmlRootElement
public class Produto {

	private int id;
	private String descricao;
	private double valor;
	private int estoque;
	private int tipo;
	public static int key;

	public Produto() {
		key = key + 1;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public int getEstoque() {
		return estoque;
	}

	public void setEstoque(int estoque) {
		this.estoque = estoque;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	@SuppressWarnings("unchecked")
	public JSONObject toJSONObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", getId());
		jsonObject.put("descricao", getDescricao());
		jsonObject.put("valor", getValor());
		jsonObject.put("estoque", getEstoque());
		jsonObject.put("tipo", getTipo());

		return jsonObject;
	}
}
