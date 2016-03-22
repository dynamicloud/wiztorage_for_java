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

Wiztorage needs a basic settings to be configured, the settings of wiztorage are within a properties file (wiztorage.properties).  This file must be in the root of your classpath.

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

###Dynamicloud account

Wiztorage needs API credentials from a Dynamicloud account, these credentials allow Wiztorage to access your account's structure (Model).  The mandatory model in your account should be composed for a model with at least seven fields.  For further information about models and fields in Dynamicloud visit its documentation at [Models & Fields](https://www.dynamicloud.org/documents/mfdoc "Dynamicloud documentation")

**We are going to explain step by step how to setup your account in Dynamicloud, trust us it's very easy:**

1. Sign up in Dynamicloud (You can use either Google, Linkedin or Github account to speed up the registration)
2. Click on **Add Field** link at your **Real time Dashboard**.  Here you need to add seven fields:
  
a. **Fields:**
  
| Field identifier | Field label | Field comments | Field type | Is a required field in form? |
| --- | --- | --- | --- | --- |
| `name` | File Name | File name, for exemple: my_file.log | Text field | **Yes** |
| `size` | File size | File size | Numeric Field | **Yes** |
| `checked` | Checked File | Indicates if this chunk is verified | Numeric Field | **Yes** |
| `type` | File type | Contents the file type | Text field | **Yes** |
| `checkSum` | Checksum  | Checksum for this chunk | Text field | **Yes** |
| `chunk` | File Chunk | This is the unit of a file | Textarea field | **Yes** |
| `sequence` | File sequence | Indicates the order for this chunk | Numeric field | **Yes** |
| `description` | File Description | This is an optional description | Textarea field | **No** |  
  
b. **Add model**
  
A model is the cointainer of these fields, to add a model follow the next steps:

1. Click on **Add model** link at your **Real time Dashboard**
2. Fill the mandatory field name and set a description (Optional)
3. Press button Create
4. In the list of Models the Model box has a header with the model Id, copy that Id and put it in your `log4j.properties` file.

```properties
# This is the model identifier
# The model contains the structure to store logs into the cloud
# For more information about models in Dynamicloud visit https://www.dynamicloud.org/documents/mfdoc
modelIdentifier=Enter_Model_Id
```

####The last step is to copy the API credentials (CSK and ACI keys) to put them in your `log4j.properties` file.

1. Click on **Your name link at right top of your account**
2. Copy the CSK and ACI keys and put them into your `log4j.properties` file.

```properties
# Credentials for REST APIs
# Go to https://www.dynamicloud.org/manage and get the API keys available in your profile
# If you don't have an account in Dynamicloud, visit https://www.dynamicloud.org/signupform
# You can easily use a social network to sign up
csk=Enter_Client_Secret_Key
aci=Enter_API_Client_Id
```

At this moment you have the necessary to start to upload files into the cloud.

#How to use
