/*
 * This file is part of ELKI:
 * Environment for Developing KDD-Applications Supported by Index-Structures
 *
 * Copyright (C) 2019
 * ELKI Development Team
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package elki.distance.similarityfunction.cluster;

import elki.data.Clustering;
import elki.data.type.SimpleTypeInformation;
import elki.database.query.DistanceSimilarityQuery;
import elki.database.query.distance.PrimitiveDistanceSimilarityQuery;
import elki.database.relation.Relation;
import elki.distance.similarityfunction.NormalizedSimilarityFunction;
import elki.evaluation.clustering.ClusterContingencyTable;
import elki.utilities.documentation.Reference;
import elki.utilities.optionhandling.AbstractParameterizer;

/**
 * Measure the similarity of clusters via the Fowlkes-Mallows Index.
 * <p>
 * Reference:
 * <p>
 * E. B. Fowlkes, C. L. Mallows<br>
 * A method for comparing two hierarchical clusterings<br>
 * In: Journal of the American Statistical Association, Vol. 78 Issue 383
 *
 * @author Erich Schubert
 * @since 0.7.0
 */
@Reference(authors = "E. B. Fowlkes, C. L. Mallows", //
    title = "A method for comparing two hierarchical clusterings", //
    booktitle = "Journal of the American Statistical Association, Vol. 78 Issue 383", //
    url = "https://doi.org/10.2307/2288117", //
    bibkey = "doi:10.2307/2288117")
public class ClusteringFowlkesMallowsSimilarityFunction implements ClusteringDistanceSimilarityFunction, NormalizedSimilarityFunction<Clustering<?>> {
  /**
   * Static instance.
   */
  public static final ClusteringFowlkesMallowsSimilarityFunction STATIC = new ClusteringFowlkesMallowsSimilarityFunction();

  /**
   * Constructor - use the static instance {@link #STATIC}!
   */
  public ClusteringFowlkesMallowsSimilarityFunction() {
    super();
  }

  @Override
  public double similarity(Clustering<?> o1, Clustering<?> o2) {
    ClusterContingencyTable ct = new ClusterContingencyTable(false, true);
    ct.process(o1, o2);
    return ct.getPaircount().fowlkesMallows();
  }

  @Override
  public double distance(Clustering<?> o1, Clustering<?> o2) {
    ClusterContingencyTable ct = new ClusterContingencyTable(false, true);
    ct.process(o1, o2);
    return 1. - ct.getPaircount().fowlkesMallows();
  }

  @Override
  public boolean isMetric() {
    return false;
  }

  @Override
  public <T extends Clustering<?>> DistanceSimilarityQuery<T> instantiate(Relation<T> relation) {
    return new PrimitiveDistanceSimilarityQuery<>(relation, this, this);
  }

  @Override
  public SimpleTypeInformation<? super Clustering<?>> getInputTypeRestriction() {
    return new SimpleTypeInformation<>(Clustering.class);
  }

  /**
   * Parameterization class.
   *
   * @author Erich Schubert
   */
  public static class Parameterizer extends AbstractParameterizer {
    @Override
    protected ClusteringFowlkesMallowsSimilarityFunction makeInstance() {
      return STATIC;
    }
  }
}