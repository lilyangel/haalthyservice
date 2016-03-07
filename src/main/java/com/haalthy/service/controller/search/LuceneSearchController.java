package com.haalthy.service.controller.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haalthy.service.controller.Interface.Response;
import com.haalthy.service.controller.Interface.SearchRequest;
import com.haalthy.service.domain.ClinicTrailInfo;
import com.haalthy.service.domain.Treatment;
import com.haalthy.service.domain.User;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/open/search")
public class LuceneSearchController {
	private String searchServerURL = "http://service.haalthy.com:8984/solr/";
	
    @RequestMapping(value = "/treatment", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public Response searchTreatment(@RequestBody SearchRequest searchRequest){
    	Response response = new Response();
        try {
            String[] searchColumns = new String[]{"treatmentName", "dosage", "cancerType", "pathological"};
        	response.setContent(searchObject(searchRequest, "aiyoutreatment", searchColumns));
        	response.setResult(1);
        	response.setResultDesp("返回成功");
       } catch (Exception e) {
     		response.setResult(-1);
     		response.setResultDesp("系统异常");
     		// TODO Auto-generated catch block
     		e.printStackTrace();
       }
    	return response;
    }
    @RequestMapping(value = "/clinictrail", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public Response searchClinictrail(@RequestBody SearchRequest searchRequest){
    	Response response = new Response();
        try {
            String[] searchColumns = new String[]{"subgroup", "drugtype", "drugname","stage"};
        	response.setContent(searchObject(searchRequest, "aiyouclinictrial", searchColumns));
        	response.setResult(1);
        	response.setResultDesp("返回成功");
        } catch (Exception e) {
     		response.setResult(-1);
     		response.setResultDesp("系统异常");
     		// TODO Auto-generated catch block
     		e.printStackTrace();
        }
    	return response;
    }
    
    @RequestMapping(value = "/user", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public Response searchUser(@RequestBody SearchRequest searchRequest){
    	Response response = new Response();
        try {
            String[] searchColumns = new String[]{"displayname", "pathological", "cancerType"};
        	response.setContent(searchObject(searchRequest, "aiyouuser", searchColumns));
        	response.setResult(1);
        	response.setResultDesp("返回成功");
       } catch (Exception e) {
     		response.setResult(-1);
     		response.setResultDesp("系统异常");
     		// TODO Auto-generated catch block
     		e.printStackTrace();
       }
    	return response;
    }
    
    public Object searchObject(SearchRequest searchRequest, String model, String[] searchColumns) throws Exception{
        String[] keywords = null;
        if (searchRequest.getCount() == 0){
        	searchRequest.setCount(20);
        }
        String beginIndex =String.valueOf(searchRequest.getCount() * searchRequest.getPage());
        StringBuilder searchParameter = new StringBuilder("");
        searchParameter.append("&start=").append(beginIndex).append("&rows=").
        	append(String.valueOf(searchRequest.getCount())).append("&wt=json&indent=true");
        String searchURLStr = "";
        if ((searchRequest.getSearchString() != null) && (searchRequest.getSearchString() != "")){
        	keywords = searchRequest.getSearchString().split(" ");
        }
        StringBuilder searchURL = new StringBuilder(model + "/select?q=");
        if (keywords != null){
			for (String keyword : keywords) {
				for (String searchColumn : searchColumns) {
					searchURL.append(searchColumn).append(":*").append(URLEncoder.encode(keyword)).append("*+OR+");
				}
			}
			for (String keyword : keywords) {
				for (String searchColumn : searchColumns) {
					searchURL.append(searchColumn).append(":").append(URLEncoder.encode(keyword)).append("+OR+");
				}
			}
			searchURLStr = searchURL.substring(0, searchURL.length() - 4);
		}else{
			searchURL.append("*:*");	
			searchURLStr = searchURL.toString();
		}
        System.out.println(searchURLStr);
        URL restServiceURL = new URL(searchServerURL + searchURLStr + searchParameter);
        HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
        httpConnection.setRequestMethod("GET");
        httpConnection.setRequestProperty("Accept", "application/json");

        if (httpConnection.getResponseCode() != 200) {
               throw new RuntimeException("HTTP GET Request Failed with Error code : "
                             + httpConnection.getResponseCode());
        }

        BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
               (httpConnection.getInputStream())));

        String output;
        StringBuilder builder = new StringBuilder("");  
        while ((output = responseBuffer.readLine()) != null) {
               builder.append(output);
        }
        List<Treatment> treatments = (List<Treatment>) analysisJSonData(builder.toString());
        httpConnection.disconnect();
    	return treatments;
    }
    
    public Object analysisJSonData(String jsonStr){
    	JSONObject jsonObj = new JSONObject();
    	jsonObj = JSONObject.fromObject(jsonStr);
    	JSONObject resultListResponse = jsonObj.getJSONObject("response");
    	JSONArray resultList = resultListResponse.getJSONArray("docs");
    	return resultList;
    }
}
