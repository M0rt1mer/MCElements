package mort.mortmagic.api;

import com.google.common.collect.ImmutableList;
import mort.mortmagic.common.runes.RuneCharacter;
import mort.mortmagic.common.runes.RuneWord;
import net.minecraftforge.fml.common.registry.GameRegistry;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.List;

public class RuneDictionary {

    List<DictionaryEntry> list = new ArrayList<>();

    public void register( List<RuneCharacter> seq, RuneWord word ){
        if(!GameRegistry.findRegistry(RuneWord.class).containsValue(word) )
            throw new IllegalArgumentException( "Given runeword (" + word + ") is not registered" );

        if( findMinimalLoop( seq ) != seq.size() )
            throw new IllegalArgumentException( "Sequence contains a repeated subsequence" );

        for( DictionaryEntry entry : list ){
            if( cyclicCompare( entry.sequence, seq ) ){
                throw new IllegalArgumentException( "Character sequence is interchangeable with already existing sequence" );
            }
        }

        list.add( new DictionaryEntry( ImmutableList.copyOf( seq ), word ) );
    }

    public void register( RuneCharacter[] seq, RuneWord word ){
        register( Arrays.asList(seq),word );
    }

    private class DictionaryEntry{

        public final ImmutableList<RuneCharacter> sequence;
        public final RuneWord word;

        public DictionaryEntry(ImmutableList<RuneCharacter> sequence, RuneWord word) {
            this.sequence = sequence;
            this.word = word;
        }
    }

    public RuneWord findMatchingWord( List<RuneCharacter> seq ){
        for( DictionaryEntry entry : list )
            if( cyclicCompare( entry.sequence, seq, findMinimalLoop(seq) ) )
                return entry.word;

        return null;
    }

    public static boolean cyclicCompare( List<RuneCharacter> first, List<RuneCharacter> second ){
        return cyclicCompare( first, second, second.size() );
    }

    /**
     * Compares two sequences, if they are cyclicly equal (they are equal in some offset).
     * Uses only first "secondLength" characters of "second"
     * @param first
     * @param second
     * @return
     */
    public static boolean cyclicCompare( List<RuneCharacter> first, List<RuneCharacter> second, int secondLength ){

        if( first.size() != secondLength )
            return false;
        int length = first.size();

        offset:
        for( int offset = 0; offset < (length - 1); offset++ ){ //offset of first.size() equals offset of 0 - safela ignore
            for( int i = 0; i<length;i++ ){
                if( first.get( (i + offset)%length ) != second.get( i ) ){
                    continue offset;
                }
            }
            return true;
        }

        return false;
    }

    /**
     * Finds the shortest repeating subsequence in given sequence (such that the entire sequence consists of only repeats of found subsequence)
     * @param sequence
     * @return length of found subsequence
     */
    public static int findMinimalLoop( List<RuneCharacter> sequence ){

        candidateLength:
        for( int candidateLength = 1; candidateLength < sequence.size()/2; candidateLength++ ){
            if( sequence.size() % candidateLength > 0 )
                continue candidateLength;

            for( int i = candidateLength; i < sequence.size(); i++  ){ //go through all characters after first subsequence
                if( sequence.get( i % candidateLength ) != sequence.get( i ) ) //compare with first occurence
                    continue candidateLength;
            }
            return candidateLength;
        }
        return sequence.size();
    }

}
