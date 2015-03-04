/**	
 * @version 1.0
 * @package jm.web.
 * @author Jorge Washington Mueses Cevallos.	
 * @copyright Copyright (C) 2008 por Jorge W. Mueses Cevallos. 
 * Todos los derechos reservados.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL.
*/

package com.far.lib;

import java.util.*;
import javax.servlet.http.*;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.*;
import java.io.*;

public class ArchivadorWeb {
    private String _directorio="";
    private String _archivoNombre="";
    private String _error="";
    
/**
 * Constructor de la clase. Setea el path del directorio de trabajo.
 * @param directorio. directorio raiz para trabajar conlos archivos.
 */    
    public ArchivadorWeb(String directorio)
    {
        this._directorio = directorio;
    }

/**
 * Ingresa una nueva ruta de directorio.
 * @param directorio. directorio raiz para trabajar conlos archivos. 
 */    
    public void setdirectorio(String directorio)
    {
        this._directorio = directorio;
    }

/**
 * Retorna la ruta del directorio.
 * @return Retorna el path del directorio de trabajo.
 */ 
    public String getdirectorio()
    {
        return this._directorio;
    }
    
/**
 * Retorna el nombre del archivo.
 * @return Retorna el nombre del archivo subido.
 */ 
    public String getNombre()
    {
        return this._archivoNombre;
    }    
        
/**
 * Sube un archivo del cliente al servidor Web. Si el archivo ya existe en el
 * servidor Web lo sobrescribe.
 * @param request. Variable que contiene el request de un formulario.
 * @param tamanioMax. Tama�o m�ximo del archivo en megas.
 * @return Retorna true o false si se subi� o no el archivo.
 */    
    public boolean subir(HttpServletRequest request, long tamanioMax)
    {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if(isMultipart){
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            try{
                List items = upload.parseRequest(request);
                Iterator iter = items.iterator();
                while(iter.hasNext()){
                    FileItem item = (FileItem) iter.next();
                    if(!item.isFormField()){
                        String tipo = item.getContentType();
                        long tamanio = item.getSize()/1024/1024; // para tamaño en megas
                        String archivoRemoto = item.getName();
                        archivoRemoto = archivoRemoto.replace("\\", "/");
                        String ruta[] = archivoRemoto.split("/");
                        String nombre = ruta[ruta.length-1];
                        this._archivoNombre = nombre.replace(" ", "_");
                        this._error = "Se ha excedido el tamaño máximo del archivo, o el tipo de archivo no es el permitido";
                        if(tamanio<=tamanioMax && tipo.compareTo("application/octet-stream")!=0){
                            File archivoSubido = new File(this._directorio, this._archivoNombre);
                            item.write(archivoSubido);
                        }
                    }
                }
                return true;
            }catch(Exception e){
                this._error = e.getMessage();
            }
        }
        return false;
    }
    
    
/** Retorna el mensaje de error provocado en el momento de la subida del archivo.
 * @return Retorna el mensaje de error.
 */ 
    public String getError()
    {
        return this._error;
    }

/**
 * Genera una cadena JSON con la lista de archivos.
 * @return Retorna una cadena JSON con la lista de archivos.
 */     
    public String getArchivosJSON()
    {
        File path = new File(this._directorio);
        String archivo[] = path.list();
        String json = "{tbl:[";
        int j=0;
        for(int i=0; i<archivo.length; i++){
            if(archivo[i].indexOf(".")>0){
                json += "{" + j + ":\"" + archivo[i] + "\"},";
                j++;
            }
        }
        json = json.substring(0, json.length()-1);
        json += "]}";
        return json;
    }
    
/**
 * Carga la lista de archivos en un vector de cadenas.
 * @return Retorna un vector de cadenas con la lista de archivos.
 */     
    public String[] getListaArchivos()
    {
        File path = new File(this._directorio);
        return path.list();
    }    
 
/**
 * Borra un archivo del servidor Web.
 * @param archivo. Nombre del archivo a borrar.
 * @return Retorna true o false si se borr� o no el archivo.
 */     
    public boolean borrar(String archivo)
    {
        File path = new File(this._directorio, archivo);
        return path.delete();
    }
}
