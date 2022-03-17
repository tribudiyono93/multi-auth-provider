#Multi Authentication Provider
project ini menggunakan framework spring boot dengan java version 1.8.

## Authentication Provider yang di support 
    - Google
    - Okta
    - Internal (login menggunakan email dan password)

## Running the Back End
supaya bisa menjalankan project ini teman-teman perlu mengisi client-id, client-secret, issuer dan audience dari 
masing-masing authentication provider. di repo ini emang sengaja ak kosongin ya (untuk alasan keamanan), kalo temen2 mau coba bisa request 
src/main/resources/application.yml nya ke aku. silakan dm ke linkedin ya (https://www.linkedin.com/in/tri-budiyono-6641a9114/?originalSubdomain=id). 
setelah mendapatkan application.yml nya teman-teman bisa import project nya ke IDE yang di pake (Eclipse, Intellij, etc). 
kemudian run project nya.

## Testing id token (google atau okta)
untuk dapetin id token nya, temen2 bisa dapetin di https://pondam.id. temen2 bisa login dengan memilih authentication provider google.
kalo mau coba id token dari okta, temen2 bisa request untuk register email nya dulu ke aku. nanti email nya ak tambahin di oktanya.
setelah berhasil login, informasi id token akan di tampilkan di web nya. nah temen-temen bisa coba dengan dengan melakukan request berikut.

pertama kita akan coba tanpa menggunakan id token nya. 
`curl --location --request GET 'http://localhost:8080/test/non-admin-resource'`
setelah di hit kita akan mendapatkan http status 401 Unauthorized.
kemudian setelah itu coba kita tambahkan id token yang di dapat sebelumnya.
`curl --location --request GET 'http://localhost:8080/test/non-admin-resource' \
--header 'Authorization: Bearer GANTI_DENGAN_ID_TOKEN'`
jika id token nya valid dan belum expired maka akan mendapatkan http status 200 OK.

## Testing id token internal provider
internal provider ini digunakan untuk validasi id token yang di dapatkan dari login email dan password. 
untuk mendapatkan id token nya, teman-teman bisa hit api berikut :
`curl --location --request POST 'http://localhost:8080/authentication/login' \
--header 'Content-Type: application/json' \
--data-raw '{
"email": "dummy@dummy.com",
"password": "password"
}'`
setelah mendapatkan id token nya teman-teman bisa hit kembali ke api berikut : 
`curl --location --request GET 'http://localhost:8080/test/non-admin-resource' \
--header 'Authorization: Bearer GANTI_DENGAN_ID_TOKEN'`
jika id token nya valid, maka akan mendapatkan response http status 200 OK.

## Self learning
untuk teman-teman yang mau mempelajari code nya bisa start dari file2 ini ya
- src/main/java/com/example/multiauthprovider/config/WebSecurityConfig.java
- src/main/java/com/example/multiauthprovider/filter/JWTTokenFilter.java
- src/main/java/com/example/multiauthprovider/security/OauthProviderFactory.java
- src/main/java/com/example/multiauthprovider/security/OauthProvider.java
- src/main/java/com/example/multiauthprovider/security/GoogleAuthProvider.java
- src/main/java/com/example/multiauthprovider/security/OktaAuthProvider.java
- src/main/java/com/example/multiauthprovider/security/InternalAuthProvider.java
- src/main/java/com/example/multiauthprovider/controller/AuthenticationController.java
- src/main/java/com/example/multiauthprovider/controller/TestController.java


#### Kalo kamu cukup terbantu dengan project ini, boleh banget loh traktir kopi. 😄



