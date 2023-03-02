package ti2cc;
import java.util.List;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        PizzaDAO pizzaDAO = new PizzaDAO();
        Pizza pizza = new Pizza("mexilhao", 'M', 35.25, "sem_borda");
        Scanner sc = new Scanner(System.in);

        Menu();
        int op = sc.nextInt();

        while (op >= 1 && op <= 4) {
            if (op == 1) {
                System.out.println("\n\n==== Inserir pizza === ");
                if (pizzaDAO.insert(pizza) == true) {
                    System.out.println("Inserção com sucesso -> " + pizza.toString());

                    System.out.println("\n\n==== Testando autenticação ===");
                    System.out.println(
                            "Pizza (" + pizza.getSabor() + "): " + pizzaDAO.autenticar("mexilhao", "sem_borda"));
                }
            } else if (op == 2) {
            	int opp=0;
            	listage();
            	opp=sc.nextInt();
            	if(opp==1) {
            		System.out.println("\n\n==== Mostrar pizzas do tamanho G === ");
                    List<Pizza> pizzas = pizzaDAO.getTamanhoG();
                    for (Pizza u : pizzas) {
                        System.out.println(u.toString());
                    }
            	} else if(opp==2) {
            		System.out.println("\n\n==== Mostrar pizzas por sabor === ");
                    List<Pizza> pizzas = pizzaDAO.getOrderBySabor();
                    for (Pizza u : pizzas) {
                        System.out.println(u.toString());
                    }
            		
            	} else if(opp==3) {
            		System.out.println("\n\n==== Mostrar pizzas por preco === ");
                    List<Pizza> pizzas = pizzaDAO.getOrderByPreco();
                    for (Pizza u : pizzas) {
                        System.out.println(u.toString());}
            	}
                
                
                
            } else if (op == 3) {
            	
                System.out.println("\n\n==== Atualizar borda (borda (" + pizza.getBorda() + ") === ");
                pizza.setBorda("com_borda");
                pizzaDAO.update(pizza);
                System.out.println("\n\n==== Testando Autenticação ===");
                System.out.println("Pizza (" + pizza.getBorda() + "): " + pizzaDAO.autenticar("sem_borda", "com_borda"));
                System.out.println("\n\n==== Invadir usando SQL Injection ===");
                System.out.println(
                        "Pizza (" + pizza.getBorda() + "): " + pizzaDAO.autenticar("sem_borda", "x' OR 'x' LIKE 'x"));
            } else {

                System.out.println("\n\n==== Excluir usuário (sabor " + pizza.getSabor() + ") === ");
                pizzaDAO.delete(pizza.getSabor());

            }
            Menu();
            op = sc.nextInt();
        }
        System.out.println("\n\n==== FIM DO PROGRAMA ====");
        sc.close();
    }
    public static void listage() {
    	System.out.println("Quer que a listagem venha de que maneira?");
    	System.out.println("1) Apenas pizzas G");
        System.out.println("2) Por sabor");
        System.out.println("3) Por preco");
    	
    }
    public static void Menu() {
        System.out.println("\nMENU DE OPÇÕES:");
        System.out.println("1) Inserir");
        System.out.println("2) Listar");
        System.out.println("3) Atualizar");
        System.out.println("4) Excluir");
        System.out.println("5) Sair");
    }
    }