Supported Formats
===

Tracks
---
The encoder is able to process multiple tracks, routes and waypoints in one file.
The output will be looped in the track section if there are more encoded tracks in the result.

A sample output can be found in [doc/sample_output.md](https://github.com/petpen/JavaPolylineEncoder2/blob/master/doc/sample_output.md).

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

### Input Format Description

#### RAW

A line represents a single track. A sequence of point must be defined in the following form:

    latitude_1,longitude_1,altitude_1;...;latitude_n,longitude_n,altitude_n

If the altitude is missing it will be replaced with `0.0`


### Output Format Description 

The first part of the output will be a status code with a small message. This will not happen if the result is a Google map.
The rest of the output is depending on the specified output format.
For a sample output read [doc/sample_output.md](https://github.com/petpen/JavaPolylineEncoder2/blob/master/doc/sample_output.md).

#### Status Codes 

###### 200
  - OK

###### 400
  - No file found
  - Invalid link
  - No inputformat specified or not supported.
  - No outputformat specified or not supported.
  - No inputformat specified or not supported. Wrong outputformat specified or not supported.
  - No data found
  - No tracks found.