import javax.swing.JOptionPane;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Class SongToTextFile deals with converting songs to text files or 
 * reading text files to import old songs.
 * 
 * @author Annabelle Perng
 * @version 5.30.2018
 */
public class SongToTextFile
{
    /**
     * Constructor for objects of class SongToTextFile
     */
    public SongToTextFile()
    {
        super();
    }

    /**
     * Converts and exports the parameter song into a text file.
     * 
     * @param   s   the song to be converted into a text file
     */
    public void createTextFile(Song s)
    {
        try
        {
            String songName = s.getTitle();
            FileWriter fw = new FileWriter(songName + ".txt");
            
            String tempo = s.getTempo() + "\n";
            
            String textedSong = tempo + s.songToText();
            fw.write(textedSong);   
            fw.close();
        }
        catch (IOException e)
        {
            //e.printStackTrace();
        } 
    }

    /**
     * Reads an existing text file and converts it into a new song.
     * 
     * @precondition    text file must be formatted exactly as the program whishes;
     *                  text file must exist.
     *                  
     * @param   songName    the name of the text file the song was saved to
     * @return  song format of text file songName.txt
     */
    public Song readTextFile(String songName)
    {
        try
        {
            BufferedReader fr = new BufferedReader( new FileReader(songName + ".txt") );

            String line;
            int measureCount=0;
            
            line = fr.readLine();
            int tempo = Integer.parseInt(line);
            
            Song s = new Song(tempo);
            s.setTitle(songName);
            
            //the following three lines are necessary because Song's constructor
            //automatically creates the first measure
            
            line = fr.readLine();
            line = line.substring(line.indexOf(">") + 1);
            s.getMeasure(measureCount).textToMeasure(line);
            
            while ((line = fr.readLine()) != null)
            {
                measureCount++;
                s.addMeasure();

                line = line.substring(line.indexOf(">") + 1);

                s.getMeasure(measureCount).textToMeasure(line);
                //System.out.println("New line added: song length = " + s.getMeasureCount());
                
                //this method relies on Measure's method, textToMeasure.
            }
            
            System.out.println(songName + ".txt has been successfully imported.");
            
            return s;
        }
        catch (IOException e)
        {
            System.out.println();
            System.out.println("Sorry! Requested song is not in the directory.");
            return null;
            
            //e.printStackTrace();
        }
    }
}
