package mort.mortmagic.common.potions;

import com.sun.javafx.geom.Vec3f;
import net.minecraft.util.math.Vec3d;

public abstract class PotionColoringHelper {

    public static final Vec3d rubToRgb[] = {
            new Vec3d( 1,1,1 ), new Vec3d(0.163,0.373,0.6), new Vec3d(1,1,0), new Vec3d(0,0.6,0.2),
            new Vec3d( 1,0,0 ), new Vec3d(0.5,0,0.5), new Vec3d(1,0.5,0), new Vec3d(0.2,0.094,0)
    };

    public static int getColorFromAspects( Vec3f aspects ){
        Vec3d result = trilinearInterpolation( rubToRgb, aspects.z, aspects.y, aspects.x );
        return ((int)(result.x*255) << 16 ) + ((int)(result.y*255) << 8 ) + (int)(result.z*255);
    }


    private static Vec3d linearInterpolation( Vec3d a, Vec3d b, float f ){
        return new Vec3d( a.x * (1-f) + b.x * f, a.y * (1-f) + b.y * f, a.z * (1-f) + b.z * f );
    }

    /**
     * i interpolates in numbers, f in characters
     * @param a1
     * @param b1
     * @param a2
     * @param b2
     * @param f
     * @param i
     * @return
     */
    private static Vec3d bilinearInterpolation( Vec3d a1, Vec3d b1, Vec3d a2, Vec3d b2, float f, float i ){
        return linearInterpolation( linearInterpolation(a1,a2,i),linearInterpolation(b1,b2,i),f );
    }
    private static Vec3d trilinearInterpolation( Vec3d[] cube, float f, float i, float z ){
        return linearInterpolation( bilinearInterpolation(cube[0],cube[1],cube[2],cube[3],f,i), bilinearInterpolation(cube[4],cube[5],cube[6],cube[7],f,i), z );
    }
    public static Vec3d getRGB( float rubedo, float auredo, float caerudo ){
        return trilinearInterpolation( rubToRgb, caerudo, auredo, rubedo );
    }

    public static final int TINT_WHITE = 16777215;

}
