package com.company;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

abstract class Shape
{
    private final String name;

    public Shape(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    abstract public void paint(Graphics g);
}

class Line extends Shape
{
    private final int x1, y1, x2, y2;

    public Line(String name, int x1, int y1, int x2, int y2)
    {
        super(name);

        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public void paint(Graphics g)
    {
        g.drawLine(x1, y1, x2, y2);
    }
}

class regularPolygon extends Shape
{
    private final int x1,y1,sides, size;

    private final ArrayList<Integer> xValues = new ArrayList<>();
    private final ArrayList<Integer> yValues = new ArrayList<>();


    public regularPolygon(String name, int x1, int y1, int sides, int size) {
        super(name);

        this.x1 = x1;
        this.y1 = y1;
        this.sides = sides;
        this.size = size;
        calculatePoint(x1,y1,sides,size);
    }

    private void calculatePoint(int x1, int y1, int sides, int size ){
        double angle = (2 * Math.PI / sides);
        for (int i = 0; i < sides + 1; i++)
        {
            xValues.add((int) (x1 + size * Math.sin(i * angle)));
            yValues.add((int) (y1 + size * Math.cos(i * angle)));
        }


    }


    public void paint(Graphics g) {
        g.drawPolygon(xValues.stream().mapToInt(Integer::intValue).toArray(), yValues.stream().mapToInt(Integer::intValue).toArray(), sides);
    }
}


class smiley extends Shape
{
    private final int x1, y1, size;

    public smiley(String name, int x1, int y1, int size) {
        super(name);

        this.x1 = x1;
        this.y1 = y1;
        this.size = size;
    }

    public void paint(Graphics g) {
        g.drawOval(x1 , y1, size, size);
        g.drawRect(x1 + (size / 4),  y1 + (size / 4), size/10,size/10);
        g.drawRect(x1 + (size / 2), y1 + (size / 4), size/10,size/10);
        g.drawLine(x1 + (size / 4),y1 + (size / 2) , x1 + (size) ,y1 + (size / 2));
    }
}



class Rectangle extends Shape
{
    private int x1, y1, width, height;

    public Rectangle(String name, int x1, int y1, int width, int height ) {
        super(name);

        this.x1 = x1;
        this.y1 = y1;
        this.width = width;
        this.height = height;
    }

    public Rectangle(String name) {
        super(name);
    }

    public void paint(Graphics g) {
        g.drawRect(x1, y1, width, height);
    }
}

class Square extends Rectangle
{

    private final int x1, y1, size;

    public Square(String name, int x1, int y1, int size) {
        super(name);

        this.x1 = x1;
        this.y1 = y1;
        this.size = size;
    }

    public void paint(Graphics g) {
        g.drawRect(x1, y1, size, size);
    }
}



public class Main
{
    public static void main(String[] args)
    {
        ShapeWindow window   = new ShapeWindow();
        Scanner     scanner  = new Scanner(System.in);
        boolean     finished = false;

        displayHelpMessage();

        do
        {
            System.out.print(">> ");
            System.out.flush();

            String command = scanner.nextLine();

            String[] token = command.trim().split(" +");

            switch (token[0])
            {
                case "clear":
                {
                    window.clearShapes();

                    break;
                }
                case "describe":
                {
                    String name = token[1];

                    Shape shape = window.getShape(name);

                    if (shape == null)
                    {
                        System.out.printf("%nError: Unknown shape '%s'%n%n",
                                name);
                    }
                    else
                    {
                        System.out.printf("%n%s%n%n", shape.toString());
                    }

                    break;
                }
                case "help":
                {
                    displayHelpMessage();

                    break;
                }
                case "line":
                {
                    String name = token[1];
                    int x1      = Integer.parseInt(token[2]);
                    int y1      = Integer.parseInt(token[3]);
                    int x2      = Integer.parseInt(token[4]);
                    int y2      = Integer.parseInt(token[5]);

                    window.add(new Line(name, x1, y1, x2, y2));

                    break;
                }
                case "rectangle":
                {
                    String name = token[1];
                    int x1 = Integer.parseInt(token[2]);
                    int y1 = Integer.parseInt(token[3]);
                    int width = Integer.parseInt(token[4]);
                    int height = Integer.parseInt(token[5]);

                    window.add(new Rectangle(name, x1, y1, width, height));

                    break;
                }
                case "square":
                {
                    String name = token[1];
                    int x1 = Integer.parseInt(token[2]);
                    int y1 = Integer.parseInt(token[3]);
                    int size = Integer.parseInt(token[4]);

                    window.add(new Square(name, x1, y1, size));

                    break;
                }
                case "polygon": {
                    String name = token[1];

                    int sides = Integer.parseInt(token[2]);
                    int x1 = Integer.parseInt(token[3]);
                    int y1 = Integer.parseInt(token[4]);
                    int size = Integer.parseInt(token[5]);

                    window.add(new regularPolygon(name, x1, y1, sides, size));

                    break;
                }
                case "smiley":{
                    String name = token[1];

                    int x1 = Integer.parseInt(token[2]);
                    int y1 = Integer.parseInt(token[3]);
                    int size = Integer.parseInt(token[4]);

                    window.add(new smiley(name, x1, y1, size));

                    break;
                }
                case "redraw":
                {
                    window.repaint();

                    break;
                }
                case "resize":
                {
                    int width  = Integer.parseInt(token[1]);
                    int height = Integer.parseInt(token[2]);

                    window.setSize(new Dimension(width, height));

                    window.repaint();

                    break;
                }
                case "quit":
                {
                    finished = true;

                    break;
                }
                default:
                {
                    System.out.printf("%nError: Unrecognised command '%s'%n%n",
                            command);
                }
            }
        }
        while (!finished);

        window.quit();
    }

    public static void displayHelpMessage()
    {
        System.out.println("\n"
                + "JShapes - A Java Shape-Drawing Program\n"
                + "======================================\n"
                + "\n"
                + "Commands available:\n"
                + "\n"
                + "clear                                - delete all shapes\n"
                + "describe name                        - display information about a\n"
                + "                                       named shape\n"
                + "help                                 - display this help message\n"
                + "line name x1 y1 x2 y2                - add a line\n"
                + "rectangle name x1 y1 width height    - draw a rectangle\n"
                + "square name x1 y1 size               - draw a square\n"
                + "polygon name sides x1 y1 size        - draw a regular polygon\n"
                + "smiley name x1 y1 size               - draw a smiley face\n"
                + "redraw                               - redraw the canvas\n"
                + "resize width height                  - resize the canvas\n"
                + "quit                                 - exit the program\n"
                + "\n");
    }
}
