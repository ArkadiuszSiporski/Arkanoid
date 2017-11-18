package singlePlayer;

import java.io.File;
import java.util.Formatter;
import java.util.Scanner;
import java.util.Vector;

/**
 * Class responsible for saving player's score after they finish one of the single player levels.
 *
 */
public class SaveScores implements Runnable
{
	private Scanner sc;
	private int newScore;
	private Vector<String> output;
	private String nick;
	private String path;
	
	/**
	 * Initializes score, nick to be saved in file and the path to the file.
	 * @param newScore player's score
	 * @param nick player's nick
	 */
	public SaveScores(int newScore, String nick)
	{
		this.newScore = newScore;

		if(nick ==null ||nick.isEmpty())
			this.nick = "Anonymous";
		else if(nick.length() > 10)
			this.nick = nick.substring(0, 10);
		else
			this.nick = nick;
		this.output= new Vector<String>();
		path = (new File("src/resources/test.txt")).getAbsolutePath();
		
	}
	/**
	 * Invokes 2 methods which in conjunction save up to top 10 scored in the file.
	 */
	@Override
	public void run()
	{
		sort();
		save();
	}
	/**
	 * Sorts current high scores and the new score.
	 */
	private void sort()
	{
		Vector<Integer> scores = new Vector<Integer>();
		Vector<String> input = new Vector<String>();
		try
		{
			
			sc = new Scanner(new File(path));
			while(sc.hasNext())
			{
				scores.add(sc.nextInt());
				input.addElement(sc.next());
			}
			sc.close();
		}
		catch(Exception e)
		{
			System.out.printf("There is no file at %s\n", path);
		}
		int i = 0;
		while(i < scores.size() && newScore <= scores.get(i)  )
		{
			i++;
		}
		if(i < 10)
		{
			scores.add(i, newScore);
			input.add(i, nick);
		}
		for(int j = 0; j < scores.size(); j++)
		{
				output.add(	String.format("%d %s",scores.get(j), input.get(j))	);
		}		
	}
	/**
	 * Writes sorted high scores back to the file.
	 */
	private void save()
	{
		try
		{
			Formatter f = new Formatter(path);
			int i = 0;
			while( i < output.size() && i < 10)
					f.format("%s%n", output.get(i++));
			f.close();
		}
		catch(Exception e)
		{
			System.out.printf("There is no file at %s\n", path);
		}
	}
}
