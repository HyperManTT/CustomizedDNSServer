import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class DnsServerImpl extends UnicastRemoteObject implements DnsServer
{
    private HashMap<String, String> cache = new HashMap<>(); //We use a hashmap as a cache
    public static RootServer root_server = null;
    public static TLDNameserver tld_nameserver = null;
    public static AuthNameserver auth_nameserver = null;

    public DnsServerImpl() throws RemoteException
    {
        super();
    }

    @Override
    public String resolveURL(String url) throws RemoteException {
        /*
        This method attempts to resolve the URL in two ways
        - It first checks the cache to see if the URL exists, if it does we simply
          return the http_server name from here.

        - If the URL isn't in cache, we query the root server for the name of the
          top-level domain server. When we get this name, we then query the TLD for
          the name of the authoritative server (AS) that handles the URL. When we get
          this name, we query the AS directly to get the name of the http_server. We
          then add the URL to cache to quickly return the http_server if asked for it again.
         */
        System.out.println("");
        String extractedUrl = getUrl(url);

        if(extractedUrl.equals("Error"))
        {
           return "Error - Cannot parse url. Please ensure that the TLD is .com";
        }

        //Check cache for stored url
        for (String keys : cache.keySet()) {
            if(keys.equals(extractedUrl))
            {
                System.out.println("Entry found in cache! No need to query Root server.");
                return replaceURL(url, cache.get(extractedUrl));
            }
        }
        System.out.println("URL not in cache... Querying Root Server");

        try {
            //Get reference to root server
            root_server = (RootServer) Naming.lookup(RootServer.LOOKUPNAME);
            String tld_name = root_server.getTLD(url);  //Get TLD name from root server

            if(tld_name.equals("Error"))
            {
                return "Error - Top Level Domain Server Cannot be found. Please ensure URL has uses .com";
            }

            System.out.println("TLD Server found...\nQuerying TLD Server");
            tld_nameserver = (TLDNameserver) Naming.lookup(tld_name);  //Get reference to TLD server
            String auth_name = tld_nameserver.getAuthNameServer(extractedUrl); // Get nameserver name

            if(auth_name.equals("Error"))
            {
                return "Error - Authoritative Nameserver for URL cannot be found.";
            }

            auth_nameserver = (AuthNameserver) Naming.lookup(auth_name); // Get reference to Auth Nameserver
            String http_servername = auth_nameserver.getHTTPServer(extractedUrl); //Get the object name
            if(http_servername.equals("Error"))
            {
                return "Error - Cannot get an HTTP Server for the provided URL.";
            }

            System.out.println("URL found in authoritative name server. Adding result to cache...");
            cache.put(extractedUrl, http_servername);
            return replaceURL(url, http_servername);
        }
        catch (Exception e)
        {
            System.err.println(e);
            e.printStackTrace();
            return null;
        }

    }

    private String getUrl(String url)
    {
        /*
        Returns the URL without the parameters/queries
         */
        String[] urlSplit = url.split("/");
        for (String str1 : urlSplit)
        {
            if(str1.contains(".com"))
            {
                return str1;
            }
        }

        return "Error";
    }

    private String replaceURL(String url, String http_servername)
    {
        /*
        This method is called when returning the information to the client.
        It replaces the domain name passed in with the name of the http_server
         */
        String searchString = ".com";
        int index = url.indexOf(searchString);
        String paramValues = url.substring(index + searchString.length());
        return "http://" + http_servername + paramValues;
    }
}