package vista;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;

public class ImagenFondoDesktopPane extends JDesktopPane {

    private Image imagen;

    public ImagenFondoDesktopPane() {
        // Asegúrate de que esta imagen esté en src/img/
        imagen = new ImageIcon(getClass().getResource("/img/fondo6.png")).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
    }

}
