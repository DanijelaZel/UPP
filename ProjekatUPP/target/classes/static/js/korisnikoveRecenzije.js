window.onload = function() {
	$.ajax({
		url: "rad/getKorisnikovRad",
		type: "GET",
		success: function(data){
			for(i=0;i<data.length;i++) {
				$("#listaRadova").append("<div>Naslov rada:"+data[i].naslov+"</div>"+
										 "<div>Apstrakt rada:"+data[i].apstrakt+"</div>"+
										 "<div>Kljucni Pojmovi rada:"+data[i].kljucniPojmovi+"</div>");
				$("#listaPdf").append('<input type="file" name="files"/><br><br>');
				$("#listaPdf").append('<br/><input type="button" value="Primjeni" onclick = "uploadData('+data[i].id+')"><br/>');
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
        url: "/rad/korisnikNapravioIzmjene/"+radID,
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
