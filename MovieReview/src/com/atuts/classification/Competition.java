package com.atuts.classification;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.rules.DecisionTable;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;


public class Competition {
	  Attribute segmentLength,lastWord,sentence,bagOfWords;
	    Attribute ClassAttribute;
	    ArrayList<String> featureVectorClassValues;
	    ArrayList<Attribute> featureVectorAttributes;
	    Instances TrainingSet;
	    Instances TestingSet;
	    Hashtable table;
	    SentiWordNetDemoCode sentiwordnet;
	    ArrayList<String> phraseId;
	    int hashval=0;
public static void main(String[] a)
{
	String testingDataFileLocation= "IOfolder/test.tsv";
    String trainingDataFileLocation="IOfolder/train.tsv";
   
    Competition ct=  new Competition();
    ct.classify(trainingDataFileLocation,testingDataFileLocation);
}



public  void readTSV(String location,Instances set)
{
	   StringTokenizer st ;
       BufferedReader TSVFile=null;
	try {
		TSVFile = new BufferedReader(new FileReader(location));
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
       List<String>dataArray = new ArrayList<String>() ;
       String dataRow=null;
	try {
		 String line;
		 
	        /**
	         * Looping the read block until all lines in the file are read.
	         */
		 int i=0;
	        while ((line = TSVFile.readLine()) != null) {
	 
	            /**
	             * Splitting the content of tabbed separated line
	             */
	            String datavalue[] = line.split("\t");
	            String value3 = datavalue[2];
	            if(i!=0){
	            String value4= datavalue[3];
	            int sent=Integer.parseInt(value4);
	            /**
	             * Printing the value read from file to the console
	             */
	        //    System.out.println(value4+"*\t" + value3);
	            
	            
	            String var = value3.replace(".","");
	            var=var.replace("//'","");
	            var=var.replace("\"", "");
	            var=var.replace("!", "");
	            var=var.replace("'","");
	            var=var.trim();
	            String[] words = var.split("\\s+");

	            Instance temp = new DenseInstance(4);

	            /*Attribute messageAtt = set.attribute("Message");
	            temp.setValue(messageAtt, messageAtt.addStringValue(var));*/
	          temp.setValue(featureVectorAttributes.get(0),words.length);
	          temp.setValue(featureVectorAttributes.get(1),getHashValue(words[words.length-1]));
	          
	          int averageValue=0;
	          
	          //bag of words are depend on Resturant Context
	          for(int i1=0;i1<words.length;i1++){
	        	  if (SentimentStrings.neg5.indexOf(words[i1]) > -1) {
	        		  averageValue-=5;
	    			} else if (SentimentStrings.neg4.indexOf(words[i1]) > -1) {
	    				averageValue-=4;
	    			} else if (SentimentStrings.neg3.indexOf(words[i1]) > -1) {
	    				averageValue-=3;
	    			} else if (SentimentStrings.neg2.indexOf(words[i1]) > -1) {
	    				averageValue-=2;
	    			} else if (SentimentStrings.neg1.indexOf(words[i1]) > -1) {
	    				averageValue-=1;
	    			}
	        	  
	        	  if (SentimentStrings.pos5.indexOf(words[i1]) > -1) {
	        		  averageValue+=5;
	    			} else if (SentimentStrings.pos4.indexOf(words[i1]) > -1) {
	    				averageValue+=4;
	    			} else if (SentimentStrings.pos3.indexOf(words[i1]) > -1) {
	    				averageValue+=3;
	    			} else if (SentimentStrings.pos2.indexOf(words[i1]) > -1) {
	    				averageValue+=2;
	    			} else if (SentimentStrings.pos1.indexOf(words[i1]) > -1) {
	    				averageValue+=1;
	    			}  
	          }
	          temp.setValue(featureVectorAttributes.get(2),averageValue);
	          temp.setValue(featureVectorAttributes.get(featureVectorAttributes.size() - 1),value4);
	        
	          temp.setDataset(TrainingSet);
	          set.add(temp);
	            }
	            i++;
	        }
       TSVFile.close();

       // End the printout with a blank line.
      // System.out.println();
	} catch (IOException e) {
		e.printStackTrace();
	} 
}




public  void readTSVTest(String location,Instances set)
{
	   StringTokenizer st ;
       BufferedReader TSVFile=null;
	try {
		TSVFile = new BufferedReader(new FileReader(location));
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
       List<String>dataArray = new ArrayList<String>() ;
       String dataRow=null;
	try {
		 String line;
		 
	        /**
	         * Looping the read block until all lines in the file are read.
	         */
		 int i=0;
	        while ((line = TSVFile.readLine()) != null) {
	 
	            /**
	             * Splitting the content of tabbed separated line
	             */
	            String datavalue[] = line.split("\t");
	            String value3 = datavalue[2];
	            
	            if(i!=0){
	            /**
	             * Printing the value read from file to the console
	             */
	            
	            	phraseId.add(datavalue[0]);
	            String var = value3.replace(".","");
	            var=var.replace("//'","");
	            var=var.replace("\"", "");
	            var=var.replace("!", "");
	            var=var.replace("'","");
	            var=var.trim();
	            String[] words = var.split("\\s+");

	            Instance temp = new DenseInstance(4);

	            /*Attribute messageAtt = set.attribute("Message");
	            temp.setValue(messageAtt, messageAtt.addStringValue(var));*/
	          temp.setValue(featureVectorAttributes.get(0),words.length);
	          temp.setValue(featureVectorAttributes.get(1),getHashValue(words[words.length-1]));
	          
	          int averageValue=0;
	          
	          //bag of words are depend on Resturant Context
	          for(int i1=0;i1<words.length;i1++){
	        	  if (SentimentStrings.neg5.indexOf(words[i1]) > -1) {
	        		  averageValue-=5;
	    			} else if (SentimentStrings.neg4.indexOf(words[i1]) > -1) {
	    				averageValue-=4;
	    			} else if (SentimentStrings.neg3.indexOf(words[i1]) > -1) {
	    				averageValue-=3;
	    			} else if (SentimentStrings.neg2.indexOf(words[i1]) > -1) {
	    				averageValue-=2;
	    			} else if (SentimentStrings.neg1.indexOf(words[i1]) > -1) {
	    				averageValue-=1;
	    			}
	        	  
	        	  if (SentimentStrings.pos5.indexOf(words[i1]) > -1) {
	        		  averageValue+=5;
	    			} else if (SentimentStrings.pos4.indexOf(words[i1]) > -1) {
	    				averageValue+=4;
	    			} else if (SentimentStrings.pos3.indexOf(words[i1]) > -1) {
	    				averageValue+=3;
	    			} else if (SentimentStrings.pos2.indexOf(words[i1]) > -1) {
	    				averageValue+=2;
	    			} else if (SentimentStrings.pos1.indexOf(words[i1]) > -1) {
	    				averageValue+=1;
	    			}  
	          }
	          temp.setValue(featureVectorAttributes.get(2),averageValue);
	          //temp.setValue(featureVectorAttributes.get(featureVectorAttributes.size() - 1),value4);
	        
	          temp.setDataset(TrainingSet);
	          set.add(temp);
	            }
	            i++;
	        }
       TSVFile.close();

       // End the printout with a blank line.
      // System.out.println();
	} catch (IOException e) {
		e.printStackTrace();
	} 
}

public Competition(){

    table=new Hashtable<String,Integer>();
    phraseId=new ArrayList<>();
    // Declare numeric feature vector
    segmentLength = new Attribute("segmentLength");
    lastWord= new Attribute("lastWord");
    bagOfWords=new Attribute("bagOfWords"); //assign number to each word. 
    
    //Decleare String attribute all words as feature vector but accuracy low, check the analysis in TextClassifier
    FastVector attributes = new FastVector();
    sentence= new Attribute("attr", (FastVector) null);
    

    // Declare the class attribute with positive and negative
    featureVectorClassValues=new ArrayList<String>();
    featureVectorClassValues.add("0");
    featureVectorClassValues.add("1");
    featureVectorClassValues.add("2");
    featureVectorClassValues.add("3");
    featureVectorClassValues.add("4");

    ClassAttribute = new Attribute("theClass",featureVectorClassValues);

    // Declare the feature vector
    featureVectorAttributes = new ArrayList<Attribute>();
    featureVectorAttributes.add(segmentLength);
    featureVectorAttributes.add(lastWord);
    featureVectorAttributes.add(bagOfWords);
    //featureVectorAttributes.add(nGram);
    //featureVectorAttributes.add(sentence);
    //class
    featureVectorAttributes.add(ClassAttribute);
  
    // Create an empty training set
    TrainingSet = new Instances("Rel", featureVectorAttributes,1000);

    // Set class index
    TrainingSet.setClassIndex(featureVectorAttributes.size() - 1);

    // Create an empty testing set
    TestingSet = new Instances("Rel", featureVectorAttributes, 1000);
    // Set class index
    TestingSet.setClassIndex(featureVectorAttributes.size() - 1);


}

public void readFromFile(String location,Instances set) throws IOException {
	
    BufferedReader br = new BufferedReader(new FileReader(new File(location)));
    String line;
    
    while ((line = br.readLine()) != null) {

        String key=line.substring(0, 3);
        String val=line.substring(3).trim();
   	      String var = val.replace(".","");
        var=var.replace("//'","");
        var=var.replace("\"", "");
        var=var.replace("!", "");
        var=var.replace("'","");
        var=var.trim();
        String[] words = var.split("\\s+");

        Instance temp = new DenseInstance(4);

        /*Attribute messageAtt = set.attribute("Message");
        temp.setValue(messageAtt, messageAtt.addStringValue(var));*/
      temp.setValue(featureVectorAttributes.get(0),words.length);
      temp.setValue(featureVectorAttributes.get(1),getHashValue(words[words.length-1]));
      
      int averageValue=0;
      
      //bag of words are depend on Resturant Context
      for(int i=0;i<words.length;i++){
    	  if (SentimentStrings.neg5.indexOf(words[i]) > -1) {
    		  averageValue-=5;
			} else if (SentimentStrings.neg4.indexOf(words[i]) > -1) {
				averageValue-=4;
			} else if (SentimentStrings.neg3.indexOf(words[i]) > -1) {
				averageValue-=3;
			} else if (SentimentStrings.neg2.indexOf(words[i]) > -1) {
				averageValue-=2;
			} else if (SentimentStrings.neg1.indexOf(words[i]) > -1) {
				averageValue-=1;
			}
    	  
    	  if (SentimentStrings.pos5.indexOf(words[i]) > -1) {
    		  averageValue+=5;
			} else if (SentimentStrings.pos4.indexOf(words[i]) > -1) {
				averageValue+=4;
			} else if (SentimentStrings.pos3.indexOf(words[i]) > -1) {
				averageValue+=3;
			} else if (SentimentStrings.pos2.indexOf(words[i]) > -1) {
				averageValue+=2;
			} else if (SentimentStrings.pos1.indexOf(words[i]) > -1) {
				averageValue+=1;
			}  
      }
      temp.setValue(featureVectorAttributes.get(2),averageValue);
      temp.setValue(featureVectorAttributes.get(featureVectorAttributes.size() - 1),key);
        
      temp.setDataset(TrainingSet);
      set.add(temp);
    }
    br.close();
}




public void classify(String trainingFile,String testingFile) {

    try {
    	readTSV(trainingFile,TrainingSet);
    	readTSVTest(testingFile,TestingSet);
        //TO-Do change the model different model
    	DecisionTable cModel = new DecisionTable();
        cModel.buildClassifier(TrainingSet);

        Evaluation eTest = new Evaluation(TrainingSet);
        /*
        eTest.evaluateModel(cModel, TestingSet);*/
        
        // check individual instance
       /*   DenseInstance instance = new DenseInstance(0);
        FastVector fvNominalVal = new FastVector(5);
		fvNominalVal.addElement("0");
		fvNominalVal.addElement("1");
		fvNominalVal.addElement("2");
		fvNominalVal.addElement("3");
		fvNominalVal.addElement("4");
		Attribute attribute1 = new Attribute("class", fvNominalVal);
		Attribute attribute2 = new Attribute("text",(FastVector) null);
        
		instance.setValue(attribute2, "Our appetizer of calamari was great");
        
       double pred =eTest.evaluateModelOnce(cModel, instance);
        System.out.println(pred); */
        
       
       double[] prediction=cModel.distributionForInstance(TestingSet.get(2));

        //output predictions
        for(int i=0; i<prediction.length; i=i+1)
        {
            System.out.println("Probability of class "+
            		TestingSet.classAttribute().value(i)+
                               " : "+Double.toString(prediction[i]));
        }
        
        PrintWriter writer = new PrintWriter("IOfolder/test1.csv", "UTF-8");
        for(int i=0;i<TestingSet.size();i++)
        {
        double pred = cModel.classifyInstance(TestingSet.get(i));
        String s=phraseId.get(i)+"," + TestingSet.classAttribute().value((int) pred);
        writer.println(s);
        System.out.println(s);
        }
        writer.close();
        /*
        //print out the results
        System.out.println("=====================================================================");
        System.out.println("Results for "+this.getClass().getSimpleName());
        String strSummary = eTest.toSummaryString();
        System.out.println(strSummary);

        System.out.println("F-measure : "+eTest.weightedFMeasure());
        System.out.println("precision : "+eTest.weightedPrecision());
        System.out.println("recall : "+eTest.weightedRecall());
        System.out.println("=====================================================================");*/


    } catch (Exception e) {
        e.printStackTrace();
    }

}


//assign each word to integer
int getHashValue(String word){

    if(table.containsKey(word)){
        return (Integer)table.get(word);
    }else{
        table.put(word,hashval++);
        return getHashValue(word);
    }

}
}
