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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;
import javax.swing.border.AbstractBorder;

/**
 *
 * @author KCC
 */
public class StyleTextField extends JTextField {

    private Color themeColor = Color.decode("#F5C396");      // Warm Peach/Apricot (#F5C396)
    private Color activeBorderColor = Color.decode("#E09F67"); // Deeper peach when typing
    private Color unselectedColor = new Color(200, 195, 185); // Soft neutral gray outline
    private Color containerColor = new Color(255, 252, 248); // Warm soft white background
    private int cornerRadius = 14;

    private String placeholder = "Type message here...";     // Hint text string
    private boolean isHovered = false;

    public StyleTextField() {
        super(15);
        setupStyle();
    }

    public StyleTextField(String text) {
        super(text);
        setupStyle();
    }

    private void setupStyle() {
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 0));
        setEditable(true);

        setFont(new Font("Segoe UI", Font.BOLD, 11));
        setForeground(Color.BLACK);
        setCaretColor(Color.BLACK);
        setHorizontalAlignment(JTextField.LEFT);

        applyRoundedBorder();

        // Repaint tracking for hover states
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                repaint();
            }
        });

        // Focus listener forces a repaint to show/hide the placeholder hint text
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                repaint();
            }
        });

        // --- FIX: Explicitly set a standard height (35 pixels) for the text field layout ---
        setPreferredSize(new java.awt.Dimension(150, 35));
        // -----------------------------------------------------------------------------------
    }

    private void applyRoundedBorder() {
        setBorder(new AbstractBorder() {
            @Override
            public void paintBorder(java.awt.Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // --- REMOVED Background Fill from here so it doesn't cover text ---
                // 1. Select border color context
                if (isFocusOwner()) {
                    g2.setColor(activeBorderColor);
                    g2.setStroke(new BasicStroke(2.0f));
                } else if (isHovered) {
                    g2.setColor(themeColor);
                    g2.setStroke(new BasicStroke(1.5f));
                } else {
                    g2.setColor(unselectedColor);
                    g2.setStroke(new BasicStroke(1.2f));
                }

                // 2. Draw outline frame
                g2.drawRoundRect(x, y, width - 1, height - 1, cornerRadius, cornerRadius);
                g2.dispose();
            }

            @Override
            public Insets getBorderInsets(java.awt.Component c) {
                return new Insets(6, 12, 6, 12); // Clean text padding cushion
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2bg = (Graphics2D) g.create();
        g2bg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // --- FIX: Fill container background FIRST before any text layers are rendered ---
        g2bg.setColor(containerColor);
        g2bg.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
        g2bg.dispose();

        // Standard Swing paints the actual characters on top of our background
        super.paintComponent(g);

        // Draw placeholder text inline if the field is empty and un-focused
        if (getText().isEmpty() && !isFocusOwner()) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setFont(getFont().deriveFont(Font.ITALIC));
            g2.setColor(new Color(160, 155, 145)); // Soft muted gray hint color

            // Align hint text neatly within the padded borders
            int x = getInsets().left;
            int y = (getHeight() - g2.getFontMetrics().getHeight()) / 2 + g2.getFontMetrics().getAscent();
            g2.drawString(placeholder, x, y);
            g2.dispose();
        }
    }

    // Getters & Setters for customization in NetBeans Properties panel
    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
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
