function filtraRaca() {
    var especie = $("#especie").val();
    
    let dropdown = $('#raca');    

    dropdown.empty();

    $.ajax({
        type : 'GET',
        url : '/clientes/animais/listaRacas',

        crossDomain : true,
        data : ({
            especie : especie
        }),
        contentType : "application/json; charset=utf-8",
        dataType : "json",

        success : function(data) {
            console.log("sucesso");

            console.log(data);
            var listaDados = data; // Lista retornada pelo banco
            dropdown.append($('<option></option>').attr('value', '').text('Selecione a raça'));
            
            for (var i = 0; i < listaDados.length; i++) {
                console.log(listaDados[i]);
                dropdown.append($('<option></option>').attr('value', listaDados[i].id).text(listaDados[i].nome));
            }

        },
        error : function(data) {
        	console.log(data)
            console.log("erro na funçao");
        }
      
    });

}
