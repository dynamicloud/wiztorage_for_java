# Wiztorage (Building process)

###Summary 
Wiztorage allows you either to upload or download files just with a method call, it's very easy and leverages the Dynamicloud's services.  This service is free for use and its limit is related to the capacity in your Dynamicloud account.

###Features

1. Wiztorage is powered by Dynamicloud.
2. Wiztorage stores a file as a chunks into a model in Dynamicloud, these chunks all together represent a file.
3. Every chunk is a based64 encode that can be checked separately from Dynamicloud's site.
4. Wiztorage sends one mega (1 mg) per chunk, so, if you need to upload a file of 10mg Wiztorage will send 10 chunks.
5. Wiztorage allows you to send any kind of files.
6. You can upload/download at any time.
