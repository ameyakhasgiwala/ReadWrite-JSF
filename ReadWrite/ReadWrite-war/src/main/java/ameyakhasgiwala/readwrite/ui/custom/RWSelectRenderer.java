package ameyakhasgiwala.readwrite.ui.custom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectItems;
import javax.faces.component.UISelectMany;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.model.SelectItem;

public class RWSelectRenderer extends RWDefaultRenderer {

  @Override
  public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
    ResponseWriter responseWriter = context.getResponseWriter();
    if (component instanceof UISelectMany || component instanceof UISelectOne) {
      List<String> valueList = getValueFromChildSelectItems(context, (UIInput) component);
      if (valueList != null && valueList.size() > 0) {
        String value = getValue(valueList);
        responseWriter.write(value);
      }
    }
  }

  // Default implementation to show comma separated values
  protected String getValue(List<String> values) {
    String str = "";
    if (values == null) {
      return str;
    } else if (values.size() == 1) {
      return values.get(0).toString();
    } else if (values.size() > 0) {
      str = values.toString();
      if (str.startsWith("[") && str.endsWith("]")) {
        str = str.substring(1, str.length() - 1);
      }
    }
    return str;
  }

  @SuppressWarnings("unchecked")
  protected List<String> getValueFromChildSelectItems(FacesContext context, UIInput component) {
    Object compVal = getComponentValue(context, component);
    List<String> values = new ArrayList<String>();
    if (compVal == null || (compVal instanceof String && ((String) compVal).length() <= 0)
        || (compVal != null && compVal instanceof Collection && ((Collection<?>) compVal).size() <= 0)) {
      return null;
    }
    boolean singleValued = !(compVal instanceof Collection);
    for (UIComponent childComp : component.getChildren()) {
      if (childComp instanceof UISelectItems) {
        Object val = ((UISelectItems) childComp).getValue();
        if (val instanceof Collection) {
          for (Object key : (Collection<?>) val) {
            if (key instanceof SelectItem) {
              SelectItem item = (SelectItem) key;
              if ((singleValued && compVal.equals(item.getValue()))
                  || (!singleValued && ((Collection<?>) compVal).contains(item.getValue()))) {
                values.add(item.getLabel());
              }
            } else if (key instanceof String) {
              String item = (String) key;
              if ((singleValued && compVal.equals(item)) || (!singleValued && ((Collection<?>) compVal).contains(item))) {
                values.add(item);
              }
            }
          }
        } else if (val instanceof Map) {
          Map<Object, Object> valMap = (Map<Object, Object>) val;
          for (Object key : valMap.keySet()) {
            if ((singleValued && compVal.equals(key)) || (!singleValued && ((Collection<?>) compVal).contains(key))) {
              values.add(String.valueOf(valMap.get(key)));
            }
          }
        }
      } else if (childComp instanceof UISelectItem) {
        Object val = ((UISelectItem) childComp).getItemValue();
        if (compVal.equals(val)) {
          values.add(((UISelectItem) childComp).getItemLabel());
        }
      }
    }
    return values;
  }
}