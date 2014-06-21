package ameyakhasgiwala.readwrite.ui.custom.tags;

import java.io.IOException;

import javax.el.ValueExpression;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

@FacesComponent(value = "readWrite")
public class ReadWriteComp extends UIComponentBase {

  public static final String SKIP_CHILDREN = "skipChildren";
  public static final String READ_ONLY_ON = "readOnlyOn";
  public static final String READ_WRITE_FAMILY = "com.juliusbaer.itasia.crm.ReadWrite";

  @Override
  public String getFamily() {
    return READ_WRITE_FAMILY;
  }

  @Override
  public boolean getRendersChildren() {
    return true;
  }

  @Override
  public void encodeBegin(FacesContext context) throws IOException {
    // log.debug("Begin ReadWrite Component");
    ValueExpression readOnly = null;
    try {
      readOnly = getValueExpression(READ_ONLY_ON);
      // log.debug("readOnlyOn:" + readOnly.getValue(context.getELContext()));
    } catch (Exception e) {
      System.out.println("Unable to evaluate readOnlyOn tag");
      e.printStackTrace();
    }

    for (UIComponent childComp : getChildren()) {
      markChildrenReadOnly(childComp, readOnly);
    }
  }

  @Override
  public void encodeChildren(FacesContext context) throws IOException {
    // log.debug("Encode ReadWrite children");

    super.encodeChildren(context);
  }

  public void markChildrenReadOnly(UIComponent component, ValueExpression expr) {
    if (component instanceof ReadWriteComp && component.getAttributes().containsKey(SKIP_CHILDREN)
        && Boolean.valueOf(component.getAttributes().get(SKIP_CHILDREN).toString())) {
      return;
    }
    component.setValueExpression(READ_ONLY_ON, expr);
    for (UIComponent childComp : component.getChildren()) {
      markChildrenReadOnly(childComp, expr);
    }
  }

  @Override
  public void encodeEnd(FacesContext context) throws IOException {
    // log.debug("End ReadWrite Component");
  }

}