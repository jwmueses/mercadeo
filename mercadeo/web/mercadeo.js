var _esSf=window.navigator.appVersion.toLowerCase().indexOf('safari')>0?true:false;
var _altEdi = 0;
var _pag = 0;
var _altTbl = 0;
var _centroX = 0;
var _centroY = 0;
var _OBJ = null;
var _wOb=0;
var _AUX='';
var fijAn = true;
var btn = new Boton();
var axPg=0;
var axNpg = 0;
function ini()
{
    _('filtro').style.display='block';
    red();
    /*
    _('d_1').onmouseover = function(){_('d_cuerpo').scrollLeft = 0;}
    _('d_3').onmouseover = function(){_('d_cuerpo').scrollLeft = parseInt(_('d_1').style.width);}
    */
    var aM = 60;
    var tM = 44;
    btn.icono(-225, 0, aM, tM, 'Ventana de inicio', "encerar(false);", 'menuizq');
    if(_MENUS_._enArregloBin("mer_tipo_planes")>=0 || _MENUS_._enArregloBin("mer_asignaciones")>=0){
        var mP=Array();
        if(_MENUS_._enArregloBin("mer_tipo_planes")>=0){
            mP.push(Array('Tipos de planes','mer_tipo_planes();'));
        }
        if(_MENUS_._enArregloBin("mer_asignaciones")>=0){
            mP.push(Array('Asignaci&oacute;n de usuarios','mer_asignaciones();'));
        }
        btn.menu(-328, 0, aM, tM, 'Tipos de planes', mP, 'menuizq');
    }
    if(_MENUS_._enArregloBin("mer_laboratorios")>=0){
        btn.icono(-276, 0, aM, tM, 'Laboratorios estrat&eacute;gicos', "mer_laboratorios();", 'menuizq');
    }

    if(_MENUS_._enArregloBin("mer_planes_mercadeo")>=0 || _MENUS_._enArregloBin("mer_aut_operaciones")>=0 || _MENUS_._enArregloBin("mer_aut_marketing")>=0 
            || _MENUS_._enArregloBin("mer_aut_ventas")>=0 || _MENUS_._enArregloBin("mer_aut_comercial")>=0 || _MENUS_._enArregloBin("mer_gastos")>=0 
            || _MENUS_._enArregloBin("mer_liquidacion")>=0 || _MENUS_._enArregloBin("mer_seguimiento")>=0 || _MENUS_._enArregloBin("mer_seguimiento_ver")>=0){
        var mM=Array();
        if(_MENUS_._enArregloBin("mer_planes_mercadeo")>=0){
            mM.push(Array('Planes de Mercadeo','mer_mercadeo();'));
        }
        /*if(_MENUS_._enArregloBin("mer_admin_tiempos")>=0){
            mM.push(Array('Tiempos para confirmar auspicios','mer_conf_auspicios();'));
        }*/
        if(_MENUS_._enArregloBin("mer_aut_operaciones")>=0){
            mM.push(Array('Autorizaci&oacute;n Operaciones','mer_operaciones();'));
        }
        if(_MENUS_._enArregloBin("mer_aut_marketing")>=0){
            mM.push(Array('Autorizaciones Marketing','mer_marketing();'));
        }
        if(_MENUS_._enArregloBin("mer_aut_ventas")>=0){
            mM.push(Array('Autorizaciones Ventas','mer_ventas();'));
        }
        if(_MENUS_._enArregloBin("mer_aut_comercial")>=0){
            mM.push(Array('Autorizaciones Comercial','mer_comercial();'));
        }
        if(_MENUS_._enArregloBin("mer_ejecucion")>=0){
            mM.push(Array('Seguimiento de Ejecuci&oacute;n','mer_ejecucion();'));
        }
        if(_MENUS_._enArregloBin("mer_seguimiento")>=0 || _MENUS_._enArregloBin("mer_seguimiento_ver")>=0){
            mM.push(Array('Seguimiento de Presupuesto','mer_seguimiento();'));
        }
        /*if(_MENUS_._enArregloBin("mer_gastos")>=0){
            mM.push(Array('Contabilizaci&oacute;n de Gastos','mer_gastos();'));
        }*/
        if(_MENUS_._enArregloBin("mer_liquidacion")>=0){
            mM.push(Array('Cerrar Planes Manualmente','mer_liquidacion();'));
        }
        btn.menu(-376, 0, aM, tM, 'Planes de Mercadeo', mM, 'menuizq');
    }
    /*if(_MENUS_._enArregloBin("mer_configuraciones")>=0 || _MENUS_._enArregloBin("mer_conceptos")>=0 
            || _MENUS_._enArregloBin("mer_conceptos")>=0){*/
    if(_MENUS_._enArregloBin("mer_reportes")>=0){
        btn.icono(-423, 0, aM, tM, 'Impresi&oacute;n de reportes', "mer_reportes();", 'menuizq');
    }
    if(_MENUS_._enArregloBin("mer_configuraciones")>=0 || _MENUS_._enArregloBin("mer_conceptos")>=0){
        var mC=Array();
        if(_MENUS_._enArregloBin("mer_conceptos")>=0){
            mC.push(Array('Montos por conceptos','mer_conceptos();'));
        }
        /*
        if(_MENUS_._enArregloBin("mer_finalizacion")>=0){
            mC.push(Array('Finalizacion de plan de mercadeo','mer_finalizaciones();'));
        }*/
        if(_MENUS_._enArregloBin("mer_configuraciones")>=0){
            mC.push(Array('Configuraciones','mer_configuraciones();'));
        }
        btn.menu(-470, 0, aM, tM, 'Configuraciones', mC, 'menuizq');
    }
    //btn.icono(-512, 0, aM, tM, 'Cambiar clave de acceso', "cambiarClave();", 'menuizq');
    //var mA = [['Manual del usuario', "abrir('manualUsuario.pdf')"], ['Acerca de...', 'creditos();']];
    var mA = [['Acerca de...', 'creditos();']];
    btn.menu(-564, 0, aM, tM, 'Ayuda', mA, 'menuizq');
    
    btn.icono(-654, 0, aM, tM, 'Cerrar sesi&oacute;n', "window.open('Salir','_parent');", 'menuder');
}
function red()
{
    _anchBody = _getIzq(_('areabody')); 
    _altBody = _getTop(_('areabody'));
    _centroX = _anchBody/2;
    _centroY = _altBody/2;
    _altEdi = _altBody - (_('filtro').style.display=='block'?170:126);
    _altTbl = _altBody - 197;

    _('d_0').style.height=_('d_1').style.height=_('d_2').style.height=_('d_3').style.height=
        _('d_4').style.height=_('d_5').style.height=_('d_6').style.height=_altEdi + 'px';
    if(fijAn){
        _('d_1').style.width=_('d_3').style.width=parseInt(((_anchBody-52)/2))+'px';
        _('d_5').style.width='1px';
    }
    
    _('edicion').style.width = (parseInt(_('d_1').style.width) + parseInt(_('d_3').style.width) + parseInt(_('d_5').style.width) + 90) + 'px';
}
function encerar(b)
{
    _FS=-1;
    _WR='';
    _AUX='';
    fijAn = true;
    if(b){
        document.body.style.backgroundImage = 'url(../img/fondo1.jpg)';
        _('cuerpo').style.visibility = 'visible';
    }else{
        document.body.style.backgroundImage = '';
        _('cuerpo').style.visibility = 'hidden';
    }
    _('filtro').style.display='block';
    _('d_1').innerHTML=_('d_3').innerHTML=_('d_5').innerHTML=_('filtro').innerHTML='&nbsp;';
    _('d_5').style.display=_('d_6').style.display='none';
    _('iconos').innerHTML='';
    red();
}
function _SUP(e)
{
    var c = _gKC(e);
    if(c!=8 && c!=9 && c!=13) {
        _inh(e);
    }
}
function _NoE(e)
{
    var c = _gKC(e);
    if(c==13 || c==27) {
        _inh(e);
    }
}
function _NE(e,f)
{
    if(_esN){
        document.body.addEventListener(e, f, false);
    }else{
        document.body.attachEvent('on'+e, f);
    }
}
function _RE(e, f1)
{
    var f = f1 || 'mousemove';
    if(_esN){
        document.body.removeEventListener(e, f, false);
    }else{
        document.body.detachEvent("on"+e, f);
    }
}
function _DNI(e)
{
    var c = _gKC(e);
    if((c<48 || c>57 && c<65 || c>90 && c<97 || c>122) && c!=0 && c!=8 && c!=9) {
        _inh(e);
    }
}
function _enMatrizJSON(v, x, p)
{
    for(var i=0;i<v.tbl.length;i++){
        if(v.tbl[i][p]==x){
            return i;
        }
    }
    return -1;
}
function abrir(p)
{
    p = encodeURI(p);
    window.open(p, '_blank', 'top=50,left=50,width=750,height=500,resizable=yes,scrollbars=yes');
}
function iniRedimensionarW(o)
{
    _OBJ=o;
    var d=_('d_menu').style.display || 'block';
    _rX = (parseInt(_getIzq(_(o))) || ((d=='block') ? 185 : 35));
    _CE('mousemove', redimensionarW);
    document.onmouseup=function(){_RE('mousemove', redimensionarW);};
}
function redimensionarW(ev)
{
    var e = ev || window.event;
    _pX = e.screenX - _rX;
    _(_OBJ).style.width = _pX+'px';
    _('gredicion').style.width = (parseInt(_(_OBJ=='d_1'?'d_3':'d_1').style.width) + _pX + 30) + 'px';
}
function redimensionarH(s)
{
    if(_OBJ){
        _('d_1').style.height = (s=='+' ? (parseInt(_('d_1').style.height) + 1) : (parseInt(_('d_1').style.height) - 1)) + 'px';
        _('d_cuerpo').scrollTop=1000;
        window.setTimeout("redimensionarH('"+s+"')", 10);
    }
}
function setAnchoPanel(p, a)
{
    _('d_'+p).style.width = a+'px';
    _('edicion').style.width = (parseInt(_('d_1').style.width) + parseInt(_('d_3').style.width) + parseInt(_('d_5').style.width) + 90) + 'px';
}
function insertarComun(o,cb,g,i,w,gr)
{
    _AUX = _(o).innerHTML;
    _(o).innerHTML='<input type="text" id="comun" style="width:'+w+'px;" onkeydown="_NoE(event)" onkeypress="_alfanumerico(event)" onkeyup="guardarComun(event, \''+o+'\', \''+cb+'\', \''+g+'\', \''+i+'\', '+w+', \''+gr+'\')" />';
    _(g).style.visibility='visible';
    _(i).style.visibility='hidden';
    _('comun').focus();
}
function guardarComun(e,o,cb,g,i,w,gr)
{
    var c = e==null ? 13 : _gKC(e);
    if(c==13){
        if(_('comun').value!=''){
            Ajax.solicitud("setComun", "obj="+o+"&gr="+gr+"&an="+w+'&comb='+cb+'&nom='+_('comun').value._trim(), false, o);
            _(g).style.visibility='hidden';
            _(i).style.visibility='visible';
        }else{
            _MS('Debe llenar la cailla de texto antes de guardar', _('comun'));
        }
    }
    if(c==27){
        _(o).innerHTML = _AUX;
        _(g).style.visibility='hidden';
        _(i).style.visibility='visible';
    }
}
function msg()
{
    return 'Los campos marcados con un * son obligatorios.';
}
function setFecha(k, fn)
{
    var f = new Date();
    var h = f.getDate() + '/' + (f.getMonth()+1) + '/' + f.getFullYear();
    return "<input type='text' id='"+k+"' name='"+k+"' value='"+h+"' size='10' readOnly><input type=\"button\" value=\"...\" onClick=\"SelectorFecha.crear('"+k+"', 'SQL', '"+fn+"');\" />";
}
function setFechas(fn)
{
    var f = new Date();
    var h = f.getDate() + '/' + (f.getMonth()+1) + '/' + f.getFullYear();
    return "<label id='lbfi'>Desde la fecha: <input type='text' id='fi' name='fi' value='"+h+"' size='10' readOnly><input type=\"button\" value=\"...\" onClick=\"SelectorFecha.crear('fi', 'SQL', '"+fn+"');\" /><br /></label>"+
    "<label id='lbff'>Hasta la fecha: &nbsp;<input type='text' id='ff' name='ff' value='"+h+"' size='10' readOnly><input type=\"button\" value=\"...\" onClick=\"SelectorFecha.crear('ff', 'SQL', '"+fn+"');\" /></label>";
}
function setCombo(k, j, f)
{
    var js = eval('(' + j + ')');
    var h = "<select id='"+k+"' name='"+k+"' onchange=\""+f+"\">";
    for(var i=0; i<js.tbl.length; i++){
        h += "<option value='"+js.tbl[i][0]+"'>"+js.tbl[i][1]+"</option>";
    }
    h+="</select>";
    return h;
}
function setRadio(k, j, f)
{
    var js = eval('(' + j + ')');
    var h ='';
    var fu = f || '';
    for(var i=0; i<js.tbl.length; i++){
        h += "<label onclick=\""+fu+"\"><input type='radio' id='"+k+i+"' name='"+k+"' "+(i==0?'checked':'')+" value='"+js.tbl[i][0]+"' />"+js.tbl[i][1]+"</label><br />";
    }
    return h;
}
function setCaja(k, f, c, di)
{
    var d = di || '';  
    var fn = d!='' ? "onkeyup=\""+f+"\"" : "onkeypress=\"if(_gKC(event)==13){"+f+"}\"";
    return "<input type='text' id='"+k+"' name='"+k+"' "+(c!=''?"class='"+c+"'":"")+" "+fn+" />";
}
function setOculto(k, v)
{
    return "<input type='hidden' id='"+k+"' name='"+k+"' value=\""+v+"\" />";
}
function setBoton(t, v, f)
{
    return " &nbsp;<input type=\""+(t || 'submit')+"\" value=\""+v+"\" onclick='"+f+"'>";
}
function setPieReporte()
{
    return '<hr />' + setRadio('x', '{tbl:[{0:"Pdf",1:"PDF"},{0:"Excel",1:"EXCEL"}]}') + '<br />' + setBoton('submit', 'Imprimir');
}
function getMarcado(id)
{
    var d = id || 'jmTbl';
    var i=0;
    var o = _('chT_'+d+i);
    var k='';
    while(o!=null){
        if(o.checked){
            k = _(d+i+'0').value;
            break;
        }
        i++;
        o = _('chT_'+d+i);
    }
    if(k==''){
        alert('Debe marcar un registro para poder realizar la acción.');
        return false;
    }
    return k;
}
function getMarcados(id)
{
    var d = id || 'jmTbl';
    var i=0;
    var o = _('chT_'+d+i);
    var k='';
    while(o!=null){
        if(o.checked){
            k += _(d+i+'0').value + ',';
        }
        i++;
        o = _('chT_'+d+i);
    }
    if(k==''){
        alert('Debe marcar por lo menos un registro para poder realizar la acción.');
        return false;
    }
    k = k.substr(0, k.length-1);
    return k;
}
function esDocumento(ob)
{
  var obj = _(ob);
  if(obj.value=='') {
	return false;
  }
  var numero = obj.value;
  var suma = 0;
  var residuo = 0;
  var pri = false;
  var pub = false;
  var nat = false;
  var numeroProvincias = 22;
  var modulo = 11;
  var ok=1;
  for (var i=0; i<numero.length && ok==1 ; i++){
	 var n = parseInt(numero.charAt(i));
	 if (isNaN(n)) ok=0;
  }
  if (ok==0){
	 _MS('Debe ingresar solo caracteres numéricos', obj);
	 return false;
  }

  if (numero.length < 10 ){
	 _MS('El número de documento ingresado no es válido', obj);
	 return false;
  }
  var provincia = numero.substr(0,2);
  if (provincia < 1 || provincia > numeroProvincias){
	 _MS('El código de la provincia (dos primeros dígitos) del número de documento es inválido', obj);
	 return false;
  }
  var d1  = numero.substr(0,1);
  var d2  = numero.substr(1,1);
  var d3  = numero.substr(2,1);
  var d4  = numero.substr(3,1);
  var d5  = numero.substr(4,1);
  var d6  = numero.substr(5,1);
  var d7  = numero.substr(6,1);
  var d8  = numero.substr(7,1);
  var d9  = numero.substr(8,1);
  var d10 = numero.substr(9,1);
  if (d3==7 || d3==8){
	 _MS('El tercer dígito del número de documento ingresado es inválido', obj);
	 return false;
  }
  if (d3 < 6){
	 nat = true;
	 p1 = d1 * 2;if (p1 >= 10) p1 -= 9;
	 p2 = d2 * 1;if (p2 >= 10) p2 -= 9;
	 p3 = d3 * 2;if (p3 >= 10) p3 -= 9;
	 p4 = d4 * 1;if (p4 >= 10) p4 -= 9;
	 p5 = d5 * 2;if (p5 >= 10) p5 -= 9;
	 p6 = d6 * 1;if (p6 >= 10) p6 -= 9;
	 p7 = d7 * 2;if (p7 >= 10) p7 -= 9;
	 p8 = d8 * 1;if (p8 >= 10) p8 -= 9;
	 p9 = d9 * 2;if (p9 >= 10) p9 -= 9;
	 modulo = 10;
  }
  else if(d3 == 6){
	 pub = true;
	 p1 = d1 * 3;
	 p2 = d2 * 2;
	 p3 = d3 * 7;
	 p4 = d4 * 6;
	 p5 = d5 * 5;
	 p6 = d6 * 4;
	 p7 = d7 * 3;
	 p8 = d8 * 2;
	 p9 = 0;
  }
  else if(d3 == 9) {
	 pri = true;
	 p1 = d1 * 4;
	 p2 = d2 * 3;
	 p3 = d3 * 2;
	 p4 = d4 * 7;
	 p5 = d5 * 6;
	 p6 = d6 * 5;
	 p7 = d7 * 4;
	 p8 = d8 * 3;
	 p9 = d9 * 2;
  }
  suma = p1 + p2 + p3 + p4 + p5 + p6 + p7 + p8 + p9;
  residuo = suma % modulo;
  var digitoVerificador = residuo==0 ? 0: modulo - residuo;
  if (pub==true){
	 if (digitoVerificador != d9){
		_MS('El ruc de la empresa del sector público es incorrecto.', obj);
		return false;
	 }
	 if ( numero.substr(9,4) != '0001' ){
		_MS('El ruc de la empresa del sector público debe terminar con 0001', obj);
		return false;
	 }
  }
  else if(pri == true){
	 if (digitoVerificador != d10){
		_MS('El ruc de la empresa del sector privado es incorrecto.', obj);
		return false;
	 }
	 if ( numero.substr(10,3) != '001' ){
		_MS('El ruc de la empresa del sector privado debe terminar con 001', obj);
		return false;
	 }
  }

  else if(nat == true){
	 if (digitoVerificador != d10){
		_MS('El número de cédula o ruc de la persona natural es incorrecto.', obj);
		return false;
	 }
	 if (numero.length >10 && numero.substr(10,3) != '001' ){
		_MS('El ruc de la persona natural debe terminar con 001', obj);
		return false;
	 }
  }
  return true;
}
function cambiarClave()
{
    Ventana.crear('vta1', 'Cambiar mi Contrase&ntilde;a', "ancho=380,alto=150,modal=true,cerrar=true");
    Ajax.solicitud('cambiarClave', '', false, 'vta1_html');
}
function establecerClave(f)
{
    var c = _('r_clave');
    var c2 = _('r_clave2');
    if(c.value=='' || c2.value==''){
        alert('Debe ingresar una nueva contraseña y confirmarla antes de guardar.');
        _('r_clave').focus();
        return false;
    }
    if(c.value!=c2.value){
        c2.value = c.value = '';
        alert('Confirmación de nueva contraseña no coincide.');
        c.focus();
        return false;
    }

    return Ajax.enviarForm(f);
}
function creditos()
{
    Ventana.crear('vta1', 'Acerca de...', "ancho=400,alto=300,modal=true,cerrar=true");
    Ajax.solicitud('Creditos', '', false, 'vta1_html');
}
function setComboFiltro(ob, scll)
{
    if(_('cmbC')==null){
        var o = _(ob);
        CMB = true;
        _NE('mousedown', comboFiltroRem);
        var d = document.createElement('div');
        d.id = 'cmbC';
        d.onmouseover = function(){CMB = false;};
        d.onmouseout = function(){CMB = true;};
        d.className = 'popup';
        o.parentNode.appendChild(d);
        if(_esIE && _(scll)!=null){
            if(_(scll).scrollTop!=''){
                var sc = parseInt(_(scll).scrollTop);
                d.style.top = ( _getTop(o) + 43 - sc ) + 'px';
            }
        }
    }
}
function comboFiltroRem()
{
    if(CMB){
        _R('cmbC');
        _RE('mousedown', comboFiltroRem);
    }
}
function adjuntarArchivo(act)
{
    var h = '<form action="'+act+'" method="post" enctype="multipart/form-data" target="procesaTransferencia" onsubmit="return iniciaTransferencia();" >' +
                    '<p id="cargando" style="display:none;"><br/>Tranfiriendo archivo... &nbsp;<img src="img/subiendo.gif" /></p>' +
                '<div id="frmArchivo" align="center"><br/>' +
                    "Tama&ntilde;o: 4 Mb m&aacute;ximo<br/>" +
                    '<input id="archivo" name="archivo" type="file" size="30" /><br/><br/>' +
                    '<input type="submit" value="Transferir archivo adjunto" />' +
                '</div>' +
                '<iframe name="procesaTransferencia" src="#" style="width:0px;height:0px;border:0px;"></iframe>' +
            '</form>';
    Ventana.crear('vta4', 'ADJUNTAR ARCHIVO', "ancho=400,alto=160,modal=true,cerrar=true", h);
}
function iniciaTransferencia()
{
    if(_('archivo').value==''){
        alert('Por favor, seleccione un archivo antes de subirlo al servidor.');
        return false;
    }
    _('cargando').style.display = 'block';
    _('frmArchivo').style.display = 'none';
    return true;
}


