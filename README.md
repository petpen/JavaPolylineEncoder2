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
The default port will be 9998

    java -jar PolylineEncoderServer.jar


Testing
===

JUnit
---

If you want to start the [JUnit](http://www.junit.org/) tests you have to tell ant to use the check branch.
He will also be selected if no parameter is given.

    ant check

The test reports can be found in `reports` the cobertura report is stored in `cob-rep`

Testfiles
---

Various testfiles can be found inside the folder `testfiles` and its subdirectories.
The file `testfiles/readFile.test` is only needed for junit tests.

Supported Formats
===

Tracks
---
The encoder is able to process multiple tracks, routes and waypoints in one file.
The output will be looped in the track section if there are more encoded tracks in the result.

Input
---

- RAW (simply the points)
- [KML](http://en.wikipedia.org/wiki/Keyhole_Markup_Language)
- KMZ (zipped KML file)
- [GPX](http://en.wikipedia.org/wiki/GPS_eXchange_Format)

Output
---

- HTML (a Google Map with all tracks included via JavaScript)
- [JSON](http://www.json.org/)
- XML
- RAW

### Output Format Description 

The first part of the output will be a status code with a small message. This will not happen if the result is a Google map.

#### Status Codes 

- **200**
  - OK
  
- **400**
  - No file found
  - Invalid link
  - No inputformat specified or not supported.
  - No outputformat specified or not supported.
  - No inputformat specified or not supported. Wrong outputformat specified or not supported.
  - No data found
  - No tracks found.

#### Sample Outputs

All sample outputs are based on `testfiles/kml/singleTrack.kml`. This track shows a track from the Westminster Abbey to the London Eye.

This produces the following data:

- Encoded points `epiyHnzW}AIH_DBIIc@?]yBEU[b@yc@g@AqCa@sH{@`
- Encoded zoom levels `PD?A?DBEF@?P`
- Number of points `17`
- Bounds
  - Min latitude `51.4997138231825`
  - Max latitude `51.50317646812461`
  - Min longitude `-0.1272722324704489`
  - Max longitude `-0.11950475968451`
  - Min altitude `0.0`
  - Max altitude `0.0`
- Center
  - Latitude `51.501445145653555`
  - Longitude `-0.12338849607747945`
- Creation Date (time in milliseconds) `1326234539831`

#### HTML

    [...]
    
    encodedPolylines.push(new GPolyline.fromEncoded({
        color: "#ff3333",
        weight: 3,
        points: "epiyHnzW}AIH_DBIIc@?]yBEU[b@yc@g@AqCa@sH{@",
        levels: "PD?A?DBEF@?P",
        zoomFactor: 2,
        numLevels: 18
    }));
    
    [...]

#### JSON

    [...]
    
    {
      "status": {
        "code": 200,
        "message": ""
      },
      "points": [
        {
          "encoded": "epiyHnzW}AIH_DBIIc@?]yBEU[b@yc@g@AqCa@sH{@",
          "count": 17,
          "levels": "PD?A?DBEF@?P"
        }
      ],
      "bounds": {
        "lat": {
          "min": 51.4997138231825,
          "max": 51.50317646812461
        },
        "lng": {
          "min": -0.1272722324704489,
          "max": -0.11950475968451
        },
        "alt": {
          "min": 0.0,
          "max": 0.0
        },
        "center": {
          "lat": 51.501445145653555,
          "lng": -0.12338849607747945
        }
      },
      "created": 1326234539831
    }
    [...]
    

#### RAW

###### Positions of information

    statusCode
    statusMessage
    createdDate
    
    centerLat, centerLng
    minLat,maxLat
    minLng, maxLng
    minAlt, maxAlt
    encodedPoints [Track n]
    pointCount [Track n]
    encodedLevels [Track n]
    [...]

`Track n` means the nth track. Any track will be directly underneath the other one. No empty lines

###### Example

    200
    
    1326235079164
    
    51.501445145653555, -0.12338849607747945
    51.4997138231825,51.50317646812461
    -0.1272722324704489, -0.11950475968451
    0.0, 0.0
    epiyHnzW}AIH_DBIIc@?]yBEU[b@yc@g@AqCa@sH{@
    17
    PD?A?DBEF@?P

#### XML

    <polylineencoder>
      <status code="200"></status>
      <points count="17">
        <encoded>epiyHnzW}AIH_DBIIc@?]yBEU[b@yc@g@AqCa@sH{@</encoded>
        <levels>PD?A?DBEF@?P</levels>
      </points>
      <bounds>
        <lat min="51.4997138231825" max="51.50317646812461"/>
    
        <lng min="-0.1272722324704489" max="-0.11950475968451"/>
        <alt min="0.0" max="0.0"/>
      </bounds>
      <center lat="51.501445145653555" lng="-0.12338849607747945"/>
      <created timestamp="1326235699756"/>
    </polylineencoder>