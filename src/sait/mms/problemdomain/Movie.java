package sait.mms.problemdomain;

// Represents a movie with duration, title, and year attributes.



 public class Movie {
    private int duration;
    private String title;
    private int year;


    // the actual movie object with it's parameters

    public Movie(int duration, String title, int year) {
        this.duration = duration;
        this.title = title;
        this.year = year;
    }



    // Movie Duration, Both Get and Set

    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }

    // Movie Titke, Both Get and Set
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    // Movie Year, Both Get and Set
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }


    // String Return

    @Override
    public String toString() {
        return String.format("%-10d %-6d %s", duration, year, title);
    }

   // String for filing
    public String toFileString() {
        return duration + "," + title + "," + year;
    }
}