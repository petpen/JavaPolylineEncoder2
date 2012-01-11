Dokumentation zu Projekt
===

Projektname
---

- Java Polyline Encoder mit RESTful Webservice

Teilnehmer
---

- Martin Bormeister
- Peter Pensold

Ziele
---

- Encoder
 - Refactoring
 - Tests

- Server


- Eingabeformate
 - GPX

- Ausgabeformate
 - JSON (kodierte Punkte, [Name, Beschreibung])
 - RAW (reine kodierte Daten)

Nice to Have
---

- Zusätzliche Eingabeformate
 - KML
 - KMZ (komprimierte KML)
 - URL (linkt auf Datei, die automatisch geparst wird)

- Zusätzliche Ausgabeformate
 - HTML (mit Google Map)
 - RAW
 - XML

Technologien
---


Probleme
---

###### Backslash Bug

###### Ant Jar Erstellung

###### Jersey

Architektur
---


Design
---


Ergebnis
---

### Erledigt
- Parser
 - Erstellung von Parsern nach Eingabeformaten
 - Parser können sowohl Dateien als auch Daten (via String) verarbeiten. Außer KMZ, da dies ein Binärformat ist.
 - Es werden mehrere Tracks innerhalb einer Datei geparst
- Refactoring des Polyencoders
 - Aufteilung in mehrere Klassen
 - Logische Trennung von Encoder und Methoden, welche nicht direkt zum Encoder gehören
 - Umbenennung von einigen Klassen
 - Packagestruktur angepasst
 - `Util`-Klasse für häufig verwendete Methoden erstellt, welche nicht unbedingt in verschiedene Klassen gehören
   - Möglich wäre noch eine Aufteilung in `FileUtil` für Methoden welche sich nur mit Dateien beschäftigen
- Umsetzung aller genannten Eingabeformate: `GPX, KML, KMZ, URL`
- Umsetzung aller genannten Ausgabeformate: `HTML, JSON, RAW, XML`

### Einschränkungen
- Ausgabe / Parsen
 - Es werden nur die nötigsten Attribute für einen Track geparst, daher wird auf zusätzliche Attribute (Bsp. Name des Tracks) kein Wert gelegt
 - Die Bounds beziehen sich nur auf den ersten Track