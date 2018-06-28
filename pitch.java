/**
 * Statische Klasse zur Frequenz-/Halbtonumrechnung in der gleichstufigen Stimmung
 * Fuer die Facharbeit vom 16.03.2017
 * 2017, Soeren Richter
 * soeren@dreieck-project.de
 * Version 0.1.5
 * Auf Grundlage von Abschnitt 2.3 der Facharbeit.
 * @author Soeren Richter
 * @version 0.1.5
 */

public class pitch
{
    /**
     * Rechnet Halbtonschritte von a aus zu Frequenzwert in Hz um.
     * @param key Halbtonschritte vom Kammerton a aus
     * @return Frequenz in Hz
     */
    public static double freq(double key)
    {
        return Math.pow(2.0, key/12.0)*440.0;
    }
    
    /**
     * Rechnet Frequenzwert in Hz zu Halbtonschritten von a aus um.
     * @param freq Frequenz in Hz
     * @return Halbtonschritte vom Kammerton a aus
     */
    public static double key(double freq)
    {
        return (Math.log(freq/440.0)/Math.log(2.0))*12.0;
    }
}
