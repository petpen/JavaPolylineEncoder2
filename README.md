Java Polyline Encoder REST Server
===

This is a [RESTful web server](http://en.wikipedia.org/wiki/Representational_state_transfer#RESTful_web_services)
 for creating encoded polylines for [Google Maps](http://maps.google.com), other possible applications or storing them in a database.

It is based on the encoder of [Mark Rambow](https://github.com/markrambow/JavaPolylineEncoder).
Which in turn is based on the polyline encoder of [Marc McClure](http://facstaff.unca.edu/mcmcclur/GoogleMaps/EncodePolyline/).

The server itself makes use of [Jersey](http://jersey.java.net/).

Requirements
===

- [Apache Ant](http://ant.apache.org/) (min 1.7.1)
- Java (min 1.6.0_20) 
- Java Compiler javac (min 1.6.0_20)
- git (optional for installation)

We tested the Server on the following systems:

- Ubuntu Server 10.04.3 LTS
- Mac OS X 10.6.8
- Windows 7 Professional x64

Installation
===

**Description is for Unix only!**

Clone the current version or simply download the zip

    git clone https://github.com/petpen/JavaPolylineEncoder2.git

If you are in the directory cloned/downloaded directory start the ant builder for building the jar file

    ant jar

Move into the build directory. All needed files will be stored there

    cd build


Usage
===

Start the server from the build directory or from every other directory where you store the files.
The default port will be `9998`

    java -jar PolylineEncoderServer.jar

You can test the Java Polyline Encoder right away in your browser with a dynamic formular (requires activated JavaScript).

    http://localhost:9998/
		
You don't have to use the form only. Make your own or send requests over GET with the file link, POST the coords as urlencoded or POST a file as `multipart/form-data`.
The URL is always `http://localhost:9998/encoder/<input>/<output>`. For GET use the param `link` and for POST it is `coords` respectively `fileData`.

Testing
===

See [doc/testing.md](https://github.com/petpen/JavaPolylineEncoder2/blob/master/doc/testing.md)

Supported Formats
===

See [doc/supported_formats.md](https://github.com/petpen/JavaPolylineEncoder2/blob/master/doc/supported_formats.md)

Sample Output
===

See [doc/sample_output.md](https://github.com/petpen/JavaPolylineEncoder2/blob/master/doc/sample_output.md)