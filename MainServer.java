import java.rmi.Naming;

public class MainServer
{
    public static void main(String[] args)
    {
        try
        {
            //Declare all the implementations of the servers to be used
            HTTPServerImpl http = new HTTPServerImpl();
            DnsServerImpl dns = new DnsServerImpl();
            RootServerImpl root = new RootServerImpl();
            TLDNameserverImpl tld = new TLDNameserverImpl();
            AuthNameserverImpl auth = new AuthNameserverImpl();


            //Add the names of all the servers to the registry.
            System.out.println("HTTP Server Starting...");
            Naming.rebind(HTTPServerImpl.LOOKUPNAME, http);

            System.out.println("DNS Server Starting...");
            Naming.rebind(DnsServerImpl.LOOKUPNAME, dns);

            System.out.println("Root Server Starting...");
            Naming.rebind(RootServerImpl.LOOKUPNAME, root);

            System.out.println("TLD NameServer Starting...");
            Naming.rebind(TLDNameserverImpl.LOOKUPNAME, tld);

            System.out.println("Authoritative Name Server Starting...");
            Naming.rebind(AuthNameserverImpl.LOOKUPNAME, auth);

            System.out.println("All Components started successfully!");
        }
        catch (Exception e)
        {
            System.err.println(e);
            System.exit(1);
        }
    }
}