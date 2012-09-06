package de.lmu.ifi.dbs.elki.data;

/*
 This file is part of ELKI:
 Environment for Developing KDD-Applications Supported by Index-Structures

 Copyright (C) 2012
 Ludwig-Maximilians-Universität München
 Lehr- und Forschungseinheit für Datenbanksysteme
 ELKI Development Team

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU Affero General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Affero General Public License for more details.

 You should have received a copy of the GNU Affero General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import de.lmu.ifi.dbs.elki.math.linearalgebra.Vector;
import de.lmu.ifi.dbs.elki.persistent.ByteBufferSerializer;
import de.lmu.ifi.dbs.elki.utilities.datastructures.arraylike.ArrayAdapter;
import de.lmu.ifi.dbs.elki.utilities.datastructures.arraylike.NumberArrayAdapter;
import de.lmu.ifi.dbs.elki.utilities.optionhandling.AbstractParameterizer;

/**
 * Specialized class implementing a one-dimensional double vector without using
 * an array. Saves a little bit of memory, albeit we cannot avoid boxing as long
 * as we want to implement the interface.
 * 
 * @author Erich Schubert
 */
public class OneDimensionalDoubleVector extends AbstractNumberVector<OneDimensionalDoubleVector, Double> {
  /**
   * Static factory instance
   */
  public static final OneDimensionalDoubleVector STATIC = new OneDimensionalDoubleVector(Double.NaN);

  /**
   * The actual data value
   */
  double val;

  /**
   * Constructor.
   * 
   * @param val Value
   */
  public OneDimensionalDoubleVector(double val) {
    this.val = val;
  }

  @Override
  public int getDimensionality() {
    return 1;
  }

  @Override
  public double doubleValue(int dimension) {
    assert (dimension == 1) : "Non-existant dimension accessed.";
    return val;
  }

  @Override
  public long longValue(int dimension) {
    assert (dimension == 1) : "Non-existant dimension accessed.";
    return (long) val;
  }

  @Override
  public Double getValue(int dimension) {
    assert (dimension == 1) : "Incorrect dimension requested for 1-dimensional vector.";
    return this.val;
  }

  @Override
  public Vector getColumnVector() {
    return new Vector(new double[] { val });
  }

  @Override
  public <A> OneDimensionalDoubleVector newFeatureVector(A array, ArrayAdapter<Double, A> adapter) {
    assert (adapter.size(array) == 1) : "Incorrect dimensionality for 1-dimensional vector.";
    return new OneDimensionalDoubleVector(adapter.get(array, 0));
  }

  @Override
  public <A> OneDimensionalDoubleVector newNumberVector(A array, NumberArrayAdapter<?, A> adapter) {
    assert (adapter.size(array) == 1) : "Incorrect dimensionality for 1-dimensional vector.";
    return new OneDimensionalDoubleVector(adapter.getDouble(array, 0));
  }

  @Override
  public ByteBufferSerializer<OneDimensionalDoubleVector> getDefaultSerializer() {
    // FIXME: add a serializer
    return null;
  }

  /**
   * Parameterization class.
   * 
   * @author Erich Schubert
   * 
   * @apiviz.exclude
   */
  public static class Parameterizer extends AbstractParameterizer {
    @Override
    protected OneDimensionalDoubleVector makeInstance() {
      return STATIC;
    }
  }
}