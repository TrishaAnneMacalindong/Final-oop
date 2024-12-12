package com.oop.Genesys;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BackgroundPanel extends JPanel {
    private BufferedImage backgroundImage;

    public BackgroundPanel(String imagePath) {
        try {
            backgroundImage = ImageIO.read(getClass().getResource(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            int imgWidth = backgroundImage.getWidth();
            int imgHeight = backgroundImage.getHeight();
            int panelWidth = getWidth();
            int panelHeight = getHeight();

            int x = (panelWidth - imgWidth) / 2;
            int y = (panelHeight - imgHeight) / 2;

            g.drawImage(backgroundImage, x, y, this);
        }
    }
}
