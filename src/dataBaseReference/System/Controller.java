package dataBaseReference.System;
import java.util.Scanner;
import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import dataBaseReference.DAO.AbstractCustomerDAO;
import dataBaseReference.DAO.AbstractOrderDAO;
import dataBaseReference.DAO.Customer_DB_DAO;
import dataBaseReference.DAO.Customer_Mem_DAO;
import dataBaseReference.DAO.Order_DB_DAO;
import dataBaseReference.DAO.Order_Mem_DAO;
import dataBaseReference.DTO.Customer;
import dataBaseReference.DTO.Orders;
import dataBaseReference.RDBMS.MariaDBConnection;
import dataBaseReference.RDBMS.MemoryDBConnection;

public class Controller
   {
   private AbstractCustomerDAO customerDAO        = null;
   private AbstractOrderDAO    ordersDAO          = null;
   private MariaDBConnection   myDBConnection     = null;
   private MemoryDBConnection  memoryDBConnection = null;
   private DataBaseType        selectedDataBase   = DataBaseType.INVALID;
   private Scanner scanner;
   
   public Controller(DataBaseType selectedDataBase)
      {
      super();
      this.selectedDataBase = selectedDataBase;
      this.scanner = new Scanner(System.in);
      }

   private void openConnection()
      {
      switch (selectedDataBase)
         {
         case MEMORY:
            {
            memoryDBConnection = new MemoryDBConnection();
            this.customerDAO = new Customer_Mem_DAO(memoryDBConnection);
            this.ordersDAO = new Order_Mem_DAO(memoryDBConnection);
            }
            break;
         case MARIADB:
            {
            myDBConnection = new MariaDBConnection();
            this.customerDAO = new Customer_DB_DAO(myDBConnection.getConnection());
            this.ordersDAO = new Order_DB_DAO(myDBConnection.getConnection());
            }
            break;
         default:
            {
            System.out.println("Database selection not supported.");
            throw new InvalidParameterException("Selector is unspecified: " + selectedDataBase);
            }
         }
      }

   private void closeConnection()
      {
      if (myDBConnection != null)
         {
         myDBConnection.close();
         }
      if (memoryDBConnection != null)
         {
         memoryDBConnection.close();
         }
      }

   public void start()
      {
      openConnection();
      int choice = 0;
      do {
          displayMainMenu();
          choice = scanner.nextInt();
          scanner.nextLine();

          switch (choice) {
              case 1:
                  handleCustomerMenu();
                  break;
              case 2:
                  handleOrderMenu();
                  break;
              case 3:
            	  handleReportsMenu();
                  break;
              case 4:
            	  handleInformationMenu();
                  break;
              case 5:
                  System.out.println("Saindo do Menu.");
                  break;
              default:
                  System.out.println("Opção inválida. Tente novamente.");
                  break;
          }
      } while (choice != 5);
      closeConnection();
      }


private void insertData()
      {
      System.out.println("Create 4 random customers");
      try
         {
         for (int i = 1; i <= 4; i++)
            {
            Customer customer = new Customer();
            customer.setId(i);
            customer.setName("Customer " + i);
            customer.setCity("City " + i);
            customer.setState("State " + i);
            customerDAO.addCustomer(customer);

            // Create 2 random orders for each customer
            for (int j = 1; j <= 2; j++)
               {
               Orders order = new Orders();
               order.setNumber((i - 1) * 2 + j); // Ensure unique order numbers
               order.setCustomerId(i);
               order.setDescription("Order " + j + " for Customer " + i);
               order.setPrice(new BigDecimal(new Random().nextDouble() * 100.0)); // Random
               ordersDAO.addOrder(order);
               }
            }
         }
      catch (SQLException e)
         {
         // TODO Auto-generated catch block
         e.printStackTrace();
         }

      System.out.println("Random customers and orders created successfully!");
      }

   private void requestData()
      {

      System.out.println("Requesting all customers");
      try
         {
         List<Customer> customers = customerDAO.getAllCustomersOrderedByName();
         for (Customer customer : customers)
            {
            System.out.println(customer.getName());
            }
         }
      catch (SQLException e)
         {
         System.err.println("Error retrieving customers: " + e.getMessage());
         }

      System.out.println("Requesting single customer");
      try
         {
         int customerId = 1; // Replace with the desired customer ID
         Customer customer = customerDAO.getCustomerById(customerId);

         if (customer != null)
            {
            System.out.println("Customer Name: " + customer.getName());
            System.out.println("City: " + customer.getCity());
            System.out.println("State: " + customer.getState());
            }
         else
            {
            System.out.println("Customer not found.");
            }
         }
      catch (SQLException e)
         {
         System.err.println("Error retrieving customer: " + e.getMessage());
         }

      System.out.println("Requesting all orders from a customer");
      try
         {
         int customerId = 1; // Replace with the desired customer
                             // ID
         List<Orders> customerOrders = ordersDAO.getOrdersByCustomerId(customerId);

         for (Orders order : customerOrders)
            {
            System.out.println("Order Number: " + order.getNumber());
            System.out.println("Description: " + order.getDescription());
            System.out.println("Price: " + order.getPrice());
            System.out.println();
            }
         }
      catch (SQLException e)
         {
         System.err.println("Error retrieving customer orders: " + e.getMessage());
         }

      System.out.println("Requesting a single order");
      try
         {
         int orderNumber = 1; // Replace with the desired order number
         Orders order = ordersDAO.getOrderByNumber(orderNumber);

         if (order != null)
            {
            System.out.println("Order Number: " + order.getNumber());
            System.out.println("Customer ID: " + order.getCustomerId());
            System.out.println("Description: " + order.getDescription());
            System.out.println("Price: " + order.getPrice());
            }
         else
            {
            System.out.println("Order not found.");
            }
         }
      catch (SQLException e)
         {
         System.err.println("Error retrieving order: " + e.getMessage());
         }
      }

   private void updateData()
      {
      // Single Customer
      try
         {
         int customerId = 1; // Replace with the desired customer ID
         Customer customer = customerDAO.getCustomerById(customerId);

         if (customer != null)
            {
            System.out.println("Customer Name: " + customer.getName());
            System.out.println("City: " + customer.getCity());
            System.out.println("State: " + customer.getState());

            customer.setCity("Limeira");
            customer.setState("SP");
            customerDAO.updateCustomer(customer);
            }
         else
            {
            System.out.println("Customer not found.");
            }
         }
      catch (SQLException e)
         {
         System.err.println("Error retrieving customer: " + e.getMessage());
         }

      }

   private void deleteData()
      {
      try
         {
         int orderNumber = 1; // Replace with the desired order number
         Orders order = ordersDAO.getOrderByNumber(orderNumber);

         if (order != null)
            {
            System.out.println("Order Number: " + order.getNumber());
            System.out.println("Customer ID: " + order.getCustomerId());
            System.out.println("Description: " + order.getDescription());
            System.out.println("Price: " + order.getPrice());

            ordersDAO.deleteOrder(order.getNumber());
            }
         else
            {
            System.out.println("Order not found.");
            }
         }
      catch (SQLException e)
         {
         System.err.println("Error retrieving order: " + e.getMessage());
         }
      }

   private void deleteAllData()
      {
      System.out.println("Deleting all data");

      try
         {
         ordersDAO.deleteAllOrders();
         customerDAO.deleteAllCustomers();
         }
      catch (SQLException e)
         {
         System.err.println("Error retrieving order: " + e.getMessage());
         }
      }
   
   public void displayMainMenu() {
	    System.out.println("Menu Principal:");
	    System.out.println("1. Gerenciar Customers");
	    System.out.println("2. Gerenciar Orders");
	    System.out.println("3. Gerenciar Relatórios");
	    System.out.println("4. Informações");
	    System.out.println("5. Sair");
	    System.out.print("Escolha uma opção: ");
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
		        	    System.out.print("Informe o ID do cliente: ");
		        	    newCustomer.setId(scanner.nextInt());
		        	    scanner.nextLine();
		        	    System.out.print("Informe o nome do cliente: ");
		        	    newCustomer.setName(scanner.nextLine());
		        	    System.out.print("Informe a cidade do cliente: ");
		        	    newCustomer.setCity(scanner.nextLine());
		        	    System.out.print("Informe o estado do cliente: ");
		        	    newCustomer.setState(scanner.nextLine());

		        	    try {
		        	        customerDAO.addCustomer(newCustomer);
		        	        System.out.println("Cliente adicionado com sucesso!");
		        	    } catch (SQLException e) {
		        	        System.err.println("Erro ao adicionar cliente: " + e.getMessage());
		        	    }
		        	    break;
	        	case 2:
	        	    System.out.print("Informe o ID do cliente: ");
	        	    int customerId = scanner.nextInt();
	        	    scanner.nextLine();

	        	    try {
	        	        customerDAO.getCustomerById(customerId);
	        	    } catch (SQLException e) {
	        	        System.err.println("Erro ao recuperar cliente: " + e.getMessage());
	        	    }
	        	    break;
	        	case 3:
	        	    System.out.print("Informe o ID do cliente que deseja excluir: ");
	        	    int customerIdDelete = scanner.nextInt();

	        	    try {
	        	        customerDAO.deleteCustomer(customerIdDelete);
	        	        System.out.println("Cliente excluído com sucesso!");
	        	    } catch (SQLException e) {
	        	        System.err.println("Erro ao excluir cliente: " + e.getMessage());
	        	    }
	        	    break;
	        	//case 4:
	        	//	getCustomerById(customerName);
	        	//  break;
	            case 5:
	                System.out.println("Voltando ao Menu Principal.");
	                break;
	            default:
	                System.out.println("Opção inválida. Tente novamente.");
	                break;
	        }
	    } while (customerChoice != 5);
	}

	public void displayCustomerMenu() {
	    System.out.println("Menu Customer:");
	    System.out.println("1. addCustomer");
	    System.out.println("2. getCustomerById");
	    System.out.println("3. getCustomerByName");
	    System.out.println("4. deleteCustomer");
	    System.out.println("5. Voltar ao Menu Principal");
	    System.out.print("Escolha uma opção: ");
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
	                System.out.println("Voltando ao Menu Principal.");
	                break;
	            default:
	                System.out.println("Opção inválida. Tente novamente.");
	                break;
	        }
	    } while (orderChoice != 4);
	}

	public void displayOrderMenu() {
	    System.out.println("Menu Order:");
	    System.out.println("1. Inserir um Order");
	    System.out.println("2. Consultar Orders");
	    System.out.println("3. Remover um Order");
	    System.out.println("4. Voltar ao Menu Principal");
	    System.out.print("Escolha uma opção: ");
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
	            case 2:
		            try {
		                customerDAO.getAllCustomersOrderedByName();
		            } catch (SQLException e) {
		                System.err.println("Erro ao recuperar clientes: " + e.getMessage());
		            }
		            break;
	            //case 3:
	            //    displayOrdersOrderedByNumber();
	            //    break;
	            //case 4:
	            //    displayOrdersByCustomerOrderedByName();
	            //    break;
	            case 5:
	                System.out.println("Voltando ao Menu Principal.");
	                break;
	            default:
	                System.out.println("Opção inválida. Tente novamente.");
	                break;
	        }
	    } while (reportChoice != 5);
	}

	public void displayReportsMenu() {
	    System.out.println("Menu Relatórios:");
	    System.out.println("1. Clientes ordenados por identificador");
	    System.out.println("2. Clientes ordenados por nome");
	    System.out.println("3. Pedidos ordenados por número");
	    System.out.println("4. Pedidos dos clientes ordenados por nome");
	    System.out.println("5. Voltar ao Menu Principal");
	    System.out.print("Escolha uma opção: ");
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
	                System.out.println("Voltando ao Menu Principal.");
	                break;
	            default:
	                System.out.println("Opção inválida. Tente novamente.");
	                break;
	        }
	    } while (infoChoice != 3);
	}

	public void displayInformationMenu() {
	    System.out.println("Menu Informações:");
	    System.out.println("1. Ajuda");
	    System.out.println("2. Sobre");
	    System.out.println("3. Voltar ao Menu Principal");
	    System.out.print("Escolha uma opção: ");
	}

	public void displayHelp() {
	    System.out.println("Texto explicativo do programa...");
	}

	public void displayAbout() {
	    System.out.println("Versão do programa...");
	    System.out.println("Créditos de autoria...");
	}

   
   }
