/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.pinot.core.query.aggregation.function;

import com.clearspring.analytics.stream.cardinality.HyperLogLog;
import java.util.Map;
import org.apache.pinot.common.function.AggregationFunctionType;
import org.apache.pinot.core.common.BlockValSet;
import org.apache.pinot.core.query.aggregation.AggregationResultHolder;
import org.apache.pinot.core.query.aggregation.groupby.GroupByResultHolder;
import org.apache.pinot.spi.data.FieldSpec.DataType;


public class DistinctCountHLLMVAggregationFunction extends DistinctCountHLLAggregationFunction {

  /**
   * Constructor for the class.
   * @param column Column name to aggregate on.
   */
  public DistinctCountHLLMVAggregationFunction(String column) {
    super(column);
  }

  @Override
  public AggregationFunctionType getType() {
    return AggregationFunctionType.DISTINCTCOUNTHLLMV;
  }

  @Override
  public void accept(AggregationFunctionVisitorBase visitor) {
    visitor.visit(this);
  }

  @Override
  public void aggregate(int length, AggregationResultHolder aggregationResultHolder, Map<String, BlockValSet> blockValSetMap) {
    HyperLogLog hyperLogLog = getDefaultHyperLogLog(aggregationResultHolder);

    BlockValSet blockValSet = blockValSetMap.get(_column);
    DataType valueType = blockValSet.getValueType();
    switch (valueType) {
      case INT:
        int[][] intValuesArray = blockValSet.getIntValuesMV();
        for (int i = 0; i < length; i++) {
          for (int value : intValuesArray[i]) {
            hyperLogLog.offer(value);
          }
        }
        break;
      case LONG:
        long[][] longValuesArray = blockValSet.getLongValuesMV();
        for (int i = 0; i < length; i++) {
          for (long value : longValuesArray[i]) {
            hyperLogLog.offer(value);
          }
        }
        break;
      case FLOAT:
        float[][] floatValuesArray = blockValSet.getFloatValuesMV();
        for (int i = 0; i < length; i++) {
          for (float value : floatValuesArray[i]) {
            hyperLogLog.offer(value);
          }
        }
        break;
      case DOUBLE:
        double[][] doubleValuesArray = blockValSet.getDoubleValuesMV();
        for (int i = 0; i < length; i++) {
          for (double value : doubleValuesArray[i]) {
            hyperLogLog.offer(value);
          }
        }
        break;
      case STRING:
        String[][] stringValuesArray = blockValSet.getStringValuesMV();
        for (int i = 0; i < length; i++) {
          for (String value : stringValuesArray[i]) {
            hyperLogLog.offer(value);
          }
        }
        break;
      default:
        throw new IllegalStateException(
            "Illegal data type for DISTINCT_COUNT_HLL_MV aggregation function: " + valueType);
    }
  }

