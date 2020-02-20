package com.jiedian.apitest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

final class FastJsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
  static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
  SerializeConfig serializeConfig;
  SerializerFeature[] serializerFeatures;

  FastJsonRequestBodyConverter(SerializeConfig config, SerializerFeature... features) {
    serializeConfig = config;
    serializerFeatures = features;
  }

  @Override
  public RequestBody convert(T value) throws IOException {
    String content;
    if (value instanceof String) {
      content = (String) value;
    } else if (serializeConfig != null) {
      if (serializerFeatures != null) {
        content = JSON.toJSONString(value, serializeConfig, serializerFeatures);
      } else {
        content = JSON.toJSONString(value, serializeConfig);
      }
    } else {
      if (serializerFeatures != null) {
        content = JSON.toJSONString(value, serializerFeatures);
      } else {
        content = JSON.toJSONString(value);
      }
    }
    return RequestBody.create(MEDIA_TYPE, content.getBytes());
  }
}
