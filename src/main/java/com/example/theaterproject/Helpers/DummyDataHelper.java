package com.example.theaterproject.Helpers;

import com.example.theaterproject.Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for generating dummy data for testing and development purposes.
 * Provides methods to create sample instances of various model classes
 * such as Movies, Accounts, Screenings, Showrooms, and Tickets.
 */
public class DummyDataHelper {

    /**
     * Creates a list of dummy Movie objects with realistic data.
     *
     * @return a list of Movie objects
     */
    public static List<Movie> getDummyMovies() {
        List<Movie> movies = new ArrayList<>();
        
        movies.add(new Movie("Action", "The Matrix", "The Wachowskis", 1999,
                "A computer programmer discovers that reality is a simulation created by machines.",
                136));
        
        movies.add(new Movie("Drama", "The Shawshank Redemption", "Frank Darabont", 1994,
                "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.",
                142));
        
        movies.add(new Movie("Science Fiction", "Inception", "Christopher Nolan", 2010,
                "A skilled thief who steals corporate secrets through the use of dream-sharing technology.",
                148));
        
        movies.add(new Movie("Comedy", "Pulp Fiction", "Quentin Tarantino", 1994,
                "The lives of two mob hitmen, a boxer, a gangster and his wife intertwine in four tales of violence and redemption.",
                154));
        
        movies.add(new Movie("Horror", "The Shining", "Stanley Kubrick", 1980,
                "A family isolated by heavy snow becomes the focus of sinister forces when they take over a remote hotel for the winter.",
                146));
        
        movies.add(new Movie("Animation", "Spirited Away", "Hayao Miyazaki", 2001,
                "During her family's move, a sullen girl wanders into a world ruled by gods, witches, and spirits.",
                125));
        
        movies.add(new Movie("Romance", "Titanic", "James Cameron", 1997,
                "A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.",
                194));
        
        movies.add(new Movie("Thriller", "The Dark Knight", "Christopher Nolan", 2008,
                "When the menace known as the Joker wreaks havoc, Batman must accept one of the greatest tests.",
                152));
        
        return movies;
    }

    /**
     * Creates a list of dummy Account objects.
     *
     * @return a list of Account objects
     */
    public static List<Account> getDummyAccounts() {
        List<Account> accounts = new ArrayList<>();
        
        accounts.add(new Account("admin_user", "password123"));
        accounts.add(new Account("theater_manager", "secure_pass"));
        accounts.add(new Account("ticket_seller", "pass456"));
        accounts.add(new Account("database_admin", "db_password"));
        
        return accounts;
    }

    /**
     * Creates a list of dummy Manager objects.
     *
     * @return a list of Manager objects
     */
    public static List<Manager> getDummyManagers() {
        List<Manager> managers = new ArrayList<>();
        
        managers.add(new Manager("john_manager", "manager123"));
        managers.add(new Manager("sarah_admin", "admin_pass"));
        managers.add(new Manager("theater_boss", "boss_password"));
        
        return managers;
    }

    /**
     * Creates a list of dummy Client objects with email addresses.
     *
     * @return a list of Client objects
     */
    public static List<Client> getDummyClients() {
        List<Client> clients = new ArrayList<>();
        
        clients.add(new Client("john_doe", "client123", "john.doe@email.com"));
        clients.add(new Client("jane_smith", "secure_pass", "jane.smith@email.com"));
        clients.add(new Client("michael_j", "password789", "michael.j@email.com"));
        clients.add(new Client("emily_brown", "emily_pass", "emily.brown@email.com"));
        clients.add(new Client("david_wilson", "david123", "david.wilson@email.com"));
        
        return clients;
    }

    /**
     * Creates a list of dummy Screening objects.
     * Requires dummy movies to be created first.
     *
     * @return a list of Screening objects
     */
    public static List<Screening> getDummyScreenings() {
        List<Screening> screenings = new ArrayList<>();
        List<Movie> movies = getDummyMovies();
        
        screenings.add(new Screening(movies.get(0), 150, 12.50, LocalDate.now().plusDays(1)));
        screenings.add(new Screening(movies.get(1), 200, 11.00, LocalDate.now().plusDays(2)));
        screenings.add(new Screening(movies.get(2), 180, 13.00, LocalDate.now().plusDays(3)));
        screenings.add(new Screening(movies.get(3), 160, 10.50, LocalDate.now().plusDays(4)));
        screenings.add(new Screening(movies.get(4), 140, 12.00, LocalDate.now().plusDays(5)));
        screenings.add(new Screening(movies.get(5), 170, 11.50, LocalDate.now().plusDays(6)));
        
        return screenings;
    }

