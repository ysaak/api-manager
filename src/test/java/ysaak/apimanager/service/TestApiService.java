package ysaak.apimanager.service;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ysaak.apimanager.AbstractTest;
import ysaak.apimanager.exception.EntityInvalidFieldsException;
import ysaak.apimanager.exception.EntityNotFoundException;
import ysaak.apimanager.model.Api;

import java.util.Collection;

@RunWith(JUnitParamsRunner.class)
@SpringBootTest
public class TestApiService extends AbstractTest {

    @Autowired
    private ApiService apiService;

    @Test
    public void testCreate_success() throws EntityInvalidFieldsException {
        // Given
        final Api newApi = new Api("TEST-CODE", "Test name", "Test description");

        // When
        final Api storedApi = apiService.create(newApi);

        // Then
        Assert.assertEquals(newApi, storedApi);
    }

    @Test(expected = EntityInvalidFieldsException.class)
    @Parameters(method = "testCreate_failure_dataSource")
    public void testCreate_failure(Api apiToCreate) throws EntityInvalidFieldsException {
        // Given
        // Parameters

        // When
        apiService.create(apiToCreate);

        // Then
        // Exception thrown
    }

    private Object[] testCreate_failure_dataSource() {
        return new Object[] {
                // Code too short
                new Object[] { new Api("TEST", "Test name", null) },
                // Code too long
                new Api(StringUtils.repeat('a', 31), "Test name", null),
                // Description too long
                new Api("TEST-CODE", "Test name", StringUtils.repeat("a", 256)),
                // Existing code
                new Api("TEST-API-10001", "TEST API 10001", "A description")
        };
    }

    @Test
    public void testFindAll() {
        // Given
        int expectedSize = 2;

        // When
        Collection<Api> apiCollection = apiService.findAll();

        // Then
        Assert.assertNotNull(apiCollection);
        Assert.assertEquals(expectedSize, apiCollection.size());
    }

    @Test
    public void testFindOne_success() throws EntityNotFoundException {
        // Given
        String code = "TEST-API-10001";
        Api expectedApi = new Api("TEST-API-10001", "TEST API 10001", "A description");

        // When
        Api actualApi = apiService.findByCode(code);

        // Then
        Assert.assertNotNull(actualApi);
        Assert.assertEquals(expectedApi, actualApi);
    }

    @Test(expected = EntityNotFoundException.class)
    @Parameters(method = "testFindOne_failure_dataSource")
    public void testFindOne_failure(String code) throws EntityNotFoundException {
        // Given
        // Parameters

        // When
        apiService.findByCode(code);

        // Then
        // Exception thrown
    }

    private Object[] testFindOne_failure_dataSource() {
        return new Object[] {
                // Unknown code
                new Object[] { "TEST-API-xxxxx" },
                // null
                null
        };
    }


}
