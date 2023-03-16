package app;

import static spark.Spark.*;
import service.PizzaService;
public class Aplicacao {
	private static PizzaService pizzaService = new PizzaService();
	
	 public static void main(String[] args) {

		 port(6532);
	        
	        staticFiles.location("/public");
	        
	        post("/pizza/insert", (request, response) -> pizzaService.insert(request, response));

	        get("/pizza/:codigo", (request, response) -> pizzaService.get(request, response));
	        
	        get("/pizza/list/:orderby", (request, response) -> pizzaService.getAll(request, response));

	        get("/pizza/update/:codigo", (request, response) -> pizzaService.getToUpdate(request, response));
	        
	        post("/pizza/update/:codigo", (request, response) -> pizzaService.update(request, response));
	           
	        get("/pizza/delete/:codigo", (request, response) -> pizzaService.delete(request, response));
	       
	    }

}

