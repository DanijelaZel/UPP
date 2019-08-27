window.onload = function() {
	$.ajax({
		url: "rad/getRecenzije",
		type: "GET",
		success: function(data){
			for(i=0;i<data.length;i++) {
				$("#listaRecenzija").append("<div>Autor recenzije:"+data[i].autorRecenzije.name+"</div>"+
										"<div>Komentar:"+data[i].komentarZaUrednika+"</div>"+
										"<div>Naslov rada:"+data[i].rad.naslov+"</div>");			
			}
			$("#listaRecenzija").append( "<div><input type=\"button\" value=\"Dobar rad\" onclick = dobarRad()></div>"+
					'<div><input type="button" value="Los rad" onclick = "losRad()"></div>' +
					'<div><input type="button" value="Veca izmjena" onclick = "vecaIzmjena()"></div>'+
					'<div><input type="button" value="Manja izmjena" onclick = "manjaIzmjena()"></div>');
		},
		error: function() {
		}
	});
}



function dobarRad(){
	
	
	$.ajax({
		url: "rad/urednikDobarRad",
		type: "POST",
		contentType: "application/json",
		dataType: "application/json",
		success: function(data){
			
		},
		error: function() {
		}
	});
}

function losRad(){
	$.ajax({
		url: "rad/urednikLosRad",
		type: "POST",
		contentType: "application/json",
		dataType: "application/json",
		success: function(data){
			
		},
		error: function() {
		}
	});
}


function vecaIzmjena(){
	
	
	$.ajax({
		url: "rad/urednikVecaPromjena",
		type: "POST",
		contentType: "application/json",
		dataType: "application/json",
		success: function(data){
			
		},
		error: function() {
		}
	});
}


function manjaIzmjena(){
	
	
	$.ajax({
		url: "rad/urednikManjaPromjena",
		type: "POST",
		contentType: "application/json",
		dataType: "application/json",
		success: function(data){
			
		},
		error: function() {
		}
	});
}