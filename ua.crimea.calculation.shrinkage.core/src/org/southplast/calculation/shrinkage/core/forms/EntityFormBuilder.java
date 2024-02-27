package org.southplast.calculation.shrinkage.core.forms;

import static org.southplast.calculation.shrinkage.core.utils.CalculationUtils.format;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import org.eclipse.core.databinding.AggregateValidationStatus;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.nebula.widgets.formattedtext.FormattedText;
import org.eclipse.nebula.widgets.formattedtext.NumberFormatter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.southplast.calculation.shrinkage.core.domain.BaseEntity;
import org.southplast.calculation.shrinkage.core.forms.decorators.ErrorDecoration;
import org.southplast.calculation.shrinkage.core.forms.validators.EmptyStringValidator;
import org.southplast.calculation.shrinkage.core.jobs.runnables.AbstractExecutor;
import org.southplast.calculation.shrinkage.core.messages.Messages;


public class EntityFormBuilder extends EntityForm {
	private static final String SPACE = " ";
	private static final Integer MAX_CHARS_COUNT = 4000;
	private static final Integer MIN_CONTROL_HEIGHT = 50;
	
	private DataBindingContext bindingContext;
	
	private BaseEntity model;
	private BaseEntity entity;
	private Class<? extends BaseEntity> entityClass;	
	
	private Label errorLabel;
	private Button saveButton;
	private Button cancelButton;
	
	public EntityFormBuilder(Shell shell, BaseEntity entity) throws CloneNotSupportedException {
		super(shell);	
		
		this.entityClass = entity.getClass();
		this.model = (BaseEntity) entity.clone();
		this.entity = entity;
		this.bindingContext = new DataBindingContext();
	}		
	
	public void createForm(){		
		create();
		
		buildErrorLabel();
	}
	
	private void buildErrorLabel(){
		Composite parent = (Composite) getContents();
		errorLabel = new Label(parent, SWT.NONE);
		errorLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		final IObservableValue errorObservable = WidgetProperties.text().observe(
				errorLabel);	
		errorObservable.addChangeListener(new IChangeListener() {
			@Override
			public void handleChange(ChangeEvent event) {
				if (errorObservable.getValue().equals("OK")) {
					errorObservable.setValue("");
					if(saveButton != null){
						saveButton.setEnabled(true);
					}					
				} else {
					if(saveButton != null){
						saveButton.setEnabled(false);
					}
				}								
			}
		});

		bindingContext.bindValue(errorObservable, new AggregateValidationStatus(
				bindingContext.getBindings(),
				AggregateValidationStatus.MAX_SEVERITY), null, null);	
		}
	
	public <T> void buildObjectList(String name, List<T> list) {
//		add input field in form
		Composite parent = (Composite) getContents();
		Label label = new Label(parent, SWT.NONE);
		label.setText(propertyTitle(Messages.get(messageKey(name))));
		
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		layoutData.horizontalIndent = 13;
		
		ComboViewer viewer = new ComboViewer(parent, SWT.BORDER |  SWT.V_SCROLL | SWT.READ_ONLY);
		viewer.getControl().setLayoutData(layoutData);
		viewer.setLabelProvider(new  LabelProvider() {
		      public Image getImage(Object element) {
		          return null;
		        }

		        public String getText(Object element) {
		        	
		        	return element.toString();
		        }
		});
		viewer.setContentProvider(new IStructuredContentProvider() {
			@Override
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			}
			
			@Override
			public void dispose() {
			}
			
			@SuppressWarnings("unchecked")
			@Override
			public Object[] getElements(Object inputElement) {
				return ((List<T>)inputElement).toArray();
			}
		});
		viewer.setInput(list);
		
//		bind field with model	
		IObservableValue model = PojoProperties.value(entityClass, name)
													.observe(this.model);
		IObservableValue widget = ViewersObservables.observeSingleSelection(
																	viewer);
		bindingContext.bindValue(widget, model);
	}
	
	public void buildNumberInterval(String name){
//		add input fields in form
		Composite parent = (Composite) getContents();
		Group group = new Group(parent, SWT.NONE);
		group.setText(Messages.get(messageKey(name)));
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		group.setLayout(new GridLayout(2, false));
		
		Label minLabel = new Label(group, SWT.NONE);
		minLabel.setText(propertyTitle(Messages.get("text.input.minimum")));
		minLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | 
											GridData.BEGINNING));
		
		Label maxLabel = new Label(group, SWT.NONE);
		maxLabel.setText(propertyTitle(Messages.get("text.input.maximum")));
		maxLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | 
											GridData.BEGINNING));
		
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		layoutData.horizontalIndent = 13;
		
		final FormattedText ftMin = new FormattedText(group, SWT.BORDER);
		ftMin.setFormatter(new NumberFormatter("########0.00", Locale.US));
		ftMin.getControl().setLayoutData(layoutData);
		
		final FormattedText ftMax = new FormattedText(group, SWT.BORDER);
		ftMax.setFormatter(new NumberFormatter("########0.00", Locale.US));
		ftMax.getControl().setLayoutData(layoutData);
		
