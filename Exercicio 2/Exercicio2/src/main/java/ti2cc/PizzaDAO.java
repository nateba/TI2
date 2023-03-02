package ti2cc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class PizzaDAO extends DAO {
	public PizzaDAO() {
		super();
		conectar();
	}

	public void finalize() {
		close();
	}
	
	
	public boolean insert(Pizza pizza) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "INSERT INTO pizza (sabor, tamanho, preco, borda) "
				       + "VALUES ('"+pizza.getSabor()+ "', '" + pizza.getTamanho() + "', '"  
				       + pizza.getPreco() + "', '" + pizza.getBorda() + "');";
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	
	public Pizza get (char tamanho) {
		Pizza pizza = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM pizza WHERE tamanho=" + tamanho;
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 pizza = new Pizza(rs.getString("sabor"), rs.getString("tamanho").charAt(0), rs.getDouble("preco"), rs.getString("borda"));
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return pizza;
	}
	
	
	public List<Pizza> get() {
		return get("");
	}

	
	public List<Pizza> getOrderBySabor() {
		return get("sabor");		
	}
	
	
	public List<Pizza> getOrderByTamanho() {
		return get("tamanho");		
	}
	
	
	public List<Pizza> getOrderByPreco() {
		return get("preco");		
	}
	
	
	private List<Pizza> get(String orderBy) {	
	
		List<Pizza> pizzas = new ArrayList<Pizza>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM pizza" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Pizza u = new Pizza(rs.getString("sabor"), rs.getString("tamanho").charAt(0), rs.getDouble("preco"), rs.getString("borda"));
	            pizzas.add(u);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return pizzas;
	}


	public List<Pizza> getTamanhoG() {
		List<Pizza> pizzas = new ArrayList<Pizza>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM pizza WHERE pizza.tamanho LIKE 'G'";
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Pizza u = new Pizza(rs.getString("sabor"), rs.getString("tamanho").charAt(0), rs.getDouble("preco"), rs.getString("borda"));
	            pizzas.add(u);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return pizzas;
	}
	
	
	public boolean update(Pizza pizza) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE pizza SET sabor = '" + pizza.getSabor() + "', tamanho = '"  
				       + pizza.getTamanho() + "', preco = '" + pizza.getPreco() + "'"
					   + " WHERE sabor = '" + pizza.getSabor()+"'";
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean delete(String sabor) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "DELETE FROM pizza WHERE sabor = '" + sabor+"'";
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	public boolean autenticar(String sabor, String borda) {
		boolean resp = false;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM pizza WHERE sabor LIKE '" + sabor + "' AND borda LIKE '" + borda  + "'";
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);
			resp = rs.next();
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return resp;
	}	
}