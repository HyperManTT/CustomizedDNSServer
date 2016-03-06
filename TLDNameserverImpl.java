import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class TLDNameserverImpl extends UnicastRemoteObject implements TLDNameserver {
    private HashMap<String, String> auth_mapping = new HashMap<>();

    public TLDNameserverImpl() throws RemoteException {
        super();
        //Map URL's to the authoritative name server
        auth_mapping.put("www.test.com", AuthNameserver.LOOKUPNAME);
        auth_mapping.put("www.randy.com", AuthNameserver.LOOKUPNAME);
        auth_mapping.put("www.distributed.com", AuthNameserver.LOOKUPNAME);
    }

    @Override
    public String getAuthNameServer(String url) throws RemoteException {
        /*
        Given a URL, we search through the mapping that's stored at the
        server and return the name of the authoritative server that
        handles this URL
         */
        for (String each_url : auth_mapping.keySet())
        {
            if (url.equals(each_url))
            {
                return auth_mapping.get(each_url);
            }
        }
        return "Error";
    }
}

