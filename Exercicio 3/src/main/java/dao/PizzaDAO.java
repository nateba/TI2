package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Pizza;

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
		
		// Condição para impossibilitar valores inválidos
		if(pizza.getPreco() > 0 && pizza.getSabor() != "" && pizza.getTamanho() != "") {
			try {  
				Statement st = conexao.createStatement();
				String sql = "INSERT INTO pizza2 (codigo, sabor, tamanho, preco) "
					       + "VALUES ("+ pizza.getCodigo() +",'"+ pizza.getSabor() + "', '"  
					       + pizza.getTamanho() + "', " + pizza.getPreco() + ");";
				System.out.println(sql);
				st.executeUpdate(sql);
				st.close();
				status = true;
			} catch (SQLException u) {  
				throw new RuntimeException(u);
			}
		}
		
		return status;
	}

	
	public Pizza get(int codigo) {
		Pizza pizza = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM pizza2 WHERE codigo=" + codigo;
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 pizza = new Pizza(rs.getInt("codigo"), rs.getString("sabor"), rs.getString("tamanho"), rs.getDouble("preco"));
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

	
	public List<Pizza> getOrderByCodigo() {
		return get("codigo");		
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
			String sql = "SELECT * FROM pizza2" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Pizza u = new Pizza(rs.getInt("codigo"), rs.getString("sabor"), rs.getString("tamanho"), rs.getDouble("preco"));
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
			String sql = "UPDATE pizza2 SET codigo = " + pizza.getCodigo()+", sabor = '" + pizza.getSabor() + "', tamanho = '"  
				       + pizza.getTamanho() + "', preco = " + pizza.getPreco()
					   + " WHERE codigo = " + pizza.getCodigo();
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean delete(int codigo) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "DELETE FROM pizza2 WHERE codigo = " + codigo;
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	public boolean autenticar(String sabor, String tamanho) {
		boolean resp = false;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM pizza2 WHERE sabor LIKE '" + sabor + "' AND tamanho LIKE '" + tamanho  + "'";
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
