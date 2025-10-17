package com.aurionpro.loanapp.service.impl;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.aurionpro.loanapp.dto.emi.EmiCalculationRequestDto;
import com.aurionpro.loanapp.dto.emi.EmiCalculationResponseDto;
import com.aurionpro.loanapp.dto.loan.LoanResponseDto;
import com.aurionpro.loanapp.entity.Customer;
import com.aurionpro.loanapp.entity.Loan;
import com.aurionpro.loanapp.entity.LoanScheme;
import com.aurionpro.loanapp.exception.ResourceNotFoundException;
import com.aurionpro.loanapp.repository.CustomerRepository;
import com.aurionpro.loanapp.repository.LoanRepository;
import com.aurionpro.loanapp.repository.LoanSchemeRepository;
import com.aurionpro.loanapp.service.ILoanService;
import com.aurionpro.loanapp.util.EmiCalculator;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements ILoanService {

	private final LoanRepository loanRepository;
	private final CustomerRepository customerRepository;
	private final LoanSchemeRepository loanSchemeRepository;
	private final ModelMapper mapper;

	@Override
	public List<LoanResponseDto> getLoansForCurrentUser(Long customerId) {
		// TODO Auto-generated method stub
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer Not Present"));

		List<Loan> loans = customer.getActiveLoans();
		List<LoanResponseDto> response = new ArrayList<>();
		for (Loan loan : loans) {
			LoanResponseDto loanRes = mapper.map(loan, LoanResponseDto.class);
			loanRes.setLoanSchemeName(loan.getLoanScheme().getSchemeName());
			loanRes.setInterest(loan.getLoanScheme().getInterestRate());

			response.add(loanRes);
		}
		return response;
	}

	@Override
	public LoanResponseDto getLoanDetails(Long loanId) {
		// TODO Auto-generated method stub
		Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new ResourceNotFoundException("Loan Not Found"));

		LoanResponseDto response = mapper.map(loan, LoanResponseDto.class);
		LoanScheme scheme = loan.getLoanScheme();
		if (scheme != null) {
			response.setLoanSchemeName(scheme.getSchemeName());
			response.setInterest(scheme.getInterestRate());
		}

		return response;                     
	}

	  @Override
	    public byte[] generateLoanStatement(Long loanId) {
	        Loan loan = loanRepository.findById(Long.valueOf(loanId))
	                .orElseThrow(() -> new ResourceNotFoundException("Loan not found with ID: " + loanId));

	        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

	            Document document = new Document();
	            PdfWriter.getInstance(document, out);
	            document.open();

	            // --- HEADER ---
	            Font headerFont = new Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 18, Font.BOLD);
	            Paragraph header = new Paragraph("Loan Statement", headerFont);
	            header.setAlignment(Element.ALIGN_CENTER);
	            document.add(header);
	            document.add(new Paragraph(" "));

	            // --- CUSTOMER DETAILS ---
	            document.add(new Paragraph("Customer Name: " + loan.getCustomer().getUser().getFirstName() + " " + loan.getCustomer().getUser().getLastName()));
	            document.add(new Paragraph("Loan ID: " + loan.getId()));
	            document.add(new Paragraph("Scheme: " + loan.getLoanScheme().getSchemeName()));
	            document.add(new Paragraph("Interest Rate: " + loan.getLoanScheme().getInterestRate() + "%"));
	            document.add(new Paragraph(" "));

	            // --- TABLE: LOAN DETAILS ---
	            PdfPTable table = new PdfPTable(2);
	            table.setWidthPercentage(100);
	            table.setSpacingBefore(10);

	            addRow(table, "Loan Amount", String.valueOf(loan.getLoanAmount()));
	            addRow(table, "Tenure (months)", String.valueOf(loan.getTenureMonths()));
	            addRow(table, "Start Date", loan.getStartDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
	            addRow(table, "End Date", loan.getEndDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
	            addRow(table, "Status", loan.getStatus().toString());

	            document.add(table);

	            document.close();
	            return out.toByteArray();

	        } catch (Exception e) {
	            throw new RuntimeException("Error generating loan statement: " + e.getMessage(), e);
	        }
	    }

	    private void addRow(PdfPTable table, String key, String value) {
	        PdfPCell cell1 = new PdfPCell(new Phrase(key));
	        PdfPCell cell2 = new PdfPCell(new Phrase(value));
	        cell1.setPadding(8);
	        cell2.setPadding(8);
	        table.addCell(cell1);
	        table.addCell(cell2);
	    }

		@Override
		public EmiCalculationResponseDto calculateEMI(EmiCalculationRequestDto calculationRequestDto,Long loanSchemeId) {
			// TODO Auto-generated method stub
			LoanScheme loanScheme = loanSchemeRepository.findById(loanSchemeId).orElseThrow(()-> new ResourceNotFoundException("Loan Scheme Not Found"));
			BigDecimal emi = EmiCalculator.calculateMonthlyInstallment(calculationRequestDto.getLoanAmount(), loanScheme.getInterestRate(),calculationRequestDto.getTenureMonths());
			
			EmiCalculationResponseDto res = new EmiCalculationResponseDto();
			res.setEmi(emi);
			
			return res;
		}

}
