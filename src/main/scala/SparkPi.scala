/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// scalastyle:off println
package org.apache.spark.examples

import com.typesafe.config.Config
import spark.jobserver.{SparkJobValidation, SparkJob}

import scala.math.random

import org.apache.spark._

object SampleJob extends SparkJob with Logging {
  override def runJob(sc:SparkContext, jobConfig: Config): Any = {
    logInfo("Job starting")
    val slices = 10
    val n = math.min(100000L * slices, Int.MaxValue).toInt // avoid overflow
    val count = sc.parallelize(1 until n, slices).map { i =>
        val x = random * 2 - 1
        val y = random * 2 - 1
        if (x*x + y*y < 1) 1 else 0
      }.reduce(_ + _)
//    println("Pi is roughly " + 4.0 * count / n)
      logInfo("Pi is roughly " + 4.0 * count / n)
      "Pi is roughly " + 4.0 * count / n
        // This can be shown in job results fetched with eg. curl 'master:8090/jobs/3f1bd6c1-8e9a-4fa7-aa1a-65d046f9393e'
  }

  override def validate(sc:SparkContext, config: Config): SparkJobValidation = spark.jobserver.SparkJobValid
}
