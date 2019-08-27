window.onload = function() {
		$.ajax({
						url: "/korisnik/getRecezenti",
						type: "GET",
						contentType:"application/json",
						success: function(data){
							for(i=0;i<data.length;i++) {
									$("#listaRecezenata").append("Ime:" + data[i].name + " " +
																 "Prezime:" + data[i].surname + " " +
																 "Email:" + data[i].email+ " " + 
																 "<input type=\"checkbox\" class = \"checks\" name = "+data[i].email+">");
						}
									$("#listaRecezenata").append("<input type = \"button\" value=\"Izaberi\" onclick=\"izaberiRecezente()\">");
						},
						error: function() {
						}
					});
}

function izaberiRecezente(){
	
	var services = new Array();
	
	var checks = document.getElementsByClassName('checks');
	for(i=0;i<checks.length;i++){
		if(checks[i].checked==true){
			services.push(checks[i].name);
		}
	}
	
	$.ajax({
		url: "rad/izborRecezenata/"+services,
		type: "POST",
		contentType: "application/json",
		dataType: "application/json",
		success: function(data){
			
		},
		error: function() {
		}
	});
	console.log(services)
}