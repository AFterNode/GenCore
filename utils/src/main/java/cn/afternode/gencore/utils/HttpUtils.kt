package cn.afternode.gencore.utils

import org.apache.hc.client5.http.fluent.Request
import java.io.IOException
import java.io.OutputStream
import kotlin.jvm.Throws

object HttpUtils {
    @JvmStatic @Throws(IOException::class) fun get(url: String): ByteArray {
        val content = Request.get(url).execute().returnContent()
        return content.asBytes()
    }

    @JvmStatic @Throws(IOException::class) fun getString(url: String): String {
        return Request.get(url).execute().returnContent().asString(Charsets.UTF_8)
    }

    @JvmStatic @Throws(IOException::class) fun download(url: String, output: OutputStream) {
        output.write(get(url))
    }
}