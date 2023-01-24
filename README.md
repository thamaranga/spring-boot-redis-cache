In this example I am demonstrating how we can 
use redis as a cache.

In this application we are storing data in a mysql db.

For using redis , first we need to install redis.
https://github.com/microsoftarchive/redis/releases  link contains 
a  redis portal distribution.
After unzipping this into a folder we need to start redis server.
(Just double click redis-server)


When we restart the application redis cache will not be cleared.
When we restart the redis server redis cache will be cleared.

We can execute redis commands in the redis cli.(Goto redis 
installation directory and double-click the redis-cli command)

Below are few important redis commands.

KEYS * -> Get all available keys (below is a sample output of above command)

1) "customer::1"
2) "customers::SimpleKey []"
3) "customer::4"


get "customer::1" -> Get  specific key from the cache

flushall -> Clear all keys in the cache

del "customer::1" -> delete specific key from cache

clear -> clear command line output


