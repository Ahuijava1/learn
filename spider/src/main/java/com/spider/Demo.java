package com.spider;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;

import com.tencentcloudapi.ecc.v20181213.EccClient;
import com.tencentcloudapi.ecc.v20181213.models.*;;

public class Demo
{
    public static void main(String [] args) {
        try{

            Credential cred = new Credential("AKIDkOnQpzzv1tajeHFiaAUfcOtcpwpSR5LO", "xe4LPDqIUP4PaYFl2FI5i5UzJPGrBF3F");

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("ecc.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            EccClient client = new EccClient(cred, "", clientProfile);

            ECCRequest req = new ECCRequest();
            req.setContent("What is your name?");

            ECCResponse resp = client.ECC(req);

            System.out.println(ECCResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }

    }

}