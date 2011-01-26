/**
 * DomainApiConnector
 * 
 * @author CISEL Vincent <vcisel@eurodns.com>
 * @copyright Copyright (C) 2011 by domainAPI.com - EuroDNS S.A.  
 * @version Revision: 1.0.0
 * 
 * For the full copyright and license information,please view the LICENSE file
 * that was distributed with this source code.
 */
package com.domainapi.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;

import com.domainapi.DomainApiException;
import com.domainapi.DomainApiReturn;
import com.domainapi.util.Authenticator;
import com.domainapi.util.ParameterEntry;

/**
 * Connector for making connection to DomainAPI.
 * 
 * @author CISEL Vincent <vcisel@eurodns.com>
 */
public class DomainApiConnector {

    /**
     * HTTP GET method.
     */
    public final static int GET = 0;
    /**
     * HTTP POST method.
     */
    public final static int POST = 1;
    /**
     * HTTP PUT method.
     */
    public final static int PUT = 2;
    /**
     * HTTP DELETE method.
     */
    public final static int DELETE = 3;
    private final static String SCHEME = "http://";
    private final static String HOST = "api.domainapi.com";
    private final static int PORT = 80;
    private final static String PATH = "/v1";
    /**
     * URL of DTWS.
     */
    private final static String URL = SCHEME + HOST + ":" + PORT;
    /**
     * To log in to DTWS.
     */
    private Authenticator authentificator;
    /**
     * list of parameters.
     */
    private ArrayList<ParameterEntry<String, String>> parameters;
    /**
     * method of HTTP request (GET,POST,PUT,DELETE).
     */
    private int method;
    /**
     * Name of service requested.
     */
    private String service;
    /**
     * Domain name requested.
     */
    private String domain;
    /**
     * Type of response (xml, json, rt).
     */
    private String returnType;
    /**
     * HttpURLConnection object used to do HTTP connection.
     */
    private HttpURLConnection httpConnection;

    /**
     * Initialize parameters.
     * 
     * @param auth
     *            Auhtentificator object use to log in to DTWS.
     * @param service
     *            Name of service requested.
     * @param domain
     *            Domain name requested.
     */
    public DomainApiConnector(Authenticator auth, String service, String domain) {
        authentificator = auth;
        this.service = service;
        this.domain = domain;
        parameters = new ArrayList<ParameterEntry<String, String>>();
        returnType = "xml";
    }

