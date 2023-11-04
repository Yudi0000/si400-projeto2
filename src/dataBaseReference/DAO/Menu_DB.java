package dataBaseReference.DAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import dataBaseReference.DTO.Customer;

public class Menu_DB {
		private Scanner scanner;
		private AbstractCustomerDAO customerDAO = null;
		
		public Menu_DB(Scanner scanner) {
	        this.scanner = scanner;
	    }

		public void displayMainMenu() {
		    System.out.println("Menu Principal:");
		    System.out.println("1. Gerenciar Customers");
		    System.out.println("2. Gerenciar Orders");
		    System.out.println("3. Gerenciar Relatórios");
		    System.out.println("4. Informações");
		    System.out.println("5. Sair");
		    System.out.print("Choose an option: ");
		}
	   
	   public void handleCustomerMenu() {
		    int customerChoice = 0;
		    do {
		        displayCustomerMenu();
		        customerChoice = scanner.nextInt();
		        scanner.nextLine();

		        switch (customerChoice) {
		        	case 1: //FUNCIONANDO
		        		  Customer newCustomer = new Customer();
			        	    System.out.print("Enter the customer ID: ");
			        	    int custumerId = scanner.nextInt();
			        	    if (custumerId >= 7000 && custumerId <= 7999) {
			        	    	newCustomer.setId(custumerId);
			        	    } else {
			        	    	System.out.println("Invalid number, try again.");
			        	    	break;
			        	    }
			        	    scanner.nextLine();
			        	    System.out.print("Enter the customer's name: ");
			        	    newCustomer.setName(scanner.nextLine());
			        	    System.out.print("Enter the customer's city: ");
			        	    newCustomer.setCity(scanner.nextLine());
			        	    System.out.print("Enter the client's state: ");
			        	    newCustomer.setState(scanner.nextLine());

			        	    try {
			        	        customerDAO.addCustomer(newCustomer);
			        	        System.out.println("Customer added successfully!");
			        	        
			        	    } catch (SQLException e) {
			        	        System.err.println("Error adding customer: " + e.getMessage());
			        	    }
			        	    break;
		        	case 2: //FUNCIONANDO
		        	    System.out.print("Enter the customer ID: ");
		        	    int customerId = scanner.nextInt();
		        	    scanner.nextLine();

		        	    try {
		        	        Customer customer = customerDAO.getCustomerById(customerId);

		        	        if (customer != null) {
		        	            System.out.println("Customer Information:");
		        	            System.out.println("ID: " + customer.getId());
		        	            System.out.println("Name: " + customer.getName());
		        	            System.out.println("City: " + customer.getCity());
		        	            System.out.println("State: " + customer.getState());
		        	        } else {
		        	            System.out.println("Customer not found.");
		        	        }
		        	    } catch (SQLException e) {
		        	        System.err.println("Error recovering client: " + e.getMessage());
		        	    }
		        	    break;
		        	case 3: //Funcionando
		        	    System.out.print("Enter the customer Name: ");
		        	    String customerName = scanner.nextLine();

		        	    try {
		        	        List<Customer> customersWithSameName = customerDAO.getCustomerByName(customerName);

		        	        if (customersWithSameName.isEmpty()) {
		        	            System.out.println("No customers with the name " + customerName + " found.");
		        	        } else {
		        	            System.out.println("Customers with the name " + customerName + ":");
		        	            for (Customer customer : customersWithSameName) {
		        	                System.out.println("ID: " + customer.getId());
		        	                System.out.println("Name: " + customer.getName());
		        	                System.out.println("City: " + customer.getCity());
		        	                System.out.println("State: " + customer.getState());
		        	                System.out.println();
		        	            }
		        	        }
		        	    } catch (SQLException e) {
		        	        System.err.println("Error recovering clients: " + e.getMessage());
		        	    }
		        	    break;

		        	
		        	case 4: //FUNCIONANDO
		        	    System.out.print("Enter the ID of the customer you want to delete: ");
		        	    int customerIdDelete = scanner.nextInt();

		        	    try {
		        	        Customer customerToDelete = customerDAO.getCustomerById(customerIdDelete);

		        	        if (customerToDelete != null) {
		        	            customerDAO.deleteCustomer(customerIdDelete);
		        	            System.out.println("Customer deleted successfully!");
		        	        } else {
		        	            System.out.println("Customer with ID " + customerIdDelete + " No customers were excluded.");
		        	        }
		        	    } catch (SQLException e) {
		        	        System.err.println("Error deleting customer: " + e.getMessage());
		        	    }
		        	    break;
		            case 5:
		                System.out.println("Returning to the Main Menu.");
		                break;
		            default:
		                System.out.println("Invalid option. Try again.");
		                break;
		        }
		    } while (customerChoice != 5);
		}

