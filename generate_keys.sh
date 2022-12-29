mkdir ./target
mkdir ./src/main/resources/certs
cd target

# create rsa key pair
openssl genrsa -out keypair.pem 2048

# extract public key
openssl rsa -in keypair.pem -pubout -out public.pem

# create private key in PKCS#8 format
openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.pem

mv public.pem ../src/main/resources/certs
mv private.pem ../src/main/resources/certs
