package oop._11_advanced;

/**
 * Demonstrates Enum (Enumeration)
 * - Special class for group of constants
 * - Type-safe constants
 * - Can have fields, methods, constructors
 */
public enum WeekDays {
    // Enum constants
    MONDAY("Monday", 1, true),
    TUESDAY("Tuesday", 2, true),
    WEDNESDAY("Wednesday", 3, true),
    THURSDAY("Thursday", 4, true),
    FRIDAY("Friday", 5, true),
    SATURDAY("Saturday", 6, false),
    SUNDAY("Sunday", 7, false);

    // Fields
    private final String displayName;
    private final int dayNumber;
    private final boolean isWeekday;

    // Constructor (private by default)
    WeekDays(String displayName, int dayNumber, boolean isWeekday) {
        this.displayName = displayName;
        this.dayNumber = dayNumber;
        this.isWeekday = isWeekday;
    }

    // Getters
    public String getDisplayName() {
        return displayName;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public boolean isWeekday() {
        return isWeekday;
    }

    // Custom method
    public void printDayInfo() {
        System.out.println(displayName + " (Day " + dayNumber + ")");
        System.out.println("Weekday: " + (isWeekday ? "Yes" : "No"));
    }

    public static void main(String[] args) {
        System.out.println("=== Enum Demo ===\n");

        // Using enum
        WeekDays today = WeekDays.MONDAY;
        System.out.println("Today is: " + today.getDisplayName());
        System.out.println("Is weekday? " + today.isWeekday());

        System.out.println("\n--- All Days ---");
        for (WeekDays day : WeekDays.values()) {
            day.printDayInfo();
            System.out.println();
        }

        // Switch with enum
        System.out.println("--- Switch Example ---");
        switchDay(WeekDays.MONDAY);
        switchDay(WeekDays.SATURDAY);
    }

    static void switchDay(WeekDays day) {
        switch (day) {
            case MONDAY:
                System.out.println("Start of the work week!");
                break;
            case FRIDAY:
                System.out.println("Almost weekend!");
                break;
            case SATURDAY:
            case SUNDAY:
                System.out.println("It's weekend!");
                break;
            default:
                System.out.println("Midweek day");
        }
    }
}
