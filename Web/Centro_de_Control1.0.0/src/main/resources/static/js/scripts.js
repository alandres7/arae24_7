function initializeJS() {

    //tool tips
    jQuery('.tooltips').tooltip();

    //popovers
    jQuery('.popovers').popover();

    //custom scrollbar
        //for html
    jQuery("html").niceScroll({styler:"fb",cursorcolor:"#007AFF", cursorwidth: '6', cursorborderradius: '10px', background: '#F7F7F7', cursorborder: '', zindex: '1000'});
        //for sidebar
    jQuery("#sidebar").niceScroll({styler:"fb",cursorcolor:"#007AFF", cursorwidth: '3', cursorborderradius: '10px', background: '#F7F7F7', cursorborder: ''});
        // for scroll panel
    jQuery(".scroll-panel").niceScroll({styler:"fb",cursorcolor:"#007AFF", cursorwidth: '3', cursorborderradius: '10px', background: '#F7F7F7', cursorborder: ''});
    
    //sidebar dropdown menu
    jQuery('#sidebar .sub-menu > a').click(function () {
        var last = jQuery('.sub-menu.open', jQuery('#sidebar'));        
        jQuery('.menu-arrow').removeClass('arrow_carrot-right');
        jQuery('.sub', last).slideUp(200);
        var sub = jQuery(this).next();
        if (sub.is(":visible")) {
            jQuery('.menu-arrow').addClass('arrow_carrot-right');            
            sub.slideUp(200);
        } else {
            jQuery('.menu-arrow').addClass('arrow_carrot-down');            
            sub.slideDown(200);
        }
        var o = (jQuery(this).offset());
        diff = 200 - o.top;
        if(diff>0)
            jQuery("#sidebar").scrollTo("-="+Math.abs(diff),500);
        else
            jQuery("#sidebar").scrollTo("+="+Math.abs(diff),500);
    });

    // sidebar menu toggle
    jQuery(function() {
        function responsiveView() {
            var wSize = jQuery(window).width();
            if (wSize <= 768) {
                jQuery('#container').addClass('sidebar-close');
                jQuery('#sidebar > ul').hide();
            }

            if (wSize > 768) {
                jQuery('#container').removeClass('sidebar-close');
                jQuery('#sidebar > ul').show();
            }
        }
        jQuery(window).on('load', responsiveView);
        jQuery(window).on('resize', responsiveView);
    });

    jQuery('.toggle-nav').click(function () {
        if (jQuery('#sidebar > ul').is(":visible") === true) {
            jQuery('#main-content').css({
                'margin-left': '0px',
                'padding-left': '37px'
            });
            jQuery('#sidebar').css({
                'margin-left': '-230px'
            });
            jQuery('#sidebar > ul').hide();
            jQuery("#container").addClass("sidebar-closed");
        } else {
            jQuery('#main-content').css({
                'margin-left': '230px',
                'padding-left': '27px'
            });
            jQuery('#sidebar > ul').show();
            jQuery('#sidebar').css({
                'margin-left': '0'
            });
            jQuery("#container").removeClass("sidebar-closed");
        }
    });

    //bar chart
    if (jQuery(".custom-custom-bar-chart")) {
        jQuery(".bar").each(function () {
            var i = jQuery(this).find(".value").html();
            jQuery(this).find(".value").html("");
            jQuery(this).find(".value").animate({
                height: i
            }, 2000)
        })
    }
}
jQuery(document).ready(function(){
    initializeJS();
});

function visualizarImagen(icono,rutaImagen){
	var fpt = document.getElementById(icono)
		.parentElement.parentElement.querySelector('.file-preview')
		.querySelector('.file-preview-thumbnails');
	if (rutaImagen != null){		
		if (fpt.hasChildNodes()){
			fpt.removeChild(fpt.firstElementChild);
		}
		fpt.parentElement.style.display = "block"; 
		var div = document.createElement("div");
		div.className = "file-preview-frame";
		var img = document.createElement("img");
		img.className = "file-preview-image";
		img.src = rutaImagen;
		div.appendChild(img);
		fpt.appendChild(div);
	}else{
		fpt.parentElement.style.display = "none"; 
	}
} 
function cargarIcono(icono){
	var fp = document.getElementById(icono)
		.parentElement.parentElement.querySelector('.file-preview');
	console.log(fp);
	fp.style.display = "block"; 
}
$('.close').on('click', function(){
	this.parentElement.style.display = "none";
});

$(document).on('ready', function() {
	var rutaCompletaObject = document.getElementById("rutaMostrada");
	if (rutaCompletaObject != null){
		var rutaCompleta = rutaCompletaObject.textContent;
		var rutaPartida = rutaCompleta.split("/");
		var ultimo = rutaPartida[rutaPartida.length-1];
		rutaCompleta = rutaCompleta.replace(ultimo,"");
		rutaCompletaObject.innerHTML= rutaCompleta;
		
		var span = document.createElement("span");	      
		span.innerHTML=ultimo;
		span.style.color = "#9FC418";
		rutaCompletaObject.appendChild(span);
	}
});

function closeMenu(){
	$('#sidebar > ul').hide();
	$('#toggleButton').css({'left': '0px'});
	$('#main-content').css({'margin-left': '37px'});
    $('#modalAplicaciones').css({'margin-left': '37px'});
    $('#sidebar').css({'margin-left': '-300px'});
    $("#container").css({'margin-left': '220px'});
    $("#arrow").removeClass('glyphicon-menu-left');
    $("#arrow").addClass('glyphicon-menu-right');
}
function openMenu(){
    $('#sidebar > ul').show();
    $('#toggleButton').css({'left': '220px'});
    $('#main-content').css({'margin-left': '257px'});
    $('#modalAplicaciones').css({'margin-left': '257px'});
    $('#sidebar').css({'margin-left': '0'});
	$("#container").css({'margin-left': '0px'});
    $("#arrow").removeClass('glyphicon-menu-right');
    $("#arrow").addClass('glyphicon-menu-left');
}
