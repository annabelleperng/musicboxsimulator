
/**
 * A note plays a single frequency. All notes in a song have the same duration. Diatonic notes
 * are the same as regular notes, but diatonic notes must have no sharps or flats.
 * A music box with only diatonic notes is a 15-note music box (as opposed to a normal,
 * 30-note music box).
 * 
 * Additional comment: I ended up not using this class.
 * 
 * @author Annabelle Perng
 * @version 3.29.2018
 */
public class NoteDiatonic extends Note
{
    /**
     * Constructor for objects of class Note.
     * 
     * @param   noteValue   an integer that shows the relative placement of the diatonic note.
     */
    public NoteDiatonic(int noteValue)
    {
        super(noteValue);
        if (!isDiatonicNote(noteValue))
        {
            throw new IllegalArgumentException("Given note value does not correspond " +
                                               "with a diatonic note.");
        }
    }
    
    /**
     * Checks if the note value given is a valid diatonic note. Diatonic notes have no 
     * sharps and flats, and their note names range from "C-1" to "C-3."
     * 
     * @param   value   the 'value' of the note, which shows where the note stands
     *                  relative to the other notes.
     *                  
     * @return  true    if the value corresponds with a diatonic note; otherwise,
     *          false
     */
    public boolean isDiatonicNote(int value)
    {
        if (value<=1 || value>=25)
        {
            return false; // out of range
        }
        else if (value==4 || value==6 || value==8 || value==11 || value==13
                || value==16 || value==18 || value==20)
        {
            return false;
        }
        return true;
    }
}