//		bind fields with model
		
		IConverter fromNumber = new IConverter() {
			@Override
			public Object getToType() {
				return String.class;
			}
			
			@Override
			public Object getFromType() {
				return BigDecimal.class;
			}
			
			@Override
			public Object convert(Object fromObject) {
				fromObject = fromObject != null?fromObject:BigDecimal.ZERO;
				return format((BigDecimal)fromObject);
			}
		};
		IConverter toNumber = new IConverter() {
			@Override
			public Object getToType() {
				return BigDecimal.class;
			}
			
			@Override
			public Object getFromType() {
				return String.class;
			}
			
			@Override
			public Object convert(Object fromObject) {
				return new BigDecimal((String)fromObject);
			}
		};
		
//		final ErrorDecoration decMin = new ErrorDecoration(ftMin.getControl());
//		final ErrorDecoration decMax = new ErrorDecoration(ftMax.getControl());
//		decMin.hide();
//		decMax.hide();
//		IValidator validator = new IValidator() {
//			
//			@Override
//			public IStatus validate(Object value) {
//				BigDecimal max = new BigDecimal(ftMax.getControl().getText());
//				BigDecimal min = new BigDecimal(ftMin.getControl().getText());
//				decMin.hide();
//				decMax.hide();
//				if(min.compareTo(max) > 0){
//					decMin.show(Messages.get("message.error.min.number"));
//					decMax.show(Messages.get("message.error.min.number"));
//					return ValidationStatus.error(Messages.get("message.error.wrong.interval"));
//				}
//				if(	errorLabel.getText().equals(
//					Messages.get("message.error.wrong.interval"))) {
//					errorLabel.setText("");
//					saveButton.setEnabled(true);
//				}				
//				
//				return ValidationStatus.ok();
//			}
//		};

		IObservableValue modelMin = PojoProperties.value(entityClass, name+
																".minimum")
														.observe(this.model);
		IObservableValue widgetMin = WidgetProperties.text(SWT.Modify).observe(
															ftMin.getControl());
		UpdateValueStrategy updateMin = new UpdateValueStrategy();
//		updateMin.setAfterGetValidator(validator);
		updateMin.setConverter(toNumber);
		
		UpdateValueStrategy loadMin = new UpdateValueStrategy();
		loadMin.setConverter(fromNumber);
		
		IObservableValue modelMax = PojoProperties.value(entityClass, name+
																".maximum")
														.observe(this.model);
		IObservableValue widgetMax = WidgetProperties.text(SWT.Modify).observe(
															ftMax.getControl());
		UpdateValueStrategy updateMax = new UpdateValueStrategy();
