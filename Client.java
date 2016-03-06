import java.rmi.Naming;

public class Client
{
    public static HTTPServer http_server = null;
    public static DnsServer dns_server = null;

    public static void main(String[] args)
    {
        try
        {
            //The client only knows the name of the DNS server and uses this to resolve all URLS
            dns_server = (DnsServer)Naming.lookup(DnsServer.LOOKUPNAME);
            String url = dns_server.resolveURL(args[0]);
            if(url.startsWith("Error"))
            {
                System.out.println(url);
                System.exit(-1);
            }

            //We perform some string manipulation here to retrieve the http_server name
            String firstString = "http://";
            String secondString = "/";
            int firstIndex = url.indexOf(firstString);
            int secondIndex = url.indexOf(secondString, firstString.length());
            String http_servername = url.substring(firstIndex + firstString.length(), secondIndex);
            http_server = (HTTPServer)Naming.lookup(http_servername);
            String output = http_server.Get(url);  //Call the get method to run the specified method
            if(output.startsWith("Error"))
            {
                System.out.println(output);
                System.exit(-1);
            }
            System.out.println(output);
        }
        catch(Exception e)
        {
            System.err.println(e);
            e.printStackTrace();
        }
    }


}