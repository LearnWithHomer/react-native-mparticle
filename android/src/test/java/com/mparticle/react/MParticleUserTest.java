package com.mparticle.react;

import android.support.annotation.NonNull;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.mparticle.MParticle;
import com.mparticle.identity.IdentityApi;
import com.mparticle.identity.MParticleUser;
import com.mparticle.react.testutils.MockMParticleUser;
import com.mparticle.react.testutils.MockReadableArray;
import com.mparticle.react.testutils.Mutable;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class MParticleUserTest {
    MParticleModule mParticleUser;
    Random random = new Random();

    @Before
    public void before() {
        MParticle.setInstance(Mockito.mock(MParticle.class));
        Mockito.when(MParticle.getInstance().Identity()).thenReturn(Mockito.mock(IdentityApi.class));
        Mockito.when(MParticle.getInstance().Identity().getUser(null)).thenReturn(null);
        mParticleUser = new MParticleModule(Mockito.mock(ReactApplicationContext.class));
    }

    @Test
    public void tesNullMParticleUserSetters() {
        Exception exception = null;
        try {
            mParticleUser.setUserAttribute(null, "key", "values");
            mParticleUser.setUserTag(null, "test");
            mParticleUser.setUserAttributeArray(null, "keuy", new MockReadableArray());
        } catch (Exception e) {
            exception = e;
        }
        assertNull(exception);
    }

    @Test
    public void testSetUserAttribute() {
        String testKey = "test key";
        String testValue = "test value";

        final Mutable<String> uaKey = new Mutable<>();
        final Mutable<Object> uaValue = new Mutable<>();
        MParticleUser mockUser = new MockMParticleUser() {
            @Override
            public boolean setUserAttribute(@NonNull String s, @NonNull Object o) {
                uaKey.value = s;
                uaValue.value = o;
                return true;
            }
        };
        Mockito.when(MParticle.getInstance().Identity().getUser(Mockito.anyLong())).thenReturn(mockUser);

        mParticleUser.setUserAttribute("1", testKey, testValue);

        assertEquals(testKey, uaKey.value);
        assertEquals(testValue, uaValue.value);
    }

    @Test
    public void getUserIdentitiesTest() {
        final Map<MParticle.IdentityType, String> identities = new HashMap<>();
        for (MParticle.IdentityType identityType: MParticle.IdentityType.values()) {
            identities.put(identityType, random.nextInt() + "");
        }
        MParticleUser mockUser = new MockMParticleUser() {
            @NonNull
            @Override
            public Map<MParticle.IdentityType, String> getUserIdentities() {
                return identities;
            }
        };

        Mockito.when(MParticle.getInstance().Identity().getUser(Mockito.anyLong())).thenReturn(mockUser);

        final Mutable<Map<MParticle.IdentityType, String>> callbackResult = new Mutable<>();
        mParticleUser.getUserIdentities("1", new Callback() {
            @Override
            public void invoke(Object... args) {
                assertEquals(1, args.length);
                callbackResult.value = (Map<MParticle.IdentityType,String>)args[0];
            }
        });

        assertNotNull(callbackResult.value);
        assertEquals(identities.size(), callbackResult.value.size());

        for (Map.Entry<MParticle.IdentityType, String> entry: identities.entrySet()) {
            assertEquals(entry.getValue(), callbackResult.value.get(entry.getKey()));
        }
    }

    @Test
    public void testGetUserIdentitiesNullUser() {
        final Mutable<Boolean> callbackInvoked = new Mutable<>(false);

        mParticleUser.getUserIdentities(null, new Callback() {
            @Override
            public void invoke(Object... args) {
                assertEquals(0, args.length);
                callbackInvoked.value = true;
            }
        });

        assertTrue(callbackInvoked.value);
    }

    @Test
    public void testSetUserTag() {
        final Mutable<String> tag = new Mutable<>();
        String testTag = "testTag";

        MParticleUser mockUser = new MockMParticleUser() {
            @Override
            public boolean setUserTag(@NonNull String s) {
                tag.value = s;
                return true;
            }
        };

        Mockito.when(MParticle.getInstance().Identity().getUser(Mockito.anyLong())).thenReturn(mockUser);

        mParticleUser.setUserTag("1", testTag);

        assertEquals(testTag, tag.value);
    }



    @Test
    public void getFirstSeenTime() {
        final Mutable<Boolean> callbackInvoked = new Mutable<>(false);

        MParticleUser mockUser = new MockMParticleUser() {
            @Override
            public long getFirstSeenTime() {
                return 2;
            }
        };
        Mockito.when(MParticle.getInstance().Identity().getUser(Mockito.anyLong())).thenReturn(mockUser);

        mParticleUser.getFirstSeen("1", new Callback() {
            @Override
            public void invoke(Object... args) {
                assertEquals(1, args.length);
                assertEquals("2", args[0]);
                callbackInvoked.value = true;
            }
        });

        assertTrue(callbackInvoked.value);
    }

    @Test
    public void getFirstSeenTimeNullUser() {
        final Mutable<Boolean> callbackInvoked = new Mutable<>(false);

        mParticleUser.getFirstSeen(null, new Callback() {
            @Override
            public void invoke(Object... args) {
                assertEquals(0, args.length);
                callbackInvoked.value = true;
            }
        });

        assertTrue(callbackInvoked.value);
    }

    @Test
    public void getLastSeenTime() {
        final Mutable<Boolean> callbackInvoked = new Mutable<>(false);

        MParticleUser mockUser = new MockMParticleUser() {
            @Override
            public long getLastSeenTime() {
                return 2;
            }
        };
        Mockito.when(MParticle.getInstance().Identity().getUser(Mockito.anyLong())).thenReturn(mockUser);

        mParticleUser.getLastSeen("1", new Callback() {
            @Override
            public void invoke(Object... args) {
                assertEquals(1, args.length);
                assertEquals("2", args[0]);
                callbackInvoked.value = true;
            }
        });

        assertTrue(callbackInvoked.value);
    }

    @Test
    public void getLastSeenTimeNullUser() {
        final Mutable<Boolean> callbackInvoked = new Mutable<>(false);

        mParticleUser.getLastSeen(null , new Callback() {
            @Override
            public void invoke(Object... args) {
                assertEquals(0, args.length);
                callbackInvoked.value = true;
            }
        });

        assertTrue(callbackInvoked.value);
    }
}
