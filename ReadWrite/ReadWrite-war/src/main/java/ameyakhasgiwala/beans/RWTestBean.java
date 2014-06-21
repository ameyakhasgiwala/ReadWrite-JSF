package ameyakhasgiwala.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

@ManagedBean(name="rwTestBean")
@SessionScoped
public class RWTestBean {

  boolean readOnly = true;
  
  String text;
  String selectOne;
  String selectBoolean;
  
  List<SelectItem> items;
  
  public RWTestBean() {
    items = new ArrayList<SelectItem>();
    items.add(new SelectItem("value-1", "Label - 1"));
    items.add(new SelectItem("value-2", "Label - 2"));
    items.add(new SelectItem("value-3", "Label - 3"));
    items.add(new SelectItem("value-4", "Label - 4"));
  }
  
  public void enterReadMode() {
    readOnly = true;
  }
  
  public void enterWriteMode() {
    readOnly = false;
  }

  public boolean isReadOnly() {
    return readOnly;
  }

  public void setReadOnly(boolean isReadOnly) {
    this.readOnly = isReadOnly;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getSelectOne() {
    return selectOne;
  }

  public void setSelectOne(String selectOne) {
    this.selectOne = selectOne;
  }

  public String getSelectBoolean() {
    return selectBoolean;
  }

  public void setSelectBoolean(String selectBoolean) {
    this.selectBoolean = selectBoolean;
  }

  public List<SelectItem> getItems() {
    return items;
  }

  public void setItems(List<SelectItem> items) {
    this.items = items;
  }
  
  
}
