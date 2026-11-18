/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.defencesystemproject;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.AbstractBorder;

/**
 *
 * @author KCC
 */
public class StyleTextArea extends JScrollPane {

    private final JTextArea textArea;
    private Color themeColor = Color.decode("#F5C396");      // Warm Peach/Apricot (#F5C396)
    private Color containerColor = new Color(255, 252, 248); // Warm soft white background
    private int cornerRadius = 12;

    public StyleTextArea() {
        textArea = new JTextArea();
        setViewportView(textArea);
        setupStyle();
    }

    private void setupStyle() {
        // 1. Configure the Scroll Pane wrapper
        setOpaque(false);
        getViewport().setOpaque(false);
        setViewportBorder(null);
        applyRoundedBorder();

        // Hide default scrollpane square backgrounds
        getVerticalScrollBar().setOpaque(false);
        getHorizontalScrollBar().setOpaque(false);

        // 2. Configure the Inner Text Area
        textArea.setOpaque(false);
        textArea.setBackground(new Color(0, 0, 0, 0));
        textArea.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10)); // Inner text padding

        textArea.setFont(new Font("Segoe UI", Font.BOLD, 11));
        textArea.setForeground(new Color(65, 52, 42)); // Warm dark brown text

        // Essential configuration for operational log displays
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
    }

    private void applyRoundedBorder() {
        setBorder(new AbstractBorder() {
            @Override
            public void paintBorder(java.awt.Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Fill smooth background
                g2.setColor(containerColor);
                g2.fillRoundRect(x, y, width - 1, height - 1, cornerRadius, cornerRadius);

                // Outer border outline in #F5C396
                g2.setColor(themeColor);
                g2.setStroke(new BasicStroke(1.8f));
                g2.drawRoundRect(x, y, width - 1, height - 1, cornerRadius, cornerRadius);

                g2.dispose();
            }

            @Override
            public Insets getBorderInsets(java.awt.Component c) {
                return new Insets(4, 4, 4, 4);
            }
        });
    }

    public void appendMessage(String message) {
        if (textArea.getText().isEmpty()) {
            // If it's the very first message, set it directly without a leading newline
            textArea.setText(message);
        } else {
            // For subsequent messages, add a newline before appending
            textArea.append("\n" + message);
        }
        // Force the carets position to the end to keep view pinned to newest logs
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    public void clearLog() {
        textArea.setText("");
    }

    // Getters so you can access the inner JTextArea directly if needed
    public JTextArea getTextArea() {
        return textArea;
    }

    public Color getThemeColor() {
        return themeColor;
    }

    public void setThemeColor(Color themeColor) {
        this.themeColor = themeColor;
        repaint();
    }

    void setText(String massage) {
        textArea.setText(massage);
    }

}
