package com.aurionpro.loanapp.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aurionpro.loanapp.dto.EmailDto;
import com.aurionpro.loanapp.dto.dashboard.document.DocumentUploadRequestDto;
import com.aurionpro.loanapp.dto.loanapplication.LoanApplicationDto;
import com.aurionpro.loanapp.dto.loanapplication.LoanApplicationRequestDto;
import com.aurionpro.loanapp.dto.loanapplication.LoanApplicationResponseDto;
import com.aurionpro.loanapp.dto.loanapplication.LoanApplicationStatusUpdateRequestDto;
import com.aurionpro.loanapp.dto.page.PageResponseDto;
import com.aurionpro.loanapp.entity.Customer;
import com.aurionpro.loanapp.entity.Document;
import com.aurionpro.loanapp.entity.Loan;
import com.aurionpro.loanapp.entity.LoanApplication;
import com.aurionpro.loanapp.entity.LoanScheme;
import com.aurionpro.loanapp.entity.Officer;
import com.aurionpro.loanapp.entity.User;
import com.aurionpro.loanapp.exception.AccessDeniedException;
import com.aurionpro.loanapp.exception.ResourceNotFoundException;
import com.aurionpro.loanapp.property.LoanApplicationStatus;
import com.aurionpro.loanapp.property.LoanStatus;
import com.aurionpro.loanapp.repository.CustomerRepository;
import com.aurionpro.loanapp.repository.DocumentRepository;
import com.aurionpro.loanapp.repository.LoanApplicationRepository;
import com.aurionpro.loanapp.repository.LoanRepository;
import com.aurionpro.loanapp.repository.LoanSchemeRepository;
import com.aurionpro.loanapp.repository.OfficerRepository;
import com.aurionpro.loanapp.repository.UserRepository;
import com.aurionpro.loanapp.service.EmailService;
import com.aurionpro.loanapp.service.ILoanApplicationService;
import com.aurionpro.loanapp.util.EmiCalculator;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoanApplicationServiceImpl implements ILoanApplicationService {
	private final UserRepository userRepository;
	private final LoanSchemeRepository loanSchemeRepository;
	private final CustomerRepository customerRepository;
	private final LoanApplicationRepository loanApplicationRepository;
	private final OfficerRepository officerRepository;
	private final DocumentRepository documentRepository;
	private final LoanRepository loanRepository;
	private final Cloudinary cloudinary;
	private final EmailService emailService;
	private final ModelMapper mapper;

	@Override
	@Transactional
	public LoanApplicationResponseDto applyForLoan(LoanApplicationRequestDto applicationRequestDto,
			String customerEmail) {
		User user = userRepository.findByEmail(customerEmail)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with email id " + customerEmail));

		Customer customer = customerRepository.findById(user.getId()).orElseThrow(
				() -> new ResourceNotFoundException("Customer profile is not created yet, first create it"));

		LoanScheme loanScheme = loanSchemeRepository.findById(applicationRequestDto.getLoanSchemeId())
				.orElseThrow(() -> new ResourceNotFoundException(
						"Loan Scheme not found with id " + applicationRequestDto.getLoanSchemeId()));
		
//		LoanApplication loanApplication = loanApplicationRepository.findById(applicationRequestDto.getLoanSchemeId())
//				.orElse(null);
//		
//		if(loanApplication != null && loanApplication.getApplicationStatus().equals(LoanApplicationStatus.))

		LoanApplication loanApplication = mapper.map(applicationRequestDto, LoanApplication.class);

		loanApplication.setId(null);
		
//		Validate Application function (Later implementation)
		if (!isEligible(applicationRequestDto))
			throw new RuntimeException("Not eligible");

		loanApplication.setApplicationStatus(LoanApplicationStatus.NOT_APPLIED_YET);
		loanApplication.setApplicationId(UUID.randomUUID().toString());
		
		loanApplication.setCustomer(customer);
		loanApplication.setLoanScheme(loanScheme);

		LoanApplication savedApplication = loanApplicationRepository.save(loanApplication);

		return mapper.map(savedApplication, LoanApplicationResponseDto.class);
	}

	@Override
	public LoanApplicationResponseDto submitLoanApplication(Long applicationId,String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with email id " + email));

		
		LoanApplication loanApplication = loanApplicationRepository.findById(applicationId).orElseThrow(
				() -> new ResourceNotFoundException("Loan application not found with id " + applicationId));
		
		if(loanApplication.getCustomer().getId() != user.getId()) {
			throw new AccessDeniedException("You are not authorized user");
		}
			

		loanApplication.setApplicationStatus(LoanApplicationStatus.PENDING);
		
		EmailDto emailDto = new EmailDto();
		emailDto.setTo(user.getEmail());
		emailDto.setSubject("Loan Application Submitted Successfully");
		String body = "Dear " + user.getFirstName()+" "+user.getLastName() + ",\n\n"
                + "Your loan application (ID: " + loanApplication.getApplicationId() + ") has been submitted successfully.\n"
                + "Our team will review it and get back to you shortly.\n\n"
                + "Thank you for choosing LoanSaathi.\n\n"
                + "Regards,\nLoanSaathi Team";
		
		emailDto.setBody(body);
		
		emailService.sendEmailWithoutImage(emailDto);
		
//		Assigned the loan officer

		loanApplication = loanApplicationRepository.save(loanApplication);

		return mapper.map(loanApplication, LoanApplicationResponseDto.class);
	}

	@Override
	@Transactional
	public LoanApplicationResponseDto uploadDocuments(Long applicationId, List<DocumentUploadRequestDto> documents)
			throws IOException {
		LoanApplication loanApplication = loanApplicationRepository.findById(applicationId).orElseThrow(
				() -> new ResourceNotFoundException("Loan application not found with id " + applicationId));

		documents.forEach(document -> {
			try {
				Map uploadResult = cloudinary.uploader().upload(document.getFile().getBytes(),
						ObjectUtils.asMap("folder", "loan_documents", "resource_type", "auto"));
				String url = uploadResult.get("secure_url").toString();

				String name = document.getFile().getOriginalFilename();
				
				Document doc = new Document();
				doc.setLoanApplication(loanApplication);
				doc.setName(name);
				doc.setType(document.getDocumentType());
				doc.setUrl(url);
				
				loanApplication.getDocuments().add(doc);

				
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage());
			}

		});
		
		loanApplicationRepository.save(loanApplication);
		return mapper.map(loanApplication, LoanApplicationResponseDto.class);

	}
	
	@Override
	@Transactional
	public LoanApplicationResponseDto updateApplicationStatus(String officerEmail,LoanApplicationStatusUpdateRequestDto request) {
		Officer officer = officerRepository.findByUserEmail(officerEmail).orElseThrow(
				() -> new ResourceNotFoundException("Officer not found with email " + officerEmail));
		
		LoanApplication loanApplication = loanApplicationRepository.findById(request.getApplicationId()).orElseThrow(
				() -> new ResourceNotFoundException("Application not found with id " + request.getApplicationId()));

		

		loanApplication.setApplicationStatus(request.getNewLoanApplicationStatus());
		loanApplication.setAssignedOfficer(officer);

		LoanApplication updatedApplication = loanApplicationRepository.save(loanApplication);

		if (request.getNewLoanApplicationStatus().equals(LoanApplicationStatus.APPROVED)) {
			createLoanFromApplication(updatedApplication);
		}
		return mapper.map(updatedApplication, LoanApplicationResponseDto.class);
	}

	@Transactional
	private void createLoanFromApplication(LoanApplication application) {
		Loan newLoan = new Loan();

		BigDecimal emiAmount = EmiCalculator.calculateMonthlyInstallment(application.getRequiredAmount(), application.getLoanScheme().getInterestRate(), application.getTenure());
		
		newLoan.setLoanNumber(UUID.randomUUID()); // Generate a unique public ID
		newLoan.setCustomer(application.getCustomer());
		newLoan.setLoanScheme(application.getLoanScheme());
		newLoan.setLoanAmount(application.getRequiredAmount());
		newLoan.setTenureMonths(application.getTenure());
		newLoan.setStartDate(LocalDateTime.now());
		newLoan.setEmiAmount(emiAmount);
		newLoan.setEndDate(LocalDateTime.now().plusMonths(application.getTenure()));
		newLoan.setStatus(LoanStatus.ACTIVE);
		newLoan.setCreatedAt(LocalDateTime.now());
		
		
		loanRepository.save(newLoan);
		Customer customer = application.getCustomer();
		User user = customer.getUser();
		
		EmailDto emailDto = new EmailDto();
		emailDto.setTo(user.getEmail());
		emailDto.setSubject("Loan Application Approved");
		String body = "Dear " + user.getFirstName() + " " + user.getLastName() + ",\n\n"
		        + "We are pleased to inform you that your loan application has been approved.\n\n"
		        + "Here are the details of your approved loan:\n"
		        + "--------------------------------------------------\n"
		        + "Application ID : " + application.getApplicationId() + "\n"
		        + "Loan Number    : " + newLoan.getLoanNumber() + "\n"
		        + "Loan Amount    : ₹" + application.getRequiredAmount() + "\n"
		        + "Tenure         : " + application.getTenure() + " months\n"
		        + "EMI Amount     : ₹" + newLoan.getEmiAmount() + "\n"
		        + "Status         : " + newLoan.getStatus() + "\n"
		        + "Start Date     : " + newLoan.getStartDate().toLocalDate() + "\n"
		        + "End Date       : " + newLoan.getEndDate().toLocalDate() + "\n"
		        + "--------------------------------------------------\n\n"
		        + "Please keep this information for your records.\n\n"
		        + "If you have any questions or need assistance, feel free to contact our support team.\n\n"
		        + "Thank you for choosing [Your Company Name].\n\n"
		        + "Sincerely,\n"
		        + "LoanSaathi Loan Department";

		emailDto.setBody(body);
		emailService.sendEmailWithoutImage(emailDto);

	}


	private boolean isEligible(LoanApplicationRequestDto applicationRequestDto) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public PageResponseDto<LoanApplicationDto> getAssignedApplicationsOfOfficer(String officerEmail, int pageNumber,int pageSize) {
		User officerUser = userRepository.findByEmail(officerEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Officer not found with username: " + officerEmail));

		Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<LoanApplication> applicationPage= loanApplicationRepository.findByAssignedOfficerId(officerUser.getId(),pageable);
        
        List<LoanApplicationDto> applications= applicationPage.getContent().stream()
        .map(application-> mapper.map(application, LoanApplicationDto.class))
        .collect(Collectors.toList());
        
        PageResponseDto<LoanApplicationDto> response = mapper.map(applicationPage,PageResponseDto.class);
        response.setContent(applications);
        
        return response;
	}

	@Override
	public LoanApplicationResponseDto getApplicationById(Long applicationId, Authentication authentication) {
		    LoanApplication application = loanApplicationRepository.findById(applicationId)
		            .orElseThrow(() -> new ResourceNotFoundException("Loan application not found with ID: " + applicationId));

		    boolean isCustomer = authentication.getAuthorities().stream()
		            .anyMatch(role -> role.getAuthority().equals("ROLE_CUSTOMER"));

		    if (isCustomer) {
		        String customerEmail = authentication.getName();
		        
		        // Security Check: Verify that the logged-in customer is the owner of the application.
		        if (!application.getCustomer().getUser().getEmail().equals(customerEmail)) {
		            throw new AccessDeniedException("You do not have permission to view this application.");
		        }
		        
		        return mapper.map(application,LoanApplicationResponseDto.class);

		    } 
		    return mapper.map(application,LoanApplicationResponseDto.class);
		

	}

	@Override
	public List<LoanApplicationDto> getApplicationsForCurrentUser(String email) {
		// TODO Auto-generated method stub
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with email id " + email));

		Customer customer = customerRepository.findById(user.getId()).orElseThrow(
				() -> new ResourceNotFoundException("Customer profile is not created yet, first create it"));
		
		List<LoanApplicationDto> response = new ArrayList<>();
		for(LoanApplication appln: customer.getLoanApplications()) {
			response.add(mapper.map(appln, LoanApplicationDto.class));
		}
		
		return response;
	}

	@Override
	public LoanApplicationDto getApplicationStatus(String applicationId) {
		// TODO Auto-generated method stub
		LoanApplication appln = loanApplicationRepository.findByApplicationId(applicationId).
				orElseThrow(()-> new ResourceNotFoundException("Application not found"));
		
		return mapper.map(appln, LoanApplicationDto.class);
	}

	// Officer
	
	@Override
	public void approveApplication(String applicationId) {
		// TODO Auto-generated method stub
		LoanApplication appln = loanApplicationRepository.findByApplicationId(applicationId).
				orElseThrow(()-> new ResourceNotFoundException("Application not found"));
		appln.setApplicationStatus(LoanApplicationStatus.APPROVED);
		loanApplicationRepository.save(appln);
		
		createLoanFromApplication(appln);
		
	}

	@Override
	public void rejectApplication(String applicationId, LoanApplicationStatus applicationStatus) {
		// TODO Auto-generated method stub
		
	}
}
