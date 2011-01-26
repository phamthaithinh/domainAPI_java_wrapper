/**
 * Authentificator
 * 
 * @author CISEL Vincent <vcisel@eurodns.com>
 * @copyright Copyright (C) 2011 by domainAPI.com - EuroDNS S.A. 
 * @version Revision: 1.0
 * 
 * For the full copyright and license information,please view the LICENSE file
 * that was distributed with this source code.
 */
package com.domainapi.util;

import org.apache.commons.codec.binary.Base64;

/**
 * Authentificator is use to log in an user.
 * 
 * @author CISEL Vincent <vcisel@eurodns.com>
 */
public class Authenticator {

	/**
	 * login of user.
	 */
	private String login;
	/**
	 * password of user.
	 */
	private String password;

	/**
	 * Initialize attributes.
	 * 
	 * @param login
	 *            login of user.
	 * @param pass
	 *            password of user.
	 */
	public Authenticator(String login, String pass) {
		this.login = login;
		password = pass;
	}

	/**
	 * Generate basic authorization string for HTTP authentification.
	 * 
	 * @return basic authorization in base64.
	 */
	public String generateBasicAuthorization() {
		byte[] encodedPassword = (login + ":" + password).getBytes();
		return "Basic " + new String(Base64.encodeBase64(encodedPassword));
	}
}
