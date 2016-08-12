package com.nisum.employee.ref.service;

/**
 * @author NISUM
 *
 */
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
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
import com.itextpdf.text.DocumentException;
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
	
	@Autowired
	private OfferRepository offerRepository;
	
    public void generateOffer(OfferDTO offer) throws ServiceException
    {
    	
    	try{
    	
    	VelocityContext context = getVelocityContext(offer);
		Template candidateTemplate = getVelocityTemplate(OFFER_LETTER_VM);

		StringWriter writer = new StringWriter();
		candidateTemplate.merge(context, writer);
    	StringReader reader = new StringReader(writer.toString());
        Document document = new Document();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PdfWriter pdfwriter = PdfWriter.getInstance(document, output);
        document.open();
        
        XMLWorkerHelper.getInstance().parseXHtml(pdfwriter, document, reader); 
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
    
    private static VelocityContext getVelocityContext(OfferDTO offer) {
		VelocityContext context = new VelocityContext();
		context.put("cname", offer.getCandidateName());
		context.put("jobcodeProfile", offer.getJobcodeProfile());
		context.put("designation", offer.getDesignation());
		context.put("ctc", offer.getCtc());
		context.put("joiningDate", offer.getJoiningDate());
		context.put("location",offer.getLocation());
		return context;
	}
}

