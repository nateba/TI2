package ti2cc;

public class Pizza {
	private String sabor;
	private char tamanho;
	private Double preco;
	private String borda;

	public Pizza() {
		this.sabor = "";
		this.tamanho = '*';
		this.preco = 0.0;
		this.borda = "";
	}

	public Pizza(String sabor, char tamanho, Double preco, String borda) {
		this.sabor = sabor;
		this.tamanho = tamanho;
		this.preco = preco;
		this.borda = borda;
	}

	public String getSabor() {
		return sabor;
	}

	public void setSabor(String sabor) {
		this.sabor = sabor;
	}

	public char getTamanho() {
		return tamanho;
	}

	public void setTamanho(char tamanho) {
		this.tamanho = tamanho;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public String getBorda() {
		return borda;
	}

	public void setBorda(String borda) {
		this.borda = borda;
	}

	@Override
	public String toString() {
		return "Pizza [sabor=" + sabor + ", tamanho=" + tamanho + ", preco=" + preco + ", borda=" + borda + "]";
	}
}