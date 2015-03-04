/**	
 * @version 1.0
 * @package jm.web.
 * @author Jorge Washington Mueses Cevallos.	
 * @copyright Copyright (C) 2008 por Jorge W. Mueses Cevallos. 
 * Todos los derechos reservados.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL.
*/

package com.far.lib;
import java.util.Properties;
import java.util.Date;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.InternetAddress;

public class Correo {
    private static String error = "";
/**
 * Funci�n que env�a un correo electr�nico desde un servidor SMTP de un remitente
 * a un destinatario.
 * @param svrSMTP. Nombre completo o IP del servidor SMTP.
 * @param dirEnv. Direcci�n del remitente.
 * @param dirA. Direcci�n del destinatario.
 * @param dirAcc. Direcci�n de copia.
 * @param dirAbcc. Direcci�n de copia oculta.
 * @param asunto. El asunto del mensaje.
 * @param txt. Cuerpo del mensaje.
 * @param esHTML. Especifica si el mensaje debe ser interpretado como HTML o solo texto.
 * @return TRue si el correo es enviado satisfactoriamente o false en caso contrario.
 */
    public static boolean enviar(String svrSMTP, String dirRem, String dirA, String dirAcc, String dirAbcc, String asunto,
    String txt, boolean esHTML){
        try {           
            MimeMultipart multipart = new MimeMultipart();
            Properties prop = new Properties();
            prop.put("mail.smtp.host", svrSMTP);
            Session session = Session.getDefaultInstance(prop, null); 
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(dirRem));
            msg.setRecipients(Message.RecipientType.TO, dirA);
            msg.setRecipients(Message.RecipientType.CC, dirAcc);
            msg.setRecipients(Message.RecipientType.BCC, dirAbcc);
            msg.setSubject(asunto);
            msg.setSentDate(new Date());
            MimeBodyPart mbp = new MimeBodyPart();
            if(esHTML){
                mbp.setContent(txt, "text/html");
                //mbp.setContent(txt.toString(), "text/html");    si txt es un StringBuffer
            }else{
                mbp.setText(txt);
                //mbp.setText(txt.toString());
            }
            multipart.addBodyPart(mbp);
            msg.setContent(multipart);
            Transport.send(msg);
        }catch (Exception me){
            Correo.error = me.getMessage();
            return false;
        }
        return true;
    }
    
    public static String getError()
    {
        return Correo.error;
    }
} 