/* tipos de planes */
function mer_tipo_planes()
{
    encerar(true);
    _('filtro').innerHTML='Acr&oacute;nimo o nombre: ' +
        setCaja('txt', 'mer_tipo_planesBuscar()', 'buscar') +
        setBoton('button', 'Filtrar', 'mer_tipo_planesBuscar()');
    btn.icono(-720, 0, 42, 42, 'Nuevo tipo de plan de mercadeo', "mer_tipo_planesEditar(-1);", 'iconos', 'jmFPBT1');
    //btn.icono(-795, 0, 42, 42, 'Impresi&oacute;n de reportes', "mer_tipo_planesImp();", 'iconos', 'jmFPBT1');
    setAnchoPanel(3, 560);
    mer_tipo_planesBuscar();
}
function mer_tipo_planesBuscar()
{
    var w = '';
    var t = _('txt').value.toLowerCase();
    if(t!=''){
        w = "where id_tipo_plan like '"+t+"%' or nombre like '%"+t+"%'";
    }
    _WR = w + ' order by nombre'; 
    new Tabla('d_1', 'jmTbl', '', 'tbl_tipo_plan', 'ACRONIMO,NOMBRE,COORDINADOR', 'id_tipo_plan,id_tipo_plan,nombre,coordinador', '0,90,200,200', _altTbl, 'mer_tipo_planesEditar(^)', _WR);
}
function mer_tipo_planesEditar(k)
{
    Ajax.solicitud('frmTipoPlan', 'id='+k, false, 'd_3');
}
function mer_tipo_planesGuardar(f)
{
    if(f.id_tipo_plan.value=='' || f.nombre.value=='' || f.coordinador.value=='' || f.mail_responsable.value=='' 
            || f.p_incremento.value=='' || f.dias_prolonga.value==''){
        alert('Los campos marcados con  *  son obligatorios');
        return false;
    }
    if(f.cedula.value==''){
        alert('Por favor debe seleccionar un empleado de la lista desplagable');
        return false;
    }
    if(!_esMail('mail_responsable')){
        return false;
    }

    var i = _('axCts').childNodes.length>0 ? parseInt(_('axCts').childNodes.item(_('axCts').childNodes.length-1).id.replace('f', ''))+1 : 0;
    if(i==0){
        alert('Debe registrar por lo menos una cuenta contable');
        return false;
    }
    _('tope').value=i;

    var b=false;
    i=0;
    while(_('ch'+i)!=null){
        if(_('ch'+i).checked){
            b=true;
            break;
        }
        i++;
    }
    if(!b){
        alert('Debe seleccionar por lo menos una sucursal');
        return false;
    }
    return Ajax.enviarForm(f);
}
function mer_getCuentas()
{
    var t = _('txtCts').value;
    if(t!=''){
        setComboFiltro('axTxt', 'd_3');
        Ajax.solicitud('getCuentas', "txt="+t+"&obj=cmbC&onC=mer_setCuentaD()", false, 'cmbC');
    }else{
        _R('cmbC');
        _RE('mousedown', comboFiltroRem);
    }
}
function mer_getPlanCuentas(o)
{
    if(_('vta1')==null){
        Ventana.crear('vta1', 'PLAN DE CUENTAS', "ancho=650,alto="+(_altBody-50)+",modal=true,cerrar=true");
        _('vta1_html').innerHTML = '<form id="frmCtas"><div class="psPanelGris" style="text-align:right"><input type="button" value="Agregar cuentas" onclick="mer_setCuentas(_(\'frmCtas\'))" /></div>'+
                '<div id="vta1_html1" style="overflow:auto;height:'+(_altBody-130)+'px"></div></form>';
        Ajax.solicitud('frmPlanCuentas', 'ojP='+o, false, o);
    }
    
    var ob = o.replace('s', '');
    if(_('t'+ob)!=null){
        if(_('t'+ob).className=='carpeta'){
            Ajax.solicitud('frmPlanCuentas', 'ojP='+o, false, o);
            _('t'+ob).className =  'carpetaAbierta';
        }else{
            _('t'+ob).className =  'carpeta';
            _('s'+ob).innerHTML = '';
        }
    }
}
function mer_setCuentas(f)
{
    var i=0;
    while(f.elements[i]!=null){
        if(f.elements[i].type=='checkbox'){
            if(f.elements[i].checked){
                var o = f.elements[i].id.replace('ch', '');
                mer_setCuenta(_('ct'+o).value, _('ds'+o).value);
            }
        }
        i++;
    }
}
function mer_setCuentaD()
{
    var c = _('cta').value.split("|");
    mer_setCuenta(c[0], c[1]);
    _R('cmbC');
    _RE('mousedown', comboFiltroRem);
    _('txtCts').value='';
    _('txtCts').focus();
}
function mer_setCuenta(ct, ds)
{
    var i = _('axCts').childNodes.length>0 ? parseInt(_('axCts').childNodes.item(_('axCts').childNodes.length-1).id.replace('f', ''))+1 : 0;
    if( !enCuentas(i, ct) ){
    _('axCts').innerHTML += '<div class="jm_filaImp" id="f'+i+'" onmouseover="this.className=\'jm_filaSobre\'" onmouseout="this.className=\'jm_filaImp\'">'+
        '<div class="columna" style="width:120px"><input type="hidden" id="c'+i+'" name="c'+i+'" value="'+ct+'" />'+ct+'</div>'+
        '<div class="columna" style="width:360px"><input type="hidden" id="d'+i+'" name="d'+i+'" value="'+ds+'" />'+ds+'</div>'+
        '<div class="columna" style="width:25px"><div class="borrar" title="Eliminar" onclick="_R(\'f'+i+'\');">&nbsp;</div></div>'+
        '</div>';
    }
}
function enCuentas(l, cod)
{
    for(var j=0; j<=l; j++){
        if( _('c'+j) != null ){
            if( _('c'+j).value==cod ){
                return true;
            }
        }
    }
    return false;
}
function mer_getEmpledos()
{
    var t = _('coordinador').value;
    if(t!=''){
        setComboFiltro('axEmp', 'd_3');
        _('cmbC').style.marginTop='0px';
        Ajax.solicitud('getEmpleados', 'txt='+t+'&obj=cmbC&onC=mer_setEmpledos()', false, 'cmbC');
    }else{
        _R('cmbC');
        _RE('mousedown', comboFiltroRem);
    }
}
function mer_setEmpledos()
{
    var v = _('emp').value.split('|');
    _('cedula').value = v[0];
    _('mail_responsable').value=v[1];
    _('centro_costos_coordinador').value=v[3];
    _('coordinador').value = _('emp').options[_('emp').selectedIndex].text;
    _R('cmbC');
    _RE('mousedown', comboFiltroRem);
    _('txtCts').focus();
}


/* asignacion de tipos de planes */
function mer_asignaciones()
{
    encerar(true);
    _('filtro').innerHTML='Acr&oacute;nimo o nombre: ' +
        setCaja('txt', 'mer_asignacionBuscar()', 'buscar') +
        setBoton('button', 'Filtrar', 'mer_asignacionBuscar()');
    setAnchoPanel(3, 560);
    mer_asignacionBuscar();
}
function mer_asignacionBuscar()
{
    var w = '';
    var t = _('txt').value.toLowerCase();
    if(t!=''){
        w = "where id_tipo_plan like '"+t+"%' or nombre like '%"+t+"%'";
    }
    _WR = w;
    new Tabla('d_1', 'jmTbl', '', 'tbl_tipo_plan', 'ACRONIMO,NOMBRE,COORDINADOR', 'id_tipo_plan,id_tipo_plan,nombre,coordinador', '0,90,200,200', _altTbl, 'mer_asignacionEditar(^)', _WR);
}
function mer_asignacionEditar(k)
{
    Ajax.solicitud('frmTipoPlanAsignacion', 'id='+k, false, 'd_3');
}
function mer_asignacionGuardar(f)
{
    _('tope').value = _('axUsr').childNodes.length>0 ? parseInt(_('axUsr').childNodes.item(_('axUsr').childNodes.length-1).id.replace('f', ''))+1 : 0;
    return Ajax.enviarForm(f);
}
function mer_getUsuarios()
{
    var t = _('txtUs').value;
    if(t!=''){
        setComboFiltro('axTxt', 'd_3');
        Ajax.solicitud('getUsuarios', 'txt='+t+'&obj=cmbC&onC=mer_setUsuarios()', false, 'cmbC');
    }else{
        _R('cmbC');
        _RE('mousedown', comboFiltroRem);
    }
}
function mer_setUsuarios()
{
    var i = _('axUsr').childNodes.length>0 ? parseInt(_('axUsr').childNodes.item(_('axUsr').childNodes.length-1).id.replace('f', ''))+1 : 0;
    var c = _('usr').value.split("|");
    _('axUsr').innerHTML += '<div class="jm_filaImp" id="f'+i+'" onmouseover="this.className=\'jm_filaSobre\'" onmouseout="this.className=\'jm_filaImp\'">'+
        '<div class="columna" style="width:120px"><input type="hidden" id="a'+i+'" name="a'+i+'" value="'+c[0]+'" />'+c[0]+'</div>'+
        '<div class="columna" style="width:300px"><input type="hidden" id="u'+i+'" name="u'+i+'" value="'+c[1]+'" />'+c[1]+'</div>'+
        '<div class="columna" style="width:25px"><div class="borrar" title="Eliminar" onclick="_R(\'f'+i+'\');">&nbsp;</div></div>'+
        '</div>';
    _R('cmbC');
    _RE('mousedown', comboFiltroRem);
    _('txtUs').value='';
    _('txtUs').focus();
}


