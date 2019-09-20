import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.util.Scanner;

/**
 * Staff class outputs a menu and interacts with the user. Staff is capable
 * of saving songs to text / importing existing songs from text,
 * adding new measures / clearing current measures, and changing the VST
 * used to play a song.
 * 
 * @author Annabelle Perng
 * @version 5.30.18
 */
public class Staff
{
    private RollDisplayComponent r;
    private JFrame frame;
    /**
     * The size of the frame window.
     */
    public static int frameX, frameY;
    /**
     * The size of the editor.
     */
    public static int rollDisplayComponentX, rollDisplayComponentY;

    // tempo control?                           //status: DONE
    // tranposition feature
    // measure selection feature / autoscroll   //status: DONE
    // title enterer                            //status: DONE

    // things to think about: 

    /**
     * Acts according to the user's choice. Choices are listed in printMenu().
     * 
     * @param   choice      corresponds to the user's choice
     */
    public void interpretUserChoice(int choice)
    {
        Scanner in = new Scanner(System.in);

        if (choice==0) //play song
        {
            Song temp = r.getSong();

            int len = temp.getMeasureCount();
            int tempo = temp.getTempo();

            System.out.println();
            System.out.println();
            System.out.println("Playing measure: ");
            System.out.print("\t");

            for (int i=0; i<len; i++)
            {
                temp.setCurrentMeasure(i);
                System.out.print((i+1) + " >> ");
                importSong(temp);
                temp.getMeasure(i).playMeasure(tempo);
            }
            System.out.print("DONE.");
            System.out.println();
        }
        else if (choice==1) //play song from middle
        {
            Song temp = r.getSong();
            int totalMeasures = temp.getMeasureCount();

            System.out.println();
            System.out.println("Enter a measure number to start playback from."
                + " (1 ≤ measure number ≤ " + totalMeasures + ")");

            int start = in.nextInt();
            while (start<=0 || start > totalMeasures)
            {
                System.out.println("ERROR: Please enter a valid measure number.");
                start = in.nextInt();
            }

            int tempo = temp.getTempo(); 

            System.out.println();
            System.out.println();
            System.out.println("Playing measure: ");
            System.out.print("\t");

            for (int i=start-1; i<totalMeasures; i++)
            {
                temp.setCurrentMeasure(i);
                System.out.print((i+1) + " >> ");
                importSong(temp);
                temp.getMeasure(i).playMeasure(tempo);
            }
            System.out.print("DONE.");
            System.out.println();
        }
        else if (choice==3) //add measure
        {
            Song temp = r.getSong();
            temp.addMeasure();
            temp.setCurrentMeasure(temp.getMeasureCount()-1);

            importSong(temp); //changed from long block
        }
        else if (choice==11) //import old song
        {
            System.out.println("Please enter the name of the song you would like to import.");
            SongToTextFile txt = new SongToTextFile();
            String existingSongName = in.nextLine();
            Song existingSong = txt.readTextFile(existingSongName);
            importSong(existingSong);

            //System.out.println(existingSongName + ".txt has been successfully imported.");
        }
        else if (choice==9) //save song to txt file
        {
            System.out.println("Enter the name of your song.");
            String title = in.nextLine();
            r.getSong().setTitle(title);

            SongToTextFile txt = new SongToTextFile();
            txt.createTextFile(r.getSong());

            System.out.println("Your song has successfully been saved to file " + title
                + ".txt.");
        }
        else if (choice==12) //create new song
        {
            System.out.println("If you have not saved your song to a text file, your current draft "
                + "will be lost!");
            System.out.println("Proceed with this action? (YES/NO)");

            String confirm = in.nextLine().toUpperCase();
            if (confirm.equals("YES"))
            {
                Song emptySong = new Song(90);
                importSong(emptySong);
            }
        }
        else if (choice==6) //change tempo
        {
            System.out.println("Enter new tempo. Current tempo is " + r.getSong().getTempo()
                + " BPM.");
            System.out.println("Default tempo is 90 BPM. " + 
                "Suggested restriction: 30 < tempo < 252.)");
            int newTempo = in.nextInt();
            r.getSong().setTempo(newTempo);
        }
        else if (choice==5) //clear current measure
        {
            Song temp = r.getSong();
            frame.setVisible(false);

            int currentMeasure = temp.getCurrentMeasure();

            temp.removeMeasure(currentMeasure);
            temp.addMeasure(currentMeasure);

            //System.out.println(currentMeasure + " = " + temp.getCurrentMeasure() + "?");
            //TROUBLESHOOTING

            importSong(temp); //changed from long block
        }
        else if (choice==8) //print out info about song
        {
            r.getSong().printStatistics();
        }
        else if (choice==7) //(re)title song
        {
            System.out.println("Enter the name of your song.");
            String title = in.nextLine();
            r.getSong().setTitle(title);
        }
        else if (choice==2) //play audio only; potentially use different VST
        {
            System.out.println("Please choose a VST to use.");
            printVSTList();

            int vst = in.nextInt();
            if (vst==0)
            {
                r.getSong().playSong();
            }
            else
            {
                r.getSong().playSongWithVST("-ver" + vst);
            }
        }
        else if (choice==4) //show different measure
        {
            Song temp = r.getSong();
            int totalMeasures = temp.getMeasureCount();

            System.out.println("Enter a measure number to edit."
                + " (1 ≤ measure number ≤ " + totalMeasures + ")");

            int measureToDisplay = in.nextInt();
            while (measureToDisplay<=0 || measureToDisplay > totalMeasures)
            {
                System.out.println("ERROR: Please enter a valid measure number.");
                measureToDisplay = in.nextInt();
            }

            temp.setCurrentMeasure(measureToDisplay-1);

            importSong(temp); //changed from long block
        }
        else if (choice==10) //print out song directory
        {
            System.out.println();
            System.out.println("Directory (does not include user inputted songs):");
            System.out.println("\t batb2");
            System.out.println("\t chopin");
            System.out.println("\t chopsticks");
            System.out.println("To import a song in the directory, press 11 (import an old song)");
            System.out.println(" and type in the song title as shown.");
        }
        else if (choice==13) //change editor size
        {
            System.out.println();
            System.out.println("Type in the number that corresponds with the preferred size:");
            System.out.println("\t1 - compact");
            System.out.println("\t2 - default");

            int size=in.nextInt();
            while (size<1 || size>2)
            {
                System.out.println("ERROR: Please enter a valid number.");
                size=in.nextInt();
            }
            setSize(size);

            frame.setVisible(false);
            frame = new JFrame();
            frame.setVisible(true);
            frame.setSize(frameX, frameY);
            r = new RollDisplayComponent(rollDisplayComponentX, rollDisplayComponentY, r.getSong());
            frame.add(r);
        }
        else if (choice==99) //help
        {
            printHelp();
        }
    }

