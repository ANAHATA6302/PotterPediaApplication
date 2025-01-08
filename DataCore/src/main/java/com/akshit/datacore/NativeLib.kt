package com.akshit.datacore

class NativeLib {

    /**
     * A native method that is implemented by the 'datacore' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {
        // Used to load the 'datacore' library on application startup.
        init {
            System.loadLibrary("datacore")
        }
    }
}