/* laboratorios */
function mer_laboratorios()
{
    encerar(true);
    _('filtro').innerHTML='Laboratorio: ' +
        setCaja('txt', 'mer_laboratorioBuscar()', 'buscar') +
        setBoton('button', 'Filtrar', 'mer_laboratorioBuscar()');
    btn.icono(-720, 0, 42, 42, 'Nuevo laboratorio estrat&eacute;gico', "mer_laboratorioEditar(-1);", 'iconos', 'jmFPBT1');
    btn.icono(-800, 0, 42, 42, 'Impresi&oacute;n de reportes', "mer_laboratorioReportes();", 'iconos', 'jmFPBT1');
    setAnchoPanel(3, 590);
    mer_laboratorioBuscar();
}
function mer_laboratorioBuscar()
{
    var w = '';
    var t = _('txt').value.toLowerCase();
    if(t!=''){
        w = "where nombre_comercial like '%"+t+"%'";
    }
    _WR = w;
    new Tabla('d_1', 'jmTbl', '', 'tbl_laboratorio', 'LABORATORIO,MONTO,VIGENCIA', 'id_laboratorio,nombre_comercial,monto,anio_vigencia', '0,200,90,70', (_altTbl-35), 'mer_laboratorioEditar(^)', _WR);
    window.setTimeout( "Ajax.solicitud('getSumaLaboratorios', 'txt="+t+"', false, 'suma')" , 1000);
}
function setSumaLaboratorios(s)
{
    _('d_1').innerHTML += '<div id="suma" class="boton" style="padding-left:210px;padding-right:80px;width:90px;bottom:30px;position:absolute">Σ '+s+'</div>';
}
function mer_laboratorioEditar(k)
{
    Ajax.solicitud('frmLaboratorioEstrategico', 'id='+k, false, 'd_3');
}
function mer_laboratorioGuardar(f)
{
    if(f.numero_idproveedor.value=='' || f.nombre_comercial.value=='' || f.anio_vigencia.value==''){
        alert('Los campos marcados con  *  son obligatorios');
        return false;
    }
    /*if(parseFloat(f.monto.value)<=0){
        alert('El monto tiene que ser mayor que cero');
        return false;
    }
    var monto = parseFloat(f.monto.value);
    var suma = 0;
    var i=0;
    while(_('presupuesto'+i)!=null){
        _('presupuesto'+i).value = _('presupuesto'+i).value !='' ? _('presupuesto'+i).value : 0; 
        suma += parseFloat( _('presupuesto'+i).value );
        i++;
    }
    if(monto !== suma){
        alert('La asignación de presupuestos debe ser igual al monto total del laboratorio estratégico');
        return false;
    }
   _('tope').value = _('axTiPl').childNodes.length>0 ? parseInt(_('axTiPl').childNodes.item(_('axTiPl').childNodes.length-1).id.replace('f', ''))+1 : 0;
    */
    return Ajax.enviarForm(f);
}
function mer_laboratorioReasignacionGuardar(f)
{
    var monto = _('montor').value!='' ? parseFloat(_('montor').value) : 0;
    var suma = 0;
    var i=0;
    while(_('presupuesto'+i)!=null){
        _('presupuesto'+i).value = _('presupuesto'+i).value !='' ? _('presupuesto'+i).value : 0; 
        suma += parseFloat( _('presupuesto'+i).value );
        i++;
    }
    if(monto !== suma){
        alert('La asignación de presupuestos debe ser igual al monto total a asignar');
        return false;
    }
   _('limite').value = _('TiPl').childNodes.length>0 ? parseInt(_('TiPl').childNodes.item(_('TiPl').childNodes.length-1).id.replace('f', ''))+1 : 0;
    
    return Ajax.enviarForm(f);
}
function mer_getProveedor()
{
    var t = _('nombre_comercial').value;
    if(t!=''){
        setComboFiltro('axTxt', 'd_3');
        _('cmbC').style.marginTop='0px';
        if(_esIE){
            if(_('cmbC').style.top!=''){
                _('cmbC').style.top = (parseInt(_('cmbC').style.top) ) + 'px';
            }
        }
        Ajax.solicitud('getProveedores', 'txt='+t+'&obj=cmbC&onC=mer_setProveedor()&por=proveedor', false, 'cmbC');
    }else{
        _R('cmbC');
        _RE('mousedown', comboFiltroRem);
    }
}
function mer_setProveedor()
{
    _('numero_idproveedor').value = _('prov').value;
    _('nombre_comercial').value = _('prov').options[_('prov').selectedIndex].text;
    _R('cmbC');
    _('monto').focus();
}
function mer_asignarPresupuesto(k)
{
    Ventana.crear('vta1', 'ASIGNACION DE PRESUPUESTOS', "ancho=580,alto="+(_altBody-100)+",modal=true,cerrar=true");
    Ajax.solicitud('frmLaboratorioPresupuesto', 'id='+k, false, 'vta1_html');
}
function mer_asignarPresupuestoGuardar(f)
{
    if(f.monto.value==''){
        alert('Debe ingresar el monto para del presupuesto.');
        return false;
    }
    var monto = parseFloat(f.monto.value);
    if(monto<=0){
        alert('El monto tiene que ser mayor que cero');
        return false;
    }
    var suma = 0;
    var i=0;
    while(_('ax_presupuesto'+i)!=null){
        _('ax_presupuesto'+i).value = _('ax_presupuesto'+i).value !='' ? _('ax_presupuesto'+i).value : 0; 
        suma += parseFloat( _('ax_presupuesto'+i).value );
        i++;
    }
    if(monto !== suma){
        alert('La asignación de presupuestos debe ser igual al monto total a asignar');
        return false;
    }
   _('tope').value = _('asTiPl').childNodes.length>0 ? parseInt(_('asTiPl').childNodes.item(_('asTiPl').childNodes.length-1).id.replace('asF', ''))+1 : 0;
    
    return Ajax.enviarForm(f);
}
function mer_laboratorioReportes()
{
    if(_('id_laboratorio')!=null){
        if(_('id_laboratorio').value!=-1){
            var h = '<form onsubmit="return reporte(this)" autocomplete="off">'
            + setOculto('idLab', _('id_laboratorio').value)
            + setOculto('lab', _('nombre_comercial').value)
            + setRadio('z', '{tbl:[{0:"KardexPresupuestoLaboratorio",1:"Kárdex de presupuestos de laboratorio"}]}') + 
            setPieReporte() + 
            '</form>';
            Ventana.crear('vta1', 'REPORTE DE LABORATORIO', "ancho=300,alto=180,modal=true,cerrar=true", h);
        }else{
            alert('Debe visualizar un laboratorio existente');
        }
    }else{
        alert('Primero debe visualizar el formulario del laboratorio');
    }
}

/* planes de mercadeo */
function mer_mercadeo()
{
    encerar(true);
    fijAn = false;
    _('filtro').style.display='none';
    red();
    btn.icono(-720, 0, 42, 42, 'Nuevo plan de mercadeo', "mer_mercadeoEditar(-1);", 'iconos', 'jmFPBT1');
    _('iconos').innerHTML+=setCaja('txt', 'if(_gKC(event)==13){mer_getPlanesMercadeo(0);mer_getActividades(-1, 0, 0);}', 'columna buscar', 'd');
    _('txt').style.margin='10px 11px 10px 0px';
    _('txt').style.width='147px';
    
    _('iconos').innerHTML+='<input type="button" onclick="mer_getFiltroPlanesMercadeo()" style="float:left;width:20px;font-size:18px;margin:11px 6px 5px -32px;height:24px;padding-top:0px;cursor:pointer" value="»" title="mostrar mas opciones de filtro" />';
    
    _('iconos').innerHTML+='<div id="axFiltarPM" class="popup psPanelVerde" style="top:66px;left:220px;width:300px;height:220px;padding:10px;display:none" onmouseover="CMB=false;" onmouseout="CMB=true;"></div>';
    Ajax.solicitud('frmFiltro', 'op=1', false, 'axFiltarPM');
    
    
    var f = new Date();
    _('iconos').innerHTML+= "<input type=\"text\" id=\"axFch\" class=\"columna axCalendario\" value=\""+(f.getDate() + "/" + (f.getMonth()+1) + "/" + f.getFullYear())+"\" onclick=\"SelectorFecha.crear('axFch', 'SQL', 'mer_limpiar_d5(0, 0)')\" >";
    btn.icono(-869, 0, 42, 42, 'Atras', "mer_limpiar_d5(-7, 0)", 'iconos', 'jmFPBT1');
    btn.icono(-904, 0, 42, 42, 'Siguiente', "mer_limpiar_d5(7, 0)", 'iconos', 'jmFPBT1');
    
    _('d_5').style.display=_('d_6').style.display='block';
    setAnchoPanel(1, 200);
    setAnchoPanel(3, parseInt(_anchBody-640) );
    setAnchoPanel(5, 350);
    _('txt').focus();
    /*_pag = 0;
        _('d_1').onscroll=function(){
        if (document.body){
            var alto = (document.body.clientHeight);
        } else {
            var alto = (window.innerHeight);
        }
        var dif = alto-_altEdi;
        _('d_3').innerHTML = _('d_1').scrollTop + ' / ' + dif;
        if(_('d_1').scrollTop==dif){
            mer_getPlanesMercadeo(0);
        }
    };*/
    //mer_getPlanesMercadeo(0);
    //mer_getActividades(-1, 0, 0);
}
function mer_getFiltroPlanesMercadeo()
{
    CMB = true;
    _('axFiltarPM').style.display='block';
    _NE('mouseup', FiltroPMRem);
}
function FiltroPMRem()
{
    if(CMB){
        _('axFiltarPM').style.display='none';
        _RE('mouseup', FiltroPMRem);
    }
}
function mer_limpiar_d5(d, es)
{
    _('d_5').innerHTML = '';
    mer_getActividades(-1, d, es);
}
function mer_getPlanesMercadeo(op)
{
    var s = 'getPlanesMercadeo';
    var p = '';
    if(op===0){
        s = 'getPlanesMercadeoEditar';
        p = '&usr='+_('usr').value;
    }
    Ajax.solicitud(s, 'txt='+_('txt').value+'&estado='+_('estadoPM').value+p, false, 'd_1');
}

