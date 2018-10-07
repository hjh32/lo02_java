package Graphic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JFrame;

import mainPackage.Game;

/**
 * Manages the buttons displayed and used by the user to answer questions an play through the graphic interface.
 * @author Benjamin Héroult
 * 
 */
class Buttons extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	/**
	 * True if it is a yes/no question
	 */
	private boolean questionType;
	
	/**
	 * List collection of buttons, used for a question with more than one answer possibility.
	 */
	private ArrayList <JButton> buttonsAnswer = new ArrayList<>();	// first has a value of 1
	
	/**
	 * First button for a yes/no question.
	 */
	private JButton but0;
	/**
	 * Second button for a yes/no question.
	 */
	private JButton but1;
	/**
	 * Quit button.
	 */
	private JButton quit;
	/**
	 * Pick button.
	 */
	private JButton pick;
	
	/**
	 * Contains the answer of the user.
	 */
	protected static int asw;	//answer
	
	/**
	 * First version of the constructor, used for a yes/no question.
	 */
	protected Buttons() {	//yes or no
		asw = -1;
		this.questionType = true;
		this.askYesNo();
		while(Buttons.asw == -1) {	//sleep and wake up until an answer is entered
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		MyWindow.answer.removeAll();	//delete the buttons
	}
	
	/**
	 * Second version of the constructor, used for a multiple answers questions.
	 * @param nb_buttons
	 * the number of buttons to display
	 */
	protected Buttons (int nb_buttons) {	//multiple answers
		asw = -1;
		this.questionType = false;
		//this.panel = panel;
		this.askSomething(nb_buttons);
		while(Buttons.asw == -1) {	//sleep and wake up until an answer is entered
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
			}
		}
		MyWindow.answer.removeAll();	//delete the buttons
	}
	
	/**
	 * Creates and displays buttons and listeners associated for a yes/no question.
	 */
	private void askYesNo() {
		this.but0 = new JButton("NON");
		MyWindow.answer.add(but0);
		this.but1 = new JButton("OUI");
		MyWindow.answer.add(but1);
		MyWindow.answer.setVisible(true);
		MyWindow.getFrame().getContentPane().repaint();
		but0.addActionListener(this);
		but1.addActionListener(this);
		System.out.println("Boutons affichés");
	}
	
	/**
	 * Creates and displays buttons and listeners associated for a question with more than one answer possible.
	 * @param nb_buttons
	 * the number of buttons to display.
	 */
	private void askSomething(int nb_buttons) {
		JButton but;
		for (int i=1 ; i<=nb_buttons ; i++) {
			String value = Integer.toString(i);
			but = new JButton(value);
			MyWindow.answer.add(but);
			but.addActionListener(this);
			this.buttonsAnswer.add(but);
		}
		
		if(Game.hasBegun == true) {
			this.quit = new JButton("QUITTER");
			MyWindow.answer.add(quit);
			quit.addActionListener(this);
			
			this.pick = new JButton("PIOCHER");
			MyWindow.answer.add(pick);
			pick.addActionListener(this);
		}
		
		MyWindow.answer.setVisible(true);
		MyWindow.getFrame().getContentPane().repaint();
	}
	
	/**
	 * Action performed when an event happened on a button listener.
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		if (questionType == true) {	//yes/no question
			if (source == but0)
				Buttons.asw = 0;
			else if (source == but1)
				Buttons.asw = 1;
		}
		else {
			boolean found = false;
			Iterator <JButton> it = this.buttonsAnswer.iterator();
			int i = 0;
			while(found == false && it.hasNext()) {	//find the answer
				i++;
				JButton current = it.next();
				if(current == source) {
					found = true;
				}
			}
			
			if(source == quit) {
				i = 66;
			}
			else if(source == pick) {
				i = 0;
			}
			
			Buttons.asw = i;	//memorizes the answer
		}
	}

}
