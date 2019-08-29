package org.billing.api.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sumitchouksey.book.encryption.HmacEncryption;
import com.sumitchouksey.book.request.json.handler.RequestJsonHandler;
import com.sumitchouksey.book.response.json.handler.ResponseJsonHandler;
import com.sumitchouksey.book.response.json.handler.util.ResponseJsonUtil;
import com.sumitchouksey.book.util.Utility;
import com.sumitchouksey.book.vos.RoleVo;
import org.billing.api.entities.*;
import org.billing.api.listeners.OnRegistrationCompleteListener;
import org.billing.api.repository.CommonsApiRepository;
import org.billing.api.util.ResponseConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.*;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.request.WebRequest;
import javax.servlet.ServletContext;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

@Service
@Transactional
public class CommonsApiService implements ServletContextAware {

    @Autowired
    private CommonsApiRepository commonsApiRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private ServletContext servletContext;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private DiscoveryClient discoveryClient;


    public ResponseJsonHandler createAccount(
            String firstName,String lastName,String email,String password,String contactNo,
            String language, String currency, String organizationName, String address,String city,String state,String country,
            int postalCode
            ){
         UserEntity userEntity  = commonsApiRepository.getUserEntity(email);
        if(userEntity!=null){
            return ResponseJsonUtil.getResponse(ResponseConstant.EMAIL_ID_ALREAY_EXISTS.getStatus(),400,"Bad Request",true);
        }else{
            ClientEntity clientEntity  = commonsApiRepository.getClientEntity(organizationName);
            if(clientEntity!=null){
                return ResponseJsonUtil.getResponse(ResponseConstant.ORGANIZATION_ALREADY_EXISTS.getStatus(),400,"Bad Request",true);
            }else{
                clientEntity  =new ClientEntity();
                clientEntity.setClientName(organizationName);
                clientEntity.setConfigurations("{\"oauthConfig\":{\"accessTokenValiditySeconds\":50000,\"authorizedGrantTypes\":[\"password\",\"refresh_token\",\"authorization_code\"],\"autoApproveScopes\":[\"\"],\"refreshTokenValiditySeconds\":1000,\"registeredRedirectUris\":[\"\"],\"resourceIds\":[\"\"],\"scopes\":[\"openId\"]}}");
                clientEntity.setCreatedOn(Utility.getCustomTimestamp());
                clientEntity.setModifiedOn(Utility.getCustomTimestamp());
                clientEntity.setIsActive(true);
                clientEntity =commonsApiRepository.saveOrUpdateClientEntity(clientEntity);

                Set<RolesEntity> rolesEntities  =new HashSet<>();
                RolesEntity rolesEntity  = new RolesEntity();
                rolesEntity.setRoleName("Admin");
                rolesEntity.setCreatedOn(Utility.getCustomTimestamp());
                rolesEntity.setModifiedOn(Utility.getCustomTimestamp());
                rolesEntity.setIsActive(true);
                rolesEntity.setClientEntity(clientEntity);
                rolesEntities.add(rolesEntity);

                userEntity  = new UserEntity();
                userEntity.setIsActive(false);
                userEntity.setPasswordCreatedOn(Utility.getCustomTimestamp());
                userEntity.setPassword(HmacEncryption.hmacEncryption(userEntity.getPasswordCreatedOn(),password));
                userEntity.setFirstName(firstName);
                userEntity.setLastName(lastName);

                userEntity.setCurrency(currency);
                userEntity.setLanguage(language);
                userEntity.setContactNo(contactNo);
                userEntity.setEmail(email);
                userEntity.setAddress(address);
                userEntity.setCity(city);
                userEntity.setState(state);
                userEntity.setCountry(country);
                userEntity.setPostalCode(postalCode);

                userEntity.setRolesEntity(rolesEntities);
                userEntity.setCreatedOn(Utility.getCustomTimestamp());
                userEntity.setModifiedOn(Utility.getCustomTimestamp());
                userEntity.setAccountVerified(false);

                commonsApiRepository.addUserEntity(userEntity);
                /*eventPublisher.publishEvent(new OnRegistrationCompleteListener(
                        userEntity,webRequest.getLocale(),webRequest.getContextPath()
                ));*/
                return ResponseJsonUtil.getResponse("An email confirmation has been sent to your email account . Please verify it",200,"success",false);
            }
        }
    }


    public void createVerificationToken(UserEntity userEntity , String token){
        TokenVerificationEntity tokenVerificationEntity = new TokenVerificationEntity();
        tokenVerificationEntity.setIsActive(true);
        tokenVerificationEntity.setToken(token);
        tokenVerificationEntity.setCreatedOn(Utility.getCustomTimestamp());
        tokenVerificationEntity.setUserEntity(userEntity);
        commonsApiRepository.addTokenVerificationEntity(tokenVerificationEntity);
    }

    public String verifyToken(String token){
        TokenVerificationEntity tokenVerificationEntity  = commonsApiRepository.getTokenVerificationEntity(token);
        if(tokenVerificationEntity!=null){
            UserEntity userEntity= tokenVerificationEntity.getUserEntity();
            if(userEntity!=null){
                if(userEntity.getAccountVerified()){
                    return "already-verified";
                }else{
                    userEntity.setIsActive(true);
                    userEntity.setAccountVerified(true);
                    commonsApiRepository.addUserEntity(userEntity);
                    return "verification-success";
                }
            }else return "verification-failer";

        }else{
            return "verification-failer";
        }
    }

