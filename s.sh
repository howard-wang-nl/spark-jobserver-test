curl --data-binary @target/scala-2.10/jobserver-test-fat.jar master:8090/jars/test3
curl -XPOST 'master:8090/jobs?appName=test3&classPath=org.apache.spark.examples.SampleJob'
