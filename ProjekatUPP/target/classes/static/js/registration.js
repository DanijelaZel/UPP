window.onload = function() {
		$.ajax({
				url: "korisnik/getRegistracija",
				type: "GET",
				success: function(data){
					for(i=0;i<data.formFields.length;i++) {
						if(data.formFields[i].type.name=="string"){
							console.log("ovdje")
							if(data.formFields[i].id == "password")
								$("#formRegistration").append(data.formFields[i].label+'<br><input type="password" name ="'+data.formFields[i].id+'" id="'+data.formFields[i].id+'"><br/>');
							else
								$("#formRegistration").append(data.formFields[i].label+'<br/><input type="text" name = "'+data.formFields[i].id+'" id="'+data.formFields[i].id+'"><br/>');
							
						}
					}
					
					$("#formRegistration").append('<br/><input type="button" value="Sign up" onclick = "registration()"><br/>');
				},
				error: function() {
				}
			});
}


function registration(){
	
	var $form = $("#formRegistration");
	var data = getFormData($form);
	var s = JSON.stringify(data);
	
$.ajax({
		
		url: "korisnik/registracija",
		type: "POST",
		data: s,
		contentType: "application/json",
		dataType: "text",
		success: function(data){
				if(data == "Uspjesno"){
					alert("Uspjesno ste se registrovali!");
					top.location.href="login.html";	
				}else{
					alert("Doslo je do greske");
					
				}
			
		},
		error: function(data){
			alert(data)
		}
		
	
	});
	
}