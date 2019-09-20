
/**
 * A measure is a collection of 16 chords. It represent 4 rhythmic beats.
 * 
 * @author Annabelle Perng
 * @version 3.27.18
 */
public class Measure
{
    private static final int SIXTEENTHS_IN_MEASURE=16;
    private Chord[]chordsInMeasure;

    /**
     * Constructor for objects of class Measure.
     */
    public Measure()
    {
        chordsInMeasure = new Chord[SIXTEENTHS_IN_MEASURE];

        for (int i=0; i<chordsInMeasure.length; i++)
        {
            chordsInMeasure[i] = new Chord(i);
        }
    }

    /**
     * Alters the chord at a given location.
     * 
     * @param   sixteenthPlace  the rhythmic location of the target chord
     * @param   n               the Note to be added to or subtracted from the chord
     */
    public void modifyChord(Note n, int sixteenthPlace)
    {
        if (sixteenthPlace<SIXTEENTHS_IN_MEASURE)
        {
            chordsInMeasure[sixteenthPlace].modifyChord(n);
        }
        else
        {
            throw new IllegalArgumentException("The measure is not long enough to fit that note!");
        }
    }

    /**
     * Plays through 'this' measure at a given tempo.
     * 
     * @param   tempo   the tempo to play the measure at
     */
    public void playMeasure(int tempo)
    {
        double sixteenthLength = (double)60/tempo;
        sixteenthLength *= 250;

        AudioPlayerExample1 a = new AudioPlayerExample1(); //fix if needed
        for (int t=0; t<chordsInMeasure.length; t++)
        {
            //play the audio - TO ADD!
            chordsInMeasure[t].playChord();
            try
            {
                Thread.sleep((long)sixteenthLength);
            }
            catch (InterruptedException e)
            {
                //ignore
            }
        }
    }

    /**
     * Plays through 'this' measure at a given tempo. Uses a different
     * sound font from the default music box note.
     * 
     * @param   tempo       the tempo to play the measure at
     * @param   vstName     the designated name for the vst, which becomes a suffix in
     *                      the audio file name
     * 
     */
    public void playMeasureWithVST(int tempo, String vstName)
    {
        double sixteenthLength = (double)60/tempo;
        sixteenthLength *= 250;

        // AudioPlayerExample1 a = new AudioPlayerExample1();

        for (int t=0; t<chordsInMeasure.length; t++)
        {
            chordsInMeasure[t].playChordWithVST(vstName);
            try
            {
                Thread.sleep((long)sixteenthLength);
            }
            catch (InterruptedException e)
            {
                //ignore
            }
        }
    }

    /**
     * Returns an array consisting of all chords in the measure.
     * 
     * @return  all chords inside the measure
     */
    public Chord[] returnChordsInMeasure()
    {
        return chordsInMeasure;
    }

    /**
     * Converts the measure to a String format. The String will list each chord's
     * sixteenth place followed by the values of all notes the chord. For example:
     * "(0)[24.22.19]" is a chord with C-1, E-1, and G-1 at the beginning of the
     * measure.
     * 
     * @return  String format of the measure
     */
    public String measureToText()
    {
        String textedMeasure="";
        for (int i=0; i<SIXTEENTHS_IN_MEASURE; i++)
        {
            textedMeasure += "(" + i + ")";
            textedMeasure += chordsInMeasure[i].chordToText();
        }
        return textedMeasure;
    }

    /**
     * The opposite to method measureToText: interprets one line of text to edit
     * one method. Text must be formatted as the program calls for it.
     * 
     * @param   s   the text to be read to import the song
     */
    public void textToMeasure(String s)
    {
        String temp = s.substring(s.indexOf("(") + 1);
        int currentSixteenth=-1;

        while (temp.length()>0)
        {
            currentSixteenth++;
            String small = temp.substring(temp.indexOf("[") + 1, temp.indexOf("]"));

            int period = small.indexOf(".");
            while (period!=-1)
            {
                int noteValue = Integer.parseInt(small.substring(0, period));
                Note n = new Note(noteValue);
                modifyChord(n, currentSixteenth);

                small = small.substring(period+1);
                period = small.indexOf(".");

                //System.out.println("Added " + noteValue);
            }

            if (small.length()>0)
            {
                int noteValue = Integer.parseInt(small);
                Note n = new Note(noteValue);
                modifyChord(n, currentSixteenth);

                //System.out.println("Added " + noteValue);
            }

            temp = temp.substring(temp.indexOf("]") + 1);
        }
    }
}
