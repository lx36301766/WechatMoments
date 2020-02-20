package com.jiedian.apitest

import okhttp3.RequestBody
import rxhttp.wrapper.annotation.Param
import rxhttp.wrapper.param.AbstractParam
import rxhttp.wrapper.param.Method


@Param(methodName = "postObjectJson")
class CustomParam(url: String) : AbstractParam<CustomParam>(url, Method.POST) {

    var params: Any? = null

    override fun getRequestBody(): RequestBody {
        return convert(params)
    }

    fun addObject(value: Any?): CustomParam {
        params = value
        return this
    }

    override fun add(key: String?, value: Any?): CustomParam {
        return this
    }

}
