window.onload = function() {
		$.ajax({
				url: "korisnik/getLogovanje",
				type: "GET",
				success: function(data){
					for(i=0;i<data.formFields.length;i++) {
						if(data.formFields[i].type.name=="string"){
							console.log("ovdje")
							if(data.formFields[i].id == "password")
								$("#formLogin").append(data.formFields[i].label+'<br><input type="password" name ="'+data.formFields[i].id+'" id="'+data.formFields[i].id+'"><br/>');
							else
								$("#formLogin").append(data.formFields[i].label+'<br/><input type="text" name = "'+data.formFields[i].id+'" id="'+data.formFields[i].id+'"><br/>');
							
						}
					}
					
					$("#formLogin").append('<br/><input type="button" value="Sign in" onclick = "login()"><br/>');
				},
				error: function() {
				}
			});
}


function login(){
	
	var $form = $("#formLogin");
	var data = getFormData($form);
	var s = JSON.stringify(data);
	
$.ajax({
		
		url: "korisnik/logovanje",
		type: "POST",
		data: s,
		contentType: "application/json",
		dataType: "text",
		success: function(data){
				if(data == "Uspjesno"){
					alert("Uspjesno ste se ulogovali!");
				//	top.location.href="casopis.html";	
				}else{
					alert("Doslo je do greske");
				//	top.location.href="registration.html";
				}
			
		},
		error: function(data){
			alert(data)
		}
		
	
	});
}