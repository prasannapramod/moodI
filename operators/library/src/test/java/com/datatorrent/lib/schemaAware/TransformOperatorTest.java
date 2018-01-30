/*
 * Copyright (c) 2012-2017 DataTorrent, Inc.
 * All Rights Reserved.
 * The use of this source code is governed by the Limited License located at
 * https://www.datatorrent.com/datatorrent-openview-software-license/
 */

package com.datatorrent.lib.schemaAware;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.datatorrent.api.Operator.InputPort;
import com.datatorrent.api.Operator.OutputPort;
import com.datatorrent.schema.api.Schema;

@org.apache.hadoop.classification.InterfaceStability.Evolving
public class TransformOperatorTest
{
  static TransformOperator transform = new TransformOperator();
  static Map<InputPort, Schema> inSchema = new HashMap<InputPort, Schema>();
  static Map<OutputPort, Schema> outSchema = new HashMap<OutputPort, Schema>();

  @BeforeClass
  public static void setup() throws IOException
  {
    inSchema.put(transform.input, new Schema());
    outSchema.put(transform.output, new Schema());
    Map<String, String> outputFieldMap = new HashMap<String, String>();
    outputFieldMap.put("uniqueId", "STRING");
    transform.setOutputFieldInfo("{\"uniqueId\":\"STRING\"}");
    transform.setExpressionInfo("{\"uniqueId\":\"2 * id\"}");
    transform.registerSchema(inSchema, outSchema);
    transform.setup(null);
  }

  @Test
  public void TestSchemaRegistration()
  {
    Assert.assertEquals(1, outSchema.get(transform.output).getFieldList().size());
    Assert.assertEquals(String.class, outSchema.get(transform.output).getField("uniqueId"));
  }

}
