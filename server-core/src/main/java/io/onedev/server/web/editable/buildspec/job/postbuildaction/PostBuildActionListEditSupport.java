package io.onedev.server.web.editable.buildspec.job.postbuildaction;

import java.io.Serializable;
import java.lang.reflect.AnnotatedElement;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

import io.onedev.server.buildspec.job.action.PostBuildAction;
import io.onedev.server.util.ReflectionUtils;
import io.onedev.server.web.editable.EditSupport;
import io.onedev.server.web.editable.EmptyValueLabel;
import io.onedev.server.web.editable.PropertyContext;
import io.onedev.server.web.editable.PropertyDescriptor;
import io.onedev.server.web.editable.PropertyEditor;
import io.onedev.server.web.editable.PropertyViewer;

public class PostBuildActionListEditSupport implements EditSupport {

	@Override
	public PropertyContext<?> getEditContext(PropertyDescriptor descriptor) {
		if (List.class.isAssignableFrom(descriptor.getPropertyClass())) {
			Class<?> elementClass = ReflectionUtils.getCollectionElementClass(descriptor.getPropertyGetter().getGenericReturnType());
			if (elementClass == PostBuildAction.class) {
				return new PropertyContext<List<Serializable>>(descriptor) {

					@Override
					public PropertyViewer renderForView(String componentId, final IModel<List<Serializable>> model) {
						return new PropertyViewer(componentId, descriptor) {

							@Override
							protected Component newContent(String id, PropertyDescriptor propertyDescriptor) {
								if (model.getObject() != null) {
									return new PostBuildActionListViewPanel(id, model.getObject());
								} else {
									return new EmptyValueLabel(id) {

										@Override
										protected AnnotatedElement getElement() {
											return propertyDescriptor.getPropertyGetter();
										}
										
									};
								}
							}
							
						};
					}

					@Override
					public PropertyEditor<List<Serializable>> renderForEdit(String componentId, IModel<List<Serializable>> model) {
						return new PostBuildActionListEditPanel(componentId, descriptor, model);
					}
					
				};
			}
		}
		return null;
	}

	@Override
	public int getPriority() {
		return 0;
	}
	
}
