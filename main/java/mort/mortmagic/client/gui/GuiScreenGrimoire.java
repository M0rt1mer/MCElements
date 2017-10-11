package mort.mortmagic.client.gui;

import mort.mortmagic.Content;
import mort.mortmagic.MortMagic;
import mort.mortmagic.common.ConditionBleeding;
import mort.mortmagic.common.SpellCaster;
import mort.mortmagic.common.grimoire.ICanBeDisplayedInGrimoire;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GuiScreenGrimoire extends GuiScreen {

    ResourceLocation backgroundTexture = new ResourceLocation(MortMagic.MODID,"textures/grimoire.png");

    EntityPlayer player;

    List<List<PositionedText>> cachedComponents;

    private static final int xSize = 256;
    private static final int ySize = 256;


    //runtime values
    ICanBeDisplayedInGrimoire openedOn = Content.chapterRoot;
    int currentDisplayPage = 0;

    public GuiScreenGrimoire(EntityPlayer player) {
        this.player = player;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.addButton( new GuiButton(0, 100, 100, 60, 20, "Previous") );
        this.addButton( new GuiButton(1, 180, 100, 60, 20, "Next") );
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        mc.getTextureManager().bindTexture( backgroundTexture );
        drawTexturedModalRect( (width - xSize)/2, (height-ySize)/2,0, 0, xSize, ySize  );

        final SpellCaster playerSpellcasting = SpellCaster.getPlayerSpellcasting(Minecraft.getMinecraft().player);
        cachedComponents = typesetText( parseString( openedOn.getTranslatedText( playerSpellcasting.knownPages ) ), new Rectangle( 20, 20, 80, 128 ) );
        for( PositionedText component : cachedComponents.get(currentDisplayPage) )
            fontRenderer.drawString(component.text.toString(), component.position.x, component.position.y, 0);

        //draw buttons and stuff
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    // page parser


    private static final String linkPattern = "\\[\\w+\\]";
    private static final String newLinePattern = "\\n";

    private static final Pattern multiPattern = buildMultimatcher( new String[]{linkPattern, newLinePattern} );

    private Iterable<FormattedText> parseString( String src ){

        ArrayList<FormattedText> components = new ArrayList<>();

        int pos = 0;
        Matcher m = multiPattern.matcher(src);
        while( m.find() ){
            if( m.start() > pos )
                components.add( new FormattedText( src.substring(pos,m.start()-1) ) );
            if( m.end(1) > 0 ) {
                components.add( createLinkTextComponent(src.substring(m.start(1), m.end(1))));
            }
            if( m.end(2) > 0 ) {
                components.add( NEW_LINE );
            }
            pos = m.end();
        }
        if( pos < src.length() )
            components.add( new FormattedText( src.substring(pos) ) );

        return  components;
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

    private FormattedText createLinkTextComponent( String link ){
        return new FormattedText( link, new Style().setClickEvent( new ClickEvent( ClickEvent.Action.CHANGE_PAGE, link )).setColor( TextFormatting.BLUE ) );
    }

    private static class TypesettingProgress{
        public List<List<PositionedText>> pages;
        public List<PositionedText> currentPage;
        public int line = 0;
        public int lineOffset = 0;

        public int lineHeight;
        public Rectangle bounds;

        public TypesettingProgress( int lineHeight, Rectangle bounds ) {
            pages = new ArrayList<>();
            currentPage = new ArrayList<>();
            pages.add( currentPage );
            line = 0;
            lineOffset = 0;
            this.lineHeight = lineHeight;
            this.bounds = bounds;
        }

        public void newLine(){
            line += 1;
            lineOffset = 0;
            if( line >= bounds.height/lineHeight ){
                line = 0;
                currentPage = new ArrayList<>();
                pages.add(currentPage);
            }
        }

        public void forceAppendText( FormattedText text, int textWidth ){
            currentPage.add(new PositionedText(text, new Rectangle(bounds.x + lineOffset, bounds.y + line * lineHeight, textWidth, lineHeight) ) );
            lineOffset += textWidth;
        }
    }

    private List<List<PositionedText>> typesetText( Iterable<FormattedText> sourceText, Rectangle rect ){

        int lineHeight = fontRenderer.FONT_HEIGHT;
        TypesettingProgress progress = new TypesettingProgress( lineHeight, rect );

        for( FormattedText text : sourceText ){
            if( text == NEW_LINE )
                progress.newLine();
            else {

                int tWidth = fontRenderer.getStringWidth(text.getUnformattedText());
                if (progress.lineOffset + tWidth < rect.width)
                    progress.forceAppendText( text, tWidth );
                else {
                    String[] words = text.getUnformattedText().split(" " );
                    for( String word : words ){
                        tWidth = fontRenderer.getStringWidth( word );
                        if (progress.lineOffset + tWidth < rect.width)
                            progress.forceAppendText( new FormattedText( word, text.style ), tWidth );
                        else if( tWidth < rect.width ){ //if theoretically can fit in the bouds
                            progress.newLine();
                            progress.forceAppendText( new FormattedText( word, text.style ), tWidth );
                        }
                        else{ //cannot fit even if it had the line for itself
                            while( !word.isEmpty() ){
                                tWidth = fontRenderer.getStringWidth(word);
                                if( progress.lineOffset + tWidth < rect.width )
                                    progress.forceAppendText( new FormattedText(word,text.style), tWidth );
                                else {
                                    String trimmed = fontRenderer.trimStringToWidth(word, rect.width - progress.lineOffset);
                                    progress.forceAppendText(new FormattedText(trimmed.substring(0, -1) + "-", text.style), rect.width - progress.lineOffset);
                                    progress.newLine();
                                    word = word.substring(trimmed.length());
                                }
                            }

                        }
                    }
                }
            }
        }
        return progress.pages;
    }



    private static class PositionedText{
        public final FormattedText text;
        public final Rectangle position;

        public PositionedText(FormattedText text, Rectangle position) {
            this.text = text;
            this.position = position;
        }
    }

    private static class FormattedText{
        public final String text;
        public final Style style;

        public FormattedText(String text, Style style) {
            this.text = text;
            this.style = style;
        }

        public FormattedText(String text) {
            this.text = text;
            this.style = new Style();
        }

        public String toString(){
            StringBuilder bld = new StringBuilder();
            if(style != null && !style.isEmpty())
                bld.append( style.getFormattingCode() );
            bld.append(text);
            if(style != null && !style.isEmpty())
                bld.append( TextFormatting.RESET );
            return bld.toString();
        }

        public String getUnformattedText(){
            return text;
        }
    }

    //formatting constants
    private static final FormattedText NEW_LINE = new FormattedText("", new Style());


}
