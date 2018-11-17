function initMap(){
    // Si se aumenta el zoom se ve más cerca. Esta latitud y longitud es para centrar el mapa en ginebra.
    var options = {
      zoom:15,
      center:{lat:3.723905,lng:-76.269237}
    }
    
  geocode(); 
   // Función que sirve para obtener la longitud y latitud de la dirección   
  function geocode(){
   // Dirección que cambiará dependiendo de cada negocio
    var location = document.getElementById('input_formulario:direccion').value;
   // Esto es lo que uso para hacer la petición http 
    axios.get('https://maps.googleapis.com/maps/api/geocode/json',{
      params:{
        address:location,
        key:'AIzaSyCSYfvxFMhQS_zoysw6ywR1udl7-vjaUWc'
      }
    })
    .then(function(response){
      // Imprimir la respuesta
      console.log(response);

      // Latitud y longitud de la dirección    
      var latitud = response.data.results[0].geometry.location.lat;
      var longitud = response.data.results[0].geometry.location.lng;
        
        
        
      // Creación del mapa con las opciones definidas anteriormente
      var map = new google.maps.Map(document.getElementById('map'), options);
      
      // marker para apuntar al negocio. Se podría cambiar el icono por otro o dejar el predeterminado que es rojito  
      var marker = new google.maps.Marker({
      position:{lat:latitud,lng:longitud},
      map:map,
      icon:'https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png'
        
      });  
           
      // información que se muestra cuando se presiona sobre el negocio  
      var infoWindow = new google.maps.InfoWindow({
      content:location
    	  
      });


      //Agregar evento de escucha cuando se presione 
      marker.addListener('click', function(){
      infoWindow.open(map, marker);
      }); 
  

    })
    .catch(function(error){
      console.log(error);
    });
  }


    
  }