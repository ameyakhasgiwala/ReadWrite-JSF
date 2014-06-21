package ameyakhasgiwala.readwrite.ui.custom;

import java.io.IOException;
import java.util.Collection;

import javax.el.ValueExpression;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UIGraphic;
import javax.faces.component.UIInput;
import javax.faces.component.UIMessage;
import javax.faces.component.UIMessages;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.ConverterException;

import com.sun.faces.facelets.compiler.UILeaf;
import com.sun.faces.renderkit.html_basic.HtmlBasicRenderer;

public class RWDefaultRenderer extends HtmlBasicRenderer {

  protected static final String WRAPPER_TAG_NAME = "SPAN";
  protected static final String VALUE = "value";
  protected static final String STYLE = "style";
  protected static final String STYLE_CLASS = "styleClass";
  protected static final String CLASS = "class";

  protected Object getComponentValue(FacesContext context, UIComponent component) {
    return getComponentAttribute(context, component, VALUE);
  }

  protected Object getComponentAttribute(FacesContext context, UIComponent component, String attrName) {
    ValueExpression expr = component.getValueExpression(attrName);
    if (expr != null) {
      return expr.getValue(context.getELContext());
    } else if (component.getAttributes().containsKey(attrName)) {
      return component.getAttributes().get(attrName);
    }
    return null;
  }

  protected void startWrapperTag(FacesContext context, UIComponent component) throws IOException {
    ResponseWriter responseWriter = context.getResponseWriter();
    responseWriter.startElement(WRAPPER_TAG_NAME, null);
    Object style = getComponentAttribute(context, component, STYLE);
    if (style != null && style.toString().length() > 0) {
      responseWriter.writeAttribute(STYLE, style, null);
    }
    Object styleClass = getComponentAttribute(context, component, STYLE_CLASS);
    if (styleClass != null && styleClass.toString().length() > 0) {
      responseWriter.writeAttribute(CLASS, styleClass, null);
    }
  }

  protected void endWrapperTag(FacesContext context) throws IOException {
    ResponseWriter responseWriter = context.getResponseWriter();
    responseWriter.endElement(WRAPPER_TAG_NAME);
  }

  @Override
  public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
    ResponseWriter responseWriter = context.getResponseWriter();
    Object value = null;
    // Render HTML Text, Image and Messages without alteration
    if (component instanceof UILeaf || component instanceof UIGraphic || component instanceof UIMessage
        || component instanceof UIMessages) {
      component.encodeAll(context);
    } else if (component instanceof UICommand) {
      // Do not render Command (CommandButton, CommandLink etc)
    } else if (component instanceof UIInput) {
      // Render only value from Input (HTMLInput, HTMLHidden etc)
      UIInput inputComp = (UIInput) component;
      value = getComponentValue(context, inputComp);
      // Check if a Converter exists
      if (inputComp.getConverter() != null) {
        value = inputComp.getConverter().getAsString(context, component, value);
      }
    }

    if (value instanceof Collection) {
      String str = value.toString();
      if (str.startsWith("[") && str.endsWith("]")) {
        value = str.substring(1, str.length() - 1);
      }
    }
    String strVal = value == null ? "" : String.valueOf(value);
    responseWriter.write(strVal);
    // log.info("ReadWriteRenderer | rendered component " + component.getClass() + " with value " +
    // String.valueOf(value));
  }

  @Override
  public void decode(FacesContext context, UIComponent component) {
    // Do Nothing
  }

  @Override
  public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
    // Do Nothing
  }

  @Override
  public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
    // Do Nothing
  }

  @Override
  public Object getConvertedValue(FacesContext context, UIComponent component, Object value) throws ConverterException {
    // Do Nothing
    return value;
  }

}