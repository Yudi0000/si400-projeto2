package dataBaseReference.DAO;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.math.BigDecimal;

import dataBaseReference.DTO.Customer;
import dataBaseReference.DTO.Orders;

public class Menu_DB {
	private Scanner scanner;
    private AbstractCustomerDAO customerDAO = null;
    private AbstractOrderDAO orderDAO = null;

    public Menu_DB(Scanner scanner, AbstractCustomerDAO customerDAO, AbstractOrderDAO orderDAO) {
        this.scanner = scanner;
        this.customerDAO = customerDAO;
        this.orderDAO = orderDAO;
    }


		public void displayMainMenu() {
		    System.out.println("Menu Principal:");
		    System.out.println("1. Menu Customers");
		    System.out.println("2. Menu Orders");
		    System.out.println("3. Menu Relatórios");
		    System.out.println("4. Informations");
		    System.out.println("5. Exit");
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
		            System.out.print("Enter the order number: ");
		            int orderNumber = scanner.nextInt();
		            scanner.nextLine();

		            Orders newOrder = new Orders();
		            newOrder.setNumber(orderNumber);

		            System.out.print("Enter the customer ID for the order: ");
		            int customerId = scanner.nextInt();
		            scanner.nextLine();
		            newOrder.setCustomerId(customerId);

		            System.out.print("Enter the description for the order: ");
		            String description = scanner.nextLine();
		            newOrder.setDescription(description);

		            boolean validPrice = false;

		            while (!validPrice) {
		                System.out.print("Enter the price for the order: ");

		                try {
		                    String priceString = scanner.nextLine();
		                    BigDecimal price = new BigDecimal(priceString.replace(',', '.')); // Substitua vírgulas por pontos
		                    newOrder.setPrice(price);
		                    validPrice = true;
		                } catch (NumberFormatException e) {
		                    System.err.println("Invalid input. Please enter a valid decimal number.");
		                }
		                
		                try {
		                    orderDAO.addOrder(newOrder);
		                    System.out.println("Order added successfully!");
		                } catch (SQLException e) {
		                    System.err.println("Error adding order: " + e.getMessage());
		                }
		            }       
		            	break;
		       	case 2:
		            System.out.print("Enter the order number you want to retrieve: ");
		            int orderNumberToRetrieve = scanner.nextInt();
		            scanner.nextLine();

		            try {
		                Orders order = orderDAO.getOrderByNumber(orderNumberToRetrieve);
		                if (order != null) {
		                    System.out.println("Order Information:");
		                    System.out.println("Order Number: " + order.getNumber());
		                    System.out.println("Customer ID: " + order.getCustomerId());
		                    System.out.println("Description: " + order.getDescription());
		                    System.out.println("Price: " + order.getPrice());
		                } else {
		                    System.out.println("Order not found.");
		                }
		            } catch (SQLException e) {
		                System.err.println("Error retrieving order: " + e.getMessage());
		            }
		            	break;
		       	case 3:
		                // Função para apagar
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
		    System.out.println("1. Create an Order");
		    System.out.println("2. Read an Orders");
		    System.out.println("3. Remove an Order");
		    System.out.println("4. Return to Main Menu");
		    System.out.print("Choose an option: ");
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
		    System.out.print("Choose an option: ");
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
		    System.out.print("Choose an option: ");
		}

		public void displayHelp() {
		    System.out.println("Texto explicativo do programa...");
		}

		public void displayAbout() {
		    System.out.println("Versão do programa...");
		    System.out.println("Créditos de autoria...");
		}
}
