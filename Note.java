
/**
 * A note plays a single frequency. All notes in a song have the same duration.
 * Some notes can be chromatic. A regular music box has 30 notes.
 * 
 * @author Annabelle Perng
 * @version 3.27.2018
 */
public class Note
{
    private String noteName;
    private int noteValue;
    
    /**
     * Constructor for objects of class Note.
     * 
     * @param   noteValue   an integer between 0 and 29, showing the relative placement of the note;
     *                      lower values correspond with higher pitches.
     */
    public Note(int noteValue)
    {
        super();
        this.noteName=assignNoteName(noteValue);
        this.noteValue=noteValue;
    }
    
    /**
     * Given a set note value, assigns a corresponding note name.
     * Note names range from E-3 (highest) to C-0 (lowest). All note values are
     * between 0 and 29, an all note names are exactly 3 characters long.
     * 
     * @param   value   the 'value' of the note, which shows where the note stands
     *                  relative to the other notes; should be between 0 and 29
     *                  
     * @return  the formal name of the note, which is a string 3 characters long
     */
    public String assignNoteName(int value)
    {
        if (value==0)
        {
            return "E-3";
        }
        else if (value==1)
        {
            return "D-3";
        }
        else if (value==2)
        {
            return "C-3";
        }
        else if (value==3)
        {
            return "B-2";
        }
        else if (value==4)
        {
            return "A#2";
        }
        else if (value==5)
        {
            return "A-2";
        }
        else if (value==6)
        {
            return "G#2";
        }
        else if (value==7)
        {
            return "G-2";
        }
        else if (value==8)
        {
            return "F#2";
        }
        else if (value==9)
        {
            return "F-2";
        }
        else if (value==10)
        {
            return "E-2";
        }
        else if (value==11)
        {
            return "D#2";
        }
        else if (value==12)
        {
            return "D-2";
        }
        else if (value==13)
        {
            return "C#2";
        }
        else if (value==14)
        {
            return "C-2";
        }
        else if (value==15)
        {
            return "B-1";
        }
        else if (value==16)
        {
            return "A#1";
        }
        else if (value==17)
        {
            return "A-1";
        }
        else if (value==18)
        {
            return "G#1";
        }
        else if (value==19)
        {
            return "G-1";
        }
        else if (value==20)
        {
            return "F#1";
        }
        else if (value==21)
        {
            return "F-1";
        }
        else if (value==22)
        {
            return "E-1";
        }
        else if (value==23)
        {
            return "D-1";
        }
        else if (value==24)
        {
            return "C-1";
        }
        else if (value==25)
        {
            return "B-0";
        }
        else if (value==26)
        {
            return "A-0";
        }
        else if (value==27)
        {
            return "G-0";
        }
        else if (value==28)
        {
            return "D-0";
        }
        else if (value==29)
        {
            return "C-0";
        }
        else
        {
            throw new IllegalArgumentException("ERROR: Note is off the grid.");
        }
    }
    
    /**
     * Returns the formal name of the note.
     * 
     * @return  the formal name of the note, which is exactly 3 characters long.
     */
    public String getNoteName()
    {
        return noteName;
    }
    
    /**
     * Returns the relative value of the note.
     * 
     * @return  the value of the note, which is between 0 and 29.
     */
    public int getNoteValue()
    {
        return noteValue;
    }
    
    /**
     * Compares 'this' note to the parameter object.
     * 
     * @param   n   the note to be compared to
     * 
     * @return  < 0     if 'this' object has a higher pitch than parameter object;
     *            0     if 'this' object has the same pitch as parameter object;
     *          > 0     if 'this' object has a lower pitch than parameter object.
     */
    public int compareTo(Note n)
    {
        if (n instanceof Note)
        {
            return (this.noteValue - ((Note)n).getNoteValue());
        }
        else
        {
            throw new IllegalArgumentException("ERROR: Parameter object must be a note.");
        }
    }
}