function mer_mercadeoEditar(k)
{
    Ventana.crear('vta1', 'ADMINISTRACION DE PLANES DE MERCADEO', "ancho="+(_anchBody-20)+",alto="+(_altBody-20)+",modal=true,cerrar=true");
    Ajax.solicitud('frmPlanMercadeo', 'id='+k+'&alB='+_altBody+'&anB='+_anchBody, false, 'vta1_html');
}
function mer_mercadeoMostrar(k, es)
{
    Ventana.crear('vta1', 'PLAN DE MERCADEO', "ancho="+(_anchBody-10)+",alto="+(_altBody-10)+",modal=true,cerrar=true");
    Ajax.solicitud('verPlanMercadeo', 'id='+k+'&alB='+_altBody+'&estado='+es+'&anB='+_anchBody, false, 'vta1_html');
}
function mer_mercadeoGuardar(f)
{
    if(f.id_tipo_plan.value=='0' || f.plan_mercadeo.value==''){
        alert('Los campos marcados con  *  son obligatorios');
        return false;
    }
    if(!f.a0.checked && !f.a1.checked){
        alert('Debe seleccionar un tipo de alcance');
        return false;
    }
    
    if(f.a0.checked){
        if(f.ev0.checked){ // si el plan evalua ventas
            if(f.fecha_ini.value=='' || f.fecha_fin.value==''){
                alert('Los campos marcados con  *  son obligatorios');
                return false;
            }
            if(_('tipo_alcance_de').value!='i'){
                if(f.fecha_ini_averificar.value=='' || f.fecha_fin_averificar.value==''){
                    alert('Los campos marcados con  *  son obligatorios');
                    return false;
                }
                var hoy = SelectorFecha.getTimestamp(_('hoy').value);
                var ini = SelectorFecha.getTimestamp(f.fecha_ini_averificar.value);
                var fin = SelectorFecha.getTimestamp(f.fecha_fin_averificar.value);
                if(ini < hoy){
                    alert('La fecha desde en las fechas para evaluar la proyección de ventas debe ser mayor a la fecha actual.');
                    return false;
                }
                if(fin < ini){
                    alert('La fecha hasta en las fechas para evaluar la proyección de ventas debe ser mayor a la fecha desde.');
                    return false;
                }
                if(_('tipo_dist_gasto').value == ''){
                    alert('Por favor ingrese la distribución del gasto');
                    mer_setDistGasto();
                    return false;
                }
                if( parseFloat(_('porcentaje_gasto').value)!=100){
                    alert('La sumatoria de los porcentajes de asignación del gasto por oficina debe ser de 100%');
                    return false;
                }
            }
        }
    }
    if(f.a1.checked){
        if(f.dfecha_ini.value=='' || f.dfecha_fin.value=='' || f.dpromedio_ventas.value=='' || f.premio.value==''){
            alert('Los campos marcados con  *  son obligatorios');
            return false;
        }
    }
    
    if(!f.mt0.checked && !f.mt1.checked){
        alert('Los campos marcados con  *  son obligatorios');
        return false;
    }
    
    var i=0;
    var toAus = 0;
    while(_('idLab'+i)!=null){
        var m = _('monto'+i).value;
        if(m!=''){
            if(parseFloat(m) > parseFloat(_('presupuesto'+i).value)){
                alert('El monto $' + m + ' supera el presupuesto disponible del laboratorio para el tipo de plan seleccionando.');
                return false;
            }
        }
        toAus += parseFloat(m);
        i++;
    }

    var l = _('axProv').childNodes.length>0 ? parseInt(_('axProv').childNodes.item(_('axProv').childNodes.length-1).id.replace('fPrv', ''))+1 : 0;
    for(i=0; i<l; i++){
        if(_('montop'+i)!=null){
            var mp = _('montop'+i).value;
            if(mp==''){
                alert('Debe ingresar el monto del auspicio del labortorio no estratégico.');
                return false;
            }else{
                if(parseFloat(mp)==0){
                    alert('El monto del auspicio del labortorio no estratégico debe ser mayor que 0.');
                    return false;
                }
            }
            if(_('numForm'+i).value==''){
                alert('Ingrese el número del formulario.');
                return false;
            }
            toAus += parseFloat(mp);
        }
    }
    if(toAus==0){
        alert("Por favor ingrese un auspicio");
        return false;
    }
    if(_RD(toAus) != _RD(_('ant_total_auspicio').value) && _('id_plan_mercadeo').value!=-1){
        alert('El monto del presupuesto '+_RD(toAus)+' no es igual USD ' + _('ant_total_auspicio').value);
        return false;
    }
    
    
    var l = _('axFrmAdj').childNodes.length>0 ? parseInt(_('axFrmAdj').childNodes.item(_('axFrmAdj').childNodes.length-1).id.replace('rFrAd', ''))+1 : 0;;
    if(f.mt1.checked){
        if(!f.ap0.checked && !f.ap1.checked){
            alert('Debe seleccionar si la mecánica se aplica para promociones o convenios');
            return false;
        }
        if(parseInt(l)==0){
            alert('Debe adjuntar por lo menos el archivo del formulario de promociones o convenios.');
            return false;
        }
        for(var i=0; i<=l; i++){
            if(_('archivo'+i)!=null){
                if(_('descripcion_arch'+i).value==''){
                    alert('Ingrese la/las descripciones para el/los archivos adjuntos.');
                    return false;
                }
            }
        }
        if(f.ope_fecha_ini.value=='' || f.ope_fecha_fin.value==''){
            alert('Ingrese las fechas de inicio y termino para el tipo de mecánica de operaciones');
            return false;
        }
        ini = SelectorFecha.getTimestamp(f.ope_fecha_ini.value);
        fin = SelectorFecha.getTimestamp(f.ope_fecha_fin.value);
        if(ini < hoy){
            alert('La fecha de inicio de la mácanica de operaciones debe ser mayor a la fecha actual.');
            return false;
        }
        if(fin < ini){
            alert('La fecha de finalización de la mácanica de operaciones debe ser mayor o igual a la fecha de inicio.');
            return false;
        }
    }
    
    _('limProv').value = _('axProv').childNodes.length>0 ? parseInt(_('axProv').childNodes.item(_('axProv').childNodes.length-1).id.replace('fPrv', ''))+1 : 0;
    /*if(f.id_plan_mercadeo.value!=-1){
        var mp = 0;
        for(i=0; i<=parseInt(_('limProv').value); i++){
            if(_('montop'+i)!=null){
                mp += parseFloat(_('montop'+i).value);
            }
        }
        if( mp != parseFloat(_('monto_total_no_est').value) ){
            alert('Los cambios en los montos de los laboratorios no estratégicos no deben modificar el monto asignado inicalmente');
            return false;
        }
    }*/
    _('limFar').value = _('axTblFarmacias').childNodes.length>0 ? parseInt(_('axTblFarmacias').childNodes.item(_('axTblFarmacias').childNodes.length-1).id.replace('rF', ''))+1 : 0;
    if(f.a0.checked){
        if(f.ev0.checked){
            if(f.tipo_alcance_de.value=='i'){
                _('limFar').value = _('axTblFarmacias1').childNodes.length>0 ? parseInt(_('axTblFarmacias1').childNodes.item(_('axTblFarmacias1').childNodes.length-1).id.replace('r1F', ''))+1 : 0;
            }
            if(_('limFar').value=='0'){
                alert('Debe registrar por lo menos una farmacia a evaluar para poder guardar el plan de mercadeo');
                return false;
            }
            for(var i=0; i<=parseInt(_('limFar').value); i++){
                if(_('p_crecimiento'+i)!=null){
                    if(_('p_crecimiento'+i).value=='' || _('p_crecimiento'+i).value=='0'){
                        alert('Por favor, ingrese el porcentaje de crecimiento.');
                        return false;
                    }
                    if(_('p_gasto'+i).value=='' || _('p_gasto'+i).value=='0'){
                        alert('Por favor, ingrese el porcentaje de distribución de gasto.');
                        return false;
                    }
                }
            }
        }
    }
    _('limProdFil').value = _('axTblProductosFiltro').childNodes.length>0 ? parseInt(_('axTblProductosFiltro').childNodes.item(_('axTblProductosFiltro').childNodes.length-1).id.replace('rPF', ''))+1 : 0;
    _('limProd').value = _('axTblProductos').childNodes.length>0 ? parseInt(_('axTblProductos').childNodes.item(_('axTblProductos').childNodes.length-1).id.replace('rP', ''))+1 : 0;
    if(f.a1.checked){
        _('limProdFil').value = _('axTblProductosFiltro1').childNodes.length>0 ? parseInt(_('axTblProductosFiltro1').childNodes.item(_('axTblProductosFiltro1').childNodes.length-1).id.replace('r1PF', ''))+1 : 0;
        _('limProd').value = _('axTblProductos1').childNodes.length>0 ? parseInt(_('axTblProductos1').childNodes.item(_('axTblProductos1').childNodes.length-1).id.replace('r1P', ''))+1 : 0;
    }
    _('limVen').value = _('axVen').childNodes.length>0 ? parseInt(_('axVen').childNodes.item(_('axVen').childNodes.length-1).id.replace('rV', ''))+1 : 0;
    _('limCli').value = _('axCli').childNodes.length>0 ? parseInt(_('axCli').childNodes.item(_('axCli').childNodes.length-1).id.replace('rC', ''))+1 : 0;
    _('limAdj').value = _('axAadjuntos').childNodes.length>0 ? parseInt(_('axAadjuntos').childNodes.item(_('axAadjuntos').childNodes.length-1).id.replace('axAdjunto', ''))+1 : 0;
    _('limAdjAct').value = l;
    //_('').value = _RD(toAus);
    return Ajax.enviarForm(f);
}
function mer_mercadeoAnular()
{
    var h='Ingrese el motivo de la anulaci&oacute;n<br />'+
          '<textarea id="motivo" style="width:370px;height:100px"></textarea><br />'+
          '<input type="button" value="anular" onclick="mer_mercadeoAnularConfirmar()">';
    Ventana.crear('vta2', 'ANULACION', "ancho=400,alto=210,modal=true,cerrar=true", h);
    _('motivo').focus();
}
function mer_mercadeoAnularConfirmar()
{
    if(_('motivo').value==''){
        alert('Por favor, ingrese el motivo de la anulación');
        return false;
    }
    Ajax.solicitud('frmPlanMercadeoAnular', 'id='+_('id_plan_mercadeo').value+'&idTP='+_('id_tipo_plan').value+'&planMer='+_('plan_mercadeo').value+'&motivo='+_('motivo').value, true, 'body');
}
function mer_setAlcance(a)
{
    if(a==1){
        _('axEvaVen').style.display=_('alcFar').style.display='block';
        _('alcDis').style.display='none';
    }else{
        _('axEvaVen').style.display=_('alcFar').style.display='none';
        _('alcDis').style.display='block';
    }
}
function mer_setEvaluaVentas(e)
{
    if(e==1){
        _('alcFar').style.display=_('alcFar').style.display='block';
    }else{
        _('alcFar').style.display='none';
    }
}
function mer_verBtnAutorizacion(op)
{
    if(_('btnAut')!=null){
        if(op==1){
            _('btnAut').style.visibility = _('btnAutVer').value;
        }else{
            _('btnAut').style.visibility = 'hidden';
        }
    }
}
function mer_mercadeoEnviarAAut(k)
{
    if(!confirm('Está seguro(a) de enviar el plan de mercadeo a autorizaciones.')){
        return false;
    }
    if(_('ev0').checked){    // si el plan evalua ventas
        var lOfi = _('axTblFarmacias').childNodes.length>0 ? parseInt(_('axTblFarmacias').childNodes.item(_('axTblFarmacias').childNodes.length-1).id.replace('rF', ''))+1 : 0;
        if(_('a0').checked){
            if(_('tipo_alcance_de').value=='i'){
                lOfi = _('axTblFarmacias1').childNodes.length>0 ? parseInt(_('axTblFarmacias1').childNodes.item(_('axTblFarmacias1').childNodes.length-1).id.replace('r1F', ''))+1 : 0;
            }
            if(lOfi=='0'){
                alert('Debe seleccionar por lo menos una farmacia');
                return false;
            }
        }
    }    
    var i=0;
    var ok = false;
    var sum_montos = 0;
    while(_('idLab'+i)!=null){
        var m = _('monto'+i).value;
        if(m!=''){
            if(parseFloat(m) > 0){
                sum_montos += parseFloat(m);
                ok = true;
            }
        }
        i++;
    }
    i=0;  
    var ok1 = false;
    while(i<50){
        if(_('montop'+i)!=null){
            var mp = _('montop'+i).value;
            if(_('numForm'+i).value==''){
                alert('Ingrese el número del formulario.');
                return false;
            }
            if(mp==''){
                alert('Debe ingresar el monto del auspicio del labortorio no estratégico.');
                return false;
            }else{
                if(parseFloat(mp)>0){
                    sum_montos += parseFloat(mp);
                    ok1 = true;
                }
            }
        }
        i++;
    }
    if(!ok && !ok1){
        alert('Debe registrar un monto de presupuesto por lo menos de un laboratorios estratégicos o no estratégico');
        return false;
    }
    var sumEst = _('sum_estgs')!=null ? _('sum_estgs').value : 0;
    sum_montos = parseFloat(sum_montos).toFixed(2);
    if(sum_montos != parseFloat(sumEst).toFixed(2)){
        alert('La sumatoria de los montos de los laboratorios estratégicos y no estratégico ('+sum_montos+') debe ser igual a la sumatoria del gasto de las actividades de todas las estrategias ('+parseFloat(sumEst).toFixed(2)+')');
        return false;
    }
    Ajax.solicitud('frmPlanMercadeoEnviarAAut', 'id='+k, true, 'body');
}
function mer_operacionesAprobar(k, a)
{
    if(a==0){
        if(_('motivo').value==''){
            alert('Debe ingresar el motivo del rechazo');
            return false;
        }
    }
    Ajax.solicitud('frmActividadAprobar', 'id='+k+'&apr='+a+'&mot='+_('motivo').value, true, 'body');
}
function mer_mercadeoAprobar(f)
{
    if(f.aprobado.value==0){
        if(f.motivo.value==''){
            alert('Debe ingresar el motivo del rechazo');
            return false;
        }
    }
    return Ajax.enviarForm(f);
}
function mer_getActividades(k, s, p)
{
    if(s!=0){
        var v = _('axFch').value.split('/');
        var f = new Date(v[2], parseInt(v[1]-1), v[0] );
        var f2 = new Date (f.getTime() + (parseInt(s)*24*3600*1000));
        _('axFch').value = f2.getDate() + '/' + (f2.getMonth()+1) + '/' + f2.getFullYear();
    }
    var sc = 'getActividades'; 
    var us = '';
    if(p===0){
        sc = 'getActividadesEditar'; 
        us = '&usr='+_('usr').value;
    }
    Ajax.solicitud(sc, 'id='+k+'&fecha='+_('axFch').value+'&txt='+_('txt').value+'&estados='+_('estadoPM').value+'&_altTbl='+_altTbl+'&sumar='+s+us, false, 'd_3');
}
function mer_actividadMostrar(k, es, pn)
{
    var p = pn || '';
    Ajax.solicitud('verActividad', 'id='+k+'&est='+(es || '0')+'&pn='+p, false, p+'d_5');
}
function mer_setPlanCuenta(k)
{
    Ajax.solicitud('setIdPlanCuenta', 'id='+k+'&idPC='+_('pre_id_tipo_plan_cuenta').value, false, 'body');
}
function mer_setTipoAlcanceDe(op)
{
    if(op=='i'){
        _('alcFarFil').style.display = _('alcProdFil').style.display = _('axFechProyVentas').style.display = 'none';
        _('alcFarIna').style.display = 'block';
        _('axFI').innerHTML = 'Per&iacute;odo a evaluar desde:';
        _('axFF').innerHTML = 'Per&iacute;odo a evaluar hasta:';
        //_('axVal').innerHTML = 'Valor de la venta:';
    }else{
        _('alcFarFil').style.display = _('alcProdFil').style.display = _('axFechProyVentas').style.display = 'block';
        _('alcFarIna').style.display = 'none';
        _('axFI').innerHTML = 'Fecha para calcular promedio de venta desde:';
        _('axFF').innerHTML = 'Fecha para calcular promedio de ventas hasta:';
        //_('axVal').innerHTML = 'Promedio de ventas:';
    }
    _('axTblFarmacias').innerHTML = _('axTblFarmacias1').innerHTML = '';
}
function mer_marcar(ch)
{
    var i = 0;
    while(_('ch'+i)!=null){
        _('ch'+i).checked = ch ? true : false;
        i++;
    }
}
/* agregar productos */
function mer_mostrarProductos(b)
{
    var g = b || '';
    if(_('axProductos'+g).style.display=='block'){
        _('axProductos'+g).style.display='none';
        _('btnProd'+g).value = 'Mostrar productos';
    }else{
        _('axProductos'+g).style.display='block';
        _('btnProd'+g).value = 'Ocultar productos';
    }
}
function mer_filtarProductos(b)
{
    var g = b || '';
    Ventana.crear('vta3', 'AGREGAR PRODUCTOS', "ancho=780,alto="+(_altBody-50)+",modal=true,cerrar=true");
    Ajax.solicitud('filProductos', 'al='+_altBody+'&grupo='+g, false, 'vta3_html');
    _('axProductos'+g).style.display='block';
    _('btnProd'+g).value = 'Ocultar productos';
    axPg = 0;
    axNpg = 0;
}
function mer_setNavegacion(obj)
{
    btn.icono(-872, -4, 34, 34, 'Anterior', "if(parseInt(axPg)>0){axPg--;_('pag').value=_('axd_nav_pag').value=axPg;Ajax.enviarForm(_('frm3'));}", obj, 'jmFPBT1');
    _(obj).innerHTML+='<div class="d_nav"> &nbsp; <input type=\"text\" style=\"width:50px\" id="axd_nav_pag" value="0" onfocus="this.select();" onkeyup="_numero(event);if(_gKC(event)==13){axPg=_(\'pag\').value=parseInt(this.value);Ajax.enviarForm(_(\'frm3\'));}" onkeydown=\"_NoE(event)\" /> / <span id="axd_nav_pags" ></span> &nbsp; </div>';
    btn.icono(-908, -4, 34, 34, 'Siguiente', "if( parseInt(axPg) < parseInt(axNpg) ){axPg++;_('pag').value=_('axd_nav_pag').value=axPg;Ajax.enviarForm(_('frm3'));}", obj, 'jmFPBT1');
}
function mer_getNiveles(o)
{
    if(_('cmbC')==null){
        setComboFiltro('axNivel');
        CMB = false;
        var d = _('cmbC');
        d.className = 'popup psPanelGris';
        d.style.top='45px';
        d.style.overflow='auto';
        d.style.width='760px';
        d.style.height= (_altBody-150) + 'px';
        Ajax.solicitud('frmNivel', 'ojP=cmbC', false, 'cmbC');
    }
    var ob = o.replace('s', '');
    if(_('t'+ob)!=null){
        if(_('t'+ob).src.indexOf('img/carpeta.png')>0 ){
            Ajax.solicitud('frmNivel', 'ojP='+o, false, o);
            _('t'+ob).src = 'img/carpetaAbierta.png';
        }else{
            _('t'+ob).src = 'img/carpeta.png';
            _('s'+ob).innerHTML = '';
        }
    }
}
function mer_setNivel(n, d)
{
    _('ax_cod_nivel').value=n;
    _('ax_desc_nivel').value=d;
    CMB = true;
    comboFiltroRem();
}
function mer_getLineas()
{
    Ajax.solicitud('getCmbLineas', 'clase='+_('ax_clase').value, false, 'axLineas');
}
function mer_agregarProductosFiltro(b)
{
    var g = b || ''; 
    var p = _('axTblProductosFiltro'+g).childNodes.length>0 ? parseInt(_('axTblProductosFiltro'+g).childNodes.item(_('axTblProductosFiltro'+g).childNodes.length-1).id.replace('r'+g+'PF', ''))+1 : 0;
    var linea = _('ax_linea').value;
    var desc = 'TODAS'; 
    if(linea!=0){
        desc = _('ax_linea').options[_('ax_linea').selectedIndex].text;
    }
    _('axTblProductosFiltro'+g).innerHTML += "<div class=\"fila jm_filaImp\" id=\"r"+g+"PF"+p+"\" onmouseover=\"this.className='fila jm_filaSobre'\" onmouseout=\"this.className='fila jm_filaImp'\">"+
    "<div class=\"columna\" style=\"width:148px\"><input type=\"hidden\" id=\"cod_nivel"+p+"\" name=\"cod_nivel"+p+"\" value=\""+_('ax_cod_nivel').value+"\" />"
            + "<input type=\"text\" id=\"desc_nivel"+p+"\" name=\"desc_nivel"+p+"\" class=\"texto\" style=\"width:144px\" readonly value=\""+_('ax_desc_nivel').value+"\" /></div>"+
    "<div class=\"columna\" style=\"width:148px\"><input type=\"hidden\" id=\"clase"+p+"\" name=\"clase"+p+"\" value=\""+_('ax_clase').value+"\" />"
            + "<input type=\"text\" id=\"desc_clase"+p+"\" name=\"desc_clase"+p+"\" class=\"texto\" style=\"width:144px\" readonly value=\""+_('ax_clase').options[_('ax_clase').selectedIndex].text+"\" /></div>"+
    "<div class=\"columna\" style=\"width:148px\"><input type=\"hidden\" id=\"linea"+p+"\" name=\"linea"+p+"\" value=\""+linea+"\" />"
            + "<input type=\"text\" id=\"desc_linea"+p+"\" name=\"desc_linea"+p+"\" class=\"texto\" style=\"width:144px\" readonly value=\""+desc+"\" /></div>"+         
    "<div class=\"columna\" style=\"width:18px;\"><div class=\"borrar\" title=\"Eliminar\" onclick=\"_R('r"+g+"PF"+p+"');\">&nbsp;</div></div>"+
    "</div>";
    mer_verBtnAutorizacion(0);
}
function mer_agregarProductos(b)
{
    var g = b || ''; 
    var p = _('axTblProductos'+g).childNodes.length>0 ? parseInt(_('axTblProductos'+g).childNodes.item(_('axTblProductos'+g).childNodes.length-1).id.replace('r'+g+'P', ''))+1 : 0;
    var i=0;
    while(_('axrP'+i)!=null){ 
        if( _('ch'+i).checked && !enProductos(p, _('axCodigo'+i).value) ){
            _('axTblProductos'+g).innerHTML += "<div class=\"fila jm_filaImp\" id=\"r"+g+"P"+p+"\" onmouseover=\"this.className='fila jm_filaSobre'\" onmouseout=\"this.className='fila jm_filaImp'\">"+
            "<div class=\"columna\" style=\"width:435px\"><input type=\"hidden\" id=\"codigo"+p+"\" name=\"codigo"+p+"\" value=\""+_('axCodigo'+i).value+"\" />"
                    + "<input type=\"hidden\" id=\"descripcion"+p+"\" name=\"descripcion"+p+"\" value=\""+_('axDescripcion'+i).value+"\" />"+_('axDescripcion'+i).value+"</div>"+
            "<div class=\"columna\" style=\"width:18px;\"><div class=\"borrar\" title=\"Eliminar\" onclick=\"_R('r"+g+"P"+p+"');\">&nbsp;</div></div>"+
            "</div>";
            p++;
        }
        i++;
    }
    mer_verBtnAutorizacion(0);
}
function enProductos(l, cod)
{
    for(var j=0; j<=l; j++){
        if( _('codigo'+j) != null ){
            if( _('codigo'+j).value==cod ){
                return true;
            }
        }
    }
    return false;
}
/* agregar farmacias */
function mer_mostrarFarmacias(b)
{
    var g = b || '';
    if(_('axFarmacias'+g).style.display=='block'){
        _('axFarmacias'+g).style.display='none';
        _('btnFar'+g).value = 'Mostrar farmacias';
    }else{
        _('axFarmacias'+g).style.display='block';
        _('btnFar'+g).value = 'Ocultar farmacias';
    }
}
function mer_filtarFarmacias(b)
{
    var g = b || '';
    Ventana.crear('vta2', 'AGREGAR FARMACIAS', "ancho=780,alto="+(_altBody-100)+",modal=true,cerrar=true", "", "if(_('tipo_alcance_de').value!='i'){mer_calcularVentas();}");
    Ajax.solicitud('filFarmacias', 'al='+_altBody+'&grupo='+g, false, 'vta2_html');
    _('axFarmacias'+g).style.display='block';
    _('btnFar'+g).value = 'Ocultar farmacias';
}
function mer_getCiudades()
{
    Ajax.solicitud('getCmbCiudades', 'idProv='+_('idProv').value, false, 'axCiudades');
}
function mer_agregarFarmacias(b)
{
    var g = b || ''; 
    var p = _('axTblFarmacias'+g).childNodes.length>0 ? parseInt(_('axTblFarmacias'+g).childNodes.item(_('axTblFarmacias'+g).childNodes.length-1).id.replace('r'+g+'F', ''))+1 : 0;
    var i=0;
    if(g=='1'){
        while(_('axrF'+i)!=null){
            if( _('ch'+i).checked && !enFarmacias(p, _('axOficina'+i).value) ){
                _('axTblFarmacias1').innerHTML += "<div class=\"fila jm_filaImp\" id=\"r1F"+p+"\" onmouseover=\"this.className='fila jm_filaSobre'\" onmouseout=\"this.className='fila jm_filaImp'\">"+
                "<div class=\"columna\" style=\"width:315px\"><input type=\"hidden\" id=\"oficina"+p+"\" name=\"oficina"+p+"\" value=\""+_('axOficina'+i).value+"\" />"
                        + "<input type=\"text\" id=\"nombre"+p+"\" name=\"nombre"+p+"\" class=\"texto\" style=\"width:313px\" value=\""+_('axNombre'+i).value+"\" /></div>"+
                "<div class=\"columna\" style=\"width:85px;\"><input type=\"text\" class=\"caja\" id=\"proy_ventas"+p+"\" name=\"proy_ventas"+p+"\" style=\"width:73px\" value=\"0\" /></div>"+
                '<div class="columna" style="width:20px;"><div class="borrar" title="Eliminar" onclick="_R(\'r1F'+p+'\');">&nbsp;</div></div>'+
                "</div>";
                p++;
            }
            i++;
        }
    }else{
        while(_('axrF'+i)!=null){
            if( _('ch'+i).checked && !enFarmacias(p, _('axOficina'+i).value) ){
                _('axTblFarmacias').innerHTML += "<div class=\"fila jm_filaImp\" id=\"rF"+p+"\" onmouseover=\"this.className='fila jm_filaSobre'\" onmouseout=\"this.className='fila jm_filaImp'\">"+
                "<div class=\"columna\" style=\"width:195px\"><input type=\"hidden\" id=\"oficina"+p+"\" name=\"oficina"+p+"\" value=\""+_('axOficina'+i).value+"\" />"
                        + "<input type=\"text\" id=\"nombre"+p+"\" name=\"nombre"+p+"\" class=\"texto\" style=\"width:193px\" value=\""+_('axNombre'+i).value+"\" /></div>"+
                "<div class=\"columna\" style=\"width:75px;\"><input type=\"text\" class=\"caja\" id=\"p_ventas"+p+"\" name=\"p_ventas"+p+"\" style=\"width:73px\" value=\"0\" readonly /></div>"+
                "<div class=\"columna\" style=\"width:45px;\"><input type=\"text\" class=\"caja\" onkeypress=\"_evaluar(event, '0123456789.')\" id=\"p_crecimiento"+p+"\" name=\"p_crecimiento"+p+"\" style=\"width:43px\" value=\"0\" onkeyup=\"mer_calcularProyecciones()\" /></div>"+
                "<div class=\"columna\" style=\"width:75px;\"><input type=\"text\" class=\"caja\" id=\"proy_ventas"+p+"\" name=\"proy_ventas"+p+"\" style=\"width:73px\" value=\"0\" readonly /></div>"+
                "<div class=\"columna\" style=\"width:75px;\"><input type=\"text\" class=\"caja\" onkeypress=\"_evaluar(event, '0123456789.');mer_calcularGastosPermitir(event);\" id=\"p_gasto"+p+"\" name=\"p_gasto"+p+"\" style=\"width:70px\" value=\"0\" onkeyup=\"mer_calcularGastosPorcentaje()\" /></div>"+
                '<div class="columna" style="width:20px;"><div class="borrar" title="Eliminar" onclick="_R(\'rF'+p+'\');mer_calcularVentas();mer_calcularGastos();">&nbsp;</div></div>'+
                "</div>";
                p++;
            }
            i++;
        }
    }
    mer_verBtnAutorizacion(0);
}
function enFarmacias(l, cod)
{
    for(var j=0; j<=l; j++){
        if( _('oficina'+j) != null ){
            if( _('oficina'+j).value==cod ){
                return true;
            }
        }
    }
    return false;
}
function mer_setPCcrecimiento()
{
    var lOfi = _('axTblFarmacias').childNodes.length>0 ? parseInt(_('axTblFarmacias').childNodes.item(_('axTblFarmacias').childNodes.length-1).id.replace('rF', ''))+1 : 0;
    for(var i=0; i<lOfi; i++){
        if(_('p_crecimiento'+i)!=null){
            _('p_crecimiento'+i).value = _('p_crecimiento').value;
        }
    }
    mer_calcularProyecciones();
    //mer_calcularGastos();
}
function mer_setPGasto()
{
    if(_('tipo_dist_gasto').value == 'p'){
        var lOfi = _('axTblFarmacias').childNodes.length>0 ? parseInt(_('axTblFarmacias').childNodes.item(_('axTblFarmacias').childNodes.length-1).id.replace('rF', ''))+1 : 0;
        var s = 0;
        for(var i=0; i<lOfi; i++){
            if(_('p_gasto'+i)!=null){
                _('p_gasto'+i).value = _('p_gasto').value;
                s += parseFloat(_('p_gasto'+i).value);
            }
        }
        _('porcentaje_gasto').value = _RD(s);
    }else{
        alert('Cambie el tipo de distribución del gasto a [Porcentaje] para poder asignar manualmente');
    }
}

