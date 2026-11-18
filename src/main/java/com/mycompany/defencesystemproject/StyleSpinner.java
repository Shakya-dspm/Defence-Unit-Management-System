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
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.basic.BasicSpinnerUI;
/**
 *
 * @author KCC
 */
public class StyleSpinner extends JSpinner{
    
    private Color themeColor = Color.decode("#F5C396");      // Warm Peach/Apricot (#F5C396)
    private Color hoverArrowColor = Color.decode("#E09F67"); // Slightly deeper peach on hover
    private Color containerColor = new Color(255, 252, 248); // Warm soft white background
    private int cornerRadius = 12; 
    
    public StyleSpinner() {
        super(new SpinnerNumberModel(0, 0, 1000, 1)); // Default range 0 to 1000
        setupSpinner();
    }

    public StyleSpinner(SpinnerModel model) {
        super(model);
        setupSpinner();
    }

    private void setupSpinner() {
        setOpaque(false);
        setUI(new CustomSpinnerUI());
        applyRoundedBorder();
        styleEditor();
    }
    
    @Override
    public void setEditor(JComponent editor) {
        super.setEditor(editor);
        styleEditor();
    }
    
    private void styleEditor() {
        JComponent editor = getEditor();
        if (editor instanceof DefaultEditor) {
            JFormattedTextField tf = ((DefaultEditor) editor).getTextField();
            tf.setEditable(false); 
            tf.setFocusable(false);
            tf.setOpaque(true);
            tf.setBackground(new Color(255, 252, 248));
            tf.setBorder(BorderFactory.createEmptyBorder());
            tf.setFont(new Font("Segoe UI", Font.BOLD, 11));
            tf.setForeground(new Color(65, 52, 42)); // Warm dark brown-gray text
            tf.setHorizontalAlignment(JTextField.CENTER);
        }
    }
    
    private void applyRoundedBorder() {
        setBorder(new AbstractBorder() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // 1. Fill smooth rounded container
                g2.setColor(containerColor);
                g2.fillRoundRect(x, y, width - 1, height - 1, cornerRadius, cornerRadius);

                // 2. Subtle tinted divider line before the up/down arrows
                g2.setColor(new Color(themeColor.getRed(), themeColor.getGreen(), themeColor.getBlue(), 130));
                g2.drawLine(width - 28, y + 4, width - 28, height - 5);

                // 3. Outer rounded border in #F5C396
                g2.setColor(themeColor);
                g2.setStroke(new BasicStroke(1.8f));
                g2.drawRoundRect(x, y, width - 1, height - 1, cornerRadius, cornerRadius);

                g2.dispose();
            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(4, 10, 4, 4);
            }
        });
    }
    
    private class CustomSpinnerUI extends BasicSpinnerUI {
        @Override
        protected Component createNextButton() {
            return createArrowButton(true);
        }

        @Override
        protected Component createPreviousButton() {
            return createArrowButton(false);
        }

        private JButton createArrowButton(boolean isUp) {
            JButton btn = new JButton() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    // Arrow color states
                    if (getModel().isPressed()) {
                        g2.setColor(new Color(185, 115, 55)); // Darker warm tone on click
                    } else if (getModel().isRollover()) {
                        g2.setColor(hoverArrowColor);         // Hover tone
                    } else {
                        g2.setColor(new Color(145, 105, 70)); // Clean warm bronze default arrow
                    }

                    int w = getWidth();
                    int h = getHeight();
                    int size = 5;
                    int cx = w / 2;
                    int cy = h / 2 + (isUp ? 1 : -1);

                    int[] xPoints = {cx - size, cx + size, cx};
                    int[] yPoints = isUp
                            ? new int[]{cy + size / 2, cy + size / 2, cy - size / 2}
                            : new int[]{cy - size / 2, cy - size / 2, cy + size / 2};

                    g2.fillPolygon(xPoints, yPoints, 3);
                    g2.dispose();
                }
            };

            btn.setName(isUp ? "Spinner.nextButton" : "Spinner.previousButton");
            btn.setBorder(BorderFactory.createEmptyBorder());
            btn.setContentAreaFilled(false);
            btn.setFocusPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setPreferredSize(new Dimension(26, 14));

            if (isUp) {
                installNextButtonListeners(btn);
            } else {
                installPreviousButtonListeners(btn);
            }
            return btn;
        }
    }
    
    // Getters & Setters so you can customize in NetBeans Properties window
    public Color getThemeColor() { return themeColor; }
    public void setThemeColor(Color themeColor) { this.themeColor = themeColor; repaint(); }

    public Color getContainerColor() { return containerColor; }
    public void setContainerColor(Color containerColor) { this.containerColor = containerColor; repaint(); }

    public int getCornerRadius() { return cornerRadius; }
    public void setCornerRadius(int cornerRadius) { this.cornerRadius = cornerRadius; repaint(); }
}
