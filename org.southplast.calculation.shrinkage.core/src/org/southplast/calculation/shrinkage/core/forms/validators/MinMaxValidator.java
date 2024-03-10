package org.southplast.calculation.shrinkage.core.forms.validators;

import org.eclipse.core.databinding.validation.IValidator;

public abstract class MinMaxValidator implements IValidator {
	protected boolean fromOtherValidator;
	public void setFromOtherValidator(boolean fromOtherValidator) {
		this.fromOtherValidator = fromOtherValidator;
	}
}
