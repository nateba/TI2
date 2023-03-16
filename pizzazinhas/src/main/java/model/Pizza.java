package model;

public class Pizza {
	private int codigo;
	private String sabor;
	private String tamanho;
	private double preco;
	
	public Pizza() {
		this.codigo = -1;
		this.sabor = "";
		this.tamanho = "";
		this.preco = -1;
	}
	
	public Pizza(int codigo, String sabor, String tamanho, double preco) {
		this.codigo = codigo;
		this.sabor = sabor;
		this.tamanho = tamanho;
		this.preco = preco;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getSabor() {
		return sabor;
	}

	public void setSabor(String sabor) {
		this.sabor = sabor;
	}

	public String getTamanho() {
		return tamanho;
	}

	public void setTamanho(String tamanho) {
		this.tamanho = tamanho;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	@Override
	public String toString() {
		return "Pizza [codigo = " + codigo + ", sabor = " + sabor + ", tamanho = " + tamanho + ", preco = " + preco + "]";
	}
}