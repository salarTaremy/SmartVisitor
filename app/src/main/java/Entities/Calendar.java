package Entities;


public class Calendar {

    public int Persian_Date;
    public int Gregorian_Date;
    public String Pr_DateDisplay;
    public String Gr_DateDisplay;
    public int Pr_Year;
    public int Pr_Month;
    public int Pr_Day;
    public int DayOfWeek;
    public String DayTitle;
    public String DayName;
    public String MonthName;

    public Calendar() {
    }

    public Calendar(int persian_Date, int gregorian_Date, String pr_DateDisplay, String gr_DateDisplay, int pr_Year, int pr_Month, int pr_Day, int dayOfWeek, String dayTitle, String dayName, String monthName) {
        Persian_Date = persian_Date;
        Gregorian_Date = gregorian_Date;
        Pr_DateDisplay = pr_DateDisplay;
        Gr_DateDisplay = gr_DateDisplay;
        Pr_Year = pr_Year;
        Pr_Month = pr_Month;
        Pr_Day = pr_Day;
        DayOfWeek = dayOfWeek;
        DayTitle = dayTitle;
        DayName = dayName;
        MonthName = monthName;
    }
}