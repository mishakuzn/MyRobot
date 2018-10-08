package com.company;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.swing.*;

import com.fazecast.jSerialComm.*;


public class Main {

    static ArrayList<DataPoint> dataList = new ArrayList<>(); //might not need the static part..

    private JFrame frame = new JFrame();

    public static void main(String[] args)
    {
        Main main = new Main();

    }

    public Main()
    {
        frame();

        SerialPort[] ports = SerialPort.getCommPorts();
        System.out.println("Select a port:");
        int i = 1;
        for (SerialPort port : ports)
            System.out.println(i++ + ": " + port.getSystemPortName());
        int chosenPort;
        if (ports.length == 1) {
            chosenPort = 1;
        } else {
            Scanner s = new Scanner(System.in);
            chosenPort = s.nextInt();
        }

        SerialPort serialPort = ports[chosenPort - 1];
        if (serialPort.openPort())
            System.out.println("Port opened successfully.");
        else {
            System.out.println("Unable to open the port.");
            return;
        }

        System.out.println();

        //serialPort.setComPortParameters(9600, 8, 1, SerialPort.NO_PARITY);

//        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 0, 0);
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);

        Scanner data = new Scanner(serialPort.getInputStream());

        int dist = 0;
        int pos = 0;

        int c = 1;

        DataPoint point;

        StringTokenizer st;

        while (data.hasNextLine()) {
            try {
                st  = new StringTokenizer(data.nextLine());
                pos = Integer.parseInt(st.nextToken());
                dist = Integer.parseInt(st.nextToken());
            } catch (Exception e) {
                e.printStackTrace();
            }
            // slider.setValue(value);
            System.out.println((c++) + " Distance = " + dist + " " + "Position = " + pos);
            //I essentially have polar coords (r, theta)... so I can just convert the Distance and Position to
            //an(x,y) coord and plot it..

            point = new DataPoint(dist,pos);

            dataList.add(point);

            frame.repaint();

        }

    }

    private void frame()
    {
        frame.setLocation(50, 50);
        frame.setBackground(Color.white);
        //frame.setUndecorated(true);
        frame.setVisible(true);
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //frame.setSize(frame.getWidth(), frame.getHeight());
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel()
        {
            public void paintComponent(Graphics g)
            {
                render(g);
            }
        };

        frame.add(panel);
        //frame.repaint();
    }



    public void render(Graphics g)
    {
        //super.paint(g);
        g.setColor(Color.black);
        for(DataPoint point : dataList)
        {
            point.drawPoint(g);
        }
    }


}