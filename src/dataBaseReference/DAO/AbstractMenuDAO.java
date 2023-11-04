package dataBaseReference.DAO;

import java.sql.SQLException;

public abstract class AbstractMenuDAO {
	abstract public void displayMainMenu () throws SQLException;
	abstract public void handleCustomerMenu () throws SQLException;
	abstract public void displayCustomerMenu () throws SQLException;
	abstract public void handleOrderMenu () throws SQLException;
	abstract public void displayOrderMenu () throws SQLException;
	abstract public void handleReportsMenu () throws SQLException;
	abstract public void displayReportsMenu () throws SQLException;
	abstract public void handleInformationMenu () throws SQLException;
}