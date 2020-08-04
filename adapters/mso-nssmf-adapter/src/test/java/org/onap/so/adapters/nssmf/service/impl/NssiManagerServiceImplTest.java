package org.onap.so.adapters.nssmf.service.impl;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.onap.so.adapters.nssmf.MSONssmfApplication;
import org.onap.so.adapters.nssmf.entity.NssmfInfo;
import org.onap.so.adapters.nssmf.entity.TokenResponse;
import org.onap.so.adapters.nssmf.enums.HttpMethod;
import org.onap.so.adapters.nssmf.exceptions.ApplicationException;
import org.onap.so.adapters.nssmf.rest.NssmfManager;
import org.onap.so.adapters.nssmf.service.NssiManagerService;
import org.onap.so.adapters.nssmf.util.RestUtil;
import org.onap.so.beans.nsmf.*;
import org.onap.so.db.request.data.repository.ResourceOperationStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.onap.so.adapters.nssmf.util.NssmfAdapterUtil.marshal;
import static org.onap.so.adapters.nssmf.util.NssmfAdapterUtil.unMarshal;
import static org.onap.so.beans.nsmf.NetworkType.CORE;
import static org.onap.so.beans.nsmf.ResourceSharingLevel.NON_SHARED;

@RunWith(SpringRunner.class)
public class NssiManagerServiceImplTest {

    @Mock
    private RestUtil restUtil;


    private NssiManagerServiceImpl nssiManagerService;

    @Mock
    private HttpResponse tokenResponse;

    @Mock
    private HttpEntity tokenEntity;

    @Mock
    private HttpResponse commonResponse;

    @Mock
    private HttpEntity commonEntity;

    @Mock
    private StatusLine statusLine;

    @Mock
    private HttpClient httpClient;

    private InputStream postStream;

    private InputStream tokenStream;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        nssiManagerService = new NssiManagerServiceImpl();

        Field restUtil = nssiManagerService.getClass().getDeclaredField("restUtil");
        restUtil.setAccessible(true);
        restUtil.set(nssiManagerService, this.restUtil);

       // nssiManagerService.setRestUtil(this.restUtil);

        when(this.restUtil.sendRequest(any(String.class), any(HttpMethod.class), any(String.class), any(EsrInfo.class)))
                .thenCallRealMethod();
    }

    private void createCommonMock(int statusCode, NssmfInfo nssmf) throws Exception {

        when(restUtil.sendRequest(any(String.class), any(HttpMethod.class), any(String.class), any(EsrInfo.class)))
                .thenCallRealMethod();
        when(restUtil.getHttpsClient()).thenReturn(httpClient);

        when(statusLine.getStatusCode()).thenReturn(200);
        when(restUtil.getNssmfHost(any(EsrInfo.class))).thenReturn(nssmf);

        when(tokenResponse.getEntity()).thenReturn(tokenEntity);
        when(tokenResponse.getStatusLine()).thenReturn(statusLine);
        when(tokenEntity.getContent()).thenReturn(tokenStream);

        when(commonResponse.getEntity()).thenReturn(commonEntity);
        when(commonResponse.getStatusLine()).thenReturn(statusLine);
        when(commonEntity.getContent()).thenReturn(postStream);

        Answer<HttpResponse> answer = new Answer<HttpResponse>() {

            public HttpResponse answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                if (arguments != null && arguments.length == 1 && arguments[0] != null) {

                    HttpRequestBase base = (HttpRequestBase) arguments[0];
                    if (base.getURI().toString().endsWith("/oauth/token")) {
                        return tokenResponse;
                    } else {
                        return commonResponse;
                    }
                }
                return commonResponse;
            }
        };
        doAnswer(answer).when(httpClient).execute(any(HttpRequestBase.class));
    }

    @Test
    public void testAllocateNssi() throws Exception {

        NssmfInfo nssmf = new NssmfInfo();
        nssmf.setUserName("nssmf-user");
        nssmf.setPassword("nssmf-pass");
        nssmf.setPort("8080");
        nssmf.setIpAddress("127.0.0.1");

        NssiResponse nssiRes = new NssiResponse();
        nssiRes.setJobId("4b45d919816ccaa2b762df5120f72067");
        nssiRes.setNssiId("NSSI-C-001-HDBNJ-NSSMF-01-A-ZX");

        TokenResponse token = new TokenResponse();
        token.setAccessToken("7512eb3feb5249eca5ddd742fedddd39");
        token.setExpires(1800);

        postStream = new ByteArrayInputStream(marshal(nssiRes).getBytes(UTF_8));
        tokenStream = new ByteArrayInputStream(marshal(token).getBytes(UTF_8));

        createCommonMock(200, nssmf);
        // assertEquals(prettyPrint(allocateNssi()), ALLOCATE);
        ResponseEntity res = nssiManagerService.allocateNssi(allocateNssi());
        assertNotNull(res);
        assertNotNull(res.getBody());
        NssiResponse allRes = unMarshal(res.getBody().toString(), NssiResponse.class);
        assertEquals(allRes.getJobId(), "4b45d919816ccaa2b762df5120f72067");
        assertEquals(allRes.getNssiId(), "NSSI-C-001-HDBNJ-NSSMF-01-A-ZX");

        System.out.println(res);
    }

    public NssiAllocateRequest allocateNssi() throws Exception {
        CnSliceProfile sP = new CnSliceProfile();
        List<String> sns = new LinkedList<>();
        sns.add("001-100001");
        List<String> plmn = new LinkedList<>();
        plmn.add("460-00");
        plmn.add("460-01");
        PerfReqEmbbList embb = new PerfReqEmbbList();
        embb.setActivityFactor(50);
        List<PerfReqEmbbList> embbList = new LinkedList<>();
        embbList.add(embb);
        PerfReq perfReq = new PerfReq();
        perfReq.setPerfReqEmbbList(embbList);
        List<String> taList = new LinkedList<>();
        taList.add("1");
        taList.add("2");
        taList.add("3");
        sP.setSnssaiList(sns);
        sP.setSliceProfileId("ab9af40f13f721b5f13539d87484098");
        sP.setPlmnIdList(plmn);
        sP.setPerfReq(perfReq);
        sP.setMaxNumberofUEs(200);
        sP.setCoverageAreaTAList(taList);
        sP.setLatency(6);
        sP.setResourceSharingLevel(NON_SHARED);
        NsiInfo nsiInfo = new NsiInfo();
        nsiInfo.setNsiId("NSI-M-001-HDBNJ-NSMF-01-A-ZX");
        nsiInfo.setNsiName("eMBB-001");
        AllocateCnNssi cnNssi = new AllocateCnNssi();
        cnNssi.setNssiId("NSST-C-001-HDBNJ-NSSMF-01-A-ZX");
        cnNssi.setNssiName("eMBB-001");
        cnNssi.setScriptName("CN1");
        cnNssi.setSliceProfile(sP);
        cnNssi.setNsiInfo(nsiInfo);
        EsrInfo esrInfo = new EsrInfo();
        esrInfo.setVendor("huawei");
        esrInfo.setNetworkType(CORE);
        NssiAllocateRequest allocate = new NssiAllocateRequest();
        allocate.setAllocateCnNssi(cnNssi);
        allocate.setEsrInfo(esrInfo);
        return allocate;
    }
}