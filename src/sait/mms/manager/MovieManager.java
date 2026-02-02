package sait.mms.manager;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import sait.mms.problemdomain.Movie;


public class MovieManager {
    private ArrayList<Movie> movies;
    private Scanner scanner;
    private static final String FILE_PATH = "movies.txt";


    // creation of movie list
    public MovieManager() {
        movies = new ArrayList<>();
        scanner = new Scanner(System.in);
        loadMovieList();
    }


    // this is to grab movies from the already existing file

    public void loadMovieList() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    int duration = Integer.parseInt(parts[0].trim());
                    String title = parts[1].trim();
                    int year = Integer.parseInt(parts[2].trim());
                    movies.add(new Movie(duration, title, year));
                }
            }
            System.out.println("Movies loaded successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + FILE_PATH);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing number in file: " + e.getMessage());
        }
    }


      // Displays the main menu and handles user options.

    public void displayMenu() {
        int option;
        do {
            System.out.println("\nMovie Management System");
            System.out.println("1 Add New Movie and Save");
            System.out.println("2 Generate List of Movies Released in a Year");
            System.out.println("3 Generate List of Random Movies");
            System.out.println("4 Exit");
            System.out.print("Enter an option: ");

            try {
                option = Integer.parseInt(scanner.nextLine());

                switch (option) {
                    case 1:
                        addMovie();
                        break;
                    case 2:
                        generateMovieListInYear();
                        break;
                    case 3:
                        generateRandomMovieList();
                        break;
                    case 4:
                        saveMovieListToFile();
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid option!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
                option = 0;
            }
        } while (option != 4);
    }



    // Prompts the user to add a new movie with basic validation.

    public void addMovie() {
        System.out.print("Enter duration: ");
        int duration;
        try {
            duration = Integer.parseInt(scanner.nextLine());
            if (duration <= 0) {
                System.out.println("Duration must be positive!");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid duration! Please enter a number.");
            return;
        }

        System.out.print("Enter movie title: ");
        String title = scanner.nextLine().trim();
        if (title.isEmpty()) {
            System.out.println("Title cannot be empty!");
            return;
        }

        System.out.print("Enter year: ");
        int year;
        try {
            year = Integer.parseInt(scanner.nextLine());
            if (year <= 0) {
                System.out.println("Year must be positive!");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid year! Please enter a number.");
            return;
        }

        movies.add(new Movie(duration, title, year));
        System.out.println("Saving movies...");
        saveMovieListToFile();
        System.out.println("Added movie to the data file.");
    }


     // Displays movies released in a specific year.

    public void generateMovieListInYear() {
        System.out.print("Enter in year: ");
        int year;
        try {
            year = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid year! Please enter a number.");
            return;
        }

        System.out.println("\nMovie List");
        System.out.println("Duration   Year   Title");
        System.out.println("-----------------------");

        int totalDuration = 0;
        int count = 0;

        for (Movie movie : movies) {
            if (movie.getYear() == year) {
                System.out.println(movie);
                totalDuration += movie.getDuration();
                count++;
            }
        }

        if (count == 0) {
            System.out.println("No movies found for year " + year);
        } else {
            System.out.println("\nTotal duration: " + totalDuration + " minutes");
        }
    }



    	//  Displays random movies
        // * this needs to be fixed, It wont run on my device

    public void generateRandomMovieList() {
        System.out.print("Enter number of movies: ");
        int count;
        try {
            count = Integer.parseInt(scanner.nextLine());
            if (count <= 0) {
                System.out.println("Number must be positive!");
                return;
            }
            if (count > movies.size()) {
                System.out.println("Requested count exceeds available movies. Showing " + movies.size() + " movies.");
                count = movies.size();
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number! Please enter a valid number.");
            return;
        }

        System.out.println("\nMovie List");
        System.out.println("Duration   Year   Title");
        System.out.println("-----------------------");

        Random random = new Random();
        int totalDuration = 0;

        // Generate random indices
        Set<Integer> selectedIndices = new HashSet<>();
        while (selectedIndices.size() < count) {
            selectedIndices.add(random.nextInt(movies.size()));
        }

        // Display selected movies
        for (int index : selectedIndices) {
            Movie movie = movies.get(index);
            System.out.println(movie);
            totalDuration += movie.getDuration();
        }

        System.out.println("\nTotal duration: " + totalDuration + " minutes");
    }


     // Saves all movies back to the data file.

    public void saveMovieListToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Movie movie : movies) {
                writer.println(movie.toFileString());
            }
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

}

