// Ticket.java
import java.io.FileWriter;
import java.io.IOException;

public class Ticket {
    private char row;
    private int seat;
    private int price;
    private Person person;

    public Ticket(char row, int seat, int price, Person person) {
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    public char getRow() {
        return row;
    }

    public void setRow(char row) {
        this.row = row;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void print() {
        System.out.println("Ticket: " + row + seat + ", Price: £" + price);
        person.print();
    }

    public void save() {
        String filename = row + "" + seat + ".txt";
        try (FileWriter fw = new FileWriter(filename)) {
            fw.write("Row: " + row + "\n");
            fw.write("Seat: " + seat + "\n");
            fw.write("Price: £" + price + "\n");
            fw.write("Name: " + person.getName() + "\n");
            fw.write("Surname: " + person.getSurname() + "\n");
            fw.write("Email: " + person.getEmail() + "\n");
        } catch (IOException e) {
            System.out.println("Error saving ticket: " + e.getMessage());
        }
    }
}
