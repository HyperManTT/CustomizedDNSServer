########################################################
#	                  Prerequisites                    #
########################################################
- JRE and JDK is installed and their paths added the Path environment variable.


########################################################
#		               Usage                           #
########################################################
- Open 3 separate Command Prompts and navigate to the program source directory
- In window 1, compile all java classes using the command "javac *.java"
- In window 2, run the command 'rmiregistry' to start the registry service
- In window 3, start the main server using the command 'java MainServer'

In Window 1 you can now type the URL that you want to navigate to
- The command to type in Window 1 is 'java Client "INSERT_URL" 'where INSERT_URL is in the format
  shown in the examples below. N.B. - URL must be enclosed in quotes.

  e.g. java Client "http://www.distributed.com/addNums?para1=20&param2=30&param3=50"

Supported URLs:
- http://www.test.com
- http://www.randy.com
- http://www.distributed.com

Methods that you can run:
- addNums
- multiplyNums

Example URL Format:
http://www.test.com/multiplyNums?para1=10&param2=20&param3=15
http://www.distributed.com/addNums?para1=20&param2=30&param3=50

 - Debug messages can be seen in Window 3


########################################################
#		          Program Description                  #
########################################################

- The HTTP Server, DNS Server, Root Server, TLD NameServer and Authoritative NameServer each export a public interface and has
   a LOOKUP name associated with them.
- Each of these servers have their interfaces implemented in an associated implementation file.

***************
* Main Server *
***************

- The MainServer registers the identifiers of each of the remote objects listed above (the DNS servers etc.)

***************
*    Client   *
***************
- The Client accepts one parameter - the URL to be resolved
- It knows its default DNS Server's name so it tries to resolve the name on the URL to a remote HTTP object by calling
  the resolveURL function advertised by the DNS server.
- It parses the response from the DNS server, if the DNS Server could not resolve the name, an error message is displayed
- If the DNS server successfully resolved the name of the remote HTTP object, it's GET method is called to run the
  command specified in the URL.

***************
*  DNS Server *
***************
- The DNS server accepts a URL and attempts to resolve it in two ways
- First it checks it's local cache, if it finds the URL there, it immediately returns the information to the client.
- If the URL was not in cache, the DNS server asks the Root Nameserver for the name of the TLD that handles the domain
  in the URL.
- If it gets a valid name, the DNS server then asks the top level domain server for the name of the
  authoritative name server that can handle the requested URL.
- If it gets a valid Authoritative Name Server, the DNS server then asks for the remote http object name.
- If it gets a valid remote http object name, it returns this to the user and caches the result.
- If any of the above checks fail, an error message is printed.

***************
* HTTP Server *
***************
- The HTTP server accepts a URL and parses it - extracting the method name and the list of parameters.
- It then looks up the method name and calls the required method, passing the arguments to the method
- The results of the method are then displayed to the user.



########################################################
#		          Program Description &                #
#		          comparison to DNS                    #
########################################################

The program involves the use of Java RMI to simulate an HTTP client server
interaction with a custom DNS server. This is similar in operation to a regular
DNS server but instead of returning an IP address corresponding to a host name,
the customised DNS server returns an object name. Both follow a recursive resolver
approach where results from each server query are returned to the DNS server and used
to query other servers until a result or error is obtained.

The program operates by a user first passing in a URL containing a method name and parameters. 
To find the actual HTTP server that this URL is referring to, the client performs a
DNS query to a known DNS server. This is similar to a DNS query your browser does when trying to
resolve a URL to IP address.

When the DNS server gets the URL, it performs the same steps as a regular DNS server.
It checks its cache to see if it has a mapping of URL to object name (instead of IP
address). If it is found in cache, it returns this object name to the user. If it isn't
found in cache, the DNS server, which knows the name of its root server, queries it
for the name of the top level domain server for the URL (.com in this case). 

The root server has a top level domain (TLD) server name for the .com domain and 
returns this name to the DNS server. The DNS server then uses this name to query 
the TLD server for the nameserver that handles the requested URL. If the TLD knows 
the nameserver for the requested URL, it returns this name to the DNS server, if not
it returns an error.

If a valid name was returned from the TLD for the nameserver, the DNS server then
queries this server for the object name corresponding to the URL passed in by the client.
If the nameserver knows the object name for the URL, it returns it to the client.

If the DNS server receives an object name from the nameserver, it stores this in its cache,
mapping the URL to the object name and returns the object name to the client. This is
similar to how the browser receives the IP address from the DNS server in regular DNS
operation. In our case, the client uses the object name to instantiate a remote
object where it can pass in the parameters to the method specified by the user in
the URL and return a result.

########################################################
#		          Limitations &	          		       #
#		          Suggested Improvements 	           #
########################################################
Below are some of the limitations and suggested improvements that can be made
to the current implementation

- Implement more stringent error checking and better debug messages.
- Add more methods to the HTTP server to facilitate more calculations.
- Implement a second HTTP server to ensure DNS resolving is working properly.