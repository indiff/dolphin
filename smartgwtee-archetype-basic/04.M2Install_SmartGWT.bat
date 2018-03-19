set SMART_GWT_VERSION=3.0
set SMART_GWT_HOME=D:\GWT\smartgwt-%SMART_GWT_VERSION%

call mvn	install:install-file "-Dfile=%SMART_GWT_HOME%\smartgwt.jar" ^
		-DgroupId=com.smartgwt ^
		-DartifactId=smartgwt ^
		-Dversion=%SMART_GWT_VERSION%  ^
		-Dpackaging=jar  ^
		-DgeneratePom=true  ^
		-DcreateChecksum=true

call mvn	install:install-file "-Dfile=%SMART_GWT_HOME%\smartgwt-skins.jar" ^
		-DgroupId=com.smartgwt ^
		-DartifactId=smartgwt-skins ^
		-Dversion=%SMART_GWT_VERSION%  ^
		-Dpackaging=jar  ^
		-DgeneratePom=true  ^
		-DcreateChecksum=true


pause