//		updateMax.setAfterGetValidator(validator);
		updateMax.setConverter(toNumber);
		
		UpdateValueStrategy loadMax = new UpdateValueStrategy();
		loadMax.setConverter(fromNumber);
		
		bindingContext.bindValue(widgetMin, modelMin, updateMin, loadMin);
		bindingContext.bindValue(widgetMax, modelMax, updateMax, loadMax);
	}
	
	public void buildEditableList(String name, List<String> list){
//		add input field in form
		Composite parent = (Composite) getContents();
		Label label = new Label(parent, SWT.NONE);		
		label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		label.setText(propertyTitle(Messages.get(messageKey(name))));
		
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		layoutData.horizontalIndent = 13;
		
		ComboViewer viewer = new ComboViewer(parent, SWT.BORDER );
		viewer.getControl().setLayoutData(layoutData);
		viewer.setLabelProvider(new  LabelProvider() {
		      public Image getImage(Object element) {
		          return null;
		        }

		        public String getText(Object element) {
		          return (String)element;
		        }
		});
		
		viewer.setContentProvider(new IStructuredContentProvider() {
			@Override
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			}
			
			@Override
			public void dispose() {
			}
			
			@SuppressWarnings("unchecked")
			@Override
			public Object[] getElements(Object inputElement) {
				return ((List<String>)inputElement).toArray();
			}
		});
		viewer.setInput(list);

//		bind field with model		
		IObservableValue model = PojoProperties.value(entityClass, name)
													.observe(this.model);		
		IObservableValue target = WidgetProperties.selection().observe(viewer
																.getControl());
		UpdateValueStrategy update = new UpdateValueStrategy();
		update.setAfterGetValidator(new EmptyStringValidator(new ErrorDecoration(
														viewer.getControl()), 
														name));
		
		bindingContext.bindValue(target, model, update, null);						
	}
	
	public void buildTextField(String name){		
//		add input field in form
		Composite parent = (Composite) getContents();
		
		Label label = new Label(parent, SWT.NONE);		
		label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		label.setText(propertyTitle(Messages.get(messageKey(name))));
		
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		layoutData.horizontalIndent = 13;
		
		Text text = new Text(parent, SWT.BORDER);
		text.setTextLimit(MAX_CHARS_COUNT);
		text.setLayoutData(layoutData);
		
//		bind field with model		
		IObservableValue model = PojoProperties.value(entityClass, name)
				  										.observe(this.model);
		IObservableValue widget = WidgetProperties.text(SWT.Modify).observe(
			     														 text);
		UpdateValueStrategy update = new UpdateValueStrategy();
		update.setAfterGetValidator(new EmptyStringValidator(new ErrorDecoration(
																		text), 
																		name));
		bindingContext.bindValue(widget, model, update, null);
	}
	
	public void buildMultiText(String name){
//		add input field in form
		Composite parent = (Composite) getContents();
		
		Label label = new Label(parent, SWT.NONE);		
		label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		label.setText(propertyTitle(Messages.get(messageKey(name))));
		
		GridData layoutData = new GridData(GridData.FILL_BOTH);
		layoutData.horizontalIndent = 13;
		
		Text text = new Text(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | 
															  SWT.H_SCROLL);
		text.setTextLimit(MAX_CHARS_COUNT);
		text.setLayoutData(layoutData);	
		text.addControlListener(new ControlListener() {
			
			@Override
			public void controlResized(ControlEvent e) {
				Control c = (Control) e.widget;
				if(c.getSize().y < MIN_CONTROL_HEIGHT){
					c.setSize(c.getSize().x, MIN_CONTROL_HEIGHT);
				}
				
				
			}
			
			@Override
			public void controlMoved(ControlEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
//		bind field with model	
		IObservableValue model = PojoProperties.value(entityClass, name)
													.observe(this.model);
		IObservableValue widget = WidgetProperties.text(SWT.Modify).observe(text);
		bindingContext.bindValue(widget, model);
	}
	
	public void buildControlBar(final AbstractExecutor job, boolean edit) {
		Composite parent = (Composite) getContents();
		
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayout(new GridLayout(2, false));
		comp.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END | 
										GridData.VERTICAL_ALIGN_END |
										GridData.FILL_HORIZONTAL));
		
		saveButton = new Button(comp, SWT.PUSH);
		saveButton.setText(Messages.get("button.save"));
		saveButton.setEnabled(edit);
		saveButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				entity.compile(model);
				
				job.run(getShell());
				
				close();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
		cancelButton = new Button(comp, SWT.PUSH);
		cancelButton.setText(Messages.get("button.cancel"));
		cancelButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				close();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});
	}
	
	public void run(){
		setBlockOnOpen(true);
		open();
	}
	
	private String messageKey(String name){
		return "text.input." + name;
	}
	
	private String propertyTitle(String name){
		return name + ":" + SPACE;
	}
}