    /**
     * Creates a list of dummy Showroom objects with screening capability.
     * Includes sample screenings for each showroom.
     *
     * @return a list of Showroom objects
     */
    public static List<Showroom> getDummyShowrooms() {
        List<Showroom> showrooms = new ArrayList<>();
        List<Screening> allScreenings = getDummyScreenings();
        
        // Showroom 1 - with first 3 screenings
        ObservableList<Screening> screenings1 = FXCollections.observableArrayList(
                allScreenings.get(0),
                allScreenings.get(1),
                allScreenings.get(2)
        );
        showrooms.add(new Showroom("Theater A", 250, screenings1));
        
        // Showroom 2 - with next 2 screenings
        ObservableList<Screening> screenings2 = FXCollections.observableArrayList(
                allScreenings.get(3),
                allScreenings.get(4)
        );
        showrooms.add(new Showroom("Theater B", 200, screenings2));
        
        // Showroom 3 - with one screening
        ObservableList<Screening> screenings3 = FXCollections.observableArrayList(
                allScreenings.get(5)
        );
        showrooms.add(new Showroom("Theater C (VIP)", 100, screenings3));
        
        // Showroom 4 - empty showroom
        ObservableList<Screening> screenings4 = FXCollections.observableArrayList();
        showrooms.add(new Showroom("Theater D", 300, screenings4));
        
        return showrooms;
    }

    /**
     * Creates a list of dummy Ticket objects.
     * Demonstrates tickets purchased by different clients for various screenings.
     *
     * @return a list of Ticket objects
     */
    public static List<Ticket> getDummyTickets() {
        List<Ticket> tickets = new ArrayList<>();
        List<Client> clients = getDummyClients();
        List<Screening> screenings = getDummyScreenings();
        List<Movie> movies = getDummyMovies();
        
        // Client 1 buys multiple tickets
        tickets.add(new Ticket(movies.get(0), screenings.get(0), 12.50, clients.get(0)));
        tickets.add(new Ticket(movies.get(1), screenings.get(1), 11.00, clients.get(0)));
        
        // Client 2 buys a ticket
        tickets.add(new Ticket(movies.get(2), screenings.get(2), 13.00, clients.get(1)));
        
        // Client 3 buys multiple tickets
        tickets.add(new Ticket(movies.get(3), screenings.get(3), 10.50, clients.get(2)));
        tickets.add(new Ticket(movies.get(4), screenings.get(4), 12.00, clients.get(2)));
        tickets.add(new Ticket(movies.get(5), screenings.get(5), 11.50, clients.get(2)));
        
        // Client 4 buys a ticket
        tickets.add(new Ticket(movies.get(0), screenings.get(0), 12.50, clients.get(3)));
        
        // Client 5 buys a ticket
        tickets.add(new Ticket(movies.get(1), screenings.get(1), 11.00, clients.get(4)));
        
        return tickets;
    }

    /**
     * Returns a single dummy Movie for quick testing.
     *
     * @return a Movie object
     */
    public static Movie getSingleDummyMovie() {
        return new Movie("Action", "The Matrix", "The Wachowskis", 1999,
                "A computer programmer discovers that reality is a simulation created by machines.",
                136);
    }

    /**
     * Returns a single dummy Manager for quick testing.
     *
     * @return a Manager object
     */
    public static Manager getSingleDummyManager() {
        return new Manager("test_manager", "testpass123");
    }

    /**
     * Returns a single dummy Client for quick testing.
     *
     * @return a Client object
     */
    public static Client getSingleDummyClient() {
        return new Client("test_client", "testpass123", "test@email.com");
    }

    /**
     * Returns a single dummy Showroom for quick testing.
     *
     * @return a Showroom object
     */
    public static Showroom getSingleDummyShowroom() {
        ObservableList<Screening> screenings = FXCollections.observableArrayList();
        return new Showroom("Test Theater", 150, screenings);
    }
}
