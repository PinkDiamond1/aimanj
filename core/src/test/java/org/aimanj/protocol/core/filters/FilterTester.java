package org.aimanj.protocol.core.filters;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import org.aimanj.protocol.AiManj;
import org.junit.Before;

import org.aimanj.protocol.ObjectMapperFactory;
import org.aimanj.protocol.AiManjService;
import org.aimanj.protocol.core.Request;
import org.aimanj.protocol.core.methods.response.ManFilter;
import org.aimanj.protocol.core.methods.response.ManLog;
import org.aimanj.protocol.core.methods.response.ManUninstallFilter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public abstract class FilterTester {

    private AiManjService aiManjjService;
    AiManj aiManj;

    final ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
    final ScheduledExecutorService scheduledExecutorService =
            Executors.newSingleThreadScheduledExecutor();

    @Before
    public void setUp() {
        aiManjjService = mock(AiManjService.class);
        aiManj = AiManj.build(aiManjjService, 1000, scheduledExecutorService);
    }

    <T> void runTest(ManLog manLog, Flowable<T> flowable) throws Exception {
        ManFilter manFilter = objectMapper.readValue(
                "{\n"
                        + "  \"id\":1,\n"
                        + "  \"jsonrpc\": \"2.0\",\n"
                        + "  \"result\": \"0x1\"\n"
                        + "}", ManFilter.class);

        ManUninstallFilter manUninstallFilter = objectMapper.readValue(
                "{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":true}", ManUninstallFilter.class);

        ManLog notFoundFilter = objectMapper.readValue(
                "{\"jsonrpc\":\"2.0\",\"id\":1,"
                + "\"error\":{\"code\":-32000,\"message\":\"filter not found\"}}",
                ManLog.class);

        @SuppressWarnings("unchecked")
        List<T> expected = createExpected(manLog);
        Set<T> results = Collections.synchronizedSet(new HashSet<T>());

        CountDownLatch transactionLatch = new CountDownLatch(expected.size());

        CountDownLatch completedLatch = new CountDownLatch(1);

        when(aiManjjService.send(any(Request.class), eq(ManFilter.class)))
                .thenReturn(manFilter);
        when(aiManjjService.send(any(Request.class), eq(ManLog.class)))
            .thenReturn(manLog).thenReturn(notFoundFilter).thenReturn(manLog);
        when(aiManjjService.send(any(Request.class), eq(ManUninstallFilter.class)))
                .thenReturn(manUninstallFilter);

        Disposable subscription = flowable.subscribe(
                result -> {
                    results.add(result);
                    transactionLatch.countDown();
                },
                throwable -> fail(throwable.getMessage()),
                () -> completedLatch.countDown());

        transactionLatch.await(1, TimeUnit.SECONDS);
        assertThat(results, equalTo(new HashSet<>(expected)));

        subscription.dispose();

        completedLatch.await(1, TimeUnit.SECONDS);
        assertTrue(subscription.isDisposed());
    }

    List createExpected(ManLog manLog) {
        List<ManLog.LogResult> logResults = manLog.getLogs();
        if (logResults.isEmpty()) {
            fail("Results cannot be empty");
        }

        return manLog.getLogs().stream()
                .map(t -> t.get()).collect(Collectors.toList());
    }
}
