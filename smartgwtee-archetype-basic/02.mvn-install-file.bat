set OJDBC_JAR=%CD%\ojdbc14.jar
set OJDBC_POM_XML=%CD%\ojdbc14-pom.xml
call mvn	install:install-file ^
		-Dfile=%OJDBC_JAR% ^
		-DpomFile=%OJDBC_POM_XML%

pause
