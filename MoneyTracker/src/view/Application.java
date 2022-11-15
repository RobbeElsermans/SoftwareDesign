package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//TODO moet geëxporteerd worden

public class Application extends JFrame implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {

        //Ik ben hier wat aant spelen met deze action listener
        this.e = e;
        System.out.println("An action has occurred!");
        System.out.println(e.getActionCommand().toLowerCase());
        System.out.println(e.getSource().getClass().getCanonicalName());
    }
    ActionEvent e;
    enum TicketType{
        Uniform,
        Variable
    }
    TicketType ticketType = TicketType.Uniform; //Eerst met uniforme tickets werken

    public Application(){

        //Het frame waarin al de elementen gaan plaats vinden
        setTitle("Money Tracker");
        setBounds(300, 90, 900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        Container c = getContentPane();
        c.setLayout(null);


        //Aanmaken van ticket invoer
        //Wordt a.d.h.v. ticket type aangepast
        JLabel ticketTypeLabel = new JLabel("Type of Tickets:");
        ticketTypeLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        ticketTypeLabel.setSize(200, 20);
        ticketTypeLabel.setLocation(0, 0);
        c.add(ticketTypeLabel);

        JComboBox ticketTypeSelection = new JComboBox(new String[]{"ticket1", "ticket2", "ticket3"});
        ticketTypeSelection.setFont(new Font("Arial", Font.PLAIN, 15));
        ticketTypeSelection.setSize(100, 20);
        ticketTypeSelection.setLocation(200, 0);
        ticketTypeSelection.addActionListener(this);
        c.add(ticketTypeSelection);

        JLabel fromPersonLabel = new JLabel("From Person:");
        fromPersonLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        fromPersonLabel.setSize(200, 20);
        fromPersonLabel.setLocation(0, 20);
        c.add(fromPersonLabel);

        JComboBox fromPersonSelection = new JComboBox(new String[]{"person1", "person2", "person3"});
        fromPersonSelection.setFont(new Font("Arial", Font.PLAIN, 15));
        fromPersonSelection.setSize(100, 20);
        fromPersonSelection.setLocation(200, 20);
        fromPersonSelection.addActionListener(this);
        c.add(fromPersonSelection);

        JLabel forPersonLabel = new JLabel("For Person:");
        forPersonLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        forPersonLabel.setSize(200, 20);
        forPersonLabel.setLocation(0, 40);
        c.add(forPersonLabel);

        JComboBox forPersonSelection = new JComboBox(new String[]{"person1", "person2", "person3"});
        forPersonSelection.setFont(new Font("Arial", Font.PLAIN, 15));
        forPersonSelection.setSize(100, 20);
        forPersonSelection.setLocation(200, 40);
        forPersonSelection.addActionListener(this);
        c.add(forPersonSelection);
        //Eerst kijken wat de gebruiker selecteert om daarna nieuwe elementen toe te voegen aan het frame -> Doen we later


/*
        JLabel ticketLabel = new JLabel("Ticket:");
        JLabel fromPersonLabel = new JLabel("From person:");
        JLabel forPersonLabel = new JLabel("For person:");

 */
/*
        switch(ticketType){
            case Uniform:{
                JLabel totAmount = new JLabel("Total Amount:");

                //Do al the logic inside this for Uniform
                break;
            }
            case Variable:{
                JLabel sepAmount = new JLabel("Amount:");

                //Do al the logic inside this for Variable
                break;
            }
        }

 */
        //Zie mockup map in root folder.

        setVisible(true);
    }
}
