/**
 * InfoService
 * 
 * @author CISEL Vincent <vcisel@eurodns.com>
 * @copyright Copyright (C) 2011 by domainAPI.com - EuroDNS S.A.  
 * @version Revision: 1.0.0
 *
 * For the full copyright and license information,please view the LICENSE
 * file that was distributed with this source code.
 */
package com.domainapi.services;

import com.domainapi.util.Authenticator;

/**
 * Info service class
 * 
 * @author CISEL Vincent <vcisel@eurodns.com>
 */
public class InfoService extends Service {

	public InfoService(Authenticator auth, String dom) {
		super(auth, "info", dom);
	}

	public void setAction(String act) {
		addParameter("Action", act);
	}

	public void setSize(String size) {
		addParameter("Size", size);
	}
}
