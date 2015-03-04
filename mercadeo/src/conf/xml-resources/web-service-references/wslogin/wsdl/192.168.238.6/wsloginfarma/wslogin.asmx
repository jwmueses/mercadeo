

<html>

    <head><link rel="alternate" type="text/xml" href="/wsloginfarma/wslogin.asmx?disco" />

    <style type="text/css">
    
		BODY { color: #000000; background-color: white; font-family: Verdana; margin-left: 0px; margin-top: 0px; }
		#content { margin-left: 30px; font-size: .70em; padding-bottom: 2em; }
		A:link { color: #336699; font-weight: bold; text-decoration: underline; }
		A:visited { color: #6699cc; font-weight: bold; text-decoration: underline; }
		A:active { color: #336699; font-weight: bold; text-decoration: underline; }
		A:hover { color: cc3300; font-weight: bold; text-decoration: underline; }
		P { color: #000000; margin-top: 0px; margin-bottom: 12px; font-family: Verdana; }
		pre { background-color: #e5e5cc; padding: 5px; font-family: Courier New; font-size: x-small; margin-top: -5px; border: 1px #f0f0e0 solid; }
		td { color: #000000; font-family: Verdana; font-size: .7em; }
		h2 { font-size: 1.5em; font-weight: bold; margin-top: 25px; margin-bottom: 10px; border-top: 1px solid #003366; margin-left: -15px; color: #003366; }
		h3 { font-size: 1.1em; color: #000000; margin-left: -15px; margin-top: 10px; margin-bottom: 10px; }
		ul { margin-top: 10px; margin-left: 20px; }
		ol { margin-top: 10px; margin-left: 20px; }
		li { margin-top: 10px; color: #000000; }
		font.value { color: darkblue; font: bold; }
		font.key { color: darkgreen; font: bold; }
		font.error { color: darkred; font: bold; }
		.heading1 { color: #ffffff; font-family: Tahoma; font-size: 26px; font-weight: normal; background-color: #003366; margin-top: 0px; margin-bottom: 0px; margin-left: -30px; padding-top: 10px; padding-bottom: 3px; padding-left: 15px; width: 105%; }
		.button { background-color: #dcdcdc; font-family: Verdana; font-size: 1em; border-top: #cccccc 1px solid; border-bottom: #666666 1px solid; border-left: #cccccc 1px solid; border-right: #666666 1px solid; }
		.frmheader { color: #000000; background: #dcdcdc; font-family: Verdana; font-size: .7em; font-weight: normal; border-bottom: 1px solid #dcdcdc; padding-top: 2px; padding-bottom: 2px; }
		.frmtext { font-family: Verdana; font-size: .7em; margin-top: 8px; margin-bottom: 0px; margin-left: 32px; }
		.frmInput { font-family: Verdana; font-size: 1em; }
		.intro { margin-left: -15px; }
           
    </style>

    <title>
	wslogin Servicio Web
</title></head>

  <body>

    <div id="content">

      <p class="heading1">wslogin</p><br>

      

      <span>

          <p class="intro">Las operaciones siguientes son compatibles. Para una definición formal, revise la <a href="wslogin.asmx?WSDL">descripción de servicios</a>. </p>
          
          
              <ul>
            
              <li>
                <a href="wslogin.asmx?op=DatosUsuario">DatosUsuario</a>
                
                
              </li>
              <p>
            
              <li>
                <a href="wslogin.asmx?op=HelloWorld">HelloWorld</a>
                
                
              </li>
              <p>
            
              <li>
                <a href="wslogin.asmx?op=IsAuthenticated">IsAuthenticated</a>
                
                
              </li>
              <p>
            
              <li>
                <a href="wslogin.asmx?op=getPerfilesUsuario">getPerfilesUsuario</a>
                
                
              </li>
              <p>
            
              </ul>
            
      </span>

      
      

    <span>
        
    </span>
    
      <span>
          <hr>
          <h3>Este servicio Web utiliza http://tempuri.org/ como espacio de nombres predeterminado.</h3>
          <h3>Recomendación: cambiar el espacio de nombres predeterminado antes de hacer público el servicio Web XML.</h3>
          <p class="intro">Cada servicio Web XML necesita un espacio de nombres único para que las aplicaciones de cliente puedan distinguir este servicio de otros servicios del Web. http://tempuri.org/ está disponible para servicios Web XML que están en desarrollo, pero los servicios Web XML publicados deberían utilizar un espacio de nombres más permanente.</p>
          <p class="intro">Debe identificar su servicio Web XML con un espacio de nombres que controle. Por ejemplo, puede utilizar el nombre de dominio de Internet de su compañía como parte del espacio de nombres. aunque muchos espacios de nombres de servicios Web XML parecen direcciones URL, éstos no pueden señalar a recursos reales en el Web. (Los espacios de nombres de los servicios Web XML son los URI.)</p>
          <p class="intro">En los servicios Web XML que se crean con ASP.NET, se puede cambiar el espacio de nombres predeterminado utilizando la propiedad Namespace del atributo WebService. Este atributo es un atributo aplicado a la clase que contiene los métodos del servicio Web XML. A continuación se muestra un ejemplo de código que establece el espacio de nombres en "http://microsoft.com/webservices/":</p>
          <p class="intro">C#</p>
          <pre>[WebService(Namespace="http://microsoft.com/webservices/")]
public class MyWebService {
    // implementación
}</pre>
          <p class="intro">Visual Basic</p>
          <pre>&lt;WebService(Namespace:="http://microsoft.com/webservices/")&gt; Public Class MyWebService
    ' implementación
End Class</pre>

          <p class="intro">C++</p>
          <pre>[WebService(Namespace="http://microsoft.com/webservices/")]
public ref class MyWebService {
    // implementación
};</pre>
          <p class="intro">Para obtener más detalles acerca de espacios de nombres XML, vea la sugerencia W3C en <a href="http://www.w3.org/TR/REC-xml-names/"">Espacio de nombres en XML</A>.</p>
          <p class="intro">Para obtener más detalles acerca de WSDL, vea <a href="http://www.w3.org/TR/wsdl">Especificación WSDL</a>.</p>
          <p class="intro">Para obtener más detalles sobre los URI, vea <a href="http://www.ietf.org/rfc/rfc2396.txt">RFC 2396</a>.</p>
      </span>

      

    
  </body>
</html>
