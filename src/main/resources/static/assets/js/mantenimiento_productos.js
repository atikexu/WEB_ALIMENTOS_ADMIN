var listaAlimentariosCache = new Object();
var listaProductosCache = new Object();
var listaDocumentosCache = new Object();

var tbl_det_productos = $('#tbl_det_productos');
var tbl_det_documentos = $('#tbl_det_documentos');
var tbl_det_estados = $('#tbl_det_estados');


var frm_dat_generales = $('#frm_dat_generales');
var frm_det_documentos = $('#frm_det_documentos');
var frm_det_productos = $('#frm_det_productos');

var medTransporteDon = "";
var table11 = $('#dataTable3');

$(document).ready(function() {
	
	var table = $('#dataTable3').DataTable();
	$('#dataTable3 tbody').on( 'click', 'button', function () {
		
		var inputfile = $("#imagenMulti");    
		inputfile.replaceWith(inputfile.val('').clone(true));
	    $('#modal_titulo').html('Actualizar Producto');
		//
	
		var datos= (table.row( this ).data());
		$('#idProducto').val($.trim(datos[0]));
		console.log("categria "+$.trim(datos[1]));
		console.log("subcategria "+$.trim(datos[2]));
	    $('#idCategoria1').val($.trim(datos[1]));
	    $('#idSubCategoria1').val($.trim(datos[2]));
	    console.log($.trim(datos[2])=='0');
	    if($.trim(datos[1])!='9'){
	    	$('#idSubCategoria1').prop('disabled', true);
	    }else{
	    	$('#idSubCategoria1').prop('disabled', false);
	    }
	    $('#nombre').val($.trim(datos[3]));
	    $('#descripcion').val($.trim(datos[4]));
	    $('#estado').val($.trim(datos[5]));
	    var urlImagen = 'http://comprayventaperu.pe/alimentoselectos.pe/alimentosftp/'+$.trim(datos[6]);
        $('#nombreArchivo').attr('src', urlImagen);
        $('#nombreImagen').val($.trim(datos[6]));
        $("#divlabel label[for=inputGroupFile01]").text($.trim(datos[6]));
	    $('#div_producto').modal('show');
	} );
	
	
    

	
	$('#href_doc_nuevo').click(function(e) {
		e.preventDefault();
		$('#h4_tit_no_alimentarios').html('Nuevo Documento');
		$('#frm_det_documentos').trigger('reset');
		
		$('#txt_doc_fecha').datepicker('setDate', new Date());
		$('#hid_cod_documento').val('0');
		$('#hid_cod_act_alfresco').val('');
		$('#hid_cod_ind_alfresco').val('');
		$('#txt_descripcion_doc').val('');
		$('#txt_sub_archivo').val(null);
		$('#div_det_documentos').modal('show');
		
	});
	
	$('#href_doc_editar').click(function(e) {
		e.preventDefault();

		var indices = [];
		var tbl_det_documentos = $('#tbl_det_documentos').DataTable();
		tbl_det_documentos.rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_det_documentos.rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);
			}
		});
		
		if (indices.length == 0) {
			addWarnMessage(null, mensajeValidacionSeleccionarRegistro);
		} else if (indices.length > 1) {
			addWarnMessage(null, mensajeValidacionSeleccionarSoloUnRegistro);
		} else {
			
			var obj = listaDocumentosCache[indices[0]];
			
			$('#h4_tit_documentos').html('Actualizar Documento');
			$('#frm_det_documentos').trigger('reset');
			$('#hid_cod_documento').val(obj.idDocumentoIngreso);			
			$('#sel_tipo_documento').val(obj.idTipoDocumento);
			$('#txt_nro_documento').val(obj.nroDocumento);
			$('#txt_doc_fecha').val(obj.fechaDocumento);
			$('#txt_descripcion_doc').val(obj.observacion);
			$('#hid_cod_act_alfresco').val(obj.codAlfresco);
			$('#hid_cod_ind_alfresco').val('');
			$('#txt_lee_sub_archivo').val(obj.nombreArchivo);
			$('#txt_sub_archivo').val(null);
			
			$('#div_det_documentos').modal('show');
		}
		
	});
	
	$('#href_doc_eliminar').click(function(e) {
		e.preventDefault();
		
		var indices = [];
		var codigo = ''
		tbl_det_documentos.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_det_documentos.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);
				var idDocumentoDonacion = listaDocumentosCache[index].idDocumentoIngreso;
				codigo = codigo + idDocumentoDonacion + '_';
			}
		});
		
		if (!esnulo(codigo)) {
			codigo = codigo.substring(0, codigo.length - 1);
		}
		
		if (indices.length == 0) {
			addWarnMessage(null, mensajeValidacionSeleccionarRegistro);
		} else {
			var msg = '';
			if (indices.length > 1) {
				msg = mensajeConfirmacionEliminacionVariosRegistros;
			} else {
				msg = mensajeConfirmacionEliminacionSoloUnRegistro;
			}
			
			swal({
				  title: 'Está seguro?',
				  text: msg,
				  type: 'warning',
				  showCancelButton: true,
				  confirmButtonColor: '#3085d6',
				  cancelButtonColor: '#d33',
				  confirmButtonText: 'Aceptar',
				  cancelButtonText: 'Cancelar',
				}).then(function () {
					loadding(true);
					
					var params = { 
						arrIdDocumentoDonacion : codigo,
						idIngreso : $('#hid_id_ingreso').val()
					};
			
					consultarAjax('POST', '/donacionesIngreso/registro-donacionesIngreso/eliminarDocumentoDonacionIngreso', params, function(respuesta) {
						if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
							loadding(false);
							addErrorMessage(null, respuesta.mensajeRespuesta);
						} else {
							listarDocumentoDonacion(true);
							addSuccessMessage(null, respuesta.mensajeRespuesta);							
						}
					});
				  swal(
					'Eliminado!',
					'Se ha eliminado satisfactoriamente.',
					'success'
				  )
				});
			
			
		}
		
	});
	
});