/* INAUGURACION */
/*function mer_getFarmacia()
{
    var t = _('nombrei').value;
    if(t!=''){
        setComboFiltro('axTxtFar', 'pm_d_1');
        if(_esIE){
            if(_('cmbC').style.top!=''){
                _('cmbC').style.top = (parseInt(_('cmbC').style.top) - 36) + 'px';
            }
        }
        Ajax.solicitud('getFarmacia', 'txt='+t+'&obj=cmbC&onC=mer_setFarmacia()', false, 'cmbC');
    }else{
        _R('cmbC');
        _RE('mousedown', comboFiltroRem);
    }
}
function mer_setFarmacia()
{
    var o = _('farIna');
    _('oficinai').value = o.value;
    _('nombrei').value = o.options[o.selectedIndex].text;
    _R('cmbC');
    _RE('mousedown', comboFiltroRem);

}*/
function mer_setDistGasto()
{
    var h = "<p><label><input type='radio' id='tiAsGa0' name='tiAsGa' "+(_('tipo_dist_gasto').value=='e'?'checked':'')+" value='e' onclick=\"_('tipo_dist_gasto').value='e';mer_calcularGastos();\" />Equitativa</label> &nbsp;&nbsp;&nbsp;&nbsp; ";
    h += "<label><input type='radio' id='tiAsGa1' name='tiAsGa' "+(_('tipo_dist_gasto').value=='v'?'checked':'')+" value='v' onclick=\"_('tipo_dist_gasto').value='v';mer_calcularGastos();\" />Promedio de ventas</label> &nbsp;&nbsp;&nbsp;&nbsp; ";
    h += "<label><input type='radio' id='tiAsGa2' name='tiAsGa' "+(_('tipo_dist_gasto').value=='p'?'checked':'')+" value='p' onclick=\"_('tipo_dist_gasto').value='p';mer_calcularGastos();\" />Porcentajes</label></p>";
    h += '<div style="text-align:right"><input type="button" value="cerrar" onclick="_R(\'vta2\');_R(\'bloq_vta2\');" /></div>';
    Ventana.crear('vta2', 'TIPO DE DISTRIBUCION DE GASTO', "ancho=400,alto=120,modal=true,cerrar=true", h);
}
function mer_calcularVentas()
{
    _('axFarmacias').style.display='block';
    var fecha_promedios = SelectorFecha.getTimestamp(_('fecha_promedios').value);
    var fi = _('fecha_ini').value;
    var ff = _('fecha_fin').value;
    if(fi=='' || ff==''){
        alert('Debe ingresar las fechas desde y hasta');
        return false;
    }
    var ini = SelectorFecha.getTimestamp(fi);
    var fin = SelectorFecha.getTimestamp(ff);
    if(ini > fecha_promedios || fin > fecha_promedios){
        alert('Las fechas desde y hasta deben ser menores por lo menos dos días antes a la fecha actual.');
        return false;
    }
    if(fin < ini){
        alert('La fecha hasta debe ser mayor o igual a la fecha de desde.');
        return false;
    }
    
    var lProF = _('axTblProductosFiltro').childNodes.length>0 ? parseInt(_('axTblProductosFiltro').childNodes.item(_('axTblProductosFiltro').childNodes.length-1).id.replace('rPF', ''))+1 : 0;
    var prCNiv = '';
    var prCCla = '';
    var prCLin = '';
    for(var i=0; i<lProF; i++){
        if(_('cod_nivel'+i)!=null){
            prCNiv += "'" + _('cod_nivel'+i).value + "',";
            prCCla += "'" + _('clase'+i).value + "',";
            prCLin += "'" + _('linea'+i).value + "',";
        }
    }
    if(prCNiv != ''){
        prCNiv = prCNiv.substr(0, prCNiv.length-1);
        prCCla = prCCla.substr(0, prCCla.length-1);
        prCLin = prCLin.substr(0, prCLin.length-1);
    }
    
    var lPro = _('axTblProductos').childNodes.length>0 ? parseInt(_('axTblProductos').childNodes.item(_('axTblProductos').childNodes.length-1).id.replace('rP', ''))+1 : 0;
    var pr = '';
    for(var i=0; i<lPro; i++){
        if(_('codigo'+i)!=null){
            pr += "'" + _('codigo'+i).value + "',";
        }
    }
    if(pr != ''){
        pr = pr.substr(0, pr.length-1);
    }
    
    var lOfi = _('axTblFarmacias').childNodes.length>0 ? parseInt(_('axTblFarmacias').childNodes.item(_('axTblFarmacias').childNodes.length-1).id.replace('rF', ''))+1 : 0;
    if(lOfi == ''){
        alert('Debe seleccionar por lo menos una farmacia');
        return false;
    }
    var of = '';
    for(var i=0; i<lOfi; i++){
        if(_('oficina'+i)!=null){
            of += "'" + _('oficina'+i).value + "',";
        }
    }
    if(of != ''){
        of = of.substr(0, of.length-1);
    }
    
    Ajax.solicitud('getVentas', 'fi='+_('fecha_ini').value+'&ff='+_('fecha_fin').value+'&arts='+pr+'&prCNiv='+prCNiv+
            '&prCCla='+prCCla+'&prCLin='+prCLin+'&ofs='+of, true, 'body');
}
function mer_setVentas()
{
    if(_('axVentas').innerHTML != '{tbl:]}'){
        var js = eval('(' + _('axVentas').innerHTML + ')'); 
        var lOfi = _('axTblFarmacias').childNodes.length>0 ? parseInt(_('axTblFarmacias').childNodes.item(_('axTblFarmacias').childNodes.length-1).id.replace('rF', ''))+1 : 0;
        for(var i=0; i<lOfi; i++){
            if(_('oficina'+i)!=null && _('p_ventas'+i)!=null){
                var p =_enMatrizJSON( js, _('oficina'+i).value, 0 );
                var v = p!=-1 ? js.tbl[p][1] : 0;
                _('p_ventas'+i).value = v;
            }
        }
        mer_calcularProyecciones();
        mer_calcularGastos();
    }
}
function mer_calcularProyecciones()
{
    var t = 0;
    var tpy = 0;
    var lOfi = _('axTblFarmacias').childNodes.length>0 ? parseInt(_('axTblFarmacias').childNodes.item(_('axTblFarmacias').childNodes.length-1).id.replace('rF', ''))+1 : 0;
    for(var i=0; i<lOfi; i++){
        if(_('p_ventas'+i)!=null){
            var v = _('p_ventas'+i).value!='' ? _('p_ventas'+i).value : 0;
            var pc = _('p_crecimiento'+i).value!='' ? _('p_crecimiento'+i).value : 0; 
            var py = _RD( parseFloat(v) + (parseFloat(v) * parseFloat(pc) / 100) ); 
            _('proy_ventas'+i).value = py;
            t += parseFloat(v);
            tpy += parseFloat(py);
        }
    }
    _('promedio_ventas').value = _RD(t);
    _('proyeccion_ventas').value = _RD(tpy);
}
function mer_calcularGastos()
{
    var lOfi = _('axTblFarmacias').childNodes.length>0 ? parseInt(_('axTblFarmacias').childNodes.item(_('axTblFarmacias').childNodes.length-1).id.replace('rF', ''))+1 : 0;
    var tdg = _('tipo_dist_gasto').value;
    if(tdg == ''){
        mer_setDistGasto();
    }
    var tpg = 0;
    var porc_gast = 0;
    var i =0; 
    _('p_gasto').value = '';
    if(tdg == 'e'){
        var num = 0;
        for(i=0; i<lOfi; i++){
            if(_('p_gasto'+i)!=null){
                num++;
            }
        }
        var pc = 100 / num;
        for(i=0; i<lOfi-1; i++){
            if(_('p_gasto'+i)!=null){
                _('p_gasto'+i).value = _RD(pc);
                tpg += _RD(pc);
            }
        }
        _('p_gasto'+i).value = 100 - tpg;
        porc_gast = tpg + parseFloat(_('p_gasto'+i).value);
    }    
    if(tdg == 'v'){
        var t = 0;
        for(i=0; i<lOfi; i++){
            if(_('p_gasto'+i)!=null){
                var v = _('p_ventas'+i).value!='' ? _('p_ventas'+i).value : 0;
                t += parseFloat(v);
            }
        }
        for(i=0; i<lOfi-1; i++){
            if(_('p_gasto'+i)!=null){
                var v = _('p_ventas'+i).value!='' ? parseFloat(_('p_ventas'+i).value) : 0;
                var pc = v * 100 / t;
                _('p_gasto'+i).value = _RD(pc);
                tpg += _RD(pc);
            }
        }
        _('p_gasto'+i).value = 100 - tpg;
        porc_gast = tpg + parseFloat(_('p_gasto'+i).value);
    }  
    if(tdg == 'p'){
        for(var i=0; i<lOfi; i++){
            if(_('p_gasto'+i)!=null){
                _('p_gasto'+i).value = 0;
            }
        }
        tpg = 0;
    }    
    _('porcentaje_gasto').value = _RD(porc_gast);
}
function mer_calcularGastosPermitir(e)
{
    var tdg = _('tipo_dist_gasto').value;
    if(tdg != 'p'){
        _inh(e);
    }
}
function mer_calcularGastosPorcentaje()
{
    var lOfi = _('axTblFarmacias').childNodes.length>0 ? parseInt(_('axTblFarmacias').childNodes.item(_('axTblFarmacias').childNodes.length-1).id.replace('rF', ''))+1 : 0;
    var porc_gast = 0;
    for(var i=0; i<lOfi; i++){
        if(_('p_gasto'+i)!=null){
            porc_gast += parseFloat(_('p_gasto'+i).value) || 0;
        }
    }
    _('porcentaje_gasto').value = _RD(porc_gast);
}
/*function mer_calcularGastosPorcentaje()
{
    var lOfi = _('axTblFarmacias').childNodes.length>0 ? parseInt(_('axTblFarmacias').childNodes.item(_('axTblFarmacias').childNodes.length-1).id.replace('rF', ''))+1 : 0;
    var s = 0;
    for(var i=0; i<lOfi; i++){
        if(_('p_gasto'+i)!=null){
            var g = _('p_gasto'+i).value || 0;
            s += parseFloat(g);
        }
    }
    _('porcentaje_gasto').value = _RD(s);
}*/
function mer_confirmarGasto(i, m, numAus)
{
    Ajax.solicitud('confirmarGasto', 'idPM='+_('id_plan_mercadeo').value+'&id_prov='+_('ruc'+i).value+'&obj=confr'+i+'&monto='+m+'&numAus='+numAus+'&i='+i, false, 'body');
    _('confr'+i).style.visibility = 'hidden';
}
/* distribucion  */
function mer_getVendedores()
{
    var t = _('txtVen').value;
    if(t!=''){
        setComboFiltro('axTxtVen', 'pm_d_1');
        Ajax.solicitud('getVendedores', 'txt='+t+'&obj=cmbC&onC=mer_setVendedores()', false, 'cmbC');
    }else{
        _R('cmbC');
        _RE('mousedown', comboFiltroRem);
    }
}
function mer_setVendedores()
{
    var i = _('axVen').childNodes.length>0 ? parseInt(_('axVen').childNodes.item(_('axVen').childNodes.length-1).id.replace('rV', ''))+1 : 0;
    var c = _('vend').value;
    var n = _('vend').options[_('vend').selectedIndex].text;
    _('axVen').innerHTML += '<div class="jm_filaImp" id="rV'+i+'" onmouseover="this.className=\'jm_filaSobre\'" onmouseout="this.className=\'jm_filaImp\'">'+
        '<div class="columna" style="width:435px"><input type="hidden" id="codigo_vendedor'+i+'" name="codigo_vendedor'+i+'" value="'+c+'" />'+
        '<input type="hidden" id="nombre_vendedor'+i+'" name="nombre_vendedor'+i+'" value="'+n+'" />'+n+'</div>'+
        '<div class="columna" style="width:25px"><div class="borrar" title="Eliminar" onclick="_R(\'rV'+i+'\');">&nbsp;</div></div>'+
        '</div>';
    _R('cmbC');
    _RE('mousedown', comboFiltroRem);
    _('txtVen').value='';
    _('txtVen').focus();
}
function mer_getClientes()
{
    var t = _('txtCli').value;
    if(t!=''){
        setComboFiltro('axTxtCli', 'pm_d_1');
        Ajax.solicitud('getClientes', 'txt='+t+'&obj=cmbC&onC=mer_setClientes()', false, 'cmbC');
    }else{
        _R('cmbC');
        _RE('mousedown', comboFiltroRem);
    }
}
function mer_setClientes()
{
    var i = _('axCli').childNodes.length>0 ? parseInt(_('axCli').childNodes.item(_('axCli').childNodes.length-1).id.replace('rC', ''))+1 : 0;
    var c = _('clie').value;
    var n = _('clie').options[_('clie').selectedIndex].text;
    _('axCli').innerHTML += '<div class="jm_filaImp" id="rC'+i+'" onmouseover="this.className=\'jm_filaSobre\'" onmouseout="this.className=\'jm_filaImp\'">'+
        '<div class="columna" style="width:435px"><input type="hidden" id="numero_idcliente'+i+'" name="numero_idcliente'+i+'" value="'+c+'" />'+
        '<input type="hidden" id="nombre_comercial'+i+'" name="nombre_comercial'+i+'" value="'+n+'" />'+n+'</div>'+
        '<div class="columna" style="width:25px"><div class="borrar" title="Eliminar" onclick="_R(\'rC'+i+'\');">&nbsp;</div></div>'+
        '</div>';
    _R('cmbC');
    _RE('mousedown', comboFiltroRem);
    _('txtCli').value='';
    _('txtCli').focus();
    mer_verBtnAutorizacion(0);
}
function mer_finTransferirAdjunto(m, a)
{
    if(m==0){
        var i = _('axAadjuntos').childNodes.length>0 ? parseInt(_('axAadjuntos').childNodes.item(_('axAadjuntos').childNodes.length-1).id.replace('axAdjunto', ''))+1 : 0;
        if( enAdjuntos(i, a) ){
            m = 'El archivo con ese nombre ya se encuentra adjunto';
        }else{    
            _('axAadjuntos').innerHTML += '<div id="axAdjunto'+i+'" class="psPanelNaranja axAdjunto">'
                + '<input style="width:190px;float:left" id="adjunto'+i+'" name="adjunto'+i+'" class="cajaNaranja" readonly type="text" value="'+a+'" title="'+a+'" />'
                + '<div class="borrar" style="float:right;margin-top:4px;" title="Eliminar" onclick="_R(\'axAdjunto'+i+'\');">&nbsp;</div>'
                +'</div>';
            _R('bloq_vta4');
            _R('vta4');
            return false;
        }
    }
    _('cargando').style.display = 'none';
    _('frmArchivo').style.display = 'block';
    _('archivo').value='';
    alert(m);
    return false;
}
function enAdjuntos(l, cod)
{
    for(var j=0; j<=l; j++){
        if( _('adjunto'+j) != null ){
            if( _('adjunto'+j).value==cod ){
                return true;
            }
        }
    }
    return false;
}
/*  auspicios */
function mer_getAuspicios()
{
    Ventana.crear('vta2', 'LABORATORIOS NO ESTRATEGICOS', "ancho=600,alto=300,modal=true,cerrar=true");
    Ajax.solicitud('getAuspicios', 'idTP='+_('id_tipo_plan').value, false, 'vta2_html');
}
function mer_setAuspicios()
{
    var x=0;
    while(_('rucAus'+x)!=null){
        var l = _('axProv').childNodes.length>0 ? parseInt(_('axProv').childNodes.item(_('axProv').childNodes.length-1).id.replace('fPrv', ''))+1 : 0;
        if(_('chAus'+x).checked && !mer_hayAuspicio(_('numAus'+x).innerHTML, l)){
            var i = l;
            var raSo = _('provAus'+x).value;
            var d = document.createElement('div');
            d.id = 'fPrv'+i;
            d.className = 'fila jm_filaImp';
            d.onmouseover = "this.className='jm_filaSobre'";
            d.onmouseout = "this.className='jm_filaImp'";
            _('axProv').appendChild(d);
            d.innerHTML = '<div class="columna" style="width:260px"><input type="hidden" id="ruc'+i+'" name="ruc'+i+'" value="'+_('rucAus'+x).value+'" />'+
            '<input type="hidden" id="fechReg'+i+'" name="fechReg'+i+'" value="'+_('hoy').value+'" />'+
            '<input type="hidden" id="nomCom'+i+'" name="nomCom'+i+'" value="'+raSo+'" />'+
            '<input type="text" class="texto" style="width:258px" value="'+raSo+'" readonly /></div>'+
            '<div class="columna" style="width:84px;"><input class="caja" type="text" id="montop'+i+'" name="montop'+i+'" value="'+_('monAus'+x).innerHTML+'" style="width:80px;" readonly /></div>'+
            '<div class="columna" style="width:84px;"><input class="texto" type="text" id="numForm'+i+'" name="numForm'+i+'" value="'+_('numAus'+x).innerHTML+'" style="width:80px;" readonly /></div>'+
            '<div class="columna" style="width:25px"><div id="noEstEli'+i+'" class="borrar" title="Eliminar" onclick="_R(\'fPrv'+i+'\');mer_postBorrarProv();">&nbsp;</div></div>';
        }
        x++;
    }
    _R('bloq_vta2');
    _R('vta2');
    mer_sumAuspicios();
    mer_verBtnAutorizacion(0);
}
function mer_hayAuspicio(na, l)
{
    for(i=0; i<l; i++){
        if(_('fPrv'+i)!=null && _('montop'+i)!=null){
            if(_('numForm'+i).value == na){
                return true;
            }
        }
    }
    return false;
}
function mer_sumAuspicios()
{
    var t = 0;
    var i =0;
    while(_('monto'+i)!=null){
        t += parseFloat(_('monto'+i).value);
        i++;
    }
    
    var l = _('axProv').childNodes.length>0 ? parseInt(_('axProv').childNodes.item(_('axProv').childNodes.length-1).id.replace('fPrv', ''))+1 : 0;
    for(i=0; i<l; i++){
        if(_('montop'+i)!=null){
            t += parseFloat(_('montop'+i).value);
        }
    }
    _('total_auspicio').value = _RD(t);
}

