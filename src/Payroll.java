import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;

/*
Given military time of start of work day and end of work day, prints hours worked in hours and minutes, then prints hours and overtime in decimal form
@author ktromp1
@version 3/27/2021

 */
public class Payroll {

    public static void main(String[] args) throws ParseException {
        Scanner in = new Scanner(System.in);

            System.out.println("Please enter the hours and minute date stamp for the START of the work day in format \"HH:mm\"");
            String start = in.next(); // User Input: starting hour for work day
            System.out.println("Please enter the hours and minute date stamp for the END of the work day in format \"HH:mm\"");
            String end = in.next(); // User Input: ending hour for work day
            System.out.println("Enter \"true\" if lunch was taken or \"false\" if lunch was not taken");
            boolean lunch = in.nextBoolean(); // User Input: lunch taken?
            hourToDec(start, end, lunch);

    }

    //converts given military time start and end of day and finds the difference. Then, turns them to decimal form.
    //@param hour1 military time for start of work day | hour format (00:00)
    //@param hour2 military time for end of work day | hour format (00:00)
    //@return total minutes worked in minutes

    public static void hourToDec(String start, String end,boolean lunch) throws ParseException {
        double overtime = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm"); //format for datestamp given
        Date d1 = sdf.parse(start); //turns the input into Date code (in milliseconds)
        Date d2 = sdf.parse(end);
        long diff = d2.getTime()-d1.getTime(); // Finds the hours worked in milliseconds by subtracting end of day by start of day
        double minutes = TimeUnit.MILLISECONDS.toMinutes(diff) % 60; // Minutes position for total hours worked
        double hours = TimeUnit.MILLISECONDS.toHours(diff) % 24; // Hours position for total hours worked

        if (lunch ==true) // Subtracts 30 minutes from hours worked when lunch has been taken
        {
            if (minutes<30)
            {
                hours--;
                minutes+=30;
            }
            else
            {
                minutes-=30;
            }
        }
        if ((hours>8)||hours==8&&minutes>=1) // Makes sure normal rated hours cannot exceed 8 hours and calculates overtime in minutes
        {
            overtime+=((hours-8)*60)+minutes;
            hours=8;
            minutes=0;
        }
        double overtimeDec = Math.round((overtime/60.0)*100.0)/100.0; // Turns the overtime in minutes into a decimal form and rounds to 100ths
        double hoursDec = Math.round((hours + minutes/60.0)*100.0)/100.0; // turns hours and minutes worked into decimal form and rounds to 100ths
        System.out.println("Total hours: " + hours + " and total minutes: " + minutes + " with overtime: " + overtime+"(in minutes)");
        System.out.println("Total hours in decimal form: " + hoursDec + " With overtime: " + overtimeDec + "(in hours)");

    }
}
