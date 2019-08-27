window.onload = function() {
	
	
		$.ajax({
				url: "casopis/getUrednikovCasopis",
				type: "GET",
				success: function(data){
					for(i=0;i<data.length;i++) {
						$("#listaRadova").append("<div>Naslov:"+data[i].naslov+"</div>"+
												"<div>Kljucni pojmovi:"+data[i].kljucniPojmovi+"</div>"+
												"<div>Apstrakt:"+data[i].apstrakt+"</div>"+
												"<div>Koautori:"+data[i].koautori+"</div>"+
												'<div><input type="button" value="Dobar rad" onclick = "dobar('+data[i].id+')"></div>'+
												'<div><input type="button" value="Los rad" onclick = "los('+data[i].id+')"></div>');
						 $('#pdf').append("<a href=/rad/download/" + data[i].id + ">Download file</a>" + 
								 '<div><input type="button" value="Dobar rad" onclick = "dobarPdf('+data[i].id+')"></div>'+
									'<div><input type="button" value="Los rad" onclick = "losPdf('+data[i].id+')"></div>');
					}
				},
				error: function() {
				}
			});
}


function dobar(idRada){
	console.log(idRada)
	
	$.ajax({
		url: "rad/pregledRadaDobar/"+idRada,
		type: "POST",
		contentType: "application/json",
		dataType: "application/json",
		success: function(data){
			
		},
		error: function() {
		}
	});
}

function los(idRada){
	console.log(idRada)
	
	$.ajax({
		url: "rad/pregledRadaLos/"+idRada,
		type: "POST",
		contentType: "application/json",
		dataType: "application/json",
		success: function(data){
			
		},
		error: function() {
		}
	});
}


function dobarPdf(idRada){
	console.log(idRada)
	
	$.ajax({
		url: "rad/pregledRadaDobarPdf/"+idRada,
		type: "POST",
		contentType: "application/json",
		dataType: "application/json",
		success: function(data){
			
		},
		error: function() {
		}
	});
}

function losPdf(idRada){
	console.log(idRada)
	
	$.ajax({
		url: "rad/pregledRadaLosPdf/"+idRada,
		type: "POST",
		contentType: "application/json",
		dataType: "application/json",
		success: function(data){
			
		},
		error: function() {
		}
	});
}
