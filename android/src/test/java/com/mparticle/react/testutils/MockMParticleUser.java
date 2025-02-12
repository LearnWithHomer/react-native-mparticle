package com.mparticle.react.testutils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mparticle.MParticle;
import com.mparticle.UserAttributeListener;
import com.mparticle.commerce.Cart;
import com.mparticle.consent.ConsentState;
import com.mparticle.identity.MParticleUser;

import java.util.Map;

public class MockMParticleUser implements MParticleUser {
    Long mpid = 0L;

    public MockMParticleUser() {}

    public MockMParticleUser(Long mpid) {
        this.mpid = mpid;
    }

    @NonNull
    @Override
    public long getId() {
        return mpid;
    }

    @NonNull
    @Override
    public Cart getCart() {
        return null;
    }

    @NonNull
    @Override
    public Map<String, Object> getUserAttributes() {
        return null;
    }

    @Nullable
    @Override
    public Map<String, Object> getUserAttributes(@Nullable UserAttributeListener userAttributeListener) {
        return null;
    }

    @Override
    public boolean setUserAttributes(@NonNull Map<String, Object> map) {
        return false;
    }

    @NonNull
    @Override
    public Map<MParticle.IdentityType, String> getUserIdentities() {
        return null;
    }

    @Override
    public boolean setUserAttribute(@NonNull String s, @NonNull Object o) {
        return false;
    }

    @Override
    public boolean setUserAttributeList(@NonNull String s, @NonNull Object o) {
        return false;
    }

    @Override
    public boolean incrementUserAttribute(@NonNull String s, int i) {
        return false;
    }

    @Override
    public boolean removeUserAttribute(@NonNull String s) {
        return false;
    }

    @Override
    public boolean setUserTag(@NonNull String s) {
        return false;
    }

    @NonNull
    @Override
    public ConsentState getConsentState() {
        return null;
    }

    @Override
    public void setConsentState(@Nullable ConsentState consentState) {

    }

    @Override
    public boolean isLoggedIn() {
        return false;
    }

    @Override
    public long getFirstSeenTime() {
        return 0;
    }

    @Override
    public long getLastSeenTime() {
        return 0;
    }
}

