package afpa.learning.tolisten;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Taken from http://stackoverflow.com/questions/9767952/how-to-add-parameters-to-httpurlconnection-using-post
 */

public class Utils {

    public static String stringifyPostData(HashMap<String, Integer> map) throws UnsupportedEncodingException {

        StringBuilder sb = new StringBuilder();
        boolean first = true;

        for (Map.Entry<String, Integer> entry : map.entrySet()) {


            if (first) {
                first = false;
            } else {
                sb.append("&");
            }
            sb.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            sb.append("=");
            sb.append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));


        }


        System.out.print(sb.toString());
        return sb.toString();
    }



}
