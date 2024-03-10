package org.southplast.calculation.shrinkage.core.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.IHandler;

public abstract class BaseHandler extends AbstractHandler implements IHandler {
	public void setEnabled(Boolean enabled) {
		setBaseEnabled(enabled);
	}
}
