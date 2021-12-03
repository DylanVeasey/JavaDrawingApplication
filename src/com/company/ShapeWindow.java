package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class ShapeWindow extends JFrame
{
    private static class Canvas extends JPanel
    {
        private final ArrayList<Shape> shapes;

        public Canvas()
        {
            super(null);

            shapes = new ArrayList<>();

            Toolkit tk = Toolkit.getDefaultToolkit();
            Dimension d = tk.getScreenSize();
            int size = Math.min(d.width, d.height);

            setSize(d.width, d.height);
            setPreferredSize(new Dimension(size / 2, size / 2));
            setBackground(Color.WHITE);
        }

        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);

            for (Shape shape : shapes)
            {
                shape.paint(g);
            }
        }

        public void add(Shape shape)
        {
            shapes.add(shape);
            repaint();
        }

        public void clearShapes()
        {
            shapes.clear();
            repaint();
        }

        public Shape getShape(String name)
        {
            for (Shape shape : shapes)
            {
                if (name.equals(shape.getName()))
                {
                    return shape;
                }
            }

            return null;
        }
    }

    private final Canvas canvas;

    public ShapeWindow()
    {
        super("JShapes");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        canvas = new Canvas();

        getContentPane().add(canvas, "Center");

        pack();
        setVisible(true);
    }

    public void quit()
    {
        System.exit(0);
    }

    public void add(Shape shape)
    {
        canvas.add(shape);
    }

    public void clearShapes()
    {
        canvas.clearShapes();
    }

    public Shape getShape(String name)
    {
        return canvas.getShape(name);
    }
}
