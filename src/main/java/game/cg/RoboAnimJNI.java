// O pacote DEVE ser este: a biblioteca nativa (bindings/jni/src/robo_anim_jni.cpp)
// exporta as funcoes com o nome mangled "Java_game_cg_RoboAnimJNI_*", entao o
// nome totalmente qualificado da classe precisa ser game.cg.RoboAnimJNI, senao
// o System.loadLibrary/native binding falha com UnsatisfiedLinkError.
package game.cg;

import java.nio.ByteBuffer;

public class RoboAnimJNI {
    
    // Carrega a biblioteca compilada (.so no Linux, .dll no Windows)
    static {
        System.loadLibrary("robo_anim_jni");
    }

    // O contexto C++ (RoboAnimContext*) é guardado no Java como um número 'long'
    public native long create(int width, int height);
    
    public native void destroy(long contextPtr);
    
    public native boolean loadScript(long contextPtr, String scriptText);
    
    public native boolean step(long contextPtr, float deltaTimeSeconds);
    
    // Recebe um Direct ByteBuffer onde o C++ vai escrever os pixels
    public native void render(long contextPtr, ByteBuffer outBuffer, boolean bgra);
    
    public native boolean isFinished(long contextPtr);
    
    public native String getLastError(long contextPtr);
}