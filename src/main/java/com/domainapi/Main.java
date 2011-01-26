/**
 * Main
 *
 * @author Vincent CISEL <vcisel@eurodns.com>
 * @copyright Copyright (C) 2011 by domainAPI.com - EuroDNS S.A.  
 * @version Revision: 1.0.0
 *
 */
package com.domainapi;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidParameterException;

import com.domainapi.services.AvailabilityService;
import com.domainapi.services.DatacubeService;
import com.domainapi.services.InfoService;
import com.domainapi.services.IpLocationService;
import com.domainapi.services.MaketplaceService;
import com.domainapi.services.SearchEngineService;
import com.domainapi.services.Service;
import com.domainapi.services.ThumbnailService;
import com.domainapi.services.WhoisHistoricService;
import com.domainapi.services.WhoisService;
import com.domainapi.util.Authenticator;

/**
 * Sample class to use DomainAPI java library.
 * 
 * @author CISEL Vincent <vcisel@eurodns.com>
 */
public class Main {

	/**
	 * Main method to call DomainAPI with a command line
	 * 
	 * @param args
	 *            the command line arguments.
	 *            <ul>
	 *            <li>0 : login</li>
	 *            <li>1 : password</li>
	 *            <li>2 : domain name</li>
	 *            <li>3 : service name (availability, whois, historic_whois,
	 *            info, ip_location, marketplace, tld_knowlegdebase,
	 *            search_engine, thumbnail or all)</li>
	 *            <li></li>
	 *            </ul>
	 */
	public static void main(String[] args) {
		String service = "all";
		try {
			if (args.length > 3) {
				service = args[3] != null ? args[3] : "all";
				Authenticator authentificator = new Authenticator(args[0],
						args[1]);
				try {
					if (service.equals("availability") || service.equals("all")) {
						availability(authentificator, args);
					} else if (service.equals("whois") || service.equals("all")) {
						whois(authentificator, args);
					} else if (service.equals("historic_whois")
							|| service.equals("all")) {
						whoisHistoric(authentificator, args);
					} else if (service.equals("info") || service.equals("all")) {
						info(authentificator, args);
					} else if (service.equals("ip_location")
							|| service.equals("all")) {
						ipLocation(authentificator, args);
					} else if (service.equals("marketplace")
							|| service.equals("all")) {
						maketplace(authentificator, args);
					} else if (service.equals("tld_knowlegdebase")
							|| service.equals("all")) {
						tldKnowledgeBase(authentificator, args);
					} else if (service.equals("search_engine")
							|| service.equals("all")) {
						searchEngine(authentificator, args);
					} else if (service.equals("thumbnail")
							|| service.equals("all")) {
						thumbnail(authentificator, args);
					} else if (service.equals("availabilityRT")
							|| service.equals("all")) {
						availabilityRT(authentificator, args);
					} else {
						throw new InvalidParameterException("Error: service ["
								+ service + "] does not exist");
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			} else {
				if (args.length == 0
						|| (args.length == 1 && args[0].equals("-help"))) {
					System.err
							.println("\nUsage: java -jar domainapi.client-1.0.0 [login] [password] [domain] [service] [args...]");
					System.err.println("\t 1 - login (require)");
					System.err.println("\t 2 - password (require)");
					System.err.println("\t 3 - domain name (require)");
					System.err.println("\t 4 - service name (require)");
					System.err
							.println("\t\t  - availability\n\t\t  - whois\n\t\t  - historic_whois\n\t\t"
									+ "  - info\n\t\t  - ip_location\n\t\t  - marketplace\n\t\t"
									+ "  - tld_knowlegdebase\n\t\t  - search_engine\n\t\t  - thumbnail\n\t\t"
									+ "  - or all");

					System.err
							.println("\t 5-n - service parameters <paramname>=<paramvalue> (optional, see: http://domainapi.com/documentation.html)");
					System.err
							.println("\nExample: java -jar domainapi.client-1.0.0 your_login your_password example.com availability regions=gen");
					System.err.println("\n");
				} else {
					throw new InvalidParameterException(
							"Error: Not enough argument");
				}
			}
		} catch (InvalidParameterException e) {
			System.err.println(e.getMessage() + "\n");
			System.err.println("use -help for a list of possible options\n");

		}
	}

	private static void availability(Authenticator authentificator,
			String[] args) throws IOException {
		AvailabilityService availabilityService = new AvailabilityService(
				authentificator, args[2]);
		setParams(availabilityService, args);
		callAndPrint(availabilityService);
	}

	private static void availabilityRT(Authenticator authentificator,
			String[] args) throws IOException {
		AvailabilityService availabilityService = new AvailabilityService(
				authentificator, args[2]);
		setParams(availabilityService, args);
		int nextChar;
		InputStream in = availabilityService.retrieveRT();
		while ((nextChar = in.read()) != -1) {
			System.out.print((char) nextChar);
		}
		System.out.println('\n');
		in.close();

	}

	private static void info(Authenticator authentificator, String[] args)
			throws IOException {
		InfoService infoService = new InfoService(authentificator, args[2]);
		setParams(infoService, args);
		callAndPrint(infoService);
	}

	private static void thumbnail(Authenticator authentificator, String[] args)
			throws IOException {
		ThumbnailService thumbnailService = new ThumbnailService(
				authentificator, args[2]);
		setParams(thumbnailService, args);
		callAndPrint(thumbnailService);
	}

	private static void tldKnowledgeBase(Authenticator authentificator,
			String[] args) throws IOException {
		DatacubeService datacubeService = new DatacubeService(authentificator,
				args[2]);
		setParams(datacubeService, args);
		callAndPrint(datacubeService);
	}

	private static void whois(Authenticator authentificator, String[] args)
			throws IOException {
		WhoisService whoisServiceService = new WhoisService(authentificator,
				args[2]);
		setParams(whoisServiceService, args);
		callAndPrint(whoisServiceService);
	}

	private static void whoisHistoric(Authenticator authentificator,
			String[] args) throws IOException {
		WhoisHistoricService whoisHistoricService = new WhoisHistoricService(
				authentificator, args[2]);
		setParams(whoisHistoricService, args);
		callAndPrint(whoisHistoricService);
	}

	private static void ipLocation(Authenticator authentificator, String[] args)
			throws IOException {
		IpLocationService ipLocationService = new IpLocationService(
				authentificator, args[2]);
		setParams(ipLocationService, args);
		callAndPrint(ipLocationService);
	}

	private static void maketplace(Authenticator authentificator, String[] args)
			throws IOException {
		MaketplaceService maketplaceService = new MaketplaceService(
				authentificator, args[2]);
		setParams(maketplaceService, args);
		callAndPrint(maketplaceService);
	}

	private static void searchEngine(Authenticator authentificator,
			String[] args) throws IOException {
		SearchEngineService searchEngineService = new SearchEngineService(
				authentificator, args[2]);
		setParams(searchEngineService, args);
		callAndPrint(searchEngineService);
	}

	private static void setParams(Service service, String[] args) {
		if (args.length > 4) {
			for (int i = 4; i < args.length; i++) {
				String[] split = args[i].split("=");
				if (split.length > 1) {
					if (split[0].equals("returnType")
							&& split[1].toLowerCase().equals("xml")) {
						service.setReturn(DomainApiReturn.XML);
					} else if (split[0].equals("returnType")
							&& split[1].toLowerCase().equals("json")) {
						service.setReturn(DomainApiReturn.JSON);
					} else {
						service.addParameter(split[0], split[1]);
					}
				} else {
					throw new InvalidParameterException("Error: parameter ["
							+ args[i] + "] is malformed");
				}
			}
		}
	}

	private static void callAndPrint(Service service) throws IOException {
		service.retrieve();
		System.out.println(service.getStatus());
		System.out.println(service.getResponse());
	}

}
