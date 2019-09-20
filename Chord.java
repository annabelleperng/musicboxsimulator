import java.util.ArrayList;

/**
 * A chord is a stack of notes. All chords take up the same rhythmic amount of space.
 * 
 * @author Annabelle Perng
 * @version 3.27.2018
 */
public class Chord
{
    private ArrayList<Note>chord;
    private int sixteenthPlace; //GOES FROM 0 TO 15!!! 
    private AudioPlayerExample1 a;
    
    /**
     * Constructor for objects of class Chord.
     * 
     * @param   sixteenthPlace  an integer between 0 and 15 which signifies
     *                          the rhythmic location of the chord in the measure
     */
    public Chord(int sixteenthPlace)
    {
        this.sixteenthPlace = sixteenthPlace;
        chord = new ArrayList<Note>();
        a = new AudioPlayerExample1();
    }
    
    /**
     * Modifies the chord according to the user's input.
     * 
     * @param   n   the note to be added to or removed from the chord
     */
    public void modifyChord(Note n)
    {
        int indexInChord = findInChord(n);
        
        if (indexInChord==-1)
        {
            chord.add(n); //adds a new note to the chord
        }
        else 
        {
            chord.remove(indexInChord); //removes an existing note from the chord
        }
    }
    
    /**
     * Checks if a given note is already in 'this' chord.
     * 
     * @param   n   the note to be checked
     * 
     * @return  the index within the chord that Note n occupies, if it is in the chord;
     *          otherwise, -1
     */
    public int findInChord(Note n)
    {
        for (int i=0; i<chord.size(); i++)
        {
            if ( n.compareTo(chord.get(i)) == 0)
            {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Returns an integer between 0 and 15 which signifies the rhythmic location
     * of the chord in the measure.
     * 
     * @return  the note's rhythmic location
     */
    public int getSixteenthPlace()
    {
        return sixteenthPlace;
    }
    
    /**
     * Returns an ArrayList containing all the notes in the chord.
     * 
     * @return  all the notes within the chord
     */
    public ArrayList<Note>returnChord()
    {
        return chord;
    }
    
    /**
     * Returns the length of the chord.
     * 
     * @return  the number of notes in the chord
     */
    public int getChordLength()
    {
        return chord.size();
    }
    
    /**
     * Converts the chord to a String format. Starts and ends the String with
     * square brackets, then writes each note value in the chord, separated
     * by periods. For example: "[24.22.19]" is a chord with C-1, E-1, and G-1.
     * 
     * @return  String format of the chord
     */
    public String chordToText()
    {
        String textedChord = "[";
        for (int i=0; i<chord.size(); i++)
        {
            textedChord += chord.get(i).getNoteValue();
            if (i < chord.size()-1)
            {
                textedChord += ".";
            }
        }
        textedChord += "]";
        return textedChord;
    }
    
    /**
     * Plays all notes in 'this' chord at the same time.
     */
    public void playChord()
    {
        //AudioPlayerExample1 a = new AudioPlayerExample1();
            //it's now an instance variable, initialized in the constructor
        
        for (int i=0; i<chord.size(); i++)
        {
            a.playNote(chord.get(i).getNoteName());
        }
    }
    
    /**
     * Plays all notes in 'this' chord at the same time. Uses a different
     * sound font from the default music box note.
     * 
     * @param   vstName     the designated name for the vst, which becomes a suffix in
     *                      the audio file name
     */
    public void playChordWithVST(String vstName)
    {
        //AudioPlayerExample1 a = new AudioPlayerExample1();
            //it's now an instance variable, initialized in the constructor
        
        String vst = vstName; 
        
        for (int i=0; i<chord.size(); i++)
        {
            a.playNote(chord.get(i).getNoteName() + vst);
        }
    }
}
