// EventStore.java

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.io.File;

public class EventStore {

  private static final EventStore instance = new EventStore("events.txt");

  private String fileName;
  private List<Event> eventList = new ArrayList<>();

  private EventStore(String fileName) {
    this.fileName = fileName;
  }

  public static EventStore getInstance() {
    return instance;
  }

  public boolean load() {
    BufferedReader reader = null;

    try {
      reader = new BufferedReader(new FileReader(new File(fileName)));

      String line;
      String[] data = new String[3];

      int count = 0;
      while ((line = reader.readLine()) != null) {
        data[count % 3] = line;

        ++count;
        if (count % 3 == 0) {
          try {
            eventList.add(new Event(EventManagerPanel.format.parse(data[0]), data[1], data[2]));
          } catch (Exception e) {
          }
        }
      }
    } catch (IOException e) {
    }

    if (reader != null) {
      try {
        reader.close();
      } catch (Exception e) {
      }
    }

    return true;
  }

  public boolean store() {
    BufferedWriter writer = null;

    try {
      writer = new BufferedWriter(new FileWriter(new File(fileName)));

      for (Event event : eventList) {
        writer.write(EventManagerPanel.format.format(event.getDate()));
        writer.newLine();

        writer.write(event.getName());
        writer.newLine();

        writer.write(event.getColor());
        writer.newLine();
      }
    } catch (IOException e) {
    }

    if (writer != null) {
      try {
        writer.close();
      } catch (Exception e) {
      }
    }

    return true;
  }

  public List<Event> getEventList() {
    return eventList;
  }
}