
package com.gozap.chouti.model;

import com.hp.hpl.sparta.Document;

public interface RssModuleParser {

	/**
	 * Obtain additional items from the channel element 
	 * @param rssType The type of the RSS file (RSS, RDF or ATOM)
	 * @param doc The Document object
	 * @return A custom object mapping your data
	 * @throws Exception
	 */
	public Object parseChannel(int rssType, Document doc) throws Exception;
	
	/**
	 * Obtain additional items from the image element 
	 * @param rssType The type of the RSS file (RSS, RDF or ATOM)
	 * @param doc The Document object
	 * @return A custom object mapping your data
	 * @throws Exception
	 */
	public Object parseImage(int rssType, Document doc) throws Exception;
	
	/**
	 * Obtain additional items from the item element 
	 * @param rssType The type of the RSS file (RSS, RDF or ATOM)
	 * @param doc The Document object
	 * @param index The index of the current item
	 * @return A custom object mapping your data
	 * @throws Exception
	 */
	public Object parseItem(int rssType, Document doc, int index) throws Exception;
}
