package com.gregory.shopping_cart;

import java.util.Scanner;

import com.gregory.shopping_cart.controller.ProductController;
import com.gregory.shopping_cart.controller.ShoppingCartController;
import com.gregory.shopping_cart.controller.StockController;
import com.gregory.shopping_cart.model.entities.Product;
import com.gregory.shopping_cart.model.entities.ShoppingCart;
import com.gregory.shopping_cart.model.entities.Stock;

public class Menu {
	private static Scanner scanner = new Scanner(System.in);
	private static StockController stockC = new StockController();
	private static ShoppingCartController shopC = new ShoppingCartController();
	private static ProductController prodC = new ProductController();

	public static void showMenu() {

		while (true) {
			System.out.println("What would you like to manage?");
			System.out.println("1. Product\n2. Stock\n3. Cart\n4. Exit");
			System.out.print("R: ");
			int mainChoice = scanner.nextInt();
			scanner.nextLine();

			if (mainChoice == 4) {
				System.out.println("Exiting...");
				scanner.close();
				break;
			} else if (mainChoice != 1 && mainChoice != 2 && mainChoice != 3) {
				System.out.println("Invalid option. Try again.");
				continue;
			}

			System.out.println("What would you like to do now?");
			System.out.println("1. Create\n2. Edit\n3. View All\n4. View Specific\n5. Delete\n6. Back");
			int subChoice = scanner.nextInt();
			scanner.nextLine();

			if (subChoice == 6) {
				continue;
			}

			switch (mainChoice) {
			case 1:
				ProductMenu(subChoice);
				break;
			case 2:
				StockMenu(subChoice);
				break;
			case 3:
				CartMenu(subChoice);
				break;
			}
		}
	}

	private static Product createProduct() {
		scanner.next();
		System.out.print("Enter product name: ");
		String name = scanner.nextLine();
		System.out.print("Enter product type: ");
		String type = scanner.nextLine();
		System.out.print("Enter product price: ");
		double price = scanner.nextDouble();

		Product product = new Product(name, type, price);
		return product;
	}

	private static Stock createStock() {
		Stock stock = new Stock();
		System.out.println("You need to get a product, what will you inform?");
		System.out.print("1. By inserting an Id\n2. By inserting a product's name and type "
				+ "(Be sure to type it correctly, otherwise it won't find the product)\nR: ");
		int choice = scanner.nextInt();
		if (choice == 1) {
			System.out.print("Insert an Id: ");
			stock.setProduct(prodC.findById(scanner.nextLong()));
		} else if (choice == 2) {
			System.out.print("Enter product name: ");
			String name = scanner.nextLine();
			System.out.print("Enter product type: ");
			String type = scanner.nextLine();
			stock.setProduct(prodC.findByAttributes(name, type));
		} else {
			System.out.print("Invalid answer!");
			return null;
		}

		System.out.print("Enter stocked quantity: ");
		Integer quantity = scanner.nextInt();
		stock.setQuantity(quantity);

		return stock;
	}

	private static ShoppingCart createShoppingCart() {
		ShoppingCart shoppingCart = new ShoppingCart();
		System.out.println("You need to get a product, what will you inform?");
		System.out.print("1. By inserting an Id\n2. By inserting a product's name and type "
				+ "(Be sure to type it correctly, otherwise it won't find the product)\nR: ");
		int choice = scanner.nextInt();
		if (choice == 1) {
			System.out.print("Insert an Id: ");
			shoppingCart.setProduct(prodC.findById(scanner.nextLong()));
		} else if (choice == 2) {
			System.out.print("Enter product name: ");
			String name = scanner.nextLine();
			System.out.print("Enter product type: ");
			String type = scanner.nextLine();
			shoppingCart.setProduct(prodC.findByAttributes(name, type));
		} else {
			System.out.print("Invalid answer!");
			return null;
		}
		System.out.print("Enter an amount to add to the cart: ");
		Integer quantity = scanner.nextInt();
		shoppingCart.setQuantity(quantity);

		return shoppingCart;
	}

	private static void ProductMenu(int subChoice) {
		switch (subChoice) {
		case 1:
			prodC.create(createProduct());
			System.out.println("Product created!");
			break;
		case 2:
			System.out.println("Write information about the product you want to change.");
			Product oldProduct = createProduct();
			System.out.println("Now, write the changed information.");
			Product newProduct = createProduct();
			prodC.update(oldProduct, newProduct);
			System.out.println("Product updated!");
			break;
		case 3:
			prodC.findAll().forEach(System.out::println);
			break;
		case 4:
			System.out.print("Insert an Id: ");
			System.out.println(prodC.findById(scanner.nextLong()));
			break;
		case 5:
			prodC.delete(createProduct());
			System.out.println("Product deleted!");
			break;
		default:
			System.out.println("Invalid option.");
		}
	}
	
	private static void StockMenu(int subChoice) {
		switch (subChoice) {
		case 1:
			stockC.create(createStock());
			break;
		case 2:
			System.out.println("How you like to update your stock?");
			System.out.print("1. By inserting a stock's Id\n2. By inserting a product's Id\nR: ");
			int choice = scanner.nextInt();
			if (choice == 1) {
				System.out.print("Insert an Id: ");
				Long id = scanner.nextLong();
				Stock stock = createStock();
				stock.setId(id);
				stockC.update(stock);
			} else if (choice == 2) {
				System.out.print("Insert an Id: ");
				Long id = scanner.nextLong();
				System.out.print("Insert stocked quantity: ");
				Integer quantity = scanner.nextInt();
				stockC.updateByProductId(id, quantity);
			} else System.out.print("Invalid answer!");
			break;
		case 3:
			stockC.findAll().forEach(System.out::println);
			break;
		case 4:
			System.out.print("Insert an Id: ");
			System.out.println(stockC.findById(scanner.nextLong()));
			break;
		case 5:
			System.out.print("Insert an Id: ");
			stockC.delete(scanner.nextLong());
			break;
		default:
			System.out.println("Invalid option.");
		}
	}
	
	private static void CartMenu(int subChoice) {
		switch (subChoice) {
		case 1:
			shopC.create(createShoppingCart());
			break;
		case 2:
			System.out.println("How you like to update your cart?");
			System.out.print("1. By inserting its Id\n2. By inserting a product's Id\nR: ");
			int choice = scanner.nextInt();
			if (choice == 1) {
				System.out.print("Insert an Id: ");
				Long id = scanner.nextLong();
				ShoppingCart shoppingCart = createShoppingCart();
				shoppingCart.setId(id);
				shopC.update(shoppingCart);
			} else if (choice == 2) {
				System.out.print("Insert an Id: ");
				Long id = scanner.nextLong();
				System.out.print("Insert the quantity you want to add to the cart: ");
				Integer quantity = scanner.nextInt();
				shopC.updateByProductId(id, quantity);
			} else System.out.print("Invalid answer!");
			break;
		case 3:
			shopC.findAll().forEach(System.out::println);
			break;
		case 4:
			System.out.print("Insert an Id: ");
			System.out.println(shopC.findById(scanner.nextLong()));
			break;
		case 5:
			System.out.print("Insert an Id: ");
			shopC.delete(scanner.nextLong());
			break;
		default:
			System.out.println("Invalid option.");
		}
	}
	
}

