package DataStructures.Tree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
public class TrieDisplay extends JPanel implements KeyListener{
	private JFrame frame;
	private int size = 30, width = 1000, height = 600;
	private Trie trie;
	private String word;			// Word you are trying to spell printed in large font
	private char likelyChar;  		// Used for single most likely character
	private boolean wordsLoaded;  	// Use this to make sure words are alll loaded before you start typing


	public TrieDisplay(){

		frame=new JFrame("Trie Next");
		frame.setSize(width,height);
		frame.add(this);
		frame.addKeyListener(this);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		// Default Settings
		word = "";
		likelyChar = ' ';
		wordsLoaded = false;

		trie = new Trie();

		File file = new File("DataStructures\\Tree\\words.txt");  
		try{
			try (BufferedReader input = new BufferedReader(new FileReader(file))) {
				String text;
				while((text = input.readLine()) != null){ 
					trie.add(text);
					wordsLoaded = true; 
				}
			}
		} catch(IOException e){
			e.printStackTrace();
		}

		repaint();
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2=(Graphics2D)g;
		g2.setColor(Color.BLACK);
		g2.fillRect(0,0,frame.getWidth(),frame.getHeight());

		g2.setFont(new Font("Arial",Font.BOLD,60));
		g2.setColor(Color.WHITE);
		if (wordsLoaded)
			g2.drawString("Start Typing:",40,100);
		else
			g2.drawString("Loading... please wait",40,100);

		g2.setFont(new Font("Arial",Font.BOLD,100));	// Typed text:  White == valid partial word
		if (trie.contains(word))								//              Red == invalid
			g2.setColor(Color.GREEN);							//				Green == full word
		else
			if (likelyChar == '_')
				g2.setColor(Color.RED);
			else
				g2.setColor(Color.WHITE);
		g2.drawString(word,40,250);
		g2.setFont(new Font("Arial",Font.BOLD,24));

		// Draw String below here for next most likely letter / letters
		// If there ae no possible next letters write something like "no further possibilities"
		if(likelyChar != '_'){
			g2.drawString("Most likely letter: "+likelyChar,40,400);
		}
		else{
			g2.drawString("No further possibilities!",40,400);
		}

	}

	public void keyPressed(KeyEvent e){
		int keyCode = e.getKeyCode();
		
		if (keyCode == 8 && word.length() > 0){
			word = word.substring(0,word.length()-1);
		} else if (keyCode >= KeyEvent.VK_A && keyCode <= KeyEvent.VK_Z)
        	word += KeyEvent.getKeyText(keyCode);
        
		likelyChar = trie.mostLikelyNextChar(word);
		
		repaint();
	}

	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}
	public void actionPerformed(ActionEvent e) {}

	public static void main(String[] args){
		new TrieDisplay();
	}
}