    /**
     * To set return type of response (xml or json).
     * 
     * @param returnType
     */
    public void setReturn(int returnType) {
        switch (returnType) {
        case DomainApiReturn.JSON:
            this.returnType = "json";
            break;
        case DomainApiReturn.XML:
            this.returnType = "xml";
            break;
        case DomainApiReturn.RT:
            this.returnType = "rt";
            break;
        }
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
     * To set list of parameters.
     * 
     * @param params
     *            parameters.
     */
    public void setParameters(ArrayList<ParameterEntry<String, String>> params) {
        parameters = params;
    }

    /**
     * To set HTTP method (GET, POST, PUT or DELETE).
     * 
     * @param httpMethod
     */
    public void setHttpMethod(int httpMethod) {
        method = httpMethod;
    }

    /**
     * To build query.
     * 
     * @return building response
     */
    public String buildBody() {
        StringBuffer body = new StringBuffer("{\"parameters\":[{");
        for (ParameterEntry<String, String> entry : parameters) {
            body.append("\"" + entry.getKey() + "\"" + ":" + "\""
                    + entry.getValue() + "\",");
        }
        body.append("}]}");
        return body.toString();
    }

    /**
     * To execute HTTP request. Write on the output stream and read the input
     * stream.
     * 
     * @return string result of HTTP request.
     * @throws DomainApiException.DTWSException
     *             if an error are throwed during HTTP connection.
     */
    public String execute() throws DomainApiException {
        try {
            initConnection();
            if (method == POST || method == PUT) {
                sendHttpRequest(buildBody());
            }
            return readHttpResponse();
        } catch (MalformedURLException ex) {
            throw new DomainApiException(ex);
        } catch (IOException ex) {
            throw new DomainApiException(ex);
        }
    }

    /**
     * To execute HTTP request. Write on the output stream and read the input
     * 
     * @return input stream of HTTP response.
     * @throws DomainApiException.DTWSException
     *             if an error are throwed during HTTP connection.
     */
    public InputStream executeStreaming() throws DomainApiException {
        try {
            Socket socket = new Socket(HOST, PORT);
            try {
                StringBuffer sb = new StringBuffer();
                sb.append("GET " + initPathParameter() + "\n");
                sb.append("Content-Type: application/json\n");
                sb.append("Authorization: "
                        + authentificator.generateBasicAuthorization() + "\n\n");
                socket.getOutputStream().write(sb.toString().getBytes());
            } catch (IOException ex) {
                throw new DomainApiException(ex);
            }
            return socket.getInputStream();
        } catch (MalformedURLException ex) {
            throw new DomainApiException(ex);
        } catch (IOException ex) {
            throw new DomainApiException(ex);
        }
    }

    /**
     * Getting error result of DTWS.
     * 
     * @return error result.
     * @throws java.io.IOException
     */
    public String getError() throws IOException {
        String line = "";
        StringBuffer buffer = new StringBuffer();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                httpConnection.getErrorStream()));
        while ((line = in.readLine()) != null) {
            buffer.append(line + "\n");
        }
        in.close();
        return buffer.toString();
    }

    /**
     * HTTP status code.
     * 
     * @return HTTP status.
     * @throws java.io.IOException
     */
    public int getResponseCode() throws IOException {
        return httpConnection.getResponseCode();
    }

    /**
     * To format URL with or without parameters, if GET or DELETE method is
     * used.
     * 
     * @return URL formatted.
     */
    private String initUrlParameter() {
        StringBuffer url = new StringBuffer();
        url.append(URL).append(initPathParameter());
        return url.toString();
    }

    private String initPathParameter() {
        StringBuffer path = new StringBuffer();
        StringBuffer slash = new StringBuffer("/");
        path.append(PATH).append(slash).append(service).append(slash)
                .append(returnType).append(slash).append(domain);
        if (method == GET || method == DELETE) {
            StringBuffer toAppend = new StringBuffer();
            for (ParameterEntry<String, String> entry : parameters) {
                if (toAppend.length() == 0) {
                    toAppend.append("?");
                } else {
                    toAppend.append("&");
                }
                toAppend.append(entry.getKey());
                toAppend.append("=");
                toAppend.append(entry.getValue());
            }
            path.append(toAppend);
        }
        return path.toString();
    }

    /**
     * Write on output steam.
     * 
     * @param body
     *            data to write.
     * @throws java.io.IOException
     *             error during writting.
     */
    private void sendHttpRequest(String body) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(
                httpConnection.getOutputStream());
        writer.write(body);
        writer.flush();
        writer.close();
    }

    /**
     * Read input stream.
     * 
     * @return response
     * @throws java.io.IOException
     */
    private String readHttpResponse() throws IOException {
        String line = "";
        StringBuffer buffer = new StringBuffer();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                httpConnection.getInputStream()));
        while ((line = in.readLine()) != null) {
            buffer.append(line + "\n");
        }
        in.close();
        return buffer.toString();
    }

    /**
     * Initialize connection, set header parameters and HTTP method.
     * 
     * @throws java.net.MalformedURLException
     *             if URL is malformed.
     * @throws java.io.IOException
     *             if connection can not be create.
     */
    private void initConnection() throws MalformedURLException, IOException {
        httpConnection = (HttpURLConnection) new URL(initUrlParameter())
                .openConnection();
        httpConnection.setDoOutput(true);
        httpConnection.setDoInput(true);
        httpConnection.setRequestMethod("GET");
        httpConnection.setRequestProperty("Content-Type", "application/json");
        httpConnection.setRequestProperty("Authorization",
                authentificator.generateBasicAuthorization());

    }
}