/*function mer_getProveedores()
{
    var t = _('txtProv').value;
    if(t!=''){
        setComboFiltro('axTxt', 'pm_d_1');
        if(_esIE){
            if(_('cmbC').style.top!=''){
                _('cmbC').style.top = (parseInt(_('cmbC').style.top) - 30) + 'px';
            }
        }
        Ajax.solicitud('getProveedores', 'txt='+t+'&obj=cmbC&onC=mer_setProveedores()', false, 'cmbC');
    }else{
        _R('cmbC');
        _RE('mousedown', comboFiltroRem);
    }
}
function mer_setProveedores()
{
    var i = _('axProv').childNodes.length>0 ? parseInt(_('axProv').childNodes.item(_('axProv').childNodes.length-1).id.replace('fPrv', ''))+1 : 0;
    var idP = _('prov').value;
    var raSo = _('prov').options[_('prov').selectedIndex].text;
    var d = document.createElement('div');
    d.id = 'fPrv'+i;
    d.className = 'fila jm_filaImp';
    d.onmouseover = "this.className='jm_filaSobre'";
    d.onmouseout = "this.className='jm_filaImp'";
    _('axProv').appendChild(d);
    d.innerHTML = '<div class="columna" style="width:310px"><input type="hidden" id="ruc'+i+'" name="ruc'+i+'" value="'+idP+'" />'+
        '<input type="hidden" id="fechReg'+i+'" name="fechReg'+i+'" value="'+_('hoy').value+'" />'+
        '<input type="hidden" id="nomCom'+i+'" name="nomCom'+i+'" value="'+raSo+'" />'+raSo+'</div>'+
        '<div class="columna" style="width:72px;"><input class="caja" type="text" id="montop'+i+'" name="montop'+i+'" size="8" onkeypress="_numero(event)" /></div>'+
        '<div class="columna" style="width:72px;"><input class="caja" type="text" id="numForm'+i+'" name="numForm'+i+'" size="6" /></div>'+
        '<div class="columna" style="width:25px"><div id="noEstEli'+i+'" class="borrar" title="Eliminar" onclick="_R(\'fPrv'+i+'\');mer_postBorrarProv();">&nbsp;</div></div>';
    _R('cmbC');
    _RE('mousedown', comboFiltroRem);
    //_('axConfAus').style.display='block';
    _('txtProv').value='';
    _('txtProv').focus();
    mer_verBtnAutorizacion(0);
}*/
function mer_getLaboratorios()
{
    Ajax.solicitud('getLaboratoriosAuspicio', 'id='+_('id_plan_mercadeo').value+'&idTiPl='+_('id_tipo_plan').value, false, 'axLabsAus');
}
/*function mer_getUsrConfAusp()
{
    Ajax.solicitud('getUsrConfAusp', 'id='+_('id_plan_mercadeo').value+'&idTiPl='+_('id_tipo_plan').value, false, 'axConfAus');
}
function mer_postBorrarProv()
{
    var i = _('axProv').childNodes.length>0 ? parseInt(_('axProv').childNodes.item(_('axProv').childNodes.length-1).id.replace('f', ''))+1 : 0;
    if(i==0){
        _('axConfAus').style.display='none';
    }
}*/

