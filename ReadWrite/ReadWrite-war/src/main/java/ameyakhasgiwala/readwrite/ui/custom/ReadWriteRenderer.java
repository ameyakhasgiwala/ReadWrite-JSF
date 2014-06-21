package ameyakhasgiwala.readwrite.ui.custom;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.render.Renderer;

import ameyakhasgiwala.readwrite.ui.custom.tags.ReadWriteComp;

import com.sun.faces.renderkit.html_basic.HtmlBasicRenderer;

public class ReadWriteRenderer extends HtmlBasicRenderer {

  static {
    try {
      ReadWriteRendererHelper.init();
      System.out.println("Loaded configuration for ReadWriteRenderer");
    } catch (Exception e) {
      System.out.println("Error loading configuration for ReadWriteRenderer");
      e.printStackTrace();
    }
  }

  @Override
  public void decode(FacesContext context, UIComponent component) {
    try {
      Renderer renderer = null;
      if (isReadOnlyOn(component)) {
        renderer = ReadWriteRendererHelper.getRWRenderer(component.getClass());
      } else {
        renderer = ReadWriteRendererHelper.getDefaultRenderer(component.getClass());
      }
      renderer.decode(context, component);
    } catch (Exception e) {
      System.out
          .println("Error while decoding " + component.getClass() + "(id=" + component.getClientId(context) + ")");
      e.printStackTrace();
    }
  }

  @Override
  public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
    // log.debug("ReadWriteRenderer | Encode Begin");
    try {
      Renderer renderer = null;
      if (isReadOnlyOn(component)) {
        renderer = ReadWriteRendererHelper.getRWRenderer(component.getClass());
      } else {
        renderer = ReadWriteRendererHelper.getDefaultRenderer(component.getClass());
      }
      renderer.encodeBegin(context, component);
    } catch (Exception e) {
      System.out.println("Error while encodeBegin of " + component.getClass() + "(id=" + component.getClientId(context)
          + ")");
      e.printStackTrace();
    }
  }

  @Override
  public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
    // log.debug("ReadWriteRenderer | Encode children");
    try {
      Renderer renderer = null;
      if (isReadOnlyOn(component)) {
        renderer = ReadWriteRendererHelper.getRWRenderer(component.getClass());
      } else {
        renderer = ReadWriteRendererHelper.getDefaultRenderer(component.getClass());
      }
      renderer.encodeChildren(context, component);
    } catch (Exception e) {
      System.out.println("Error while encodeChildren of " + component.getClass() + "(id="
          + component.getClientId(context) + ")");
      e.printStackTrace();
    }
  }

  @Override
  public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
    // log.debug("ReadWriteRenderer | Encode End");
    try {
      Renderer renderer = null;
      if (isReadOnlyOn(component)) {
        renderer = ReadWriteRendererHelper.getRWRenderer(component.getClass());
      } else {
        renderer = ReadWriteRendererHelper.getDefaultRenderer(component.getClass());
      }
      renderer.encodeEnd(context, component);
    } catch (Exception e) {
      System.out.println("Error while encodeEnd of " + component.getClass() + "(id=" + component.getClientId(context)
          + ")");
      e.printStackTrace();
    }
  }

  @Override
  public Object getConvertedValue(FacesContext context, UIComponent component, Object submittedValue)
      throws ConverterException {
    // log.debug("ReadWriteRenderer | getConvertedValue");
    Renderer renderer = null;
    if (!isReadOnlyOn(component)) {
      renderer = ReadWriteRendererHelper.getDefaultRenderer(component.getClass());
    }
    if (renderer != null) {
      return renderer.getConvertedValue(context, component, submittedValue);
    }
    return submittedValue;
  }

  protected boolean isReadOnlyOn(UIComponent component) {
    if (component.getAttributes().get(ReadWriteComp.READ_ONLY_ON) != null
        && Boolean.valueOf(component.getAttributes().get(ReadWriteComp.READ_ONLY_ON).toString())) {
      return true;
    }
    return false;
  }

}