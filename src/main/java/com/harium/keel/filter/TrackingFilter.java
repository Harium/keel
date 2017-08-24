package com.harium.keel.filter;

import java.util.List;

import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.strategy.ComponentModifierStrategy;
import com.harium.keel.core.strategy.ComponentValidationStrategy;
import com.harium.keel.core.strategy.SearchFilter;
import com.harium.keel.feature.Component;

public abstract class TrackingFilter {
	
	protected SearchFilter searchStrategy;
	
	public TrackingFilter(SearchFilter searchStrategy) {
		super();
		
		this.searchStrategy = searchStrategy;
	}
	
	public void addValidation(ComponentValidationStrategy validation) {
		searchStrategy.addValidation(validation);
	}

	public ComponentModifierStrategy getComponentModifierStrategy() {
		return searchStrategy.getComponentModifierStrategy();
	}

	public void setComponentModifierStrategy(
			ComponentModifierStrategy componentModifierStrategy) {
		searchStrategy.setComponentModifierStrategy(componentModifierStrategy);
	}

	public SearchFilter getSearchStrategy() {
		return searchStrategy;
	}

	public void setSearchStrategy(SearchFilter searchStrategy) {
		this.searchStrategy = searchStrategy;
	}

	public void clearValidations() {
		searchStrategy.getValidations().clear();
	}
	
	public abstract List<Component> filter(ImageSource bimg, Component component);
	
	public abstract Component filterFirst(ImageSource bimg, Component component);
	
}
