package de.lmu.ifi.dbs.elki.math.statistics.distribution;

/*
 This file is part of ELKI:
 Environment for Developing KDD-Applications Supported by Index-Structures

 Copyright (C) 2013
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

import java.util.Random;

import de.lmu.ifi.dbs.elki.utilities.Alias;
import de.lmu.ifi.dbs.elki.utilities.exceptions.ExceptionMessages;
import de.lmu.ifi.dbs.elki.utilities.exceptions.NotImplementedException;

/**
 * Exponentially modified Gaussian (EMG) distribution (ExGaussian distribution)
 * is a combination of a normal distribution and an exponential distribution.
 * 
 * @author Erich Schubert
 */
@Alias({ "exgaussian" })
public class ExponentiallyModifiedGaussianDistribution implements Distribution {
  /**
   * Mean value for the generator
   */
  private double mean;

  /**
   * Standard deviation
   */
  private double stddev;

  /**
   * Exponential rate.
   */
  private double lambda;

  /**
   * Random generator.
   */
  private Random rnd;

  /**
   * Constructor for ExGaussian distribution
   * 
   * @param mean Mean
   * @param stddev Standard Deviation
   * @param lambda Rate
   * @param rnd Random
   */
  public ExponentiallyModifiedGaussianDistribution(double mean, double stddev, double lambda, Random rnd) {
    super();
    this.mean = mean;
    this.stddev = stddev;
    this.lambda = lambda;
    this.rnd = rnd;
  }

  /**
   * Constructor for ExGaussian distribution
   * 
   * @param mean Mean
   * @param stddev Standard Deviation
   * @param lambda Rate
   */
  public ExponentiallyModifiedGaussianDistribution(double mean, double stddev, double lambda) {
    this(mean, stddev, lambda, null);
  }

  @Override
  public double pdf(double val) {
    return pdf(val, mean, stddev, lambda);
  }

  @Override
  public double cdf(double val) {
    return cdf(val, mean, stddev, lambda);
  }

  /**
   * @deprecated Not yet implemented!
   */
  @Override
  @Deprecated
  public double quantile(double q) {
    return quantile(q, mean, stddev, lambda);
  }

  @Override
  public double nextRandom() {
    double no = mean + rnd.nextGaussian() * stddev;
    double ex = -Math.log(rnd.nextDouble()) / lambda;
    return no + ex;
  }

  @Override
  public String toString() {
    return "ExGaussianDistribution(mean=" + mean + ", stddev=" + stddev + ", lambda=" + lambda + ")";
  }

  /**
   * @return the mean
   */
  public double getMean() {
    return mean;
  }

  /**
   * @return the standard deviation
   */
  public double getStddev() {
    return stddev;
  }

  /**
   * @return the lambda value.
   */
  public double getLambda() {
    return lambda;
  }

  /**
   * Probability density function of the ExGaussian distribution.
   * 
   * @param x The value.
   * @param mu The mean.
   * @param sigma The standard deviation.
   * @param lambda Rate parameter.
   * @return PDF of the given exgauss distribution at x.
   */
  public static double pdf(double x, double mu, double sigma, double lambda) {
    final double dx = x - mu;
    final double erfc = NormalDistribution.erfc(lambda * sigma * sigma - dx);
    return .5 * lambda * Math.exp(lambda * (lambda * sigma * sigma * .5 - dx)) * erfc;
  }

  /**
   * Cumulative probability density function (CDF) of an exgauss distribution.
   * 
   * @param x value to evaluate CDF at.
   * @param mu Mean value.
   * @param sigma Standard deviation.
   * @param lambda Rate parameter.
   * @return The CDF of the given exgauss distribution at x.
   */
  public static double cdf(double x, double mu, double sigma, double lambda) {
    final double u = lambda * (x - mu);
    final double v = lambda * sigma;
    final double v2 = v * v;
    final double logphi = Math.log(NormalDistribution.cdf(u, v2, v));
    return NormalDistribution.cdf(u, 0., v) - Math.exp(-u + v2 * .5 + logphi);
  }

  /**
   * Inverse cumulative probability density function (probit) of an exgauss
   * distribution.
   * 
   * @param x value to evaluate probit function at.
   * @param mu Mean value.
   * @param sigma Standard deviation.
   * @param lambda Rate parameter.
   * @return The probit of the given exgauss distribution at x.
   * 
   * @deprecated Not yet implemented!
   */
  @Deprecated
  public static double quantile(double x, double mu, double sigma, double lambda) {
    // FIXME: implement!
    throw new NotImplementedException(ExceptionMessages.UNSUPPORTED_NOT_YET);
  }
}
