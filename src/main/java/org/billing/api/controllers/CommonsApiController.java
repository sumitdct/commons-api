package org.billing.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumitchouksey.book.request.json.handler.RequestJsonHandler;
import com.sumitchouksey.book.response.json.handler.ResponseJsonHandler;
import com.sumitchouksey.book.response.json.handler.util.ResponseJsonUtil;
import com.sumitchouksey.book.util.Utility;
import org.apache.commons.lang.StringUtils;
import org.billing.api.service.CommonsApiService;
import org.billing.api.util.ResponseConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import java.util.regex.Pattern;

@RestController
public class CommonsApiController {

    @Autowired
    private CommonsApiService commonsApiService;


    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/create-account")
    public ResponseJsonHandler addUser(@RequestBody RequestJsonHandler requestJsonHandler, WebRequest webRequest, Device device){

        String firstName =requestJsonHandler.getStringValue("firstName");
        if(firstName==null || StringUtils.isBlank(firstName))
            return ResponseJsonUtil.getResponse("Please provide firstName",400,"Bad Request",true);


        String lastName =requestJsonHandler.getStringValue("lastName");
        if(lastName==null || StringUtils.isBlank(lastName))
            return ResponseJsonUtil.getResponse("Please provide lastName",400,"Bad Request",true);

        String email  = requestJsonHandler.getStringValue("email");
        if(email==null || StringUtils.isBlank(email))
            return ResponseJsonUtil.getResponse(ResponseConstant.EMAIL_ERROR.getStatus(),400,"Bad Request",true);
        else {
            Pattern pattern = Pattern.compile(Utility.EMAIL_PATTERN);
            if(!pattern.matcher(email).find())
                return ResponseJsonUtil.getResponse(ResponseConstant.INVALID_EMAIL.getStatus(),400,"Bad Request",true);
        }

        String password  = requestJsonHandler.getStringValue("password");
        if(password==null||StringUtils.isBlank(password))
            return ResponseJsonUtil.getResponse(ResponseConstant.PASSWORD_ERROR.getStatus(),400,"Bad Request",true);
        else{
            Pattern pattern = Pattern.compile(Utility.PASSWORD_PATTERN);
            if(!pattern.matcher(password).find())
                return ResponseJsonUtil.getResponse(ResponseConstant.IMPROPER_PASSWORD_PATTERN.getStatus(),400,"Bad Request",true);
        }

        String contactNo  = requestJsonHandler.getStringValue("contactNo");
        if(contactNo==null||StringUtils.isBlank(contactNo))
            return ResponseJsonUtil.getResponse(ResponseConstant.CONTACT_NO_ERROR.getStatus(),400,"Bad Request",true);

        String  language  = requestJsonHandler.getStringValue("language");
        if(language==null||StringUtils.isBlank(language))
            return ResponseJsonUtil.getResponse("Please select language",400,"Bad Request",true);

        String  currency  = requestJsonHandler.getStringValue("currency");
        if(currency==null||StringUtils.isBlank(currency))
            return ResponseJsonUtil.getResponse("Please select currency",400,"Bad Request",true);

        String  address  = requestJsonHandler.getStringValue("address");
        if(address==null||StringUtils.isBlank(address))
            return ResponseJsonUtil.getResponse("Please provide address",400,"Bad Request",true);


        String  city  = requestJsonHandler.getStringValue("city");
        if(city==null||StringUtils.isBlank(city))
            return ResponseJsonUtil.getResponse("Please provide city",400,"Bad Request",true);


        String  state  = requestJsonHandler.getStringValue("state");
        if(state==null||StringUtils.isBlank(state))
            return ResponseJsonUtil.getResponse("Please provide state",400,"Bad Request",true);


        String  country  = requestJsonHandler.getStringValue("country");
        if(country==null||StringUtils.isBlank(country))
            return ResponseJsonUtil.getResponse("Please provide country",400,"Bad Request",true);


        Integer postalCode  = requestJsonHandler.getIntegerValue("postalCode");
        if(postalCode==null)
            return ResponseJsonUtil.getResponse("Please provide postalCode",400,"Bad Request",true);

        String organizationName  = requestJsonHandler.getStringValue("organizationName");
        if(organizationName==null||StringUtils.isBlank(organizationName))
            return ResponseJsonUtil.getResponse("Please provide organizationName",400,"Bad Request",true);

        return commonsApiService.createAccount(firstName.trim(),lastName.trim(),email.trim(),password.trim(),contactNo.trim()
            ,language.trim(),currency.trim(),organizationName.trim(),address.trim(),city.trim(),state.trim(),country.trim(),postalCode
        );
    }

    @PostMapping("/validate-password")
    public ResponseJsonHandler validatePassword(@RequestBody RequestJsonHandler requestJsonHandler, Device device)
    {
       String email =requestJsonHandler.getStringValue("email");

       if(email==null || StringUtils.isBlank(email)){
           return ResponseJsonUtil.getResponse(ResponseConstant.EMAIL_ERROR.getStatus(),400,"Bad Request",true);
       }
        else {
            Pattern pattern = Pattern.compile(Utility.EMAIL_PATTERN);
            if(!pattern.matcher(email).find())
                return ResponseJsonUtil.getResponse(ResponseConstant.INVALID_EMAIL.getStatus(),400,"Bad Request",true);
        }

        String password  = requestJsonHandler.getStringValue("password");
        if(password==null||StringUtils.isBlank(password))
            return ResponseJsonUtil.getResponse(ResponseConstant.PASSWORD_ERROR.getStatus(),400,"Bad Request",true);
        else{
            Pattern pattern = Pattern.compile(Utility.PASSWORD_PATTERN);
            if(!pattern.matcher(password).find())
                return ResponseJsonUtil.getResponse(ResponseConstant.IMPROPER_PASSWORD_PATTERN.getStatus(),400,"Bad Request",true);
        }

        String organizationName  = requestJsonHandler.getStringValue("organizationName");
        if(organizationName==null || StringUtils.isEmpty(organizationName))
            return ResponseJsonUtil.getResponse("Please provide organizationName",400,"Bad Request",true);

        return commonsApiService.validatePassword(email.trim(),password.trim(),organizationName.trim());
    }


    @PostMapping("/forgot-password")
    public ResponseJsonHandler forgotPassword(@RequestBody RequestJsonHandler requestJsonHandler){
        return null;
    }




}
