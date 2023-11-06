package dataBaseReference.DAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.math.BigDecimal;

import dataBaseReference.DTO.Customer;
import dataBaseReference.DTO.Orders;
import dataBaseReference.System.Controller;

public class Menu_DB {
	private Scanner scanner;
    private AbstractCustomerDAO customerDAO = null;
    private AbstractOrderDAO orderDAO = null;
    private Controller controller;

    public Menu_DB(Scanner scanner, AbstractCustomerDAO customerDAO, AbstractOrderDAO orderDAO) {
        this.scanner = scanner;
        this.customerDAO = customerDAO;
        this.orderDAO = orderDAO;

    }
    
    public Menu_DB(Scanner scanner, AbstractCustomerDAO customerDAO, AbstractOrderDAO orderDAO, Controller controller) {
        this(scanner, customerDAO, orderDAO);
        this.controller = controller; 
    }

		public void displayMainMenu() {
		    System.out.println("Main Menu:");
		    System.out.println("1. Menu Customers");
		    System.out.println("2. Menu Orders");
		    System.out.println("3. Menu Reports");
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
		        	case 1:
		        		
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
		        	            System.out.println("-----------------------------");
			        	        System.out.println("Customer added successfully!");
		        	            System.out.println("-----------------------------");

			        	    } catch (SQLException e) {
		        	            System.out.println("-----------------------------");
			        	        System.err.println("Error adding customer: " + e.getMessage());
		        	            System.out.println("-----------------------------");

			        	    }
			        	    break;
		        	case 2:
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
		        	            System.out.println("-----------------------------");

		        	        } else {
		        	            System.out.println("-----------------------------");
		        	            System.out.println("Customer not found.");
		        	            System.out.println("-----------------------------");

		        	        }
		        	    } catch (SQLException e) {
		        	        System.err.println("Error recovering client: " + e.getMessage());
		        	    }
		        	    break;
		        	case 3:
		        	    System.out.print("Enter the customer Name: ");
		        	    String customerName = scanner.nextLine();

		        	    try {
		        	        List<Customer> customersWithSameName = customerDAO.getCustomerByName(customerName);

		        	        if (customersWithSameName.isEmpty()) {
		        	            System.out.println("-----------------------------");
		        	            System.out.println("No customers with the name " + customerName + " found.");
		        	            System.out.println("-----------------------------");

		        	        } else {
		        	            System.out.println("Customers with the name " + customerName + ":");
		        	            for (Customer customer : customersWithSameName) {
		        	                System.out.println("ID: " + customer.getId());
		        	                System.out.println("Name: " + customer.getName());
		        	                System.out.println("City: " + customer.getCity());
		        	                System.out.println("State: " + customer.getState());
		        	                System.out.println();
			        	            System.out.println("-----------------------------");

		        	            }
		        	        }
		        	    } catch (SQLException e) {
		        	        System.err.println("Error recovering clients: " + e.getMessage());
		        	    }
		        	    break;
		        	case 4:
		        	    System.out.print("Enter the ID of the customer you want to delete: ");
		        	    int customerIdDelete = scanner.nextInt();
		        	    try {
		        	        Customer customerToDelete = customerDAO.getCustomerById(customerIdDelete);

		        	        if (customerToDelete != null) {
		        	            customerDAO.deleteCustomer(customerIdDelete);
		        	            System.out.println("-----------------------------");
		        	            System.out.println("Customer deleted successfully!");
		        	            System.out.println("-----------------------------");

		        	        } else {
		        	            System.out.println("-----------------------------");
		        	            System.out.println("Customer with ID " + customerIdDelete + "not found. No customers were excluded.");
		        	            System.out.println("-----------------------------");

		        	        }
		        	    } catch (SQLException e) {
	        	            System.out.println("-----------------------------");
		        	        System.err.println("Error deleting customer: " + e.getMessage());
	        	            System.out.println("-----------------------------");

		        	    }
		        	    break;
		            case 5:
        	            System.out.println("-----------------------------");
		                System.out.println("Returning to the Main Menu.");
        	            System.out.println("-----------------------------");

		                break;
		            default:
        	            System.out.println("-----------------------------");
		                System.out.println("Invalid option. Try again.");
        	            System.out.println("-----------------------------");

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
		                    BigDecimal price = new BigDecimal(priceString.replace(',', '.'));
		                    newOrder.setPrice(price);
		                    validPrice = true;
		                } catch (NumberFormatException e) {
	        	            System.out.println("-----------------------------");
		                	System.err.println("Invalid input. Please enter a valid decimal number.");
	        	            System.out.println("-----------------------------");

		                }
		                
		                try {
		                    orderDAO.addOrder(newOrder);
	        	            System.out.println("-----------------------------");
		                    System.out.println("Order added successfully!");
	        	            System.out.println("-----------------------------");

		                } catch (SQLException e) {
	        	            System.out.println("-----------------------------");
		                    System.err.println("Error adding order: " + e.getMessage());
	        	            System.out.println("-----------------------------");

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
	        	            System.out.println("-----------------------------");

		                } else {
	        	            System.out.println("-----------------------------");
		                    System.out.println("Order not found.");
	        	            System.out.println("-----------------------------");

		                }
		            } catch (SQLException e) {
		                System.err.println("Error retrieving order: " + e.getMessage());
		            }
		            	break;
		       	case 3:
		       	    System.out.print("Enter the order number to delete: ");
		       	    int orderNumberToDelete = scanner.nextInt();
		       	    scanner.nextLine();

		       	    try {
		       	        orderDAO.deleteOrderByNumber(orderNumberToDelete);
        	            System.out.println("-----------------------------");
		       	        System.out.println("Order deleted successfully!");
        	            System.out.println("-----------------------------");

		       	    } catch (SQLException e) {
        	            System.out.println("-----------------------------");
		       	        System.err.println("Error deleting order: " + e.getMessage());
        	            System.out.println("-----------------------------");

		       	    }
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
		
		public void handleReportsMenu() throws SQLException {
		    int reportChoice = 0;
		    do {
		        displayReportsMenu();
		        reportChoice = scanner.nextInt();
		        scanner.nextLine();

		        switch (reportChoice) {
		            case 1:
		        try {
		            List<Customer> customers = customerDAO.getAllCustomersOrderedById();

		            System.out.println("Customers Ordered by ID:");
		            for (Customer customer : customers) {
		                System.out.println("ID: " + customer.getId());
		                System.out.println("Name: " + customer.getName());
		                System.out.println("City: " + customer.getCity());
		                System.out.println("State: " + customer.getState());
		                System.out.println();
        	            System.out.println("-----------------------------");

		            }
		        } catch (SQLException e) {
		            System.err.println("Error retrieving customers: " + e.getMessage());
		        }		            
		        	break;
		        case 2:
		            try {
		                List<Customer> customers = customerDAO.getAllCustomersOrderedByName();
		                if (customers.isEmpty()) {
	        	            System.out.println("-----------------------------");
		                    System.out.println("No customers found.");
	        	            System.out.println("-----------------------------");

		                } else {
		                    System.out.println("Customers sorted by name:");
		                    for (Customer customer : customers) {
		                        System.out.println("Name: " + customer.getName());
		                        System.out.println("ID: " + customer.getId());
		                        System.out.println("City: " + customer.getCity());
		                        System.out.println("State: " + customer.getState());
		                        System.out.println();
		        	            System.out.println("-----------------------------");

		                    }
		                }
		            } catch (SQLException e) {
		                System.err.println("Error retrieving clients: " + e.getMessage());
		            }
		            break;
		        case 3:
		            try {
		                List<Orders> orders = orderDAO.getAllOrdersOrderedByNumber();
		                for (Orders order : orders) {
		                    System.out.println("Order Number: " + order.getNumber());
		                    System.out.println("Customer ID: " + order.getCustomerId());
		                    System.out.println("Description: " + order.getDescription());
		                    System.out.println("Price: " + order.getPrice());
		                    System.out.println();
	        	            System.out.println("-----------------------------");

		                }
		            } catch (SQLException e) {
		                System.err.println("Error retrieving orders: " + e.getMessage());
		            }
		            break;
		        case 4:
		            List<Orders> customerOrdersReport = orderDAO.generateOrdersReportByCustomerName();

		            if (customerOrdersReport.isEmpty()) {
		                System.out.println("Nenhum pedido encontrado.");
		            } else {
		                String currentCustomer = null;
		                BigDecimal totalPrice = BigDecimal.ZERO;

		                for (Orders order : customerOrdersReport) {
		                    if (order.getCustomerName() != null) {
		                        if (!order.getCustomerName().equals(currentCustomer)) {
		                            if (currentCustomer != null) {
		                                System.out.println("Total Price: " + totalPrice);
		                                System.out.println("-----------------------------");
		                            }

		                            currentCustomer = order.getCustomerName();
		                            totalPrice = BigDecimal.ZERO;
		                            System.out.println("-----------------------------");
		                            System.out.println("Customer: " + currentCustomer);
		                        }

		                        System.out.println("Número do Pedido: " + order.getNumber());
		                        System.out.println("Descrição: " + order.getDescription());
		                        System.out.println("Preço: " + order.getPrice());
	                            System.out.println("-----------------------------");

		                        totalPrice = totalPrice.add(order.getPrice());
		                    }
		                }

		                System.out.println("Total Price: " + totalPrice);
		                System.out.println("-----------------------------");
		            }

		            break;


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
		    System.out.println("4. Customer's orders sorted by name");
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
		                handleSpecialOperationsMenu();
		                break;
		            case 4:
		                System.out.println("Returning to the Main Menu.");
		                break;
		            default:
		                System.out.println("Invalid option. Try again.");
		                break;
		        }
		    } while (infoChoice != 4);
		}

		public void displayInformationMenu() {
		    System.out.println("Menu Informações:");
		    System.out.println("1. Help");
		    System.out.println("2. About");
		    System.out.println("3. Special operations");
		    System.out.println("4. Return to Main Menu");
		    System.out.print("Choose an option: ");
		}

		public void handleSpecialOperationsMenu() {
		    int specialChoice = 0;
		    do {
		        displaySpecialOperationsMenu();
		        specialChoice = scanner.nextInt();
		        scanner.nextLine();
		        
		        switch (specialChoice) {
		            case 1:
		                controller.insertData();
		                break;
		            case 2:
		            	controller.deleteAllData();
		                break;
		            case 3:
		            	controller.requestData();
		                break;
		            case 4:
		                System.out.println("Returning to Information Menu.");
		                break;
		            default:
		                System.out.println("Invalid option. Try again.");
		                break;
		        }
		    } while (specialChoice != 4);
		}

		public void displaySpecialOperationsMenu() {
		    System.out.println("Special Operation:");
		    System.out.println("1. Insert Random Data");
		    System.out.println("2. Delete All Data");
		    System.out.println("3. Request Data");
		    System.out.println("4. Return to Information Menu");
		    System.out.print("Choose a special operation: ");
		}

		public void displayHelp() {
            System.out.println("-----------------------------");
		    System.out.println("Customer and Order Database Management System is software designed");
		    System.out.println("to help businesses maintain an organized record of their customers and orders.");
		    System.out.println("This system offers essential functionality for storing and managing ");
		    System.out.println("important information about customers and the products or services they request.");
            System.out.println("-----------------------------");

		}

		public void displayAbout() {
            System.out.println("-----------------------------");
		    System.out.println("This is Version 1.0 ");
		    System.out.println("Credits:");
	        System.out.println("Gabrielly Tegon - 260797");
	        System.out.println("Henrique Bexiga Eulálio - 255002");
	        System.out.println("Luiz Henrique Oliveira André - 247546");
	        System.out.println("Matheus Yudi Colli Issida - 260848");
	        System.out.println("Vinícius da Silva Germano - 254377");
            System.out.println("-----------------------------");

		}
}
