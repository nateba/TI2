package service;

import java.util.Scanner;
import java.io.File;
import java.util.List;
import dao.PizzaDAO;
import model.Pizza;
import spark.Request;
import spark.Response;

public class PizzaService {
	private PizzaDAO PizzaDAO = new PizzaDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_CODIGO = 1;
	private final int FORM_ORDERBY_SABOR = 2;
	private final int FORM_ORDERBY_TAMANHO = 3;
	private final int FORM_ORDERBY_PRECO = 4;
	
	
	public PizzaService() {
		makeForm();
	}

	
	public void makeForm() {
		makeForm(FORM_INSERT, new Pizza(), FORM_ORDERBY_SABOR);
	}

	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Pizza(), orderBy);
	}

	
	public void makeForm(int tipo, Pizza pizza, int orderBy) {
		String nomeArquivo = "./src/main/resources/paginazinha.html";
		form = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	form += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		String umaPizza = "";
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/pizza/";
			String name, sabor, buttonLabel;
			if (tipo == FORM_INSERT){
				action += "insert";
				name = "Inserir Pizza";
				sabor = "";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + pizza.getCodigo();
				name = "Atualizar Pizza (Código " + pizza.getCodigo() + ")";
				sabor = pizza.getSabor();
				buttonLabel = "Atualizar";
			}
			umaPizza += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umaPizza += "\t<table width=\"80%\" align=\"center\" class=\"principal__form--inserir\">";
			umaPizza += "\t\t<tr>";
			umaPizza += "\t\t\t<td colspan=\"3\" bgcolor=\"\" text-align=\"center\"><font size=\"+2\"><b>" + name + "</b></font></td>";
			umaPizza += "\t\t</tr>";
			umaPizza += "\t\t<tr class=\"principal__form--sabores-atributos\">";
			umaPizza += "\t\t\t<td>Código: <input class=\"input--register\" type=\"text\" name=\"codigo\" placeholder=\"Código da Pizza\" value=\""+ pizza.getCodigo() +"\"></td>";
			umaPizza += "\t\t\t<td>Sabor: <input class=\"input--register\" type=\"text\" name=\"sabor\" placeholder=\"Sabor da Pizza\" value=\""+ sabor +"\"></td>";
			umaPizza += "\t\t\t<td>Preco: <input class=\"input--register\" type=\"text\" name=\"preco\" placeholder=\"0000\" value=\""+ pizza.getPreco() +"\"></td>";
			umaPizza += "\t\t\t<td>Tamanho: <input class=\"input--register\" type=\"text\" name=\"tamanho\" placeholder=\"Tamanho da Pizza\" value=\""+ pizza.getTamanho() +"\"></td>";
			umaPizza += "\t\t</tr>";
			umaPizza += "\t\t<tr>";
			umaPizza += "\t\t\t<td colspan=\"3\" text-align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button btn btn-danger\"></td>";
			umaPizza += "\t\t</tr>";
			umaPizza += "\t</table>";
			umaPizza += "\t</form>";		
		} else if (tipo == FORM_DETAIL){
			umaPizza += "\t<table class=\"principal__tabela--detalhes\" width=\"80%\" bgcolor=\"##ff0000\" align=\"center\">";
			umaPizza += "\t\t<tr>";
			umaPizza += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>Detalhar Pizza (Código " + pizza.getCodigo() + ")</b></font></td>";
			umaPizza += "\t\t</tr>";
			umaPizza += "\t\t<tr>";
			umaPizza += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umaPizza += "\t\t</tr>";
			umaPizza += "\t\t<tr>";
			umaPizza += "\t\t\t<td>Sabor: "+ pizza.getSabor() +"</td>";
			umaPizza += "\t\t\t<td>Preco: "+ pizza.getPreco() +"</td>";
			umaPizza += "\t\t\t<td>Tamanho: "+ pizza.getTamanho() +"</td>";
			umaPizza += "\t\t</tr>";
			umaPizza += "\t\t<tr>";
			umaPizza += "\t\t\t<td></td>";
			umaPizza += "\t\t</tr>";
			umaPizza += "\t</table>";		
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replaceFirst("<!--UMA-PIZZA-->", umaPizza);
		
		String list = new String("<table class=\"principal__tabela--pizzas\" width=\"80%\" align=\"center\" bgcolor=\"#ff0000\">");
		list += "\n<tr><td colspan=\"7\" align=\"center\"><font size=\"+2\"><b>Relação de Pizzas</b></font></td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td align=\"center\"><a href=\"/pizza/list/" + FORM_ORDERBY_CODIGO + "\"><b><i class=\"fa-solid fa-sort\"></i> Código</b></a></td>\n" +
        		"\t<td align=\"center\"><a href=\"/pizza/list/" + FORM_ORDERBY_SABOR + "\"><b><i class=\"fa-solid fa-sort\"></i> Sabor</b></a></td>\n" +
        		"\t<td align=\"center\"><a href=\"/pizza/list/" + FORM_ORDERBY_TAMANHO + "\"><b><i class=\"fa-solid fa-sort\"></i> Tamanho</b></td>\n" +
        		"\t<td><a href=\"/pizza/list/" + FORM_ORDERBY_PRECO + "\"><b><i class=\"fa-solid fa-sort\"></i> Preco</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<Pizza> pizzas;
		if (orderBy == FORM_ORDERBY_CODIGO) {               pizzas = PizzaDAO.getOrderByCodigo();
		} else if (orderBy == FORM_ORDERBY_SABOR) {		pizzas = PizzaDAO.getOrderBySabor();
		} else if (orderBy == FORM_ORDERBY_TAMANHO) {			pizzas = PizzaDAO.getOrderByTamanho();
		} else if (orderBy == FORM_ORDERBY_PRECO) {			pizzas = PizzaDAO.getOrderByPreco();
		} else {											pizzas = PizzaDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (Pizza p : pizzas) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
            		  "\t<td width=\"150\" align=\"center\">" + p.getCodigo() + "</td>\n" +
            		  "\t<td>" + p.getSabor() + "</td>\n" +
            		  "\t<td>" + p.getTamanho() + "</td>\n" +
            		  "\t<td>" + p.getPreco() + "</td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/pizza/" + p.getCodigo() + "\"><i class=\"fa-solid fa-circle-info\"></i></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/pizza/update/" + p.getCodigo() + "\"><i class=\"fa-sharp fa-solid fa-pen-to-square\"></i></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/pizza/delete/" + p.getCodigo() + "\"><i class=\"fa-solid fa-trash\"></i></a></td>\n" +
            		  "</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<!--LISTAR-PIZZA-->", list);		
	}
	
	
	public Object insert(Request request, Response response) {
		int codigo = Integer.parseInt(request.queryParams("codigo"));
		String sabor = request.queryParams("sabor");
		double preco = Double.parseDouble(request.queryParams("preco"));
		String tamanho = request.queryParams("tamanho");
		
		String resp = "";
		
		Pizza pizza = new Pizza(codigo, sabor, tamanho, preco);
		
		if(PizzaDAO.insert(pizza) == true) {
            resp = "Pizza (" + sabor + ") inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "Pizza (" + sabor + ") não inserido!";
			response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<!--MENSAGEM-->", "<p>" + resp + "</p>");
	}

	
	public Object get(Request request, Response response) {
		int codigo = Integer.parseInt(request.params(":codigo"));		
		Pizza pizza = (Pizza) PizzaDAO.get(codigo);
		
		if (pizza != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, pizza, FORM_ORDERBY_SABOR);
        } else {
            response.status(404); // 404 Not found
            String resp = "Pizza " + codigo + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<!--MENSAGEM-->", "<p>" + resp + "</p>");     
        }

		return form;
	}

	
	public Object getToUpdate(Request request, Response response) {
		int codigo = Integer.parseInt(request.params(":codigo"));		
		Pizza pizza = (Pizza) PizzaDAO.get(codigo);
		
		if (pizza != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, pizza, FORM_ORDERBY_SABOR);
        } else {
            response.status(404); // 404 Not found
            String resp = "Pizza " + codigo + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<!--MENSAGEM-->", "<p>" + resp + "</p>");     
        }

		return form;
	}
	
	
	public Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}			
	
	public Object update(Request request, Response response) {
        int codigo = Integer.parseInt(request.params(":codigo"));
		Pizza pizza = (Pizza) PizzaDAO.get(codigo);
        String resp = "";       

        if (pizza != null) {
        	pizza.setCodigo(Integer.parseInt(request.queryParams("codigo")));
        	pizza.setSabor(request.queryParams("sabor"));
        	pizza.setPreco(Double.parseDouble(request.queryParams("preco")));
        	pizza.setTamanho(request.queryParams("tamanho"));
        	PizzaDAO.update(pizza);
        	response.status(200); // success
            resp = "Pizza (Codigo " + pizza.getCodigo() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "Pizza (Codigo " + pizza.getCodigo() + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<!--MENSAGEM-->", "<p>" + resp + "</p>");
	}

	
	public Object delete(Request request, Response response) {
        int codigo = Integer.parseInt(request.params(":codigo"));
        Pizza pizza = (Pizza) PizzaDAO.get(codigo);
        String resp = "";       

        if (pizza != null) {
            PizzaDAO.delete(codigo);
            response.status(200); // success
            resp = "Pizza (" + codigo + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "Pizza (" + codigo + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<!--MENSAGEM-->", "<p>" + resp + "</p>");
	}
}
	
	

