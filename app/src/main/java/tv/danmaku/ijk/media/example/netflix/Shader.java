package tv.danmaku.ijk.media.example.netflix;

import java.util.HashMap;
import java.util.Map;

public class Shader {
    private static Map<ShaderVar, String> varsToNames;

    public interface FragmentShader {
        String getFragmentShaderString();
    }

    public enum ShaderVar {
        V_ASPECT_RATIO,
        V_LETTERBOX_PERCENTAGE,
        SCALE,
        H_PADDING_PERCENT,
        V_SHIFT_PERCENT,
        V_PADDING_PERCENT
    }

    public interface VertexShader {
        String getVertexShaderString();
    }

    static {
        HashMap hashMap = new HashMap();
        varsToNames = hashMap;
        hashMap.put(ShaderVar.V_ASPECT_RATIO, "vAspectRatio");
        varsToNames.put(ShaderVar.V_LETTERBOX_PERCENTAGE, "vLetterboxPercentage");
        varsToNames.put(ShaderVar.SCALE, "videoScale");
        varsToNames.put(ShaderVar.H_PADDING_PERCENT, "hPaddingPercentage");
        varsToNames.put(ShaderVar.V_SHIFT_PERCENT, "vShiftPercentage");
        varsToNames.put(ShaderVar.V_PADDING_PERCENT, "vPaddingPercentage");
    }

    public static String getVarName(ShaderVar shaderVar) {
        return (String) varsToNames.get(shaderVar);
    }
}
