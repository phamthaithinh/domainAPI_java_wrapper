/**
 * SearchEngineService
 *
 * @author CISEL Vincent <vcisel@eurodns.com>
 * @copyright Copyright (C) 2011 by domainAPI.com - EuroDNS S.A. 
 * @version Revision: 1.0.0
 * 
 * For the full copyright and license information,please view the LICENSE file
 * that was distributed with this source code.
 */
package com.domainapi.services;

import com.domainapi.util.Authenticator;

public class SearchEngineService extends Service {
	/**
	 * Search engine service class
	 * 
	 * @author CISEL Vincent <vcisel@eurodns.com>
	 * 
	 */
	public SearchEngineService(Authenticator auth, String name) {
		super(auth, "search_engine", name);
	}

}
