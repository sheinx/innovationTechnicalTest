$(document).ready(function(){
	
	$(".datepicker").datepicker({});
	
	$(".checkbox-switch").bootstrapSwitch();
	
	//Propiedades generales de datatable de la cuales extenderan nuestras tablas.
	$.extend( true, $.fn.dataTable.defaults, {
		searching: false,
		processing: true,
        language: {
        	"processing":   "Procesando...",
            "lengthMenu": 	"Mostrar _MENU_ elementos por p&aacute;gina",
            "zeroRecords": 	"No se han encontrado resultados",
            "emptyTable": "No hay datos disponibles",
            "info": 		"Mostrando p&aacute;gina _PAGE_ de _PAGES_",
            "infoEmpty": 	"No se han encontrado resultados",
            "infoFiltered": "(filtrado de _MAX_ elementos)",
            "search":         "Buscar:",
            "paginate": {
                "first":      "Primero",
                "last":       "&Uacute;ltimo",
                "next":       "Siguiente",
                "previous":   "Anterior"
            },
        }
    });
	
	/* Asistente */
	// Deshabilitamos campos
	$("#asistente-form :input").prop('disabled', true);
	
//	// Pasos del asistente
//	$(".asistente-step:not(:first)").hide();
//	$(".next-step").on("click", function(e){
//		e.preventDefault();
//		//var stepToShow = $(this).attr("rel");
//		var stepToShow = $(this).data("next-step");
//		
//		$(".asistente-step").hide();
//		$(stepToShow).show();
//		$($(stepToShow).data("fields")).prop('disabled', false);
//					
////		var disableFieldsTecnico = ${disableFieldsTecnico};
////		$($(stepToShow).data("fields-tecnico")).prop('disabled', disableFieldsTecnico);
//		
//		var $stepArrow = $(stepToShow + " .box-arrow")
//		//var arrowrefel = $stepArrow.attr("rel");
//		var arrowrefel = $stepArrow.data("input-related");
//		$elref = $(arrowrefel);
//		var elrefpos = $elref.offset();
//		
//		$stepArrow.css("position", "absolute");
//		$stepArrow.css("top", 0); //resetamos el valor
//		var arrowpos = $stepArrow.offset();
//		
//		$stepArrow.css("top", elrefpos.top - arrowpos.top);
//	});
	
	//Al poner el foco en algun input volvemos al paso correspondiente
	$("#asistente-form :input").on("focus", function(e){
		//recorremos todos los step y buscamos el id del input en el campo data-fields
		var $thisInput = $(this);
		$(".asistente-step").each(function(){
			var fieldsVinculadosAlStep = $(this).data("fields");
			if(typeof fieldsVinculadosAlStep != 'undefined'){
				if( fieldsVinculadosAlStep.indexOf($thisInput.attr("id")) != -1){
					$(".asistente-step").hide();
					$(this).show();
				}
			}
		})
	});
	
	
	//Deshabilita asistente
	$("#disable-asistente").on("change", function(e){
		if($(this).is(":checked")){
			$(".form-asistente-wrapper").addClass("asistente-disabled");
			//guardamos el estado de habilitado de los inputs por si se vuelve a habilitar el asistente
			$("#asistente-form :input").each(function(){
				$(this).data("prev-disabled-state", $(this).prop("disabled"));
			});
			$("#asistente-form :input").prop('disabled', false);
		}
	});
	$("#enable-asistente").on("click", function(e){
		e.preventDefault();
		$(".form-asistente-wrapper").removeClass("asistente-disabled");
		$('#disable-asistente').attr('checked', false);
		
		$("#asistente-form :input").each(function(){
			$(this).prop("disabled", $(this).data("prev-disabled-state"));
		});
	});
	
	$("#clasificacion").on("change", function(){
		$("#step-clasificacion-next-step-button").show();
	});
	
	$("input[name='mas_usuarios']").on("change", function(){
		$("#step-mas_usuarios-next-step-button").show();
	});
	
	//Autocomplete asistente
	var availableTags = [
     	"Adriano",
     	"ActionScript",
     	"AppleScript",
     	"Asp",
     	"BASIC",
     	"C",
     	"C++",
     	"Clojure",
     	"COBOL",
     	"ColdFusion",
     	"Erlang",
     	"Fortran",
     	"Groovy",
     	"Haskell",
     	"Java",
     	"JavaScript",
     	"Lisp",
     	"Perl",
     	"PHP",
     	"Python",
     	"Ruby",
     	"Scala",
     	"Scheme"
     ];
	
	var lastAutocompleteSearchResults = null;
	
	$("#asistente-form .autocomplete" ).autocomplete({
    	 source: function(request, callback){
    		 //Peticion ajax para obtener los terminos (calcula results = availableTags)
    		 
    		 var results = new Array();
    		 $.each(availableTags, function(index, value){
    			 if(value.toLowerCase().indexOf(request.term.toLowerCase()) != -1){
    				 results.push(value);
    			 }
    		 })
    		 
    		 //Almacenamos la busqueda para usarla como recomendaciones
    		 lastAutocompleteSearchResults = results;
    		 
    		 //Le indicamos al widget las tipolgias
    		 callback(results);
    	 }
	});
     
     
     $("#asistente-form .autocomplete" ).on("autocompleteselect", function( event, ui ) {
    	 //ui es el objeto seleccionado (ui.item.label y ui.item.value)
    	 
    	 //Peticion ajax para obtener el detalle de la tipologia (ui.item.value)
    	 
    	 //Indicamos titulo y detalle de la tipologia
    	 //step-tipologia-selected-info -> <strong>titulo:</strong> descripcion
    	 $("#step-tipologia-selected-info").html("<strong>"+ui.item.label+":</strong> ... Descripci&oacute;n de " + ui.item.label);
    	 
    	 //Verificamos si tiene base de datos del conocimiento
    	 // $("#step-tipologia-selected-bd-conocimiento").hide(); -> si no tengo url_xwiki
    	 $("#step-tipologia-selected-bd-conocimiento").show();
    	 $("#step-tipologia-selected-bd-conocimiento-items").empty();
    	 $("#step-tipologia-selected-bd-conocimiento-items").html("<li><a href='http://www.google.es' target='_blank'>"+ui.item.label+"</li>")
    	 
    	 //Verificamos si podemos mostrar tipologias alternativas
    	 $("#step-tipologia-selected-related").hide();
    	 if(lastAutocompleteSearchResults != null && lastAutocompleteSearchResults.length > 0){
    		 $("#step-tipologia-selected-related").show();
        	 $("#step-tipologia-selected-related-items").empty();
        	 for(var i=0; i<lastAutocompleteSearchResults.length; i++){
        		 $("#step-tipologia-selected-related-items").append("<li><a href='#' target='_blank'>"+lastAutocompleteSearchResults[i]+"</li>")
        	 }
    	 }
    	 
    	 
    	 //Ocultamos el div inicial y mostramos el de detalle
    	 //step-tipologia -> step-tipologia-selected
    	 $("#step-tipologia").hide();
    	 $("#step-tipologia-selected").show();
    	 
    	 //Habilitamos el boton siguiente
    	 $("#step-tipologia-next-step-button").show();
     });
});