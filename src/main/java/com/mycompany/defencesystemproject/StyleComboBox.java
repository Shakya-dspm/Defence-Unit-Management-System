/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.defencesystemproject;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;

/**
 *
 * @author KCC
 */
public class StyleComboBox<E> extends JComboBox<E> {

    private Color themeColor = Color.decode("#F5C396");      // Warm Peach/Apricot (#F5C396)
    private Color hoverArrowColor = Color.decode("#E09F67"); // Deeper peach on hover
    private Color containerColor = new Color(255, 252, 248); // Warm soft white background
    private Color textColor = new Color(65, 52, 42);         // Warm dark brown text
    private int cornerRadius = 14;                           // Smooth rounded corners

    public StyleComboBox() {
        super();
        setupStyle();
    }

    public StyleComboBox(E[] items) {
        super(items);
        setupStyle();
    }

    private void setupStyle() {
        setOpaque(false);
        setBackground(containerColor);
        setForeground(textColor);
        setFont(new Font("Segoe UI", Font.BOLD, 11));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(160, 35));
        setBorder(BorderFactory.createEmptyBorder(4, 12, 4, 8));

        // Apply custom UI for the arrow button and dropdown popup
        setUI(new CustomComboBoxUI());

        // Style the dropdown list items (padding, selection highlight, and colors)
        setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setFont(new Font("Segoe UI", isSelected ? Font.BOLD : Font.PLAIN, 13));
                setForeground(textColor);

                if (index == -1) {
                    // Closed combo box display: make transparent so no gray-blue rectangle shows
                    setOpaque(false);
                    setBorder(new EmptyBorder(0, 4, 0, 4));
                } else {
                    // Open dropdown popup items: keep solid background and hover highlight
                    setOpaque(true);
                    setBorder(new EmptyBorder(6, 12, 6, 12));
                    setBackground(isSelected ? themeColor : containerColor);
                }
                return this;
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 1. Fill smooth rounded background container
        g2.setColor(containerColor);
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);

        // 2. Subtle vertical divider before the dropdown arrow
        int dividerX = getWidth() - 32;
        g2.setColor(new Color(themeColor.getRed(), themeColor.getGreen(), themeColor.getBlue(), 130));
        g2.drawLine(dividerX, 6, dividerX, getHeight() - 7);

        // 3. Draw outer rounded border in #F5C396
        g2.setColor(isPopupVisible() ? hoverArrowColor : themeColor);
        g2.setStroke(new BasicStroke(1.8f));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);

        g2.dispose();
        super.paintComponent(g);
    }

    private class CustomComboBoxUI extends BasicComboBoxUI {

        @Override
        protected JButton createArrowButton() {
            JButton btn = new JButton() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    // Arrow color states
                    if (getModel().isPressed()) {
                        g2.setColor(new Color(185, 115, 55));
                    } else if (getModel().isRollover()) {
                        g2.setColor(hoverArrowColor);
                    } else {
                        g2.setColor(new Color(145, 105, 70));
                    }

                    // Draw clean downward chevron arrow
                    int cx = getWidth() / 2;
                    int cy = getHeight() / 2;
                    g2.setStroke(new BasicStroke(2.2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    g2.drawLine(cx - 5, cy - 2, cx, cy + 3);
                    g2.drawLine(cx, cy + 3, cx + 5, cy - 2);

                    g2.dispose();
                }
            };
            btn.setBorder(BorderFactory.createEmptyBorder());
            btn.setContentAreaFilled(false);
            btn.setFocusPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            return btn;
        }

        @Override
        public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
            // Leave empty so Nimbus does not draw a blue selection box inside the closed combo box
        }

        @Override
        protected ComboPopup createPopup() {
            BasicComboPopup popup = new BasicComboPopup(comboBox) {
                @Override
                protected void configurePopup() {
                    super.configurePopup();
                    // Style the dropdown list border to match the #F5C396 theme
                    setBorder(BorderFactory.createLineBorder(themeColor, 2));
                }
            };
            return popup;
        }
    }

    // Getters & Setters so you can customize in NetBeans Properties window
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
