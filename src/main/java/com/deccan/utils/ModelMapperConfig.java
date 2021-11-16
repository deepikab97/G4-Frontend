package com.deccan.utils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;

import com.deccan.dto.ViewReceiptDto;
import com.deccan.entity.Enrollment;

@Configuration
public class ModelMapperConfig {

	@Bean
	public static ModelMapper getModelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setFieldMatchingEnabled(true).setMatchingStrategy(MatchingStrategies.LOOSE)
				.setAmbiguityIgnored(true);

		return modelMapper;
	}
	// generic method to convert entity list into dto list  
	public static <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
		ModelMapper modelMapper=ModelMapperConfig.getModelMapper();
	    return source
	      .stream()
	      .map(element -> modelMapper.map(element, targetClass))
	      .collect(Collectors.toList());
	}

	public  static  <D, T> Page<D> mapEntityPageIntoDtoPage(Page<T> entities, Class<D> dtoClass) {
	    return entities.map(objectEntity -> ModelMapperConfig.getModelMapper().map(objectEntity, dtoClass));
	}


}
