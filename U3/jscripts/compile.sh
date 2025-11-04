rm -r binaries/
javac -encoding UTF-8 Main.java -d binaries
javac -encoding UTF-8 pkg/**/*.java -d binaries
javac -encoding UTF-8 lib/**/*.java -d binaries