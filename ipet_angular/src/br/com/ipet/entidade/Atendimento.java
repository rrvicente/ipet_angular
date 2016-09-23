package br.com.ipet.entidade;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Atendimento {

	private int id;
	private int id_animal;
	private Double valor;
	private String data_atendimento;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_animal() {
		return id_animal;
	}

	public void setId_animal(int id_animal) {
		this.id_animal = id_animal;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getData_atendimento() {
		return data_atendimento;
	}

	public void setData_atendimento(String data_atendimento) {
		this.data_atendimento = data_atendimento;
	}

}
