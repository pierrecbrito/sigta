/**
 * 
 */

const excluirEtapa1 = () => {
	  $('.btn-excluir').hide();
	  $("input[id='formTarefaAtualizar:btn-excluir']").show();
  }
  
  const excluirEtapa2 = () => {
	  $('.btn-excluir').show();
	  $("input[id='formTarefaAtualizar:btn-excluir']").hide();
	  $.modal.close();
  }
  
  const excluirArquivadaEtapa1 = () => {
	  $('.btn-excluir2').hide();
	  $("input[id='formTarefaArquivada:btn-excluir2']").show();
  }
  
  const excluirArquivadaEtapa2 = () => {
	  $('.btn-excluir2').show();
	  $("input[id='formTarefaArquivada:btn-excluir2']").hide();
	  $.modal.close();
  }
  
  const arquivarEtapa1 = () => {
	  $('.btn-arquivar').hide();
	  $("input[id='formTarefaAtualizar:btn-arquivar']").show();
  }
  
  const arquivarEtapa2 = () => {
	  $('.btn-arquivar').show();
	  $("input[id='formTarefaAtualizar:btn-arquivar']").hide();
  }
  
  const abrirModalDeEdicao = () => {
	  $("#modalEdicao").modal("show");
  }
  
  const abrirModalArquivada = () => {
	  $("#modalArquivada").modal("show");
  }
  
const toggleFiltro = () => {
      $("#filtro-expand-content").animate({
    	    height: 'toggle'
      });
 };
 
 const toggleFiltroArquivadas = () => {
       $("#filtro-expand-content-arquivadas").animate({
     	    height: 'toggle'
       });
};
  
  const showToast = (message) => {
        Toastify({
            text: message,
            duration: 3000,
            close: true,
            gravity: "bottom",
            position: "right",
            backgroundColor: "#015FFA",
            stopOnFocus: true
        }).showToast();
  }
  
  function handleSaveEvent(data) {
        if (data.status === "success") {
        	if( $("#globalMessage li").length > 0) {
        		 $.modal.close();
        		 showToast($("#globalMessage li").text());
        	}
        }
   }
  
  function handleEditEvent(data) {
        if (data.status === "success") {
        	if( $("#globalMessage li").length > 0) {
        		 showToast($("#globalMessage li").last().text());
        	}
        }
   }
  
  function handleArquivartEvent(data) {
        if (data.status === "success") {
        	if( $("#globalMessage li").length > 0) {
        		 $.modal.close();
        		 showToast($("#globalMessage li").last().text());
        	}
        }
   }
  
  function handleRemoveEvent(data) {
        if (data.status === "success") {
        	if( $("#globalMessage li").length > 0) {
        		 $.modal.close();
        		 showToast($("#globalMessage li").last().text());
        	}
        }
   }