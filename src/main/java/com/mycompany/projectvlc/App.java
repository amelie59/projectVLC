package com.mycompany.projectvlc;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        NativeLibrary.addSearchPath(
                RuntimeUtil.getLibVlcLibraryName(), "D:\\Program Files\\VLC\\sdk\\lib"
            );
            Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
    }
}
