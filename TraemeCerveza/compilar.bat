set PATH=C:\Program Files\Java\jdk1.8.0_261\bin;%PATH%
set classpath=src\lib\Base64.jar;src\lib\GraphLayout.jar;src\lib\bcel.jar;\src\lib\http.jar;src\lib\iiop.jar;src\lib\jade.jar;src\lib\jadeTools.jar;src\lib\jadex_examples.jar;src\lib\jadex_jadeadapter.jar;src\lib\jadex_rt.jar;src\lib\jadex_standalone.jar;src\lib\jadex_tools.jar;src\lib\jhall.jar;src\lib\jibx-bind.jar;src\lib\jibx-extras.jar;src\lib\jibx-run.jar;src\lib\xpp3.jar;src;src\lib\commons-commons\commons-codec-1.3.jar

javac src\ontology\actions\*java 
javac src\ontology\concepts\*java 
javac src\ontology\predicates\*java 
javac src\robot\beliefs\*java
javac src\fridge\plans\*java
javac src\human\plans\*java
javac src\robot\plans\*java
pause
jar cvf TraemeCervezasJadex *
pause 

