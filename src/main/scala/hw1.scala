import org.apache.spark.sql.SparkSession

object peopleYouMightKnow extends App {

  val spark = SparkSession.builder
    .master("local[*]")
    .appName("Spark Word Count")
    .getOrCreate()
  spark.sparkContext.setLogLevel("ERROR")

  val rddFromFile = spark.sparkContext.textFile("soc-LiveJournal1Adj.txt")
  val lines = rddFromFile.map(line => line.split("\t"))
  val friends = lines.filter( _.length == 2 ).map(line => (line(0),line(1).split(",")) )


  val directFriends= friends.flatMap(data => { data._2.map(friend => ((data._1,friend),0)) }  )
  val mutualFriends= friends.flatMap(data => { data._2.combinations(2).map(pair => ((pair(0),pair(1)),1)) }  )

  val fullList= directFriends.union(mutualFriends).reduceByKey(_+_)

  val groupedList = fullList.filter(_._2 > 0).map( tuple =>  (tuple._1._1, (tuple._2, tuple._1._2))).groupByKey().map(x => (x._1,x._2.toList))
  val suggestions = groupedList.map( data => (data._1, data._2.sortBy(_._1).reverse)).map(x => (x._1,x._2.take(10)))

  val output = suggestions.map( data => (data._1,{data._2.map(s => s._2)})).map(x => (x._1,x._2.mkString(" ")))

  output.saveAsTextFile("result")
  spark.stop()

}