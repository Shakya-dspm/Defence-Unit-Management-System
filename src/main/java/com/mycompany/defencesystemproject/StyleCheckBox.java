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
import javax.swing.JCheckBox;

/**
 *
 * @author KCC
 */
public class StyleCheckBox extends JCheckBox {

    private Color themeColor = Color.decode("#F5C396");      // Warm Peach/Apricot (#F5C396)
    private Color unselectedColor = new Color(160, 155, 145); // Warm gray outline for unselected state
    private Color textColor = new Color(65, 52, 42);         // Dark warm text color
    private int boxRadius = 6;

    public StyleCheckBox() {
        super("Checkbox");
        setupStyle();
    }

    public StyleCheckBox(String text) {
        super(text);
        setupStyle();
    }

    private void setupStyle() {
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setFont(new Font("Segoe UI", Font.BOLD, 11));
        setForeground(textColor);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        // --- FIX: Create a blank placeholder icon to hide the default square ---
        javax.swing.Icon blankIcon = new javax.swing.Icon() {
            @Override
            public void paintIcon(java.awt.Component c, java.awt.Graphics g, int x, int y) {
            }

            @Override
            public int getIconWidth() {
                return 18;
            }  // Matches our custom box size

            @Override
            public int getIconHeight() {
                return 18;
            }
        };
        setIcon(blankIcon);
        setSelectedIcon(blankIcon);
        setPressedIcon(blankIcon);
        setRolloverIcon(blankIcon);
        // -----------------------------------------------------------------------

        setIconTextGap(2);
        setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int size = 12; // Dimensions of the checkbox box
        int x = getInsets().left;
        int y = (getHeight() - size) / 2;

        boolean isSelected = isSelected();
        boolean isArmed = getModel().isArmed();
        boolean isRollover = getModel().isRollover();

        // 1. Draw the checkbox box background and outline
        if (isSelected) {
            // Fill background with theme color if checked
            if (isArmed) {
                g2.setColor(themeColor.darker());
            } else if (isRollover) {
                g2.setColor(Color.decode("#E09F67")); // Hover color
            } else {
                g2.setColor(themeColor);
            }
            g2.fillRoundRect(x, y, size, size, boxRadius, boxRadius);

            // Outer match boundary line
            g2.setColor(themeColor.darker());
            g2.setStroke(new BasicStroke(1.2f));
            g2.drawRoundRect(x, y, size, size, boxRadius, boxRadius);
        } else {
            // Empty background for unselected state
            g2.setColor(new Color(255, 252, 248)); // Matches custom text area/spinner bg
            g2.fillRoundRect(x, y, size, size, boxRadius, boxRadius);

            // Border color toggles on hover
            g2.setColor(isRollover ? themeColor : unselectedColor);
            g2.setStroke(new BasicStroke(1.8f));
            g2.drawRoundRect(x, y, size, size, boxRadius, boxRadius);
        }

        // 2. Draw the vector checkmark (✓) if checked
        if (isSelected) {
            g2.setColor(new Color(65, 52, 42)); // Dark brown-gray checkmark for nice contrast

            g2.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            // FIX: Rebalanced mathematical keypoints calculated directly for a 12x12 boundary box
            int startX = x + 3;
            int startY = y + 6;

            int midX = x + 5;
            int midY = y + 9;

            int endX = x + 9;
            int endY = y + 3;

            g2.drawLine(startX, startY, midX, midY);
            g2.drawLine(midX, midY, endX, endY);
        }

        g2.dispose();

        // Let standard Swing render the text component label correctly offset next to our box
        super.paintComponent(g);
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        // Ensure there is plenty of room for text + icon footprint calculation
        return new Dimension(d.width + 10, Math.max(d.height, 24));
    }

    // Getters & Setters for customization in the NetBeans Visual Designer
    public Color getThemeColor() {
        return themeColor;
    }

    public void setThemeColor(Color themeColor) {
        this.themeColor = themeColor;
        repaint();
    }

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
        setForeground(textColor);
        repaint();
    }

    public int getBoxRadius() {
        return boxRadius;
    }

    public void setBoxRadius(int boxRadius) {
        this.boxRadius = boxRadius;
        repaint();
    }
}
