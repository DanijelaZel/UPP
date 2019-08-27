window.onload = function() {


	$.ajax({
			url: "/rad/getKorisnikovPdf",
			type: "GET",
			success: function(data){
				$("#listaPdf").empty();
				for(i=0;i<data.length;i++) {

					$("#listaPdf").append('<input type="text" name = "'+data[i].naslov+'"value="'+data[i].naslov+'"><br><br>' + 
							'<input type="text" name = "'+data[i].kljucniPojmovi+'"value="'+data[i].kljucniPojmovi+'"><br><br>'+
							'<input type="text" name = "'+data[i].apstrakt+'"value="'+data[i].apstrakt+'"><br><br>');
						$("#listaPdf").append('<input type="file" name="files"/><br><br>');
						$("#listaPdf").append('<br/><input type="button" value="Izmijeni" onclick = "uploadData('+data[i].id+')"><br/>');
					}
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


function uploadData(radID) {

    // Get form
    var form = $('#fileUploadForm')[0];
    console.log(form)
    var data = new FormData(form);
    console.log(data)
    
    $("#btnSubmit").prop("disabled", true);

    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "/rad/updatePdf/"+radID,
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