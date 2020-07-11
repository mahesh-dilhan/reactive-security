# Reactive Spring Security
Security + Reactive + JWT 


# Kafka Consumer
Examples in this repo:

 * Basic Spring Security
 * JWT token based Spring security 


### Dependency 
Setup reactive-stream repo

## Setup & build

OpenJDK 13 (let jenv to manage multiple JDKs )

Run `mvn package` to build a single executable JAR file.

### Basic Spring Security - PERMIT ALL

Access endpoint `greet/gs` and `person/v2`

```
Maheshs-MBP:rsocket mahesh$ http :8080/greet/gs
HTTP/1.1 200 OK
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Content-Length: 19
Content-Type: text/plain;charset=UTF-8
Expires: 0
Pragma: no-cache
Referrer-Policy: no-referrer
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
X-Content-Type-Options: nosniff
X-Frame-Options: DENY
X-XSS-Protection: 1 ; mode=block

Hello from service!

```
```
Maheshs-MBP:rsocket mahesh$ http :8080/person/v2
HTTP/1.1 200 OK
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Content-Length: 5
Content-Type: text/plain;charset=UTF-8
Expires: 0
Pragma: no-cache
Referrer-Policy: no-referrer
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
X-Content-Type-Options: nosniff
X-Frame-Options: DENY
X-XSS-Protection: 1 ; mode=block

Hello

```

### Basic Spring Security - Add Path level restriction 
Lets restric path `person/**` and permit `greet/**`

Ater changing the code `JwtAuthenticationApplication`
Run `mvn package` to build a single executable JAR file.

Lets access same endpoints again

```
 Maheshs-MBP:rsocket mahesh$ http :8080/greet/gs
 HTTP/1.1 200 OK
 Cache-Control: no-cache, no-store, max-age=0, must-revalidate
 Content-Length: 19
 Content-Type: text/plain;charset=UTF-8
 Expires: 0
 Pragma: no-cache
 Referrer-Policy: no-referrer
 Vary: Origin
 Vary: Access-Control-Request-Method
 Vary: Access-Control-Request-Headers
 X-Content-Type-Options: nosniff
 X-Frame-Options: DENY
 X-XSS-Protection: 1 ; mode=block
 
 Hello from service!
 
 Maheshs-MBP:rsocket mahesh$ http :8080/person/v2
 HTTP/1.1 401 Unauthorized
 Cache-Control: no-cache, no-store, max-age=0, must-revalidate
 Expires: 0
 Pragma: no-cache
 Referrer-Policy: no-referrer
 WWW-Authenticate: Basic realm="Realm"
 X-Content-Type-Options: nosniff
 X-Frame-Options: DENY
 X-XSS-Protection: 1 ; mode=block
 content-length: 0
 
 ```
Lets pass username/password and access same endpoint which prohibited 
```
 
 Maheshs-MBP:rsocket mahesh$ http :8080/person/v2 -a mahesh:pwd
 HTTP/1.1 200 OK
 Cache-Control: no-cache, no-store, max-age=0, must-revalidate
 Content-Length: 5
 Content-Type: text/plain;charset=UTF-8
 Expires: 0
 Pragma: no-cache
 Referrer-Policy: no-referrer
 Vary: Origin
 Vary: Access-Control-Request-Method
 Vary: Access-Control-Request-Headers
 X-Content-Type-Options: nosniff
 X-Frame-Options: DENY
 X-XSS-Protection: 1 ; mode=block
 
 Hello

```

### Enable JWT 
Lets enable the code segement for JWT and Run the application 

Lets pass username/password and access same endpoint which we able to access in previous  

```
Maheshs-MBP:rsocket mahesh$ http :8080/person/v2 -a mahesh:pwd
HTTP/1.1 401 Unauthorized
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Expires: 0
Pragma: no-cache
Referrer-Policy: no-referrer
WWW-Authenticate: Basic realm="Realm"
X-Content-Type-Options: nosniff
X-Frame-Options: DENY
X-XSS-Protection: 1 ; mode=block
content-length: 0
```

Lets get the bearer token 

```
Maheshs-MBP:rsocket mahesh$ echo '{"username":"mahesh","password":"pwd"}' | http :8080/auth/token
HTTP/1.1 200 OK
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYWhlc2giLCJyb2xlcyI6IlJPTEVfQURNSU4sUk9MRV9VU0VSIiwiaWF0IjoxNTk0NDQ5Mzk0LCJleHAiOjE1OTQ0NTI5OTR9.J4l5YE9HgNlfg_RbiOR577L-H3KNKyzp8OEKykBd4_4
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Content-Length: 188
Content-Type: application/json
Expires: 0
Pragma: no-cache
Referrer-Policy: no-referrer
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
X-Content-Type-Options: nosniff
X-Frame-Options: DENY
X-XSS-Protection: 1 ; mode=block

{
    "id_token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYWhlc2giLCJyb2xlcyI6IlJPTEVfQURNSU4sUk9MRV9VU0VSIiwiaWF0IjoxNTk0NDQ5Mzk0LCJleHAiOjE1OTQ0NTI5OTR9.J4l5YE9HgNlfg_RbiOR577L-H3KNKyzp8OEKykBd4_4"
}

```

