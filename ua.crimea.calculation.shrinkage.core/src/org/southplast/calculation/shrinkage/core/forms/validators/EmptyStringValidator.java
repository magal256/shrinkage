package org.southplast.calculation.shrinkage.core.forms.validators;

import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.southplast.calculation.shrinkage.core.forms.decorators.ErrorDecoration;
import org.southplast.calculation.shrinkage.core.messages.Messages;


public class EmptyStringValidator implements IValidator {
	private ErrorDecoration decorator;
	private String field;
	
	public EmptyStringValidator(ErrorDecoration decorator, String field) {
		this.decorator = decorator;
		this.field = field;
	}
	
	@Override
	public IStatus validate(Object value) {
		String str = (String)value;		
		if(str.isEmpty()) {
			decorator.show(Messages.get("message.error.empty.string"));
			return ValidationStatus.error(Messages.get("message.write." + field));
		}
		
		decorator.hide();
		return ValidationStatus.ok();
	}

}
