
package com.gozap.chouti.model;

import java.util.HashMap;
import java.util.Map;

public class RssImage {
  private String title;
  private String url;
  private String link;
  
  private Map<String, Object> additionalInfo;

  /**
   * Create a new RssImage
   */
  public RssImage(){
     title = "";
     url = "";
     link = "";
  }

  /**
   * Create a new RssImage
   * @param title Title of the image
   * @param url URL of the image
   * @param link The URL to the feed web page
   */
  public RssImage(String title, String url, String link){
	 this.title = title;
	 this.url = url;
	 this.link = link;
  }

  /** 
   * Adds an object with additional info identified by the string name
   * 
   * @param name The string identifying the object
   * @param object The object to add
   */  
  public void putAdditionalInfo(String name, Object object){
	  if (this.additionalInfo==null) this.additionalInfo = new HashMap<String,Object>();
	  this.additionalInfo.put(name, object);
  }
  
  /**
   * Get an object with additional info identified by the string name
   * 
   * @param name The string identifying the object
   * @return An object with additional info
   */  
  public Object getAdditionalInfo(String name){
	  return this.additionalInfo.get(name);
  }  
  
  /**
   * Sets the title of the image element
   * @param title Title of the image element
   */
  public void setTitle(String title){
	 this.title = title;
  }

  /**
   * Sets the URL of the image 
   * @param url URL of the image
   */  
  public void setUrl(String url){
	 this.url = url;
  }

  /**
   * Sets the URL to the feed web page
   * @param link the URL to the feed web page
   */  
  public void setLink(String link){
	 this.link = link;
  }

  /**
   * Returns the title of the image
   * @return Title of the image
   */  
  public String getTitle(){
	 return title;
  }

  /**
   * Returns the URL of the image
   * @return URL of the image
   */    
  public String getUrl(){
	 return url;
  }

  /**
   * Returns the URL where the image links
   * @return URL where the image links
   */    
  public String getLink(){
	 return link;
  }
}