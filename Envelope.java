/**
 * Implementation der ADSR-Huellkurve
 * Fuer die Facharbeit vom 16.03.2017
 * Objekte dieser Klasse fuehren die Berechnung
 * der Funktionswerte der Huellkurve aus.
 * 2017, Soeren Richter
 * soeren@dreieck-project.de
 * Version 0.1.5
 * Code vollstaendig selbst geschrieben, fuer Hintergruende und 
 * Referenzen siehe Abschnitt 3.6 der Facharbeit.
 * @author Soeren Richter
 * @version 0.1.5
 */

public class Envelope
{
    // konstante ADSR-Attribute der Huellkurve:
    private final double attack;
    private final double decay;
    private final double sustain;
    private final double release;
    
    // veraenderbare Hold-Zeit/Tonlaenge:
    private double length;
    
    /**
     * Konstruktor der Klasse Envelope.
     * @param pAttack Attack-Zeit der Huellkurve
     * @param pDecay Decay-Zeit der Huellkurve
     * @param pSustain Sustain-Amplitude der Huellkurve
     * @param pRelease Release-Zeit der Huellkurve
     */
    public Envelope(double pAttack, double pDecay, double pSustain, double pRelease)
    {
        attack = pAttack;
        decay = pDecay;
        sustain = pSustain;
        release = pRelease;
        length = 1;
    }
    
    /**
     * Rueckgabe des Huellkurven-Funktionswertes zum Zeitpunkt time
     * @param time Zeitpunkt in s
     * @return Huellkurven-Funktionswert
     */
    public double getAmplitude(double time)
    {
        double amp = 0;
        if (time <= attack) {
            amp = time/attack;
        } else if (time <= attack+decay && time > attack) {
            amp = (-1 + sustain) / decay * time + (1 - sustain) 
                    / decay * (attack + decay) + sustain;
        } else if (time <= length && time > attack+decay) {
            amp = sustain;
        } else if (time > length) {
            amp = (-sustain) / release * time + sustain 
                    / release * length + sustain;
        }
        return amp;
    }
    
    /**
     * Festlegen der Hold-/Tonlaenge
     * @param pLength Hold-/Tonlaenge in s
     */
    public void setLength(double pLength)
    {
        length = pLength;
    }
    
    /**
     * Rueckgabe der vollstaendigen Huellkurvenlaenge inklusive Release-Zeit
     * @return vollstaendige Laenge der Huellkurve
     */
    public double getFullLength()
    {
        return length+release;
    }
}