    public ResponseJsonHandler validatePassword(String email, String password,String  organizationName)
    {
        UserEntity userEntity  = commonsApiRepository.getUserEntity(email.trim());
        if (userEntity == null)
            return ResponseJsonUtil.getResponse(ResponseConstant.EMAIL_ID_NOT_EXISTS.getStatus(), 404, "Not Found",true);
       /* else if(!userEntity.getIsActive())
            return ResponseJsonUtil.getResponse(ResponseConstant.EMAIL_ID_INACTIVE.getStatus(), 400, "Bad Request",true);*/
        else if (userEntity.getAttempts() > 5)
            return ResponseJsonUtil.getResponse(ResponseConstant.ATTEMPT_LIMIT_EXCEEDED.getStatus(), 404, "Not Found",true);
        else if (!HmacEncryption.validateHmac(userEntity.getPasswordCreatedOn(),userEntity.getPassword(),password)){
            userEntity.setAttempts((userEntity.getAttempts() + 1));
            commonsApiRepository.updateUserEntity(userEntity);
            return ResponseJsonUtil.getResponse(ResponseConstant.INVALID_PASSWORD.getStatus(), 401, "Unauthorized",true);
        }

        ClientEntity clientEntity  = commonsApiRepository.getClientEntity(organizationName);
        if(clientEntity==null)
            return ResponseJsonUtil.getResponse("Organization not found", 404, "Not Found",true);
        else{
            long clientId=clientEntity.getId();
            boolean flag=false;
            Set<RolesEntity> rolesEntity = userEntity.getRolesEntity();
            if(rolesEntity!=null){
                if(!rolesEntity.isEmpty()){
                    for(RolesEntity re : rolesEntity){
                        if(re.getIsActive()){
                            ClientEntity clientEntity1= re.getClientEntity();
                            if(clientId==clientEntity1.getId()){
                                flag=true;

                            }
                        }
                    }
                }
            }
            if(!flag)
                return ResponseJsonUtil.getResponse(
                        "user-doesn't-belong-to-organization",400,"bad Request",true
                );
        }
        ObjectNode tokenNode  = getOAuth2Tokens(organizationName,email,password);
        ObjectNode responseNode  = objectMapper.createObjectNode();
        responseNode.put("token",tokenNode);
        return ResponseJsonUtil.getResponse(responseNode,200,ResponseConstant.SUCCESS.getStatus(),false);

    }

    public ObjectNode getOAuth2Tokens(String clientId, String userName , String password)
    {
        String encodedPassword=null;
        try {
            encodedPassword= URLEncoder.encode(password, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        String uri="";
        List<ServiceInstance> serviceInstanceList= discoveryClient.getInstances("identity-provider");
        for (ServiceInstance serviceInstance : serviceInstanceList) {
            uri=serviceInstance.getUri().toString();
        }
        String url = uri + "/oauth/token?grant_type=password&username="+userName+"&password="+encodedPassword+"&client_id="+clientId;
        //String url = "http://10.90.21.55:30001/identity-provider/oauth/token?grant_type=password&username="+userName+"&password="+encodedPassword+"&client_id="+clientId;
        ObjectNode objectNode  = objectMapper.createObjectNode();
        try
        {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            HttpEntity<String> requestEntity = new HttpEntity<>("parameters", headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST,requestEntity,String.class);
            if(response.getStatusCodeValue()==200)
            {
                String responseBody = response.getBody();
                if(responseBody!=null)
                {
                    JsonNode jsonNode;
                    try
                    {
                        jsonNode  = objectMapper.readTree(responseBody);
                        objectNode.put("access_token",jsonNode.get("access_token"));
                        objectNode.put("token_type",jsonNode.get("token_type"));
                        objectNode.put("refresh_token",jsonNode.get("refresh_token"));
                        objectNode.put("expires_in",jsonNode.get("expires_in"));
                        objectNode.put("scope",jsonNode.get("scope"));
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
            return objectNode;
        }
        catch (Exception e){
            return objectNode;
        }
    }



    public ResponseJsonHandler changePassword(RequestJsonHandler requestJsonHandler){
        String email = requestJsonHandler.getStringValue("email");
        Long userId  = requestJsonHandler.getLongValue("userId");
        String password = requestJsonHandler.getStringValue("password");
        String newPassword  = requestJsonHandler.getStringValue("newPassword");
        UserEntity userEntity  = commonsApiRepository.getUserEntity(userId);
        if (userEntity == null)
            return ResponseJsonUtil.getResponse(ResponseConstant.EMAIL_ID_NOT_EXISTS.getStatus(), 404, "Not Found",true);
        else if(!userEntity.getIsActive())
            return ResponseJsonUtil.getResponse(ResponseConstant.EMAIL_ID_INACTIVE.getStatus(), 400, "Bad Request",true);
        else if (userEntity.getAttempts() > 5)
            return ResponseJsonUtil.getResponse(ResponseConstant.ATTEMPT_LIMIT_EXCEEDED.getStatus(), 404, "Not Found",true);
        else if (!HmacEncryption.validateHmac(userEntity.getPasswordCreatedOn(),userEntity.getPassword(),password)){
            return ResponseJsonUtil.getResponse(ResponseConstant.INVALID_PASSWORD.getStatus(), 401, "Unauthorized",true);
        }
        String newPasswordHmac= HmacEncryption.hmacEncryption(userEntity.getPasswordCreatedOn(),newPassword);
        if(newPasswordHmac.equals(userEntity.getPassword())){
            return ResponseJsonUtil.getResponse(ResponseConstant.PASSWORDS_SHOULD_NOT_MATCH.getStatus(), 401, "Unauthorized",true);
        }
        userEntity.setPasswordCreatedOn(Utility.getCustomTimestamp());
        userEntity.setPassword(HmacEncryption.hmacEncryption(userEntity.getPasswordCreatedOn(),newPassword));
        commonsApiRepository.updateEntity(userEntity);
        return ResponseJsonUtil.getResponse("Password successfully changed",200,"success",false);
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
            this.servletContext=servletContext;
    }
}
