package Homework;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Scanner;

public class Program {
	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws Exception {

		System.out.println("Enter server url:");
		String serverUrl = scanner.nextLine();
		System.out.println("Enter username:");
		String userName = scanner.nextLine();
		System.out.println("Enter password:");
		String passWord = scanner.nextLine();

		ProductDAO productDao = new ProductDAO(serverUrl, userName, passWord);

		if (productDao.getConnection() != null) {
			System.out.println("Connection Successfully !");
			handleMenu(productDao);
		} else {
			System.out.println("Connection Failed! Some errors occur please try again !");
		}

	}

	private static void handleMenu(ProductDAO productDAO) {
		boolean isExist = false;

		do {
			System.out.println("\n--------------Options---------------");
			System.out.println("1. Read all products");
			System.out.println("2. Read detail of a product by id");
			System.out.println("3. Add a new product");
			System.out.println("4. Update a product");
			System.out.println("5. Delete a product by id");
			System.out.println("6. Exit");
			System.out.print("\nYour choice: ");
			String userChoice = scanner.nextLine();

			switch (userChoice) {
				case "1":
					handleReadAllProducts(productDAO);
					break;
				case "2":
					handleReadProductDetailById(productDAO);
					break;
				case "3":
					handleAddNewProduct(productDAO);
					break;
				case "4":
					handleUpdateProduct(productDAO);
					break;
				case "5":
					handleDeleteProductById(productDAO);
					break;
				case "6":
					isExist = true;
					System.out.println("--------------End Program-------------");
					break;
				default:
					System.out.println("Selection not valid ! Please choose again!");
			}
		} while (!isExist);
	}

	private static void handleReadAllProducts(ProductDAO productDAO) {
		List<Product> products = productDAO.readAll();

		System.out.println("\nProduct List:");
		for (Product product : products) {
			System.out.println(product.toString());
		}
	}

	private static void handleReadProductDetailById(ProductDAO productDAO) {
		System.out.print("Enter product ID:");
		Integer id = scanner.nextInt();
		scanner.nextLine();
		Product product = productDAO.read(id);

		if (product != null) {
			System.out.println(product.toString());
		} else {
			System.out.println("Product with id = " + id + "does not exist.");
		}

	}

	private static void handleAddNewProduct(ProductDAO productDAO) {
		System.out.print("Enter product name:");
		String name = scanner.nextLine();
		System.out.print("Enter product price:");
		Integer price = scanner.nextInt();
		scanner.nextLine();
		Product product = new Product();
		product.setName(name);
		product.setPrice(price);

		if (productDAO.add(product) != null) {
			System.out.println("Added product successfully !");
		} else {
			System.out.println("Added product failed, please try again !");
		}
	}

	private static void handleUpdateProduct(ProductDAO productDAO) {
		System.out.print("Enter product ID:");
		Integer id = scanner.nextInt();
		scanner.nextLine();
		Product product = productDAO.read(id);

		if (product != null) {
			System.out.print("Enter product name:");
			String name = scanner.nextLine();
			System.out.print("Enter product price:");
			Integer price = scanner.nextInt();
			scanner.nextLine();
			product.setName(name);
			product.setPrice(price);
			if (productDAO.update(product)) {
				System.out.println("Product updated successfully !");
			} else {
				System.out.println("Product updated failed ! Please try again !");
			}
		} else {
			System.out.println("Product with id = " + id + "does not exist.");
		}
	}

	private static void handleDeleteProductById(ProductDAO productDAO) {
		System.out.print("Enter product ID:");
		Integer id = scanner.nextInt();
		scanner.nextLine();

		if (productDAO.delete(id)) {
			System.out.println("Product deleted successfully !");
		} else {
			System.out.println("Product with id = " + id + "does not exist.");
		}
	}

}
