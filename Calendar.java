// Calendar.java
import java.awt.Color;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.event.WindowEvent;

/*
 * This program displays the calendar of year 2019.
 * Three main panels are added to the contentPane to fulfill the functions of the calendar.
 */
public class Calendar extends JFrame {

  // Declare variables including the labels and panels needed to use
  private JPanel currentView;
  private JPanel[] viewPanels = new JPanel[3];
  private JPanel contentPane = new JPanel();
  private ButtonPanel buttonPanel;

  // create the constructor of the Calendar class
  public Calendar() {
    //set the title, size and closeDefaultOperation of the frame
    setTitle("Calendar");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(1400, 700);

    // set the contentPane
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    contentPane.setLayout(null);

    JPanel headPanel = createHeadPanel();
    contentPane.add(headPanel);

    EventManagerPanel eventManagerPanel = new EventManagerPanel(this);
    eventManagerPanel.setBounds(1000, 170, 350, 480);
    contentPane.add(eventManagerPanel);

    // refresh calendar views
    refreshCalendarViews(0);

    // set the contentPane
    setContentPane(contentPane);

    this.setLocationRelativeTo(null);
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent winEvt) {
        EventStore.getInstance().store();
        
        System.exit(0);
      }
    });
  }

  public void refreshCalendarViews(int index) {
    for (int i = 0; i < viewPanels.length; i++) {
      if (viewPanels[i] != null) {
        contentPane.remove(viewPanels[i]);
      }
    }
    currentView = null;

    // add invisible calendar view panels
    viewPanels[0] = CalendarViews.createViewPanel(53, 7, 0, 6, CalendarViews.monthIndex());
    viewPanels[1] = CalendarViews.createViewPanel(1, 7, 0, 6, CalendarViews.weeklyIndex());
    viewPanels[2]  = CalendarViews.createViewPanel(1, 1, 0, 0, CalendarViews.dayIndex());

    for (int i = 0; i < viewPanels.length; i++) {
      viewPanels[i].setVisible(false);
      contentPane.add(viewPanels[i]);
    }

    switchCalendarView(index);
  }

  private JPanel createHeadPanel() {
    // declare the headPanel and set its background, bounds and add it to the contentPane
    JPanel headPanel = new JPanel();
    headPanel.setBackground(Color.WHITE);
    headPanel.setBounds(10, 10, 1350, 138);
    headPanel.setLayout(null);

    // set the logoPanel and add the SCU logo to the logoPanel
    JPanel logoPanel = new Logo((new ImageIcon("SCULogo.png")).getImage());
    logoPanel.setBounds(442, 0, 137, 135);
    headPanel.add(logoPanel);

    // set the yearLabel, add the year "2019" to the label, and set the properties of digits "2019"
    JLabel yearLabel = new JLabel("2019");
    yearLabel.setBounds(629, 53, 100, 42);
    headPanel.add(yearLabel);
    yearLabel.setForeground(Color.ORANGE);
    yearLabel.setFont(new Font("Times New Roman", Font.BOLD, 35));

    // radio buttons
    buttonPanel = new ButtonPanel(this);
    headPanel.add(buttonPanel);

    return headPanel;
  }

  // Main function: instantiate a Calendar object and make it visible
  public static void main(String[] args) {
    // read event history file
    EventStore.getInstance().load();

    Calendar calendar = new Calendar();
    calendar.setVisible(true);
  }

  public void switchCalendarView(int index) {
    if (currentView != null) {
      currentView.setVisible(false);
    }

    viewPanels[index].setVisible(true);
    currentView = viewPanels[index];
  }

  public int getSelectedViewIndex() {
    return buttonPanel.getSelectedIndex();
  }
}
