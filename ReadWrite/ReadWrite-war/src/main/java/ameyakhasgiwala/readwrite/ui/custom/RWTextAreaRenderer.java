package ameyakhasgiwala.readwrite.ui.custom;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

public class RWTextAreaRenderer extends RWDefaultRenderer {

  @Override
  public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
    ResponseWriter responseWriter = context.getResponseWriter();
    if (component instanceof HtmlInputTextarea) {
      responseWriter
          .write("<div class='textarea' style='overflow-x:hidden; overflow-y:scroll; border-left:1px solid #ccc; border-top:1px solid #ccc; border-right:1px solid #ccc; border-bottom: 1px solid #ccc;'>");
      // super.encodeBegin(context, component);
      Object value = getComponentValue(context, (HtmlInputTextarea) component);
      if (value == null) {
        value = "";
      }
      int index = 0;
      for (String strChunk : value.toString().split("[\n]")) {
        if (index++ > 0) {
          responseWriter.write("<br/>");
        }
        responseWriter.writeText(strChunk, null);
      }
      responseWriter.write("</div>");
    }
  }

}
