package com.aistem.facedetection

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import org.opencv.android.BaseLoaderCallback
import org.opencv.android.LoaderCallbackInterface
import org.opencv.android.OpenCVLoader
import java.io.IOException
import android.app.Activity
import com.astem.facedetection.R


class MainActivity : Activity() {

    private val mLoaderCallback = object : BaseLoaderCallback(this) {
        override fun onManagerConnected(status: Int) {
            when (status) {
                LoaderCallbackInterface.SUCCESS -> isOpenCVInit = true
                LoaderCallbackInterface.INCOMPATIBLE_MANAGER_VERSION -> {
                }
                LoaderCallbackInterface.INIT_FAILED -> {
                }
                LoaderCallbackInterface.INSTALL_CANCELED -> {
                }
                LoaderCallbackInterface.MARKET_ERROR -> {
                }
                else -> {
                    super.onManagerConnected(status)
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        if (null == savedInstanceState) {
            fragmentManager
                .beginTransaction()
                .replace(
                    R.id.container,
                    Camera2BasicFragment.newInstance()
                )
                .commit()
        }
    }

    override fun onResume() {
        super.onResume()
        if (!OpenCVLoader.initDebug()) {
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, mLoaderCallback)
        } else {
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS)
        }
    }


//    fun testFacedetect() {
//        val bmp = getImageFromAssets("test.jpg") ?: return
//        var str = "image size = ${bmp.width}x${bmp.height}\n"
//        imageView.setImageBitmap(bmp)
//        val mat = MatOfRect()
//        val bmp2 = bmp.copy(bmp.config, true)
//        Utils.bitmapToMat(bmp, mat)
//        val FACE_RECT_COLOR = Scalar(255.0, 0.0, 0.0)
//        val FACE_RECT_THICKNESS = 3
//        val startTime = System.currentTimeMillis()
//        val facesArray = facedetect(mat.nativeObjAddr)
//        str = str + "face num = ${facesArray.size}\n"
//        for (face in facesArray) {
//            Imgproc.rectangle(mat, face.faceRect, FACE_RECT_COLOR, FACE_RECT_THICKNESS)
//        }
//        str = str + "detectTime = ${System.currentTimeMillis() - startTime}ms\n"
//        Utils.matToBitmap(mat, bmp2)
//        imageView.setImageBitmap(bmp2)
//        textView.text = str
//    }

    /**
     * A native method that is implemented by the 'libfacedetection' native library,
     * which is packaged with this application.
     */
    //external fun facedetect(matAddr: Long): Array<Face>


    companion object {

        // Used to load the 'facedetection' library on application startup.
//        init {
//            System.loadLibrary("facedetection")
//        }
        init {
            //        System.loadLibrary("opencv_java");
            System.loadLibrary("opencv_java4")
        }

        @JvmStatic
        var isOpenCVInit = false
    }


    /**
     * read from Assets
     */
    private fun getImageFromAssets(fileName:String):Bitmap?
    {
        var image:Bitmap? = null
        try {
            val stream = resources.assets.open(fileName)
            image = BitmapFactory.decodeStream(stream)
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }finally {
         return image
        }
    }
}