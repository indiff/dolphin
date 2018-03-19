set GWT_VERSION=2.4.0
set GWT_WINDOWS_HOME=D:\GWT\gwt-%GWT_VERSION%

call mvn	install:install-file "-Dfile=%GWT_WINDOWS_HOME%\gwt-user.jar" ^
		-DgroupId=com.google.gwt ^
		-DartifactId=gwt-user ^
		-Dversion=%GWT_VERSION%  ^
		-Dpackaging=jar  ^
		-DgeneratePom=true  ^
		-DcreateChecksum=true

call mvn	install:install-file "-Dfile=%GWT_WINDOWS_HOME%\gwt-servlet.jar"  ^
		-DgroupId=com.google.gwt ^
		-DartifactId=gwt-servlet ^
		-Dversion=%GWT_VERSION%  ^
		-Dpackaging=jar  ^
		-DgeneratePom=true  ^
		-DcreateChecksum=true

call mvn	install:install-file "-Dfile=%GWT_WINDOWS_HOME%\gwt-dev.jar"   ^
		-DgroupId=com.google.gwt ^
		-DartifactId=gwt-dev ^
		-Dversion=%GWT_VERSION%  ^
		-Dpackaging=jar  ^
		-DgeneratePom=true  ^
		-DcreateChecksum=true

pause