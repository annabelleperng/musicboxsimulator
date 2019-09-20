 import java.util.ArrayList;
import java.util.Scanner;

/**
 * A song is made up of one or more measures.
 * 
 * @author Annabelle Perng
 * @version 5.30.2018
 */
public class Song
{
    private int tempo;
    private ArrayList<Measure>song;
    private int currentMeasure;
    private String title;

    /**
     * Constructor for objects of class Song.
     * 
     * @param   tempo   the tempo to play the song at
     */
    public Song(int tempo)
    {
        super();
        this.tempo=tempo;
        song = new ArrayList<Measure>();
        currentMeasure=-1;
        addMeasure();
        title="Untitled";
    }

    /**
     * Returns the tempo of the song.
     * 
     * @return  the tempo at which the song is played at. Tempos are usually between
     *          30 and 252. If the tempo has not been set by the user, the default 
     *          tempo is 90 BPM.
     */
    public int getTempo()
    {
        return tempo;
    }

    /**
     * Changes the tempo of the song.
     * 
     * @param   newTempo    the new tempo to play the song at
     */
    public void setTempo(int newTempo)
    {
        tempo=newTempo;
    }

    /**
     * Adds one measure to the end of the song. Displays the measure that was just added.
     */
    public void addMeasure()
    {
        Measure m = new Measure();
        song.add(m);
        currentMeasure++;
    }
    
    /**
     * Adds one measure to the middle of the song. Displays the measure that was just added.
     * 
     * @param   measurePosition     the position number the new measure will assume.
     *                              Measure numbers start at 0. To add a new first
     *                              measure, measurePosition would equal 0.
     */
    public void addMeasure(int measurePosition)
    {
        Measure m = new Measure();
        song.add(measurePosition, m);
        currentMeasure++;
    }

    /**
     * Retrieves a measure of the song.
     * 
     * @param   measureNumber   the index of the measure to be retrieved
     * @return  the measure at measureNumber
     */
    public Measure getMeasure(int measureNumber)
    {
        return song.get(measureNumber);
    }

    /**
     * Removes one measure from the song.
     * 
     * @param   measureNumber   the index of the measure to be removed
     */
    public void removeMeasure(int measureNumber)
    {
        if (measureNumber > song.size())
        {
            throw new IllegalArgumentException("Parameter measure is not in the song.");
        }

        song.remove(measureNumber);
        
        currentMeasure--;
        
        if (song.size()==0)
        {
            addMeasure();
        }
        else if (currentMeasure<0)
        {
            currentMeasure=0;
        }
    }

    /**
     * Plays through the whole song.
     */
    public void playSong()
    {   
        System.out.println();
        System.out.println("Playing measure: ");
        System.out.println("\t");
        for (int m=0; m<song.size(); m++)
        {
            System.out.print((m+1) + " >> ");
            song.get(m).playMeasure(tempo);
            currentMeasure=m;
        }
        System.out.print("DONE.");
    }
    
    /**
     * Plays through the song from measure start to the end of the song.
     * 
     * @param   start   the measure to start playback from
     * @precondition    1 ≤ start ≤ last measure
     */
    public void playSong(int start)
    {   
        System.out.println();
        System.out.println("Playing measure: ");
        System.out.println("\t");
        for (int m=start-1; m<song.size(); m++)
        {
            System.out.print((m+1) + " >> ");
            song.get(m).playMeasure(tempo);
            currentMeasure=m;
        }
        System.out.print("DONE.");
    }

    /**
     * Plays through the whole song (using a different sound font from
     * the default music box note).
     * 
     * @param   vstName     the designated name for the vst, which becomes a suffix in
     *                      the audio file name
     */
    public void playSongWithVST(String vstName)
    {   
        for (int m=0; m<song.size(); m++)
        {
            song.get(m).playMeasureWithVST(tempo, vstName);
        }
    }

    /**
     * Sets a title for the song. Songs do not have to have titles.
     * 
     * @param   songTitle   the new name for the song
     */
    public void setTitle(String songTitle)
    {
        this.title = songTitle;
    }

    /**
     * Retrieves the song's title.
     * 
     * @return  the String form of the song's title - if the song is unnamed,
     *          an empty String will be returned
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Returns the current length of the song, otherwise known as the
     * number of measures in the song.
     * 
     * @return  the length of the song
     */
    public int getMeasureCount()
    {
        return song.size();
    }
    
    /**
     * Returns the measure which is currently being displayed to the user.
     * (If not explicitly set, this defaults to the last measure in the song.)
     * 
     * @return  the measure number of the measure being displayed
     */
    public int getCurrentMeasure()
    {
        return currentMeasure;
    }

    /**
     * Sets the variable currentMeasure so that the user can edit a
     * measure of their choice.
     * 
     * @param   selectedMeasure     the measure the user wants to edit
     * @precondition                selectedMeasure < song's measure count
     */
    public void setCurrentMeasure(int selectedMeasure)
    {
        currentMeasure = selectedMeasure;
    }
    
    /**
     * Converts the whole song to a String format. The String will be in the format:
     * <Measure number>(Rhythmic beat of the measure)[notes inside chord at given beat].
     * 
     * @return  String format of the song
     */
    public String songToText()
    {
        String textedSong="";
        for (int i=0; i<song.size(); i++)
        {
            textedSong += "<" + i + ">";
            textedSong += song.get(i).measureToText();
            textedSong += "\n";
        }
        return textedSong;
    }

    /**
     * Prints out information about the song, including the title, the tempo, and 
     * the number of measures in the song.
     */
    public void printStatistics()
    {
        System.out.println();
        System.out.println("Title:\t" + title);
        System.out.println("Tempo:\t" + tempo + " BPM");
        System.out.println("Length:\t" + getMeasureCount() + " measure(s)");
    }

    //+++ next measure and previous message button
    //+++ measure display/choose menu 
    //+++ how play handles scrolling
}
