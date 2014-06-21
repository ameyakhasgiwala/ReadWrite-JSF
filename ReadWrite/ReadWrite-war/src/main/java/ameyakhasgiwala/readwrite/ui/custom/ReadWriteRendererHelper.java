package ameyakhasgiwala.readwrite.ui.custom;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.component.UIComponent;
import javax.faces.render.Renderer;

/**
 * This class maintains the Instance Pool for Renderers
 * 
 * @author u26594
 * 
 */
class ReadWriteRendererHelper {

  protected static Map<Class<? extends UIComponent>, RendererMapping> rendererMap;
  protected static Map<String, Renderer> rendererPool;
  protected static final ResourceBundle rb = ResourceBundle.getBundle("ameyakhasgiwala.ReadWriteConfig");

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public static void init() {
    rendererMap = new HashMap<Class<? extends UIComponent>, RendererMapping>();
    rendererPool = new HashMap<String, Renderer>();

    try {

      // Initialize default Renderer
      String defaultRwRenderer = rb.getString("defaultRWRenderer");
      initRenderer(defaultRwRenderer);

      // Initialize all other Renderers and load Config data
      for (int i = 0; rb.containsKey(i + ".component"); i++) {

        // Load Component Class
        Class compCls = Class.forName(rb.getString(i + ".component"));

        // Initialize Default JSF Renderer
        String defaultRenderer = rb.getString(i + ".defaultRenderer");
        initRenderer(defaultRenderer);

        // Initialize RW Renderer
        String rwRenderer = null;
        if (rb.containsKey(i + ".rwRenderer") && !"".equals(rb.getString(i + ".rwRenderer"))) {
          rwRenderer = rb.getString(i + ".rwRenderer");
          initRenderer(rwRenderer);
        } else {
          rwRenderer = defaultRwRenderer;
        }
        rendererMap.put(compCls, new RendererMapping(compCls, defaultRenderer, rwRenderer));
      }
      System.out.println("Initialized Renderer Pool for ReadWriteRenderer");
    } catch (Exception e) {
      System.out.println("Error while Initializing Renderer Pool for ReadWriteRenderer");
      e.printStackTrace();
    }
  }

  protected static Renderer initRenderer(String cls) throws Exception {
    Renderer rendererObj = null;
    if (rendererPool.containsKey(cls)) {
      rendererObj = rendererPool.get(cls);
    } else {
      Class<?> rendererCls = Class.forName(cls);
      rendererObj = (Renderer) rendererCls.newInstance();
      rendererPool.put(cls, rendererObj);
    }
    return rendererObj;
  }

  public static Renderer getDefaultRenderer(Class<? extends UIComponent> component) {
    RendererMapping map = rendererMap.get(component);
    // if there is no renderer for this component, then try renderer for parent class
    if (map == null) {
      map = rendererMap.get(component.getSuperclass());
    }
    return rendererPool.get(map.defaultRenderer);
  }

  public static Renderer getRWRenderer(Class<? extends UIComponent> component) {
    RendererMapping map = rendererMap.get(component);
    return rendererPool.get(map.rwRenderer);
  }

  public static String getProperty(String name) {
    return rb.getString(name);
  }

  /**
   * Class to Hold the Component's mapping with DefaultRenderer and RWRenderer
   * 
   * @author u26594
   * 
   */
  private static class RendererMapping {
    Class<? extends UIComponent> component;
    String defaultRenderer;
    String rwRenderer;

    public RendererMapping(Class<? extends UIComponent> component, String defaultRenderer, String rwRenderer) {
      this.component = component;
      this.defaultRenderer = defaultRenderer;
      this.rwRenderer = rwRenderer;
    }

    public Class<? extends UIComponent> getComponent() {
      return component;
    }

    public String getDefaultRenderer() {
      return defaultRenderer;
    }

    public String getRwRenderer() {
      return rwRenderer;
    }
  }

}