function inicializarDatos() {
		

	$('#txt_sub_archivo').change(function(e) {
		e.preventDefault();
	    var file_name = $(this).val();
	    var file_read = file_name.split('\\').pop();
	    $('#txt_lee_sub_archivo').val($.trim(file_read).replace(/ /g, '_'));
	    if (esnulo($('#hid_cod_documento').val())) {
	    	$('#hid_cod_ind_alfresco').val('1'); // Nuevo registro
	    } else {
	    	$('#hid_cod_ind_alfresco').val('2'); // Registro modificado
	    }
	   
	    frm_det_documentos.bootstrapValidator('revalidateField', 'txt_lee_sub_archivo');	    
	});
	
	tbl_det_documentos.on('click', '.btn_exp_doc', function(e) {
		e.preventDefault();
		
		var id = $(this).attr('id');
		var name = $(this).attr('name');
		if (!esnulo(id) && !esnulo(name)) {
			descargarDocumento(id, name);
		} else {
			addInfoMessage(null, mensajeValidacionDocumento);
		}
		
	});

}

function descargarDocumento(codigo, nombre) {	
	loadding(true);
	var url = VAR_CONTEXT + '/common/archivo/exportarArchivo/'+codigo+'/'+nombre+'/';	
	$.fileDownload(url).done(function(respuesta) {
		loadding(false);	
		if (respuesta == NOTIFICACION_ERROR) {
			addErrorMessage(null, mensajeReporteError);
		} else {
			addInfoMessage(null, mensajeReporteExito);
		}		
	}).fail(function (respuesta) {
		loadding(false);
		addErrorMessage(null, mensajeReporteError);
	});	
}

function listarDocumentoDonacion(indicador) {
	var params = { 
		idIngreso : $('#hid_id_ingreso').val()
	};			
	
	consultarAjaxSincrono('GET', '/donacionesIngreso/registro-donacionesIngreso/listarDocumentoDonacionIngreso', params, function(respuesta) {
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			listarDetalleDocumentos(respuesta);
			if (indicador) {
				loadding(false);
			}
			if (respuesta != null && respuesta.length > 0) {
				$('#sel_tip_movimiento').prop('disabled', true);
			} else {
				$('#sel_tip_movimiento').prop('disabled', false);
			}
		}
	});
}

function listarDetalleDocumentos(respuesta) {

	tbl_det_documentos.dataTable().fnDestroy();
	
	tbl_det_documentos.dataTable({
		data : respuesta,
		columns : [ {
			data : 'idDocumentoIngreso',
			sClass : 'opc-center',
			render: function(data, type, row) {
				if (data != null) {
					return '<label class="checkbox">'+
								'<input type="checkbox"><i></i>'+
						   '</label>';	
				} else {
					return '';	
				}											
			}
		}, {	
			data : 'idTipoDocumento',
			render : function(data, type, full, meta) {
				var row = meta.row + 1;
				return row;											
			}
		}, {
			data : 'nombreDocumento'
		}, {
			data : 'nroDocumento',
				render: function(data, type, row) {
					if (data != null) {
						return '<button type="button" id="'+row.codigoArchivoAlfresco+'" name="'+row.nombreArchivo+'"'+ 
							   'class="btn btn-link input-sm btn_exp_doc">'+data+'</button>';
					} else {
						return '';	
					}											
				}
		}, {
			data : 'fechaDocumento'
		}, {
			data : 'nombreArchivo'
		} ],
		language : {
			'url' : VAR_CONTEXT + '/resources/js/Spanish.json'
		},
		bFilter : false,
		paging : false,
		ordering : false,
		info : false
	});
	
	listaDocumentosCache = respuesta;

}

