package com.erui.boss.web.util;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.CustomSerializerFactory;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by GS on 2017/12/28 0028.
 * @description 解决Date类型返回json格式为自定义格式
 * @author aokunsang
 *
 */
@Component("DateConverter")
public class DateConverter extends ObjectMapper {
    /*@Override
    public void serialize(Date date, JsonGenerator jg, SerializerProvider sp)
            throws IOException, JsonProcessingException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        jg.writeString(sdf.format(date));
    }*/
    public DateConverter(){
        CustomSerializerFactory factory = new CustomSerializerFactory();
        factory.addGenericMapping(Date.class, new JsonSerializer<Date>(){
            @Override
            public void serialize(Date value,
                                  JsonGenerator jsonGenerator,
                                  SerializerProvider provider)
                    throws IOException, JsonProcessingException {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                jsonGenerator.writeString(sdf.format(value));
            }
        });
       // this.setSerializerFactory(factory);
    }
  /*  private void setSerializerFactory(CustomSerializerFactory factory) {
    }*/

}

