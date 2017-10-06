package abapci.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;

import abapci.domain.GlobalTestState;

public class AbapCiDashboardView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "abapci.views.AbapCiDashboardView";

	public AbapCiDashboardView() {
		ViewModel.INSTANCE.getOverallTestState();
	}

	public void createPartControl(Composite parent) {
		
		FillLayout fillLayout = new FillLayout();
		fillLayout.type = SWT.VERTICAL;
		parent.setLayout(fillLayout);
		Label lblOverallTestState = new Label(parent, SWT.BORDER);
		ViewModel.INSTANCE.setLblOverallTestState(lblOverallTestState);

		FontData[] fontData = lblOverallTestState.getFont().getFontData();
		fontData[0].setHeight(16);
		lblOverallTestState.setFont(new Font(Display.getCurrent(), fontData[0]));
		lblOverallTestState.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, true));

		GlobalTestState initialGlobalTestState = new GlobalTestState();
		ViewModel.INSTANCE.setGlobalTestState(initialGlobalTestState);
		lblOverallTestState.setText(initialGlobalTestState.getTestStateOutputForDashboard());
		lblOverallTestState.setBackground(initialGlobalTestState.getColor());

	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub	
	}

}