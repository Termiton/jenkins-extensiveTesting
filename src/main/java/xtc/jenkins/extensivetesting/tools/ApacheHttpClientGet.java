package xtc.jenkins.extensivetesting.tools;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class ApacheHttpClientGet {

    public static String request(String url, String method, String sessionCookie) {
        String output = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet getRequest = new HttpGet(url + method);
            getRequest.addHeader(Const.ACCEPT, Const.CONTENT_TYPE);
            if (null != sessionCookie) {
                getRequest.addHeader(Const.SESSION_COOKIE, sessionCookie);
            }

            HttpResponse response = httpClient.execute(getRequest);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException(Const.HTTPERR
                        + response.getStatusLine().getStatusCode());
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));

            System.out.println(Const.SERVOUT);


            while ((output = br.readLine()) != null) {
                System.out.println(output);
                stringBuilder.append(output);
            }

            httpClient.getConnectionManager().shutdown();

        } catch (ClientProtocolException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

}

