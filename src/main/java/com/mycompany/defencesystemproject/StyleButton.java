package com.mycompany.defencesystemproject;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 *
 * @author KCC
 */
public class StyleButton extends JButton {

    private int radius = 12;
    private Color topColor = new Color(116, 134, 62);      // Lighter olive top
    private Color bottomColor = new Color(75, 92, 34);     // Darker olive bottom
    private Color hoverTopColor = new Color(132, 152, 72); // Brighter on hover
    private Color hoverBottomColor = new Color(88, 108, 40);

    private Color disabledTopColor = new Color(158, 163, 148);
    private Color disabledBottomColor = new Color(132, 137, 123);

    public StyleButton() {
        super("Button");
        setupStyle();
    }

    public StyleButton(String text) {
        super(text);
        setupStyle();
    }

    private void setupStyle() {
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
        setFont(new Font("Segoe UI", Font.BOLD, 11));
        setForeground(new Color(240, 244, 232));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBorder(BorderFactory.createEmptyBorder(8, 24, 11, 24));
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        // Switch cursor between hand and normal arrow when disabled
        setCursor(new Cursor(enabled ? Cursor.HAND_CURSOR : Cursor.DEFAULT_CURSOR));
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        boolean enabled = isEnabled();

        int shadowOffset = enabled ? 3 : 0; // Remove drop shadow when disabled (flat look)
        int pressOffset = (enabled && getModel().isPressed()) ? 2 : 0;

        int btnW = w - 2;
        int btnH = h - 4;

        if (!enabled) {
            // Apply soft transparency/faded effect for disabled state
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.65f));
        }

        // 1. Draw soft drop shadow only when enabled
        if (enabled) {
            g2.setColor(new Color(0, 0, 0, 75));
            g2.fillRoundRect(1, shadowOffset + 1, btnW, btnH, radius, radius);
        }

        // 2. Choose gradient colors based on enabled, hover, or press state
        Color c1;
        Color c2;
        if (!enabled) {
            c1 = disabledTopColor;
            c2 = disabledBottomColor;
        } else if (getModel().isPressed()) {
            c1 = bottomColor;
            c2 = topColor;
        } else if (getModel().isRollover()) {
            c1 = hoverTopColor;
            c2 = hoverBottomColor;
        } else {
            c1 = topColor;
            c2 = bottomColor;
        }

        // 3. Fill vertical gradient shading
        GradientPaint gradient = new GradientPaint(0, pressOffset, c1, 0, btnH + pressOffset, c2);
        g2.setPaint(gradient);
        g2.fillRoundRect(1, pressOffset, btnW, btnH, radius, radius);

        // 4. Draw glossy top inner highlight only when enabled
        if (enabled) {
            g2.setColor(new Color(255, 255, 255, 65));
            g2.setStroke(new BasicStroke(1.2f));
            g2.drawRoundRect(2, pressOffset + 1, btnW - 2, btnH - 2, radius - 2, radius - 2);
        }

        // 5. Draw outer border (softer gray when disabled)
        g2.setColor(enabled ? new Color(85, 96, 65) : new Color(145, 148, 140));
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawRoundRect(1, pressOffset, btnW, btnH, radius, radius);

        g2.dispose();

        // 6. Paint text (faded when disabled, normal off-white when enabled)
        Graphics2D gText = (Graphics2D) g.create();
        if (!enabled) {
            gText.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.55f));
        }
        if (pressOffset > 0) {
            gText.translate(0, pressOffset);
        }
        super.paintComponent(gText);
        gText.dispose();
    }

    // Getters & Setters for NetBeans Properties window
    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
        repaint();
    }

    public Color getTopColor() {
        return topColor;
    }

    public void setTopColor(Color topColor) {
        this.topColor = topColor;
        repaint();
    }

    public Color getBottomColor() {
        return bottomColor;
    }

    public void setBottomColor(Color bottomColor) {
        this.bottomColor = bottomColor;
        repaint();
    }
}
