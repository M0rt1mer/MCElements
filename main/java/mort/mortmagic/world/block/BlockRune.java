package mort.mortmagic.world.block;

import mort.mortmagic.runes.RuneCharacter;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockRune extends Block {

    RuneCharacter runeCharacter;

    public BlockRune(Material p_i45394_1_, RuneCharacter runeCharacter ) {
        super(p_i45394_1_);
        this.runeCharacter = runeCharacter;
    }


}
