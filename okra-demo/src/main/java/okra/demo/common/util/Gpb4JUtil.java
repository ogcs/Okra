/*
 *     Copyright 2016-2026 TinyZ
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package okra.demo.common.util;

import com.google.protobuf.Descriptors;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import org.ogcs.okra.example.game.generated.Gpb;

import java.util.Map;

/**
 * Google Protocol Buffers反射为Java数组
 * @author TinyZ
 * @date 2017-01-15.
 */
public class Gpb4JUtil {


    public Object[] covertGpb2ObjArray(Message message) {
        Map<FieldDescriptor, Object> fields = message.getAllFields();
        Object[] objects = new Object[fields.size()];
        for (Map.Entry<FieldDescriptor, Object> entry : fields.entrySet()) {
            FieldDescriptor fieldDescriptor = entry.getKey();
            String name = fieldDescriptor.getName();



        }
        return objects;
    }

    public static void main(String[] args) throws InvalidProtocolBufferException {
        Descriptors.FileDescriptor descriptor = Gpb.getDescriptor();

        Gpb.Request build = Gpb.Request.newBuilder()
                .setId(1)
                .setApi(1001)
                .build();
        Gpb.Response response = Gpb.Response.newBuilder()
                .setId(1)
                .setData(build.toByteString())
                .setError(
                        Gpb.Error.newBuilder()
                                .setMsg("SomeThing")
                                .setRet(1)
                                .build()
                )
                .build();

        //  收到的数据
//        Message parseFrom = Gpb.Request.parseFrom(build.toByteArray());
        Message parseFrom = Gpb.Response.parseFrom(response.toByteArray());

        Map<FieldDescriptor, Object> fields = parseFrom.getAllFields();
        Object[] objects = parseFrom.getAllFields().values().toArray();

        Object[] params = new Object[parseFrom.getAllFields().size()];


        for (Map.Entry<FieldDescriptor, Object> entry : fields.entrySet()) {
            Object covert = covert(entry.getKey(), entry.getValue());
            params[entry.getKey().getIndex()] = entry.getValue();
            System.out.println();
        }


        System.out.println();
    }

    private static Object covert(FieldDescriptor fieldDescriptor, Object obj) {
        FieldDescriptor.JavaType javaType = fieldDescriptor.getJavaType();
        switch (javaType) {
            case INT:
                break;
            case LONG:
                break;
            case FLOAT:
                break;
            case DOUBLE:
                break;
            case BOOLEAN:
                break;
            case STRING:
                break;
            case BYTE_STRING:
                break;
            case ENUM:
                break;
            case MESSAGE:
                break;
        }
        return null;
    }













}
