package ro.dobrescuandrei.utils

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import okio.*
import java.io.File

object RequestBodyForContentUri
{
    fun create(contentType : MediaType?, uri : String, contentResolver : ContentResolver) : RequestBody
    {
        return object : RequestBody()
        {
            override fun contentType(): MediaType? = contentType

            override fun contentLength() : Long
            {
                contentResolver.openInputStream(Uri.parse(uri))?.use { inputStream ->
                    return inputStream.available().toLong()
                }

                return 0
            }

            override fun writeTo(output : BufferedSink)
            {
                contentResolver.openInputStream(Uri.parse(uri))?.source()?.buffer()?.use { input ->
                    output.writeAll(input)
                }
            }
        }
    }
}

object RetrofitUtils
{
    fun fileUpload(context : Context, fileKey : String = "image", path : String) : MultipartBody.Part
    {
        val fileName=path.split("/").last().replace("[\\\\/:*?\"<>|]".toRegex(), "")
        val contentType="multipart/form-data".toMediaTypeOrNull()

        val requestFileBody=
            if (path.startsWith("content://"))
                RequestBodyForContentUri.create(contentType, path, context.contentResolver)
            else File(path).asRequestBody(contentType)

        return MultipartBody.Part.createFormData(fileKey, fileName, requestFileBody)
    }

    @Throws(Exception::class)
    fun downloadFile(body : ResponseBody, outputPath : String)
    {
        File(outputPath).sink().buffer().use { output ->
            output.writeAll(body.source())
        }
    }
}
