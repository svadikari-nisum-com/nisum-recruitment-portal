package com.nisum.employee.ref.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nisum.employee.ref.domain.InterviewFeedback;
import com.nisum.employee.ref.domain.InterviewSchedule;
import com.nisum.employee.ref.domain.Position;
import com.nisum.employee.ref.domain.Profile;
import com.nisum.employee.ref.domain.UserInfo;
import com.nisum.employee.ref.domain.UserNotification;
import com.nisum.employee.ref.exception.ServiceException;
import com.nisum.employee.ref.repository.OfferRepository;
import com.nisum.employee.ref.repository.ProfileRepository;
import com.nisum.employee.ref.repository.UserInfoRepository;
import com.nisum.employee.ref.util.Constants;
import com.nisum.employee.ref.util.EnDecryptUtil;
import com.nisum.employee.ref.view.CalendarDTO;
import com.nisum.employee.ref.view.NotificationMailDTO;
import com.nisum.employee.ref.view.OfferDTO;
import com.nisum.employee.ref.view.PositionDTO;

@Service
@Setter
@Slf4j
public class NotificationService implements INotificationService {

	private static final String DD_MMM_YYYY_HH_MM = "dd-MMM-yyyy HH:mm";
	private static final String YYYY_MM_DD_T_HH_MM_SS_SSS_Z = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	private static final String YOUR_INTERVIEW_FOR = " - Your Interview For ";
	private static final String YOU_NEED_TO_TAKE_INTERVIEW_OF = " - You Need To Take Interview Of ";
	private static final String SKYPE_ID = "skypeId";
	private static final String APP_NAME = "Nisum Recruitment Portal";

	private static final String LOCATION = "location";
	private static final String ALTMOBILE_NO = "altmobileNo";
	private static final String MOBILE_NO = "mobileNo";
	private static final String INTERVIEW_DATE_TIME = "interviewDateTime";
	private static final String TYPE_OF_INTERVIEW = "typeOfInterview";

	private static final String ROUND_NAME = "roundName";
	private static final String INAME = "iname";
	private static final String CNAME = "cname";
	private static final String JOBCODE = "jobcode";
	private static final String TEXT_HTML = "text/html";
	private static final String OF = " of ";
	private static final String FEEDBACK_SUBMITTED_FOR = "Feedback Submitted For ";
	private static final String RATING_LIST = "ratingList";
	private static final String IMPROVEMENTS = "improvements";
	private static final String STRENGTHS = "strengths";
	private static final String FEEDBACK_STATUS = "feedbackStatus";
	private static final String TRUE = "true";
	private static final String MAIL_SMTP_PORT = "mail.smtp.port";
	private static final String MAIL_SMTP_HOST = "mail.smtp.host";
	private static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
	private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";

	private static final String INTERVIEWERS_NOTAVAILABLE_SUBJECT = "Interviewers not available";
	private static final String OFFER_LETTER = "Offer Letter";
	private static final String NEW_POSITION_CREATED="New Position is Created";
	private static final String CLIENT="client";
	private static final String NOOFPOSITIONS="noOfPositions";
	private static final String POSITIONCODE="positionCode";
	private static final String STATUS="status";
	private static final String POSITION_STATUS_CHANGED="Position Status Changed";
	
	private static final String DESCRIPTION = "DESCRIPTION";

	@Value("${mail.smtp.auth}")
	private String smtpAuthRequired;
	@Value("${mail.fromAddress}")
	private String from;
	@Value("${mail.username}")
	private String username;
	@Value("${mail.password}")
	private String password;
	@Value("${mail.host}")
	private String host;

	@Value("${mail.port}")
	private String port;

	@Autowired
	private EnDecryptUtil enDecryptUtil;

	@Value("${SRC_CANDIDATE_VM}")
	private String SRC_CANDIDATE_VM;
	@Value("${SRC_INTERVIEWER_VM}")
	private String SRC_INTERVIEWER_VM;
	@Value("${SRC_FEEDBACK_HR_VM}")
	private String SRC_FEEDBACK_HR_VM;

	@Value("${APP_ERROR_MESSAGE_VM}")
	private String APP_ERROR_MESSAGE_VM;

