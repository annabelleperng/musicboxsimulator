import java.io.File;
import java.io.IOException;

//import java.nio.file.Files; //unused?
//import java.nio.file.Paths; //unused?

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;  
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * This is an example program that demonstrates how to play back an audio file
 * using the Clip in Java Sound API.
 * 
 * A note: all sounds used in the Music Box project are taken from
 * Musescore 2 (a composition application).
 * 
 * @author www.codejava.net
 * @author Annabelle Perng (commented some things out and edited play method)
 * @version 5.30.2018
 *
 */
public class AudioPlayerExample1 implements LineListener
{

    /**
     * This flag indicates whether the playback is completed.
     */
    boolean playCompleted;
    
    /**
     * The location (directory) of the audio files.
     */
    public static String filePath;
    
    /**
     * Finds the file pathway to the current directory. This is unique for each device.
     */
    public void findFilePath()
    {
        filePath = getClass().getProtectionDomain().getCodeSource().getLocation().toString();
        filePath = filePath.substring(filePath.indexOf(":")+1);
        
        int troubleshootSpaces = filePath.indexOf("%20");
        while (troubleshootSpaces!=-1)
        {
            filePath = filePath.substring(0,troubleshootSpaces) + " "
                       + filePath.substring(troubleshootSpaces+3);
            troubleshootSpaces = filePath.indexOf("%20");
        }
        
        System.out.println(filePath);
        //System.out.println("/Users/Annabelle/Desktop/Prog/MusicBox DRAFT 5/");
    }

    /**
     * Plays a given audio file.
     * 
     * @param audioFilePath Path of the audio file.
     */
    void play(String audioFilePath)
    {
        File audioFile = new File(audioFilePath);

        try
        {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            AudioFormat format = audioStream.getFormat();

            DataLine.Info info = new DataLine.Info(Clip.class, format);

            Clip audioClip = (Clip) AudioSystem.getLine(info);

            audioClip.addLineListener(this);

            audioClip.open(audioStream);

            audioClip.start();

            //             while (!playCompleted) {
            //                 // wait for the playback completes
            //                 try {
            //                     Thread.sleep(1000);
            //                 } catch (InterruptedException ex) {
            //                     ex.printStackTrace();
            //                 }
            //             }
            
            //audioClip.close();

        }
        catch (UnsupportedAudioFileException ex)
        {
            System.out.println("The specified audio file is not supported.");
            ex.printStackTrace();
        }
        catch (LineUnavailableException ex) 
        {
            System.out.println("Audio line for playback is unavailable.");
            ex.printStackTrace();
        }
        catch (IOException ex)
        {
            System.out.println("Error playing the audio file.");
            ex.printStackTrace();
        }
    }

    /**
     * Listens to the START and STOP events of the audio line.
     * 
     * @param   event   the event of type start or stop: updates the system to
     *                  see if the sound has stopped playing.
     */
    @Override
    public void update(LineEvent event)
    {
        //         LineEvent.Type type = event.getType();
        // 
        //         if (type == LineEvent.Type.START)
        //         {
        //             //System.out.println("Playback started.");
        //         }
        //         else if (type == LineEvent.Type.STOP)
        //         {
        //             playCompleted = true;
        //             //System.out.println("Playback completed.");
        //         }
    }

    /**
     * Plays a speciifc note given a note name. Note names are defined in class Note.
     * 
     * @param   noteName    the formal name of the note, which is a string 3 characters long.
     *                      noteName must be a valid note name between E-3 (highest) and C-0
     *                      (lowest).
     */
    public void playNote(String noteName)
    {
        String audioFilePath = filePath + noteName + ".wav"; //This path is unique to the device. 
        
        //String audioFilePath = "/Users/Annabelle/Desktop/Prog/MusicBox DRAFT 9/"
        //                       + noteName + ".wav";
        //This was the old, manual way of finding the file path directory.
        
        AudioPlayerExample1 player = new AudioPlayerExample1();
        player.play(audioFilePath);
    }
}