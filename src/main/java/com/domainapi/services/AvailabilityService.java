/**
 * AvailabilityService
 *
 * @author CISEL Vincent <vcisel@eurodns.com>
 * @copyright Copyright (C) 2011 by domainAPI.com - EuroDNS S.A.  
 * @version Revision: 1.0.0
 *
 * For the full copyright and license information,please view the LICENSE
 * file that was distributed with this source code.
 */
package com.domainapi.services;

import java.io.InputStream;

import com.domainapi.DomainApiException;
import com.domainapi.DomainApiReturn;
import com.domainapi.util.Authenticator;

/**
 * Availabity service class
 * @author CISEL Vincent <vcisel@eurodns.com>
 */
public class AvailabilityService extends Service {

    public AvailabilityService(Authenticator auth, String dom) {
        super(auth, "availability", dom);
    }

    /**
     * Method to get the stream of the service response
     * @return
     */
    public InputStream retrieveRT() {
        this.domainApiConnector.setHttpMethod(DomainApiConnector.GET);
        this.setReturn(DomainApiReturn.RT);
        try {
            return this.domainApiConnector.executeStreaming();
        } catch (DomainApiException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void setType(String typ) {
        addParameter("type", typ);
    }

    public void addRegion(String region) {
        addParameter("regions", region);
    }

    public void addTld(String tld) {
        addParameter("tlds", tld);
    }
}
