Dokumentation zu Java Polyline Encoder mit RESTful Webservice
===

Inhalt
---
1. Projektbeschreibung
2. Probleme während der Entwicklung
3. Architektur / Design
4. Quelltext Stil
5. Ergebnis
 
***

1 Projektbeschreibung
---

### 1.1. Ziel

Der frühere Polylineencoder soll überarbeitet werden. Dazu müssen allgemeinere Methoden ausgegliedert werden.
Außerdem sollen Tests für eigene Methoden geschrieben werden.

Der Jersey RESTful Server soll mindestens `GPX`-Dateien akzeptieren und `JSON, RAW` als Antwort liefern.
Fehler sollen entsprechend abgefangen werden und dem Nutzer mitgeteilt werden.
 
### 1.2. Technologien

- Programmiersprache `Java`
- Testframework `JUnit`
- Server Framework `Java Jersey`

### 1.3. Nice to Have

- Zusätzliche Eingabeformate: `KML`, `KMZ` (komprimierte KML), `URL` (linkt auf Datei, die automatisch geparst wird), `RAW`
- Zusätzliche Ausgabeformate: `HTML` (mit Google Map), `RAW`, `XML`

### 1.4. Teilnehmer mit Aufgabenverteilung

###### Martin Bormeister
 - Server + Tests
 - Server ViewGenerator Factory
 - Basis Eingabeformular
 - Weitere Tests

###### Peter Pensold
 - Encoder Refactoring + Tests
 - Parser Factory + Tests
 - In/Output Enums
 - Dynamisches Eingabeformular
 - Testdaten Erstellung

###### Gemeinsam
 - Bugfixes
 - Readme
 - Ant Tasks

***

2 Probleme während der Entwicklung
---

Folgende Dinge haben uns im Projekt die Nerven geraubt.

### 2.1. Backslash Bug

