package com.callinize.crm;

import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.sugarcrm.api.SugarBean;
import com.sugarcrm.api.SugarClient;
import com.sugarcrm.api.SugarCredentials;
import com.sugarcrm.api.SugarSession;
//import org.apache.http.client.ClientProtocolException;

public class SugarCRM {

  /**
   * This method logs in to the SugarCRM with a POST and returns the session
   * id.
   * @return {@code String} session id
   * @throws NoSuchAlgorithmException
   * @throws ClientProtocolException
   * @throws IOException
 * @throws URISyntaxException 
 * @throws HttpException 
   */
  private String login() throws NoSuchAlgorithmException, IOException, URISyntaxException, HttpException {
    // user your credentials
    String username = "admin";
    String password = "adF32wjkh";

    MessageDigest md5 = MessageDigest.getInstance("MD5");
    String passwordHash = new BigInteger(1, md5.digest(password.getBytes()))
        .toString(16);

    // the order is important, so use a ordered map
    Map<String, String> userCredentials = new LinkedHashMap<String, String>();  
    userCredentials.put("user_name", username);
    userCredentials.put("password", passwordHash);

    // the order is important, so use a ordered map
    Map<String, Object> request = new LinkedHashMap<String, Object>();
    request.put("user_auth", userCredentials);
    request.put("application_name", "Callinify");

    MultipartEntity multipartEntity = new MultipartEntity();
    multipartEntity.addPart("method", new StringBody("login"));
    // define request encoding
    multipartEntity.addPart("input_type", new StringBody("JSON"));
    // define response encoding
    multipartEntity.addPart("response_type", new StringBody("JSON"));
    multipartEntity.addPart("rest_data",
        new StringBody(JSONObject.toJSONString(request)));

    // yourSugarCRM has to be changed to your SugarCRM instance
    // something like localhost/sugarcrm
    String url = "http://sugar.alertus.com/sugarcrm/service/v4/rest.php?";
    HttpPost httpPost = new HttpPost(url);
    httpPost.setEntity(multipartEntity);

    DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
    HttpResponse execute = defaultHttpClient.execute(httpPost);

    HttpEntity entity = execute.getEntity();

    JSONObject parse = (JSONObject) JSONValue.parse(new InputStreamReader(
        entity.getContent()));
    return (String) parse.get("id");
  }
  
  //https://github.com/mmarum-sugarcrm/sugarcrm-api-java/blob/master/sugar-api/src/test/java/com/sugarcrm/api/SugarClientTest.java
  public void getBeanTest(){
	    SugarClient client = new SugarClient("http://sugar.alertus.com/sugarcrm");
	    try{
	      SugarSession session = client.getSugarSession(new SugarCredentials("admin", "adF32wjkh"));
	      SugarBean bean = client.getBean(session, "Contacts", "0033000000YlMvqAAF");
	      System.out.println( bean.get("first_name") + bean.get("last_name") );
	    }catch(Exception e){
	      e.printStackTrace();
	    }
  }
	  
  
  public static void main(String[] args) {
	  
	  SugarCRM s = new SugarCRM();
	 
	  s.getBeanTest();
	  
	  
	  try {
		System.out.println( s.login());
	} catch (NoSuchAlgorithmException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (URISyntaxException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (HttpException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
  }
}