When using `httpie` you can also use `http POST :8080/auth/token 'username:shawn' 'password=pwd'`
```
Maheshs-MBP:rsocket mahesh$ http POST :8080/auth/token 'username=shawn' 'password=pwd'
HTTP/1.1 200 OK
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzaGF3biIsInJvbGVzIjoiUk9MRV9VU0VSIiwiaWF0IjoxNTk0NDQ5NjY0LCJleHAiOjE1OTQ0NTMyNjR9._AswTh2f5TfZHarfNO-WI9kF8eD0GNWKJ8lrbN2hqGA
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Content-Length: 172
Content-Type: application/json
Expires: 0
Pragma: no-cache
Referrer-Policy: no-referrer
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
X-Content-Type-Options: nosniff
X-Frame-Options: DENY
X-XSS-Protection: 1 ; mode=block

{
    "id_token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzaGF3biIsInJvbGVzIjoiUk9MRV9VU0VSIiwiaWF0IjoxNTk0NDQ5NjY0LCJleHAiOjE1OTQ0NTMyNjR9._AswTh2f5TfZHarfNO-WI9kF8eD0GNWKJ8lrbN2hqGA"
}

```

Lets send the toekn with `Authorization:Bearer BEARER_TOKEN` Header tag

```
Maheshs-MBP:rsocket mahesh$ http :8080/person/v2 'Authorization:Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYWhlc2giLCJyb2xlcyI6IlJPTEVfQURNSU4sUk9MRV9VU0VSIiwiaWF0IjoxNTk0NDQ5Mzk0LCJleHAiOjE1OTQ0NTI5OTR9.J4l5YE9HgNlfg_RbiOR577L-H3KNKyzp8OEKykBd4_4'
HTTP/1.1 200 OK
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Content-Length: 5
Content-Type: text/plain;charset=UTF-8
Expires: 0
Pragma: no-cache
Referrer-Policy: no-referrer
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
X-Content-Type-Options: nosniff
X-Frame-Options: DENY
X-XSS-Protection: 1 ; mode=block

Hello

```

With Method level security
mahesh -ADMIN
shawn - USER
```
Maheshs-MBP:rsocket mahesh$ http :8080/auth/token 'username=mahesh' 'password=pwd'
HTTP/1.1 200 OK
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYWhlc2giLCJyb2xlcyI6IlJPTEVfQURNSU4sUk9MRV9VU0VSIiwiaWF0IjoxNTk0NDUzMDc1LCJleHAiOjE1OTQ0NTY2NzV9.GR-jHiFbh33RK4GKLiBJZtGp84ovXrkRXjT6OrBNKws
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Content-Length: 188
Content-Type: application/json
Expires: 0
Pragma: no-cache
Referrer-Policy: no-referrer
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
X-Content-Type-Options: nosniff
X-Frame-Options: DENY
X-XSS-Protection: 1 ; mode=block

{
    "id_token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYWhlc2giLCJyb2xlcyI6IlJPTEVfQURNSU4sUk9MRV9VU0VSIiwiaWF0IjoxNTk0NDUzMDc1LCJleHAiOjE1OTQ0NTY2NzV9.GR-jHiFbh33RK4GKLiBJZtGp84ovXrkRXjT6OrBNKws"
}

Maheshs-MBP:rsocket mahesh$ http :8080/auth/token 'username=shawn' 'password=pwd'
HTTP/1.1 200 OK
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzaGF3biIsInJvbGVzIjoiUk9MRV9VU0VSIiwiaWF0IjoxNTk0NDUzMDg0LCJleHAiOjE1OTQ0NTY2ODR9.7s8Xb0YoFW-65mwxslb9ZV57zVfsY1xqCUCHKc1U01A
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Content-Length: 172
Content-Type: application/json
Expires: 0
Pragma: no-cache
Referrer-Policy: no-referrer
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
X-Content-Type-Options: nosniff
X-Frame-Options: DENY
X-XSS-Protection: 1 ; mode=block

{
    "id_token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzaGF3biIsInJvbGVzIjoiUk9MRV9VU0VSIiwiaWF0IjoxNTk0NDUzMDg0LCJleHAiOjE1OTQ0NTY2ODR9.7s8Xb0YoFW-65mwxslb9ZV57zVfsY1xqCUCHKc1U01A"
}

Maheshs-MBP:rsocket mahesh$ http :8080/greet/admin 'Authorization:Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzaGF3biIsInJvbGVzIjoiUk9MRV9VU0VSIiwiaWF0IjoxNTk0NDUzMDg0LCJleHAiOjE1OTQ0NTY2ODR9.7s8Xb0YoFW-65mwxslb9ZV57zVfsY1xqCUCHKc1U01A'
HTTP/1.1 403 Forbidden
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Content-Type: text/plain
Expires: 0
Pragma: no-cache
Referrer-Policy: no-referrer
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
X-Content-Type-Options: nosniff
X-Frame-Options: DENY
X-XSS-Protection: 1 ; mode=block
content-length: 6

Denied

Maheshs-MBP:rsocket mahesh$ http :8080/greet/admin 'Authorization:Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYWhlc2giLCJyb2xlcyI6IlJPTEVfQURNSU4sUk9MRV9VU0VSIiwiaWF0IjoxNTk0NDUzMDc1LCJleHAiOjE1OTQ0NTY2NzV9.GR-jHiFbh33RK4GKLiBJZtGp84ovXrkRXjT6OrBNKws'
HTTP/1.1 200 OK
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Content-Length: 20
Content-Type: text/plain;charset=UTF-8
Expires: 0
Pragma: no-cache
Referrer-Policy: no-referrer
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
X-Content-Type-Options: nosniff
X-Frame-Options: DENY
X-XSS-Protection: 1 ; mode=block

Admin access: mahesh


```