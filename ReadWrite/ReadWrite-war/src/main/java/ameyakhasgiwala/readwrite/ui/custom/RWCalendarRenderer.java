package ameyakhasgiwala.readwrite.ui.custom;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.primefaces.component.calendar.Calendar;

import ameyakhasgiwala.readwrite.util.Constants;

public class RWCalendarRenderer extends RWDefaultRenderer {

  @Override
  public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
    ResponseWriter responseWriter = context.getResponseWriter();
    if (component instanceof Calendar) {
      Calendar calComp = (Calendar) component;
      String value = "";
      Object compVal = getComponentValue(context, calComp);
      if (compVal != null) {
        String pattern = calComp.getPattern();
        pattern = (pattern!=null && pattern.length()>0) ? Constants.DEF_DATE_FORMAT : pattern;
        value = (new SimpleDateFormat(pattern)).format(compVal);
      }
      responseWriter.write(value);
    }
  }

}
