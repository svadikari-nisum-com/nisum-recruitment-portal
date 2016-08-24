package com.nisum.employee.ref.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CTCClaculator {

	private Long medicalAllowance = new Long(1250);
	private Long conveyanceAllowance = new Long(1600);

	private Long ctcMonthly;
	private Long ctcYearly;
	private Long basicPayYearly;
	private Long basicPayMonthly;
	private Long hraYearly;
	private Long hraMonthly;

	private Long employeePfContributionYearly;
	private Long employeePfContributionMonthly;
	private Long professionalTaxMonthly;
	private Long professionalTaxYearly;
	private Long grossSalayMonthly;
	private Long grossSalayYearly;

	private Long netSalayMonthly;
	private Long netSalayYearly;
	private Long deductionsYearly;
	private Long deductionsMonthly;
	private Long specialAllowanceMonthly;
	private Long specialAllowanceYearly;
	private Long otherAllowanceMonthly;
	private Long otherAllowanceYearly;

	private Long employerPfContributionYearly;
	private Long employerPfContributionMontly;
	private Long medicalAllowanceYearly;
	private Long medicalAllowanceMonthly;
	private Long conveyanceAllowanceYearly;
	private Long conveyanceAllowanceMonthly;

	public Long getMedicalAllowance() {
		return medicalAllowance;
	}

	public Long getConveyanceAllowance() {
		return conveyanceAllowance;
	}

	public Long getCtcMonthly() {
		return ctcMonthly;
	}

	public Long getCtcYearly() {
		return ctcYearly;
	}

	public Long getBasicPayYearly() {
		return basicPayYearly;
	}

	public Long getBasicPayMonthly() {
		return basicPayMonthly;
	}

	public Long getHraYearly() {
		return hraYearly;
	}

	public Long getHraMonthly() {
		return hraMonthly;
	}

	public Long getEmployeePfContributionYearly() {
		return employeePfContributionYearly;
	}

	public Long getEmployeePfContributionMonthly() {
		return employeePfContributionMonthly;
	}

	public Long getProfessionalTaxMonthly() {
		return professionalTaxMonthly;
	}

	public Long getProfessionalTaxYearly() {
		return professionalTaxYearly;
	}

	public Long getGrossSalayMonthly() {
		return grossSalayMonthly;
	}

	public Long getGrossSalayYearly() {
		return grossSalayYearly;
	}

	public Long getNetSalayMonthly() {
		return netSalayMonthly;
	}

	public Long getNetSalayYearly() {
		return netSalayYearly;
	}

	public Long getDeductionsYearly() {
		return deductionsYearly;
	}

	public Long getDeductionsMonthly() {
		return deductionsMonthly;
	}

	public Long getSpecialAllowanceMonthly() {
		return specialAllowanceMonthly;
	}

	public Long getSpecialAllowanceYearly() {
		return specialAllowanceYearly;
	}

	public Long getOtherAllowanceMonthly() {
		return otherAllowanceMonthly;
	}

	public Long getOtherAllowanceYearly() {
		return otherAllowanceYearly;
	}

	public Long getEmployerPfContributionYearly() {
		return employerPfContributionYearly;
	}

	public Long getEmployerPfContributionMontly() {
		return employerPfContributionMontly;
	}

	public Long getMedicalAllowanceYearly() {
		return medicalAllowanceYearly;
	}

	public Long getMedicalAllowanceMonthly() {
		return medicalAllowanceMonthly;
	}

	public Long getConveyanceAllowanceYearly() {
		return conveyanceAllowanceYearly;
	}

	public Long getConveyanceAllowanceMonthly() {
		return conveyanceAllowanceMonthly;
	}

	public CTCClaculator(long ctc) throws Exception {

		if(ctc > 0){
			basicPayYearly = new BigDecimal(ctc * 40 / 100).setScale(0,RoundingMode.HALF_UP).longValue();
			basicPayMonthly = new BigDecimal(getBasicPayYearly() / 12).setScale(0,RoundingMode.HALF_UP).longValue();;
	
			hraYearly = new BigDecimal(getBasicPayYearly() * 40 / 100).setScale(0,RoundingMode.HALF_UP).longValue();
			hraMonthly = new BigDecimal(getHraYearly() / 12).setScale(0, RoundingMode.HALF_UP).longValue();
	
			medicalAllowanceYearly = (getMedicalAllowance() * 12);
			medicalAllowanceMonthly = getMedicalAllowance();
			conveyanceAllowanceYearly = (getConveyanceAllowance() * 12);
			conveyanceAllowanceMonthly = getConveyanceAllowance();
	
			// Special allowance and other allowance 
			specialAllowanceYearly = new BigDecimal(ctc * 10 / 100).setScale(0,RoundingMode.HALF_UP).longValue();
			specialAllowanceMonthly = new BigDecimal(specialAllowanceYearly/12).setScale(0, RoundingMode.HALF_UP).longValue();
			
			professionalTaxMonthly = 200l;
			professionalTaxYearly = (getProfessionalTaxMonthly() * 12);
	
			employeePfContributionYearly = new BigDecimal(getBasicPayYearly() * 12 / 100).setScale(0,RoundingMode.HALF_UP).longValue();
			employeePfContributionMonthly = new BigDecimal(employeePfContributionYearly / 12).setScale(0,RoundingMode.HALF_UP).longValue();
	
			employerPfContributionYearly = (getEmployeePfContributionYearly());
			employerPfContributionMontly = (getEmployeePfContributionMonthly());
			
			ctcYearly = ctc;
			ctcMonthly = new BigDecimal(ctc / 12).setScale(0,RoundingMode.HALF_UP).longValue();
			
			deductionsYearly = professionalTaxYearly + employeePfContributionYearly;
			deductionsMonthly = professionalTaxMonthly + employeePfContributionMonthly;
			
			grossSalayYearly = ctcYearly - employerPfContributionYearly;
			grossSalayMonthly = ctcMonthly - employerPfContributionMontly;
			
			netSalayYearly = grossSalayYearly - deductionsYearly;
			netSalayMonthly = grossSalayMonthly - deductionsMonthly;
	
			
			//Other Allowance= Gross Salary-(Basic+HRA+CA+MA+Special)
			otherAllowanceYearly = grossSalayYearly - (basicPayYearly + hraYearly + conveyanceAllowanceYearly + medicalAllowanceYearly +
					                                   specialAllowanceYearly );
			if(otherAllowanceYearly <= 0){
				otherAllowanceYearly = 0l;
			}
			otherAllowanceMonthly = grossSalayMonthly - (basicPayMonthly + hraMonthly + getConveyanceAllowance() + getMedicalAllowance() +
                    									specialAllowanceMonthly );
			if(otherAllowanceMonthly <= 0){
				otherAllowanceMonthly = 0l;
			}
			
		}else{
			throw new Exception("CTC should be greater than 0");
		}
		
	}
}
