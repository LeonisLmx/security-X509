#!/bin/bash
#define
PASSWD=studyTest
HOSTNAME=test.it

mkdir keys
cd keys
#precondition
 rm -f ca*
 rm -f client.*
 rm -f server.*
echo "create "

echo "######### ca"
openssl req -new -x509 -keyout ca.key -out ca.crt -sha256 -days 365 -passout pass:${PASSWD} -subj "/C=cn/ST=beijing/L=beijing/O=aspire/OU=aspire/CN=ca.it"
openssl x509 -in ca.crt -out ca.pem -outform PEM

keytool -keystore client.truststore -alias caroot -import -file ca.crt -storepass ${PASSWD}

keytool -keystore server.truststore -alias caroot -import -file ca.crt -storepass ${PASSWD}

echo "######### server"
openssl genrsa -passout pass:${PASSWD} -out server.key 2048

openssl req -new -key server.key -out server.csr -subj "/C=CN/ST=SHANXI/L=XI'AN/O=TW/OU=IT/CN=${HOSTNAME}"

cat>server.ext<<EOF
[v3_req]
keyUsage = critical, digitalSignature, keyAgreement
extendedKeyUsage = serverAuth
subjectAltName = @alt_names
[ alt_names ]
DNS.1 = test.it
IP.1 = 127.0.0.1
EOF

openssl x509 -req -days 365 -in server.csr -CA ca.crt -CAkey ca.key -passin pass:${PASSWD} -CAcreateserial -out server.crt -sha256 -extensions v3_req -extfile server.ext
openssl x509 -in server.crt -out server.pem -outform PEM
# 转换为pkcs12密钥库
openssl pkcs12 -export -passout pass:${PASSWD} -in server.crt -inkey server.key -out server.p12 -name server -chain -CAfile ca.crt -caname rootca

# 导入到server.keystore中
keytool -importkeystore \
-deststorepass ${PASSWD} -destkeypass ${PASSWD} -destkeystore server.keystore \
-srckeystore server.p12 -srcstoretype PKCS12 -srcstorepass ${PASSWD} \
-alias server


echo "######### client"
openssl genrsa -des3 -passout pass:${PASSWD} -out client.key 4096
openssl req -new -key client.key -passin pass:${PASSWD} -out client.csr -subj "/C=CN/ST=SHANXI/L=XI'AN/O=TW/OU=IT/CN=client"
openssl x509 -req -days 365 -in client.csr -passin pass:${PASSWD}  -CA ca.crt -CAkey ca.key  -set_serial 01 -out client.crt  -sha256
openssl x509 -in client.crt -out client.pem -outform PEM
openssl pkcs12 -export -passout pass:${PASSWD} -in client.crt -inkey client.key -passin pass:${PASSWD} -out client.p12 -name client -chain -CAfile ca.crt -caname caroot

# 导入到client.keystore中
keytool -importkeystore \
-deststorepass ${PASSWD} -destkeypass ${PASSWD} -destkeystore client.keystore \
-srckeystore client.p12 -srcstoretype PKCS12 -srcstorepass ${PASSWD} \
-alias client