	@Value("${INTERVIEWERS_NOTAVAILABLE_TEMPLATE}")
	private String INTERVIEWERS_NOTAVAILABLE_TEMPLATE;
	
	@Value("${OFFER_LETTER_MAIL_BODY_TEMPLATE}")
	private String OFFER_LETTER_MAIL_BODY_TEMPLATE;
	
	@Value("${OFFER_INITIATED_TEMPLATE}")
	private String OFFER_INITIATED_TEMPLATE;
	
	@Value("${CANDIDATE_JOINED_TEMPLATE}")
	private String CANDIDATE_JOINED_TEMPLATE;
	
	@Value("${NOTIFICATION_MAIL_TEMPLATE}")
	private String NOTIFICATION_MAIL_TEMPLATE;

	@Value("${error.mail.to}")
	private String errors_notifications_to;
	@Value("${SRC_POSITION_HEAD_VM}")
	private String SRC_POSITION_HEAD_VM;
	@Value("${SRC_POSITION_STATUS_CHANGE_VM}")
	private String SRC_POSITION_STATUS_CHANGE_VM;

	@Autowired
	IProfileService profileService;
	
	@Autowired
	OfferRepository offerRepository;

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Autowired
	UserNotificationService userNotificationService;

	@Autowired
	ProfileRepository profileRepository;
	
