function getPlaces(pac_input){
   var input = document.getElementById(pac_input);
   console.log("hello");
        var options = {
            types : ['address']
        };
        var latitude;
        var longitude;
        autocomplete = new google.maps.places.Autocomplete(input, options);
        autocomplete.addListener('place_changed', function(){
            var place = autocomplete.getPlace();
            var coordinates = place.geometry.location;
            latitude = coordinates.lat();
            longitude = coordinates.lng();
            console.log("Lat - " + latitude + " Long - " + longitude );
        });
}

function initMap() {
    var myLatLng = {lat: 37.3352, lng: -121.8811};

    var map = new google.maps.Map(document.getElementById('map'), {
      zoom: 4,
      center: myLatLng
    });

    var marker = new google.maps.Marker({
      position: myLatLng,
      map: map,
      title: 'Hello World!'
    });
  }
