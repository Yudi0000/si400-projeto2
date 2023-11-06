package dataBaseReference.DAO;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import dataBaseReference.DTO.Customer;
import dataBaseReference.DTO.Orders;
import dataBaseReference.RDBMS.MemoryDBConnection;


public class Order_Mem_DAO extends AbstractOrderDAO {
    private MemoryDBConnection databaseRef;
    private AbstractCustomerDAO customerDAO; // Adicione este campo
    private AbstractOrderDAO orderDAO; // Adicione este campo

    public Order_Mem_DAO(MemoryDBConnection databaseRef, AbstractCustomerDAO customerDAO, AbstractOrderDAO orderDAO) {
        super();
        this.databaseRef = databaseRef;
        this.customerDAO = customerDAO;
        this.orderDAO = orderDAO;
    }

   @Override
   public List<Orders> getOrdersByCustomerId(int customerId) throws SQLException
      {
      List<Orders> orders = new ArrayList<>();
      Iterator<Orders> iterator = databaseRef.getOrderList().iterator();

      while (iterator.hasNext())
         {
         Orders buffer = iterator.next();
         if (buffer.getCustomerId() == customerId)
            {
            orders.add(buffer);
            }
         }
      return orders;
      }

   @Override
   public Orders getOrderByNumber(int orderNumber) throws SQLException
      {
      Orders order = null;
      Iterator<Orders> iterator = databaseRef.getOrderList().iterator();

      while (iterator.hasNext())
         {
         Orders buffer = iterator.next();
         if (buffer.getNumber() == orderNumber)
            {
            order = buffer;
            }
         }
      return order;
      }

   @Override
   public void addOrder(Orders order) throws SQLException
      {
      databaseRef.getOrderList().add(order);
      }

   @Override
   public void updateOrder(Orders order) throws SQLException
      {
      ArrayList<Orders> orders = databaseRef.getOrderList();

      for (int index = 0; index < orders.size(); index++)
         {
         if (orders.get(index).getNumber() == order.getNumber())
            {
            orders.set(index, order);
            break;
            }
         }
      }

   @Override
   public void deleteOrder(int orderNumber) throws SQLException
      {
      ArrayList<Orders> orders = databaseRef.getOrderList();

      for (int index = 0; index < orders.size(); index++)
         {
         if (orders.get(index).getNumber() == orderNumber)
            {
            orders.remove(index);
            break;
            }
         }
      }

   @Override
   public void deleteAllOrders() throws SQLException
      {
      databaseRef.getOrderList().clear();
      }
   
   @Override
   public List<Orders> getAllOrdersOrderedByNumber() throws SQLException {
       List<Orders> orders = new ArrayList<>();

       List<Orders> orderList = databaseRef.getOrderList();

       orderList.sort(Comparator.comparingInt(Orders::getNumber));

       orders.addAll(orderList);

       return orders;
   }
   
   public void deleteOrderByNumber(int orderNumber) throws SQLException {
	    List<Orders> orders = databaseRef.getOrderList();
	    Orders orderToRemove = null;

	    for (Orders order : orders) {
	        if (order.getNumber() == orderNumber) {
	            orderToRemove = order;
	            break;
	        }
	    }

	    if (orderToRemove != null) {
	        orders.remove(orderToRemove);
	    }
	}

   public List<Orders> generateOrdersReportByCustomerName() throws SQLException {
	    List<Orders> allOrders = getAllOrdersOrderedByNumber();
	    List<Customer> allCustomers = customerDAO.getAllCustomersOrderedById();

	    Map<Integer, Customer> customerMap = new HashMap<>();
	    for (Customer customer : allCustomers) {
	        customerMap.put(customer.getId(), customer);
	    }

	    Map<String, List<Orders>> ordersByCustomerName = new TreeMap<>();
	    for (Orders order : allOrders) {
	        int customerId = order.getCustomerId();
	        Customer customer = customerMap.get(customerId);
	        if (customer != null) {
	            String customerName = customer.getName();

	            if (!ordersByCustomerName.containsKey(customerName)) {
	                ordersByCustomerName.put(customerName, new ArrayList<>());
	            }

	            ordersByCustomerName.get(customerName).add(order);
	        }
	    }

	    List<Orders> report = new ArrayList<>();
	    for (Map.Entry<String, List<Orders>> entry : ordersByCustomerName.entrySet()) {
	        String customerName = entry.getKey();
	        List<Orders> customerOrders = entry.getValue();

	        Orders customerHeader = new Orders();
	        customerHeader.setCustomerName(customerName);
	        report.add(customerHeader);

	        report.addAll(customerOrders);

	        BigDecimal totalValue = customerOrders.stream()
	                .map(Orders::getPrice)
	                .reduce(BigDecimal.ZERO, BigDecimal::add);
	        Orders totalValueOrder = new Orders();
	        totalValueOrder.setDescription("Valor Total dos Pedidos");
	        totalValueOrder.setPrice(totalValue);
	        report.add(totalValueOrder);
	    }

	    return report;
	}




   }
