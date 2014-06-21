package ameyakhasgiwala.readwrite.ui.custom;

import java.util.List;



public class RWSelectOneManyRenderer extends RWSelectRenderer {
  
  //Only override the method to get value, here <br/>
  protected String getValue(List<String> values) {
    StringBuffer sb = new StringBuffer();
    String separator = ReadWriteRendererHelper.getProperty("MULTI_VALUE_SEPARATOR");
    for(int i=0; i< values.size(); i++) {
      String value = values.get(i);
      if(i>0) {
        sb.append(separator);
      }
      sb.append(value);
    }
    return sb.toString();
  }

}
