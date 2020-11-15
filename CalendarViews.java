// CalendarViews.java

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.stream.Collectors;
import javax.swing.*;
import java.util.Map;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarViews {

  public static JPanel createViewPanel(int rows, int cols, int startDay, int endDay, int startIndex) {
    JPanel viewPanel = new JPanel();
    viewPanel.setBackground(Color.WHITE);
    viewPanel.setBounds(10, 10, 1000, 1024);
    viewPanel.setLayout(null);

    JPanel titlePane = CalendarViews.createTitlePanel(startDay, endDay);
    viewPanel.add(titlePane);

    // set the scrollPane and add it to the contentPane
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.setBounds(10, 190, 1000, 480);

    JPanel calendarPanel = CalendarViews.createCalendarPanel(rows, cols, startIndex);
    scrollPane.setViewportView(calendarPanel);

    viewPanel.add(scrollPane);

    return viewPanel;
  }

  public static JPanel createTitlePanel(int startDay, int endDay) {
    String weekTitle[] = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
    JLabel titleName[] = new JLabel[endDay - startDay + 1];

    // declare the titlePanel and set its bounds, layout, background, and add it to the contentPane
    JPanel titlePanel = new JPanel();
    titlePanel.setBounds(10, 149, 1000, 42);
    titlePanel.setLayout(new GridLayout(1, 7));
    titlePanel.setBackground(new Color(244, 164, 96));

    // use a loop to add the week titles to the titlePanel one by one
    for (int i = startDay; i <= endDay; i++) {
      titleName[i] = new JLabel(weekTitle[i], JLabel.CENTER);
      titlePanel.add(titleName[i]);
    }

    return titlePanel;
  }

  public static JPanel createCalendarPanel(int rows, int cols, int startIndex) {
    // declare the calendarPanel and set the scrollPane to view the content of calendarPanel
    JPanel calendarPanel = new JPanel();
    calendarPanel.setLayout(new GridLayout(rows, cols));
    calendarPanel.setBackground(Color.white);

    JLabel dayLabel[] = new JLabel[rows * cols];
    // create 53 * 7 labels and use a for loop to add individual labels to the calendarPanels
    for (int i = 0; i < rows * cols; i++) {
      dayLabel[i] = new JLabel("", JLabel.CENTER);
      dayLabel[i].setForeground(Color.BLACK);
      dayLabel[i].setBorder(BorderFactory.createLineBorder(Color.gray));
      dayLabel[i].setVerticalAlignment(SwingConstants.TOP);

      calendarPanel.add(dayLabel[i]);
    }

    CalendarDate dates = new CalendarDate();
    // group events by date
    Map<Date, List<Event>> eventsMap = EventStore.getInstance().getEventList()
        .stream()
        .collect(Collectors.groupingBy(Event::getDate));

    // use a nested for loop to label each day with a date, set the background color and size,
    // and add the US holidays into respective dates and hightlight the first day of each month
    // the first day in the calendar is set with "count = -1" so that Jan-1 can be made 'count = 1' just for convenience
    for (int i = 0; i < rows; i++) {
      for(int j = 0; j < cols; j++) {
        int index = startIndex + cols * i + j;
        String date = dates.getDate(index);

        List<Event> events = new ArrayList<>();
        try {
          final String keyStr = date + ((index <= 0) ? "-2018" : "-2019");
          Date key = new SimpleDateFormat("MMMM-d-yyyy", Locale.US).parse(keyStr);

          if (eventsMap.containsKey(key)) {
            events = eventsMap.get(key);
          }
        } catch (Exception e) {
        }

        dayLabel[i*7+j].setText(formatEventAsHtml(date, events));
      }
    }

    return calendarPanel;
  }

  public static String formatEventAsHtml(String dateStr, List<Event> events) {
    StringBuilder builder = new StringBuilder();
    String daydate[] = dateStr.split("-");

    // mark the first day of month as red
    if (daydate[1].equals("1")) {
      builder.append("<html><body><p align=\"center\" style='width:100px;color:red;background-color:#FFE4B5;font-size:12px'>" + dateStr + "</p><br/>");
    } else {
      builder.append("<html><body><p align=\"center\" style='width:100px;color:black;background-color:#FFE4B5;font-size:12px'>" + dateStr + "</p><br/>");
    }


    for (Event event : events) {
      builder.append("<p align=\"center\" style='color:" + event.getColor().toLowerCase() + ";font-size:12px'><br/><br/>" + event.getName() + "<br/><br/><br/></p>");
    }

    builder.append("</body></html>");

    return builder.toString();
  }

  public static int dayIndex() {
    Calendar calendar = Calendar.getInstance();
    return calendar.get(Calendar.DAY_OF_YEAR);
  }

  public static int weeklyIndex() {
    Calendar calendar = Calendar.getInstance();
    return (calendar.get(Calendar.WEEK_OF_YEAR) - 1) * 7 - 1;
  }

  public static int monthIndex() {
    return -1;
  }
}