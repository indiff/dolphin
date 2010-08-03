package com.tan.util;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

/**
 * @author Genix.Cao Blog:http://blogs.sun.com/greysh
 */
public class Email {
	
	public static  void main(String[] args) throws Exception{}
	private static final Integer SimpleEmail = 0;
	private static final Integer MultiPartEmail = 1;
	private static final Integer HtmlEmail = 2;
	private String host;
	private String username;
	private String password;
	private String charset;
	private String postEmail;
	private String postName;
	private String receiveEmail;
	private String receiveName;
	private String subject;
	private String content;
	private String path;
	private String name;
	private String description;
	private boolean isLocalFile;
	private boolean isAttach;

	public void initPoster(String host, 
						String username, 
						String password, 
						String charset, 
						String postEmail) {
		this.host = host;
		this.username = username;
		this.password = password;
		this.charset = charset;
		this.postEmail = postEmail;
	}

	public void initReceiver(String receiveEmail, 
						String receiveName, 
						String subject, 
						String content, 
						boolean isAttach) {
		this.receiveEmail = receiveEmail;
		this.receiveName = receiveName;
		this.subject = subject;
		this.content = content;
		this.isAttach = isAttach;
	}

	public void initAttachment(String path, 
			String name, 
			String description, 
			boolean isLocalFile) {
		this.path = path;
		this.name = name;
		this.description = description;
		this.isLocalFile = isLocalFile;
	}

	public void send(Integer emailType) throws EmailException, MalformedURLException {
		org.apache.commons.mail.Email email;
		if (emailType.equals(SimpleEmail)) {
			email = new SimpleEmail();
		} else if (emailType.equals(MultiPartEmail)) {
			email = new MultiPartEmail();
			if (isAttach && getEmailAttachment() != null) {
				((MultiPartEmail) email).attach(getEmailAttachment());
			}
		} else {
			email = new HtmlEmail();
			if (isAttach && getEmailAttachment() != null) {
				((MultiPartEmail) email).attach(getEmailAttachment());
			}
		}
		email.setHostName(host);
		email.setAuthentication(username, password);
		email.setCharset(charset);
		email.setFrom(postEmail, postName);
		email.addTo(receiveEmail, receiveName);
		email.setSubject(subject);
		if (emailType.equals(HtmlEmail)) {
			((HtmlEmail) email).setHtmlMsg(content);
		} else {
			email.setMsg(content);
		}
		email.send();
	}

	public EmailAttachment getEmailAttachment() throws MalformedURLException {
		EmailAttachment attachment = new EmailAttachment();
		if (isLocalFile) {
			attachment.setPath(path);
		} else {
			attachment.setURL(new URL(path));
		}
		attachment.setDisposition(EmailAttachment.ATTACHMENT);
		attachment.setDescription(description);
		attachment.setName(name);
		return attachment;
	}
}
