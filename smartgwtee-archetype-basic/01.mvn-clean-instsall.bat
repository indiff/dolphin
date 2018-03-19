call mvn	clean install ^
	-Dmaven.test.skip=true
rmdir /s /q ".\target\classes"
pause
