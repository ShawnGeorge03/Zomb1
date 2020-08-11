/*
 * This application creates a simple Pong game
 * Shawn SG.
 * ICS3U4U - 01
 * 1/18/2018
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
/*
 * The MainMenu class creates a Window and outputs different choices for
 * the user to select like 1 Player, 2 Player, Controls, Quit
 */
public class MenuGUI extends JFrame {

	//Creates a universal version identifier for a Serializable class
	private static final long serialVersionUID = 1L;

	//Declares variables
	int screenWidth = 600;
	int screenHeight = 400;
	
	int relativeWidth = 100;
	int relativeHeight = 40;

	//Creates JButtons
	JButton onePlayer, quit, twoPlayer, controls;	
		
	//Creates and outputs a Window
	public MenuGUI() {
		
		addGUI();
		addActions();
		
		//Returns the contentPane object for this frame and sets layout
		getContentPane().setLayout(null);
		//Returns the contentPane object for this frame and sets background to black
		getContentPane().setBackground(new Color(0, 0, 0)); 
		
		//Sets the location(x, y) and size(relativeWidth, relativeHeight ) for each buttons
		onePlayer.setBounds((screenWidth - relativeWidth)/2, 100, relativeWidth, relativeHeight);
		twoPlayer.setBounds((screenWidth - relativeWidth)/2, 150, relativeWidth , relativeHeight);
		controls.setBounds((screenWidth - relativeWidth)/2, 200, relativeWidth, relativeHeight);
		quit.setBounds((screenWidth - relativeWidth)/2, 250, relativeWidth, relativeHeight);

		//Returns the contentPane object for this frame and adds the onePlayer  button
		getContentPane().add(onePlayer);
		getContentPane().add(twoPlayer);
		getContentPane().add(controls);
		getContentPane().add(quit);

		//Arranges everything
		pack();
		//Sets visible to true
		setVisible(true);
		//Sets the size of the JFrame to screenWidth and screenHeight 
		setSize(screenWidth, screenHeight );
		//Sets the title of the JFrame to say Pong
		setTitle("Pong");
		//Set the default close operation to exit the application using the System exit method
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Doesn’t allow user to resize the JFrame
		setResizable(true);
		//Sets the Location of the JFrame to the center of the screen
		setLocationRelativeTo(null);
			
	}
	
	//Initializes the components
	private void addGUI() {
		onePlayer = new JButton("Play");
		twoPlayer = new JButton("2 Players");		
		controls = new JButton("Controls");
		quit = new JButton("Quit");
	
	}
	
	//Adds an ActionListener to all the JButtons
	private void addActions() {
		// Adds an ActionListener to the JButton
		onePlayer.addActionListener(new ActionListener() {
			// This method allows the user to chooses 1 Player
			public void actionPerformed(ActionEvent arg0) {
				//Closes this JFrame
				dispose();
				//Calls on to the other Class
				try {
					Game game = new Game();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
			
		} );
		// Adds an ActionListener to the JButton
		twoPlayer.addActionListener(new ActionListener() {
			// This method allows the user to chooses 2 Player
			public void actionPerformed(ActionEvent arg0) {
				//Closes this JFrame
				dispose();
				//Calls on to the other Class
				//Game game = new Game();

			}
			
		} );
		// Adds an ActionListener to the JButton
		controls.addActionListener(new ActionListener() {
			// This method allows the user to find out about the controls
			public void actionPerformed(ActionEvent arg0) {
				//Closes this JFrame
				dispose();
				//Calls onto the other class
			}
			
		} );
		// Adds an ActionListener to the JButton
		quit.addActionListener(new ActionListener(){
			//Allows user to close the application
			public void actionPerformed(ActionEvent arg0) {
				//Closes the application
				System.exit(0);
			}
		});
		
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g; 
		
		Font f = new Font("Tahoma", Font.BOLD, 36);
		Color white = new Color(255, 255, 255);
		// Set color and font properties
		g2.setFont(f);
		g2.setColor(white);
		// Output message
		g2.drawString("PONG", 250, 100); 

		
	}
	
	public static void main(String[]args) {
		//Calls onto MainMenu class
		new MenuGUI();
	}
	
}
