
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

/**
 * RollDisplayComponent class shows the music box roll.
 * 
 * @author Annabelle Perng
 *         with assistance from Shyan-Ming Perng
 * @version 5.30.2018
 */
public class RollDisplayComponent extends JComponent
{
    private int width, height; //clickedAtX, clickedAtY;
    private Song thisSong;
    //private int currentMeasure;

    /**
     * Constructor of class RollDisplayComponent. Creates a new empty editor.
     * 
     * @param   frameWidth      width of the editor
     * @param   frameHeight     height of the editor
     */
    public RollDisplayComponent(int frameWidth, int frameHeight)
    {
        super();
        width = frameWidth;
        height = frameHeight;
        thisSong = new Song(90);
        //this.currentMeasure=0;
        this.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e)  
                {
                    //  int oldX = clickedAtX;
                    //  int oldY = clickedAtY;
                    //  if (oldX!=0 && oldY!=0)
                    //  {
                    //      int oldXIndex = oldX / (width/17);
                    //      int oldYIndex = oldY / (height/30);
                    //      repaint(oldXIndex*(width/17), oldYIndex*(height/30),
                    //      width/17, height/30);
                    //  }
                    // 
                    //  clickedAtX = e.getX();
                    //  clickedAtY = e.getY();
                    //
                    //  int horizontalIndex = clickedAtX / (width/17);
                    //  int verticalIndex = clickedAtY / (height/30);
                    //  repaint(horizontalIndex*(width/17), verticalIndex*(height/30),
                    //                         width/17, height/30);

                    int clickedAtX = e.getX();
                    int clickedAtY = e.getY();

                    int col = clickedAtX / (width/17);
                    int row = clickedAtY / (height/30);
                    
                    if (col!=0) //!!!
                    {
                        //Measure m = thisSong.getMeasure(0);
                        Measure m = thisSong.getMeasure(thisSong.getCurrentMeasure());
                        Note n = new Note(row);
                        m.modifyChord(n, col-1);
                        
                        //first column is actually column -1 because note names takes up an 
                        //extra column! So, subtract 1 from col when creating a new note.
    
                        repaint();
                    }
                }
            });
    }
    
    /**
     * Constructor of class RollDisplayComponent. Displays the song indicated in the parameter.
     * 
     * @param   frameWidth      width of the editor
     * @param   frameHeight     height of the editor
     * @param   s               the song to be displayed
     */
    public RollDisplayComponent(int frameWidth, int frameHeight, Song s)
    {
        super();
        width = frameWidth;
        height = frameHeight;
        thisSong = s;
        this.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e)  
                {
                    int clickedAtX = e.getX();
                    int clickedAtY = e.getY();

                    int col = clickedAtX / (width/17);
                    int row = clickedAtY / (height/30);
                    
                    if (col!=0) //!!!
                    {
                        Measure m = thisSong.getMeasure(thisSong.getCurrentMeasure());
                        Note n = new Note(row);
                        m.modifyChord(n, col-1);
                        
                        //first column is actually column -1 because note names takes up an 
                        //extra column! So, subtract 1 from col when creating a new note.
                        
                        repaint();
                    }
                }
            });
    }
    
    /**
     * Paints the display. Shows a linear 'grid' of notes which represents one measure.
     * 
     * @param   g   the Graphics which is passed by the parent method display().
     */
    @Override
    protected void paintComponent(Graphics g)
    {
        int yGap = height / 30; //gap between horizontal lines
        int horizontalLines = 30; //number of horizontal lines
        int xGap = width / 17; //gap between vertical lines
        int verticalLines = 17; //number of vertical lines

        //draws BG of first (note name) column
        g.setColor(new Color(210,210,210)); //225,225,225
        g.fillRect(0,0,xGap,height); //x, y, width, height

        //draws alternation of BG colors btwn lines
        g.setColor(new Color(230,230,230)); //230,230,230
        for (int i=0; i<horizontalLines; i+=2)
        {
            g.fillRect(xGap, i*yGap, width-xGap, yGap); //x, y, width, height
        }
        
        g.setColor(new Color(182,182,182)); //182,182,182
        
        //draws horizontal lines
        for (int i=0; i<horizontalLines; i++)
        {
            g.drawLine(0, i*yGap, width, i*yGap); //x1, y1, x2, y2
        }

        //draws vertical lines
        for (int i=0; i<verticalLines; i++)
        {
            g.drawLine(i*xGap, 0, i*xGap, height); //x1, y1, x2, y2
        }

        //draws text of note names
        g.setColor(new Color(0,0,0));
        int hTextGap = (int)(yGap/1.32);
        int vTextGap = (int)(xGap/3.7);
        for (int i=0; i<horizontalLines; i++)
        {
            Note temp = new Note(i);
            g.drawString(temp.assignNoteName(i), vTextGap, i*yGap+hTextGap); //String, x, y
        }

        //draws beat-separating lines
        g.drawLine(1*xGap, 0, 1*xGap, height); //x1, y1, x2, y2
        g.drawLine(5*xGap, 0, 5*xGap, height); //x1, y1, x2, y2
        g.drawLine(9*xGap, 0, 9*xGap, height); //x1, y1, x2, y2
        g.drawLine(13*xGap, 0, 13*xGap, height); //x1, y1, x2, y2
        
        //draws actual notes
        Measure m  = thisSong.getMeasure(thisSong.getCurrentMeasure());
        Chord[] chordsInM = m.returnChordsInMeasure();
            //chordsInM are all the chords in the measure m

        g.setColor(new Color(189,172,233)); //light purple: 215,200,245
                                            //dark purple: 189,172,233
                                            //darker purple: 64,0,138

        for (int i=0;i<chordsInM.length;i++)
        {
            Chord chordAtI = chordsInM[i]; //chordAtI is chord in measure m at sixteenthLength i
            ArrayList<Note>notesAtI = chordAtI.returnChord();
                //notesAtI are the individual notes in chordAtI

            for (int j=0;j<notesAtI.size();j++)
                //iterates through each note in that measure at that time
            {
                Note n = notesAtI.get(j);
                int nValue = n.getNoteValue();
                
                g.fillRect((i+1)*(width/17)+1, nValue*(height/30)+1, width/17-2, height/30-2);
                    //x, y, width, height
                
                //FOR VISIBLE PERIMETER try +1, +1, -2, -2.
                    //still works with +-0, but the fill is more complete.
                //BE MINDFUL OF THE SHIFT: use i+1, not i for fillRect!
                    //again, this is because of the note names on the left.
            }
        }

        //System.out.print("Your song is now " + thisSong.songToText());
                //TROUBLESHOOTS SONG TO TEXT 
    }

    /**
     * Returns the song displayed by this RollDisplayComponent.
     * 
     * @return      the song which is currently being shown
     */
    public Song getSong()
    {
        return thisSong;
    }
    
    /**
     * Sets the song displayed by this RollDisplayComponent.
     * 
     * @param   s   the new song to be shown
     */
    public void setSong(Song s)
    {
        thisSong = s;
    }
}
