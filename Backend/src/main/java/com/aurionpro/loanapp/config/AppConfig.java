package com.aurionpro.loanapp.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aurionpro.loanapp.dto.loanapplication.LoanApplicationResponseDto;
import com.aurionpro.loanapp.entity.LoanApplication;

@Configuration
public class AppConfig {

	@Bean
	public ModelMapper modelMapper() {
		
		ModelMapper modelMapper = new ModelMapper();
		
		Converter<LoanApplication, String> customerNameConverter = new Converter<LoanApplication, String>() {
			public String convert(MappingContext<LoanApplication, String> context) {

				LoanApplication source = context.getSource();

				if (source == null || source.getCustomer() == null || source.getCustomer().getUser() == null) {
					return null;
				}

				String firstName = source.getCustomer().getUser().getFirstName();
				String lastName = source.getCustomer().getUser().getLastName();

				return firstName + " " + lastName;
			}
		};

		// Add the mapping for LoanApplication -> LoanApplicationResponseDto
		modelMapper.typeMap(LoanApplication.class, LoanApplicationResponseDto.class).addMappings(mapper -> {
			mapper.using(customerNameConverter).map(source -> source, LoanApplicationResponseDto::setCustomerName);
		});

		return modelMapper;
	}

}
