package abapci.utils;

import java.util.ArrayList;
import java.util.List;

import com.sap.adt.atc.model.atcfinding.IAtcFinding;
import com.sap.adt.atc.model.atcfinding.IAtcFindingList;
import com.sap.adt.atc.model.atcobject.IAtcObject;
import com.sap.adt.atc.model.atcworklist.IAtcWorklist;

import abapci.domain.InvalidItem;
import abapci.domain.TestResult;
import abapci.domain.TestState;
import abapci.views.ViewModel;

public class AtcResultAnalyzer {

	public static TestResult getTestResult(IAtcWorklist atcWorklist) {
		TestResult testResult;

		if (atcWorklist == null) {
			testResult = new TestResult();
		} else {
			testResult = new TestResult(true, getInvalidItems(atcWorklist));
		}

		return testResult;
	}

	private static List<InvalidItem> getInvalidItems(IAtcWorklist atcWorklist) {
		List<InvalidItem> invalidItems = new ArrayList<>();

		for (IAtcObject object : atcWorklist.getObjects().getObject()) {
			IAtcFindingList findingList = object.getFindings();
			for (IAtcFinding finding : findingList.getFinding()) {
				if (finding.getPriority() == 1) {
					// TODO Adapt check for isSuppressed
					String className = finding.getParentUri();
					boolean isSuppressed = ViewModel.INSTANCE.getSuppressions().stream()
							.anyMatch(item -> item.getClassName() == className);
					
					invalidItems.add(new InvalidItem(finding.getName(), finding.getDescription(), isSuppressed));
				}
			}
		}
		return invalidItems;
	}

	@Deprecated
	public static TestState getTestState(IAtcWorklist atcWorklist) {
		TestState testState;

		if (atcWorklist == null) {
			testState = TestState.UNDEF;
		} else if (getNumOfFindings(atcWorklist) > 0) {
			testState = TestState.NOK;
		} else {
			testState = TestState.OK;
		}
		return testState;
	}

	@Deprecated
	public static int getNumOfFindings(IAtcWorklist atcWorklist) {
		int numFindings = 0;

		for (IAtcObject object : atcWorklist.getObjects().getObject()) {
			IAtcFindingList findingList = object.getFindings();
			for (IAtcFinding finding : findingList.getFinding()) {
				if (finding.getPriority() == 1) {
					numFindings = numFindings + 1;
				}
			}
		}
		return numFindings;
	}

	public static String getOutputLabel(IAtcWorklist atcWorklist) {
		String outputLabel;
		if (atcWorklist == null) {
			outputLabel = TestState.UNDEF.toString();
		} else {
			int numFindings = getNumOfFindings(atcWorklist);
			if (numFindings > 0) {
				outputLabel = TestState.NOK.toString();
			} else {
				outputLabel = "Findings: " + numFindings;
			}
		}
		return outputLabel;
	}

}
