// Event.java

import java.awt.Color;
import java.util.Date;

public class Event {
  private Date date;
  private String name;
  private String color;

  public Event(Date date, String name, String color) {
    this.date = date;
    this.name = name;
    this.color = color;
  }

  public Date getDate() {
    return date;
  }

  public String getName() {
    return name;
  }

  public String getColor() {
    return color;
  }
}