package org.tweet.meta.service;

import java.util.List;

import org.classification.spring.ClassificationConfig;
import org.common.service.LinkLiveService;
import org.common.spring.CommonContextConfig;
import org.common.spring.CommonPersistenceJPAConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.keyval.spring.KeyValPersistenceJPAConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.stackexchange.util.SimpleTwitterAccount;
import org.tweet.meta.spring.TwitterMetaConfig;
import org.tweet.meta.spring.TwitterMetaPersistenceJPAConfig;
import org.tweet.spring.TwitterConfig;
import org.tweet.spring.TwitterLiveConfig;
import org.tweet.twitter.service.TwitterLiveService;

import com.google.common.collect.Lists;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {// @formatter:off
    KeyValPersistenceJPAConfig.class, 
        
    CommonPersistenceJPAConfig.class, 
    CommonContextConfig.class, 
    
    ClassificationConfig.class,
    
    TwitterConfig.class, 
    TwitterLiveConfig.class,
    TwitterMetaPersistenceJPAConfig.class, 
        
    TwitterMetaConfig.class 
}) // @formatter:on
public class RetweetScoresTuningLiveTest {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TwitterLiveService twitterService;

    @Autowired
    private LinkLiveService linkService;

    // tests

    @Test
    public final void whenContextIsBootstrapped_thenNoException() {
        //
    }

    /*
     * AspnetDaily - Twitter introduces a weird link to ASP.NET (which should not be a link)
    */
    @Test
    public final void whenOneAccountIsAnalyzed_thenScoreSuggestionsAreGiven() {
        analyzeScoresForAccount(SimpleTwitterAccount.AspnetDaily);
    }

    @Test
    public final void whenAllAccountsAreAnalyzed_thenScoreSuggestionsAreGiven() {
        for (final SimpleTwitterAccount account : SimpleTwitterAccount.values()) {
            analyzeScoresForAccount(account);
        }
    }

    private void analyzeScoresForAccount(final SimpleTwitterAccount account) {
        int numberOfTweetsRetrieved;
        final List<String> latestTweetsOnAccount = twitterService.listTweetsOfInternalAccount(account.name(), 12);
        numberOfTweetsRetrieved = latestTweetsOnAccount.size();

        final List<String> relevantDomains = Lists.newArrayList("http://stackoverflow.com/", "http://askubuntu.com/", "http://superuser.com/");
        final int totalRelevantLinks = linkService.countLinksToAnyDomain(latestTweetsOnAccount, relevantDomains);
        final int totalLinksNotToSo = numberOfTweetsRetrieved - totalRelevantLinks;

        logger.warn("Number of links not to SO for account= " + account.name() + " is= " + totalLinksNotToSo);
        System.out.println("Number of links not to SO for account= " + account.name() + " is= " + totalLinksNotToSo);
        if (totalLinksNotToSo <= 2) {
            logger.warn("Scores (minrt) are probably to HIGH for account= " + account.name());
            System.out.println("Scores (minrt) are probably to HIGH for account= " + account.name());
        } else if (totalLinksNotToSo >= 6) {
            logger.warn("Scores (minrt) are probably to LOW for account= " + account.name());
            System.out.println("Scores (minrt) are probably to LOW for account= " + account.name());
        } else {
            logger.warn("Scores (minrt) look OK for account= " + account.name());
            System.out.println("Scores (minrt) look OK for account= " + account.name());
        }
    }
}