package mort.mortmagic.runes;

/**
 * Represents a single rune, like "Ra". Placeable runes are "BlockRune"
 */
public class RuneCharacter {

    public final String name;

    public RuneCharacter(String name) {
        this.name = name;
    }

    public static final RuneCharacter ra = new RuneCharacter("ra");
    public static final RuneCharacter ort = new RuneCharacter("ort");
    public static final RuneCharacter dol = new RuneCharacter( "dol" );

}
