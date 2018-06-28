/**
 * Implementation der Fourier-Reihen
 * Fuer die Facharbeit vom 16.03.2017
 * Objekte dieser Klasse fuehren die Berechnung der Funktionswerte
 * der Fourier-Reihe ihrer entsprechnden Wellenform und Parameter aus.
 * 2017, Soeren Richter
 * soeren@dreieck-project.de
 * Version 0.1.5
 * Code vollstaendig selbst geschrieben. Fuer Referenzen und mathematische
 * Grundlage siehe Kapitel 3 der Facharbeit.
 * @author Soeren Richter
 * @version 0.1.5
 */

public class Oscillator
{
    // Wellenform; "sine", "saw", "square" oder "triangle":
    private final String waveform;
    
    // Grenzfrequenz der berechnung der Fourier-Reihen-Summen
    private final int cutoff;
    
    /**
     * Konstruktor der Klasse Oscillator.
     * @param pWaveform Wellenform ("sine", "saw", "square" oder "triangle")
     * @param pCutoff Grenzfrequenz der berechnung der Fourier-Reihen-Summen
     */
    public Oscillator(String pWaveform, int pCutoff)
    {
        waveform = pWaveform;
        cutoff = pCutoff;
    }
    
    /**
     * Berechnung des Funktionswertes einer Sinus-Funktion (Sine) der Frequenz frequency zum Zeitpunkt timeSample
     * @param frequency Frequenz in Hz
     * @param timeSample Zeitpunkt in s
     * @return Funktionswert
     */
    private double synthesizeSine(double frequency, double timeSample)
    {
       return Math.sin(timeSample * frequency * Math.PI * 2);
    }
    
    // Die folgenden Funktionen nutzen die Fourier-Reihen der entsprechenden
    // Wellenformen zur Berechnung der Funktionswerte. Der Algorithmus ist
    // an das Pseudocode-Konzept (Beispiel: Rechteck/Square) angelehnt:
    /**
     * Funktion sq(t):
     *	   funktionswert = 0;
     *	   von order = 1 bis 22050/freq/2, Schritt 1:
     *          funktionswert = funktionswert + 
     *          (sin(2 * PI * freq * (order * 2 - 1) * t) 
     *          / (order * 2 - 1));
     *     Rückgabe von 4 / PI * funktionswert;
     */
    
    /**
     * Berechnung des Funktionswertes einer Saegezahn-Funktion (Sawtooth) der Frequenz frequency zum Zeitpunkt timeSample
     * @param frequency Frequenz in Hz
     * @param timeSample Zeitpunkt in s
     * @return Funktionswert
     */
    private double synthesizeSaw(double frequency, double timeSample)
    {
       double tempSample = 0;
       for (int order = 1; order <= cutoff/frequency; order++) {
           tempSample += (Math.sin(timeSample * frequency * order 
                            * Math.PI * 2) / order);
       }
       return (2 / Math.PI * tempSample);
    }
    
    /**
     * Berechnung des Funktionswertes einer Rechteck-Funktion (Square) der Frequenz frequency zum Zeitpunkt timeSample
     * @param frequency Frequenz in Hz
     * @param timeSample Zeitpunkt in s
     * @return Funktionswert
     */
    private double synthesizeSquare(double frequency, double timeSample)
    {
       double tempSample = 0;
       for (int order = 1; order <= cutoff/frequency/2; order++) {
           tempSample += (Math.sin(timeSample * frequency * (order * 2 - 1)
                                * Math.PI * 2) / (order * 2 - 1));
       }
       return (4 / Math.PI * tempSample);
    }
    
    /**
     * Berechnung des Funktionswertes einer Dreieck-Funktion (Triangle) der Frequenz frequency zum Zeitpunkt timeSample
     * @param frequency Frequenz in Hz
     * @param timeSample Zeitpunkt in s
     * @return Funktionswert
     */
    private double synthesizeTriangle(double frequency, double timeSample)
    {
       double tempSample = 0;
       for (int order = 1; order <= cutoff/frequency/2; order++) {
           tempSample += (Math.cos(timeSample * frequency *
                                    (order * 2 - 1) * Math.PI * 2)) 
                                    / ((order * 2 - 1)*(order * 2 - 1));
       }
       return ((8 / (Math.PI*Math.PI)) * tempSample);
    }
    
    /**
     * Methode zur Ausgabe des Funktionswertes der festgelegten Wellenform der Frequenz frequency zum Zeitpunkt timeSample
     * @param frequency Frequenz in Hz
     * @param timeSample Zeitpunkt in s
     * @return Funktionswert
     */
    public float getSample(double frequency, double timeSample)
    {        
        if (null != waveform) switch (waveform) {
            case "sine":
                return (float)synthesizeSine(frequency, timeSample);
            case "saw":
                return (float)synthesizeSaw(frequency, timeSample);
            case "square":
                return (float)synthesizeSquare(frequency, timeSample);
            case "triangle":
                return (float)synthesizeTriangle(frequency, timeSample);
            default:
                return 0;
        } else return 0;
    }
}
