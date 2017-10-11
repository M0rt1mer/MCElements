package mort.mortmagic.common.grimoire;

import java.util.HashMap;
import java.util.Set;

public interface ICanBeDisplayedInGrimoire {

    public String getTranslatedText( HashMap<GrimoirePage,Byte> knownPages );

}
