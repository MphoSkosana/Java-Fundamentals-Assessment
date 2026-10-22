import java.util.Scanner;

/**
 * Furniture shop sales program.
 * Calculates salaries, turnover, salary expenditure and the best salesperson.
 */
public class SalesApp {

    private static final double BASIC_WAGE = 7500.0;
    private static final double COMMISSION_RATE = 0.10;
    private static final int NUM_PERSONS = 4;
    private static final int NUM_WEEKS = 4;

    private static String[] arPersons = new String[NUM_PERSONS];
    private static double[][] arSales = new double[NUM_PERSONS][NUM_WEEKS];

    private static final Scanner input = new Scanner(System.in);

    static void main(String[] args) {
        initialize();
        String again;
        do {
            showMenu();
            int option = readOption();
            switch (option) {
                case 1:
                    display();
                    break;
                case 2:
                    System.out.print("Search for salesperson: ");
                    String name = input.nextLine().trim();
                    int pos = findSalesPerson(name);
                    if (pos >= 0) {
                        System.out.println(arPersons[pos] + " is found at " + pos);
                    } else {
                        System.out.println(name + " was not found.");
                    }
                    break;
                case 3:
                    calcTotalMonthlySales();
                    break;
                case 4:
                    calcMonthlySalary();
                    break;
                case 5:
                    getBestSalesPerson();
                    break;
                case 6:
                    System.out.println("Monthly Turnover " + calcMonthlyTurnOver());
                    break;
                case 7:
                    System.out.println("Salary Expenditure " + calcSalaryExpenditure());
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1 - 7.");
            }
            System.out.print("Do you want to continue? (Y/N)");
            again = input.nextLine().trim();
        } while (again.equalsIgnoreCase("Y"));

        System.out.println("Goodbye!");
        input.close();
    }

    /** Fills the arrays with 4 salespeople and 4 weekly sales each. */
    public static void initialize() {
        arPersons = new String[] {"Thabo", "Loraine", "Zama", "Peter"};
        arSales = new double[][] {
                {17550.0, 14500.0, 11000.0, 12500.0},
                {15000.0, 12000.0, 12500.0, 16800.0},
                {15620.0, 18100.0, 21500.0, 17630.0},
                {15755.0, 13900.0, 12950.0, 19200.0}
        };
    }

    /** Returns the position of the salesperson, or -1 if not found. */
    public static int findSalesPerson(String name) {
        for (int i = 0; i < arPersons.length; i++) {
            if (arPersons[i].equalsIgnoreCase(name)) {
                return i;
            }
        }
        return -1;
    }

    /** Asks for a name, then calculates, displays and returns that person's total monthly sales. */
    public static double calcTotalMonthlySales() {
        int pos = askForSalesPerson();
        if (pos < 0) {
            return 0;
        }
        double total = totalSalesAt(pos);
        System.out.println("Total Sales for " + arPersons[pos] + ": " + total);
        return total;
    }

    /** Asks for a name, then calculates, displays and returns that person's monthly salary. */
    public static double calcMonthlySalary() {
        int pos = askForSalesPerson();
        if (pos < 0) {
            return 0;
        }
        double salary = salaryAt(pos);
        System.out.println("Monthly Salary for " + arPersons[pos] + ": " + salary);
        return salary;
    }

    /** Total of all sales made by all salespeople. */
    public static double calcMonthlyTurnOver() {
        double turnover = 0;
        for (int i = 0; i < arPersons.length; i++) {
            turnover += totalSalesAt(i);
        }
        return turnover;
    }

    /** Total of all salaries paid to all salespeople. */
    public static double calcSalaryExpenditure() {
        double expenditure = 0;
        for (int i = 0; i < arPersons.length; i++) {
            expenditure += salaryAt(i);
        }
        return expenditure;
    }

    /** Displays the name and monthly sales of the salesperson with the highest sales. */
    public static void getBestSalesPerson() {
        int best = 0;
        for (int i = 1; i < arPersons.length; i++) {
            if (totalSalesAt(i) > totalSalesAt(best)) {
                best = i;
            }
        }
        System.out.println("Best Salesperson");
        System.out.println(arPersons[best] + "\t" + totalSalesAt(best));
    }

    /** Displays every salesperson with weekly sales and total monthly sales. */
    public static void display() {
        System.out.printf("%-10s%-10s%-10s%-10s%-10s%s%n",
                "Name", "Week 1", "Week 2", "Week 3", "Week 4", "Total Sales");
        System.out.println("-".repeat(75));
        for (int i = 0; i < arPersons.length; i++) {
            System.out.printf("%-10s", arPersons[i]);

            for (int w = 0; w < NUM_WEEKS; w++) {
                System.out.printf("%-10s", arSales[i][w]);
            }
            System.out.println(totalSalesAt(i));
        }
    }

    // ---------- helper methods ----------

    private static void showMenu() {
        System.out.println();
        System.out.println("Please select a menu item below:");
        System.out.println("1 - See All Salespeople");
        System.out.println("2 - Find Salesperson");
        System.out.println("3 - Total Monthly Sales");
        System.out.println("4 - Monthly Salary");
        System.out.println("5 - Best Salesperson");
        System.out.println("6 - Monthly Turnover");
        System.out.println("7 - Total Salary Expenditure");
        System.out.print("Your Option: ");
    }

    private static int readOption() {
        try {
            return Integer.parseInt(input.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /** Prompts for a name and returns the position, printing a message if not found. */
    private static int askForSalesPerson() {
        System.out.print("Search for salesperson: ");
        String name = input.nextLine().trim();
        int pos = findSalesPerson(name);
        if (pos < 0) {
            System.out.println(name + " was not found.");
        }
        return pos;
    }

    private static double totalSalesAt(int position) {
        double total = 0;
        for (double weekly : arSales[position]) {
            total += weekly;
        }
        return total;
    }

    private static double salaryAt(int position) {
        return BASIC_WAGE + COMMISSION_RATE * totalSalesAt(position);
    }
}