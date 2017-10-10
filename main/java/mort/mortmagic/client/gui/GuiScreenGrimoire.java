package mort.mortmagic.client.gui;

import javafx.scene.paint.Material;
import mort.mortmagic.MortMagic;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.*;
import net.minecraft.util.text.event.ClickEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GuiScreenGrimoire extends GuiScreen {

    ResourceLocation backgroundTexture = new ResourceLocation(MortMagic.MODID,"textures/grimoire.png");

    EntityPlayer player;

    List<ITextComponent> cachedComponents;

    private static final int xSize = 256;
    private static final int ySize = 256;

    public GuiScreenGrimoire(EntityPlayer player) {
        this.player = player;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        mc.getTextureManager().bindTexture( backgroundTexture );
        drawTexturedModalRect( (width - xSize)/2, (height-ySize)/2,0, 0, xSize, ySize  );

        String pagestring = "Example string links to [link] test testst. Hovno ";

        cachedComponents = GuiUtilRenderComponents.splitText( parseString(pagestring), 80, fontRenderer, true, true  );
        int offset = 0;
        for( ITextComponent component : cachedComponents ) {
            fontRenderer.drawString(component.getUnformattedText(), 20, 20 + offset * fontRenderer.FONT_HEIGHT, 0);
            offset++;
        }
        //draw buttons and stuff
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    // page parser


    private static final String linkPattern = "\\[\\w+\\]";

    private static final Pattern multiPattern = buildMultimatcher( new String[]{linkPattern} );

    private ITextComponent parseString( String src ){

        ArrayList<ITextComponent> components = new ArrayList<>();

        int pos = 0;
        Matcher m = multiPattern.matcher(src);
        while( m.find() ){
            if( m.start() > pos )
                components.add( new TextComponentString( src.substring(pos,m.start()-1) ) );
            if( m.end(1) > 0 ) {
                components.add( createLinkTextComponent(src.substring(m.start(1), m.end(1))));
            }

            pos = m.end();
        }
        if( pos < src.length() )
            components.add( new TextComponentString( src.substring(pos) ) );
        ITextComponent base = components.get(0);
        for( int i = 1; i<components.size(); i++ )
            base.appendSibling( components.get(i) );
        return base;
    }

    private static Pattern buildMultimatcher( String[] patterns ){
        StringBuilder bld = new StringBuilder();
        for( int i = 0; i<patterns.length; i++ ){
            bld.append( "(" );
            bld.append( patterns[i] );
            bld.append( ")" );
            if(i < patterns.length -1 )
                bld.append( "|" );
        }
        return Pattern.compile( bld.toString() );
    }

    private ITextComponent createLinkTextComponent( String link ){
        return new TextComponentString( link ).setStyle( new Style().setClickEvent( new ClickEvent( ClickEvent.Action.CHANGE_PAGE, link )).setColor( TextFormatting.BLUE ) );
    }

}
