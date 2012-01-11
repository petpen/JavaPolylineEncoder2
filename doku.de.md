Dokumentation zu Projekt
===

Projektname
---

- Java Polyline Encoder mit RESTful Webservice

Teilnehmer und Aufgabenverteilung
---

### Martin Bormeister
 - Server + Tests
 - Server View Generator Factory
 - Basis Eingabeformular
 - Weitere Tests

### Peter Pensold
 - Encoder Refactoring + Tests
 - Parser Factory + Tests
 - In/Output Enums
 - Dynamisches Eingabeformular
 - Testdaten Erstellung

### Alle
 - Bugfixes
 - Readme
 - Ant Tasks

Ziele
---

### Encoder
 - Refactoring
 - Tests

### Server


### Eingabeformate
 - GPX

### Ausgabeformate
 - JSON (kodierte Punkte, [Name, Beschreibung])
 - RAW (reine kodierte Daten)

Nice to Have
---

### Zusätzliche Eingabeformate
 - KML
 - KMZ (komprimierte KML)
 - URL (linkt auf Datei, die automatisch geparst wird)

### Zusätzliche Ausgabeformate
 - HTML (mit Google Map)
 - RAW
 - XML

Technologien
---

- Programmiersprache `Java`
- Testframework `JUnit`
- Server Framework `Java Jersey`


Probleme
---

### Backslash Bug

### Ant Jar Erstellung

### Jersey

Architektur
---


Design
---


Ergebnis
---

### Erledigt

###### Parser
- Erstellung von Parsern nach Eingabeformaten
- Parser können sowohl Dateien als auch Daten (via String) verarbeiten. Außer KMZ, da dies ein Binärformat ist.
- Es werden mehrere Tracks innerhalb einer Datei geparst
- Umsetzung aller genannten Eingabeformate: `GPX, KML, KMZ, URL`

###### Refactoring des Polyencoders
- Aufteilung in mehrere Klassen
- Logische Trennung von Encoder und Methoden, welche nicht direkt zum Encoder gehören
- Umbenennung von einigen Klassen
- Packagestruktur angepasst
- `Util`-Klasse für häufig verwendete Methoden erstellt, welche nicht unbedingt in verschiedene Klassen gehören
  - Möglich wäre noch eine Aufteilung in `FileUtil` für Methoden welche sich nur mit Dateien beschäftigen
  
###### Server
- Umsetzung aller genannten Ausgabeformate: `HTML, JSON, RAW, XML`
- Umsetzung von Fehlerbehandlungen. Der Nutzer wird benachrichtigt (siehe [README.md](https://github.com/petpen/JavaPolylineEncoder2/blob/master/README.md) > Supported Formats > Status Codes)

### Einschränkungen
###### Ausgabe / Parsen
Es werden nur die nötigsten Attribute für einen Track geparst, daher wird auf zusätzliche Attribute (Bsp. Name des Tracks) kein Wert gelegt.
Die Bounds beziehen sich nur auf den ersten Track.

###### Bounds
Die Bounds funktionieren korrekt, solange die Polyline die Erde nicht umrundet. Das hängt mit der Berechnung zusammen.
Diese sucht nur die größten und die kleinsten Zahlen der Koordinaten für Longitude, Latitude und Altitude innerhalb des Tracks heraus. 
 
###### Encoder
Es wurden bei den JUnit Tests nicht alle Methoden getestet.
Das liegt daran, dass der ursprüngliche Algorithmus nicht von uns stammt.
Er wurde entsprechend an Java angepasst. Während manueller Tets waren aber keine Abweichungen gegenüber des Originalalgorithmus zu erkennen.
Bis auf den oben beschriebenen Backslash Bug bei der Browserausgabe.

### Neue Ziele
- Properties Datei für Serverkonfiguration
- HTML Ausgabe der Polyline mit Version 3 der Google Maps Api 