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

**This documentation has the following content:**

1. [Dependencies](#dependencies)
2. [Settings](#settings)
  1. [Installation](#installation)
  2. [Dynamicloud account](#dynamicloud-account)
3. [How to use](#how-to-use)
  
#Dependencies
**Wiztorage has only one depedendency:** dynamicloud and its dependencies.

#Installation
Install wiztorage's jar like any other jar file in your app's lib folder.
Wiztorage jar file is available on release page.

#Settings

Wiztorage needs a basic settings to be configured, the settings of wiztorage are within a properties file.

**These are the settings of wiztorage:**

```properties
# Go to https://www.dynamicloud.org/manage and get the API keys available in your profile
# If you don't have an account in Dynamicloud, visit https://www.dynamicloud.org/signupform
# You can easily use a social network to sign up
csk=csk#...
aci=aci#...
# This is the model identifier
# The model contains the structure to store logs into the cloud
# For more information about models in Dynamicloud visit https://www.dynamicloud.org/documents/mfdoc
modelIdentifier=123
```
