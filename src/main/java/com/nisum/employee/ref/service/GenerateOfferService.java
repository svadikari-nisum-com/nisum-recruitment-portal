package com.nisum.employee.ref.service;

/**
 * @author NISUM
 *
 */
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.nisum.employee.ref.exception.ServiceException;
import com.nisum.employee.ref.repository.OfferRepository;
import com.nisum.employee.ref.view.OfferDTO;

@Service
public class GenerateOfferService 
{
	@Value("${OFFER_LETTER_VM}")
	private String OFFER_LETTER_VM;
	
	@Value("${CTC_BREAK_VM}")
	private String CTC_BREAK_VM;
	
	@Autowired
	private OfferRepository offerRepository;
	
    public void generateOffer(OfferDTO offer) throws ServiceException
    {
    	
    	try{
    	
    	VelocityContext context = getVelocityContext(offer);
		Template candidateTemplate = getVelocityTemplate(OFFER_LETTER_VM);
		Template ctcTemplate = getVelocityTemplate(CTC_BREAK_VM);

		/*candidateTemplate.merge(context, writer);
    	StringReader reader = new StringReader(writer.toString());
        Document document = new Document();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PdfWriter pdfwriter = PdfWriter.getInstance(document, output);
        document.open();*/
        
        StringWriter offerWriter = new StringWriter();
		StringWriter ctcWriter = new StringWriter();
		candidateTemplate.merge(context, offerWriter);
		ctcTemplate.merge(context, ctcWriter);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
    	StringReader offerReader = new StringReader(offerWriter.toString());
    	StringReader ctcReader = new StringReader(ctcWriter.toString());
      // step 1
        Document document = new Document();
        // step 2
        PdfWriter pdfwriter = PdfWriter.getInstance(document, output);
        // step 3
        document.setPageSize(PageSize.A4);
        document.setMargins(50, 45, 50, 60);
        document.setMarginMirroring(false);
        HeaderFooterPageEvent event = new HeaderFooterPageEvent();
        pdfwriter.setPageEvent(event);
        
        document.open();
        // step 4
        XMLWorkerHelper.getInstance().parseXHtml(pdfwriter, document, offerReader); 
        //XMLWorkerHelper.getInstance().parseXHtml(pdfwriter, document, new FileInputStream("Job Offer Letter.html")); 
        //step 5
        document.newPage();
        XMLWorkerHelper.getInstance().parseXHtml(pdfwriter, document, ctcReader); 
        
        document.close();
        //ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());
        
        //XMLWorkerHelper.getInstance().parseXHtml(pdfwriter, document, new FileInputStream("Job Offer Letter.html")); 
        //step 5
        
        offerRepository.saveOfferInBucket(output, offer.getCandidateName(),offer.getEmailId());
        
        
        /*FileOutputStream file = new FileOutputStream("text3.pdf");
        file.write(output.toByteArray());
        file.flush();
        file.close();
        System.out.println("PDF Generated!!!");*/
        
    	}catch(Exception ex){
    		throw new ServiceException(ex);
    	}
         
    }
    public static Template getVelocityTemplate(String templetName) {
		Properties prop = new Properties();
		prop.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		prop.setProperty("classpath.resource.loader.class",
				ClasspathResourceLoader.class.getName());
		Velocity.init(prop);
		return Velocity.getTemplate("templates/" + templetName);
	}
    
    private static VelocityContext getVelocityContext(OfferDTO offer) throws NumberFormatException, ServiceException {
		VelocityContext context = new VelocityContext();
		CTCCalculator calc = new CTCCalculator(Long.parseLong(offer.getCtc()));
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy");
		
		context.put("cname", offer.getCandidateName());
		context.put("jobcodeProfile", offer.getJobcodeProfile());
		context.put("designation", offer.getDesignation());
		context.put("ctc", offer.getCtc());
		context.put("date", dateFormat.format(offer.getJoiningDate()));
		context.put("location",offer.getLocation());
		context.put("city", "Hyderabad");
		context.put("company", "NISUM CONSULTING PRIVATE LIMITED");
		context.put("probation", "2");
		context.put("currentDate",dateFormat.format(new Date()));
		
		context.put("ctcMonthly", calc.getCtcMonthly());
		context.put("ctcYearly", calc.getCtcYearly());
		context.put("employerPfContributionMonthly", calc.getEmployerPfContributionMontly());
		context.put("employerPfContributionYearly", calc.getEmployerPfContributionYearly());
		context.put("netSalaryMonthly",calc.getNetSalayMonthly());
		context.put("netSalaryYearly", calc.getNetSalayYearly());
		context.put("deductionsMonthly", calc.getDeductionsMonthly());
		context.put("deductionsYearly", calc.getDeductionsYearly());
		context.put("employeePfContributionMonthly", calc.getEmployeePfContributionMonthly());
		context.put("employeePfContributionYearly",calc.getEmployeePfContributionYearly());
		
		context.put("professionalTaxMonthly", calc.getProfessionalTaxMonthly());
		context.put("professionalTaxYearly", calc.getProfessionalTaxYearly());
		context.put("grossSalaryMonthly", calc.getGrossSalayMonthly());
		context.put("grossSalaryYearly", calc.getGrossSalayYearly());
		context.put("otherAllowanceMonthly",calc.getOtherAllowanceMonthly());
		context.put("otherAllowanceYearly", calc.getOtherAllowanceYearly());
		context.put("specialAllowanceMonthly", calc.getSpecialAllowanceMonthly());
		context.put("specialAllowanceYearly", calc.getSpecialAllowanceYearly());
		context.put("conveyanceAllowanceMonthly", calc.getConveyanceAllowanceMonthly());
		context.put("conveyanceAllowanceYearly",calc.getConveyanceAllowanceYearly());
		
		context.put("medicalAllowanceMonthly", calc.getMedicalAllowanceMonthly());
		context.put("medicalAllowanceYearly", calc.getMedicalAllowanceYearly());
		context.put("hraMonthly", calc.getHraMonthly());
		context.put("hraYearly", calc.getHraYearly());
		
		context.put("basicPayMonthly",calc.getBasicPayMonthly());
		context.put("basicPayYearly",calc.getBasicPayYearly());
		
		return context;
	}
}

