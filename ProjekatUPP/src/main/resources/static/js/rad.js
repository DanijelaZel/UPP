window.onload = function() {
				$.ajax({
						url: "/naucnaOblast/getAll",
						type: "GET",
						contentType:"application/json",
						success: function(data){
							for(i=0;i<data.length;i++) {
									$("#naucnaOblast").append("<option id="+data[i].id+">"+data[i].naziv+"</option>");
							}
							
						},
						error: function() {
						}
					});

				$.ajax({
					url: "/rad/getRad",
					type: "GET",
					contentType:"application/json",
					success: function(data){
						for(i=0;i<data.formFields.length;i++) {
							if(data.formFields[i].type.name=="string"){	
							$("#uFormi").append('<input type="text" name = "'+data.formFields[i].id+'"placeholder="'+data.formFields[i].label+'"><br><br>');
							}
						}
						$("#uFormi").append('<input type="file" name="files"/><br><br>');
						 
								
					},
					error: function() {
					}
				});
}


$(document).ready(function () {

    $("#btnSubmit").click(function (event) {

        event.preventDefault();

        uploadData();

    });
});


function uploadData() {

    // Get form
    var form = $('#fileUploadForm')[0];
    console.log(form)
    var data = new FormData(form);
    
    var casopisId = sessionStorage.getItem('casopisId');
    console.log(casopisId)
    console.log(data)
    var naucnaOblast = $('select[id="naucnaOblast"] option:selected').val();
    console.log(naucnaOblast)
    
    $("#btnSubmit").prop("disabled", true);

    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "/rad/add/"+casopisId+"/"+naucnaOblast,
        data: data,
        processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
        	$('#result').empty();
            $("#result").text(data);
            console.log("SUCCESS : ", data);
            $("#btnSubmit").prop("disabled", false);

        },
        error: function (e) {
        	$('#result').empty();
            $("#result").text(e.responseText);
            console.log("ERROR : ", e);
            $("#btnSubmit").prop("disabled", false);

        }
    });

}