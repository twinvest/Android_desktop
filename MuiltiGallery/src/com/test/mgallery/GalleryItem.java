package com.test.mgallery;

import android.graphics.Bitmap;

public class GalleryItem
{
  private String id;
  private String data;
  private Bitmap bitmap;
  private boolean checkedState;
  
  public Bitmap getBitmap() {
	return bitmap;
}
public void setBitmap(Bitmap bitmap) {
	this.bitmap = bitmap;
}
public String getId()
  {
    return id;
  }
  public void setId(String id)
  {
    this.id = id;
  }
  public String getData()
  {
    return data;
  }
  public void setData(String data)
  {
    this.data = data;
  }
  public boolean getCheckedState()
  {
    return checkedState;
  }
  public void setCheckedState(boolean checkedState)
  {
    this.checkedState = checkedState;
  }
}
