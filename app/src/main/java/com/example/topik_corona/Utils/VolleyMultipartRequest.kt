package com.example.topik_corona.Utils

import com.android.volley.*
import com.android.volley.toolbox.HttpHeaderParser
import java.io.*
import java.lang.RuntimeException
import kotlin.math.min

class VolleyMultipartRequest(method: Int, endpoint: String, requestParams: Map<String, String>,
                             fileParams: Map<String, DataPart>,
                             listener: Response.Listener<NetworkResponse>,
                             errorListener: Response.ErrorListener):
        Request<NetworkResponse>(method, "${Constants.BASE_URL}$endpoint", errorListener) {

    private val boundary: String = "apiclient-${System.currentTimeMillis()}"
    private val multipartRequestParams: Map<String, String> = requestParams
    private val multipartRequestFileParams: Map<String, DataPart> = fileParams
    private val responseListener: Response.Listener<NetworkResponse> = listener
    private val responseErrorListener: Response.ErrorListener = errorListener

    private fun buildDataPart(dataOutputStream: DataOutputStream, parameterName: String,
                              parameterValue: DataPart
    ) {
        dataOutputStream.writeBytes("--$boundary\r\n")
        dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"$parameterName\"; " +
                "filename=\"${parameterValue.getName()}\r\n")
        dataOutputStream.writeBytes("\r\n")

        val inputStream = ByteArrayInputStream(parameterValue.getData())
        var availableByte: Int = inputStream.available()
        val maxBuffer: Int = 1024 * 1024
        var bufferSize: Int = min(availableByte, maxBuffer)
        val buffer = ByteArray(bufferSize)
        var byteRead: Int = inputStream.read(buffer, 0, bufferSize)

        while(byteRead > 0) {
            dataOutputStream.write(buffer, 0, bufferSize)
            availableByte = inputStream.available()
            bufferSize = min(availableByte, maxBuffer)
            byteRead = inputStream.read(buffer, 0, bufferSize)
        }

        dataOutputStream.writeBytes("\r\n")
    }

    private fun dataParse(dataOutputStream: DataOutputStream, data: Map<String, DataPart>) {
        for(entry: Map.Entry<String, DataPart> in data.entries) {
            buildDataPart(dataOutputStream, entry.key, entry.value)
        }
    }

    private fun buildTextPart(dataOutputStream: DataOutputStream, parameterName: String,
                              parameterValue: String) {
        dataOutputStream.writeBytes("--$boundary\r\n")
        dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"$parameterName\"\r\n")
        dataOutputStream.writeBytes("\r\n")
        dataOutputStream.writeBytes("$parameterValue\r\n")
    }

    private fun textParse(dataOutputStream: DataOutputStream, parameter: Map<String, String>,
                          encoding: String) {
        try {
            for(entry: Map.Entry<String, String> in parameter.entries) {
                buildTextPart(dataOutputStream, entry.key, entry.value)
            }
        } catch (e: UnsupportedEncodingException) {
            throw RuntimeException("Encoding not supported: $encoding", e)
        }
    }

    override fun getBodyContentType(): String {
        return "multipart/form-data;boundary=${boundary}"
    }

    override fun getBody(): ByteArray? {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val dataOutputStream = DataOutputStream(byteArrayOutputStream)

        try {
            val parameters: Map<String, String> = multipartRequestParams
            val data: Map<String, DataPart> = multipartRequestFileParams

            if(parameters.isNotEmpty()) {
                textParse(dataOutputStream, parameters, paramsEncoding)
            }

            if(data.isNotEmpty()) {
                dataParse(dataOutputStream, data)
            }

            dataOutputStream.writeBytes("--$boundary--\r\n")
            return byteArrayOutputStream.toByteArray()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    override fun parseNetworkResponse(response: NetworkResponse?): Response<NetworkResponse> {
        return try {
            Response.success(response, HttpHeaderParser.parseCacheHeaders(response))
        } catch (e: Exception) {
            Response.error(ParseError(e))
        }
    }

    override fun deliverResponse(response: NetworkResponse?) {
        return responseListener.onResponse(response)
    }

    override fun deliverError(error: VolleyError?) {
        return responseErrorListener.onErrorResponse(error)
    }
}

class DataPart(name: String, content: ByteArray) {
    private val filename: String = name
    private val data: ByteArray = content

    fun getName(): String {
        return filename
    }

    fun getData(): ByteArray {
        return data
    }
}