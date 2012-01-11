Sample Outputs
===

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

HTML
---

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

JSON
---

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
    

RAW
---

### Positions of information

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

### Example

    200
    
    1326235079164
    
    51.501445145653555, -0.12338849607747945
    51.4997138231825,51.50317646812461
    -0.1272722324704489, -0.11950475968451
    0.0, 0.0
    epiyHnzW}AIH_DBIIc@?]yBEU[b@yc@g@AqCa@sH{@
    17
    PD?A?DBEF@?P

XML
---

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