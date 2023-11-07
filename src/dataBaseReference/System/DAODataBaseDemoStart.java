package dataBaseReference.System;

public final class DAODataBaseDemoStart
   {
   public static void main(String[] args)
      {
      System.out.println("Database demonstration running now...");
      System.out.println("Welcome to the Customer and Order Database Management System!");

      try
         {
         (new Controller(DataBaseType.MEMORY)).start();
         }
      catch (Exception myException)
         {
         System.out.println("Exception launched. Programa aborted.");
         System.out.println(myException.getMessage());
         myException.printStackTrace();
         System.exit(1);
         }
      System.out.println("Thank you for using the Customer and Order Database Management System!");
      System.out.println("Database demonstration stopping now...");
      System.exit(0);
      }
   }
