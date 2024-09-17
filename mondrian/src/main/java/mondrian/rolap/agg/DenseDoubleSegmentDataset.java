/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2028-08-13
 ******************************************************************************/

package mondrian.rolap.agg;


import mondrian.olap.Util;
import mondrian.rolap.CellKey;
import mondrian.rolap.SqlStatement;
import mondrian.spi.SegmentBody;
import mondrian.util.Pair;

import java.util.*;

/**
 * Implementation of {@link mondrian.rolap.agg.DenseSegmentDataset} that stores
 * values of type {@code double}.
 *
 * @author jhyde
 */
class DenseDoubleSegmentDataset extends DenseNativeSegmentDataset {
    final double[] values; // length == m[0] * ... * m[axes.length-1]

    /**
     * Creates a DenseDoubleSegmentDataset.
     *
     * @param axes Segment axes, containing actual column values
     * @param size Number of coordinates
     */
    DenseDoubleSegmentDataset(SegmentAxis[] axes, int size) {
        this(axes, new double[size], Util.bitSetBetween(0, size));
    }

    /**
     * Creates a populated DenseDoubleSegmentDataset.
     *
     * @param axes Segment axes, containing actual column values
     * @param values Cell values; not copied
     * @param nullIndicators Null indicators
     */
    DenseDoubleSegmentDataset(
        SegmentAxis[] axes, double[] values, BitSet nullIndicators)
    {
        super(axes, nullIndicators);
        this.values = values;
    }

    public double getDouble(CellKey key) {
        int offset = key.getOffset(axisMultipliers);
        return values[offset];
    }

    public Object getObject(CellKey pos) {
        if (values.length == 0) {
            // No values means they are all null.
            // We can't call isNull because we risk going into a SOE. Besides,
            // this is a tight loop and we can skip over one VFC.
            return null;
        }
        int offset = pos.getOffset(axisMultipliers);
        return getObject(offset);
    }

    public Double getObject(int offset) {
        final double value = values[offset];
        if (value == 0 && isNull(offset)) {
            return null;
        }
        return value;
    }

    public boolean exists(CellKey pos) {
        return true;
    }

    public void populateFrom(int[] pos, SegmentDataset data, CellKey key) {
        final int offset = getOffset(pos);
        final double value = values[offset] = data.getDouble(key);
        if (value != 0d || !data.isNull(key)) {
            nullValues.clear(offset);
        }
    }

    public void populateFrom(
        int[] pos, SegmentLoader.RowList rowList, int column)
    {
        final int offset = getOffset(pos);
        final double value = values[offset] = rowList.getDouble(column);
        if (value != 0d || !rowList.isNull(column)) {
            nullValues.clear(offset);
        }
    }

    public SqlStatement.Type getType() {
        return SqlStatement.Type.DOUBLE;
    }

    void set(int k, double d) {
        values[k] = d;
    }

    protected int getSize() {
        return values.length;
    }

    public SegmentBody createSegmentBody(
        List<Pair<SortedSet<Comparable>, Boolean>> axes)
    {
        return new DenseDoubleSegmentBody(
            nullValues,
            values,
            axes);
    }
}

// End DenseDoubleSegmentDataset.java
