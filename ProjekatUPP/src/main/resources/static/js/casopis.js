window.onload = function() {
	
	$.ajax({
			url: "/casopis/getAll",
			type: "GET",
			success: function(data){
				$(".listaCasopisa").empty();
				for(i=0;i<data.length;i++) {
						$(".listaCasopisa").append('Ime casopisa:<br>'+data[i].ime+'<br/>' + 
												   'ISSNBr:<br>'+ data[i].issnBr+'<br/>'+
												   '<br/><input type="button" value="Izaberi casopis" onclick = "biranjeCasopisa('+data[i].issnBr+')"><br/>');
					}
			},
			error: function() {
			}
		});
	
}

function biranjeCasopisa(issnBr){
	
	sessionStorage.setItem('casopisId',issnBr);
	
	$.ajax({
		url: "casopis/biranjeCasopisa/"+issnBr,
		type: "POST",
		contentType: "application/json",
		dataType: "application/json",
		success: function(data){
			if(data == "Uspjesno"){
				top.location.href="rad.html";	
			}
			
		},
		error: function() {
		}
	});
}