  @Override
  public void aggregateGroupBySV(int length, int[] groupKeyArray, GroupByResultHolder groupByResultHolder,
      Map<String, BlockValSet> blockValSetMap) {
    BlockValSet blockValSet = blockValSetMap.get(_column);
    DataType valueType = blockValSet.getValueType();

    switch (valueType) {
      case INT:
        int[][] intValuesArray = blockValSet.getIntValuesMV();
        for (int i = 0; i < length; i++) {
          HyperLogLog hyperLogLog = getDefaultHyperLogLog(groupByResultHolder, groupKeyArray[i]);
          for (int value : intValuesArray[i]) {
            hyperLogLog.offer(value);
          }
        }
        break;
      case LONG:
        long[][] longValuesArray = blockValSet.getLongValuesMV();
        for (int i = 0; i < length; i++) {
          HyperLogLog hyperLogLog = getDefaultHyperLogLog(groupByResultHolder, groupKeyArray[i]);
          for (long value : longValuesArray[i]) {
            hyperLogLog.offer(value);
          }
        }
        break;
      case FLOAT:
        float[][] floatValuesArray = blockValSet.getFloatValuesMV();
        for (int i = 0; i < length; i++) {
          HyperLogLog hyperLogLog = getDefaultHyperLogLog(groupByResultHolder, groupKeyArray[i]);
          for (float value : floatValuesArray[i]) {
            hyperLogLog.offer(value);
          }
        }
        break;
      case DOUBLE:
        double[][] doubleValuesArray = blockValSet.getDoubleValuesMV();
        for (int i = 0; i < length; i++) {
          HyperLogLog hyperLogLog = getDefaultHyperLogLog(groupByResultHolder, groupKeyArray[i]);
          for (double value : doubleValuesArray[i]) {
            hyperLogLog.offer(value);
          }
        }
        break;
      case STRING:
        String[][] stringValuesArray = blockValSet.getStringValuesMV();
        for (int i = 0; i < length; i++) {
          HyperLogLog hyperLogLog = getDefaultHyperLogLog(groupByResultHolder, groupKeyArray[i]);
          for (String value : stringValuesArray[i]) {
            hyperLogLog.offer(value);
          }
        }
        break;
      default:
        throw new IllegalStateException(
            "Illegal data type for DISTINCT_COUNT_HLL_MV aggregation function: " + valueType);
    }
  }

  @Override
  public void aggregateGroupByMV(int length, int[][] groupKeysArray, GroupByResultHolder groupByResultHolder,
      Map<String, BlockValSet> blockValSetMap) {
    BlockValSet blockValSet = blockValSetMap.get(_column);
    DataType valueType = blockValSet.getValueType();

    switch (valueType) {
      case INT:
        int[][] intValuesArray = blockValSet.getIntValuesMV();
        for (int i = 0; i < length; i++) {
          int[] intValues = intValuesArray[i];
          for (int groupKey : groupKeysArray[i]) {
            HyperLogLog hyperLogLog = getDefaultHyperLogLog(groupByResultHolder, groupKey);
            for (int value : intValues) {
              hyperLogLog.offer(value);
            }
          }
        }
        break;
      case LONG:
        long[][] longValuesArray = blockValSet.getLongValuesMV();
        for (int i = 0; i < length; i++) {
          long[] longValues = longValuesArray[i];
          for (int groupKey : groupKeysArray[i]) {
            HyperLogLog hyperLogLog = getDefaultHyperLogLog(groupByResultHolder, groupKey);
            for (long value : longValues) {
              hyperLogLog.offer(value);
            }
          }
        }
        break;
      case FLOAT:
        float[][] floatValuesArray = blockValSet.getFloatValuesMV();
        for (int i = 0; i < length; i++) {
          float[] floatValues = floatValuesArray[i];
          for (int groupKey : groupKeysArray[i]) {
            HyperLogLog hyperLogLog = getDefaultHyperLogLog(groupByResultHolder, groupKey);
            for (float value : floatValues) {
              hyperLogLog.offer(value);
            }
          }
        }
        break;
      case DOUBLE:
        double[][] doubleValuesArray = blockValSet.getDoubleValuesMV();
        for (int i = 0; i < length; i++) {
          double[] doubleValues = doubleValuesArray[i];
          for (int groupKey : groupKeysArray[i]) {
            HyperLogLog hyperLogLog = getDefaultHyperLogLog(groupByResultHolder, groupKey);
            for (double value : doubleValues) {
              hyperLogLog.offer(value);
            }
          }
        }
        break;
      case STRING:
        String[][] stringValuesArray = blockValSet.getStringValuesMV();
        for (int i = 0; i < length; i++) {
          String[] stringValues = stringValuesArray[i];
          for (int groupKey : groupKeysArray[i]) {
            HyperLogLog hyperLogLog = getDefaultHyperLogLog(groupByResultHolder, groupKey);
            for (String value : stringValues) {
              hyperLogLog.offer(value);
            }
          }
        }
        break;
      default:
        throw new IllegalStateException(
            "Illegal data type for DISTINCT_COUNT_HLL_MV aggregation function: " + valueType);
    }
  }
}
