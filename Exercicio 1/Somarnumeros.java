package application;

import java.util.Scanner;

public class Somarnumeros {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int num1, num2, soma=0;
		System.out.println("Digite um numero qualquer:");
		num1= sc.nextInt();
		System.out.println("Digite outro numero: ");
		num2 = sc.nextInt();
		
		soma= num1+num2;
		
		System.out.println("A soma do números é: "+ soma);
		sc.close();

	}

}
