/**
 * Synth-Klasse der Implementation von Fourier-Reihen zur Klangsynthese.
 * Fuer die Facharbeit vom 16.03.2017
 * Dies ist "das Herz" der Anwendung. 
 * Objekte dieser Klasse fuehren Berechnung und WAVE-Speichrung des Klanges aus.
 * 2017, Soeren Richter
 * soeren@dreieck-project.de
 * Version 0.1.5
 * Code selbst geschrieben, WAVE-Speicherung basierend auf
 * http://stackoverflow.com/questions/3297749/java-reading-manipulating-and-writing-wav-files
 * und Abschnitt 2.2 sowie 4 der Facharbeit.
 * @author Soeren Richter
 * @version 0.1.5
 */

import java.io.ByteArrayInputStream;
import java.io.File;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.awt.*;
import java.io.IOException;
import javax.swing.*;

public class Synth {
    // Samplerate des zu erzeugenden PCM-Datenstroms:
    private final double samplerate;
    
    // Tonlänge exklusive Relese der Huellkurve (Hold-Zeit):
    private final double length;
    
    // Frequenz der zu synthetisierenden Tons:
    private final double freq;
    
    // relative Amplitude:
    private final double amplitude;
    
    // Wellenform; "sine", "saw", "square" oder "triangle":
    private final String waveform;
    
    // Attribute der Huellkurve:
    private final double attack;
    private final double decay;
    private final double sustain;
    private final double release;
    
    /**
     * Konstruktor der Synth-Klasse.
     * @param pSamplerate Samplerate des zu erzeugenden PCM-Datenstroms
     * @param pLength Tonlänge exklusive Relese der Huellkurve (Hold-Zeit)
     * @param pFreq Frequenz der zu synthetisierenden Tons
     * @param pAmp relative Amplitude
     * @param pWaveform Wellenform ("sine", "saw", "square" oder "triangle")
     * @param pAttack Attack-Zeit der Huellkurve
     * @param pDecay Decay-Zeit der Huellkurve
     * @param pSustain Sustain-Amplitude der Huellkurve
     * @param pRelease Release-Zeit der Huellkurve
     */    
    public Synth(double pSamplerate, double pLength, double pFreq, double pAmp,
            String pWaveform, double pAttack, double pDecay, double pSustain,
            double pRelease) {
        samplerate = pSamplerate;
        length = pLength;
        freq = pFreq;
        amplitude = pAmp;
        waveform = pWaveform;
        attack = pAttack;
        decay = pDecay;
        sustain = pSustain;
        release = pRelease;
    }
    
    /**
     * Methode zur Durchfuehrung der Klangsynthese.
     */
    public void synthesize_standard() {
        // Die Zeildatei der Synthese wird nach Auswahl festgelegt.
        File filePath = getFilePath();
        
        // Wenn eine Datei gewaehlt wurde, wird die Klangsynthese
        // durchgefuehrt, ansonsten mit entsprechendem Hinweis nicht.
        if (filePath != null) {
            // Oscillator- und Envelope-Objekt werden den jeweils
            // festgelegten konstanten Attributen erzeugt.
            Oscillator osc = new Oscillator(waveform, (int)samplerate/2);
            Envelope env = new Envelope(attack,decay,sustain,release);
            
            // Hold-Laenge des Tons wird an das Envelope-Objekt uebergeben.
            env.setLength(length);
            
            // Fliesskomma-Array buffer zur Aufnahme der
            // berechneten Werte wird erzeugt.
            float[] buffer = new float[(int)(samplerate * env.getFullLength())];
            
            // Werteberechnung der synthetisierten Schallwelle wird
            // entsprechend Pseudocode-Konzept durchgefuehrt, siehe:
            /**
             * von sample = 0 bis 44100 * (length + release), Schritt 1:
	     *	    schreibe amp * rect(sample/44100) * env(sample/44100);
             */
            for (int sample = 0; sample < buffer.length; sample++) {
                buffer[sample] = (float)(osc.getSample(freq, sample / samplerate)
                                    * env.getAmplitude(sample / samplerate)
                                    * amplitude);
            }
            
            // Der Fliesskomma-Buffer wird an die Speichermethode weitergegeben.
            save_as_wave(buffer, filePath);
            
        } else {
            JOptionPane.showMessageDialog(new JFrame("Info"),
                                            "Es wurde keine Datei geschrieben.");
        }
        
    }
    
    /**
     * Methode, die die Fliesskommareihe sampleBuffer in einen 16-Bit PCM Datenstrom konvertiert und als WAVE-Datei nach outputFile speichert.
     * @param sampleBuffer Umzuwandelnde Fliesskommareihe
     * @param outputFile Zieldatei der WAV-Speicherung
     */
    private void save_as_wave(float[] sampleBuffer, File outputFile) {
        // Erstellung eines Byte-Arrays zur Aufnahme der 16-Bit PCM-Werte.
        // Fuer jeden Datenwert werden 2 Byte (2*8 Bit) benötigt. Daher muss
        // der Byte-Buffer doppelt so lang sein, wie der bisherige Buffer.
        final byte[] byteBuffer = new byte[sampleBuffer.length * 2];
        
        int bufferIndex = 0;
        
        // Umwandlung der Fliesskommawerte in einen 16-Bit Datenstrom, der
        // aus jeweils zwei Byte (8-Bit) Werten für jeden PCM-Wert besteht.
        // Jeder zweite Wert wird vor der Zuweisung um 8 Bit-Stellen verschoben.
        for (int i = 0; i < byteBuffer.length; i++) {
            final int x = (int) (sampleBuffer[bufferIndex++] * 32767.0);
            byteBuffer[i] = (byte) x;
            i++;
            byteBuffer[i] = (byte) (x >>> 8);
        }
        
        // Nutzung der javax.sound.sampled-API zum Schreiben des Byte-Buffers
        // in eine WAVE-Datei outputFile mit korrektem Header. Moegliche
        // Probleme bei der Speicherung werden abgefangen.
        try {
            AudioFormat format = new AudioFormat((float)samplerate, 16, 1,
                                                   true, false);
            ByteArrayInputStream bais = new ByteArrayInputStream(byteBuffer);
            
            try (AudioInputStream audioInputStream = new AudioInputStream(bais,
                    format, sampleBuffer.length)) {
                AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE,
                        outputFile);
            } 
            
            JOptionPane.showMessageDialog(new JFrame("Info"),
                            "Die WAV-Datei wurde erfolgreich generiert und nach "
                            + outputFile + " geschrieben.");
            
        } catch (HeadlessException | IOException e) {
            JOptionPane.showMessageDialog(new JFrame("Info"),
                "Es wurde keine Datei geschrieben, da folgender Fehler auftrat: "
                + e);
        }
    }
    
    /**
     * Methode zur Auswahl einer Zieldatei
     * @return Pfad der WAVE-Zieldatei
     */
    private File getFilePath() {
        // Dateiwahldialog wird aufgerufen.
        JFileChooser fc = new JFileChooser();        
        int returnVal = fc.showOpenDialog(new JFrame("parent"));
        
        // Wurde eine Datei gewaehlt, wird diese zurueckgegeben. Falls die
        // Endung ".wav" bisher fehlte, wird diese zusaetzlich angefuegt.
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            if (fc.getSelectedFile().toString().endsWith(".wav"))
                return fc.getSelectedFile();
            else {
                return new File(fc.getSelectedFile().toString() + ".wav");
            }
        } else {
            return null;
        }
    }
}
