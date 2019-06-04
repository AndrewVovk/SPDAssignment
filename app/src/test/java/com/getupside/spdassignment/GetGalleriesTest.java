package com.getupside.spdassignment;

import com.getupside.spdassignment.model.repository.network.GetGalleries;
import com.getupside.spdassignment.model.repository.network.NetworkManager;
import com.getupside.spdassignment.model.repository.network.data.Data;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class GetGalleriesTest {

    @Mock
    private NetworkManager networkManager;
    @Mock
    private Function2<List<Data>, Boolean, Unit> onDataReceived;
    @Mock
    private Function1<String, Unit> onError;

    @InjectMocks
    private GetGalleries provider;

    @Test
    public void whenGetGalleriesCreatedThenNetworkManagerIsQueried() throws InterruptedException {

        TimeUnit.SECONDS.sleep(1);

        verify(networkManager)
                .getGalleries(
                        any(Integer.class),
                        Mockito.<Function1<List<Data>, Unit>>any(),
                        Mockito.<Function1<String, Unit>>any()
                );
    }
}
