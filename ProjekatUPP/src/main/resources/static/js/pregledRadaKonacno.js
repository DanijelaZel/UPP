window.onload = function() {
	$.ajax({
		url: "/rad/getRad",
		type: "GET",
		contentType:"application/json",
		success: function(data){
			for(i=0;i<data.formFields.length;i++) {
				if(data.formFields[i].type.name=="string"){	
				$("#formComment").append('<input type="text" name = "'+data.formFields[i].id+'"placeholder="'+data.formFields[i].label+'"><br><br>');
				}
			}
			 
					
		},
		error: function() {
		}
	});
	
		$.ajax({
				url: "rad/getRecezentovRad",
				type: "GET",
				success: function(data){
					for(i=0;i<data.length;i++) {
						$("#listaRadova").append("<div>Naslov:"+data[i].naslov+"</div>"+
												"<div>Kljucni pojmovi:"+data[i].kljucniPojmovi+"</div>"+
												"<div>Apstrakt:"+data[i].apstrakt+"</div>");
						 $('#pdf').append("<a href=/rad/download/" + data[i].id + ">Download file</a>");
						 $("#formComment").append('<div><input type="button" value="Obavi recenziju" onclick = "obaviRecenziju('+data[i].id+')"></div>');
						 
					}
				},
				error: function() {
				}
			});
}

function obaviRecenziju(radId){
	
	var $form = $("#formComment");
	var data = getFormData($form);
	var s = JSON.stringify(data);
	
	$.ajax({
		url: "rad/obavljanjeRecenzije/"+radId,
		type: "POST",
		data: s,
		contentType: "application/json",
		dataType: "application/json",
		success: function(data){
			
		},
		error: function() {
		}
	});
}