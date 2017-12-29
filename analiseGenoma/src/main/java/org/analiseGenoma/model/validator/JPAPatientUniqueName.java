package org.analiseGenoma.model.validator;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;


@FacesValidator(value = "JPAPatientUniqueNameValidator")

public class JPAPatientUniqueName implements Validator{

//    @Inject
//    private JpaUniqueSupport jpaUniqueSupport;
//    @Inject
//    private ResourcesUtil resourcesUtil;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
//        if (entity == null && "".equals(property) ) {
//            return;
//        }
//
//        if (entity == null) {
//            throw new IllegalStateException("Missing 'entity' attribute");
//        }
//
//        if (property == null) {
//            throw new IllegalStateException("Missing 'property' attribute");
//        }

//        if (!jpaUniqueSupport.isUnique(entity, property, value)) {
//            FacesMessage fm = new FacesMessage(resourcesUtil.getProperty(entity.getClass().getSimpleName().toLowerCase() + "_" + property + "_already_exists"));
//            fm.setSeverity(SEVERITY_ERROR);
//            throw new ValidatorException(fm);
//        }
    }

//    private Identifiable<?> entity;
//    private String property;
//
//    public void setEntity(Identifiable<?> entity) {
//        this.entity = entity;
//    }
//
//    public Identifiable<?> getEntity() {
//        return entity;
//    }
//
//    public void setProperty(String property) {
//        this.property = property;
//    }
//
//    public String getProperty() {
//        return property;
//    }
}