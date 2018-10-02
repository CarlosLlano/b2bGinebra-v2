package co.b2bginebra.logica;

import java.util.Calendar;
import java.util.Properties;


import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import co.b2bginebra.modelo.SolicitudReg;
import co.b2bginebra.modelo.Usuario;
import co.b2bginebra.seguridad.CipherTools;

@Stateless
public class GestionCorreosLogica
{
	@EJB
	private ParametroSistemaLogica parametroSistemaLogica;


	
	public void enviarCorreoResetPassword(Usuario usuario, String ruta)
	{
		try 
		{
			//Parametros de envio
			String sender = parametroSistemaLogica.consultarParametroPorNombre("Correo Institucional").getValor();
			String mailhost = parametroSistemaLogica.consultarParametroPorNombre("host-correo").getValor();
			String userId = parametroSistemaLogica.consultarParametroPorNombre("UserId-correo").getValor();
			String password = parametroSistemaLogica.consultarParametroPorNombre("Password-correo").getValor();
			
			//ruta
			Calendar fecha = Calendar.getInstance();
			String cambioUnico = fecha.get(Calendar.HOUR_OF_DAY)+""+
					  fecha.get(Calendar.DAY_OF_MONTH)+""+
					  fecha.get(Calendar.MONTH)+""+fecha.get(Calendar.YEAR);
			String parametro = CipherTools.encriptar(usuario.getIdUsuario()+";"+cambioUnico);
			
			String mensaje = "Cordial Saludo,\n" 
					+ "De click en el siguiente link para cambiar su clave de acceso:\n" 
					+ ruta + "?recover=" + parametro;
			sendMail("Password reset", mensaje, sender, usuario.getCorreo(), mailhost, userId, password);
		} 
		catch (Exception e) 
		{
			
		}
	}
	
	public void enviarCorreoContacto(String mensaje)
	{
		try 
		{
			//Parametros de envio
			String sender = parametroSistemaLogica.consultarParametroPorNombre("Correo Institucional").getValor();
			String mailhost = parametroSistemaLogica.consultarParametroPorNombre("host-correo").getValor();
			String userId = parametroSistemaLogica.consultarParametroPorNombre("UserId-correo").getValor();
			String password = parametroSistemaLogica.consultarParametroPorNombre("Password-correo").getValor();
			
			sendMail("Correo de contacto", mensaje, sender, sender, mailhost, userId, password);
		} 
		catch (Exception e) 
		{
			
		}
	}
	
	
	public void enviarCorreo(String destinatarios, String Asunto, String mensaje)
	{
		try 
		{
			//Parametros de envio
			String sender = parametroSistemaLogica.consultarParametroPorNombre("Correo Institucional").getValor();
			String mailhost = parametroSistemaLogica.consultarParametroPorNombre("host-correo").getValor();
			String userId = parametroSistemaLogica.consultarParametroPorNombre("UserId-correo").getValor();
			String password = parametroSistemaLogica.consultarParametroPorNombre("Password-correo").getValor();
			
			sendMail(Asunto, mensaje, sender, destinatarios, mailhost, userId, password);
		} 
		catch (Exception e) 
		{
			
		}
	}
	
	public void enviarCorreoSolicitudCreada(SolicitudReg solicitudReg)
	{
		try 
		{
			//Parametros de envio
			String sender = parametroSistemaLogica.consultarParametroPorNombre("Correo Institucional").getValor();
			String mailhost = parametroSistemaLogica.consultarParametroPorNombre("host-correo").getValor();
			String userId = parametroSistemaLogica.consultarParametroPorNombre("UserId-correo").getValor();
			String password = parametroSistemaLogica.consultarParametroPorNombre("Password-correo").getValor();
			
			
			
			//Se envia correo a la Alcaldia informando que alguien quiere registrarse
			String body = "\n" + "Datos de la solicitud: " + "\n" + solicitudReg.getDescripcion();
			sendMail("Solicitud de registro enviada", body, sender, sender, mailhost, userId, password);
			
		} 
		catch (Exception e) 
		{
			
		}
	}
	
	public void enviarCorreoSolicitudAceptada(SolicitudReg solicitudReg)
	{
		try 
		{
			//Parametros de envio
			String sender = parametroSistemaLogica.consultarParametroPorNombre("Correo Institucional").getValor();
			String mailhost = parametroSistemaLogica.consultarParametroPorNombre("host-correo").getValor();
			String userId = parametroSistemaLogica.consultarParametroPorNombre("UserId-correo").getValor();
			String password = parametroSistemaLogica.consultarParametroPorNombre("Password-correo").getValor();
			
			//Se envia correo al usuario informando que su solicitud ha sido aceptada
			String mensaje = solicitudReg.getRespuesta();
			String body = mensaje  +  "\n" + "Datos de la solicitud: " + "\n" + solicitudReg.getDescripcion();
			sendMail("Solicitud de registro aceptada", body, sender, solicitudReg.getNegocio().getUsuario().getCorreo(), mailhost, userId, password);
			
		} 
		catch (Exception e) 
		{
			
		}
	}
	
	public void enviarCorreoSolicitudRechazada(SolicitudReg solicitudReg)
	{
		try 
		{
			//Parametros de envio
			String sender = parametroSistemaLogica.consultarParametroPorNombre("Correo Institucional").getValor();
			String mailhost = parametroSistemaLogica.consultarParametroPorNombre("host-correo").getValor();
			String userId = parametroSistemaLogica.consultarParametroPorNombre("UserId-correo").getValor();
			String password = parametroSistemaLogica.consultarParametroPorNombre("Password-correo").getValor();
			
			//Se envia correo a la Alcaldia informando que su solicitud ha sido rechazada
			String mensaje = solicitudReg.getRespuesta();
			String body = mensaje  +  "\n" + "Datos de la solicitud: " + "\n" + solicitudReg.getDescripcion();
			sendMail("Solicitud de registro rechazada", body, sender, solicitudReg.getNegocio().getUsuario().getCorreo(), mailhost, userId, password);
			
		} 
		catch (Exception e) 
		{
			
		}
	}
	
	


	/**
	 * Metodo generico para enviar correos electronicos
	 * @param subject - Asunto del correo
	 * @param body - Mensaje del correo
	 * @param sender - correo origen
	 * @param recipients - correos destinatarios separados por coma(,)
	 * @param mailhost - proveedor de correo (ej: smtp.gmail.com)
	 * @param userId - Nombre de usuario de la cuenta origen
	 * @param password - Clave de ingreso de la cuenta origen
	 */
	public synchronized void sendMail(String subject, String body,
			String sender, String recipients, String mailhost, String userId, String password) 
	{
		try 
		{

			Properties props = new Properties();
			props.setProperty("mail.transport.protocol", "smtp");
			props.setProperty("mail.host", mailhost);
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.socketFactory.fallback", "false");
			props.setProperty("mail.smtp.quitwait", "false");

			Session session = Session.getDefaultInstance(props, 
					new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(
							userId, password);
				}
			});

			MimeMessage message = new MimeMessage(session);
			message.setSender(new InternetAddress(sender));
			message.setSubject(subject);
			message.setContent(body, "text/plain");
			if (recipients.indexOf(',') > 0)
			{
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
			}	
			else
			{
				message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipients));
			}

			Transport.send(message);
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}


	}


}
