# metro
## Steps to Run Code
1. Create package: mvn package Or use packaged jar file inside target directory
2. First run the spring boot application
`java -jar target/metro-0.0.1-SNAPSHOT.jar`
This will bring up a server on port 8080 by default.
3. To find paths run below command
`curl -X POST   http://localhost:8080/api/v1/train-path/   -H 'cache-control: no-cache'   -H 'content-type: application/json'   -H 'postman-token: a3ce4473-0292-42a2-e73d-7874c24945ad'   -d '{
    "sourceStationName": "Boon Lay",
    "destinationStationName": "Little India",
    "timeOfTravel": "2019-04-03T06:30",
    "json": false
}' | json_pp`

## Explanation
1. If parameter `json` is `true` the output is a json output with all the paths in sorted order by time. The json output can be use by some client app to display information to user based on his own criteria.
2. I have solved it for `Bonus` points as well. So constraints mentioned under `Bonus` section in question are handled.