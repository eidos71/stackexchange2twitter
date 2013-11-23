package org.tweet.twitter.util;

import java.util.Map;
import java.util.Set;

import com.google.api.client.util.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public final class ErrorUtil {

    public static final Map<String, Set<String>> bannedRegExesMaybeErrors = Maps.newConcurrentMap();
    public static final Map<String, Set<String>> bannedCommercialRegExesMaybeErrors = Maps.newConcurrentMap();

    public static final Map<String, Set<String>> bannedCommercialContainsMaybeErrors = Maps.newConcurrentMap();

    public static final Map<String, Set<String>> bannedContainsMaybeErrorsForAnalysis = Maps.newConcurrentMap();
    public static final Map<String, Set<String>> bannedContainsMaybeErrorsForTweeting = Maps.newConcurrentMap();

    public static final Set<String> rejectedByClassifierJob = Sets.newConcurrentHashSet();

    private ErrorUtil() {
        throw new AssertionError();
    }

    // API

    public static final void registerError(final Set<String> collector, final String error) {
        Preconditions.checkNotNull(collector);
        Preconditions.checkNotNull(error);

        collector.add(error);
    }

    public static final void registerError(final Map<String, Set<String>> collector, final String key, final String error) {
        Preconditions.checkNotNull(collector);
        Preconditions.checkNotNull(error);

        Set<String> existingListForRegex = collector.get(key);
        if (existingListForRegex == null) {
            existingListForRegex = Sets.newConcurrentHashSet();
            collector.put(key, existingListForRegex);
        }
        existingListForRegex.add(error);
    }
}