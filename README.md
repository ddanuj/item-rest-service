# item-rest-service
Java REST service that gets and posts item objects.

### REST request structure - 
- HTTP POST `/items` , request JSON body `{ "item":{ "id": 123, "timestamp": "2016-01-01T23:01:01.000Z" } }` returns `201 created`
- HTTP GET `/items` , returns the list of items POSTed in the last 2 seconds or the list of last 100 POSTed items, whichever greater. `[ {"item": {"id": 123, "timestamp": "2016-01-01T23:01:01.000Z"} },  {"item": {"id": 124, "timestamp": "2016-01-01T23:01:01.001Z"} },…]`


## Instructions to build and run the code
1. Clone this repository
2. Go to `itemservice` directory
3. Using maven, build the project - `mvn clean install`
4. Please use following JVM arguments while running the code to get optimal heap footprint
    - Use G1 garbage collector - `-XX:+UseG1GC`
    - Set appropriate upper limit to memory say 100 MB - `-Xmx100m`
    - Set appropriate step size to allocate more memory in case of increased load - `-Xms25m -XX:NewRatio=2`