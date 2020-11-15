// EventManagerPanel.java

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;
import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class EventManagerPanel extends JPanel {

  private JLabel headLb;
  private JLabel nameLb;
  private JLabel dateLb;
  private JPanel labelPanel;
  private JTextField nameTF;
  private JTextField dateTF;
  private JPanel tfPanel;
  JComboBox<String> comboBox;
  private JButton addEvent;
  private JLabel changeColor;
  private JLabel history;
  private JList historyTF;
  private JButton deleteE;

  public static final SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
  private static final String[] colorList = { "Red", "Yellow", "Orange", "Green", "Gray" };

  public EventManagerPanel(Calendar calendar) {

    headLb = new JLabel("Customize Calendar");
    headLb.setFont(new Font("Times New Roman", Font.BOLD, 20));
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    add(headLb);

    nameLb = new JLabel("Event Name:");
    dateLb = new JLabel("Event Date:");
    nameLb.setPreferredSize(new Dimension(116, 38));
    dateLb.setPreferredSize(new Dimension(116, 38));
    labelPanel = new JPanel();
    labelPanel.setPreferredSize(new Dimension(300, 80));
    labelPanel.add(nameLb);
    labelPanel.add(dateLb);
    add(labelPanel);

    nameTF = new JTextField();
    nameTF.setPreferredSize(new Dimension(116, 38));
    dateTF = new JTextField();
    dateTF.setPreferredSize(new Dimension(98, 38));
    addEvent = new JButton("Add Event");
    addEvent.addActionListener(e -> {
      String name = nameTF.getText();
      String color = colorList[comboBox.getSelectedIndex()];

      if (name.length() > 0) {
        try {
          Date date = format.parse(dateTF.getText());
          EventStore.getInstance().getEventList().add(new Event(date, name, color));

          // refresh history
          historyTF.setListData(getEventStrings());

          // refresh calendar views
          calendar.refreshCalendarViews(calendar.getSelectedViewIndex());
        } catch (ParseException ex) {
          JOptionPane.showMessageDialog(null, "Invalid date string: " + dateTF.getText());
        }
      }
    });
    tfPanel = new JPanel();
    tfPanel.setSize(300, 80);
    tfPanel.add(nameTF);
    tfPanel.add(dateTF);
    tfPanel.add(addEvent);
    add(tfPanel);

    changeColor = new JLabel("Change Text Color:");
    changeColor.setPreferredSize(new Dimension(200, 80) );
    add(changeColor);

    comboBox = new JComboBox<>(colorList);
    comboBox.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
        }
      }
    });
    comboBox.setSelectedIndex(0);
    add(comboBox);

    history = new JLabel("History:");
    add(history);
    historyTF = new JList();
    historyTF.setPreferredSize(new Dimension(200, 400));
    historyTF.setBackground(Color.WHITE);
    historyTF.setListData(getEventStrings());
    add(historyTF);
    deleteE = new JButton("Delete Event");
    deleteE.addActionListener(e -> {
      EventStore.getInstance().getEventList().remove(historyTF.getSelectedIndex());

      // refresh history
      historyTF.setListData(getEventStrings());

      // refresh calendar views
      calendar.refreshCalendarViews(calendar.getSelectedViewIndex());
    });
    add(deleteE);
  }

  private String[] getEventStrings() {
    String[] result = new String[EventStore.getInstance().getEventList().size()];

    for (int i = 0; i < result.length; i++) {
      result[i] = EventStore.getInstance().getEventList().get(i).getName() + " "
          + format.format(EventStore.getInstance().getEventList().get(i).getDate());
    }

    return result;
  }
}