	@Override
	public String sendScheduleMail(InterviewSchedule interviewSchedule,
			String mobileNo, String altMobileNo, String skypeId)
			throws ServiceException {

		try {
			// Update UserNotification
			UserNotification userNotification = new UserNotification();
			userNotification.setUserId(interviewSchedule
					.getEmailIdInterviewer());
			userNotification.setMessage("Take interview of "
					+ interviewSchedule.getCandidateName() + " for "
					+ interviewSchedule.getRoundName());
			userNotification.setRead("No");
			userNotificationService.createNotification(userNotification);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}

		String to = interviewSchedule.getCandidateId();
		String toInterviewer = interviewSchedule.getEmailIdInterviewer();

		Session session = getSession();

		VelocityContext context = getVelocityContext(
				interviewSchedule.getCandidateName(),
				interviewSchedule.getJobcode(),
				interviewSchedule.getInterviewerName(),
				interviewSchedule.getRoundName());
		context.put(TYPE_OF_INTERVIEW, interviewSchedule.getTypeOfInterview());
		context.put(INTERVIEW_DATE_TIME,
				getDateTime(interviewSchedule.getInterviewDateTime()));
		context.put(MOBILE_NO, mobileNo);
		context.put(ALTMOBILE_NO, altMobileNo);
		context.put(LOCATION, interviewSchedule.getInterviewLocation());

		Template candidateTemplate = getVelocityTemplate(SRC_CANDIDATE_VM);

		StringWriter writer = new StringWriter();
		candidateTemplate.merge(context, writer);

		VelocityContext context2 = getVelocityContext(
				interviewSchedule.getCandidateName(),
				interviewSchedule.getJobcode(),
				interviewSchedule.getInterviewerName(),
				interviewSchedule.getRoundName());
		context2.put(MOBILE_NO, mobileNo);
		context2.put(ALTMOBILE_NO, altMobileNo);
		context2.put(TYPE_OF_INTERVIEW, interviewSchedule.getTypeOfInterview());
		context2.put(INTERVIEW_DATE_TIME,
				getDateTime(interviewSchedule.getInterviewDateTime()));
		if(skypeId!=null&&!skypeId.isEmpty()){
		context2.put(SKYPE_ID, skypeId);
		}else{
			context2.put(SKYPE_ID, "");
		}

		Template interviewerTemplate = getVelocityTemplate(SRC_INTERVIEWER_VM);

		StringWriter writer2 = new StringWriter();
		interviewerTemplate.merge(context2, writer2);

		// ----------- End Interviewer Config -----------

		// --- Set Interviewer Email Content ---
		try{
			Message msgInterviewer = new MimeMessage(session);
			msgInterviewer.setFrom(new InternetAddress(from));
			msgInterviewer.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(toInterviewer));
			msgInterviewer.setSubject(APP_NAME + YOU_NEED_TO_TAKE_INTERVIEW_OF
					+ interviewSchedule.getCandidateName());
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(writer2.toString(), TEXT_HTML);
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			messageBodyPart = new MimeBodyPart();
			String[] resume = profileService.getResume(interviewSchedule
					.getCandidateId());
			DataSource source = new FileDataSource(resume[0]);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(interviewSchedule.getCandidateName() + "_"+ resume[1]);
			multipart.addBodyPart(messageBodyPart);
			msgInterviewer.setContent(multipart);
	
			// --- Set Candidate Content ---
			Message message = getMessage();
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
			message.setSubject(APP_NAME + YOUR_INTERVIEW_FOR
					+ interviewSchedule.getRoundName() + " Is Sheduled.");
			message.setContent(writer.toString(), TEXT_HTML);
	
			// --- Send Mails ---
			Transport.send(msgInterviewer);
			Transport.send(message);
			return "Mails Sent Successfully!";
		}catch(Exception ex){
			throw new ServiceException(ex);
		}
		
	}

	@Override
	public void sendFeedbackMail(InterviewFeedback interviewFeedback)
			throws MessagingException {

		
		List<Profile> profile = profileRepository.retrieveCandidateDetails(interviewFeedback.getCandidateId());
		
		/*List<String> HR_Emails = new ArrayList<String>();

		for (UserInfo ui : info) {
			HR_Emails.add(ui.getEmailId());
		}*/

		VelocityContext context = getVelocityContext(
				interviewFeedback.getCandidateName(),
				interviewFeedback.getJobcode(),
				interviewFeedback.getInterviewerName(),
				interviewFeedback.getRoundName());
		context.put(STRENGTHS, interviewFeedback.getStrengths());
		context.put(IMPROVEMENTS, interviewFeedback.getImprovement());
		context.put(RATING_LIST, interviewFeedback.getRateSkills());
		context.put(FEEDBACK_STATUS, interviewFeedback.getStatus());
		Template candidateTemplate = getVelocityTemplate(SRC_FEEDBACK_HR_VM);

		StringWriter writer = new StringWriter();
		candidateTemplate.merge(context, writer);

		Message message = null;
		try {
			message = getMessage();
		} catch (ServiceException e) {
			log.error(e.getMessage(),e);
		}
		if (message != null) {
			message.setSubject(FEEDBACK_SUBMITTED_FOR
					+ interviewFeedback.getRoundName() + OF
					+ interviewFeedback.getCandidateName());
			message.setContent(writer.toString(), TEXT_HTML);

			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(profile.get(0).getHrAssigned()));
			Transport.send(message);
			
		}
	}

	private Template getVelocityTemplate(String templetName) {
		Properties prop = new Properties();
		prop.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		prop.setProperty("classpath.resource.loader.class",
				ClasspathResourceLoader.class.getName());
		Velocity.init(prop);
		return Velocity.getTemplate("templates/" + templetName);
	}

	private VelocityContext getVelocityContext(String cname, String jobcode,
			String iname, String roundName) {
		VelocityContext context = new VelocityContext();
		context.put(CNAME, cname);
		context.put(JOBCODE, jobcode);
		context.put(INAME, iname);
		context.put(ROUND_NAME, roundName);
		return context;
	}

	private Message getMessage() throws AddressException, MessagingException,
			ServiceException {
		Session session = getSession();
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		return message;
	}

	private Session getSession() throws ServiceException {
		Properties props = new Properties();
		props.put(MAIL_SMTP_AUTH, smtpAuthRequired);
		props.put(MAIL_SMTP_STARTTLS_ENABLE, TRUE);
		props.put(MAIL_SMTP_HOST, host);
		props.put(MAIL_SMTP_PORT, port);

		Session session = null;
		if (Boolean.TRUE.equals(Boolean.parseBoolean(smtpAuthRequired))) {
			String pwd = enDecryptUtil.decrypt(password);
			session = Session.getInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(username, pwd);
						}
					});
		} else {
			session = Session.getInstance(props);
		}
		return session;
	}

	private String getDateTime(String dateTime) {
		SimpleDateFormat formatter, FORMATTER;
		formatter = new SimpleDateFormat(YYYY_MM_DD_T_HH_MM_SS_SSS_Z);
		//User selected time(UTC) is converting to local time by DatePicker and same is saving in MongoDB
		//We need to convert local time to UTC, while using time in our java code. DatePicker is  doing that in UI level, Here we need to do.
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		FORMATTER = new SimpleDateFormat(DD_MMM_YYYY_HH_MM);
		Date convertedDate = null;
		try {
			convertedDate = formatter.parse(dateTime.substring(0, 24));
		} catch (ParseException e) {
			log.error(e.getMessage(),e);
		}
		return FORMATTER.format(convertedDate);
	}

	public void sendExcetionLog(Exception exp) {
		try {
			VelocityContext context = new VelocityContext();

			StringWriter sw = new StringWriter();
			exp.printStackTrace(new PrintWriter(sw));
			context.put("exceptionLog", sw.toString());
			context.put("appName", APP_NAME);
			Template candidateTemplate = getVelocityTemplate(APP_ERROR_MESSAGE_VM);
			StringWriter writer = new StringWriter();
			candidateTemplate.merge(context, writer);
			Message message = getMessage();
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(errors_notifications_to));
			message.setSubject("There is an error in " + APP_NAME
					+ " application");
			message.setContent(writer.toString(), TEXT_HTML);
			Transport.send(message);
		} catch (Exception ex) {
			log.error(ex.getMessage(),ex);
		}
	}

	public void sendInterviewersNotAvailableStatusNotification()
			throws MessagingException, ServiceException {
		List<UserInfo> interviewers = userInfoRepository
				.retrieveUserByRole("ROLE_INTERVIEWER");
		List<Map<String, String>> interviewersNotAvailable = new ArrayList<>();
		interviewers
				.stream()
				.filter(interviewer -> Boolean.TRUE.equals(interviewer
						.getIsNotAvailable())).forEach(interviewer -> {
					Map<String, String> interviewersMap = new HashMap<>();
					interviewersMap.put("name", interviewer.getName());
					interviewersMap.put("email", interviewer.getEmailId());
					interviewersNotAvailable.add(interviewersMap);
				});
		VelocityContext context = new VelocityContext();
		context.put("interviewersNotAvailable", interviewersNotAvailable);
		Template candidateTemplate = getVelocityTemplate(INTERVIEWERS_NOTAVAILABLE_TEMPLATE);
		StringWriter writer = new StringWriter();
		candidateTemplate.merge(context, writer);
		Message message = getMessage();
		message.setSubject(INTERVIEWERS_NOTAVAILABLE_SUBJECT);
		message.setContent(writer.toString(), TEXT_HTML);
		List<UserInfo> recruiters = userInfoRepository
				.retrieveUserByRole("ROLE_RECRUITER");
		StringBuilder sbAddresses = new StringBuilder();
		recruiters.forEach(recruiter -> sbAddresses.append(
				recruiter.getEmailId()).append(","));
		message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(sbAddresses.toString(), true));
		Transport.send(message);
	}
	
	public void sendOfferLetterNotificationMail(OfferDTO offer) throws ServiceException {
		
		try{
			Message message = getMessage();
			message.setSubject(OFFER_LETTER);
			SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy");
			//message.setDescription("Please find the attached Offer details");
			//message.setContent(writer.toString(), TEXT_HTML);
			//message.setContent(mp);
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(offer.getEmailId(), true));
			
			BodyPart messageBodyPart = new MimeBodyPart();
			
			//messageBodyPart.setDescription("This is message body");//TODO We need get template for this message body.
			Multipart multipart = new MimeMultipart();
			String[] file = offerRepository.getData(offer.getEmailId());
			
			VelocityContext context = new VelocityContext();
			context.put(Constants.CANDIDATE_NAME, offer.getCandidateName());
			context.put(Constants.DESIGNATION, offer.getDesignation());
			context.put(Constants.JOINING_DATE,  dateFormat.format(offer.getJoiningDate()));
			Template candidateTemplate = getVelocityTemplate(OFFER_LETTER_MAIL_BODY_TEMPLATE);
			StringWriter writer = new StringWriter();
			candidateTemplate.merge(context, writer);
			
			BodyPart messageBody = new MimeBodyPart();
			message.setSubject(Constants.OFFER_OF_EMPLOYMENT);
			messageBody.setContent(writer.toString(), TEXT_HTML);
			
			DataSource source = new FileDataSource(file[0]);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(file[1]);
			multipart.addBodyPart(messageBodyPart);
			multipart.addBodyPart(messageBody);
			message.setContent(multipart);
			Transport.send(message);
		
		}catch(Exception ex){
			log.error(ex.getMessage(),ex);
			throw new ServiceException(ex);
		}
	}

	@Override
	public void sendpositionCreationMail(PositionDTO position) throws MessagingException {
	List<UserInfo> managers=userInfoRepository.retrieveUserById(position.getHiringManager());
	UserInfo managerInfo=managers.get(0);
	List<UserInfo> loc_Head=userInfoRepository.retrieveUserById(position.getLocationHead());
	UserInfo loc_HeadInfo=loc_Head.get(0);
	VelocityContext context = new VelocityContext();
		context.put(POSITIONCODE, position.getJobcode());
		context.put(CLIENT, position.getClient());
		context.put(NOOFPOSITIONS, position.getNoOfPositions());
		context.put("functionalGroup", position.getFunctionalGroup());
		context.put("jobHeader", position.getJobHeader());
		context.put("locationHead",loc_HeadInfo.getName());
		context.put("iname",managerInfo.getName());
		Template candidateTemplate = getVelocityTemplate(SRC_POSITION_HEAD_VM);
		StringWriter writer = new StringWriter();
		candidateTemplate.merge(context, writer);

		Message message = null;
		try {
			message = getMessage();
		} catch (ServiceException e) {
			log.error(e.getMessage(),e);
		}
		if (message != null) {
			message.setSubject(NEW_POSITION_CREATED);
			message.setContent(writer.toString(), TEXT_HTML);

			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(position.getLocationHead()));
			Transport.send(message);
			
		}
		
	}

	@Override
	public void sendpositionStatusChangeMail(Position position)
			throws MessagingException {
		List<UserInfo> managers=userInfoRepository.retrieveUserById(position.getHiringManager());
		UserInfo managerInfo=managers.get(0);
		List<UserInfo> loc_Head=userInfoRepository.retrieveUserById(position.getLocationHead());
		UserInfo loc_HeadInfo=loc_Head.get(0);
		VelocityContext context = new VelocityContext();
		context.put(POSITIONCODE, position.getJobcode());		
		context.put(STATUS, position.getStatus());
		context.put("managerName",managerInfo.getName());
		context.put("iname",loc_HeadInfo.getName());
		Template candidateTemplate = getVelocityTemplate(SRC_POSITION_STATUS_CHANGE_VM);
		StringWriter writer = new StringWriter();
		candidateTemplate.merge(context, writer);

		Message message = null;
		try {
			message = getMessage();
		} catch (ServiceException e) {
			log.error(e.getMessage(),e);
		}
		if (message != null) {
			message.setSubject(POSITION_STATUS_CHANGED);
			message.setContent(writer.toString(), TEXT_HTML);
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(position.getHiringManager()));
			Transport.send(message);
		
	   }
	}
	
    public void sendOfferNotificationMail(String name,String emailId,String candidateName,String subject) throws ServiceException {
		
		try{
			Message message = getMessage();
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(emailId, true));
			Multipart multipart = new MimeMultipart();
			Template candidateTemplate = null;
			VelocityContext context = new VelocityContext();
			context.put(Constants.REPORTING_MANAGER_OR_HR_NAME, name);
			context.put(Constants.CANDIDATE_NAME, candidateName);
			if(subject.equals(Constants.CANDIDATE_JOINED)){
				candidateTemplate = getVelocityTemplate(CANDIDATE_JOINED_TEMPLATE);
			}else{
				candidateTemplate = getVelocityTemplate(OFFER_INITIATED_TEMPLATE);
			}
			
			StringWriter writer = new StringWriter();
			candidateTemplate.merge(context, writer);
			
			BodyPart messageBody = new MimeBodyPart();
			message.setSubject(subject);
			messageBody.setContent(writer.toString(), TEXT_HTML);
			multipart.addBodyPart(messageBody);
			message.setContent(multipart);
			Transport.send(message);
		
		}catch(Exception ex){
			log.error(ex.getMessage(),ex);
			throw new ServiceException(ex);
		}
	}
    
    public boolean sendNotificationMail(NotificationMailDTO notificationMailDTO) {
    	
    	Message message = null;
		boolean isMailSent = false;
		try {
			message = new MimeMessage(getSession());

			Multipart mp = new MimeMultipart();
			mp.addBodyPart(buildCalendarPart(notificationMailDTO));
			mp.addBodyPart(buildHtmlTextPart(notificationMailDTO.getCalendarDTO()));
			
			message.setContent(mp);
			message.setSubject(notificationMailDTO.getSubject());
			
//			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("dprasad@nisum.com"));
			
			InternetAddress[] address = new InternetAddress[notificationMailDTO.getAttendees().size()];
			for (int i = 0; i < notificationMailDTO.getAttendees().size(); i++) {
				address[i] = new InternetAddress(notificationMailDTO.getAttendees().get(i));
			}

			message.setRecipients(Message.RecipientType.TO, address);
			
			Transport.send(message);
			isMailSent = true;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		
		return isMailSent;
    }

	private BodyPart buildCalendarPart(NotificationMailDTO notificationMailDTO) throws MessagingException {
		BodyPart calendarPart = new MimeBodyPart();
		
		CalendarDTO calendarDTO = notificationMailDTO.getCalendarDTO();
		
		dateFormatter(calendarDTO.getStartDateTime());

		String calendarContent = "BEGIN:VCALENDAR\n" + "METHOD:REQUEST\n"
				+ "PRODID: BCP - Meeting\n" + "VERSION:2.0\n"
				+ "BEGIN:VEVENT\n" + "DTSTAMP:"
				+ dateFormatter(calendarDTO.getStartDateTime())
				+ "\n"
				+ "DTSTART:"
				+ dateFormatter(calendarDTO.getStartDateTime())
				+ "\n"
				+ "DTEND:"
				+ dateFormatter(calendarDTO.getEndDateTime())
				+ "\n"
				+ "SUMMARY:"
				+ notificationMailDTO.getSubject()
				+"\n"
				+ "UID:324\n"
				+ prepareAttendees(notificationMailDTO.getAttendees())
				+ "ORGANIZER:MAILTO:"
				+ notificationMailDTO.getOrganizer()
				+"\n"
				+ "LOCATION:"
				+ calendarDTO.getLocation()
				+"\n"
				+ "SEQUENCE:0\n"
				+ "PRIORITY:5\n"
				+ "CLASS:PUBLIC\n"
				+ "STATUS:CONFIRMED\n"
				+ "TRANSP:OPAQUE\n"
				+ "BEGIN:VALARM\n"
				+ "ACTION:DISPLAY\n"
				+ "DESCRIPTION:REMINDER\n"
				+ "TRIGGER;RELATED=START:-PT00H15M00S\n"
				+ "END:VALARM\n"
				+ "END:VEVENT\n" + "END:VCALENDAR";

		calendarPart.addHeader("Content-Class", "urn:content-classes:calendarmessage");
		calendarPart.setContent(calendarContent, "text/calendar;method=CANCEL");
		System.out.println(calendarContent);
		return calendarPart;
	}
	
	private String dateFormatter(LocalDateTime localDateTime) {
		return localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmm'00'"));
	}
	
	public String prepareAttendees(List<String> attendees) {
		StringBuilder attendeeBuilder = new StringBuilder();
		for(String x : attendees) {
			attendeeBuilder.append("ATTENDEE;ROLE=REQ-PARTICIPANT;PARTSTAT=NEEDS-ACTION;RSVP=TRUE:MAILTO:"+x+"\n");
		}
		return attendeeBuilder.toString();
	}
	
	private BodyPart buildHtmlTextPart(CalendarDTO calendarDTO) throws MessagingException {
		MimeBodyPart messageBody = new MimeBodyPart();
		
		if(calendarDTO != null) {
			VelocityContext context = new VelocityContext();
			context.put(DESCRIPTION, calendarDTO.getDescription());
			
			Template notificationTemplate = null;
			notificationTemplate = getVelocityTemplate(NOTIFICATION_MAIL_TEMPLATE);
			StringWriter writer = new StringWriter();
			notificationTemplate.merge(context, writer);
			messageBody.setContent(writer.toString(), TEXT_HTML);
		}
		
		return messageBody;
	}
}
