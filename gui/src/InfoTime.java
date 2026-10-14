import java.time.*;

/**
 * Classe que irá buscar a data atual detalhada do sistema
 * @author Dárcio Guerra e Rodrigo Pereira
 */
public class InfoTime {
    private DayOfWeek dayOfWeek;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;

    public InfoTime() {
        LocalDateTime now = LocalDateTime.now();

        this.dayOfWeek = now.getDayOfWeek();
        this.year = now.getYear();
        this.month = now.getMonthValue();
        this.day = now.getDayOfMonth();
        this.hour = now.getHour();
        this.minute = now.getMinute();
        this.second = now.getSecond();
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }
}
