Java Polyline Encoder REST Server
===

This is a [RESTful web server](http://en.wikipedia.org/wiki/Representational_state_transfer#RESTful_web_services)
 for creating encoded polylines for [Google Maps](http://maps.google.com) or other possible applications.

Requirements
===

- [Apache Ant](http://ant.apache.org/) (min 1.7.1)
- Java (min 1.6.0_20) 
- Java Compiler javac (min 1.6.0_20)

We tested the Server on the following systems:

- Ubuntu Server 10.04.3 LTS
- Mac OS X 10.6.8
- Windows 7 Professional x64


Supported Formats
===

Input
---

- RAW (simply the points)
- [KML](http://en.wikipedia.org/wiki/Keyhole_Markup_Language)
- KMZ (zipped KML file)
- [GPX](http://en.wikipedia.org/wiki/GPS_eXchange_Format)

Output
---

- RAW
- XML
- HTML (a Google Map with all tracks included via JavaScrip)

Install
===

**Description is for Unix only!**

Clone the current version or simply download the zip

    git clone https://github.com/petpen/JavaPolylineEncoder2.git

Then simply start the ant builder for building the jar file

    ant jar

Move into the build directory. All needed files will be stored there

    cd build


Usage
===

Start the server from the build directory or from every other directory where you store the files.
The default port will be 9998

    java -jar PolylineEncoderServer.jar


Notes
===

If you want to start the [JUnit](http://www.junit.org/) tests you have to tell ant to use the check branch.
He will also be selected if no parameter is given.

    ant check

The test reports can be found under *reports/* the cobertura report is stored in *cob-rep/*
