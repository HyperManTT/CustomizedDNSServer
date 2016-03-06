import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class AuthNameserverImpl extends UnicastRemoteObject implements AuthNameserver {

    private HashMap<String, String> http_mapping = new HashMap<>();
    public AuthNameserverImpl() throws RemoteException {
        super();
        http_mapping.put("www.test.com", HTTPServer.LOOKUPNAME);
        http_mapping.put("www.randy.com", HTTPServer.LOOKUPNAME);
        http_mapping.put("www.distributed.com", HTTPServer.LOOKUPNAME);
    }

    @Override
    public String getHTTPServer(String url) throws RemoteException {
        for (String keys : http_mapping.keySet())
        {
            if (keys.equals(url))
            {
                return http_mapping.get(keys);
            }
        }
        return "Error";
    }
}