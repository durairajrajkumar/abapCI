package abaci.utils;

import static org.junit.Assert.*;

import abapci.domain.UiColor;
import abapci.domain.UiTheme;
import abapci.utils.ColorToThemeMapper;

public class ColorToThemeMapperTest {
	
	@org.junit.Test
	public void mappingCompleteTest() 
	{
	   for(UiColor uiColor : UiColor.values()) 
	   {
		   UiTheme uiTheme = ColorToThemeMapper.mapUiColorToTheme(uiColor);  
		   assertNotNull(uiTheme);
		   if(uiColor != UiColor.DEFAULT)
		   {
			   assertNotEquals(UiTheme.STANDARD_THEME, uiTheme);			   
		   }
	   }
	}
	
	
}
