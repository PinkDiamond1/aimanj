package org.aimanj.protocol.core;

import java.math.BigInteger;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import org.aimanj.protocol.AiManj;
import org.junit.Before;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.aimanj.protocol.core.methods.request.ManFilter;
import org.aimanj.protocol.core.methods.response.ManBlock;
import org.aimanj.protocol.http.HttpService;

import static org.junit.Assert.assertTrue;

/**
 * Flowable callback tests.
 */
public class FlowableIT {
    private static Logger log = LoggerFactory.getLogger(FlowableIT.class);

    private static final int EVENT_COUNT = 5;
    private static final int TIMEOUT_MINUTES = 5;

    private AiManj aiManj;

    @Before
    public void setUp() {
        this.aiManj = AiManj.build(new HttpService());
    }

    @Test
    public void testBlockFlowable() throws Exception {
        run(aiManj.blockFlowable(false));
    }

    @Test
    public void testPendingTransactionFlowable() throws Exception {
        run(aiManj.pendingTransactionFlowable());
    }

    @Test
    public void testTransactionFlowable() throws Exception {
        run(aiManj.transactionFlowable());
    }

    @Test
    public void testLogFlowable() throws Exception {
        run(aiManj.manLogFlowable(new ManFilter()));
    }

    @Test
    public void testReplayFlowable() throws Exception {
        run(aiManj.replayPastBlocksFlowable(
                new DefaultBlockParameterNumber(0),
                new DefaultBlockParameterNumber(EVENT_COUNT), true));
    }

    @Test
    public void testReplayPastAndFutureBlocksFlowable() throws Exception {
        ManBlock manBlock = aiManj.manGetBlockByNumber(DefaultBlockParameterName.LATEST, false)
                .send();
        BigInteger latestBlockNumber = manBlock.getBlock().getNumber();
        run(aiManj.replayPastAndFutureBlocksFlowable(
                new DefaultBlockParameterNumber(latestBlockNumber.subtract(BigInteger.ONE)),
                false));
    }

    private <T> void run(Flowable<T> flowable) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(EVENT_COUNT);
        CountDownLatch completedLatch = new CountDownLatch(EVENT_COUNT);

        Disposable subscription = flowable.subscribe(
                x -> countDownLatch.countDown(),
                Throwable::printStackTrace,
                completedLatch::countDown
        );

        countDownLatch.await(TIMEOUT_MINUTES, TimeUnit.MINUTES);
        subscription.dispose();
        completedLatch.await(1, TimeUnit.SECONDS);

        log.info("CountDownLatch={}, CompletedLatch={}", countDownLatch.getCount(),
                completedLatch.getCount());
        assertTrue(subscription.isDisposed());
    }
}
