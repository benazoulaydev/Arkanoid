# 330485269
# azoulab1
bin:
	mkdir bin
compile: bin
	find src -name "*.java" > sources.txt
	javac -d bin -cp biuoop-1.4.jar @sources.txt
jar: compile
	jar cfm ass7game.jar Manifest.txt -C bin . -C resources .
run: jar
	java -jar ass7game.jar
check:
	java -jar checkstyle-5.7-all.jar -c biuoop.xml src/*/*/*.java src/*/*.java src/*.java
