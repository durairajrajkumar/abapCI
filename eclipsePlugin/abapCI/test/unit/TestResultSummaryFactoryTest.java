package unit;

import org.junit.Assert;
import org.junit.Test;
import org.easymock.*;

import com.sap.adt.tools.abapsource.abapunit.IAbapUnitAlert;
import abapci.Domain.TestResultSummary;
import abapci.Domain.TestState;
import abapci.result.TestResultSummaryFactory;

public class TestResultSummaryFactoryTest extends EasyMockSupport {

	final String TEST_PACKAGE_NAME = "TEST_PACKAGE"; 

	AbapUnitResultMock unitResultMock = new AbapUnitResultMock(); 
	IAbapUnitAlert abapUnitAlertMock = new AbapUnitAlertMock(); 
	AbapUnitResultItemMock abapUnitResultItemMock = new AbapUnitResultItemMock(); 
	AbapUnitResultItemMock abapUnitResultItemSubMock = new AbapUnitResultItemMock(); 
	AbapUnitResultItemMock abapUnitResultItemSubSubMock = new AbapUnitResultItemMock(); 

	
	@Test
	public void testNoError() {
		abapUnitResultItemMock.addChildItem(abapUnitResultItemSubMock); 
		unitResultMock.addItem(abapUnitResultItemMock);  

		TestResultSummary testResultSummary = TestResultSummaryFactory.create(TEST_PACKAGE_NAME, unitResultMock);
		Assert.assertEquals(TestState.OK, testResultSummary.getTestState()); 
	}

	public void testSubitemsNoError() {

		TestResultSummary testResultSummary = TestResultSummaryFactory.create(TEST_PACKAGE_NAME, unitResultMock);
		Assert.assertEquals(TestState.OK, testResultSummary.getTestState()); 
	}
	
	@Test
	public void testOneError() {
		unitResultMock.addAlert(abapUnitAlertMock);

		TestResultSummary testResultSummary = TestResultSummaryFactory.create(TEST_PACKAGE_NAME, unitResultMock);
		Assert.assertEquals(TestState.NOK, testResultSummary.getTestState()); 
	}

	@Test
	public void testOneSubError() {
		abapUnitResultItemMock.setAlert(abapUnitAlertMock); 
		unitResultMock.addItem(abapUnitResultItemMock);  

		TestResultSummary testResultSummary = TestResultSummaryFactory.create(TEST_PACKAGE_NAME, unitResultMock);
		Assert.assertEquals(TestState.NOK, testResultSummary.getTestState()); 
	}

	@Test
	public void testOneSubSubError() {
		abapUnitResultItemSubMock.setAlert(abapUnitAlertMock);  
		abapUnitResultItemMock.addChildItem(abapUnitResultItemSubMock); 
		unitResultMock.addItem(abapUnitResultItemMock);  

		TestResultSummary testResultSummary = TestResultSummaryFactory.create(TEST_PACKAGE_NAME, unitResultMock);
		Assert.assertEquals(TestState.NOK, testResultSummary.getTestState()); 
	}

	@Test
	public void testOneSubSubSubError() {
		abapUnitResultItemSubSubMock.setAlert(abapUnitAlertMock);  
		abapUnitResultItemSubMock.addChildItem(abapUnitResultItemSubSubMock);  
		abapUnitResultItemMock.addChildItem(abapUnitResultItemSubMock); 
		unitResultMock.addItem(abapUnitResultItemMock);  

		TestResultSummary testResultSummary = TestResultSummaryFactory.create(TEST_PACKAGE_NAME, unitResultMock);
		Assert.assertEquals(TestState.NOK, testResultSummary.getTestState()); 
	}

}


