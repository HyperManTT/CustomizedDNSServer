import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class HTTPServerImpl extends UnicastRemoteObject implements HTTPServer
{
    public HTTPServerImpl() throws RemoteException
    {
        super();
    }

    public String Get(String url) throws  RemoteException
    {
        //TODO Parse URL from Client
        return url_parser(url);
    }

    //http://www.httpServer.com/addNum?param=2&param2=3
    private String url_parser(String url)
    {
        /*
        Parses the URL passed in from the user and extracts the method names
        and the parameter values
         */
        String[] urlSplit = url.split("/");
        String methodName = "";
        ArrayList<String> paramVals = new ArrayList<String>();
        for (String urlExtract : urlSplit)
        {
            if(urlExtract.contains("?")) {
                String methodSplit[] = urlExtract.split("[?]");
                methodName = methodSplit[0];
                String[] paramSplit = methodSplit[1].split("&");
                for (String params: paramSplit)
                {
                    paramVals.add(params.split("=")[1]);
                }
            }
        }
        String result = runRelevantMethod(methodName, paramVals);
        return result;
    }

    private String runRelevantMethod(String methodName, ArrayList<String> paramVals)
    {
        /*
        Compares the method passed in from the user to the ones the http server can
        run. If it is found, te method is called and the parameters passed to it.
         */
        //System.out.println(methodName);
        if(methodName.equals("addNums"))
        {
           return addNum(paramVals);
        }

        else if(methodName.equals("multiplyNums"))
        {
            return multiplyNums(paramVals);
        }

        return "Error - Method not found.";
    }

    private String addNum(ArrayList<String> nums)
    {
        /*
        Method to add numbers passed in from the user
         */
        int sum = 0;
        for (String each_num : nums)
        {
            sum += Integer.parseInt(each_num);
        }

        return "Sum: " + Integer.toString(sum);
    }

    private String multiplyNums(ArrayList<String> nums)
    {
        /*
        Method to multiply numbers passed in from the user
         */
        int multiplicationSum = 1;
        for (String each_num : nums)
        {
            multiplicationSum *= Integer.parseInt(each_num);
        }

        return "Product: " + Integer.toString(multiplicationSum);
    }


}