		public void displayCustomerMenu() {
		    System.out.println("Menu Customer:");
		    System.out.println("1. Add Customer");
		    System.out.println("2. Query Customer informations by ID");
		    System.out.println("3. Query customer information by Name");
		    System.out.println("4. Delete Customer");
		    System.out.println("5. Return to Main Menu");
		    System.out.print("Choose an option: ");
		} 

		public void handleOrderMenu() {
		    int orderChoice = 0;
		    do {
		        displayOrderMenu();
		        orderChoice = scanner.nextInt();
		        scanner.nextLine();

		        switch (orderChoice) {
		            case 1:
		                // Função para inserir um novo order
		                break;
		            case 2:
		                // Função para listar orders
		                break;
		            case 3:
		                // Função para atualizar order
		                break;
		            case 4:
		                System.out.println("Returning to the Main Menu.");
		                break;
		            default:
		                System.out.println("Invalid option. Try again.");
		                break;
		        }
		    } while (orderChoice != 4);
		}

		public void displayOrderMenu() {
		    System.out.println("Menu Order:");
		    System.out.println("1. Inserir um Order");
		    System.out.println("2. Consultar Orders");
		    System.out.println("3. Remover um Order");
		    System.out.println("4. Return to Main Menu");
		    System.out.print("Choose an option:: ");
		}
		
		public void handleReportsMenu() {
		    int reportChoice = 0;
		    do {
		        displayReportsMenu();
		        reportChoice = scanner.nextInt();
		        scanner.nextLine();

		        switch (reportChoice) {
		            //case 1:
		            //    displayCustomersOrderedById();
		            //    break;
		        case 2: // FUNCIONANDO
		            try {
		                List<Customer> customers = customerDAO.getAllCustomersOrderedByName();
		                if (customers.isEmpty()) {
		                    System.out.println("No customers found.");
		                } else {
		                    System.out.println("Customers sorted by name:");
		                    for (Customer customer : customers) {
		                        System.out.println("Name: " + customer.getName());
		                        System.out.println("ID: " + customer.getId());
		                        System.out.println("City: " + customer.getCity());
		                        System.out.println("State: " + customer.getState());
		                        System.out.println();
		                    }
		                }
		            } catch (SQLException e) {
		                System.err.println("Error retrieving clients: " + e.getMessage());
		            }
		            break;
		            //case 3:
		            //    displayOrdersOrderedByNumber();
		            //    break;
		            //case 4:
		            //    displayOrdersByCustomerOrderedByName();
		            //    break;
		            case 5:
		                System.out.println("Returning to the Main Menu.");
		                break;
		            default:
		                System.out.println("Invalid option. Try again.");
		                break;
		        }
		    } while (reportChoice != 5);
		}

		public void displayReportsMenu() {
		    System.out.println("Reports Menu:");
		    System.out.println("1. Customers sorted by ID");
		    System.out.println("2. Customers sorted by name");
		    System.out.println("3. Orders sorted by number");
		    System.out.println("4. Customer orders sorted by name");
		    System.out.println("5. Return to Main Menu");
		    System.out.print("Choose an option:: ");
		}
		
		public void handleInformationMenu() {
		    int infoChoice = 0;
		    do {
		        displayInformationMenu();
		        infoChoice = scanner.nextInt();
		        scanner.nextLine();

		        switch (infoChoice) {
		            case 1:
		                displayHelp();
		                break;
		            case 2:
		                displayAbout();
		                break;
		            case 3:
		                System.out.println("Returning to the Main Menu.");
		                break;
		            default:
		                System.out.println("Invalid option. Try again.");
		                break;
		        }
		    } while (infoChoice != 3);
		}

		public void displayInformationMenu() {
		    System.out.println("Menu Informações:");
		    System.out.println("1. Help");
		    System.out.println("2. About");
		    System.out.println("3. Return to Main Menu");
		    System.out.print("Choose an option:: ");
		}

		public void displayHelp() {
		    System.out.println("Texto explicativo do programa...");
		}

		public void displayAbout() {
		    System.out.println("Versão do programa...");
		    System.out.println("Créditos de autoria...");
		}
}