$('#btn_gra_documento').click(function(e) {
	e.preventDefault();
	
	var bootstrapValidator = frm_det_documentos.data('bootstrapValidator');
	bootstrapValidator.validate();
	if (bootstrapValidator.isValid()) {			
		loadding(true);
		
		var params = { 
			idDocumentoIngreso : $('#hid_cod_documento').val(),
			idIngreso: $('#hid_id_ingreso').val(),
			idTipoDocumento : $('#sel_tipo_documento').val(),
			nroDocumento : $('#txt_nro_documento').val(),
			fechaDocumento : $('#txt_doc_fecha').val(),
			nombreArchivo : $('#txt_lee_sub_archivo').val(),
			observacion : $('#txt_descripcion_doc').val()
		};
		
		var cod_ind_alfresco = $('#hid_cod_ind_alfresco').val();
		if (cod_ind_alfresco == '1' || cod_ind_alfresco == '2') { // Archivo cargado
			var file_name = $('#txt_sub_archivo').val();
			var file_data = null;
			if (!esnulo(file_name) && typeof file_name !== 'undefined') {
				file_data = $('#txt_sub_archivo').prop('files')[0];
			}
			
			var formData = new FormData();
			formData.append('file_doc', file_data);
			// Carpeta contenedor, ubicado en config.properties
	    	formData.append('uploadDirectory', 'params.alfresco.uploadDirectory.donaciones');
	    	
			consultarAjaxFile('POST', '/common/archivo/cargarArchivo', formData, function(resArchivo) {
				
				if (resArchivo == NOTIFICACION_ERROR) {
					
					$('#div_det_documentos').modal('hide');
					frm_det_documentos.data('bootstrapValidator').resetForm();
					loadding(false);
					addErrorMessage(null, mensajeCargaArchivoError);						
				} else {
					
					params.codigoArchivoAlfresco = resArchivo;
				
					grabarDetalleDocumento(params);					
				}
			});
			
		} else { // Archivo no cargado
			
			params.codAlfresco = $('#hid_cod_act_alfresco').val();

			grabarDetalleDocumento(params);				
		}
	}
	
});

function grabarDetalleDocumento(params) {
	consultarAjax('POST', '/donacionesIngreso/registro-donacionesIngreso/grabarDocumentoDonacionIngreso', params, function(respuesta) {
		$('#div_det_documentos').modal('hide');
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			loadding(false);			
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			listarDocumentoDonacion(true);
			addSuccessMessage(null, respuesta.mensajeRespuesta);	
		}
		frm_det_documentos.data('bootstrapValidator').resetForm();
	});
}

function readImage (input) {
	if (input.files && input.files[0]) {
	  var reader = new FileReader();
	  reader.onload = function (e) {
	      $('#nombreArchivo').attr('src', e.target.result); // Renderizamos la imagen
//	      $('#nombreImagen').val(e.target.result);
	  }
	  reader.readAsDataURL(input.files[0]);
	}
}
$("#imagenMulti").change(function () {
   // Código a ejecutar cuando se detecta un cambio de archivO
   var file_name = $(this).val();
   var file_read = file_name.split('\\').pop();
   $('#nombreImagen').val(file_read);
   readImage(this);
});

$(".custom-file-input").on("change", function() {
  var fileName = $(this).val().split("\\").pop();
  $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
});

$('#btn_agregar_producto').click(function(e) {
		
		var inputfile = $("#imagenMulti");    
		inputfile.replaceWith(inputfile.val('').clone(true));
		$('#modal_titulo').html('Nuevo Producto');
//		var datos= (table.row( this ).data());
		$('#idProducto').val('');
	    $('#idCategoria1').val(1);
	    $('#idSubCategoria1').val(0);
	    $('#idSubCategoria1').prop('disabled', true);
	    $('#nombre').val('');
	    $('#descripcion').val('');
	    $('#estado').val(1);
	    var urlImagen = 'http://comprayventaperu.pe/alimentoselectos.pe/alimentosftp/logotipo-alimentos-selectos.png';
        $('#nombreArchivo').attr('src', urlImagen);
        $('#nombreImagen').val('');
        $("#divlabel label[for=inputGroupFile01]").text('');
	    $('#div_producto').modal('show');
} );

$('#idCategoria1').on('change', function() {
  if(this.value!='9'){
    	$('#idSubCategoria1').prop('disabled', true);
    	$('#idSubCategoria1').val(0);
    }else{
    	$('#idSubCategoria1').prop('disabled', false);
    	$('#idSubCategoria1').val(0);
    }
});