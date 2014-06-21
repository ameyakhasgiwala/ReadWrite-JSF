package ameyakhasgiwala.readwrite.ui.custom;

import java.io.IOException;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

public class RWDisabledComponentRenderer extends RWDefaultRenderer {

  private static final String DISABLED = "disabled";

  @Override
  public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
    ValueExpression disabledExpr = component.getValueExpression(DISABLED);
    component.getAttributes().put(DISABLED, Boolean.TRUE);
    ReadWriteRendererHelper.getDefaultRenderer(component.getClass()).encodeBegin(context, component);
    component.getAttributes().put(DISABLED, Boolean.FALSE);
    component.setValueExpression(DISABLED, disabledExpr);
  }

  @Override
  public void decode(FacesContext context, UIComponent component) {
    ReadWriteRendererHelper.getDefaultRenderer(component.getClass()).decode(context, component);
  }

  @Override
  public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
    ValueExpression disabledExpr = component.getValueExpression(DISABLED);
    component.getAttributes().put(DISABLED, Boolean.TRUE);
    ReadWriteRendererHelper.getDefaultRenderer(component.getClass()).encodeEnd(context, component);
    component.getAttributes().put(DISABLED, Boolean.FALSE);
    component.setValueExpression(DISABLED, disabledExpr);
  }

  @Override
  public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
    ValueExpression disabledExpr = component.getValueExpression(DISABLED);
    component.getAttributes().put(DISABLED, Boolean.TRUE);
    ReadWriteRendererHelper.getDefaultRenderer(component.getClass()).encodeChildren(context, component);
    component.getAttributes().put(DISABLED, Boolean.FALSE);
    component.setValueExpression(DISABLED, disabledExpr);
  }

  @Override
  public Object getConvertedValue(FacesContext context, UIComponent component, Object value) throws ConverterException {
    return ReadWriteRendererHelper.getDefaultRenderer(component.getClass())
        .getConvertedValue(context, component, value);
  }

}
