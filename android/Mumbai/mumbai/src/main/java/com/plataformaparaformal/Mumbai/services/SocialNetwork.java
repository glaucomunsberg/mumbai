package com.plataformaparaformal.Mumbai.services;

public enum SocialNetwork {

	account_none(0),account_google(1), account_facebook(2), account_twitter(3);
    private int value;

    private SocialNetwork(int value){
        this.value = value;
    }

    public String getAccount(){
        switch (this){
            case account_facebook:
                return "Facebook";
            case account_google:
                return "Google";
            case account_twitter:
                return "Twitter";
            case account_none:
            default:
                return "";
        }
    }

}
