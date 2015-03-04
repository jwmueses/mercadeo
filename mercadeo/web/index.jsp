<%-- 
    Document   : index
    Created on : 14/05/2013, 07:42:52 PM
    Author     : Jorge
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
    <title>SISTEMA DE ADMINISTRACIÓN DE PANES DE MERCADEO</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Expires" content="Mon, 01 Jan 2001 00:00:01 GMT" />
    <meta http-equiv="Cache-Control" content="no-store" />
    <meta http-equiv="Cache-Control" content="must-revalidate" />
    <meta http-equiv="Cache-Control" content="no-cache" />
    <link href="img/favicon.ico" type="image/x-icon" rel="shortcut icon" />

<script language="JavaScript" type="text/javascript">
var _esN = window.navigator.appName=='Netscape' ? true : false;
var _esIE = window.navigator.appName=='Microsoft Internet Explorer' ? true : false;
window.moveTo(0,0);
if(_esN){
    window.outerHeight = window.screen.availHeight;
    window.outerWidth = window.screen.availWidth;
}else{
    window.resizeTo(window.screen.availWidth, window.screen.availHeight);
}
document.onmousedown=function(e){
    if(_esN && (e.which==3 || e.which==2)){
        return false;
    }
    else if(_esIE){
        if(event.button==2){
            return false;
        }
    }
    return true;
};
document.oncontextmenu = new Function("return false;");
function _(o)
{
    if(o==''){
        return null;
    }
    var v = (parseInt(window.navigator.appVersion)==4) ? true : false;
    return (document.getElementById) ? document.getElementById(o) : ((document.all) ? document.all[o] :((_esN && v) ? document.layers[o] : null));
}
function _gKC(e)
{
    return e.keyCode || e.which;
}
function _inh(e)
{
    if(_esN) {
        e.preventDefault();
    } else {
        event.returnValue=false;
        event.cancelBubble=true;
    }
}
function _getTop(o)
{
    if(o == null){
        return 0;
    }else{
        var obj = o.offsetParent;
        if(o.style.top){
            return o.style.top + _getTop(obj);
        }
        if(o.style.pixelTop){
            return o.style.pixelTop + _getTop(obj);
        }
        if(o.offsetTop){
            return o.offsetTop + _getTop(obj);
        }
        return 0;
    }
}
function ubicar()
{
    var v = _('jm_ventana');
    var tp = (_getTop(_('pie'))-125)/2;
    var iz = (document.body.clientWidth - 310)/2;
    v.style.top= (tp>0?tp:0) + 'px';
    v.style.left= (iz>0?iz:0) + 'px';
}
function ini()
{
    _('jm_ventana').style.visibility='visible';
    ubicar();
    _('usuario').focus();
}
function val(f)
{
    if(f.usuario.value!='' && f.usuario.value!='usuario' && f.clave.value!='' && f.clave.value!='clave'){
        f.submit();
    }
    return false;
}
</script>
<style type="text/css">
BODY{font-size:62.5%;background-image:url('img/fondo0.jpg');margin:0px;font-family:Verdana, Geneva, Arial, Helvetica, sans-serif;}
INPUT{font-size:13px;}
.jm_error{color:red;font-size:13px;}
#jm_ventana{background-color:#FFFFFF;border:4px solid #8B8B8B;position:absolute;visibility:hidden;text-align:center;width:310px;height:125px;}
#modal{position:fixed;background-color:#000000;left:0px;top:0px;width:100%;height:100%;z-index:30;}
#error{height:30px;}
#usuario,#clave{background-repeat:no-repeat;padding-left:20px;width:200px;border:1px solid #8B8B8B;}
#usuario{background-image:url(img/usuario.gif);}
#clave{background-image:url(img/clave.gif);}
#pie,#derecha{position:absolute;height:1px;}
#pie{bottom:0px;}
#derecha{right:0px;}
#jm_carg{position:absolute;z-index:51;display:none;}
</style>
</head>
<body onLoad="ini()" onresize="ubicar()">
    <noscript>
        <p class="jm_error">
            La p&aacute;gina que est&aacute;s viendo requiere de JAVASCRIPT para su correcto funcionamiento.
            Si lo ha deshabilitado intencionalmente, por favor, vuelve a activarlo.
        </p>
    </noscript>

    <img id="jm_carg" src="img/cargando.gif" alt="cargando" />

    <form id="frm" method="post" action="Autenticar" autocomplete="off" onsubmit="return val(this);">
        <div id="jm_ventana">
            <div id="error" class="jm_error">Autenticaci&oacute;n</div>
            <div>
                <input id="usuario" name="usuario" type="text" size="30" value="usuario" onFocus="if(_('usuario').value=='usuario'){_('usuario').value='';}" onBlur="if(_('usuario').value==''){_('usuario').value='usuario';}" onkeydown="if(_gKC(event)==13){_inh(event);_('clave').focus();}" /><br /><br />
                <input id="clave" name="clave" type="password" size="30" value="clave" onFocus="if(_('clave').value=='clave'){_('clave').value='';}" onBlur="if(_('clave').value==''){_('clave').value='clave';}" onkeypress="if(_gKC(event)==13){val(_('frm'));}" />
            </div>
        </div>
    </form>

    <div id="pie"></div>
    <div id="derecha"></div>

    <%
    String msg = request.getParameter("msg")!=null ? request.getParameter("msg") : "";
    if(msg.compareTo("")!=0){
        out.print("<script language=\"JavaScript\" type=\"text/javascript\">");
        out.print("alert('"+msg+"');");
        out.print("</script>");
    }
    %>
    
</body>
</html>
