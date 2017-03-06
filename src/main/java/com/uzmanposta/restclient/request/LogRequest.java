package com.uzmanposta.restclient.request;



import java.util.Date;

public class LogRequest {


	public LogRequest(String domain,String fromEmail,String mailTo,Date startDate,String subject){

		this.domain = domain;
		this.fromEmail = fromEmail;
		this.mailTo = mailTo;
		this.startDate =startDate;
		this.subject =subject;
	}
	
	private String domain;
	private String fromEmail;
	private String mailTo;
	private Date startDate;
	private String subject;

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getMailTo() {
		return mailTo;
	}

	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getFromEmail() {
		return fromEmail;
	}

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public static class LogRequestBuilder {
		String domain;
		String fromEmail;
		String mailTo;
		String subject;
		Date startDate;

		public LogRequestBuilder addDomain(String domain) {
			this.domain = domain;
			return this;
		}

		public LogRequestBuilder addFromEmail(String fromEmail) {
			this.fromEmail = fromEmail;
			return this;
		}
		
		public LogRequestBuilder addMailTo(String mailTo) {
			this.mailTo = mailTo;
			return this;
		}
		
		public LogRequestBuilder addSubject(String subject) {
			this.subject = subject;
			return this;
		}
		
		public LogRequestBuilder addStartDate(Date startDate) {
			this.startDate = startDate;
			return this;
		}
		public LogRequest build() {
			return new LogRequest(domain,fromEmail,mailTo,startDate,subject);
		}
		
	}

}