/*  estrategias */
function mer_estrategiaEditar(k)
{
    Ajax.solicitud('frmEstrategia', 'id='+k+'&estado='+_('estado').value+'&plan_completo='+_('plan_completo').value+'&id_plan_mercadeo='+_('id_plan_mercadeo').value, false, 'pm_d_3');
    _('pm_d_5').innerHTML = '';
}
function mer_estrategiaMostrar(k)
{
    Ajax.solicitud('verEstrategia', 'id='+k+'&id_plan_mercadeo='+_('id_plan_mercadeo').value+'&estado='+_('estado').value, false, 'pm_d_3');
    _('pm_d_5').innerHTML = '';
}
function mer_estrategiaGuardar(f)
{
    if(f.estrategia.value=='' || f.concepto.value==''){
        alert('Todos los campos marcados con * son obligatorios');
        return false;
    }
    return Ajax.enviarForm(f);
}
function mer_estrategiaEliminar(k)
{
    if(confirm('Está seguro(a) de querer eliminar la estrategia y todas las actividades que ésta contiene, ya no podrá recuperar la información.')){
        Ajax.solicitud('frmEstrategiaEliminar', 'id='+k+'&id_plan_mercadeo='+_('id_plan_mercadeo').value, false, 'axActividades');
    }
}
function mer_getEstrategias()
{
    Ajax.solicitud('getEstrategias', 'id_plan_mercadeo='+_('id_plan_mercadeo').value, false, 'axEstrategias');
}
/*  actividades */
function mer_actividadEditar(k)
{
    Ajax.solicitud('frmActividad', 'id='+k+'&id_estrategia='+_('id_estrategia').value+'&id_plan_mercadeo1='+_('id_plan_mercadeo').value+
            '&id_tipo_plan='+_('id_tipo_plan').value+'&estado='+_('estado1').value+'&plan_completo='+_('plan_completo1').value+
            '&fecha_creacion='+_('fecha_creacion').value, false, 'pm_d_5');
}
function mer_actividadGuardar(f)
{
    if(f.p0.checked){
        f.actividad.value=f.actividadf.options[f.actividadf.selectedIndex].text;
    }
    if(f.actividad.value=='' || f.act_fecha_ini.value=='' || f.act_fecha_fin.value=='' || f.responsable_eje.value=='' || 
            f.pre_proveedor.value=='' || f.pre_cantidad.value=='' || f.pre_p_u.value=='' || f.pre_total.value==''){
        alert('Todos los campos marcados con * son obligatorios');
        return false;
    }
    
    var f_crea = SelectorFecha.getTimestamp(SelectorFecha.sumar( f.fecha_crea.value, 'dia', 8));
    var hoy = SelectorFecha.getTimestamp(_('hoy').value);
    var ini = SelectorFecha.getTimestamp(f.act_fecha_ini.value);
    var fin = SelectorFecha.getTimestamp(f.act_fecha_fin.value);
    if(ini < hoy){
        alert('La fecha de inicio de la actividad debe ser mayor o igual a la fecha actual.');
        return false;
    }
    if(fin < ini){
        alert('La fecha de término de la actividad debe ser mayor o igual a la fecha de inicio.');
        return false;
    }
    if(ini < f_crea){
        if(!confirm('La fecha de inicio de la actividad se encuentra fuera de tiempo. Desea continuar registrando la actividad?')){
            return false;
        }
    }
    
    /*if(f.mt0.checked){
        if(f.mecanica.value==''){
            alert('Ingrese un texto de la mecánica');
            return false;
        }
    }
    
    var l = _('axFrmAdj').childNodes.length>0 ? parseInt(_('axFrmAdj').childNodes.item(_('axFrmAdj').childNodes.length-1).id.replace('rFrAd', ''))+1 : 0;;
    if(f.mt1.checked){
        if(!f.ap0.checked && !f.ap1.checked){
            alert('Debe seleccionar si la mecánica se aplica para promociones o convenios');
            return false;
        }
        if(parseInt(l)==0){
            alert('Debe adjuntar por lo menos el archivo del formulario de promociones o convenios.');
            return false;
        }
        for(var i=0; i<=l; i++){
            if(_('archivo'+i)!=null){
                if(_('descripcion_arch'+i).value==''){
                    alert('Ingrese la/las descripciones para el/los archivos adjuntos.');
                    return false;
                }
            }
        }
        if(f.ope_fecha_ini.value=='' || f.ope_fecha_fin.value==''){
            alert('Ingrese las fechas de inicio y termino para el tipo de mecánica de operaciones');
            return false;
        }
        ini = SelectorFecha.getTimestamp(f.ope_fecha_ini.value);
        fin = SelectorFecha.getTimestamp(f.ope_fecha_fin.value);
        if(ini < hoy){
            alert('La fecha de inicio de la mácanica de operaciones debe ser mayor a la fecha actual.');
            return false;
        }
        if(fin < ini){
            alert('La fecha de finalización de la mácanica de operaciones debe ser mayor o igual a la fecha de inicio.');
            return false;
        }
    }*/
    
    if(f.p0.checked){
        if(parseFloat(f.ax_monto_act.value) < parseFloat(f.pre_total.value)){
            alert('El total del presupuesto para la actividad supera lo disponible.');
            return false;
        }
    }
    
    var axf_entr = SelectorFecha.getTimestamp(SelectorFecha.sumar( f.act_fecha_ini.value, 'dia', 11));
    var t = _('ax_pre_crono_adqui').childNodes.length>0 ? parseInt(_('ax_pre_crono_adqui').childNodes.item(_('ax_pre_crono_adqui').childNodes.length-1).id.replace('axCrAd', ''))+1 : 0;
    for(var i=0; i<t; i++){
        if(_('crono_fecha'+i)!=null){
            var f_entr = SelectorFecha.getTimestamp(_('crono_fecha'+i).value);
            if(f_entr < ini){
                alert('La fecha de entrega '+_('crono_fecha'+i).value+' debe ser mayor a la fecha de inicio de la actividad '+f.act_fecha_ini.value);
                return false;
            }
            if(f_entr < axf_entr){
                if(!confirm('La fecha de entrega '+_('crono_fecha'+i).value+' se encuentra fuera de tiempo. Desea continuar registrando la actividad?')){
                    return false;
                }
            }
            if(_('crono_descripcion'+i).value==''){
                alert('Ingrese la/las descripciones para el/los cronograma(s) de entregas.');
                return false;
            }
        }
    }
    
    _('limCrono').value = t;
    //_('limAdjAct').value = l;
    return Ajax.enviarForm(f);
}
function mer_actividadEliminar(k)
{
    if(confirm('Está seguro(a) de querer eliminar la actividad, ya no podrá recuperar la información.')){
        Ajax.solicitud('frmActividadEliminar', 'id='+k+'&id_estrategia='+_('id_estrategia').value, false, 'axActividades');
    }
}
function ProcesaTransferirFormularioAdjunto(m, a)
{
    if(m==0){
        var j = _('axFrmAdj').childNodes.length>0 ? parseInt(_('axFrmAdj').childNodes.item(_('axFrmAdj').childNodes.length-1).id.replace('rFrAd', ''))+1 : 0;
        if( enFormularioAdjuntos(j, a) ){
            m = 'El archivo con ese nombre ya se encuentra adjunto';
        }else{    
            var d = document.createElement('div');
            d.id = 'rFrAd'+j;
            d.className = 'fila jm_filaImp';
            d.onmouseover = "this.className='fila jm_filaSobre'";
            d.onmouseout = "this.className='fila jm_filaImp'";
            _('axFrmAdj').appendChild(d);
            d.innerHTML += "<div class=\"columna\" style=\"width:150px;\"><input type=\"text\" class=\"texto\" id=\"archivo"+j+"\" name=\"archivo"+j+"\" style=\"width:140px;border:0px\" value=\""+a+"\" readonly /></div>"+
                            "<div class=\"columna\" style=\"width:300px\"><input type=\"text\" class=\"texto\" id=\"descripcion_arch"+j+"\" name=\"descripcion_arch"+j+"\" style=\"width:290px\" /></div>"+
                                    "<div class=\"columna\" style=\"width:25px\"><div class=\"borrar\" title=\"Eliminar\" onclick=\"_R('rFrAd"+j+"');\">&nbsp;</div></div>";
            _R('bloq_vta4');
            _R('vta4');
            return false;
        }
        mer_verBtnAutorizacion(0);
    }
    _('cargando').style.display = 'none';
    _('frmArchivo').style.display = 'block';
    _('archivo').value='';
    alert(m);
    return false;
}
function enFormularioAdjuntos(l, cod)
{
    for(var j=0; j<=l; j++){
        if( _('archivo'+j) != null ){
            if( _('archivo'+j).value==cod ){
                return true;
            }
        }
    }
    return false;
}
function mer_calcTotal()
{
    var c = _('pre_cantidad').value || 0;
    var pu = _('pre_p_u').value || 0;
    var t = parseFloat(c) * parseFloat(pu);
    _('pre_total').value = _RD(t);
}
function mer_getResponsable(op)
{
    var t = _('responsable_'+op).value;
    if(t!=''){
        setComboFiltro('axRes'+op, 'pm_d_5');
        _('cmbC').style.marginTop='0px';
        if(_esIE){
            if(parseInt(_('pm_d_5').scrollTop) > 0){
                _('cmbC').style.marginTop='-47px';
            }
            if(_('pm_gredicion').scrollLeft!=''){
                var sc = parseInt(_('pm_gredicion').scrollLeft) + 30;
                _('cmbC').style.left = ( _getIzq(_('axRes'+op)) - sc ) + 'px';
            }
        }
        Ajax.solicitud('getEmpleados', "txt="+t+"&obj=cmbC&onC=mer_setResponsable('"+op+"')", false, 'cmbC');
    }else{
        _R('cmbC');
        _RE('mousedown', comboFiltroRem);
    }
}
function mer_setResponsable(op)
{
    _('responsable_'+op).value = _('emp').options[_('emp').selectedIndex].text;
    var v = _('emp').value.split('|');
    _('usuario_'+op).value = v[2];
    _R('cmbC');
    _RE('mousedown', comboFiltroRem);
}
function mer_setFecha()
{
    var f = _('axFecha').value;
    var i = _('ax_pre_crono_adqui').childNodes.length>0 ? parseInt(_('ax_pre_crono_adqui').childNodes.item(_('ax_pre_crono_adqui').childNodes.length-1).id.replace('axCrAd', ''))+1 : 0;
    if(!enCronograma(i, f)){
        var d = document.createElement('div');
        d.id = 'axCrAd'+i;
        d.className = 'fila jm_filaImp';
        d.onmouseover = "this.className='fila jm_filaSobre'";
        d.onmouseout = "this.className='fila jm_filaImp'";
        d.style.height = 'height:23px';
        _('ax_pre_crono_adqui').appendChild(d);
        d.innerHTML += "<div class=\"columna\" style=\"width:80px\"><input type=\"hidden\" id=\"crono_fecha"+i+"\" name=\"crono_fecha"+i+"\" value=\""+f+"\" />"+f+"</div>"+
            "<div class=\"columna\" style=\"width:380px\"><input type=\"text\" class=\"texto\" id=\"crono_descripcion"+i+"\" name=\"crono_descripcion"+i+"\" style=\"width:370px\" /></div>"+
            "<div class=\"columna\" style=\"width:25px\"><div class=\"borrar\" title=\"Eliminar\" onclick=\"_R('axCrAd"+i+"');\">&nbsp;</div></div>";
    }
}
function enCronograma(l, cod)
{
    for(var j=0; j<=l; j++){
        if( _('crono_fecha'+j) != null ){
            if( _('crono_fecha'+j).value==cod ){
                return true;
            }
        }
    }
    return false;
}

/* Administracion de tiempos para confirmar auspicios */
function mer_conf_auspicios()
{
    encerar(true);
    _('filtro').innerHTML='Plan de mercadeo: ' +
        setCaja('txt', 'mer_conf_auspiciosBuscar()', 'buscar') +
        setBoton('button', 'Filtrar', 'mer_conf_auspiciosBuscar()');
    setAnchoPanel(3, 400);
    mer_conf_auspiciosBuscar();
}
function mer_conf_auspiciosBuscar()
{
    var w = '';
    var t = _('txt').value.toLowerCase();
    if(t!=''){
        w = "where plan_mercadeo like '%"+t+"%'";
    }
    _WR = w;
    new Tabla('d_1', 'jmTbl', '', 'tbl_plan_mercadeo', 'No.,PLAN DE MERCADEO,F. CREACION,USUARIO', 'id_plan_mercadeo,sec_tipo_plan,plan_mercadeo', '0,40,350', (_altTbl-35), 'mer_conf_auspiciosEditar(^)', _WR);
}
function mer_conf_auspiciosEditar(k)
{
    Ajax.solicitud('frmPlanMercdeoConfAuspicio', 'id='+k, false, 'd_3');
}


function calendario(es)
{
    encerar(true);
    fijAn = false;
    _('filtro').style.display='none';
    red();

    _('iconos').innerHTML+=setCaja('txt', 'if(_gKC(event)==13){mer_getPlanesMercadeo(1);mer_getActividades(-1, 0, '+es+');}', 'columna buscar', 'd') + 
            setOculto('estadoPM', es);
    
    _('txt').style.margin='10px';
    _('txt').style.width='180px';
    
    var f = new Date();
    _('iconos').innerHTML+= "<input type=\"text\" id=\"axFch\" class=\"columna axCalendario\" value=\""+(f.getDate() + "/" + (f.getMonth()+1) + "/" + f.getFullYear())+"\" onclick=\"SelectorFecha.crear('axFch', 'SQL', 'mer_limpiar_d5(0, "+es+")')\" >";
    btn.icono(-869, 0, 42, 42, 'Atras', "mer_limpiar_d5(-7, "+es+")", 'iconos', 'jmFPBT1');
    btn.icono(-904, 0, 42, 42, 'Siguiente', "mer_limpiar_d5(7, "+es+")", 'iconos', 'jmFPBT1');
    
    _('d_5').style.display=_('d_6').style.display='block';
    setAnchoPanel(1, 200);
    setAnchoPanel(3, parseInt(_anchBody-640) );
    setAnchoPanel(5, 350);
    _('txt').focus();
    mer_getPlanesMercadeo(1);
    mer_getActividades(-1, 0, es);
}


/* autorizacion operaciones */
function mer_operaciones()
{
    calendario(3);
}


/* autorizacion marketing */
function mer_marketing()
{
    calendario(4);
}


/* autorizacion ventas */
function mer_ventas()
{
    calendario(5);
}


/* autorizacion comercial */
function mer_comercial()
{
    calendario(6);
}


/* contabilizacion de gastos */
function mer_gastos()
{
    encerar(true);
}


/* seguimiento de ejecucion de activicades */
function mer_ejecucion()
{
    encerar(true);
    Ajax.solicitud('frmFiltro', 'op=4', false, 'filtro');
    btn.icono(-722, -42, 42, 42, 'Marcar como realizadas', "mer_actividadesMarcRealizadas();", 'iconos', 'jmFPBT1');
    setAnchoPanel(1, 750);
    setAnchoPanel(3, 5);
}
function mer_getActivicadesEjecucion()
{
    Ajax.solicitud('tblActividadesEjecucion', 'idPM='+_('idPMSegEje').value+'&altEdi='+_altEdi, false, 'd_1');
}
function mer_actividadesMarcRealizadas()
{
    var b=false;
    var i=0;
    while(_('idAct'+i)!=null){
        if(_('chIdAct'+i).checked){
            b=true;
        }
        i++;
    }
    if(b){
        _('idPlanMercadeo').value = _('idPMSegEje').value;
        return Ajax.enviarForm(_('frmConfActs'));
        //_('frmConfActs').submit();
    }else{
        alert('Debe marcar por lo menos una actividad para poder confirmar su ejecución');
    }
}

