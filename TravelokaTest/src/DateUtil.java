
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import org.openqa.selenium.WebElement;

public class DateUtil {
    public static String getCurrentDay() {
      
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        int todayInt = calendar.get(Calendar.DAY_OF_MONTH);
        String todayStr = Integer.toString(todayInt);

        return todayStr;
    }

    public static String getCurrentDayPlus(int days) {
        LocalDate currentDate = LocalDate.now();

        int dayOfWeekPlus = currentDate.getDayOfWeek().plus(days).getValue();
        String strPlus = Integer.toString(dayOfWeekPlus);
        System.out.println(strPlus);
        return Integer.toString(dayOfWeekPlus);
    }

    public static void clickDate(List<WebElement> elementList, String day) {
        elementList.stream()
            .filter(element -> element.getText().contains(day))
            .findFirst()
            .ifPresent(WebElement::click);
    }
}
