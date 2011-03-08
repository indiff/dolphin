@Echo off
REM del /f /s /q jutils.jar
rmdir /s /q bin/.svn
set FILENAME=jtuils34.jar
set java_home=D:\Java\jdk1.6.0_24
COPY /Y "plugin.xml" "bin/plugin.xml"
xcopy /E /H /K /C /Y icons "bin/icons"
%java_home%\bin\jar.exe cvf %FILENAME% -C bin/ .
pause