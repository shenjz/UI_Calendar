// ButtonPanel.java

import javax.swing.*;
import java.awt.*;

public class ButtonPanel extends JPanel {

  private JRadioButton monthBtn;
  private JRadioButton weekBtn;
  private JRadioButton dayBtn;
  private ButtonGroup group;

  public ButtonPanel(Calendar calendar) {
    monthBtn = new JRadioButton("Month      ", true);
    monthBtn.addActionListener(e -> {
      calendar.switchCalendarView(0);
    });

    weekBtn = new JRadioButton("Week      ");
    weekBtn.addActionListener(e -> {
      calendar.switchCalendarView(1);
    });

    dayBtn = new JRadioButton("Day  ");
    dayBtn.addActionListener(e -> {
      calendar.switchCalendarView(2);
    });

    group = new ButtonGroup();
    group.add(monthBtn);
    group.add(weekBtn);
    group.add(dayBtn);

    this.setLayout(new FlowLayout());
    this.add(monthBtn);
    this.add(weekBtn);
    this.add(dayBtn);
    this.setBounds(820, 50, 390, 45);
    this.setBackground(new Color(255, 228, 181));
  }

  public int getSelectedIndex() {
    if (monthBtn.isSelected()) {
      return 0;
    } else if (weekBtn.isSelected()) {
      return 1;
    } else if (dayBtn.isSelected()) {
      return 2;
    }
    return 0;
  }
}
