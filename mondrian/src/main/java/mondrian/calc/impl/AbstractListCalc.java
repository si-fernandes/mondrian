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

package mondrian.calc.impl;

import mondrian.calc.Calc;
import mondrian.calc.ListCalc;
import mondrian.calc.ResultStyle;
import mondrian.calc.TupleIterable;
import mondrian.calc.TupleList;
import mondrian.olap.Evaluator;
import mondrian.olap.Exp;
import mondrian.olap.type.SetType;

/**
 * Abstract implementation of the {@link mondrian.calc.ListCalc} interface.
 *
 * <p>The derived class must
 * implement the {@link #evaluateList(mondrian.olap.Evaluator)} method, and the {@link
 * #evaluate(mondrian.olap.Evaluator)} method will call it.
 *
 * @author jhyde
 * @since Sep 27, 2005
 */
public abstract class AbstractListCalc
  extends AbstractCalc
  implements ListCalc {
  private final boolean mutable;

  /**
   * Creates an abstract implementation of a compiled expression which returns a mutable list of tuples.
   *
   * @param exp   Expression which was compiled
   * @param calcs List of child compiled expressions (for dependency analysis)
   */
  protected AbstractListCalc( Exp exp, Calc[] calcs ) {
    this( exp, calcs, true );
  }

  /**
   * Creates an abstract implementation of a compiled expression which returns a list.
   *
   * @param exp     Expression which was compiled
   * @param calcs   List of child compiled expressions (for dependency analysis)
   * @param mutable Whether the list is mutable
   */
  protected AbstractListCalc( Exp exp, Calc[] calcs, boolean mutable ) {
    super( exp, calcs );
    this.mutable = mutable;
    assert type instanceof SetType : "expecting a set: " + getType();
  }

  public SetType getType() {
    return (SetType) super.getType();
  }

  public final Object evaluate( Evaluator evaluator ) {
    final TupleList tupleList = evaluateList( evaluator );
    assert tupleList != null : "null as empty tuple list is deprecated";
    return tupleList;
  }

  public TupleIterable evaluateIterable( Evaluator evaluator ) {
    return evaluateList( evaluator );
  }

  public ResultStyle getResultStyle() {
    return mutable
      ? ResultStyle.MUTABLE_LIST
      : ResultStyle.LIST;
  }

  public String toString() {
    return "AbstractListCalc object";
  }
}

// End AbstractListCalc.java
