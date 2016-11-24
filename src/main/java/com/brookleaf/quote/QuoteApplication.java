package com.brookleaf.quote;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.List;

import static java.util.Arrays.*;

public class QuoteApplication {
    public static void main( String[] args ) {
        List<String> colors = asList("red", "green", "yellow", "brown", "black", "purple", "orange", "pink");
    
        SparkConf sparkConf = new SparkConf().setAppName("SparkLab").setMaster("local");
        JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);
    
        JavaRDD<String> distColors = sparkContext.parallelize(colors);
        JavaRDD<String> filteredColors = distColors.filter(color -> color.equals("red"));
        
        System.out.println(filteredColors.count());
    }
}
