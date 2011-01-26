/**
 * Service
 * 
 * @author CISEL Vincent <vcisel@eurodns.com>
 * @copyright Copyright (C) 2011 by domainAPI.com - EuroDNS S.A.  
 * @version Revision: 1.0.0
 * 
 * For the full copyright and license information,please view the LICENSE file
 * that was distributed with this source code.
 */
package com.domainapi.services;

import java.io.IOException;
import java.util.ArrayList;

import com.domainapi.DomainApiException;
import com.domainapi.util.Authenticator;
import com.domainapi.util.ParameterEntry;

/**
 * Abstract service class used for calling CRUD function from DomainApi.
 * 
 * @author CISEL Vincent <vcisel@eurodns.com>
 */
public abstract class Service {

	/**
	 * To log in to DomainApi.
	 */
	private Authenticator authentificator;
	/**
	 * DomainApi Connector.
	 */
	DomainApiConnector domainApiConnector;
	/**
	 * Domain name requested.
	 */
	private String domain;
	/**
	 * list of parameters.
	 */
	private ArrayList<ParameterEntry<String, String>> parameters;
	/**
	 * DTWS response
	 */
	private String response;
	/**
	 * HTTP response status
	 */
	private int status;
	/**
	 * Name of service
	 */
	private String name;

	/**
	 * Initialize attributes
	 * 
	 * @param auth
	 *            Auhtentificator object use to log into DomainApi.
	 * @param name
	 *            Name of service requested.
	 * @param dom
	 *            Domain name requested.
	 */
	public Service(Authenticator auth, String name, String dom) {
		authentificator = auth;
		this.name = name;
		domain = dom;
		parameters = new ArrayList<ParameterEntry<String, String>>();
		init();
	}

	/**
	 * To initialize DomainApiConnector
	 */
	private void init() {
		domainApiConnector = new DomainApiConnector(authentificator, name,
				domain);
	}

	/**
	 * To add parameter at the attribute's list. Several parameters can be have
	 * the same key.
	 * 
	 * @param key
	 *            name of parameter.
	 * @param value
	 *            value of parameter.
	 */
	public void addParameter(String key, String value) {
		parameters.add(new ParameterEntry<String, String>(key, value));
	}

	/**
	 * To execute HTTP request
	 * 
	 * @throws java.io.IOException
	 */
	private void execute() throws IOException {
		domainApiConnector.setParameters(parameters);
		try {
			response = domainApiConnector.execute();
		} catch (DomainApiException ex) {
			response = domainApiConnector.getError();
		}
		status = domainApiConnector.getResponseCode();
	}

	/**
	 * Service create execution
	 * 
	 * @throws java.io.IOException
	 *             HTTP error are throwed
	 */
	public void create() throws IOException {
		domainApiConnector.setHttpMethod(DomainApiConnector.POST);
		execute();
	}

	/**
	 * Service retrieve execution
	 * 
	 * @throws java.io.IOException
	 *             HTTP error are throwed
	 */
	public void retrieve() throws IOException {
		domainApiConnector.setHttpMethod(DomainApiConnector.GET);
		execute();
	}

	/**
	 * Service update execution
	 * 
	 * @throws java.io.IOException
	 *             HTTP error are throwed
	 */
	public void update() throws IOException {
		domainApiConnector.setHttpMethod(DomainApiConnector.PUT);
		execute();
	}

	/**
	 * Service delete execution
	 * 
	 * @throws java.io.IOException
	 *             HTTP error are throwed
	 */
	public void delete() throws IOException {
		domainApiConnector.setHttpMethod(DomainApiConnector.DELETE);
		execute();
	}

	/**
	 * To set return type of response (xml or json).
	 * 
	 * @param returnType
	 */
	public void setReturn(int returnType) {
		domainApiConnector.setReturn(returnType);
	}

	/**
	 * Getting response
	 * 
	 * @return
	 */
	public String getResponse() {
		return response;
	}

	/**
	 * Getting HTTP status
	 * 
	 * @return
	 */
	public int getStatus() {
		return status;
	}
}
