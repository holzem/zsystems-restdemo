# zsystems-restdemo

This demo shows how to create a [Spring Boot](https://projects.spring.io/spring-boot/) rest service on a IBM Z mainframe.

IBM Z is different to other server environments but it does not need to be. True, you have to run the IBM operating system but after that you are not bound to COBOL (although the language has its merits). 

So - how much is really necessary to create a more comfortable environment for a Java developer? The answer is: not much.  

The service in this demo is a very simple one, because I wanted to focus mainly on the mainframe specifics. It is a simple GET service reading a file on Windows and a PDS member on the mainframe. It provides an idea how to encapsulate functionality using dependency injection to make the implementation less platform dependent.

Access to special IBM Z resources is shown in [https://github.com/zsystems/java-samples](https://github.com/zsystems/java-samples). All are based on `ibmjzos.jar` located in a subdirectory of the IBM JDK on an IBM Z mainframe.

## Launching a Java Demon on IBM Z (also known as a Started Task)

The first thing necessary on the mainframe is a job. The following job is used to create a process starting the shell script `/tmp/restdemo/RESTDEMO.sh` inside a IBM Z UNIX environment.

```
//<jobname> JOB (<account>),'<name>',TIME=5,REGION=512M
// CLASS=<class>,MSGCLASS=<msgclass>  
//*                                               
//* Start Unix Environment                        
//*                                               
//RESTDEMO EXEC PGM=BPXBATCH                      
//*                                               
//* Start Java using a shell script               
//*                                               
//STDPARM DD *                                    
SH /tmp/restdemo/RESTDEMO.sh                
/*                                                
//STDIN   DD PATH='/dev/null'                     
//STDERR  DD SYSOUT=(*)                           
//STDOUT  DD SYSOUT=(*)                           
```

The shell script starts a java runtime. Additionally to starting the JVM the script handles the temporary work directory of the process. The example is stored in the `/tmp` folder of the operating system.    

```
#!/bin/sh                                                               
                                                                        
export STC=RESTDEMO                                                     
export WORKDIR=/tmp/restdemo                                      
export TMPDIR=$WORKDIR/temp                                             
export JAVA_HOME=<root of Java8 installation>                                  
export JAVA_OPTS=-Xmx512m                                               
export JAVA_CLASSPATH="$WORKDIR/restdemo-0.0.1-SNAPSHOT.jar:."          
export JAVA_MAIN="org.springframework.boot.loader.JarLauncher"          
export APP_OPTS=""                                                      
                                                                        
echo " "                                                                
echo "Running STC $STC"                                                 
echo " "                                                                
echo "WORKDIR:        $WORKDIR"                                         
echo "TMPDIR:         $TMPDIR"                                          
echo "JAVA_HOME:      $JAVA_HOME"                                       
echo "JAVA_OPTS:      $JAVA_OPTS"                                       
echo "JAVA_CLASSPATH: $JAVA_CLASSPATH"                                  
echo "JAVA_MAIN:      $JAVA_MAIN"                                       
echo " "                                                                
echo "Change to working directory"                                      
                                                                        
cd $WORKDIR                                                             
                                                                        
echo " "                                                                
echo "Remove/Create workdirectory"                                      
                                                                        
rm -Rfv $WORKDIR/temp                                                   
mkdir $WORKDIR/temp                                                     
                                                                        
echo " "                                                                
echo "Start Java Application"                                           
                                                                        
$JAVA_HOME/bin/java $JAVA_OPTS $APP_OPTS -cp $JAVA_CLASSPATH:. $JAVA_MAIN
                                                                        
echo " "
echo "Java Application done" 
                             
echo " "                     
echo "Cleaning up...."       
                             
echo " "                     
echo "Remove workdirectory"  
                             
rm -Rfv $WORKDIR/temp        
                             
echo " "                     
echo "Done"                  
echo " "                                                                                                                
```

## Software needed to Edit and Compile the Example 

* [IBM JDK (Version 8)](https://developer.ibm.com/javasdk/downloads/)
* [Eclipse IDE for Java EE Developers](http://www.eclipse.org/downloads/eclipse-packages/)
* [IBM Explorer for z/OS](https://developer.ibm.com/mainframe/products/downloads/eclipse-tools/) (supports Eclipse Neon V4.6 and seems to work on Oxygen as well)
* [Spring Tool Suite](https://spring.io/tools/sts/all)
* `ibmjzos.jar` downloaded from IBM z (from the `lib/ext` folder of the Java installation)copied and stored in the artifact repository
 
## Pitfalls

* The port `8080` in application.properties should not be used when starting the process on the mainframe. Ask your administrator for an available port you are allowed to use for the process.
* The `FileSystemServiceConfiguration` distinguishes between Windows 7 and z/OS as operating system. If you run on a newer Windows system you will add/change the value of `os.name`.

