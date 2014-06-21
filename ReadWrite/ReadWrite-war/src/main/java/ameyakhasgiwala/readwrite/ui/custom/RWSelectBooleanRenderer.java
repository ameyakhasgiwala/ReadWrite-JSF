package ameyakhasgiwala.readwrite.ui.custom;

import java.io.IOException;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UISelectBoolean;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

public class RWSelectBooleanRenderer extends RWDefaultRenderer {

  @Override
  public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
    ResponseWriter responseWriter = context.getResponseWriter();
    Object value = null;
    if (component instanceof UISelectBoolean) {
      // Render only value from Input (HTMLInput, HTMLHidden etc)
      UIInput inputComp = (UIInput) component;
      value = getComponentValue(context, inputComp);
      if (value instanceof Boolean && ((Boolean) value)) {
        String expr = ReadWriteRendererHelper.getProperty("SELECT_BOOLEAN_SCRIPT");
        ValueExpression valExpr = context.getApplication().getExpressionFactory()
            .createValueExpression(context.getELContext(), expr, String.class);
        value = valExpr.getValue(context.getELContext());
      } else {
        value = "";
      }
    }

    String strVal = value == null ? "" : String.valueOf(value);
    responseWriter.write(strVal);
  }

}