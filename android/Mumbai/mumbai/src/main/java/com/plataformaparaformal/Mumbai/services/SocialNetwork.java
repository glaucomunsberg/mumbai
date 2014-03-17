package com.plataformaparaformal.Mumbai.services;

public enum SocialNetwork {

	account_google(0), account_facebook(1), account_twitter(2);
    private int value;

    private SocialNetwork(int value){
        this.value = value;
    }

}
