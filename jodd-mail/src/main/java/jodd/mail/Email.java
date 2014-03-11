// Copyright (c) 2003-2014, Jodd Team (jodd.org). All Rights Reserved.

package jodd.mail;

import jodd.util.MimeTypes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * E-mail holds all parts of an email and handle attachments.
 */
public class Email extends CommonEmail {

	/**
	 * Static constructor for fluent interface.
	 */
	public static Email create() {
		return new Email();
	}

	// ---------------------------------------------------------------- from, to, cc, bcc

	/**
	 * @see #setFrom(String)
	 */
	public Email from(String from) {
		setFrom(from);
		return this;
	}

	/**
	 * @see #setTo(String...)
	 */
	public Email to(String to) {
		setTo(to);
		return this;
	}
	/**
	 * @see #setTo(String...)
	 */
	public Email to(String... tos) {
		setTo(tos);
		return this;
	}

	/**
	 * @see #setReplyTo(String...)
	 */
	public Email replyTo(String replyTo) {
		setReplyTo(replyTo);
		return this;
	}
	/**
	 * @see #setReplyTo(String...)
	 */
	public Email replyTo(String... replyTos) {
		setReplyTo(replyTos);
		return this;
	}

	/**
	 * @see #setCc(String...)
	 */
	public Email cc(String cc) {
		setCc(cc);
		return this;
	}
	/**
	 * @see #setCc(String...)
	 */
	public Email cc(String... ccs) {
		setCc(ccs);
		return this;
	}

	/**
	 * @see #setBcc(String...)
	 */
	public Email bcc(String bcc) {
		setBcc(bcc);
		return this;
	}
	/**
	 * @see #setBcc(String...)
	 */
	public Email bcc(String... bccs) {
		setBcc(bccs);
		return this;
	}

	// ---------------------------------------------------------------- subject

	public Email subject(String subject) {
		setSubject(subject);
		return this;
	}

	// ---------------------------------------------------------------- message

	public Email message(String text, String mimeType, String encoding) {
		addMessage(text, mimeType, encoding);
		return this;
	}
	public Email message(String text, String mimeType) {
		addMessage(text, mimeType);
		return this;
	}

	/**
	 * Adds plain message text.
	 */
	public Email addText(String text) {
		messages.add(new EmailMessage(text, MimeTypes.MIME_TEXT_PLAIN));
		return this;
	}
	public Email addText(String text, String encoding) {
		messages.add(new EmailMessage(text, MimeTypes.MIME_TEXT_PLAIN, encoding));
		return this;
	}

	/**
	 * Adds HTML message.
	 */
	public Email addHtml(String message) {
		messages.add(new EmailMessage(message, MimeTypes.MIME_TEXT_HTML));
		return this;
	}
	public Email addHtml(String message, String encoding) {
		messages.add(new EmailMessage(message, MimeTypes.MIME_TEXT_HTML, encoding));
		return this;
	}

	// ---------------------------------------------------------------- attachments

	protected ArrayList<EmailAttachment> attachments;

	/**
	 * Returns an array of attachments or <code>null</code> if no attachment enclosed with this email. 
	 */
	public List<EmailAttachment> getAttachments() {
		return attachments;
	}

	/**
	 * Adds attachment.
	 */
	public Email attach(EmailAttachment emailAttachment) {
		if (attachments == null) {
			attachments = new ArrayList<EmailAttachment>();
		}
		attachments.add(emailAttachment);
		return this;
	}

	/**
	 * Embed attachment to last message.
	 */
	public Email embed(EmailAttachment emailAttachment) {
		attach(emailAttachment);

		if (emailAttachment.isInline()) {
			int size = messages.size();
			if (size > 0) {
				emailAttachment.setEmbeddedMessage(messages.get(size - 1));		// get last message
			}
		}
		return this;
	}

	public Email attach(EmailAttachmentBuilder emailAttachmentBuilder) {
		emailAttachmentBuilder.setInline(false);
		attach(emailAttachmentBuilder.create());
		return this;
	}

	public Email embed(EmailAttachmentBuilder emailAttachmentBuilder) {
		emailAttachmentBuilder.setInline(true);
		embed(emailAttachmentBuilder.create());
		return this;
	}

	// ---------------------------------------------------------------- headers

	public Email header(String name, String value) {
		setHeader(name, value);
		return this;
	}

	public Email priority(int priority) {
		super.setPriority(priority);
		return this;
	}

	// ---------------------------------------------------------------- date

	/**
	 * Sets current date as e-mails sent date.
	 */
	public Email setCurrentSentDate() {
		sentDate = new Date();
		return this;
	}
	
	public Email sentOn(Date date) {
		setSentDate(date);
		return this;
	}

	// ---------------------------------------------------------------- toString

	@Override
	public String toString() {
		return "Email{'" + from + "\', subject='" + subject + "\'}";
	}
}
