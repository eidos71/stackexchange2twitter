package org.gplus.stackexchange;

import org.junit.Test;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.google.api.impl.GoogleTemplate;
import org.springframework.social.google.connect.GoogleConnectionFactory;

public class GooglePlusLiveTest {

    @Test
    public final void when_then() {
        new GoogleTemplate("AIzaSyBe3NudfLPIuYnIZ2GR4pbowiFukwYb1S8");
    }

    // util

    final ConnectionFactoryLocator connectionFactoryLocator() {
        final ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
        registry.addConnectionFactory(new GoogleConnectionFactory("108554000280856523466", "AIzaSyDWrdZu9FHLP2OMTGAT1TtmlGDnM0H_uJ0"));
        return registry;
    }

}
