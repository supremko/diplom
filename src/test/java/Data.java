import lombok.*;

public class Data {
    private Data() {}

    @Value
    public static class MonthAndYear {
        private String validMonth;
        private String validYear;
        private String invalidMonth;
        private String invalidYear;
    }

    public static MonthAndYear getMonthAndYear() {
        return new MonthAndYear("10", "22", "19", "30");
    }

    @Value
    public static class ListCards {
        private String card1;
        private String card2;
    }

    public static ListCards getListCards() {
        return new ListCards("4444 4444 4444 4441", "4444 4444 4444 4442");
    }
}