/* seguimiento de presupuesto */
function mer_seguimiento()
{
    encerar(true);
    Ajax.solicitud('frmFiltro', 'op=2', false, 'filtro');
    //btn.icono(-796, 0, 42, 42, 'Impresi&oacute;n de reportes', "mer_seguimientoReportes();", 'iconos', 'jmFPBT1');
    setAnchoPanel(3, 800);
}
function mer_seguimientoBuscar()
{
    var w = "where aprobado_comercial=1 and abierto=1 and fecha_aprobacion between '"+_('fi').value+" 00:00:00.000' and '"+_('ff').value+" 23:59:59.999'";
    var ty = _('tpy').value;
    if(ty!='0'){
        w += " and id_tipo_plan='"+ty+"'";
    }
    var u = _('usr').value;
    if(u!='0'){
        w += " and usuario_creacion='"+u+"'";
    }
    var t = _('txt').value;
    if(t!=''){
        w = "where fecha_aprobacion is not null and sec_tipo_plan="+t;
    }
    _WR = w + ' order by sec_tipo_plan'; 
    new Tabla('d_1', 'jmTbl', '', 'tbl_plan_mercadeo', 'No.,PLAN DE MERCADEO,USUARIO', 'id_plan_mercadeo,sec_tipo_plan,plan_mercadeo,usuario_creacion', '0,60,180,110', _altTbl, 'mer_seguimientoEditar(^)', _WR);
    _('d_3').innerHTML = '';
}
function mer_seguimientoEditar(k)
{
    Ajax.solicitud('tblSeguimiento', 'id='+k, false, 'd_3');
}
function mer_seguimientoVer(k, tp)
{
    Ventana.crear('vta1', 'COMPRAS DIRECTAS', "ancho=680,alto="+(_altBody-50)+",modal=true,cerrar=true");
    Ajax.solicitud('tblSeguimientoComprasVer', 'idAct='+k+'&totPre='+tp, false, 'vta1_html');
}
function mer_seguimientoAnadir(k, tp, ti)
{
    var an = _anchBody<1000 ? _anchBody-30 : 1000; 
    Ventana.crear('vta1', 'COMPRAS DIRECTAS', "ancho="+an+",alto="+(_altBody-50)+",modal=true,cerrar=true");
    Ajax.solicitud('tblSeguimientoCompras', 'idAct='+k+'&totPre='+tp+'&totInc='+ti+'&alto='+_altBody, false, 'vta1_html');
}
function mer_seguimientoCompraGuardar(f, a)
{
    if(f.fac_fecha.value=='' || f.fac_ruc.value=='' || f.fac_proveedor.value=='' || f.fac_num_documento.value=='' || 
            (f.fac_base_12.value=='' && f.fac_base_0.value=='') || f.fac_total.value==''){
        alert('Todos los campos de la fila son obligatorios.');
        return false;
    }
    if(parseFloat(f.fac_base_12.value)==0 && parseFloat(f.fac_base_0.value)==0){
        alert('Debe ingresar un valor en Base 12% o Base 0%');
        return false;
    }
    if(!_esFecha('fac_fecha')){
        return false;
    }
    if(f.fac_num_documento.value.search(/\d\d\d-\d\d\d-[\d]+/) != 0) {
        _MS('Formato de número de documento no válido.', f.fac_num_documento);
        return false;
    }
    
    var t = _('tblGastos').childNodes.length>0 ? parseInt(_('tblGastos').childNodes.item(_('tblGastos').childNodes.length-1).id.replace('fGasto', ''))+1 : 0;
    var tot = 0;
    for(var i=0; i<t; i++){
        if(_('fac_total'+i)!=null){
            tot += parseFloat(_('fac_total'+i).value);
        }
    }
    tot += parseFloat(_('fac_total').value);
    
    var totLim = parseFloat(_('totInc').value);
    if(tot > totLim){
        alert('El total de gastos supera el límite de presupuesto permitido');
        return false;
    }
    _('frmFact').action = a; 
    return Ajax.enviarForm(f);
}
function mer_getProveedoresCompras(pr)
{
    var t = _('fac_'+pr).value;
    if(t!=''){
        setComboFiltro('axTxt'+pr, 'vta1_html');
        _('cmbC').style.marginTop='0px';
        if(_esIE){
            if(_('cmbC').style.top!=''){
                _('cmbC').style.top = (parseInt(_('cmbC').style.top) ) + 'px';
            }
        }
        Ajax.solicitud('getProveedores', "txt="+t+"&obj=cmbC&onC=mer_setProveedorCompras('"+pr+"')&por="+pr, false, 'cmbC');
    }else{
        _R('cmbC');
        _RE('mousedown', comboFiltroRem);
    }
}
function mer_setProveedorCompras(pr)
{
    _('fac_ruc').value = _('prov').value;
    var nombre = _('prov').options[_('prov').selectedIndex].text;
    if(pr=='ruc'){
        var vec = nombre.split('-');
        _('fac_proveedor').value = vec[1]._trim();
    }else{
        _('fac_proveedor').value = nombre;
    }
    _R('cmbC');
    _('fac_detalle').focus();
}
function mer_seguimientoSumar(e, cIva)
{
    var c = _gKC(e);  
    if(c==13 || c==27) {
        _inh(e);
        return false;
    }    
    var b12 = parseFloat(_('fac_base_12').value) || 0;
    var b0 = parseFloat(_('fac_base_0').value) || 0;
    var iv = parseFloat(_('fac_iva').value) || 0;
    var t = 0;
    if(b12>0){
        if(cIva){
           iv = _RD(b12 * 12 / 100);
           _('fac_iva').value = iv;
        }
        //_('fac_base_0').value = 0;
        t = b12 + iv;
    /*}else{
        if(b0 > 0){
            _('fac_base_12').value = _('fac_iva').value = 0;
            t = b0;
        }*/
    }
    _('fac_total').value = _RD(t + b0);
    mer_seguimientoValGastoTotal(_('fac_total').value);
}
function mer_seguimientoCompraActualizar(e, i, cIva)
{
    var c = _gKC(e);  
    /*if(c!=13 || c!=27) {
        _inh(e);
        return false;
    }  */  
    var ax = cIva || 'n';
    if(ax!='n'){
        var b12 = parseFloat(_('fac_base_12_'+i).value) || 0; 
        var b0 = parseFloat(_('fac_base_0_'+i).value) || 0;
        var iv = parseFloat(_('fac_iva'+i).value) || 0;
        var t = 0;
        if(b12>0){
            if(cIva=='1'){
               iv = _RD(b12 * 12 / 100);
               _('fac_iva'+i).value = iv;
            }
            //_('fac_base_0_'+i).value = 0;
            t = b12 + iv;
        /*}else{
            if(b0 > 0){
                _('fac_base_12_'+i).value = _('fac_iva'+i).value = 0;
                t = b0;
            }*/
        }
        _('fac_total'+i).value = _RD(t + b0);
    }
    if(!mer_seguimientoValGastoTotal(_('fac_total').value)){
        return false;
    }
    
    if(c==13){
        if(_('fac_fecha'+i).value=='' || _('fac_ruc'+i).value=='' || _('fac_proveedor'+i).value=='' || _('fac_num_documento'+i).value=='' || 
            (_('fac_base_12_'+i).value=='' && _('fac_base_0_'+i).value=='') || _('fac_iva'+i).value=='' || _('fac_total'+i).value==''){
            alert('Debe ingresar un valor para poder guardar.');
            return false;
        }
        
        var t = _('tblGastos').childNodes.length>0 ? parseInt(_('tblGastos').childNodes.item(_('tblGastos').childNodes.length-1).id.replace('fGasto', ''))+1 : 0;
        var tot = 0;
        for(var j=0; j<t; j++){
            if(_('fac_total'+j)!=null){
                tot += parseFloat(_('fac_total'+j).value);
            }
        }
        var totLim = parseFloat(_('totInc').value);
        if(tot > totLim){
            alert('El total de gastos supera el límite de presupuesto permitido');
            return false;
        }
       
        Ajax.solicitud( 'tblSeguimientoComprasGuardar', 'idAct='+_('idAct').value+'&idActCom='+_('idActCom'+i).value+'&fac_fecha='+_('fac_fecha'+i).value+
                '&fac_ruc='+_('fac_ruc'+i).value+'&fac_proveedor='+_('fac_proveedor'+i).value+'&fac_detalle='+_('fac_detalle'+i).value+
                '&fac_num_documento='+_('fac_num_documento'+i).value+'&fac_cantidad='+_('fac_cantidad'+i).value+'&fac_base_12='+_('fac_base_12_'+i).value+
                '&fac_base_0='+_('fac_base_0_'+i).value+'&fac_iva='+_('fac_iva'+i).value+'&fac_total='+_('fac_total'+i).value+'&alto='+_altBody );
    }
}
function mer_seguimientoValGastoTotal(v)
{
    v = v=='' ? 0 : v;
    var t = _('tblGastos').childNodes.length>0 ? parseInt(_('tblGastos').childNodes.item(_('tblGastos').childNodes.length-1).id.replace('fGasto', ''))+1 : 0;
    var tot = 0;
    for(var i=0; i<t; i++){
        if(_('fac_total'+i)!=null){
            tot += parseFloat(_('fac_total'+i).value);
        }
    }
    var tg = tot + parseFloat(v);
    if(parseFloat(_('totInc').value) >= parseFloat(tg) ){
        _('totGasto').value = _RD(tg);
    }else{
        _('totGasto').value = _RD(tot);
        _('fac_total').value='';
        alert('El total de gastos supera el límite de presupuesto permitido');
        return false;
    }
    _('difer').value = _RD( parseFloat(_('totPre').value) - parseFloat(_('totGasto').value) );
    return true;
}
function mer_seguimientoSetGastoTotal(t)
{
    _('totGasto').value = t; 
    _('difer').value = _RD( parseFloat(_('totPre').value)- t );
    mer_seguimientoEditar(_('id').value);
    _('fac_fecha').value = _('fac_ruc').value = _('fac_proveedor').value = _('fac_detalle').value = _('fac_num_documento').value = '';
    _('fac_cantidad').value = _('fac_base_12').value = _('fac_base_0').value = _('fac_iva').value = _('fac_total').value = '0';
}
function mer_seguimientoCerrarPlan(k, n)
{
    if(confirm('Se realizará el cierre del Plan de Mercadeo Nro. '+n+'; no podrá ingresar más facturas')){
        Ajax.solicitud('frmPlanMercadeoCerrar', 'id='+k+'&d=s', false, 'body');
    }
}
/*function mer_seguimientoReportes()
{
    var h = '<form onsubmit="return reporte(this)" autocomplete="off">'
    + setOculto('nro', _('txt').value)
    + setOculto('tpy1', _('tpy').value)
    + setOculto('usr1', _('usr').value)
    + setOculto('fi1', _('fi').value)
    + setOculto('ff1', _('ff').value)
    + setRadio('z', '{tbl:[{0:"PresupuestoConsolidado",1:"Presupuesto consolidado"},{0:"DistribucionGasto",1:"Distribuci&oacute;n de gasto"},{0:"PlanMercadeoDetallado",1:"Plan de mercadeo detallado"}]}', 'mer_seguimientoReporteOpcion()') 
    + '<div id="grSegRep" style="display:none"><hr />No. de Plan de Mercadeo: <input type=\"text\" id=\"numPlan\" name=\"numPlan\" size=\"8\" onkeypress=\"_numero(event);\" /></div>'
    + setPieReporte()
    + '</form>';
    Ventana.crear('vta1', 'REPORTES DE SEGUIMIENTO', "ancho=350,alto=260,modal=true,cerrar=true", h);
}
function mer_seguimientoReporteOpcion()
{
    _('grSegRep').style.display = _('z1').checked ? 'block' : 'none';
}*/

/* liquidacion de planes */
function mer_liquidacion()
{
    encerar(true);
    setAnchoPanel(3, 820);
    mer_liquidacionBuscar();
}
function mer_liquidacionBuscar()
{
    var w = "where estado=6 order by sec_tipo_plan";
    _WR = w; 
    new Tabla('d_1', 'jmTbl', '', 'tbl_plan_mercadeo', 'No.,PLAN DE MERCADEO,USUARIO', 'id_plan_mercadeo,sec_tipo_plan,plan_mercadeo,usuario_creacion', '0,60,180,110', _altTbl, 'mer_liquidacionEditar(^)', _WR);
    _('d_3').innerHTML = '';
}
function mer_liquidacionEditar(k)
{
    Ajax.solicitud('tblLiquidacion', 'id='+k+'&WHERE='+_WR+'&p='+(_objT!=null?_objT.pg:0), false, 'd_3');
}
function mer_liquidacionAnadir(k, idA)
{
    Ventana.crear('vta1', 'COMPRAS DIRECTAS', "ancho=900,alto="+(_altBody-50)+",modal=true,cerrar=true");
    Ajax.solicitud('tblLiquidacionCompras', 'id='+k+'&alto='+_altBody+'&idAct='+idA, false, 'vta1_html');
}
function mer_liquidacionGetFacturas(k, tp, ti)
{
    Ajax.solicitud('tblLiquidacionComprasFacturas', 'idPM='+_('id').value+'&idAct='+k+'&totPre='+tp+'&totInc='+ti+'&alto='+_altBody, false, 'tblFacturas');
}
function mer_liquidacionCerrarPlan(k, d)
{
    if(d>0){
        if(confirm('Si está seguro(a) de cerrar el plan de mercadeo pulse Aceptar. O desea añadir más facturas de gasto pulse Cancelar.')){
            Ajax.solicitud('frmPlanMercadeoCerrar', 'id='+k+'&d=l', false, 'd_3');
        }else{
            mer_liquidacionAnadir(k, -1);
        }
    }
}



/* Montos por conceptos (actividades) */
function mer_conceptos()
{
    encerar(true);
    _('filtro').innerHTML='concepto: ' +
        setCaja('txt', 'mer_conceptoBuscar()', 'buscar') +
        setBoton('button', 'Filtrar', 'mer_conceptoBuscar()');
    setAnchoPanel(3, 370);
    mer_conceptoBuscar();
}
function mer_conceptoBuscar()
{
    var w = '';
    var t = _('txt').value.toLowerCase();
    if(t!=''){
        w = "where concepto like '"+t+"%'";
    }
    _WR = w + ' order by concepto'; 
    new Tabla('d_1', 'jmTbl', '', 'tbl_actividad_tipo', 'CONCEPTO,MONTO', 'id_actividad_tipo,concepto,monto', '0,200,50', _altTbl, 'mer_conceptoEditar(^)', _WR);
}
function mer_conceptoEditar(k)
{
    Ajax.solicitud('frmConcepto', 'id='+k, false, 'd_3');
}
function mer_conceptoGuardar(f)
{
    if(f.monto.value==''){
        alert('Los campos marcados con  *  son obligatorios');
        return false;
    }
    return Ajax.enviarForm(f);
}

/* configuraciones */
function mer_configuraciones()
{
    Ventana.crear('vta1', 'CONFIGURACIONES', "ancho=550,alto=330,modal=true,cerrar=true");
    Ajax.solicitud('frmConfiguracion', '', false, 'vta1_html');
}
function mer_configuracionGuardar(f)
{
    if(f.mail_remitente.value=='' || f.mail_operaciones.value=='' || f.mail_marketing.value=='' 
            || f.mail_ventas.value=='' || f.mail_comercial.value=='' || f.admin_tiempos_conf.value==''){
        alert('Todos los parámetros son obligatorios');
        return false;
    }
    if(!_esMail('mail_remitente')){
        return false;
    }
    if(!_esMail('mail_operaciones')){
        return false;
    }
    if(!_esMail('mail_marketing')){
        return false;
    }
    if(!_esMail('mail_ventas')){
        return false;
    }
    if(!_esMail('mail_comercial')){
        return false;
    }
    return Ajax.enviarForm(f);
}


/* REPORTES */
function mer_reportes()
{
    Ventana.crear('vta1', 'REPORTES', "ancho=470,alto=460,modal=true,cerrar=true");
    Ajax.solicitud('frmFiltro', 'op=3', false, 'vta1_html');
}
function mer_reporteOpcion()
{
    _('grFech').style.display = _('z3').checked || _('z4').checked ? 'block' : 'none';
    _('etFech').innerHTML = _('z4').checked ? 'Per&iacute;odo desde: ' : 'Fecha de ingreso de facturas desde: ';
    _('grIdOfi').style.display = _('z4').checked ? 'block' : 'none';
    _('axX2').style.display = _('z1').checked ? 'block' : 'none';
    if(_('z0').checked){
        var cl = _('cmbTipoTodos').cloneNode(true);
        _('axTpy1').innerHTML = cl.innerHTML.replace(/<<ID>>/g, 'tpy1');
    }else{
        var cl = _('cmbTipoNoTodos').cloneNode(true);
        _('axTpy1').innerHTML = cl.innerHTML.replace(/<<ID>>/g, 'tpy1');
    }
    if(_('z1').checked){
        //_('axX1').style.display = 'none';
        //_('x0').checked = true;
        _('nro').focus();
    //}else{
        //_('axX1').style.display = 'block';
    }
    _('x0').checked=true;
    mer_reporteGetPlanes();
}
function mer_reporteGetTiposPlanes(k)
{
    Ajax.solicitud('getPlanesMercadeoTiposCombo', 'usr='+k, false, 'body');
}
function mer_reporteGetPlanes()
{
    var dC = _('z4').checked ? '1' : '0';
    Ajax.solicitud('getPlanesMercadeoCombo', 'idTP='+_('tpy1').value+'&usr='+_('usr1').value+'&deCump='+dC, true, 'body');
}
function mer_reporteGetOficinas()
{
    if(_('idPM1').value!=0){
        _('grIdOfi').style.display = 'block';
        Ajax.solicitud('getPlanesMercadeoOficinasCombo', 'idPM='+_('idPM1').value, true, 'axIdOfi');
    }else{
        _('grIdOfi').style.display = 'none';
        _('axIdOfi').innerHTML='<input type="hidden" id="idOfic1" name="idOfic1" value="0"  />';
    }
}
function reporte(f)
{
    if(_('z1')!=null){
        if(_('z1').checked){
            if(_('nro').value=='' && _('idPM1').value==0){
                alert('Debe ingresar un número de plan de mercadeo o seleccione un nombre de plan de mercadeo');
                return false;
            }
        }
    }
    var p = 'WHERE='+_WR+'&';
    var i=0;
    var pg = '';
    var tp = '';
    while(f.elements[i]!=null){
        if(f.elements[i].type!='reset' && f.elements[i].type!='submit' && f.elements[i].type!='button' && f.elements[i].type!='image' && f.elements[i].type!=undefined && f.elements[i].type!='fieldset'){
            if(f.elements[i].type=='checkbox'){
                p += f.elements[i].name + '=' + f.elements[i].checked + '&';
            }else if(f.elements[i].type=='radio'){
                      if(f.elements[i].checked && f.elements[i].name=='z'){
                          pg = f.elements[i].value._trim();
                      }if(f.elements[i].checked && f.elements[i].name=='x'){
                          tp = f.elements[i].value._trim();
                      }
                      if(f.elements[i].checked && f.elements[i].name!='x' && f.elements[i].name!='z'){
                          p += f.elements[i].name + '=' + f.elements[i].value._trim() + '&';
                      }
                  }else{
                      p += f.elements[i].name + '=' + f.elements[i].value._trim() + '&';
                  }
        }
        i++;
    }
    p = p.substr(0, p.length-1);
    abrir(tp+pg+'?'+p);
    return false;
}