Marc McClure [beschrieb bereits](http://facstaff.unca.edu/mcmcclur/GoogleMaps/EncodePolyline/pitfalls.html),
dass es Probleme mit Backslahes und deren Kodierung geben könnte. Dieses Problem haben wir auch gelöst.
Jedoch gab es Anfangs noch Schwierigkeiten zwischen Jersey, der Markerreplace Methode und der Backslashreplace Methode.
Die Tests schlugen auch nicht fehl. Daher war es ein Rätsel wo wir suchen mussten.
Mittlerweile funktioniert die Ausgabe und das Encodieren korrekt.

### 2.2. Ant Jar Erstellung

Wir wollten zunächst eine ausführbare Jar-Datei mit eingebetteten Bibliotheken erstellen. Das funktionierte auch am Anfang.
Jedoch konnte dann das Startformular nicht angezeigt werden. Jersey vermisste Informationen, welche in einigen Manifest-Dateien
innerhalb der Bibliotheken standen, die aber nicht mitkopiert wurden.
Daher versuchten wir die Manifest Dateien mithilfe von Ant zusammenzufügen. Dies gelang auch, jedoch waren noch nicht alle Informationen vorhanden.

Also mussten wir uns mit einer anderen Lösung zufrieden geben:

Wir kopierten den Hauptbibliothekenordner `lib/main` und teilten Ant mit, dass die Manifest Datei den Klassenpfad anhand der Bibliotheken zusammenbauen soll.
Somit liegen alle benötigten Bibliotheken innerhalb des Ordners `build/libs` nach dem erzeugen der Jar Datei. 

### 2.3. Jersey

Die Verwendung von Jersey war nicht weiter schwer, jedoch musste zur Nutzung ersteinmal eine Reihe von Bibliotheken geladen werden.
Zusätzlich benötigte die Verwendung von `multipart/form-data` noch weitere Bibliotheken.
Durch die Suche von immer weiteren Bibliotheken wurde der Arbeitsfluss etwas gebremst.

***

3 Architektur / Design
---

- Logische Strukturierung durch Packages
- Anwendung des Factory-Patterns für Parser und für ViewGenerator. Dadurch lassen sich weitere Parser und Ausgaben zügig ergänzen.
- Enums für Ein- und Ausgabeformate erleichtern die Abfrage innerhalb von Mehrfachverzweigungen.
- Trennung von Texten, die nicht in Java geschrieben sind und sonst umständlich im Quelltext verankert werden müssten. Beispielsweise das Formular `templates/view/formular.html`
- Relevante Testabdeckung

***

4 Quelltext Stil
---

Wir haben versucht den Quelltext so leserlich wie möglich zu gestalten. Einige kleine optische Unterstützungen sind:

- Die Komplexität in Cobertura sollte nie den Wert `3` überschreiten.
- Drei freie Zeilen zwischen den Methoden. Dadurch kann man sofort den Anfang einer Methode erkennen.
- Damit der Lesefluss nicht unterbrochen wird, werden auch zu lange Zeilen nicht unterbrochen. Lediglich bei JavDoc.
- Multiplikationen und Divisionen angelehnt an Mathematik:
  Wenn etwas multipliziert oder dividiert wird, dann werden keine Leerzeichen gesetzt. Bei Addition und Subtraktion schon.
  Dadurch sind Punkt und Strichrechnungen einfacher zu unterscheiden. Leider lässt sich diese Regel nicht in Checkstyle einpflegen.
  - Beispiel: `a*b` statt `a * b` oder `a/c` statt `a / c`. Kombination von Punkt und Strichrechnung: `(START + index)/var`

Der Stil steht in [doc/style.xml](https://github.com/petpen/JavaPolylineEncoder2/blob/master/doc/style.xml) und kann beispielsweise in Eclipse importiert werden.

***

5 Ergebnis
---

### 5.1. Erledigt

###### Parser
- Erstellung von Parsern nach Eingabeformaten
- Parser können sowohl Dateien (via Dateinamen) als auch Daten (via String) verarbeiten. Außer KMZ, da dies ein Binärformat ist.
- Es werden mehrere Tracks innerhalb einer Datei geparst
- Umsetzung aller genannten Eingabeformate: `GPX, KML, KMZ, URL, RAW`

###### Refactoring des Polyencoders
- Aufteilung in mehrere Klassen
- Logische Trennung von Encoder und Methoden, welche nicht direkt zum Encoder gehören
- Umbenennung von einigen Klassen
- Packagestruktur angepasst
- `Util`-Klasse für häufig verwendete Methoden erstellt, welche nicht unbedingt in verschiedene Klassen gehören
  - Möglich wäre noch eine Aufteilung in `FileUtil` für Methoden welche sich nur mit Dateien beschäftigen und dann Erzeugung eines Packages `util`.
  
###### Server
- Umsetzung aller genannten Ausgabeformate: `HTML, JSON, RAW, XML`
- Umsetzung von Fehlerbehandlungen. Der Nutzer wird benachrichtigt (siehe [README.md](https://github.com/petpen/JavaPolylineEncoder2/blob/master/README.md) > Supported Formats > Status Codes)
- Ausgabe aller enkodierten Tracks bei allen Rückgabeformaten
- Umsetzung eines Formulares zur Eingabe von Daten, Dateien und Links

### 5.2. Einschränkungen
###### Ausgabe / Parsen
Es werden nur die nötigsten Attribute für einen Track geparst, daher wird auf zusätzliche Attribute (Bsp. Name des Tracks) kein Wert gelegt.

Waypoints in GPX Dateien werden als Track erkannt.
Das bedeutet, dass der erste Waypoint in einer Datei der Startpunkt des Tracks sein wird und der letzte Waypoint entsprechend der letzte Trackpoint.

###### Bounds
Die Bounds beziehen sich nur auf den ersten Track.
Die Bounds funktionieren korrekt, solange die Polyline nicht die Erde umrundet. Das hängt mit der Berechnung zusammen.
Diese sucht nur die größten und die kleinsten Zahlen der Koordinaten für Longitude, Latitude und Altitude innerhalb des Tracks heraus. 
 
###### Encoder
Es wurden bei den JUnit Tests nicht alle Methoden getestet.
Das liegt daran, dass der ursprüngliche Algorithmus nicht von uns stammt. Er wurde entsprechend an Java angepasst.
Während manueller Tets waren aber keine Abweichungen gegenüber des Originalalgorithmus zu erkennen.
Bis auf den oben beschriebenen Backslash Bug bei der Browserausgabe.

### 5.3. Neue Ziele
- Properties Datei für Serverkonfiguration
- Konsolen Statusausgabe, wenn zum Beispiel ein Track geparsed wird, von wem er kam und wann er kam.
- HTML Ausgabe der Polyline mit Version 3 der Google Maps Api
- Datenbank zur Verwaltung von Polylines
- GenerateErrorMsg in die ViewFactory und ViewGenerator übernehmen
- Einschränkungen reduzieren