    /**
     * Outputs a menu of options for the user to use.
     */
    public void printMenu()
    {
        System.out.println();
        System.out.println();
        System.out.println("OPTIONS:");
        System.out.println("0 - play current song."); //0
        System.out.println("1 - play current song from the middle."); //12
        System.out.println("2 - play current song (audio only).");
        System.out.println("    >> Offers 2 alternate VST options."); //9

        System.out.println("3 - add a new measure (to the end of the song)."); //1
        System.out.println("4 - show a different measure."); //10
        System.out.println("5 - clear all notes in current measure."); //6

        System.out.println("6 - change the song's tempo."); //5
        System.out.println("7 - change the title of the song."); //8
        System.out.println("8 - print out information about the song."); //7

        System.out.println("9 - save current song to file."); //3
        System.out.println("10 - print out the default song directory."); //11
        System.out.println("11 - import an old song."); //2
        System.out.println("12 - create a new song."); //4

        System.out.println("13 - change the editor size."); //13
        System.out.println("99 - HELP!"); //99
        System.out.println("100 - quit program."); //100
        System.out.println();
    }

    /**
     * Prints instructions for the user.
     */
    public void printHelp()
    {
        System.out.println();
        System.out.println("_____________________________________________________________________");
        System.out.println();
        System.out.println("INSTRUCTIONS:");
        System.out.println("Click on a space to add a note. Click on an existing note ");
        System.out.println("to remove it from the grid. Songs are always played from the ");
        System.out.println("very beginning.");
        System.out.println();
        System.out.println("FORMAT:");
        System.out.println("The song is in 4/4. One measure is always shown onscreen by ");
        System.out.println("default. Lower notes are on the bottom of the grid, while higher ");
        System.out.println("notes are at the top. The staff is modeled after the available ");
        System.out.println("notes in a typical 30-note music box.");
        System.out.println();
        System.out.println("TROUBLESHOOTING:");
        System.out.println("If nothing is showing up onscreen, try dragging the grid window to ");
        System.out.println("resize it larger or smaller.");
        System.out.println("_____________________________________________________________________");
    }

    /**
     * Specifies which types of VSTs are available to the user. (A VST is basically
     * an instrument. The default VST is the music box / glockenspiel sound.)
     */
    public void printVSTList()
    {
        System.out.println("AVAILABLE LIST OF VSTS");
        System.out.println("0 - Default (music box).");
        System.out.println("1 - Bass.");
        System.out.println("2 - Pizzicato (plucked strings).");
        System.out.println();
    }

    /**
     * Interacts with the user. Terminates the program when the user specifies.
     */
    public void interactWithUser()
    {
        AudioPlayerExample1 finder = new AudioPlayerExample1();
        finder.findFilePath();

        printHelp();
        printMenu();

        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();
        while (choice!=100)
        {
            interpretUserChoice(choice);
            printMenu();
            choice = in.nextInt();
        }

        System.out.println();
        System.out.println("Thanks for using the music box program!");
    }

    /**
     * Sets the size of the editor. There are two different size options.
     * 
     * @param size  choice indicating the desired size of the editor
     *              1   compact
     *              2   normal
     */
    public void setSize(int size)
    {
        if (size==1)
        {
            frameX=702;
            frameY=501;
            rollDisplayComponentX=702;
            rollDisplayComponentY=492;
        }
        else if (size==2)
        {
            frameX=935;
            frameY=716;
            rollDisplayComponentX=935;
            rollDisplayComponentY=710;
        }
    }

    /**
     * Constructor for objects of class Staff.
     */
    public Staff()
    {
        super();

        frameX=935;
        frameY=716;
        rollDisplayComponentX=935;
        rollDisplayComponentY=710;

        display();

        //Constructor used to have the capabilities of method display().
        //To avoid repetition of code, display() is now a separate method.
    }

    /**
     * Displays the editor window without a song.
     */
    public void display()
    {
        frame = new JFrame();
        frame.setVisible(true);
        frame.setSize(frameX,frameY); //935 x 716

        r = new RollDisplayComponent(rollDisplayComponentX,rollDisplayComponentY); //935 x 710
        frame.add(r);
    }

    /**
     * Main method for class Staff. Creates an instance of Staff, then 
     * interacts with the user.
     * 
     * @param   args    array with information that may be passed
     *                  at start of processing
     */
    public static void main(String[]args)
    {
        Staff staff = new Staff();
        staff.interactWithUser();
    }

    /**
     * Imports and displays the song specified in the parameter.
     * 
     * @param   song    the song to be shown
     */
    public void importSong(Song song)
    {
        frame.setVisible(false);
        display();
        r.setSong(song);
    }
}
