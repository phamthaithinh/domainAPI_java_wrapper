/**
 * ThumbnailService
 * 
 * @author CISEL Vincent <vcisel@eurodns.com>
 * @copyright Copyright (C) 2011 by domainAPI.com - EuroDNS S.A. 
 * @version Revision: 1.0
 * 
 * For the full copyright and license information,please view the LICENSE file
 * that was distributed with this source code.
 */
package com.domainapi.services;

import com.domainapi.util.Authenticator;

/**
 * 
 * Thumbnail service class
 * 
 * @author CISEL Vincent <vcisel@eurodns.com>
 */
public class ThumbnailService extends Service {

	public ThumbnailService(Authenticator auth, String dom) {
		super(auth, "thumbnail", dom);
	}

	public void setSize(String size) {
		addParameter("size", size);
	}
}
