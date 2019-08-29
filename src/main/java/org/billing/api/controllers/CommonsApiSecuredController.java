package org.billing.api.controllers;

import com.sumitchouksey.book.request.json.handler.RequestJsonHandler;
import com.sumitchouksey.book.response.json.handler.ResponseJsonHandler;
import com.sumitchouksey.book.response.json.handler.util.ResponseJsonUtil;
import com.sumitchouksey.book.util.Utility;
import org.apache.commons.lang.StringUtils;
import org.billing.api.service.CommonsApiService;
import org.billing.api.util.ResponseConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/secured")
public class CommonsApiSecuredController
{
    @Autowired
    private CommonsApiService commonsApiService;

    @PostMapping("/change-password")
    public ResponseJsonHandler changePassword(@RequestBody RequestJsonHandler requestJsonHandler)
    {
        String email = requestJsonHandler.getStringValue("email");

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

        String newPassword  = requestJsonHandler.getStringValue("newPassword");
        if(newPassword==null||StringUtils.isBlank(newPassword))
            return ResponseJsonUtil.getResponse(ResponseConstant.NEW_PASSWORD_ERROR.getStatus(),400,"Bad Request",true);
        else{
            Pattern pattern = Pattern.compile(Utility.PASSWORD_PATTERN);
            if(!pattern.matcher(newPassword).find())
                return ResponseJsonUtil.getResponse(ResponseConstant.NEW_PASSWORD_ERROR.getStatus(),400,"Bad Request",true);
        }
        return commonsApiService.changePassword(requestJsonHandler);
    }

    @PostMapping("/update-profile")
    public ResponseJsonHandler updateProfile(@RequestBody RequestJsonHandler requestJsonHandler)
    {
        String email = requestJsonHandler.getStringValue("email");
        String password = requestJsonHandler.getStringValue("password");
        if(email==null)
            return ResponseJsonUtil.getResponse(ResponseConstant.EMAIL_ERROR.getStatus(),400,"Bad Request",true);
        else
        {
            if(email.isEmpty())
                return ResponseJsonUtil.getResponse(ResponseConstant.EMAIL_ERROR.getStatus(),400,"Bad Request",true);
        }
        if(password==null)
            return ResponseJsonUtil.getResponse(ResponseConstant.PASSWORD_ERROR.getStatus(),400,"Bad Request",true);
        else
        {
            if(password.isEmpty())
                return ResponseJsonUtil.getResponse(ResponseConstant.PASSWORD_ERROR.getStatus(),400,"Bad Request",true);
        }
        //Checking Password Pattern
        Pattern r = Pattern.compile(Utility.PASSWORD_PATTERN);
        if(!r.matcher(password).find())
            return ResponseJsonUtil.getResponse(ResponseConstant.IMPROPER_PASSWORD_PATTERN.getStatus(),400,"Bad Request");

        return null;
    }
}
