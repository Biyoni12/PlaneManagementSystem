// PlaneManagement.java
import java.util.Scanner;

public class PlaneManagement {
    private static final int[][] seats = new int[4][14];
    private static final int[] rowSizes = {14, 12, 12, 14};
    private static final Ticket[] tickets = new Ticket[52];
    private static int numTickets = 0;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to the Plane Management application");

        int option;
        do {
            printMenu();
            option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            option = switch (option) {
                case 1 -> {
                    buy_seat();
                    yield option;
                }
                case 2 -> {
                    cancel_seat();
                    yield option;
                }
                case 3 -> {
                    find_first_available();
                    yield option;
                }
                case 4 -> {
                    show_seating_plan();
                    yield option;
                }
                case 5 -> {
                    print_tickets_info();
                    yield option;
                }
                case 6 -> {
                    search_ticket();
                    yield option;
                }
                case 0 -> {
                    System.out.println("Goodbye!");
                    yield option;
                }
                default -> {
                    System.out.println("Invalid option");
                    yield option;
                }
            };
        } while (option != 0);
    }

    private static void printMenu() {
        System.out.println("*************************************************");
        System.out.println("MENU OPTIONS");
        System.out.println("\t1) Buy a seat");
        System.out.println("\t2) Cancel a seat");
        System.out.println("\t3) Find first available seat");
        System.out.println("\t4) Show seating plan");
        System.out.println("\t5) Print tickets information and total sales");
        System.out.println("\t6) Search ticket");
        System.out.println("\t0) Quit");
        System.out.println("*************************************************");
        System.out.println("Please select an option:");
    }

    private static void buy_seat() {
        System.out.print("Enter row letter (A-D): ");
        String rowStr = scanner.nextLine().toUpperCase();
        if (rowStr.length() != 1) {
            System.out.println("Invalid row");
            return;
        }
        char rowChar = rowStr.charAt(0);
        int row = rowChar - 'A';
        if (row < 0 || row > 3) {
            System.out.println("Invalid row");
            return;
        }

        System.out.print("Enter seat number (1-" + rowSizes[row] + "): ");
        int seatNum;
        try {
            seatNum = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid seat number");
            return;
        }
        if (seatNum < 1 || seatNum > rowSizes[row]) {
            System.out.println("Invalid seat");
            return;
        }

        if (seats[row][seatNum - 1] == 1) {
            System.out.println("Seat already sold");
            return;
        }

        seats[row][seatNum - 1] = 1;

        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter surname: ");
        String surname = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        Person person = new Person(name, surname, email);
        int price = getPrice(seatNum);
        Ticket ticket = new Ticket(rowChar, seatNum, price, person);
        tickets[numTickets++] = ticket;
        ticket.save();

        System.out.println("Seat bought successfully");
    }

    private static void cancel_seat() {
        System.out.print("Enter row letter (A-D): ");
        String rowStr = scanner.nextLine().toUpperCase();
        if (rowStr.length() != 1) {
            System.out.println("Invalid row");
            return;
        }
        char rowChar = rowStr.charAt(0);
        int row = rowChar - 'A';
        if (row < 0 || row > 3) {
            System.out.println("Invalid row");
            return;
        }

        System.out.print("Enter seat number (1-" + rowSizes[row] + "): ");
        int seatNum;
        try {
            seatNum = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid seat number");
            return;
        }
        if (seatNum < 1 || seatNum > rowSizes[row]) {
            System.out.println("Invalid seat");
            return;
        }

        if (seats[row][seatNum - 1] == 0) {
            System.out.println("Seat already available");
            return;
        }

        seats[row][seatNum - 1] = 0;

        for (int i = 0; i < numTickets; i++) {
            if (tickets[i].getRow() == rowChar && tickets[i].getSeat() == seatNum) {
                for (int j = i; j < numTickets - 1; j++) {
                    tickets[j] = tickets[j + 1];
                }
                numTickets--;
                System.out.println("Seat cancelled successfully");
                return;
            }
        }
        System.out.println("No ticket found for this seat"); // Should not happen if consistent
    }

    private static void find_first_available() {
        for (int r = 0; r < 4; r++) {
            for (int s = 0; s < rowSizes[r]; s++) {
                if (seats[r][s] == 0) {
                    char rowChar = (char) ('A' + r);
                    System.out.println("First available seat: " + rowChar + (s + 1));
                    return;
                }
            }
        }
        System.out.println("No available seats");
    }

    private static void show_seating_plan() {
        for (int r = 0; r < 4; r++) {
            if (r == 1 || r == 2) {
                System.out.print(" ");
            }
            for (int s = 0; s < rowSizes[r]; s++) {
                System.out.print(seats[r][s] == 0 ? "O" : "X");
            }
            System.out.println();
        }
    }

    private static void print_tickets_info() {
        int total = 0;
        for (int i = 0; i < numTickets; i++) {
            tickets[i].print();
            total += tickets[i].getPrice();
        }
        System.out.println("Total sales: £" + total);
    }

    private static void search_ticket() {
        System.out.print("Enter row letter (A-D): ");
        String rowStr = scanner.nextLine().toUpperCase();
        if (rowStr.length() != 1) {
            System.out.println("Invalid row");
            return;
        }
        char rowChar = rowStr.charAt(0);
        int row = rowChar - 'A';
        if (row < 0 || row > 3) {
            System.out.println("Invalid row");
            return;
        }

        System.out.print("Enter seat number (1-" + rowSizes[row] + "): ");
        int seatNum;
        try {
            seatNum = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid seat number");
            return;
        }
        if (seatNum < 1 || seatNum > rowSizes[row]) {
            System.out.println("Invalid seat");
            return;
        }

        for (int i = 0; i < numTickets; i++) {
            if (tickets[i].getRow() == rowChar && tickets[i].getSeat() == seatNum) {
                tickets[i].print();
                return;
            }
        }
        System.out.println("This seat is available");
    }

    private static int getPrice(int seat) {
        if (seat >= 1 && seat <= 5) {
            return 200;
        } else if (seat <= 9) {
            return 150;
        } else {
            return 180;
        }
    }
}
