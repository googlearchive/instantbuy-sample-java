Java Sample for Instant Buy API Copyright (C) 2013 Google Inc.

instantbuy-sample-java
=======================

Basic Java implementation of the Google Wallet for Instant Buy API.

### Setup

To setup the sample, edit webapp/WEB-INF/appengine-web.xml.

* If you don't already have a Google contact, submit the [Instant buy interest form](http://getinstantbuy.withgoogle.com). Google will respond to qualified merchants with instructions on how to set up merchant credentials.
* Replace online_wallet_enviroment to PRODUCTION or SANDBOX to set the desired environment.
* If environment is PRODUCTION : Replace production_merchant_id, production_merchant_auth_key, merchant_name in appengine-web.xml with your Production Merchant Id, Production Merchant Auth Key, Merchant Name.
* Else If environment is SANDBOX : Replace sandbox_merchant_id, sandbox_merchant_auth_key, merchant_name in appengine-web.xml with your Sandbox Merchant Id, Sandbox Merchant Auth Key, Merchant Name.
* Create an API project in the [Google API Console](https://code.google.com/apis/console/) then select the API Access tab in your API project, and click Create an OAuth 2.0 client ID and also enable Google+ API in services tab.
* Replace oauth_client_id in appengine-web.xml with your Oauth Client Id.

### Google appengine.

To run application on google appengine requires [Google App Engine SDK](https://developers.google.com/appengine/downloads#Google_App_Engine_SDK_for_Java).

1. Create new application at your [appengine account](https://appengine.google.com).
2. Change application name in webapp/WEB-INF/appengine-web.xml file.
3. Follow instruction to install google appengine sdk for java and to upload the application on [Google Appengine for Java Docs](https://developers.google.com/appengine/docs/java/gettingstarted/introduction).
4. Create the WAR file using maven plugin for eclipse. Go to [Maven](http://maven.apache.org/) for more details.

### Local dev.

To run application on local server requires [Maven](http://maven.apache.org/) 2.0 or greater.

1. Install maven plugin in eclipse.
2. Run you application in eclipse using maven.
2. You can now visit http://localhost in your browser to see the application in action.
