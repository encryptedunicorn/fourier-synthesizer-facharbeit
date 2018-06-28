# fourier-synthesizer-facharbeit
Abgabeversion der Beispielimplementation eines Synthesizers basierend auf Fourierreihen aus meiner Facharbeit.

## Hintergrund

Im elften Jahrgang am Gymnasium wurde im Rahmen des Seminarfaches eine Facharbeit verfasst. Zu meiner Facharbeit mit dem Titel *"Was sind die mathematischen Grundlagen moderner elektronischer Musik? - Die Fourier-Reihen und -Synthese"* entschied ich mich, eine Referenzimplementation als Anhang zu meiner Facharbeit zu programmieren.
Da die Schule erfolgreich mit dem Abitur abgeschlossen habe, stelle ich jetzt den Code zu meiner Facharbeit (und jene auf meiner Website) offen und frei zur Verfügung.

## Code

Der Code ist JavaDoc konform kommentiert. Zur Erstellung der Graphical User Interface wurde der NetBeans Form Editor verwendet.
Die Klassen sind
* mainWindow (GUI- und Interaktionscode)
* Synth (Synthesizer-Klasse mit WAVE-Export)
* Oscillator (Oszillator-Klasse, Berechnung der Samples für verschiedene Waveforms)
* Envelope (Hüllkurven-Klasse, berechnet Amplituden für Zeitpunkte nach ADSR Parametern)
* pitch (Statisch; zur Umwandlung zwischen Frequenzen und Skalentönen)

## Funktionen

* Vier Wellenformen (Sinus, Rechteck, Sägezahn, Dreieck)
* Angabe von Ton aus 12-Ton Skala mit cent-Abweichungen oder Angabe von Frequenz in Hz
* Hüllkurve nach Attack, Decay, Sustain, Release
* Export als WAVE-File mit verschiedenen Sampleraten

## Weiterverwendung

Den Code stelle ich frei zur Weiterverwendung und zum Zitieren zur Verfügung unter der Bedingung einer wissenschaftlich korrekten Zitierweise beziehungsweise Nennung meines Namens sowie dieser Quelle. **Fork it!**

*P.S. die Facharbeit erhielt die Bestnote! :D*
