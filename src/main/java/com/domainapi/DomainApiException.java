/**
 * DomainApiException
 * 
 * @author CISEL Vincent <vcisel@eurodns.com>
 * @copyright Copyright (C) 2011 by domainAPI.com - EuroDNS S.A.  
 * @version Revision: 1.0.0
 * 
 * For the full copyright and license information,please view the LICENSE file
 * that was distributed with this source code.
 */
package com.domainapi;

/**
 * DomainAPI Connection Exception
 * @author CISEL Vincent <vcisel@eurodns.com>
 */
public class DomainApiException extends Exception {

    /**
	 * serial version uid
	 */
	private static final long serialVersionUID = -3187529177139345295L;

	public DomainApiException(String message) {
        super(message);
    }

    public DomainApiException(Throwable cause) {
        super(cause);
    }

    public DomainApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
