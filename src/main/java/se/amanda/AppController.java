package se.amanda;

import java.sql.Date;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class AppController {

    private static Scanner scanner;
    private boolean running;
    private WorkRoleDAO workRoleDAO;
    private EmployeeDAO employeeDAO;

    public AppController() {
        this.workRoleDAO = new WorkRoleDAOImpl();           //Instans av workRoleImp-klassen
        this.running = true;
        this.employeeDAO = new EmployeeDAOImpl();           //Instans av employeeImp-klassen
    }

    public void start() {
        System.out.println("Welcome to your work app!");
        getUserInput();
    }

    public void printMenu() {
        System.out.println("What would you like to do?" +
                "\n------------------------------------" +
                "\n1. Create a new work role \n2. Delete a work role \n3. Show all work roles" +
                "\n4. Show one work role \n5. Update a work role \n6. Sign in and see your work role" +
                "\n7. Exit" + "\n------------------------------------");
    }

    public static Scanner getScanner() {
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }
        return scanner;
    }

    public void getUserInput() {
        while (running) {
            printMenu();

            String userInput = getScanner().nextLine();
            switch (userInput) {
                case "1" -> createNewWorkRole();
                case "2" -> deleteWorkRole();
                case "3" -> showAllWorkRoles();
                case "4" -> showOneWorkRole();
                case "5" -> updateWorkRole();
                case "6" -> signInAndSeeWorkRole();
                case "7" -> quit();
                default -> System.out.println("Incorrect input");
            }
        }
    }

    public void createNewWorkRole() {
        System.out.println("Enter title:");
        String title = getScanner().nextLine();
        System.out.println("Enter description: ");
        String description = getScanner().nextLine();

        double salary = getValidSalary();                   //Skapat metoder för att hämta salary, creationdate (även role-id om det skulle behövas)
        String creationDate = getValidCreationDate();

        WorkRole workRole = new WorkRole(title, description, salary, Date.valueOf(creationDate)); //Skapar en workRole med de nya värdena

        try {
            workRoleDAO.insertWorkRole(workRole);           //Kallar på metoden för att skapa en ny workRole i databasen
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteWorkRole() {
        int roleId = getValidRoleId();      //hämtar role id genom separat metod

        try {
            workRoleDAO.deleteWorkRole(roleId);     //kallar på metod för att radera en work role och skickar med det hämtade idt
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void showAllWorkRoles() {
        try {
            List<WorkRole> workRoleList = workRoleDAO.selectAllWorkRoles();     //Skapar en lista för att samla resultatet från selectAllWorkRoles

            for (WorkRole workRole : workRoleList) {                        //Loopar igenom resultat och skriver ut
                System.out.println(workRole);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showOneWorkRole() {
        int roleId = getValidRoleId();

        try {
            workRoleDAO.selectWorkRole(roleId);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateWorkRole() {
        WorkRole existingWorkRole = null;       //deklarerar en workRole

        while (existingWorkRole == null) {        //Kontrollerar om en roll med angivet id finns i databasen. Loop körs tills dess
            int roleId = getValidRoleId();

            try {
                existingWorkRole = workRoleDAO.selectWorkRole(roleId);          //Lagrar match i existingWorkRole

            } catch (SQLException e) {
                e.printStackTrace();
                return;
            }
        }
        System.out.println("To update work role, please follow instructions below.");
        System.out.println("Enter new title: ");
        String title = getScanner().nextLine();
        System.out.println("Enter new description: ");
        String desc = getScanner().nextLine();

        double salary = getValidSalary();
        String creationDate = getValidCreationDate();

        WorkRole workRole = new WorkRole(existingWorkRole.getRoleId(), title, desc, salary, Date.valueOf(creationDate));

        try {
            workRoleDAO.updateWorkRole(workRole);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void signInAndSeeWorkRole() {
        System.out.println("Enter your email:");
        String email = getScanner().nextLine();
        System.out.println("Enter password:");
        String password = getScanner().nextLine();

        try {
            System.out.println(employeeDAO.getEmployeeDetailsWithLeftJoin(email, password));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void quit() {
        System.out.println("Thank you for using this work role App");
        closeScanner();
        running = false;
    }

    private void closeScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }

    private int getValidRoleId() {
        while (true) {     //Använder en loop för att ge användaren fler chanser att skriva in role id om den skriver fel
            try {
                System.out.println("Enter role-id");
                int roleId = getScanner().nextInt();
                getScanner().nextLine();                //rensar scannern efter nextInt
                return roleId;      //Returnerar role id när ett giltigt värde skrivits in
            } catch (InputMismatchException e) {
                System.out.println("Incorrect input. Please try again.");
                getScanner().nextLine(); //rensar scannern från felaktiga värden
            }
        }
    }

    private double getValidSalary() {
        while (true) {          //Samma princip för salary
            try {
                System.out.println("Enter salary:");
                double salary = getScanner().nextDouble();
                getScanner().nextLine();
                return salary;
            } catch (InputMismatchException e) {
                System.out.println("Please enter only numbers for salary");
                getScanner().nextLine();
            }
        }
    }

    private String getValidCreationDate() {
        while (true) {            //Samma princip för creation date
            try {
                System.out.println("Enter creation date(yyyy-mm-dd):");
                String creationDate = getScanner().nextLine();
                Date.valueOf(creationDate);

                return creationDate;
            } catch (IllegalArgumentException e) {
                System.out.println("Please try again");
            }
        }
    }

}
