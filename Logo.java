// Logo.java

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

// This function loads an image and later this function is used to add an image to the logoPanel
public class Logo extends JPanel {

  Image image;

  public Logo(Image image) {
    this.image = image;
    this.setOpaque(true);
  }

  public void paintComponent(Graphics g) {
    super.paintComponents(g);
    g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);

  }
}