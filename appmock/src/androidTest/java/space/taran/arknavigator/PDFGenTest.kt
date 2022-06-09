package space.taran.arknavigator

import android.content.res.AssetManager
import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import space.taran.arklib.pdfThumbnailGenerate;
import java.io.File
import kotlin.io.path.*

@RunWith(AndroidJUnit4::class)
class PDFGenTest {

    @get:Rule
    val mainActivityRule = ActivityTestRule(MainActivity::class.java)
//    @Test
//    fun multi_pdfgen() {
//        for (i in 0..2) {
//            val appContext = InstrumentationRegistry
//                .getInstrumentation()
//                .targetContext
//            val path = Path("${appContext.cacheDir}/test.pdf")
//            appContext.resources.openRawResource(R.raw.pdfsample).copyTo(
//                path.outputStream()
//            )
//            Log.i("PDFGen","Calling PDF Native Renderer")
//            val data = path.inputStream().readBytes()
//            val pdfData = pdfThumbnailGenerate(data,"")
//            path.inputStream().close()
//        }
//    }
    @Test
    fun is_pdfgen() {
        val appContext = InstrumentationRegistry
            .getInstrumentation()
            .targetContext

        val path = Path("${appContext.cacheDir}/test.pdf")
        val font_cache = Path("${appContext.cacheDir}/fonts")
        font_cache.createDirectories()
        appContext.resources.openRawResource(R.raw.test).copyTo(
            path.outputStream()
        )
        appContext.resources.assets.list("fonts")?.map {

            appContext.resources.assets.open("fonts/${it}").copyTo(
                Path("${appContext.cacheDir}/fonts/${it}").outputStream()
            )
        }
        Log.i("PDFGen","Calling PDF Native Renderer")
        Log.i("Path", font_cache.pathString)
        File(font_cache.pathString).walk().forEach {
            Log.i(it.name,it.path)
        }
        val data = path.inputStream().readBytes()
        val pdfData = pdfThumbnailGenerate(data,font_cache.pathString)
        path.inputStream().close()
    }
}