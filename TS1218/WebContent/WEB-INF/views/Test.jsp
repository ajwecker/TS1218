<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Test Viewer</title>
<link rel="stylesheet" href="https://unpkg.com/leaflet@1.0.2/dist/leaflet.css" />

<script src="https://unpkg.com/leaflet@1.0.2/dist/leaflet.js"></script>


<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>

<script src="https://unpkg.com/leaflet-iiif@1.2.1"></script>
</head>
<body class="${dir}">

<!-- div id="openseadragon1" style="width: 100%; height: 250; "></div>

		<script src="/openseadragon/openseadragon.min.js"></script>
		<script type="text/javascript">
    		var viewer = OpenSeadragon({
    id: "openseadragon1",
    prefixUrl: "images/openseadragon/",
    immediateRender: true,
    showNavigator: true,
     preserveViewport: true,
  
    tileSources: "http://localhost:8182/iiif/2/sample.jpg/info.json"
    /* tileSources:   [{
      "@context": "http://iiif.io/api/image/2/context.json",
      "@id": "http://localhost:8182/iiif/2/sample.jpg",
      "height": 243,
      "width": 243,
      "profile": [ "http://iiif.io/api/image/2/level2.json" ],
      "protocol": "http://iiif.io/api/image",
      "tiles": [{
        "scaleFactors": [ 1, 2, 4, 8, 16, 32 ],
        "width": 243
      }] */
});


</script -->


<div id="map" style="width:400px;height:400px;"></div>


<script>
  setTimeout(function(){
    var map = L.map('map', {
      center: [0, 0],
      crs: L.CRS.Simple,
      zoom: 0
    });
    
   // "http://www.e-codices.unifr.ch/metadata/iiif/bge-cl0146/canvas/bge-cl0146_160.json
    //http://www.e-codices.unifr.ch/en/bge/cl0146/481/0/Sequence-1316
    	//L.tileLayer.iiif('http://www.e-codices.unifr.ch/metadata/iiif/bge-cl0146/canvas/bge-cl0146_002.json').addTo(map);
   // L.tileLayer.iiif('http://localhost:8182/iiif/2/sample.jpg/info.json').addTo(map);
    L.tileLayer.iiif('http://localhost:8182/iiif/2/bge-cl0146_007.jpg/info.json').addTo(map);
 
  var marker = L.marker([-300, 300]).addTo(map);
  /*
  var polygon = L.polygon([
	    [-300,150],
	    [-300,300],
	    [-325,300],
	    [-325,150]
	]).addTo(mymap);

   */
   
    var circle = L.circle([-400,150], {
        color: 'red',
        fillColor: '#f03',
        fillOpacity: 0.5,
        radius: 30
    	}).addTo(mmap);
  
      //  L.tileLayer.iiif('https:\/\/iiif.lib.ncsu.edu\/iiif\/mc00042-005-ff0033-000-001_0001\/info.json').addTo(map);
    
    
    
   // L.tileLayer.iiif('https:\/\/iiif.lib.ncsu.edu\/iiif\/mc00042-005-ff0033-000-001_0001\/info.json').addTo(map);
  }, 2000);
</script>
<br>
<img alt="Geneva 146 Page 7 Line 13" width="480" height="50" src="http://localhost:8182/iiif/2/bge-cl0146_007.jpg/511,4684,4155,449/full/0/default.jpg" >
</body>
</html>