package br.com.etyllica.motion.filter;

import br.com.etyllica.motion.core.strategy.ComponentModifierStrategy;
import br.com.etyllica.motion.core.strategy.ComponentValidationStrategy;
import br.com.etyllica.motion.core.strategy.SearchFilter;

public class TrackingFilter {
	
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

}
