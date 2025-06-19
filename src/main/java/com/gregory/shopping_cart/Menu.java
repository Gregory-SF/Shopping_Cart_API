package com.gregory.shopping_cart;

import java.util.Scanner;

import javax.persistence.NoResultException;

import com.gregory.shopping_cart.controller.ProductController;
import com.gregory.shopping_cart.controller.ShoppingCartController;
import com.gregory.shopping_cart.controller.StockController;
import com.gregory.shopping_cart.dao.AbstractDao;
import com.gregory.shopping_cart.exception.DbException;
import com.gregory.shopping_cart.exception.InvalidValuesException;
import com.gregory.shopping_cart.exception.NoProductException;
import com.gregory.shopping_cart.model.entities.Product;
import com.gregory.shopping_cart.model.entities.ShoppingCart;
import com.gregory.shopping_cart.model.entities.Stock;

public class Menu {
	private static Scanner scanner = new Scanner(System.in);
	private static StockController stockC = new StockController();
	private static ShoppingCartController shopC = new ShoppingCartController();
	private static ProductController prodC = new ProductController();
	private static AbstractDao<?> dao;

	public static void showMenu() {

		while (true) {
			for (int i = 0; i < 50; ++i)
				System.out.println();
			System.out.println("What would you like to manage?");
			System.out.println("1. Product\n2. Stock\n3. Cart\n4. Exit");
			System.out.print("R: ");
			int mainChoice = scanner.nextInt();
			scanner.nextLine();

			if (mainChoice == 4) {
				System.out.println("\nExiting...");
				scanner.close();
				dao.close();
				break;
			} else if (mainChoice != 1 && mainChoice != 2 && mainChoice != 3) {
				System.out.println("\nInvalid option. Try again.");
				continue;
			}

			System.out.println("\nWhat would you like to do now?");
			System.out.println("1. Create\n2. Edit\n3. View All\n4. View Specific\n5. Delete\n6. Back");
			System.out.print("R: ");
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
		System.out.print("Enter product name: ");
		String name = scanner.nextLine();
		System.out.print("Enter product type: ");
		String type = scanner.nextLine();
		System.out.print("Enter product price: ");
		double price = scanner.nextDouble();
		scanner.nextLine();

		Product product = new Product(name, type, price);
		return product;
	}

	private static Stock createStock() {
		Stock stock = new Stock();
		System.out.println("You need to get a product, what will you inform?");
		System.out.print("1. By inserting an Id\n2. By inserting a product's name and type "
				+ "(Be sure to type it correctly, otherwise it won't find the product)\nR: ");
		int choice = scanner.nextInt();
		scanner.nextLine();
		if (choice == 1) {
			System.out.print("\nInsert an Id: ");
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
		System.out.println("\nYou need to get a product, what will you inform?");
		System.out.print("1. By inserting an Id\n2. By inserting a product's name and type "
				+ "(Be sure to type it correctly, otherwise it won't find the product)\nR: ");
		int choice = scanner.nextInt();
		scanner.nextLine();
		if (choice == 1) {
			System.out.print("Insert an Id: ");
			shoppingCart.setProduct(prodC.findById(scanner.nextLong()));
		} else if (choice == 2) {
			System.out.print("Enter product name: ");
			String name = scanner.nextLine();
			System.out.print("Enter product type: ");
			String type = scanner.nextLine();
			System.out.println(prodC.findByAttributes(name, type));
			shoppingCart.setProduct(prodC.findByAttributes(name, type));
		} else {
			System.out.print("Invalid answer!");
			return null;
		}
		System.out.print("Enter an amount to add to the cart: ");
		Integer quantity = scanner.nextInt();
		shoppingCart.setQuantity(quantity);
		System.out.println(shoppingCart);

		return shoppingCart;
	}

	private static void ProductMenu(int subChoice) {
		try {
			switch (subChoice) {
			case 1:
				prodC.create(createProduct());
				System.out.println("\nProduct created!");
				scanner.nextLine();
				break;
			case 2:
				System.out.println("\nWrite information about the product you want to change.");
				Product oldProduct = createProduct();
				System.out.println("\nNow, write the changed information.");
				Product newProduct = createProduct();
				prodC.update(oldProduct, newProduct);
				System.out.println("\nProduct updated!");
				scanner.nextLine();
				break;
			case 3:
				prodC.findAll().forEach(System.out::println);
				System.out.println("\n\nPress enter to continue.");
				scanner.nextLine();
				break;
			case 4:
				System.out.print("\nInsert an Id: ");
				System.out.println(prodC.findById(scanner.nextLong()));
				System.out.println("\n\nPress enter to continue.");
				scanner.nextLine();
				break;
			case 5:
				prodC.delete(createProduct());
				System.out.println("\nProduct deleted!");
				scanner.nextLine();
				break;
			default:
				System.out.println("\nInvalid option.");
				scanner.nextLine();
			}
		} catch (NoProductException e) {
			System.out.println(e.getMessage() + "! Please insert a valid product.");
			scanner.nextLine();
			ProductMenu(subChoice);
		} catch (InvalidValuesException e) {
			System.out.println(e.getMessage());
			scanner.nextLine();
			ProductMenu(subChoice);
		} catch (DbException e) {
			System.out.println(e.getMessage());
			scanner.nextLine();
		}
	}

	private static void StockMenu(int subChoice) {
		try {
			switch (subChoice) {
			case 1:
				stockC.create(createStock());
				System.out.println("\nStock created!");
				scanner.nextLine();
				scanner.nextLine();
				break;
			case 2:
				System.out.println("\nHow you like to update your stock?");
				System.out.print("1. By inserting a stock's Id\n2. By inserting a product's Id\nR: ");
				int choice = scanner.nextInt();
				scanner.nextLine();
				if (choice == 1) {
					System.out.print("\nInsert an Id: ");
					Long id = scanner.nextLong();
					System.out.println("\nNow insert the same product and the changed amount.");
					Stock stock = createStock();
					stock.setId(id);
					stockC.update(stock);
					System.out.println("\nStock updated!");
					scanner.nextLine();
					scanner.nextLine();
				} else if (choice == 2) {
					System.out.print("\nInsert an Id: ");
					Long id = scanner.nextLong();
					System.out.print("Insert stocked quantity: ");
					Integer quantity = scanner.nextInt();
					stockC.updateByProductId(id, quantity);
					System.out.println("\nStock updated!");
					scanner.nextLine();
					scanner.nextLine();
				} else {
					System.out.print("\nInvalid answer!");
					scanner.nextLine();
				}
				break;
			case 3:
				stockC.findAll().forEach(System.out::println);
				scanner.nextLine();
				break;
			case 4:
				System.out.print("\nInsert an Id: ");
				System.out.println(stockC.findById(scanner.nextLong()));
				scanner.nextLine();
				scanner.nextLine();
				break;
			case 5:
				System.out.print("\nInsert stock's Id: ");
				stockC.delete(scanner.nextLong());
				System.out.println("\nStock deleted!");
				scanner.nextLine();
				scanner.nextLine();
				break;
			default:
				System.out.println("\nInvalid option.");
				scanner.nextLine();
			}
		} catch (NoProductException e) {
			System.out.println(e.getMessage());
			scanner.nextLine();
			StockMenu(subChoice);
		} catch (NoResultException e) {
			System.out.println(e.getMessage() + ". Try again.");
			scanner.nextLine();
			StockMenu(subChoice);
		} catch (InvalidValuesException e) {
			System.out.println(e.getMessage());
			scanner.nextLine();
			StockMenu(subChoice);
		} catch (NullPointerException e) {
			System.out.println("Try again.");
			scanner.nextLine();
			StockMenu(subChoice);
		}

	}

	private static void CartMenu(int subChoice) {
		try {
			switch (subChoice) {
			case 1:
				shopC.create(createShoppingCart());
				System.out.println("\nProduct added to cart!");
				scanner.nextLine();
				scanner.nextLine();
				break;
			case 2:
				System.out.println("\nHow you like to update your cart?");
				System.out.print("1. By inserting its Id\n2. By inserting a product's Id\nR: ");
				int choice = scanner.nextInt();
				if (choice == 1) {
					System.out.print("Insert an Id: ");
					Long id = scanner.nextLong();
					ShoppingCart shoppingCart = createShoppingCart();
					shoppingCart.setId(id);
					System.out.println(shoppingCart);
					shopC.update(shoppingCart);
					System.out.println("\nCart updated!");
					scanner.nextLine();
					scanner.nextLine();

				} else if (choice == 2) {
					System.out.print("Insert an Id: ");
					Long id = scanner.nextLong();
					System.out.print("Insert the quantity you want to add to the cart: ");
					Integer quantity = scanner.nextInt();
					shopC.updateByProductId(id, quantity);
					System.out.println("\nCart Updated!");
					scanner.nextLine();
					scanner.nextLine();

				} else {
					System.out.print("Invalid answer!");
					scanner.nextLine();
					scanner.nextLine();
				}
				break;
			case 3:
				shopC.findAll().forEach(System.out::println);
				scanner.nextLine();
				break;
			case 4:
				System.out.print("Insert an Id: ");
				System.out.println(shopC.findById(scanner.nextLong()));
				scanner.nextLine();
				scanner.nextLine();
				break;
			case 5:
				System.out.print("Insert an Id: ");
				shopC.delete(scanner.nextLong());
				System.out.println("\nCart Deleted!");
				scanner.nextLine();
				scanner.nextLine();
				break;
			default:
				System.out.println("Invalid option.");
				scanner.nextLine();
			}
		} catch (NoProductException e) {
			System.out.println(e.getMessage());
			scanner.nextLine();
			CartMenu(subChoice);
		} catch (NoResultException e) {
			System.out.println(e.getMessage() + ". Try again.");
			scanner.nextLine();
			CartMenu(subChoice);
		} catch (InvalidValuesException e) {
			System.out.println(e.getMessage());
			scanner.nextLine();
			CartMenu(subChoice);
		} catch (NullPointerException e) {
			System.out.println("Try again.");
			scanner.nextLine();
			CartMenu(subChoice);
		}
	}
}
