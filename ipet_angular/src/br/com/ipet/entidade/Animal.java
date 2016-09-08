package br.com.ipet.entidade;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Animal {

	private int id;
	private int id_cliente;
	private String especie;
	private String raca;
	private String nome_pet;
	private String data_nascimento_pet;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}

	public String getEspecie() {
		return especie;
	}

	public void setEspecie(String especie) {
		this.especie = especie;
	}

	public String getRaca() {
		return raca;
	}

	public void setRaca(String raca) {
		this.raca = raca;
	}

	public String getNome_pet() {
		return nome_pet;
	}

	public void setNome_pet(String nome_pet) {
		this.nome_pet = nome_pet;
	}

	public String getData_nascimento_pet() {
		return data_nascimento_pet;
	}

	public void setData_nascimento_pet(String data_nascimento_pet) {
		this.data_nascimento_pet = data_nascimento_pet;
	}

}
