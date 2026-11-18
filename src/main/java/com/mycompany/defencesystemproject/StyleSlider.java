/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.defencesystemproject;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JSlider;
import javax.swing.plaf.basic.BasicSliderUI;

/**
 *
 * @author KCC
 */
public class StyleSlider extends JSlider {

    private Color themeColor = Color.decode("#D0B17A");      // Warm Tan/Gold (#D0B17A)
    private Color trackBgColor = new Color(225, 222, 215);   // Soft light gray-beige empty track
    private Color containerColor = new Color(248, 246, 240); // Soft off-white rounded background
    private Color borderColor = new Color(190, 175, 145);    // Subtle warm border
    private int cornerRadius = 12;

    public StyleSlider() {
        super(JSlider.VERTICAL, 0, 100, 50); // Vertical slider, 0 to 100, starts at 50
        setupSlider();
    }

    private void setupSlider() {
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setFont(new Font("Segoe UI", Font.BOLD, 11));
        setForeground(new Color(75, 70, 60)); // Dark warm gray for 0, 20, 40 labels
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Major ticks only every 20 units (0, 20, 40, 60, 80, 100)
        setMajorTickSpacing(20);
        setMinorTickSpacing(10);
        setPaintTicks(true);
        setPaintLabels(true);
        setLabelTable(createStandardLabels(20));

        // Apply custom rounded track & thumb UI
        setUI(new CustomSliderUI(this));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 1. Paint rounded background container
        g2.setColor(containerColor);
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);

        // 2. Paint subtle rounded border around the slider
        g2.setColor(borderColor);
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);

        g2.dispose();
        super.paintComponent(g);
    }

    private class CustomSliderUI extends BasicSliderUI {

        public CustomSliderUI(JSlider slider) {
            super(slider);
        }

        @Override
        protected Dimension getThumbSize() {
            // Prevents ghost trails when dragging the circular thumb
            return new Dimension(16, 16);
        }

        @Override
        public void paintTrack(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int thickness = 3; // Rounded track thickness

            if (slider.getOrientation() == JSlider.VERTICAL) {
                int x = trackRect.x + (trackRect.width - thickness) / 2;

                // Full unfilled track with rounded ends
                g2.setColor(trackBgColor);
                g2.fillRoundRect(x, trackRect.y, thickness, trackRect.height, thickness, thickness);

                // Filled track from 0 up to the thumb in #D0B17A
                int fillTop = thumbRect.y + (thumbRect.height / 2);
                int fillBottom = trackRect.y + trackRect.height;
                int fillHeight = fillBottom - fillTop;

                if (fillHeight > 0) {
                    g2.setColor(themeColor);
                    g2.fillRoundRect(x, fillTop, thickness, fillHeight, thickness, thickness);
                }
            } else {
                // Support horizontal orientation too if changed in Properties
                int y = trackRect.y + (trackRect.height - thickness) / 2;

                g2.setColor(trackBgColor);
                g2.fillRoundRect(trackRect.x, y, trackRect.width, thickness, thickness, thickness);

                int fillWidth = (thumbRect.x + thumbRect.width / 2) - trackRect.x;
                if (fillWidth > 0) {
                    g2.setColor(themeColor);
                    g2.fillRoundRect(trackRect.x, y, fillWidth, thickness, thickness, thickness);
                }
            }

            g2.dispose();
        }

        @Override
        public void paintThumb(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int size = 11;
            int x = thumbRect.x + (thumbRect.width - size) / 2;
            int y = thumbRect.y + (thumbRect.height - size) / 2;

            // Soft shadow behind the thumb
            g2.setColor(new Color(0, 0, 0, 45));
            g2.fillOval(x, y + 1, size, size);

            // Fill thumb with #D0B17A
            g2.setColor(themeColor);
            g2.fillOval(x, y, size, size);

            // Crisp white ring border
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(2.2f));
            g2.drawOval(x, y, size, size);

            g2.dispose();
        }

        @Override
        public void paintFocus(Graphics g) {
            // Hides the dotted focus rectangle when clicked
        }
    }

    // Getters & Setters so you can tweak colors/radius in NetBeans Properties
    public Color getThemeColor() {
        return themeColor;
    }

    public void setThemeColor(Color themeColor) {
        this.themeColor = themeColor;
        repaint();
    }

    public Color getContainerColor() {
        return containerColor;
    }

    public void setContainerColor(Color containerColor) {
        this.containerColor = containerColor;
        repaint();
    }

    public int getCornerRadius() {
        return cornerRadius;
    }

    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        repaint();
    }
}
