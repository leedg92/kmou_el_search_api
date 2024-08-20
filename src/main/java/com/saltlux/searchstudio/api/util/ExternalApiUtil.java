package com.saltlux.searchstudio.api.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ExternalApiUtil {
    public static final String METHOD_POST = "POST";
    public static final String METHOD_GET = "GET";

    //URLs
    public static final String AI_RECOMMEND_URL = "http://192.168.0.201:35602";

    //end points
    public static final String AI_RECOMMEND_NONCOURSE_ENDPOINT = "/kmou_recommend_system/noncourse";

    public static String getAiRecommendApi(String apiUrl, String endpoint, String method, String studentNo){
        StringBuilder sb = new StringBuilder();

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonObj = mapper.createObjectNode();
        jsonObj.put("STUDENT_NO", studentNo);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String paramStr = objectMapper.writeValueAsString(jsonObj);
            URL url = new URL(apiUrl + endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setRequestProperty("Accept", "*/*");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            try (OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), "UTF-8")) {
                writer.write(paramStr);
                writer.flush();
            }

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
                String output;
                while ((output = br.readLine()) != null) {
                    sb.append(output);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return sb.toString();

    }
}
