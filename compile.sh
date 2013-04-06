DIR=~/projects/dvrlib/
if [ -d $DIR ] ; then
   # Build
   echo " *** Building source..."
   mkdir -p $DIR/build
   cd $DIR/src/
   javac -d ../build/ dvrlib/*/* -g -Xlint:unchecked
   if [ "$?" != "0" ] ; then
      echo " *** Error(s) while compiling source; Aborting"
      exit
   fi

   if [ -d $DIR/test-src ] ; then
      # Build tests
      echo " *** Building tests..."
      mkdir -p $DIR/test-build
      cd $DIR/test-src/
      javac -cp /usr/share/java/junit.jar:../build/ -d ../test-build/ dvrlib/*/* -Xlint:unchecked
      if [ "$?" != "0" ] ; then
         echo " *** Error(s) while compiling tests; Aborting"
         exit
      fi

      # Run tests
      echo " *** Running tests..."
      cd $DIR/test-build
      java -cp /usr/share/java/junit.jar:../build/:./ -ea org.junit.runner.JUnitCore $(/usr/bin/find . -iname \*.class | /usr/bin/grep -v '\$' | sed 's:\./\(.*\)\.class:\1:' | tr '/' '.')
      if [ "$?" != "0" ] ; then
         echo " *** Error(s) while running tests; Aborting"
         exit
      fi
   fi

   # Package
   echo " *** Packaging..."
   mkdir -p $DIR/dist
   cd $DIR/dist/
   jar cf DvRLib.jar -C ../build/ dvrlib
   if [ "$?" != "0" ] ; then
      echo " *** Error(s) while packaging; Aborting"
      exit
   fi

   # Done
   echo " *** Done"
else
   echo " *** Directory $DIR does not exist; Aborting"
fi
